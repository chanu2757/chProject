package admin;

import java.sql.CallableStatement; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class Admin {
/*
 * 
 * 피시방 자리 확인 !
 * 
 */
	

public void adminMenu() {
	while(true) {
	currentPC();
	System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	System.out.println("\t\t\t 관리자 메뉴");
	System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	System.out.println("\t\t\t1. PC관리");
	System.out.println("\t\t\t2. 주문관리");
	System.out.println("\t\t\t3. 매출관리");
	System.out.println("\t\t\t4. 문의내역");
	System.out.println("\t\t\t0. 뒤로가기");
	System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
	System.out.print("\t\t\t▷입력:");
	Scanner sc = new Scanner(System.in);
	String cho = sc.nextLine();
	
	if(cho.equals("1")) {
		// pc관리메뉴
		pcManagement PC = new pcManagement();
		PC.pcManagement();
	}
	else if(cho.equals("2")) {
		// 주문 관리메뉴
		orderManagement order = new orderManagement();
	}
	else if(cho.equals("3")) {
		// 매출 관리메뉴
		salesManagement salesManagement = new salesManagement();
		salesManagement.salesManagement();
	}
	else if(cho.equals("4")) {
		// 문의 내역 메뉴
		enquirylist enquiry = new enquirylist();
		enquiry.printlist();
		
	}
	else if(cho.equals("0")) {
		// 이전으로 
		break;
	}
	}
}

public void currentPC() {
	Connection conn = null;
	CallableStatement stat = null;
	DBUtil util = new DBUtil();
	ResultSet rs = null;
	Scanner scan = new Scanner(System.in);
	
	try {
		
		conn = util.open("localhost","chproject","java1234");
		String sql = "{ call procPC(?)}";
		
		stat = conn.prepareCall(sql);
		
		stat.registerOutParameter(1, OracleTypes.CURSOR);
		
		String[][] arr = new String[7][10];
		HashMap<Integer,String> hm = new HashMap<Integer, String>();
		ArrayList<String> pcnum = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		ArrayList<String> starttime = new ArrayList<String>();
		ArrayList<String> remainingtime = new ArrayList<String>();
		ArrayList<String> doing = new ArrayList<String>();
		
		stat.executeQuery();
		rs = (ResultSet)stat.getObject(1);
		int n=1;
		while(rs.next()) {
			String con = String.format("%s번PC,%s,%s,%s시간 %s분남음,%s하는중",rs.getString("num"),
																rs.getString("id"),
																rs.getString("starttime"),
																Integer.parseInt(rs.getString("remainingtime"))/60,
																Integer.parseInt(rs.getString("remainingtime"))%60,
																rs.getString("doing"));
			
//			pcnum.set(n,rs.getString("num"));
//			id.set(n, rs.getString("id"));
//			starttime.set(n, rs.getString("starttime"));
//			remainingtime.set(n, rs.getString("remainingtime"));
//			doing.set(n, rs.getString("doing"));
//			
//			String con = String.format("%5s번PC\n%10s\n%15s\n%15s\n%15s",pcnum.get(n),id.get(n),starttime.get(n),remainingtime.get(n),doing.get(n));
			n++;
			hm.put(n,con);
		}
		
		int a=2, b=3,c=5,d=8, num=1;
		for(int i=0; i < arr.length;i++) {
			for(int j=0; j < arr[0].length;j++) {
				arr[i][j]="";
				
			if(i ==0) {
				arr[i][2]=hm.get(1);
				arr[i][3]=hm.get(2);
				arr[i][5]=hm.get(3);
				arr[i][8]=hm.get(4);
			}
			if(i ==1) {
				arr[i][1]=hm.get(5);
				arr[i][2]=hm.get(6);
				arr[i][5]=hm.get(7);
				arr[i][8]=hm.get(8);
			}
			if(i ==2) {
				arr[i][0]=hm.get(9);
				arr[i][1]=hm.get(10);
				arr[i][5]=hm.get(11);
				arr[i][8]=hm.get(12);
			}
			if(i ==3) {
				arr[i][0]=hm.get(13);
				arr[i][1]=hm.get(14);
				arr[i][5]=hm.get(15);
				arr[i][6]=hm.get(16);
				arr[i][7]=hm.get(17);
				arr[i][8]=hm.get(18);
			}
			if(i ==4) {
				arr[i][0]=hm.get(19);
				arr[i][1]=hm.get(20);
				arr[i][5]=hm.get(21);
				arr[i][8]=hm.get(22);
			}
			if(i ==5) {
				arr[i][1]=hm.get(23);
				arr[i][2]=hm.get(24);
				arr[i][5]=hm.get(25);
				arr[i][8]=hm.get(26);
			}
			if(i ==6) {
				arr[i][2]=hm.get(27);
				arr[i][3]=hm.get(28);
				arr[i][5]=hm.get(29);
				arr[i][8]=hm.get(30);
			}
			
				
				System.out.printf("%s\t\t",arr[i][j]);
			}
		System.out.println();
		System.out.println();
		System.out.println();
		}
		

		
		conn.close();
		stat.close();
	} catch (Exception e) {
		System.out.println("Ex07_CallableStatment.m5()");
		e.printStackTrace();
	}
	}
}