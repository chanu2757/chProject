package nonMomber;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import member.MemberUser;
import oracle.jdbc.OracleTypes;

public class selectPC {
	//비회원의 자리 선택 
	MemberUser user = new MemberUser();
	public void nonselectpc(MemberUser user) {
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procselectpc(?,?,?,?) }";
				stat = conn.prepareCall(sql);
				
				
				stat.setInt(1, 1);
				System.out.print("\t\t\t자리선택(pc번호):");
				String select = scan.nextLine();
				stat.setString(3, select);
				stat.registerOutParameter(4, OracleTypes.NUMBER);
				stat.executeUpdate();
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}
	}
