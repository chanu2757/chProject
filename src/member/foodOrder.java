package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Scanner;

public class foodOrder {
	
	/**
	 * 회원 주문하는 메소드
	 * @param memberUser 회원정보
	 */
	public void order(MemberUser memberUser) {
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			
			// 현재 로그인 정보
			
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    음식 주문");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t1. 라면(2500원)");
			System.out.println("\t\t\t2. 얼박사(2000원)");
			System.out.println("\t\t\t1. 감자튀김(3500원)");
			System.out.println("\t\t\t2. 감자핫도그(3000원)");
			System.out.println("\t\t\t2. 소떡소떡(2500원)");			
			System.out.println("\t\t\t0. 뒤로가기");
		
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 입력: ");

			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			
			if (sel.equals("1")) {
				procOrder(sel, memberUser);
			} 	
			else if (sel.equals("2")) {
				procOrder(sel, memberUser);
			} 
			else if (sel.equals("3")) {
				procOrder(sel, memberUser);
			} 
			else if (sel.equals("4")) {
				procOrder(sel, memberUser);
			} 
			else if (sel.equals("5")) {
				procOrder(sel, memberUser);
			} 
			
			// 뒤로가기
			else if (sel.equals("0")) {
				
				break;
				
			} 
			// 예외
			else {
				System.out.println("\t\t\t번호를 다시 입력해주세요.");
			}
		}
		
		
		
	}//menu

	private void procOrder(String sel, MemberUser memberUser) {
		
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		Scanner scan = new Scanner(System.in);
				
		try {
			
			conn = util.open("localhost", "chproject", "java1234");
			
			String sql = "{ call procOrder(?,?) }";
			
			stat = conn.prepareCall(sql);
			
			//String을 int로 형변환
			int sel1 = Integer.parseInt(sel);
			
			stat.setInt(1, sel1);
			stat.setInt(2, memberUser.getNum());
		
			stat.executeUpdate();
			
			stat.close();
			conn.close();
			
			System.out.println("\t\t\t주문하기 성공 했습니다.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\t\t\t주문하기 실패 했습니다.");
		}
		
	}
	
}//class
