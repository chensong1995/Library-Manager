package databaseConnection;

public class UserRecord {
	private String username;
	private String status;
	private String type;
	
	public UserRecord(String username, String status, String type) {
		this.username = username;
		if (status.equals("valid")) {
			this.status = "正常用户";
		} else if (status.equals("invalid")) {
			this.status = "被禁用";
		} else {
			this.status = status;
		}
		if (type.equals("admin")) {
			this.type = "管理员";
		} else if (type.equals("standard")) {
			this.type = "标准读者";
		} else if (type.equals("premium")) {
			this.type = "高级读者";
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getType() {
		return type;
	}
}
