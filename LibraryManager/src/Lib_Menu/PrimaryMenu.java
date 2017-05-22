package Lib_Menu;

import Lib_Function.*;
import Lib_Constants.Constants;

public class PrimaryMenu {
	
	private static PrimaryMenu prm = new PrimaryMenu();
	private PrimaryMenu(){}
	
	public void menu(){
		BookManager bom = BookManager.getInstance();
		AccountManager acm = AccountManager.getInstance();
		
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		int sel = 0;
		System.out.println("                                   "+DbManager.accArr.get(InitialMenu.userKey).getName()+"님 반갑습니다.");
		scu.primaryMenu();
		sel = exe.inputIntegerCatch(Constants.PRIMARY_MENU_SIZE);
		switch(sel){
		case 1:
			scu.clearScreen();
			bom.searchBook();
			break;
		case 2:
			scu.clearScreen();
			bom.lendBook();
			break;
		case 3:
			scu.clearScreen();
			bom.returnBook();
			break;
		case 4:
			scu.clearScreen();
			acm.searchAccount();
			break;
		case 5:
			scu.clearScreen();
			acm.modifyOwnData();
			break;
		case 6:
			scu.clearScreen();
			acm.deleteAccount();
			break;
		case 7:
			scu.clearScreen();
			acm.logOut();
			break;
		case 8:
			System.out.println();
			scu.exitProgram();
			break;
		}
	}
	
	public static PrimaryMenu getInstance(){
		return prm;
	}

}
