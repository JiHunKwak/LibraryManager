package Lib_Function;

import java.util.Scanner;
import java.util.regex.Pattern;

import Lib_Constants.Constants;
import Lib_Menu.InitialMenu;
import Lib_Function.AccountManager;

public class ExceptionCatcher {
	
	private static ExceptionCatcher exc = new ExceptionCatcher();
	private ExceptionCatcher(){}
	public static ExceptionCatcher getInstance(){
		return exc;
	}
	
	private Scanner scan;
	
	public int inputIntegerCatch(int menuSize){
		int buffer = 0;
		boolean flag = true;
		scan = new Scanner(System.in);
			while(true){
				flag = true;
				try{
					buffer = Integer.parseInt(scan.nextLine());
					if(buffer <1 || buffer >menuSize) flag = false;
				}catch(Exception e){flag = false;}
				if(flag) break;
				System.out.println("잘못된 입력입니다. 다시 입력하십시오.");
			}
		return buffer;
	}
	
	public String inputStringCatch(){
		String buffer = null;
		boolean flag = true;
		scan = new Scanner(System.in);
			while(true){
				flag = true;
				try{
					buffer = scan.nextLine();
					if(buffer.isEmpty() == true) flag = false;
				}catch(Exception e){ flag = false; }
				if(flag) break;
				System.out.println("잘못된 입력입니다. 다시 입력하십시오.");
			}
		return buffer;
	}
	
	public boolean signupException(int mode){
		boolean flag = true;
		Pattern name = Pattern.compile("^[a-zA-z가-힣]{2,5}$");
		Pattern id = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
		Pattern pw = Pattern.compile("^[a-z0-9]{5,15}$");
		Pattern phone = Pattern.compile("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$");
		Pattern email = Pattern.compile("^[a-z0-9]*@[a-z]*.[a-z]{3,3}$");
		
		String[] buffer = new String[Constants.ACCOUNT_DATA_LENGTH];
		int key = InitialMenu.userKey;
		
		switch(mode){
		case Constants.SIGNUP_MODE:
			for(int i=0; i<buffer.length; i++)
				buffer[i] = InitialMenu.buffer[i];
			break;
		case Constants.MODIFY_MODE:
			for(int i=0; i<buffer.length; i++)
				buffer[i] = AccountManager.buffer[i];
			break;
		}
		
		if(name.matcher(buffer[0]).find() == false){
			System.out.println("[이름] 형식에 맞지 않습니다.");
			flag = false;
		}
		
		
		if(id.matcher(buffer[1]).find() == false){
			System.out.println("[ID] 형식에 맞지 않습니다.");
			flag = false;
		}
		else if(isOverlap(key, buffer[1], Constants.CONDITION_ID)){
			System.out.println("[ID] 이미 존재합니다.");
			flag = false;
		}
		
		if(pw.matcher(buffer[2]).find() == false){
			System.out.println("[비밀번호] 형식에 맞지 않습니다.");
			flag = false;
		}
		
		
		if(phone.matcher(buffer[3]).find() == false){
			System.out.println("[휴대폰번호] 형식에 맞지 않습니다.");
			flag = false;
		}
		else if(isOverlap(key, buffer[3], Constants.CONDITION_PHONENUMBER)){
			System.out.println("[휴대폰번호] 이미 존재합니다.");
			flag = false;
		}
		
		
		if(email.matcher(buffer[4]).find() == false){
			System.out.println("[e-mail] 형식에 맞지 않습니다.");
			flag = false;
		}
		else if(isOverlap(key, buffer[4], Constants.CONDITION_EMAIL)){
			System.out.println("[E-mail] 이미 존재합니다.");
			flag = false;
		}
		
		return flag;
	}
	
	public boolean isOverlap(int key, String buffer, int condition){
		for(int i=0; i<DbManager.accArr.size(); i++){
			switch(condition){
			case Constants.CONDITION_ID:
				if(i != key && buffer.equals(DbManager.accArr.get(i).getId()))
					return true;
				break;
			case Constants.CONDITION_PHONENUMBER:
				if(i != key && buffer.equals(DbManager.accArr.get(i).getPhoneNum()))
					return true;
				break;
			case Constants.CONDITION_EMAIL:
				if(i != key && buffer.equals(DbManager.accArr.get(i).getEmail()))
					return true;
				break;
			}
		}
		return false;
	}
	
	public boolean selectException(String sel){
		if(sel.charAt(0) != 'Y' && sel.charAt(0) != 'N')
			return false;
		else if(sel.length() > 1)
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
