package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class chargeTime {
	/**
	 * 회원의 사용시간을 충전하는 메뉴입니다.
	 * @param 회원정보
	 */
	public void chargeMenu(MemberUser memberuser) {
		while(true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t\t      충전하기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t 저희 PC방은 1000원 당 1시간 입니다.");
			System.out.println("\t\t\t0.뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");	
			System.out.print("\t\t\t▷금액 입력:");
			Scanner sc = new Scanner(System.in);
			String cho = sc.nextLine();
			for (int i=0;i<cho.length();i++) {
				int b =0;
				b=cho.charAt(i);
				if(b<48 || b>57) {
					System.out.println("\t\t\t숫자만 입력가능 합니다.");
					break;
				}
				else {
					chargeTime(memberuser,cho);
					break;
				}
			}//for
			if(cho.equals("0")) {
				// 이전으로 
				break;
			}
	}
}
	/**
	 * 회원의 시간을 입력한 금액만큼 충전하는 메소드입니다.
	 * @param 회원정보
	 * @param 충전할 금액
	 */
	public void chargeTime(MemberUser memberuser,String money){
	
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				
				
				try {
					conn = util.open("localhost","chproject","java1234");
					String sql = "{ call procchargetime(?,?,?) }";
					stat = conn.prepareCall(sql);
					
					stat.setInt(1,memberuser.getNum());
					stat.setString(2,money);
					
					if(Integer.parseInt(money) < 1000) {
						System.out.println("\t\t\t1000원 이상부터 충전 가능합니다.");
					}
					stat.registerOutParameter(3, OracleTypes.NUMBER);
					
					stat.executeUpdate();
					int result = stat.getInt(3) ; 
					
				if(result == 1 && Integer.parseInt(money)>0) {
					procpaymoney(money);
					System.out.println("\t\t\t충전이 완료 되었습니다.");
					MemberUser User = new MemberUser();
					User.MemberSitPc(memberuser);
				}
					conn.close();
					stat.close();
				
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("\t\t\t충전 실패!");
				}
		}	
	
	/**
	 * 회원이 사용한 금액을 매출테이블에 저장하는 메소드입니다.
	 * @param 수요
	 */
	public void procpaymoney(String money) {
			Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procpaymoney(?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.setString(1, money);
				stat.registerOutParameter(2, OracleTypes.NUMBER);
				
				stat.executeUpdate();
				int result = stat.getInt(2);
				
				if(result == 1) {
					System.out.println("\t\t\t결제가 완료되었습니다.");
				}
				
				else {
					System.out.println("\t\t\t결제 실패!");
				}
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}
	
	
	}	

