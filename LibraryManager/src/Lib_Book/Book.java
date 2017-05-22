package Lib_Book;

public class Book {

	private int state;
	private String name;
	private String author;
	private String publisher;
	private int price;
	private String nowLoc;
	private String dueDate;
	
	public Book(){}
	
	public Book(int state, String name, String author, String publisher, int price, String nowLoc, String dueDate){
		this.state = state;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.nowLoc = nowLoc;
		this.dueDate = dueDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getNowLoc() {
		return nowLoc;
	}

	public void setNowLoc(String nowLoc) {
		this.nowLoc = nowLoc;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	
	
}
