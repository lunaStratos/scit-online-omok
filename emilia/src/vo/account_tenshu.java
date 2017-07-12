package vo;

public class account_tenshu extends account {

	public account_tenshu( String id, int win, int defeat) {
		super(id);
		this.win = win;
		this.defeat = defeat;
		// TODO Auto-generated constructor stub
	}
	
	public account_tenshu( String id, String pw, int win, int defeat) {
		super(id, pw);
		this.win = win;
		this.defeat = defeat;
		// TODO Auto-generated constructor stub
	}

	private int win;
	private int defeat;
	
	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDefeat() {
		return defeat;
	}

	public void setDefeat(int defeat) {
		this.defeat = defeat;
	}

	@Override
	public String toString() {
		return super.toString()+ " ½Â: " + win + " | ÆÐ: " + defeat;
	}
	
	

}
