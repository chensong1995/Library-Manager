package databaseConnection;

public class UserRecord {
	private String username;
	private String status;
	private String type;
	
	public UserRecord(String username, String status, String type) {
		this.username = username;
		if (status.equals("valid")) {
			this.status = "�����û�";
		} else if (status.equals("invalid")) {
			this.status = "������";
		} else {
			this.status = status;
		}
		if (type.equals("admin")) {
			this.type = "����Ա";
		} else if (type.equals("standard")) {
			this.type = "��׼����";
		} else if (type.equals("premium")) {
			this.type = "�߼�����";
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
