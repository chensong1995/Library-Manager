package databaseConnection;

public class BookRecord {
	private int id;
	private String isbn;
	private String title;
	private String type;
	private int price;
	private String press;
	private String author;
	private String date;
	
	public BookRecord(int id, String isbn, String title, String type, int price, String press, String author, String date) {
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.type = type;
		this.price = price;
		this.press = press;
		this.author = author;
		this.date = date;
	}
	
	public BookRecord(String isbn, String title, String type, int price, String press, String author, String date) {
		this(-1, isbn, title, type, price, press, author, date); // -1 means undefined
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getIsbn() {
		return isbn;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return price;
	}
	
	public void setPress(String press) {
		this.press = press;
	}
	public String getPress() {
		return press;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return date;
	}
}
