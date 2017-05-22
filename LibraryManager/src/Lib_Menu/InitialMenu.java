package Lib_Menu;

import Lib_Function.*;
import Lib_Constants.Constants;
import Lib_Account.Account;

public class InitialMenu {
	
	public static int userKey;
	
	public static String[] buffer = null;

	public void menu(){
		userKey = -1;
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		int sel = 0;
		
		scu.initialMenu();
		sel = exe.inputIntegerCatch(Constants.INITIAL_MENU_SIZE);
		switch(sel){
		case 1:
			this.signIn();
			break;
		case 2:
			this.signUp();
			break;
		case 3:
			scu.exitProgram();
			break;
		}
	}
	
	public void signIn(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		
		System.out.println();
		System.out.println("Sign In");
		boolean idFlag = false;
		boolean loginFlag = false;
		String idBuf = null;
		String pwBuf = null;
		
		System.out.print("ID: ");
		idBuf = exe.inputStringCatch();
		System.out.print("PW: ");
		pwBuf = exe.inputStringCatch();
		
		for(int i=0; i<DbManager.accArr.size(); i++){
			if(idBuf.equals(DbManager.accArr.get(i).getId())){
				idFlag = true;
				if(pwBuf.equals(DbManager.accArr.get(i).getPassword())){
					loginFlag = true;
					InitialMenu.userKey = i;
				}
			}
		}
		if(idFlag == false){
			System.out.println("아이디를 확인하십시오.");
			scu.delay();
			this.menu();
		}
		else if(idFlag && loginFlag == false){
			System.out.println("비밀번호를 확인하십시오.");
			scu.delay();
			this.menu();
		}
		else if(idFlag && loginFlag){
			System.out.println(DbManager.accArr.get(userKey).getName()+"님, 환영합니다.");
			try{Thread.sleep(3000);}catch(Exception e){}
			scu.clearScreen();
			PrimaryMenu.getInstance().menu();
		}
	}
	public void signUp(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		DbManager dbm = DbManager.getInstance();
		
		System.out.println();
		System.out.println("Sign Up");
		buffer = new String[Constants.ACCOUNT_DATA_LENGTH];
		for(int i=0; i<Constants.SIGNUP_TEXTBOX.length; i++){
			System.out.print(Constants.SIGNUP_TEXTBOX[i]);
			buffer[i] = exe.inputStringCatch();
		}
		System.out.println();
		boolean flag = exe.signupException(Constants.SIGNUP_MODE);
		if(flag){
			System.out.println(buffer[0]+"님, 회원가입을 축하드립니다.");
			DbManager.accArr.add(new Account(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4]));
			dbm.saveData();
			scu.delay();
			this.menu();
		}
		else{
			scu.delay();
			this.signUp();
		}
	}
	
}
