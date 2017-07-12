package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class accountData implements Serializable {
	/**
	 * 
	 */
	// 전송되는 데이터의 종류 구분
	private static final long serialVersionUID = 1L;
	public static final int newAccount = 7;
	public static final int modifiyAccount = 8;
	public static final int deleteAccount = 9;

	int state;
	String id;
	String pw;
	boolean flag;

	public accountData(int state, String id, String pw, boolean flag) {
		super();
		this.state = state;
		this.id = id;
		this.pw = pw;
		this.flag = flag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

}
