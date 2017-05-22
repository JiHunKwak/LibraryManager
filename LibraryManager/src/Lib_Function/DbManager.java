package Lib_Function;

import java.sql.*;
import java.util.*;
import Lib_Account.Account;
import Lib_Book.Book;

public class DbManager {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/Library";
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "sowlq8755";
	
	public static List<Account> accArr;
	public static List<Book> bookArr;
	
	private static DbManager dbManager = new DbManager();
	private DbManager(){}
	
	public static DbManager getInstance(){
		return dbManager;
	}
	
	public void loadData(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet ress = null;
		
		accArr = new ArrayList<>();
		bookArr = new ArrayList<>();
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String accQuery = "SELECT * FROM account";
			String bookQuery = "SELECT * FROM book";
			
			ress = stmt.executeQuery(accQuery);
			while(ress.next()){
				Account acc = new Account(ress.getString(1), ress.getString(2), ress.getString(3), ress.getString(4), ress.getString(5));
				DbManager.accArr.add(acc);
			}
			ress = stmt.executeQuery(bookQuery);
			while(ress.next()){
				Book book = new Book(ress.getInt(1), ress.getString(2), ress.getString(3), ress.getString(4), ress.getInt(5), ress.getString(6), ress.getString(7));
				DbManager.bookArr.add(book);
			}
			
			System.out.println("Data has been successfully loaded.");
			
			
		}catch(Exception e){
			System.out.println(e.toString());
			System.out.println("Data load failed.");
		}finally{
			if(ress != null)try{ress.close();}catch(SQLException sqle){}
			if(stmt != null)try{stmt.close();}catch(SQLException sqle){}
			if(conn != null)try{conn.close();}catch(SQLException sqle){}
		}
	}
	
	public void saveData(){
		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String accQuery1 = null;
			String accQuery2 = "DELETE FROM account";
			String bookQuery1 = null;
			String bookQuery2 = "DELETE FROM book";
		
			stmt.executeUpdate(accQuery2);
			for(int i=0; i<DbManager.accArr.size(); i++){
				accQuery1 = "INSERT INTO account VALUES('"+DbManager.accArr.get(i).getName()+"', '"+DbManager.accArr.get(i).getId()+"', '"+
						DbManager.accArr.get(i).getPassword()+"', '"+DbManager.accArr.get(i).getPhoneNum()+"', '"+DbManager.accArr.get(i).getEmail()+"')";
				stmt.executeUpdate(accQuery1);
			}
			
			stmt.executeUpdate(bookQuery2);
			for(int i=0; i<DbManager.bookArr.size(); i++){
				bookQuery1 = "INSERT INTO book VALUES('"+DbManager.bookArr.get(i).getState()+"', '"+DbManager.bookArr.get(i).getName()+"', '"+
						DbManager.bookArr.get(i).getAuthor()+"', '"+DbManager.bookArr.get(i).getPublisher()+"', '"+DbManager.bookArr.get(i).getPrice()+"', '"+
						DbManager.bookArr.get(i).getNowLoc()+"', '"+DbManager.bookArr.get(i).getDueDate()+"')";
				stmt.executeUpdate(bookQuery1);
			}
			
			System.out.println("Data has been successfully saved.");
			
		}catch(Exception e){
			System.out.println(e.toString());
			System.out.println("Data save failed.");
		}finally{
			if(stmt != null)try{stmt.close();}catch(SQLException sqle){}
			if(conn != null)try{conn.close();}catch(SQLException sqle){}
		}
	}

	
}
