package admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class Adminlog {
public void login() {
		while(true) {
		Connection conn = null;
		Statement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			conn = util.open("localhost","chproject","java1234");
			stat = conn.createStatement();
			
			String sql = String.format("select id,pw from tblAdmin");
			
			HashMap<String,String> hmap = new HashMap<String,String>();
			rs = stat.executeQuery(sql);
			while(rs.next()) {
				hmap.put(rs.getString("id"),(rs.getString("id")));
				hmap.put(rs.getString("pw"),(rs.getString("pw")));		
			}
			
			System.out.print("\t\t\t▶ ID:");
			String inputId = scan.nextLine();
			System.out.print("\t\t\t▶ PW:");
			String inputPw = scan.nextLine();
			
			if(inputId.equals(hmap.get(inputId)) && inputPw.equals(hmap.get(inputPw))) {
				System.out.println("\t\t\t로그인성공");
				Admin menu = new Admin();
				menu.adminMenu();
				break;
			}
			else if(!inputId.equals(hmap.get(inputId)) || !inputPw.equals(hmap.get(inputPw))) {
				System.out.println("\t\t\t아이디 혹은 비밀번호가 틀렸습니다.");
				break;
			}
			
			rs.close();
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		}
	}
}

