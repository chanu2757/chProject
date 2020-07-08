package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class endPc {
	public void Bye(MemberUser memberuser) {
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t			사용종료");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t          사용종료 하시겠습니까?(Y/N)");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷입력: ");
			String cho = sc.nextLine();
			if(cho.equals("Y")|| cho.equals("y")) {
				//pc번호 입력해서 status를 0으로 만드는 프로시저
				procendpc(memberuser);	
				System.exit(0);
			}
			else if(cho.equals("N")||cho.equals("n") ) {
				// 이전으로 
				break;
			}
			else{
				for (int i=0;i<cho.length();i++) {
					int b =0;
					b=cho.charAt(i);
					if(b<48 && b>57) {
						System.out.println("숫자만 입력가능 합니다.");
					}
				}
			}//else
		}
	}
		public void procendpc(MemberUser membernum) {
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				ResultSet rs = null;
				Scanner scan = new Scanner(System.in);
				
				try {
					conn = util.open("localhost","chproject","java1234");
					String sql = "{ call procPCManagement(?,?) }";
					stat = conn.prepareCall(sql);
					
					
					stat.setInt(1, membernum.getNum());
					stat.registerOutParameter(2, OracleTypes.NUMBER);
					stat.executeUpdate();
					int result = stat.getInt(2);
					
					if(result ==1) {
					System.out.println("\t\t\tpc가 정상적으로 종료되었습니다.");
					}
					else {
						System.out.println("모야");
					}
			
					
					conn.close();
					stat.close();
				} catch (Exception e) {
					System.out.println("Ex07_CallableStatment.m5()");
					e.printStackTrace();
				}
				
			}
		public void procremainingTime(int membernum) {
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
					
					stat.setInt(1, membernum);
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
