package admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class enquirylist {

public void printlist() {
	while(true) {
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t		문의내역");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		
		// 고객들의 문의 리스트
		procprintenquirylist();
		
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");	
		System.out.println("\t\t\t0. 뒤로가기");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t▷번호 선택: ");	
		Scanner sc = new Scanner(System.in);
		String cho = sc.nextLine();
		System.out.print("\t\t\t▷답변 입력: ");	
		String content = sc.nextLine();
		//번호선택, 답변 하는 프로시저
		if(cho.equals("0")) {
			// 이전으로 
			break;
		}
		else {
				procAdminReply(cho, content);
				
			}
		}
		
	}

	/**
	 * 고객문의에 답변다는 메소드
	 * @param cho 선택한 문의
	 * @param content 답변
	 */
	private void procAdminReply(String cho, String content) {
	
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		Scanner scan = new Scanner(System.in);
		
		try {
			
			conn = util.open("localhost", "chproject", "java1234");
			
			String sql = "{ call procAdminReply(?,?) }";
			
			stat = conn.prepareCall(sql);
			
			//int로 형변환
			int icho = Integer.parseInt(cho);
			
			stat.setInt(1, icho);					
			stat.setString(2, content);
			
			stat.executeUpdate();
			
		
			stat.close();
			conn.close();
				
			System.out.println();
			System.out.println("\t\t\t문의내용 답변에 성공했습니다.");
			System.out.println();
			
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\t\t\t문의내용 답변에 실패했습니다.");
			}
		
	}

	/**
	 * 고객 문의 내역리스트 보는 메소드
	 */
	public void procprintenquirylist() {
		
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			
			//프로시저 호출 준비
			String sql = "{ call procAdminEnquiry(?,?) }";
			conn = util.open("localhost", "chproject", "java1234");
			stat = conn.prepareCall(sql);
			
			//프로시저 매개변수 설정
			stat.registerOutParameter(1, OracleTypes.CURSOR); 
			stat.registerOutParameter(2, OracleTypes.CURSOR); 
			
			//프로시저 실행
			stat.executeQuery();
			
			rs = (ResultSet) stat.getObject(1);
			rs2 = (ResultSet) stat.getObject(2);
			System.out.println("\t\t\t[문의번호]\t[pc번호]\t\t[문의내용]");
		
			while (rs.next()) {
				
				System.out.print("\t\t\t");
				System.out.printf("   %d", rs.getInt("num"));
				System.out.print("\t");
				System.out.printf("   %d", rs.getInt("pcNum"));
				System.out.print("\t\t");
				System.out.print(rs.getString("content"));
				System.out.println();
				
			}
			
//			while (rs2.next()) {
//				
//				System.out.print("\t\t\t");
//				System.out.printf("   %d", rs.getInt("num"));
//				System.out.print("\t");
//				System.out.printf("   %d", rs.getInt("pcNum"));
//				System.out.print("\t\t");
//				System.out.print(rs.getString("content"));
//				System.out.println();
//			}

			rs2.close();
			rs.close();
			stat.close();
			conn.close();

	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		}
		
		
		
	}

}//class
