package Lib_Function;

import java.util.*;

import Lib_Constants.Constants;
import Lib_Menu.*;

public class AccountManager {
	
	private static AccountManager acm = new AccountManager();
	private AccountManager(){}
	public static AccountManager getInstance(){
		return acm;
	}
	
	private Scanner scan;
	private List<Integer> searchBuffer;
	public static String[] buffer = null;
	
	public void searchAccount(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		int sel = 0;
		
		scu.clearScreen();
		scu.searchAccountMenu();
		sel = exe.inputIntegerCatch(Constants.SEARCH_BOOK_MENU_SIZE);
		switch(sel){
		case 1:
			this.searchRoutine(Constants.CONDITION_NAME);
			break;
		case 2:
			this.searchRoutine(Constants.CONDITION_ID);
			break;
		case 3:
			this.searchRoutine(Constants.CONDITION_EMAIL);
			break;
		case 4:
			scu.clearScreen();
			PrimaryMenu.getInstance().menu();
			break;
		}
		
	}
	
	public void searchRoutine(int condition){
		boolean searchFlag = false;
		String keyword = null;
		searchBuffer = new ArrayList<>();
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();

		System.out.println();
		switch(condition){
		case Constants.CONDITION_NAME:
			System.out.print("Enter keyword: ");
			keyword = exe.inputStringCatch();
			for(int i=0; i<DbManager.accArr.size(); i++)
				if(DbManager.accArr.get(i).getName().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		case Constants.CONDITION_ID:
			System.out.print("Enter keyword: ");
			keyword = exe.inputStringCatch();
			for(int i=0; i<DbManager.accArr.size(); i++)
				if(DbManager.accArr.get(i).getId().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		case Constants.CONDITION_EMAIL:
			System.out.print("Enter keyword: ");
			keyword = exe.inputStringCatch();
			for(int i=0; i<DbManager.accArr.size(); i++)
				if(DbManager.accArr.get(i).getEmail().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		}
		
		if(searchFlag){
			System.out.println("검색결과 입니다.");
			System.out.println("-----------------------------------------------------------------------------------");
			for(int i=0; i<searchBuffer.size(); i++){
				System.out.print((i+1) + ") ");
				System.out.print("name: " + DbManager.accArr.get(searchBuffer.get(i)).getName());
				System.out.print(" | ID: " + DbManager.accArr.get(searchBuffer.get(i)).getId());
				if(i == InitialMenu.userKey) System.out.print("(나)");
				System.out.println();
				System.out.println("   Phone number: " + DbManager.accArr.get(searchBuffer.get(i)).getPhoneNum());
				System.out.println("   E-mail: " + DbManager.accArr.get(searchBuffer.get(i)).getEmail());
				System.out.println("-----------------------------------------------------------------------------------");
			}
		}
		else{
			System.out.println("검색결과가 없습니다.");
		}

		scu.askContinue();
		PrimaryMenu.getInstance().menu();
	}

	public void modifyOwnData(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		PrimaryMenu prm = PrimaryMenu.getInstance();
		scan = new Scanner(System.in);
		
		if(BookManager.getInstance().isRental()){
			System.out.println("반납중인 책을 모두 반납후 시도해 주십시오.");
			scu.delay();
			prm.menu();
		}
		
		System.out.println();
		System.out.print("비밀번호를 입력하십시오: ");
		String pwBuffer = exe.inputStringCatch();
		
		if(pwBuffer.equals(DbManager.accArr.get(InitialMenu.userKey).getPassword())){
			System.out.println("새로운 회원정보를 입력하십시오.");
			System.out.println("   기존의 정보를 사용하시려면 Enter키를 누르십시오.");
			buffer = new String[Constants.ACCOUNT_DATA_LENGTH];
			for(int i=0; i<Constants.SIGNUP_TEXTBOX.length; i++){
				System.out.print(Constants.SIGNUP_TEXTBOX[i]);
				buffer[i] = scan.nextLine();
				if(buffer[i].isEmpty()){
					switch(i){
					case Constants.CONDITION_NAME:
						buffer[i] = DbManager.accArr.get(InitialMenu.userKey).getName();
						break;
					case Constants.CONDITION_ID:
						buffer[i] = DbManager.accArr.get(InitialMenu.userKey).getId();
						break;
					case Constants.CONDITION_PASSWORD:
						buffer[i] = DbManager.accArr.get(InitialMenu.userKey).getPassword();
						break;
					case Constants.CONDITION_PHONENUMBER:
						buffer[i] = DbManager.accArr.get(InitialMenu.userKey).getPhoneNum();
						break;
					case Constants.CONDITION_EMAIL:
						buffer[i] = DbManager.accArr.get(InitialMenu.userKey).getEmail();
						break;
					}
				}
			}
			
			boolean flag = exe.signupException(Constants.MODIFY_MODE);
			if(flag){
				DbManager.accArr.get(InitialMenu.userKey).setName(buffer[0]);
				DbManager.accArr.get(InitialMenu.userKey).setId(buffer[1]);
				DbManager.accArr.get(InitialMenu.userKey).setPassword(buffer[2]);
				DbManager.accArr.get(InitialMenu.userKey).setPhoneNum(buffer[3]);
				DbManager.accArr.get(InitialMenu.userKey).setEmail(buffer[4]);
				System.out.println("회원정보 수정이 완료되었습니다.");
				scu.delay();
				prm.menu();
			}
			else{
				scu.delay();
				System.out.println("회원정보 수정을 실패하였습니다.");
				prm.menu();
			}
		}
		else{
			System.out.println("비밀번호가 일치하지 않습니다.");
			scu.delay();
			prm.menu();
		}
	}
	
	public void deleteAccount(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		PrimaryMenu prm = PrimaryMenu.getInstance();
		
		if(BookManager.getInstance().isRental()){
			System.out.println("반납중인 책을 모두 반납후 시도해 주십시오.");
			scu.delay();
			prm.menu();
		}
		
		System.out.println();
		System.out.print("비밀번호를 입력하십시오: ");
		String pwBuffer = exe.inputStringCatch();
		
		if(pwBuffer.equals(DbManager.accArr.get(InitialMenu.userKey).getPassword())){
			System.out.print("정말로 탈퇴하시겠습니까? Y/N ");
			String sel = exe.inputStringCatch();
			boolean flag = exe.selectException(sel.toUpperCase());
			if(flag){
				if(sel.toUpperCase().charAt(0) == 'Y'){
					DbManager.accArr.remove(InitialMenu.userKey);
					InitialMenu.userKey = -1;
					System.out.println("정상적으로 처리되었습니다.");
					scu.delay();
					new InitialMenu().menu();
				}
				else if(sel.toUpperCase().charAt(0) == 'N'){
					System.out.println("회원탈퇴가 취소되었습니다.");
					scu.delay();
					prm.menu();
				}
			}
			else{
				System.out.println("잘못된 답변입니다.");
				scu.delay();
				prm.menu();
			}
		}
		
		else{
			System.out.println("비밀번호가 일치하지 않습니다.");
			scu.delay();
			prm.menu();
		}
	}
	
	public void logOut(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		PrimaryMenu prm = PrimaryMenu.getInstance();
		
		System.out.print("정말로 로그아웃하시겠습니까? Y/N ");
		String sel = exe.inputStringCatch();
		boolean flag = exe.selectException(sel.toUpperCase());
		if(flag){
			if(sel.toUpperCase().charAt(0) == 'Y'){
				InitialMenu.userKey = -1;
				System.out.println("정상적으로 로그아웃되었습니다.");
				scu.delay();
				new InitialMenu().menu();
			}
			else if(sel.toUpperCase().charAt(0) == 'N'){
				prm.menu();
			}
		}
		else{
			System.out.println("잘못된 답변입니다.");
			scu.delay();
			prm.menu();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
