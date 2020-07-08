package admin;

import java.util.Scanner;

public class orderManagement {
	public void pcManagement() {
		while(true) {
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.println("\t\t\t 주문 관리");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			//번호.자리번호(아이디)/주문내용) 출력 프로시저
			System.out.println("\t\t\t0. 뒤로가기");
			System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
			System.out.print("\t\t\t번호 선택:");
			Scanner sc = new Scanner(System.in);
			String cho = sc.nextLine();
			
			
			if(cho.equals("0")) {
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
}
