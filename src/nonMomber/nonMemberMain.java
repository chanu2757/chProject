package nonMomber;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import member.doing;
import member.enquiry;
import member.foodOrder;
import member.movePc;
import oracle.jdbc.OracleTypes;

public class nonMemberMain {
	//멤버 로그인 후 여기로
	public void main(String[] args) {
		nonMembermain();
	}
	public void nonMembermain() {
		loginfo log1 = new loginfo();
		/*회원의 아이디,시작시간,남은시간을 불러오는 프로시저
		+ 아이디,시작시간 ,남은 시간을 변수에 저장
		*/
		while(true) {
		
			procprintloginfo(); // pc번호, 회원 id, 시작시간, 남은시간 출력 
			System.out.println("\t\t\t1. 하기");
			System.out.println("\t\t\t2. 음식주문");
			System.out.println("\t\t\t3. 문의");
			System.out.println("\t\t\t4. 자리이동");
			System.out.println("\t\t\t5. 충전하기");
			System.out.println("\t\t\t6. 사용종료");
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t번호 선택:");
			Scanner sc = new Scanner(System.in);
			String cho = sc.nextLine();
			
			if(cho.equals("1")) {
				doing do12 = new doing();
				do12.doingmenu();			}
			else if(cho.equals("2")) {
				foodOrder order = new foodOrder();
			}
			else if(cho.equals("3")) {
				enquiry inquiry = new enquiry();
			}
			else if(cho.equals("4")) {
				movePc mpc = new movePc();
			}
			else if(cho.equals("5")) {
				chargeTime cTime = new chargeTime();
			}
			else if(cho.equals("6")) {
				pc.main main = new pc.main();
				main.main(null);
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
	
	public void procprintloginfo() {
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);
			loginfo log1 = new loginfo();
			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procPrintloginfo(?,?) }";
				stat = conn.prepareCall(sql);
				int remaintime =0;
				
				String num ="1"; //회원 번호 로그인시 받아와야댐
				
				stat.setString(1, num);
				stat.registerOutParameter(2, OracleTypes.CURSOR);
				
				stat.executeQuery();
				rs = (ResultSet)stat.getObject(2);
				while(rs.next()) {
					log1.setPcnum(rs.getString(1));
					log1.setId(rs.getString(2));
					log1.setRemaintime(rs.getInt(3));
					log1.setStartime(rs.getString(4));
				}
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.printf("\t\t\t%s번PC(비회원)\n",log1.getPcnum());
				System.out.printf("\t\t\t    				시작시간:%s시%s분\n",log1.getStartime().substring(0,2),log1.getStartime().substring(3,5));
				System.out.printf("\t\t\t	  			남은시간:%d시간%d분\n",log1.getRemaintime()/60,log1.getRemaintime());
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				
				conn.close();
				stat.close();
				
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
					
			}
			
		}
	
	public void procremainingTime(int remainingTime) {
		//종료시 남은시간을 저장하는 메소드
		Connection conn = null;
			CallableStatement stat = null;
			DBUtil util = new DBUtil();
			ResultSet rs = null;
			Scanner scan = new Scanner(System.in);

			try {
				conn = util.open("localhost","chproject","java1234");
				String sql = "{ call procremainingTime(?,?,?) }";
				stat = conn.prepareCall(sql);
				
				stat.setString(1, "1");
				stat.setInt(2, remainingTime);
				stat.registerOutParameter(3, OracleTypes.NUMBER);
				
				stat.executeUpdate();
				int result = stat.getInt(3);
				
				conn.close();
				stat.close();
			} catch (Exception e) {
				System.out.println("Ex07_CallableStatment.m5()");
				e.printStackTrace();
			}
			
		}
	}

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