package nonMomber;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class chargeTime {
	//비회원의 시간 충전
	
	public void nonchargeTime() {
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("local","chproject","java1234");
				String sql = "{ call procPrintAttendanceStudent(?,?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.executeQuery();
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			} 
			
		}
	}

