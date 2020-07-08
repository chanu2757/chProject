package nonMomber;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class nonMemberUser {

	/**
	 * 회원 자리 선택메뉴 메소드
	 */
	public void nonMemberSitPc() {
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    비회원 자리 선택");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t0. 뒤로가기");			

			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 자리 선택: ");

			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			if (sel.equals("0")) {
				
				break;
								
			} else {
				
				//자리 선택
				
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				ResultSet rs = null;				
				
				try {
					
					//프로시저 호출 준비
					String sql = "{ call procUpdateNomemberSitPc(?) }";
					conn = util.open("211.89.63.61", "chproject", "java1234");
					stat = conn.prepareCall(sql);
					
					//String을 int로 변환
					int intSel = Integer.parseInt(sel);
				
					//프로시저 매개변수 설정
					stat.setInt(1, intSel);			
					
					//프로시저 실행
					stat.executeUpdate();	
				
					System.out.println();
					System.out.println("\t\t\t자리선택을 완료했습니다.");
					System.out.println("\t\t\t엔터를 입력하시면 카드번호 입력 메뉴로 이동합니다.");
					scan.nextLine();					
					
					//비회원 카드 번호 입력
					procUpdateCardnumToPc(intSel);
					
					rs.close();
					stat.close();
					conn.close();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("자리선택에 실패했습니다");
				}
				
			} //else
			
		}//while
		
	}//procUpdateMemberSitPc

	
	
	public void procUpdateCardnumToPc(int intSel) {
		
			Scanner scan = new Scanner(System.in);
			
	while (true) {			
			
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	
			//카드 번호 출력
			procPrintNouseCard();
			
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    비회원 카드번호 입력");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t0. 뒤로가기");			
	
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 번호 입력: ");
	
			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		
		
		
			Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;				
			
			try {
				
				//프로시저 호출 준비
				String sql = "{ call procUpdateCardnumToPc(?,?) }";
				conn = util.open("localhost", "chproject", "java1234");
				stat = conn.prepareCall(sql);
				
				int sel1 = Integer.parseInt(sel);
				
				//프로시저 매개변수 설정
				stat.setInt(1, sel1);		
				stat.setInt(2, intSel);			
				
				//프로시저 실행
				stat.executeUpdate();	
			
				System.out.println();
				System.out.println("\t\t\t카드번호입력을 완료했습니다.");
				System.out.println("\t\t\t엔터를 입력하시면 충전하기 메뉴로 이동합니다.");
				scan.nextLine();					
				
				//비회원 충전하기 
				procUpdateNonChargetime(intSel);
				
				rs.close();
				stat.close();
				conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("카드번호입력에 실패했습니다");
			}
	}
		
	}

	/**
	 * 비회원자리에 시간충전하는 메소드
	 * @param intSel 선택한 자리
	 */
	public void procUpdateNonChargetime(int intSel) {

		while (true) {		
		
		Scanner scan = new Scanner(System.in);
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t\t    비회원 충전 하기");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t0. 뒤로가기");			
		System.out.println("\t\t\t\t    1000원 당 1시간");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t▷ 금액 입력: ");

		String sel = scan.nextLine();
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
						
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		
		try {
			
			//프로시저 호출 준비
			String sql = "{ call procUpdateNonChargetime(?,?) }";
			conn = util.open("localhost", "chproject", "java1234");
			stat = conn.prepareCall(sql);
			
			int sel1 = Integer.parseInt(sel);
			
			//프로시저 매개변수 설정
			
			if(sel1 < 1000) {
				System.out.println("\t\t\t1000원 이상부터 충전 가능합니다.");
				procUpdateNonChargetime(intSel);
			}
			
			stat.setInt(1, sel1);		
			stat.setInt(2, intSel);		
			
			//프로시저 실행
			stat.executeUpdate();	
		
			System.out.println();
			System.out.println("\t\t\t카드번호입력을 완료했습니다.");
			System.out.println("\t\t\t엔터를 입력하시면 비회원 메뉴로 이동합니다.");
			scan.nextLine();					
			
			//비회원 메인메뉴로 이동 
			nonMemberMain nonMemberMain = new nonMemberMain();
			nonMemberMain.nonMembermain();;
			
			rs.close();
			stat.close();
			conn.close();
			
			} catch (Exception e) {
			e.printStackTrace();
			System.out.println("카드번호입력에 실패했습니다");
			}
		
		}
		
	}



	/**
	 * 사용하지 않은 카드번호 가져오는 메소드
	 */
	public void procPrintNouseCard() {
		
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		
		try {
			
			//프로시저 호출 준비
			String sql = "{ call procPrintNouseCard(?) }";
			conn = util.open("211.89.63.61", "chproject", "java1234");
			stat = conn.prepareCall(sql);
			
			//프로시저 매개변수 설정
			stat.registerOutParameter(1, OracleTypes.CURSOR); //결과값 받아올 매개변수 설정
			
			//프로시저 실행
			stat.executeQuery();
			
			rs = (ResultSet) stat.getObject(1);
			int i = 1;

			while (rs.next()) {
				
				System.out.println( "\t\t\t[" + i + "]  " + rs.getInt("cardNum"));
				i++;
			}

			rs.close();
			stat.close();
			conn.close();


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("카드번호 출력을 실패했습니다");
		}
		
	}
		
}//class