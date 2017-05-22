package Lib_Main;

import Lib_Function.DbManager;
import Lib_Menu.InitialMenu;

public class Run {
	
	public void run(){
		DbManager dbM = DbManager.getInstance();
		dbM.loadData();				
		new InitialMenu().menu();
	}

}
