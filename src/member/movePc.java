package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class movePc {
	/**
	 * 회원이 사용중인 pc의 자리를 옮길 때 회원정보를 이동시키는 메소드입니다.
	 * @param 회원정보 객체
	 * @author 김찬우
	 */
	public void move(MemberUser memberuser) {
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procmovepc(?,?,?) }";
				stat = conn.prepareCall(sql);
				
				System.out.print("\t\t\t자리이동할 pc번호:");
				String pcnum = scan.nextLine();
				
				
				stat.setString(1,pcnum);
				stat.setInt(2, memberuser.getNum());
				stat.registerOutParameter(3, OracleTypes.NUMBER);
				
				stat.executeUpdate();
				int result =stat.getInt(3);
				if(result == 1) {
					System.out.println("\t\t\t자리이동이 완료되었습니다.");
				}
				else {
					System.out.println("\t\t\t자리이동 실패!");
				}
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
		}
	}

