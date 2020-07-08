package pc;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

// 회원가입
public class user {

public void signup(){
	
	Connection conn = null;
	CallableStatement stat = null;
	ResultSet rs = null;
	DBUtil util = new DBUtil();
	Scanner sc = new Scanner(System.in);
	try {
		conn = util.open("localhost", "chproject", "java1234");
		String sql = "{ call procAddmember(?,?,?,?,?,?,?)}";
		stat = conn.prepareCall(sql);
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t			회원가입");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		
		System.out.print("\t\t\t ▶ID:");
		String id = sc.nextLine();
		System.out.print("\t\t\t ▶비밀번호:");
		String pw = sc.nextLine();
		System.out.print("\t\t\t ▶이름:");
		String name = sc.nextLine();
		System.out.print("\t\t\t ▶주민등록번호:");
		String ssn = sc.nextLine();
		System.out.print("\t\t\t ▶연락처:");
		String tel = sc.nextLine();
		System.out.print("\t\t\t E-mail:");
		String email = sc.nextLine();
	
		stat.setString(1, id);
		stat.setString(2, pw);
		stat.setString(3, name);
		stat.setString(4, ssn);
		stat.setString(5, tel);
		stat.setString(6, email);
		stat.registerOutParameter(7, OracleTypes.NUMBER);
		
		
	
		stat.executeUpdate();
		int result= stat.getInt(7);
		
		if(result ==1) {
			System.out.println("회원가입 완료!");
		}
		stat.close();
		conn.close();

	} catch (Exception e) {
		System.out.println("회원가입 실패!");
	}

}

}
