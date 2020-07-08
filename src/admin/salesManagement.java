package admin;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class salesManagement {
	public void salesManagement() {
	while(true) {
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t\t 매출관리");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t1. 년 매출 조회");
		System.out.println("\t\t\t2. 월 매출 조회");
		System.out.println("\t\t\t3. 일 매출 조회");
		System.out.println("\t\t\t0. 뒤로가기");
		Scanner sc = new Scanner(System.in);
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t▷ 입력: ");

		String cho = sc.nextLine();
		
		if(cho.equals("1")) {
			//년 매출 조회
			proPrintSalesYear();
		}
		else if(cho.equals("2")) {
			// 월 매출 조회
			proPrintSalesMonth();
		}
		else if(cho.equals("3")) {
			// 일 매출 조회
			proPrintSalesToday();
		}
		else if(cho.equals("0")) {
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
		}
	}
}
	
	/**
	 * 일 매출 조회
	 */
	private void proPrintSalesToday() {
		
		Scanner scan = new Scanner(System.in);
		while(true) {	
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    일 매출 조회");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 년도 입력: ");
			String year = scan.nextLine();
			System.out.print("\t\t\t▷ 월 입력: ");
			String month = scan.nextLine();
			System.out.print("\t\t\t▷ 일 입력: ");
			String day = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			
			if (year.equals("0")) {
				break;	
			} 
			// 년도 입력
			else  {
				
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				ResultSet rs = null;
				
				try {
					
					conn = util.open("localhost", "chproject", "java1234");
					
					String sql = "{ call proPrintSalesToday(?,?,?,?) }";
					
					stat = conn.prepareCall(sql);
					
					//입력 받은 year를 int로 형변환
					int iyear = Integer.parseInt(year);
					//입력 받은 month를 int로 형변환
					int imonth = Integer.parseInt(month);
					//입력 받은 day를 int로 형변환
					int iday = Integer.parseInt(day);
					
					stat.setInt(1, iyear);
					stat.setInt(2, imonth);
					stat.setInt(3, iday);
							
					stat.registerOutParameter(4, OracleTypes.CURSOR);
					
					stat.executeQuery();
					
					rs = (ResultSet)stat.getObject(4); 
					
					
					System.out.println("\t\t\t[----년도 -월 -일]\t\t[->]\t\t[매출]");
						
					while(rs.next()) {
						
						System.out.print("\t\t\t");				
						System.out.printf("%d년도 %d월 %d일", iyear, imonth, iday);	
						System.out.print("\t\t");				
						System.out.print("->");
						System.out.print("\t\t");
						System.out.printf("%,d원", rs.getInt("sum(sales)"));	
					
						System.out.println();
							
					}

					rs.close();
					stat.close();
					conn.close();
						
					System.out.println();
					System.out.println("\t\t\t일 매출 출력에 성공했습니다.");
					System.out.println("\t\t\t계속 하시려면 엔터를 입력해주세요 .");
					scan.nextLine();
					
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("\t\t\t일 매출 출력에 실패했습니다.");
					}
					
			} 
		}
		
	}

	/**
	 * 월 매출 조회하는 메소드
	 */
	private void proPrintSalesMonth() {
		
		Scanner scan = new Scanner(System.in);
		while(true) {	
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    월 매출 조회");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 년도 입력: ");
			String year = scan.nextLine();
			System.out.print("\t\t\t▷ 월 입력: ");
			String month = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			
			if (year.equals("0")) {
				break;	
			} 
			// 년도 입력
			else  {
				
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				ResultSet rs = null;
				
				try {
					
					conn = util.open("localhost", "chproject", "java1234");
					
					String sql = "{ call proPrintSalesMonth(?,?,?) }";
					
					stat = conn.prepareCall(sql);
					
					//입력 받은 year를 int로 형변환
					int iyear = Integer.parseInt(year);
					//입력 받은 month를 int로 형변환
					int imonth = Integer.parseInt(month);
					
					stat.setInt(1, iyear);
					stat.setInt(2, imonth);
							
					stat.registerOutParameter(3, OracleTypes.CURSOR);
					
					stat.executeQuery();
					
					rs = (ResultSet)stat.getObject(3); 
					
					
					System.out.println("\t\t\t[----년도 -월]\t\t[->]\t\t[매출]");
						
					while(rs.next()) {
						
						System.out.print("\t\t\t");				
						System.out.printf("%d년도 %d월", iyear, imonth);	
						System.out.print("\t\t");				
						System.out.print("->");
						System.out.print("\t\t");
						System.out.printf("%,d원", rs.getInt("sum(sales)"));	
					
						System.out.println();
							
					}

					rs.close();
					stat.close();
					conn.close();
						
					System.out.println();
					System.out.println("\t\t\t월 매출 출력에 성공했습니다.");
					System.out.println("\t\t\t계속 하시려면 엔터를 입력해주세요 .");
					scan.nextLine();
					
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("\t\t\t월 매출 출력에 실패했습니다.");
					}
					
			} 
		}
	}

	/**
	 * 연 매출 조회하는 메소드
	 */
	private void proPrintSalesYear() {
		
		Scanner scan = new Scanner(System.in);
	while(true) {	
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t\t    년 매출 조회");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t0. 뒤로가기");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t▷ 년도 입력: ");
		String year = scan.nextLine();
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		
		if (year.equals("0")) {
			break;	
		} 
		// 년도 입력
		else  {
			
			Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			
			try {
				
				conn = util.open("localhost", "chproject", "java1234");
				
				String sql = "{ call proPrintSalesYear(?,?) }";
				
				stat = conn.prepareCall(sql);
				
				//입력 받은 year를 int로 형변환
				int iyear = Integer.parseInt(year);
				
				stat.setInt(1, iyear);
						
				stat.registerOutParameter(2, OracleTypes.CURSOR);
				
				stat.executeQuery();
				
				rs = (ResultSet)stat.getObject(2); 
				
				
				System.out.println("\t\t\t[----년도]\t\t[->]\t\t[매출]");
					
				while(rs.next()) {
					
					System.out.print("\t\t\t");				
					System.out.printf("%d년도", iyear);	
					System.out.print("\t\t\t");				
					System.out.print("->");
					System.out.print("\t\t");
					System.out.printf("%,d원", rs.getInt("sum(sales)"));	
				
					System.out.println();
						
				}

				rs.close();
				stat.close();
				conn.close();
					
				System.out.println();
				System.out.println("\t\t\t년 매출 출력에 성공했습니다.");
				System.out.println("\t\t\t계속 하시려면 엔터를 입력해주세요 .");
				scan.nextLine();
				
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("\t\t\t년 매출 출력에 실패했습니다.");
				}
				
		} 
		

		
		
	}//while
		
	}//proPrintSalesYear
	
}//class
