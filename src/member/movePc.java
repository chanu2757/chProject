package member;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class movePc {
// 자리이동하는 프로시저
	public void move(MemberUser memberuser) {
		Connection conn = null;
			Statement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procmovepc(?,?,?) }";
				stat = conn.createStatement();
				
				System.out.print("\t\t\t옮길 pc번호 입력:");
				String pcnum = scan.nextLine();
	
				
				rs = stat.executeQuery(sql);
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}
	}

