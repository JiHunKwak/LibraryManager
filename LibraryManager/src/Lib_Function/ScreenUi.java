package Lib_Function;

import java.util.Scanner;

public class ScreenUi {
	
	private static ScreenUi scu = new ScreenUi();
	private ScreenUi(){}
	
	public static ScreenUi getInstance(){
		return scu;
	}

	public void initialMenu(){
		System.out.println("******Library Manager******");
		System.out.println();
		System.out.println("1.Sign in  2.Sign up  3.Exit");
		System.out.println();
		System.out.print("Select Number: ");
	}
	
	public void primaryMenu(){
		System.out.println();
		System.out.println("1. 도서 검색");
		System.out.println("2. 도서 대출");
		System.out.println("3. 도서 반납");
		System.out.println("4. 회원 검색");
		System.out.println("5. 회원정보 수정");
		System.out.println("6. 회원탈퇴");
		System.out.println("7. Log out");
		System.out.println("8. Exit");
		System.out.println();
		System.out.print("Select Num: ");
	}
	
	public void searchBookMenu(){
		System.out.println("검색 조건을 선택하십시오.");
		System.out.println();
		System.out.println("1. 도서명 검색");
		System.out.println("2. 저자명 검색");
		System.out.println("3. 출판사 검색");
		System.out.println("4. 전체목록 보기");
		System.out.println("5. Go back");
		System.out.println();
		System.out.print("Select Number: ");
	}
	
	public void searchAccountMenu(){
		System.out.println("검색 조건을 선택하십시오.");
		System.out.println();
		System.out.println("1. 이름 검색");
		System.out.println("2. ID 검색");
		System.out.println("3. E-mail 검색");
		System.out.println("4. Go back");
		System.out.println();
		System.out.print("Select Number: ");
	}
	
	public void delay(){
		System.out.println("     3초후 이전화면으로 돌아갑니다.");
		try{Thread.sleep(3000);}catch(Exception e){}
		this.clearScreen();
	}
	
	public void askContinue(){
		System.out.println("     계속하려면 아무키나 입력하십시오.");
		new Scanner(System.in).nextLine();
		this.clearScreen();
	}
	
	public void exitProgram(){
		this.clearScreen();
		DbManager.getInstance().saveData();
		System.out.println("프로그램이 정상적으로 종료되었습니다.");
	}
	
	public void clearScreen(){
		for(int i=0; i<80; i++)
			System.out.println();
	}
	
}
