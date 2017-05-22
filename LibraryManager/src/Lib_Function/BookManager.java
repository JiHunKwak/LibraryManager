package Lib_Function;

import Lib_Constants.Constants;
import Lib_Menu.InitialMenu;
import Lib_Menu.PrimaryMenu;

import java.util.*;

public class BookManager {
	
	private static BookManager bom = new BookManager();
	private BookManager(){}
	public static BookManager getInstance(){
		return bom;
	}
	
	private List<Integer> searchBuffer;
	
	public void searchBook(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		int sel = 0;
		
		scu.clearScreen();
		scu.searchBookMenu();
		sel = exe.inputIntegerCatch(Constants.SEARCH_BOOK_MENU_SIZE);
		switch(sel){
		case 1:
			searchRoutine(Constants.CONDITION_NAME);
			scu.askContinue();
			PrimaryMenu.getInstance().menu();
			break;
		case 2:
			searchRoutine(Constants.CONDITION_AUTHOR);
			scu.askContinue();
			PrimaryMenu.getInstance().menu();
			break;
		case 3:
			searchRoutine(Constants.CONDITION_PUBLISHER);
			scu.askContinue();
			PrimaryMenu.getInstance().menu();
			break;
		case 4:
			searchRoutine(Constants.CONDITION_ALL);
			scu.askContinue();
			PrimaryMenu.getInstance().menu();
			break;
		case 5:
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
			for(int i=0; i<DbManager.bookArr.size(); i++)
				if(DbManager.bookArr.get(i).getName().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		case Constants.CONDITION_AUTHOR:
			System.out.print("Enter keyword: ");
			keyword = exe.inputStringCatch();
			for(int i=0; i<DbManager.bookArr.size(); i++)
				if(DbManager.bookArr.get(i).getAuthor().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		case Constants.CONDITION_PUBLISHER:
			System.out.print("Enter keyword: ");
			keyword = exe.inputStringCatch();
			for(int i=0; i<DbManager.bookArr.size(); i++)
				if(DbManager.bookArr.get(i).getPublisher().toUpperCase().contains(keyword.toUpperCase())){
					searchBuffer.add(i);
					searchFlag = true;
				}
			break;
		case Constants.CONDITION_ALL:
			for(int i=0; i<DbManager.bookArr.size(); i++){
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
				System.out.print("name: " + DbManager.bookArr.get(searchBuffer.get(i)).getName());
				System.out.print(" | Author: " + DbManager.bookArr.get(searchBuffer.get(i)).getAuthor());
				System.out.print(" | Publisher: " + DbManager.bookArr.get(searchBuffer.get(i)).getPublisher());
				System.out.println(" | Price: " + DbManager.bookArr.get(searchBuffer.get(i)).getPrice());
				if(DbManager.bookArr.get(searchBuffer.get(i)).getState() == Constants.LENDING_POSSIBLE) System.out.println("   (O) 대출가능");
				else if(DbManager.bookArr.get(searchBuffer.get(i)).getState() == Constants.LENDING_IMPOSSIBLE) System.out.println("   (X) 대출불가");
				System.out.println("-----------------------------------------------------------------------------------");
			}
		}
		else{
			System.out.println("검색결과가 없습니다.");
		}
			
	}
	
	public void lendBook(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		int sel = 0;
		
		scu.searchBookMenu();
		sel = exe.inputIntegerCatch(Constants.SEARCH_BOOK_MENU_SIZE);
		switch(sel){
		case 1:
			this.lendRoutine(Constants.CONDITION_NAME);
			break;
		case 2:
			this.lendRoutine(Constants.CONDITION_AUTHOR);
			break;
		case 3:
			this.lendRoutine(Constants.CONDITION_PUBLISHER);
			break;
		case 4:
			this.lendRoutine(Constants.CONDITION_ALL);
			break;
		case 5:
			scu.clearScreen();
			PrimaryMenu.getInstance().menu();
			break;
		}
	}
	
	public void lendRoutine(int condition){
		int lendSelect = 0;
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		DbManager dbm = DbManager.getInstance();
		PrimaryMenu prm = PrimaryMenu.getInstance();
				
		this.searchRoutine(condition);
		
		if(searchBuffer.size() == 0){
			scu.askContinue();
			prm.menu();
		}
		
		System.out.println();
		System.out.print("대출을 원하는 책의 번호를 입력하십시오: ");
		lendSelect = exe.inputIntegerCatch(searchBuffer.size())-1;
		if(DbManager.bookArr.get(searchBuffer.get(lendSelect)).getState() == 0){
			DbManager.bookArr.get(searchBuffer.get(lendSelect)).setState(1);
			DbManager.bookArr.get(searchBuffer.get(lendSelect)).setNowLoc(DbManager.accArr.get(InitialMenu.userKey).getName());
			DbManager.bookArr.get(searchBuffer.get(lendSelect)).setDueDate(addDate(Constants.LENDING_PERIOD));
			System.out.println("대출이 완료되었습니다.");
			System.out.println("귀하의 반납일은 " + addDate(Constants.LENDING_PERIOD) + " 입니다.");
			dbm.saveData();
		}
		else if(DbManager.bookArr.get(searchBuffer.get(lendSelect)).getState() == 1){
			System.out.println("선택하신 책은 이미 대출중입니다.");
		}
		
		scu.delay();
		prm.menu();
	}
	
	public void returnBook(){
		ExceptionCatcher exe = ExceptionCatcher.getInstance();
		ScreenUi scu = ScreenUi.getInstance();
		DbManager dbm = DbManager.getInstance();
		PrimaryMenu prm = PrimaryMenu.getInstance();
		
		int returnSelect = 0;
		List<Integer> lendingListBuffer = new ArrayList<>();
		
		boolean flag = false;
		for(int i=0; i<DbManager.bookArr.size(); i++)
			if(DbManager.bookArr.get(i).getNowLoc().equals(DbManager.accArr.get(InitialMenu.userKey).getName()))
				flag = true;
		
		if(!flag){
			System.out.println("반납할 책이 없습니다.");
			scu.delay();
			prm.menu();
		}
		
		else if(flag){
			
			for(int i=0; i<DbManager.bookArr.size(); i++){
				if(DbManager.bookArr.get(i).getNowLoc().equals(DbManager.accArr.get(InitialMenu.userKey).getName()))
					lendingListBuffer.add(i);
				}
			
			System.out.println(DbManager.accArr.get(InitialMenu.userKey).getName() + "님의 현재 대여중인 책 목록입니다.");
			System.out.println();

			System.out.println("-----------------------------------------------------------------------------------");
			for(int i=0; i<lendingListBuffer.size(); i++){
				System.out.print((i+1) + ") ");
				System.out.print("name: " + DbManager.bookArr.get(lendingListBuffer.get(i)).getName());
				System.out.print(" | Author: " + DbManager.bookArr.get(lendingListBuffer.get(i)).getAuthor());
				System.out.println(" | Publisher: " + DbManager.bookArr.get(lendingListBuffer.get(i)).getPublisher());
				System.out.println("   Due Date: " + DbManager.bookArr.get(lendingListBuffer.get(i)).getDueDate() + " 까지");
				System.out.println("-----------------------------------------------------------------------------------");
			}
			
			System.out.println();
			System.out.print("반납할 책의 번호를 입력하십시오: ");
			returnSelect = exe.inputIntegerCatch(lendingListBuffer.size())-1;
			DbManager.bookArr.get(lendingListBuffer.get(returnSelect)).setState(Constants.LENDING_POSSIBLE);
			DbManager.bookArr.get(lendingListBuffer.get(returnSelect)).setNowLoc("LIB");
			DbManager.bookArr.get(lendingListBuffer.get(returnSelect)).setDueDate("null");
			System.out.println("반납이 완료되었습니다.");
			dbm.saveData();
			scu.delay();
			prm.menu();
		}
		
	}
	
	public boolean isRental(){
		for(int i=0; i<DbManager.bookArr.size(); i++)
			if(DbManager.bookArr.get(i).getNowLoc().equals(DbManager.accArr.get(InitialMenu.userKey).getName()))
				return true;
		return false;
	}
	
	public String addDate(int day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return String.valueOf(cal.get(Calendar.YEAR)) + "년 " + String.valueOf((cal.get(Calendar.MONTH)+1)) + "월 "
			+ String.valueOf(cal.get(Calendar.DATE)) + "일";
	}

}
