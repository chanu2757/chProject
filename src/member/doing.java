package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

public class doing {
	/**
	 * @author 김찬우
	 * pc방에서 할 활동을 정하는 메뉴입니다.
	 * @param memberUser 
	 */
	public void doingmenu(MemberUser memberUser) {
		while(true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t	하는중");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t1. 게임하기");
			System.out.println("\t\t\t2. 영화보기");
			System.out.println("\t\t\t3. 드라마보기");
			System.out.println("\t\t\t4. 음악듣기");
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t▷입력: ");
			Scanner sc = new Scanner(System.in);
			String cho = sc.nextLine();
			
			if(cho.equals("1")) {
				//게임하기 
				procPrintDoing(memberUser,cho);
			}
			else if(cho.equals("2")) {
				//영화보기
				procPrintDoing(memberUser,cho);
			}
			else if(cho.equals("3")) {
				//드라마보기
				procPrintDoing(memberUser,cho);
			}
			else if(cho.equals("4")) {
				//음악듣기
				procPrintDoing(memberUser,cho);
			}
			else if(cho.equals("0")) {
				// 이전으로 
				break;
			}
			}
		}
	/**
	 * @author 김찬우
	 *  장르번호를 입력받아 그 장르에 맞는 프로그램이 있으면 실행시키고 없으면 다운로드로 이동하는 메소드
	 * @param 장르번호(= 메뉴번호)
	 */
public void procPrintDoing(MemberUser user,String cho) {
		while(true) {
		Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procPrintDoing(?,?) }";
			stat = conn.prepareCall(sql);
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			if(cho.equals("1")){
			System.out.print("\t\t\t실행할  게임:");
			}
			else if(cho.equals("2")){
				System.out.print("\t\t\t실행할  영화:");
			}
			else if(cho.equals("3")){
				System.out.print("\t\t\t실행할  드라마:");
			}
			else if(cho.equals("4")){
				System.out.print("\t\t\t실행할  음악:");
				}
			String Do = scan.nextLine(); 
			Do = Do.replace(" ", "");
	
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			stat.setString(1,Do);
			stat.registerOutParameter(2, OracleTypes.CURSOR);
			
			stat.executeQuery();
			rs = (ResultSet)stat.getObject(2);
				if(rs.next()) {
				if(cho.equals("1")) {
				System.out.printf("\t\t\t%s을(를) 실행합니다.\n",Do);
				}
				else {
					System.out.printf("\t\t\t%s을(를) 재생합니다.\n",Do);
				}
				//pc테이블의 하는중에 업데이트 메소드
				procUpdatepcDoing(user,Do);
				
				break;
			}
			// 회원이 입력한 활동이 없을 경우 다운받을지 여부를 확인하고 다운을받거나 메뉴로 돌아감
			else if(!rs.next()) {
				System.out.printf("\t\t\t%s이 없습니다.\n",Do);
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.println("\t\t\t설치하시겠습니까?");
				System.out.println("\t\t\t1.설치하기");
				System.out.println("\t\t\t0.뒤로가기");
				System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
				System.out.print("\t\t\t▷입력:");
				Scanner sc = new Scanner(System.in);
				String cho2 = sc.nextLine();
				if(cho2.equals("1")) {
					//설치프로시저
					procAddDoing(Do,cho);
				}
				else if(cho2.equals("0")) {
						break;		
				}
				else {
					System.out.println("\t\t\t올바르지 않은 메뉴 번호입니다.");
				}
			}
			
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		}
	}
/**
 * @author 김찬우
 * 회원이 실행시킨 활동명과 그 활동의 카테고리번호를 입력받아 DB에 저장하는 메소드
 * @param activity 활동명
 * @param category 카테고리번호
 */
private void procAddDoing(String activity,String category) {
	Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();
		ResultSet rs = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procAddDoing(?,?,?) }";
			
		
			stat = conn.prepareCall(sql);
			
			stat.setString(1,activity);
			stat.setInt(2,Integer.parseInt(category));
			stat.registerOutParameter(3, OracleTypes.NUMBER);
			
			stat.executeQuery();
			int result = stat.getInt(3);
			if(result==1) {
				System.out.printf("\t\t\t%s을(를) 다운로드 완료했습니다.\n",activity);
			}
			else {
				System.out.printf("\t\t\t%s을(를) 다운로드 하지 못했습니다.\n",activity);
			}
			conn.close();
			stat.close();
		} catch (Exception e) {
			System.out.println("Ex07_CallableStatment.m5()");
			e.printStackTrace();
		}
		
	}
/**
 * @author 김찬우
 * 입력한 활동을 pc테이블에 업로드해 현재 활동을 기록하는 메소드
 * @param activity 활동명
 */
public void procUpdatepcDoing(MemberUser user,String activity) {
	Connection conn = null;
		CallableStatement stat = null;
		DBUtil util = new DBUtil();

		
		try {
			conn = util.open("localhost","chproject","java1234");
			String sql = "{ call procUpdatepcDoing(?,?,?) }";
			stat = conn.prepareCall(sql);
			
			stat.setInt(1, user.getNum()); //회원번호
			stat.setString(2, activity);
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

	

