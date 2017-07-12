package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import vo.*;

public class ServerThread implements Runnable {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static ArrayList<account_tenshu> idList = new ArrayList<>();
	private static ArrayList<makeRoom> makeRoom = new ArrayList<>();
	private static HashMap<String, ServerThread> connectList = new HashMap<>();
	private serverManager sm = new serverManager();
	Socket socket;
	String username;
	String addr;
	private static ArrayList<ServerThread> threadList = new ArrayList<>();
	private static ArrayList<ObjectOutputStream> outputList = new ArrayList<>();

	public ServerThread(Socket socket) {
		try {
			this.socket = socket;
			// 클라이언트와의 스트림 생성
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
			addr = socket.getInetAddress().getHostAddress();
		} catch (IOException e) {
			System.out.println(addr + "과의 연결 실패.");
		}
		// threadList.add(this);
		// outputList.add(output);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean flag = true;
		Object getData = null;
		account ac = null;
		account_tenshu ac_tenshu = null;
		try {
			while (flag) {
				getData = input.readObject();
				// System.out.println(id + " > status:" + state + ", state:" +
				// data.getState());
				if (getData instanceof loginData) { // 로그인 부분
					// protocol, id, pw, loginboolean
					switch (((loginData) getData).getState()) {

					case loginData.login: // 로그인
						String id = ((loginData) getData).getId();
						String pw = ((loginData) getData).getPw();
						int nowconn = 0;
						for (int index = 0; index < idList.size(); index++) {
							// 현재 접속자에 있다면 로그인실패
							if (idList.get(index).getId().equals(((loginData) getData).getId())) {
								nowconn++;
							}
						}
						if (nowconn >= 1) { // 현재 접속자가 있다면 로그인 실패로 종료
							((loginData) getData).setLoginBoolean(false);
						} else { // 현재 접속자와 중복이 아니라면 검사 시작
							ac_tenshu = sm.searchAccount(id, pw); // DB에서 아이디 암호
																	// 검색
							if (ac_tenshu != null) { // DB에 값이 있다면
								threadList.add(this); // 접속자 추가
								connectList.put(id, this);
								((loginData) getData).setLoginBoolean(true);
								((loginData) getData).setAct(ac_tenshu);
								idList.add(ac_tenshu); // 보이는 접속자 추가
								outputList.add(output);
							} else {// DB에 값이 없다면
								((loginData) getData).setLoginBoolean(false);
							}
						}
						System.out.println("그거 " + ac_tenshu);
						System.out.println("로그인 : " + ((loginData) getData).isLoginBoolean());
						output.writeObject(getData);
						output.reset();
						// broadCasting(getData);
						break;
					}

				} else if (getData instanceof roomData) { // 접속자 갱신
					switch (((roomData) getData).getState()) {
					case roomData.loginAc: // 현재접속자 리셋부분 중요
						String id = ((roomData) getData).getId();
						ac = sm.searchAccountTenshu(id); // id로 찾아서 데이터 가져옴
						// ac의 데이터는 id(s), pw(""), win(i), defeat(i)이다.
						// 현재 접속된 인원을 보여주는 Array에 더해준다.
						if (ac instanceof account_tenshu) {
							((roomData) getData).setWin(((account_tenshu) ac).getWin());
							((roomData) getData).setDefeat(((account_tenshu) ac).getDefeat());
						}
						System.out.println(idList);
						((roomData) getData).setUsernames(idList); // 현재 방 수정
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData); // 이건 전체 뿌리기
						break;
					case roomData.Allchat: // 대기룸에서 전체채팅
						broadCasting(getData); // 이건 전체 뿌리기
						// int state, String id, String message
						break;
					case roomData.p2pchat: // 대기룸에서 전체채팅
						broadCasting(getData); // 이건 전체 뿌리기로 끝 .
						break;

					case roomData.roomMake: // 게임방 생성
						makeRoom mk = ((roomData) getData).getMakeRoomVO();
						int nowInwon = mk.getInwon();
						makeRoom.add(mk);
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData);
						break;
					case roomData.roomDestroy: // 게임방 나가기
						// 일단 빈칸으로 만들어 줌 //나가는 아이디는 id
						System.out.println(((roomData) getData).getId());
						String uids = ((roomData) getData).getId();
						if (((roomData) getData).isGameEnd()) {
							// 게임 마지막에 방 나가기
							for (int index = 0; index < makeRoom.size(); index++) {
								if (makeRoom.get(index).getMakeName().equals(uids)) {
									makeRoom.get(index).setInwon(0);
								} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
									makeRoom.get(index).setInwon(0);
								}
							}
						} else {
							// 일반적인 방 나가기
							for (int index = 0; index < makeRoom.size(); index++) {
								if (makeRoom.get(index).getMakeName().equals(uids)) {
									// 1번이 나간다고 한다면

									if (makeRoom.get(index).getInwon() == 1) {
										// 방이 한명일 경우
										makeRoom.get(index).setMakeName(null);
										makeRoom.get(index).setInwon(0);
										// 후 처리로 삭제됨 >
									} else if (makeRoom.get(index).getInwon() == 2) {
										// 방이 두명일 경우 player1과 player2를 처리해야 한다.
										// 뒤바꿔줌
										if (makeRoom.get(index).getMakeName().equals(uids)) {
											// 2명일때 1이 나간다고 한다면
											makeRoom.get(index).setMakeName(makeRoom.get(index).getMakeName2());
											// 1번 플레이어는 2번을 받고
											makeRoom.get(index).setMakeName2(null);
											// 2번 플레이어는 null로 교체해줌
										} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
											// 2번 아이디와 같다면
											makeRoom.get(index).setMakeName2(null);
										}
										makeRoom.get(index).setInwon(1);
										// 인원은 1로 교체 (공통)
									}

								} else if (makeRoom.get(index).getMakeName2().equals(uids)) {
									// 2번이 나갈때는 인원이 2일때이다. 1일때는 없다.
									makeRoom.get(index).setMakeName2(null);
									makeRoom.get(index).setInwon(1);
									// 인원은 1로 교체 (공통)
								}
							}
							// 방 삭제 (0명이면 X)

						}
						for (int index = 0; index < makeRoom.size(); index++) {
							if (makeRoom.get(index).getInwon() <= 0) {
								makeRoom.remove(index);
							}
						}

						System.out.println(makeRoom);
						((roomData) getData).setMakeroom(makeRoom);
						// 마지막으로 방 리스트 갱
						broadCasting(getData);
						break;

					case roomData.roomIn: // 게임방 들어가기
						/*
						 * 채팅VO로 사용 mkid는 방을 만든 상대방 아이디 inId는 내 아이디
						 */
						String mkid = ((roomData) getData).getMessage();
						String inId = ((roomData) getData).getId();
						// 방들어검, 내 아이디, 방을 만든 아이디
						for (int i = 0; i < makeRoom.size(); i++) {
							if (makeRoom.get(i).getMakeName().equals(mkid)) {
								makeRoom.get(i).setInwon(2);
								makeRoom.get(i).setMakeName2(inId);
							}
						}
						((roomData) getData).setMakeroom(makeRoom);
						broadCasting(getData);
						break;
						
					case roomData.roomRefresh: // 대기자 초기화
						broadCasting(getData);
						break;

					}
				} else if (getData instanceof gameData) { // 게임부분
					System.out.println("서버의 게임데이터");
					String player1 = ((gameData) getData).getId();
					switch (((gameData) getData).getState()) {
					case gameData.ready: // 레디
						// protocol, id, 상대방 아이디, makeroom vo를 가진 Arraylist
						for (int i = 0; i < makeRoom.size(); i++) {
							if (makeRoom.get(i).getMakeName().equals(player1)) {
								makeRoom.get(i).setPlayer1(true);
								((gameData) getData).setMakeroom(makeRoom);
							} else if (makeRoom.get(i).getMakeName2().equals(player1)) {
								makeRoom.get(i).setPlayer2(true);
								((gameData) getData).setMakeroom(makeRoom);
							}

						}
						System.out.println(makeRoom);
						System.out.println(makeRoom.get(0).isPlayer1());
						System.out.println(makeRoom.get(0).isPlayer2());
						broadCasting(getData);
						break;

					case gameData.whiteGo: // 내가 돌을 올리는 경우
						// gameData.whiteGo, id, omokTable, true, stoneX,stoneY
						System.out.println(player1);
						broadCasting(getData);
						// 검사는 상대방 클라이언트에서 한다.
						break;
					case gameData.blackGo: // 상대방이 올리는 경우
						System.out.println(player1);
						broadCasting(getData);
						// 검사는 상대방 클라이언트에서 한다.
						break;

					case gameData.chatPerson: // 게임방내 채팅
						broadCasting(getData);
						// 검사는 상대방 클라이언트에서 한다.
						break;

					case gameData.winWhite: // player1 승리기록
						String idWinW = ((gameData) getData).getId();
						int winWhite = ((gameData) getData).getWin();
						account_tenshu act = new account_tenshu(idWinW, "", winWhite, 0);
						sm.updateAccountTenshu(act);

						break;
					case gameData.defeatWhite: // player2 패배기록
						String idDefeatW = ((gameData) getData).getId();
						int defeatWhite = ((gameData) getData).getDefeat();
						account_tenshu actDW = new account_tenshu(idDefeatW, "", 0, defeatWhite);
						sm.updateAccountTenshu(actDW);

						break;
					case gameData.winBlack: // player2 승리기록
						String idWinB = ((gameData) getData).getId();
						int winBlack = ((gameData) getData).getWin();
						account_tenshu actWB = new account_tenshu(idWinB, "", winBlack, 0);
						sm.updateAccountTenshu(actWB);

						break;
					case gameData.defeatBlack: // player1 패배기록
						String idDefeatB = ((gameData) getData).getId();
						int defeatBlack = ((gameData) getData).getDefeat();
						account_tenshu actDB = new account_tenshu(idDefeatB, "", 0, defeatBlack);
						sm.updateAccountTenshu(actDB);
						break;

					}
				} else if (getData instanceof accountData) { // 회원가입 부분
					switch (((accountData) getData).getState()) {

					case accountData.newAccount:// 새로운 계정 생성
						String id = ((accountData) getData).getId();
						String pw = ((accountData) getData).getPw();
						ac = new account(id, pw);
						boolean accountInduceGet = sm.addAccount(ac);
						// truy false
						((accountData) getData).setFlag(accountInduceGet);
						output.writeObject(getData);
						output.reset();
						break;
					case accountData.modifiyAccount:// 계정수정
						String idModify = ((accountData) getData).getId();
						String pwModify = ((accountData) getData).getPw();
						boolean accountModifyGet = sm.updateAccount(idModify, pwModify);

						((accountData) getData).setFlag(accountModifyGet);
						output.writeObject(getData);
						output.reset();
						break;
					case accountData.deleteAccount:// 계정 삭제
						boolean accountDeleteGettenshu = false; // table2
						boolean accountDeleteGet = false;
						String idDelete = ((accountData) getData).getId();
						String pwDelete = ((accountData) getData).getPw();
						ac = sm.searchAccount(idDelete, pwDelete);
						if (ac != null) { // DB에 값이 있다면
							accountDeleteGettenshu = sm.deleteAccountTenshu(idDelete);
							accountDeleteGet = sm.deleteAccount(idDelete, pwDelete);
						} else {
						}
						((accountData) getData).setFlag(accountDeleteGet);
						output.writeObject(getData);
						output.reset();
						break;

					}
				}

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			threadList.remove(output);
			System.out.println("ClassNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			threadList.remove(output);
			System.out.println("IOException");
			flag = false;
		}
	}

	// connectList
	public void broadCasting(Object data) {
		System.out.println("브로드캐스팅 : 클라이언트 수 : " + threadList.size());
		for (ServerThread thread : threadList) {
			try {
				thread.output.writeObject(data);
				// thread.output.writeUnshared(data);
				thread.output.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
