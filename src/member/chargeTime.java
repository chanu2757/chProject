package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class chargeTime {
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
		
	}	

