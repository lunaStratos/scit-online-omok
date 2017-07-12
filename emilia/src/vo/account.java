package vo;

import java.io.Serializable;

public class account implements Serializable {

	private String id;
	private String pw;


	public account(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
	}

	public account(String id2) {
		// TODO Auto-generated constructor stub
		this.id = id2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	@Override
	public String toString() {
		return "ID : " + id + " |";
	}
	
	

}
