package databaseConnection;

public class BorrowRecord {
	private int id;
	private String username;
	private String isbn;
	private String title;
	private String startDate;
	private String endDate;
	
	public BorrowRecord(int id, String username, String isbn, String title, String startDate, String endDate) {
		this.id = id;
		this.username = username;
		this.isbn = isbn;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public boolean hasBeenReturned() {
		return endDate != null;
	}
	
	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getIsbn() {
		return isbn;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
}
