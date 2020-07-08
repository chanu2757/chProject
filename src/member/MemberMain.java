package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class MemberMain {
	/**
	 * 회원이 사용하는 메인메뉴입니다.
	 * @author 김찬우
	 * @param 회원정보 객체
	 */
	public void MemberMainmenu(MemberUser memberUser) {
		
		Scanner scan = new Scanner(System.in);
		
		loginfo log1 = new loginfo();
		/*회원의 아이디,시작시간,남은시간을 불러오는 프로시저
		+ 아이디,시작시간 ,남은 시간을 변수에 저장 */
		
		while (true) {
			procprintloginfo(memberUser.getNum());
			
			System.out.println("\t\t\t\t    회원 메인 메뉴");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t1. 하는중");
			System.out.println("\t\t\t2. 음식 주문");
			System.out.println("\t\t\t3. 문의하기");
			System.out.println("\t\t\t4. 자리 이동");
			System.out.println("\t\t\t5. 충전하기");
			System.out.println("\t\t\t6. 사용종료");
			System.out.println("\t\t\t0. 로그아웃");

			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷ 입력: ");

			String sel = scan.nextLine();
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

			// 하는중
			if (sel.equals("1")) {
				doing do12 = new doing();
				do12.doingmenu(memberUser);		
			} 
			// 음식 주문
			else if (sel.equals("2")) {
				foodOrder order = new foodOrder();
				order.order(memberUser);
			} 
			// 문의 하기
			else if (sel.equals("3")) {
				
				enquiry enquiryMenu = new enquiry();
				enquiryMenu.enquiryMenu(memberUser);
				
			} 
			// 자리 이동
			else if (sel.equals("4")) {
				procremainingTime(memberUser.getNum());
				endPc by2 = new endPc();
				by2.procendpc(memberUser);
				movePc mpc = new movePc();	
				mpc.move(memberUser);
			} 
			// 충전 하기
			else if (sel.equals("5")) {
				chargeTime(memberUser);		
			} 
			// 사용 종료
			else if (sel.equals("6")) {
				procremainingTime(memberUser.getNum());
				endPc bye = new endPc();
				bye.Bye(memberUser);
				
			} 
			// 뒤로가기
			else if (sel.equals("0")) {
				procremainingTime(memberUser.getNum());
				pc.main main = new pc.main();
				main.main(null);
			} 
			// 예외
			else {
				for (int i=0;i<sel.length();i++) {
					int b =0;
					b=sel.charAt(i);
					if(b<48 && b>57) {
						System.out.println("\t\t\t숫자만 입력가능 합니다.");
					}
				}
		
			}	
		
			}
	
	}//MemberMainmenu
	/**
	 * 로그인한 회원의 정보를 출력하는 메소드
	 * @param 회원 번호
	 */
	public void procprintloginfo(int membernum) {
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		loginfo log1 = new loginfo();
		MemberUser user =new MemberUser();
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procPrintlogmemberinfo(?,?) }";
			stat = conn.prepareCall(sql);
	
			
			//회원 번호 로그인시 받아와야댐
			stat.setInt(1, membernum);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			
			stat.executeQuery();
			rs = (ResultSet)stat.getObject(2);
			
			while(rs.next()) {
				log1.setPcnum(rs.getString("num"));
				log1.setId(rs.getString("id"));
				log1.setRemaintime(rs.getInt("remaintime"));
				log1.setStartime(rs.getString("starttime"));
			}
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.printf("\t\t\t%s번PC(%s)\n",log1.getPcnum(),log1.getId());
			System.out.printf("\t\t\t    				시작시간:%s시%s분\n",log1.getStartime().substring(0,2),log1.getStartime().substring(3,5));
			System.out.printf("\t\t\t	  			남은시간:%d시간%d분\n",log1.getRemaintime()/60,log1.getRemaintime()%60);
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			
			if(log1.getRemaintime()/60<=0 && log1.getRemaintime()%60<=0) {
				System.out.println("\t\t\t이용시간을 다 사용해 종료됩니다.");
				endPc bye = new endPc();
				bye.procendpc(user);
				pc.main m = new pc.main();
				m.main(null);
			}
			conn.close();
			stat.close();
			
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
				
		}
			
		}//procprintloginfo
	/**
	 * 사용자가 pc를 종료했을 경우 pc시간이 회원정보에 저장되는 메소드
	 * @param membernum
	 */
	public void procremainingTime(int membernum) {
		//종료시 남은시간을 저장하는 메소드
			Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);

			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procremainingTime(?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.setInt(1, membernum);
				stat.registerOutParameter(2, OracleTypes.NUMBER);
				
				stat.executeUpdate();
				int result = stat.getInt(2);
				if(result == 1) {
					System.out.println("\t\t\t시간이 저장되었습니다.");
				}
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
	}
	/**
	 * 시간을 충전하는 메소드입니다.
	 * @param 회원 정보 객체
	 */
		public void chargeTime(MemberUser memberuser){
				
				Connection conn = null;
				CallableStatement stat = null;
				DBUtil util = new DBUtil();
				Scanner sc = new Scanner(System.in);
				
				try {
					conn = util.open("localhost","chproject","java1234");
					String sql = "{ call procchargetime(?,?,?) }";
					stat = conn.prepareCall(sql);
				
					
					stat.setInt(1,memberuser.getNum());
					System.out.print("\t\t\t충전할 금액:");
					String money = sc.nextLine();
					stat.setString(2,money);
					
					if(Integer.parseInt(money) < 1000) {
						System.out.println("\t\t\t1000원 이상부터 충전 가능합니다.");
					}
					stat.registerOutParameter(3, OracleTypes.NUMBER);
					
					stat.executeUpdate();
					int result = stat.getInt(3) ; 
					
				if(result == 1 && Integer.parseInt(money)>0) {
					System.out.println("\t\t\t충전이 완료 되었습니다.");
					MemberMain MemberMainmenu = new MemberMain();			
					MemberMainmenu.MemberMainmenu(memberuser);
				}
					conn.close();
					stat.close();
				
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("\t\t\t충전 실패!");
				}
		}	
	
	}//class
	/**
	 * 회원 정보를 저장하는 클래스입니다.
	 * @author 김찬우
	 *
	 */
	class loginfo{
		String id;
		int remaintime;
		String startime;
		String pcnum;
		public String getPcnum() {
			return pcnum;
		}
		public void setPcnum(String pcnum) {
			this.pcnum = pcnum;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public int getRemaintime() {
			return remaintime;
		}
		public void setRemaintime(int remaintime) {
			this.remaintime = remaintime;
		}
		public String getStartime() {
			return startime;
		}
		public void setStartime(String startime) {
			this.startime = startime;
		}
	}
