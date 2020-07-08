package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class doing {
	public void doingmenu() {
		while(true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t			하는중");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t1. 게임하기");
			System.out.println("\t\t\t2. 영화보기");
			System.out.println("\t\t\t3. 드라마보기");
			System.out.println("\t\t\t4. 음악듣기");
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷입력: ");
			Scanner sc = new Scanner(System.in);
			String cho = sc.nextLine();
			
			if(cho.equals("1")) {
				//게임하기 
				procPrintDoing(cho);
			}
			else if(cho.equals("2")) {
				//영화보기
				procPrintDoing(cho);
			}
			else if(cho.equals("3")) {
				//드라마보기
				procPrintDoing(cho);
			}
			else if(cho.equals("4")) {
				//음악듣기
				procPrintDoing(cho);
			}
			else if(cho.equals("0")) {
				// 이전으로 
				break;
			}
			}
		}
	
public void procPrintDoing(String cho) {
		while(true) {
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procPrintDoing(?,?) }";
			stat = conn.prepareCall(sql);
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			if(cho.equals("1")){
			System.out.print("\t\t\t실행할  게임:");
			}
			else if(cho.equals("2")){
				System.out.print("\t\t\t실행할  영화:");
			}
			else if(cho.equals("3")){
				System.out.print("\t\t\t실행할  드라마:");
			}
			else if(cho.equals("4")){
				System.out.print("\t\t\t실행할  음악:");
				}
			String Do = scan.nextLine(); 
			Do = Do.replace(" ", "");
	
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			stat.setString(1,Do);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			
			stat.executeQuery();
			rs = (ResultSet)stat.getObject(2);
				if(rs.next()) {
				System.out.printf("\t\t\t%s을 실행합니다.\n",Do);
				//pc테이블의 하는중에 업데이트 메소드
				procUpdatepcDoing(Do);
				
				break;
			}
			else if(!rs.next()) {
				System.out.printf("\t\t\t%s이 없습니다.\n",Do);
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\t\t\t설치하시겠습니까?");
				System.out.println("\t\t\t1.설치하기");
				System.out.println("\t\t\t0.뒤로가기");
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.print("\t\t\t▷입력:");
				Scanner sc = new Scanner(System.in);
				String cho2 = sc.nextLine();
				if(cho2.equals("1")) {
					//설치프로시저
					procAddDoing(Do,cho);
				}
				else if(cho2.equals("0")) {
						break;		
				}
				else {
					System.out.println("\t\t\t올바르지 않은 메뉴 번호입니다.");
				}
			}
			
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		}
	}

private void procAddDoing(String activity,String category) {
	Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procAddDoing(?,?,?) }";
			
		
			stat = conn.prepareCall(sql);
			
			stat.setString(1,activity);
			stat.setInt(2,Integer.parseInt(category));
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			
			stat.executeQuery();
			int result = stat.getInt(3);
			if(result==1) {
				System.out.printf("\t\t\t%s을(를) 다운로드 완료했습니다.\n",activity);
			}
			else {
				System.out.printf("\t\t\t%s을(를) 다운로드 하지 못했습니다.\n",activity);
			}
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		
	}
public void procUpdatepcDoing(String activity) {
	Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();

		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procUpdatepcDoing(?,?,?) }";
			stat = conn.prepareCall(sql);
			
			stat.setInt(1, 1); //회원번호
			stat.setString(2, activity);
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			
			stat.executeUpdate();
			int result = stat.getInt(3);
		
		
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		
	}
}

	

