package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;



// 회원 로그인
public class MemberUser {

	int num; 		//회원 번호
	String id; 		//회원 아이디
	String pw; 		//회원 비밀번호
	String name; 	//회원 이름
	int remainTime;	//회원 남은시간
	boolean loginFlag = false;
	
	public void login(MemberUser memberUser) {
		
		// Database connection	
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		DBUtil util = new DBUtil();
		
		Scanner scan = new Scanner(System.in);
		
		// 로그인한 회원 정보를 넣어줄 변수
		HashMap<String, ArrayList<String>> memberInfo = new HashMap<String, ArrayList<String>>();
		
		try {
			
			conn = util.open("localhost", "chproject", "java1234");
			stat = conn.createStatement();
			
			String sql = String.format("select * from tblMember");
			rs = stat.executeQuery(sql);
			
			// Insert info to loginInfo map
			// 데이터 입력
			while (rs.next()){
				
				ArrayList<String> temp = new ArrayList<String>();
				
				temp.add(rs.getString("pw"));
				temp.add(rs.getString("num"));
//				temp.add(rs.getString("name"));
//				temp.add(rs.getString("remainTime"));
				memberInfo.put(rs.getString("id"), temp);
								
			}//while
			
			// input id,pw
			// 사용자에게 id,pw 입력받기
			System.out.print("\t\t\t▶ ID:");
			String inputId = scan.nextLine();
			System.out.print("\t\t\t▶ PW:");
			String inputPw = scan.nextLine();
			
			// loginInfo search
			for (String id: memberInfo.keySet()) {				
				// id matching
				
				if (id.equals(inputId)) {
					
					// password get
					String pw = memberInfo.get(id).get(0);	
					
					if (pw.equals(inputPw)) {
						
						// login on
						memberUser.loginFlag = true;
						
						// set info						
						memberUser.setNum(Integer.parseInt(memberInfo.get(id).get(1)));
						
						// 회원의 남은 시각 확인
						// if 남은 시간 > 0 이면, 자리선택 메뉴로						
						if( procprintRemainTime(memberUser) > 0 ) {
						
							//자리 선택 하는 메소드
							MemberSitPc(memberUser);
							
						} else {

							// else 남은시간이 0 이면, 남은 시간 메뉴로
							noRemainTiem(memberUser);
							
							
						}
						
					
						
					}
					
				}
				
				
			}
			
			// when enter wrong info
			if(!memberUser.loginFlag) {
				System.out.println("\t\t\t아이디와 비밀번호를 다시 입력해주세요.");
			}
			// logout
			else {
				System.out.println("\t\t\t로그아웃을 진행합니다.");
				scan.nextLine();
				memberUser.loginFlag = false;
			}
			
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}//Member login
	
	/**
	 * 회원 자리 선택메뉴 메소드
	 */
	public void MemberSitPc(MemberUser memberUser) {
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    회원 자리 선택");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t0. 뒤로가기");			

			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 자리 선택: ");

			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			if (sel.equals("0")) {
				
				break;
								
			} else {
				
				//자리 선택
				
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				ResultSet rs = null;				
				
				try {
					
					//프로시저 호출 준비
					String sql = "{ call procUpdateMemberSitPc(?,?) }";
					conn = util.open("localhost", "chproject", "java1234");
					stat = conn.prepareCall(sql);
					
					//String을 int로 변환
					int intSel = Integer.parseInt(sel);
				
					//프로시저 매개변수 설정
					stat.setInt(1, memberUser.getNum());
					stat.setInt(2, intSel);			
					
					//프로시저 실행
					stat.executeUpdate();	
				
					System.out.println();
					System.out.println("\t\t\t자리선택을 완료했습니다.");
					System.out.println("\t\t\t엔터를 입력하시면 회원 메뉴로 이동합니다.");
					scan.nextLine();
					
					MemberMain MemberMainmenu = new MemberMain();			
					MemberMainmenu.MemberMainmenu(memberUser);
				
					rs.close();
					stat.close();
					conn.close();
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("자리선택에 실패했습니다");
				}
				
			} //else
			
		}//while
		
	}//procUpdateMemberSitPc
	


	/**
	 * 회원 남은 시간이 0일때 메소드
	 */
	public void noRemainTiem(MemberUser memberUser) {
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t\t    남은 시간이 없습니다");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t1. 충전하기");
			System.out.println("\t\t\t0. 뒤로가기");			

			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 입력: ");

			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			if (sel.equals("1")) {
				
				chargeTime chargeMenu = new chargeTime();
				chargeMenu.chargeMenu(memberUser);				
				
			} else if (sel.equals("0")) {

				break;
			} 
			
		}
		
	}// noRemainTiem()


	/**
	 * 회원의 남은 시간 반환 하는 메소드
	 * @param memberUser 
	 * @return 
	 */
	public int procprintRemainTime(MemberUser memberUser) {
		
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		
		try {
			
			//프로시저 호출 준비
			String sql = "{ call procprintRemainTime(?,?) }";
			conn = util.open("localhost", "chproject", "java1234");
			stat = conn.prepareCall(sql);
			
			//프로시저 매개변수 설정
			stat.setInt(1, memberUser.getNum());
			stat.registerOutParameter(2, OracleTypes.CURSOR); //성공여부 받아올 매개변수 설정
			
			//프로시저 실행
			stat.executeQuery();
			
			rs = (ResultSet) stat.getObject(2);
			
			//남은 시간 
			int remainTime = 0;

			while (rs.next()) {
				
			// 회원 남은 시간
			remainTime = rs.getInt("remainTime"); 
			}

			rs.close();
			stat.close();
			conn.close();

			// 회원 남은 시간 반환
			return remainTime; 

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		}
		

		return -99999;		
		
	}//procprintRemainTime
	

	
	/**
	 * getter setter
	 */
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

//	public String getId() {
//		return id;
//	}
//	
//	public void setId(String id) {
//		this.id = id;
//	}
//	
//	public String getPw() {
//		return pw;
//	}
//
//	public void setPw(String pw) {
//		this.pw = pw;
//	}

	
	
	
	
	
}//MemberUser
