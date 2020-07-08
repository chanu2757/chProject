package admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import member.DBUtil;
import oracle.jdbc.OracleTypes;

public class pcManagement {
	public void pcManagement() {
	while(true) {
		Admin ad = new Admin();
		ad.currentPC();
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t Pc 관리");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t0. 뒤로가기");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t종료시킬 번호 선택:");
		Scanner sc = new Scanner(System.in);
		String cho = sc.nextLine();
		
		if(cho.equals("1")) {
			//pc번호 입력해서 status를 0으로 만드는 프로시저
			procendpc(cho);
		}
		else if(cho.equals("0")) {
			// 이전으로 
			break;
		}
		else{
			for (int i=0;i<cho.length();i++) {
				int b =0;
				b=cho.charAt(i);
				if(b<48 && b>57) {
					System.out.println("\t\t\t숫자만 입력가능 합니다.");
				}
			}
		}//else
	}
}
	public void procendpc(String pcnum) {
			Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procendpc(?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.setString(1, pcnum);
				stat.registerOutParameter(2, OracleTypes.NUMBER);
				procremainingTime(pcnum);
				
				stat.executeUpdate();
				int result = stat.getInt(2);
				
				if(result ==1) {
				System.out.println("pc가 정상적으로 종료되었습니다.");
				procremainingTime(pcnum);
				}
				else {
					System.out.println("오류");
				}
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}
	public void procremainingTime(String membernum) {
		//종료시 남은시간을 저장하는 메소드
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);

			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procremainingTime(?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.setString(1, membernum);
				stat.registerOutParameter(2, OracleTypes.NUMBER);
				
				stat.executeUpdate();
				int result = stat.getInt(2);
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}//procremainingTime
	}


