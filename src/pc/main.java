package pc;
import java.util.Scanner;

import admin.Adminlog;
import member.MemberUser;
import nonMomber.nonMemberUser;

public class main {
public static void main(String[] args) {

	Scanner scan = new Scanner(System.in);
	while (true) {
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.println("\t\t\t\t   	 CH 피시방");
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

		System.out.println("\t\t\t1. 회원 로그인");
		System.out.println("\t\t\t2. 비회원 로그인");
		System.out.println("\t\t\t3. 관리자 로그인");
		System.out.println("\t\t\t4. 회원가입");
		System.out.println("\t\t\t0. 종료");

		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
		System.out.print("\t\t\t▷ 입력: ");

		String sel = scan.nextLine();
		System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

		// 회원 로그인
		if (sel.equals("1")) {
			MemberUser memberUser = new MemberUser();
			memberUser.login(memberUser);
		} 
		// 비회원 로그인
		else if (sel.equals("2")) {
			nonMemberUser nonMem = new nonMemberUser();
			nonMem.nonMemberSitPc();
		} 
		// 관리자 로그인
		else if (sel.equals("3")) {
			//관리자 로그인
		Adminlog log = new Adminlog();
		log.login();
		} 
		// 회원 가입
		else if (sel.equals("4")) {
			//회원 가입
			user add = new user();
			add.signup();			
		} 
		// 종료
		else if (sel.equals("0")) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t프로그램을 종료합니다."); // needMore
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			break;
		} 
		// 예외
		else {
			System.out.println("\t\t\t번호를 다시 입력해주세요.");
		}
	}

}
}

