package vo;

import java.io.Serializable;
import java.util.ArrayList;

public class roomData implements Serializable {
	/**
	 * 
	 */
	// 전송되는 데이터의 종류 구분
	private static final long serialVersionUID = 1L;
	// roomGUI에서 사용되는 채팅과 쪽지기능
	public static final int loginAc = 9; // 누군가 로그인 시 리플래시용
	public static final int Allchat = 30; // 대기방채팅 30번대
	public static final int p2pchat = 31; // 개인간 채팅
	// RoomMake 방을 만드는 Signal
	public static final int roomMake = 50; // 방 만들기 (방 )
	public static final int roomIn = 51; // 방 들어가기
	public static final int roomDestroy = 52; // 방 나가기 (방 )
	public static final int roomRefresh = 55;

	int state; // 전송되는 데이터 종류
	String id; // 대화내용 입력한 사용자 이름
	String idOpposing;// 상대방 아이디
	String pw; // 암호 (로그인때만 사용)
	String message; // 메세지 내용
	int win; // 승
	int defeat; // defeat
	account acc;
	makeRoom makeRoomVO;
	ArrayList<account_tenshu> usernames; // 접속자 이름 목록
	ArrayList<makeRoom> makeroom; // 방만들때 이거 사용.
	ArrayList<Object> makeroomList; // 방 만들시 이거 사용
	boolean ready1p;
	boolean ready2p;
	boolean gameEnd;

	// Data.loninAc
	public roomData(int state, String id, int win, int defeat, ArrayList<account_tenshu> usernames,
			ArrayList<makeRoom> makeroom) {
		super();
		this.state = state;
		this.id = id;
		this.win = win; // 승리수
		this.defeat = defeat; // 패배수
		this.usernames = usernames; // 대기방 리스트의 사용자 리스트
		this.makeroom = makeroom; // 대기 방의 게임방 리스트
	}

	// 채팅 과 방 들어가기 할때 사용합니다.
	public roomData(int state, String id, String message) {
		super();
		this.state = state;
		this.id = id;
		this.message = message;
	}

	public roomData(int state, ArrayList<account_tenshu> usernames) {
		super();
		this.state = state;
		this.usernames = usernames;
	}

	// 방 만들기 시 사용
	public roomData(int state, String id, String idOpposing, makeRoom makeRoomVO, ArrayList<makeRoom> makeroom,
			ArrayList<Object> makeroomList, boolean ready1p, boolean ready2p) {
		super();
		this.state = state;
		this.makeRoomVO = makeRoomVO;
		this.id = id;
		this.idOpposing = idOpposing;
		this.makeroom = makeroom;
		this.makeroomList = makeroomList;
		this.ready1p = ready1p;
		this.ready2p = ready2p;

	}

	// 귓속말
	public roomData(int state, String id, String idOpposing, String message) {
		super();
		this.state = state;
		this.id = id;
		this.idOpposing = idOpposing;
		this.message = message;
	}

	// 방 들어가기
	public roomData(int state, String id, ArrayList<makeRoom> makeroom) {
		super();
		this.state = state;
		this.id = id;
		this.makeroom = makeroom;
	}

	// 게임 끝날때 방 나가기
	public roomData(int state, String id, ArrayList<makeRoom> makeroom, boolean gameEnd) {
		super();
		this.state = state;
		this.id = id;
		this.makeroom = makeroom;
		this.gameEnd = gameEnd;
	}

	public boolean isGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
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

	public String getIdOpposing() {
		return idOpposing;
	}

	public void setIdOpposing(String idOpposing) {
		this.idOpposing = idOpposing;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

	public account getAcc() {
		return acc;
	}

	public void setAcc(account acc) {
		this.acc = acc;
	}

	public makeRoom getMakeRoomVO() {
		return makeRoomVO;
	}

	public void setMakeRoomVO(makeRoom makeRoomVO) {
		this.makeRoomVO = makeRoomVO;
	}

	public ArrayList<account_tenshu> getUsernames() {
		return usernames;
	}

	public void setUsernames(ArrayList<account_tenshu> idList) {
		this.usernames = idList;
	}

	public ArrayList<makeRoom> getMakeroom() {
		return makeroom;
	}

	public void setMakeroom(ArrayList<makeRoom> makeroom) {
		this.makeroom = makeroom;
	}

	public ArrayList<Object> getMakeroomList() {
		return makeroomList;
	}

	public void setMakeroomList(ArrayList<Object> makeroomList) {
		this.makeroomList = makeroomList;
	}

	public boolean isReady1p() {
		return ready1p;
	}

	public void setReady1p(boolean ready1p) {
		this.ready1p = ready1p;
	}

	public boolean isReady2p() {
		return ready2p;
	}

	public void setReady2p(boolean ready2p) {
		this.ready2p = ready2p;
	}

}
