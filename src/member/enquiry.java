package member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;


import oracle.jdbc.OracleTypes;

public class enquiry {

   public void enquiryMenu(MemberUser memberUser) {
      
      
      Scanner scan = new Scanner(System.in);
      
      while (true) {
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         
         // 현재 로그인 정보
         
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         System.out.println("\t\t\t\t    문의하기");
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         System.out.println("\t\t\t1. 문의하기");
         System.out.println("\t\t\t2. 답변보기");
         System.out.println("\t\t\t0. 뒤로가기");
      
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         System.out.print("\t\t\t▷ 입력: ");

         String sel = scan.nextLine();
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");

         // 문의하기
         if (sel.equals("1")) {
            procAddMemberEnquiry(memberUser);
         } 
         // 답변보기
         else if (sel.equals("2")) {
            procPrintMemberReply(memberUser);
         } 
         
         // 뒤로가기
         else if (sel.equals("0")) {
            
            break;
            
         } 
         // 예외
         else {
            System.out.println("\t\t\t번호를 다시 입력해주세요.");
         }
      }
      
      
      
   }//menu

   /**
    * 회원이 문의한 답변보기
    * @param memberUser
    */
   public void procPrintMemberReply(MemberUser memberUser) {
      
      Connection conn = null;
      CallableStatement stat = null;
      DBUtil util = new DBUtil();
      ResultSet rs = null;
      Scanner scan = new Scanner(System.in);
      
      try {
         
         conn = util.open("localhost", "chproject", "java1234");
         
         String sql = "{ call procPrintMemberReply(?,?) }";
         
         stat = conn.prepareCall(sql);
               
         stat.setInt(1, memberUser.getNum());
               
         stat.registerOutParameter(2, OracleTypes.CURSOR);
         
         stat.executeQuery();
         
         rs = (ResultSet)stat.getObject(2); 
         
         
         System.out.println("\t\t\t[문의내용]\t\t\t[->]\t\t\t[답변내용]");
            
         while(rs.next()) {
            
            System.out.print("\t\t\t");            
            System.out.printf("%s", rs.getString("enquiry"));   
            System.out.print("\t");            
            System.out.print("->");
            System.out.print("\t\t\t");
            System.out.printf("%s", rs.getString("reply"));   
         
            System.out.println();
            
               
         }
   
         
         

         rs.close();
         stat.close();
         conn.close();
            
         System.out.println();
         System.out.println("\t\t\t문의한 답변 출력에 성공했습니다.");
         System.out.println();
         
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("\t\t\t문의한 답변 출력에 실패했습니다.");
      }
      
      
   }//procPrintMemberReply
   
   
   /**
    * 회원이 문의하는 메소드
    * @param memberUser
    */
   public void procAddMemberEnquiry(MemberUser memberUser) {
      
      Connection conn = null;
      CallableStatement stat = null;
      DBUtil util = new DBUtil();
      Scanner scan = new Scanner(System.in);
            
      try {
         
         conn = util.open("localhost", "chproject", "java1234");
         
         String sql = "{ call procAddMemberEnquiry(?,?) }";
         
         stat = conn.prepareCall(sql);
         
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         System.out.println("\t\t\t\t    문의 하기");
         System.out.println("\t\t\t〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
         
         
         stat.setInt(1, memberUser.getNum());
                  
         System.out.print("\t\t\t내용 입력: ");
         String content = scan.nextLine();
         stat.setString(2, content);
      
         
         stat.executeUpdate();
         
         stat.close();
         conn.close();
         
         System.out.println("\t\t\t문의하기 성공 했습니다.");
         
         
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("\t\t\t문의하기 실패 했습니다.");
      }
      
      
   }//procAddMemberEnquiry

}//class