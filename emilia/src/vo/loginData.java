package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class loginData implements Serializable {
	/**
	 * 
	 */
	// 전송되는 데이터의 종류 구분
	private static final long serialVersionUID = 1L;
	
	//로그인과 리스트 갱신에 사용
	public static final int login = 1; // 로그인

	int state; // 전송되는 데이터 종류
	String id; // 대화내용 입력한 사용자 이름
	String pw; // 암호 (로그인때만 사용)
	boolean loginBoolean;
	account_tenshu act ;

	
	public loginData(int state, String id, String pw, boolean loginBoolean, account_tenshu act) {
		super();
		this.state = state;
		this.id = id;
		this.pw = pw;
		this.loginBoolean = loginBoolean;
		this.act = act;
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

	public boolean isLoginBoolean() {
		return loginBoolean;
	}

	public void setLoginBoolean(boolean loginBoolean) {
		this.loginBoolean = loginBoolean;
	}

	public account_tenshu getAct() {
		return act;
	}

	public void setAct(account_tenshu act) {
		this.act = act;
	}

	

}
