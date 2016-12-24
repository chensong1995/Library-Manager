package databaseConnection;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;  

public class DBHelper {

    public static final String url = "jdbc:mysql://120.27.121.163:3306/library?useUnicode=true&characterEncoding=UTF-8";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "test";  
    public static final String password = "password";  

    public Connection conn = null;  
  
    public DBHelper() {  
        try {  
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public void closeConnection() {  
        try {  
            this.conn.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
    
    public boolean isConnectionEstablished() {
        try {  
           return !conn.isClosed();
        } catch (SQLException e) {  
            e.printStackTrace();  
            return false;
        }  
    }
    
    private String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
			e.printStackTrace();
			return "";
        }
    }
    
    public String login(String username, String password) {
    	String status = "fail";
    	if (isConnectionEstablished()) {
    		try {
    			String sql = "SELECT user_type "
    					+ "FROM users "
    					+ "WHERE id = ? "
    					+ "AND password = ? "
    					+ "AND status = 'valid';";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, getMD5(password));
				ResultSet result = ps.executeQuery(); 
				if (result.next()) { // login success
					status = result.getString(1);
				}
				result.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return status;
    }
    
    public List<BookRecord> searchBook(String keyword) {
    	ArrayList<BookRecord> records = new ArrayList<BookRecord>();
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT * "
        				+ "FROM books "
        				+ "WHERE isbn = ? "  
        				+ "OR title LIKE ? "
        				+ "OR type LIKE ? "
        				+ "OR press LIKE ? "
        				+ "OR author LIKE ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, keyword);
				ps.setString(2, "%" + keyword + "%");
				ps.setString(3, "%" + keyword + "%");
				ps.setString(4, "%" + keyword + "%");
				ps.setString(5, "%" + keyword + "%");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					records.add(new BookRecord(
							rs.getInt(1),    // id
							rs.getString(2), // isbn
							rs.getString(3), // title
							rs.getString(4), // type
							rs.getInt(5),    // price
							rs.getString(6), // press
							rs.getString(7), // author
							rs.getString(8)  // date
							));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return records;
    }
    
    public boolean isBookBorrowed(int id) {
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT 1 "
        				+ "FROM books, borrowings "
        				+ "WHERE books.id = borrowings.bid "  
        				+ "AND books.id = ? "
        				+ "AND borrowings.end_date is null;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					rs.close();
					ps.close();
					return true;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean addBook(BookRecord book) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "INSERT INTO books "
	    				+ "VALUES (null, ?, ?, ?, ?, ?, ?, ?);";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, book.getIsbn());
				ps.setString(2, book.getTitle());
				ps.setString(3, book.getType());
				ps.setInt(4, book.getPrice());
				ps.setString(5, book.getPress());
				ps.setString(6, book.getAuthor());
				ps.setString(7, book.getDate());
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean modifyBook(BookRecord book) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE books "
	    				+ "SET isbn = ? ,"
	    				+ "title = ? ,"
	    				+ "type = ? ,"
	    				+ "price = ? ,"
	    				+ "press = ? ,"
	    				+ "author = ? ,"
	    				+ "date = ? "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, book.getIsbn());
				ps.setString(2, book.getTitle());
				ps.setString(3, book.getType());
				ps.setInt(4, book.getPrice());
				ps.setString(5, book.getPress());
				ps.setString(6, book.getAuthor());
				ps.setString(7, book.getDate());
				ps.setInt(8, book.getId());
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public List<UserRecord> searchUser(String keyword) {
    	ArrayList<UserRecord> records = new ArrayList<UserRecord>();
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT id, status, user_type "
        				+ "FROM users "
        				+ "WHERE id LIKE ?;"; 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + keyword + "%");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					records.add(
							new UserRecord(
							rs.getString(1),    // username
							rs.getString(2),    // status
							rs.getString(3)     // type
							));
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return records;
    }
    
    public boolean setAsAdmin(String username) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET user_type = 'admin' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean setAsStandard(String username) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET user_type = 'standard' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean setAsPremium(String username) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET user_type = 'premium' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean banUser(String username) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET status = 'invalid' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean activeUser(String username) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET status = 'valid' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean modifyPassword(String username, String password) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "UPDATE users "
	    				+ "SET password = ? "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, getMD5(password));
				ps.setString(2, username);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public boolean createStandard(String username, String password) {
    	if (isConnectionEstablished()) {
			try {
	    		String sql = "INSERT INTO users "
	    				+ "VALUES (?, ?, 'valid', 'standard');";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, getMD5(password));
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
    
    public List<BorrowRecord> searchBorrowRecord(String keyword) {
    	ArrayList<BorrowRecord> records = new ArrayList<BorrowRecord>();
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT borrowings.id, borrowings.uid, books.isbn, books.title, borrowings.start_date, borrowings.end_date "
        				+ "FROM users, books, borrowings "
        				+ "WHERE borrowings.uid = users.id "
        				+ "AND borrowings.bid = books.id "
        				+ "AND (borrowings.uid LIKE ? "
        				+ "OR books.title LIKE ?) "
        				+ "ORDER BY borrowings.id DESC;"; 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, "%" + keyword + "%");
				ps.setString(2, "%" + keyword + "%");
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					records.add(
							new BorrowRecord(
							rs.getInt(1),        // id
							rs.getString(2),     // username
							rs.getString(3),     // isbn
							rs.getString(4),     // title
							rs.getString(5),     // start_time
							rs.getString(6)      // end_time
							));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return records;
    }
    
    public List<BorrowRecord> searchBorrowRecordByUsername(String username) {
    	ArrayList<BorrowRecord> records = new ArrayList<BorrowRecord>();
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT borrowings.id, borrowings.uid, books.isbn, books.title, borrowings.start_date, borrowings.end_date "
        				+ "FROM users, books, borrowings "
        				+ "WHERE borrowings.uid = users.id "
        				+ "AND borrowings.bid = books.id "
        				+ "AND borrowings.uid = ? "
        				+ "ORDER BY borrowings.id DESC;"; 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					records.add(
							new BorrowRecord(
							rs.getInt(1),        // id
							rs.getString(2),     // username
							rs.getString(3),     // isbn
							rs.getString(4),     // title
							rs.getString(5),     // start_time
							rs.getString(6)      // end_time
							));
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return records;
    }
    
    public boolean borrowBook(String username, int bid) {
    	if (isConnectionEstablished()) {
			try {
				Date date = new Date(); // now
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = simpleDateFormat.format(date);
	    		String sql = "INSERT INTO borrowings "
	    				+ "VALUES (null, ?, ?, '" + startDate + "', null);";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setInt(2, bid);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }

    public int getAlreadyBorrowedCount(String username) {
    	if (isConnectionEstablished()) {
    		try {
        		String sql = "SELECT COUNT(1) "
        				+ "FROM borrowings "
        				+ "WHERE uid = ? "  
        				+ "AND end_date is null;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					int count = rs.getInt(1);
					rs.close();
					ps.close();
					return count;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return 0;
    }
    
    public boolean returnBook(int id) {
    	if (isConnectionEstablished()) {
			try {
				Date date = new Date(); // now
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String endDate = simpleDateFormat.format(date);
	    		String sql = "UPDATE borrowings "
	    				+ "SET end_date = '" + endDate + "' "
	    				+ "WHERE id = ?;";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				if (ps.executeUpdate() != 0) { // success
					ps.close();
					return true;
				}
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return false;
    }
}
