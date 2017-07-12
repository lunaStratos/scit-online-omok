package client;

import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import vo.accountData;
import vo.account_tenshu;
import vo.gameData;
import vo.loginData;
import vo.makeRoom;
import vo.roomData;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainGUI extends JFrame implements ActionListener, Runnable {
	CardLayout card = new CardLayout(); // 카드레이아웃 사용을 위해
	private String id; // 내 고유 아이디 값 (2중 protocol)
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private static Socket socket;
	private Thread th;
	private JList gameRoom;
	private clientManager mg;
	private boolean b;
	private ArrayList<makeRoom> makeRoomGet;
	private JPanel mainPanel;
	private JPanel loginPanel;
	private JButton loginButton;
	private JButton newsButton;
	private JButton NewAccountLoginButton;
	private JLabel pwLabel;
	private JTextField IDField;
	private JTextField pwField;
	private JLabel ID_Label;
	private JLabel loginStatusLabel;
	private JPanel roomGUIPanel;
	private JTextField chatTextField;
	private JScrollPane scrollPane;
	private JLabel TodaysMessageLabel;
	private JScrollPane chatScroll;
	private JScrollPane roomListScroll;
	private JButton NewRoom;
	private JTextField roomTitle;
	private JButton MakeRoom;
	private JButton cancel;
	private JTextArea textArea;
	private JList nowList;
	private JPanel battleNetPanel;
	private JTextField BattelChat;
	private JButton GamereadyButton;
	private JButton OutRoom;
	private JScrollPane charTextAreaScroll;
	private JPanel playerPanel;
	private JLabel player1pLabel;
	private String player1;
	private String player2;
	private JTextArea charTextArea;
	private JMenuItem CaptureMenu;
	private JMenuItem exitMenu;
	private JFXPanel AD_Panel_2;
	// newAccount panel
	private JTextField passWordField;
	private JTextField pwIDField;
	private JButton newAccountButton;
	private JButton newAccountCancelButton;
	private ImageIcon backgroundLoginPanel;
	JButton modifyButton;
	JButton deleteButton;
	// 여기서 부터는 battlePanel
	private ImageIcon whiteStone;
	private ImageIcon blackStone;
	private ImageIcon refreshStone;
	private JLabel whiteStoneOn;
	private JLabel blackStoneOn;
	private JLabel refreshStoneOn;
	private JLabel baookpans;
	private JLabel nowTime;
	private Image stoneImage[]; // 현재는 바둑판 그림에서만 사용중
	private boolean doolSelect; // 바둑돌 검은색 하얀색 번갈아서 하얀색이 true 검은색이 false
	private int stoneX;
	private int stoneY;
	private int intXInt = 37;
	private int intYInt = 37;
	private int count;
	private int omokTable[][]; // 오목의 메인 바둑돌 int 2차원 배열 // 중요함.
	private ImageIcon whiteDool;
	private ImageIcon blackDool;
	private boolean gameStart = false; // 둘다 레디면 게임시작 true
	private boolean gameplayerSelect = true; // 지금 해야 할 플레이어 true일시 사용.
	private int gameWin; // 게임중은 0, 하얀돌이 이기면 1, 검은돌이 이기면 2
	private JPanel newAccountPanel;
	private JMenuItem MakePersonMenuPop;
	private ArrayList<String> todayArrayList;
	private int winme;// 내 승리수
	private int defeatme; // 내 패배수
	private int winOppo; // 상대방 승리수
	private int defeatOppo; // 상대방 패배수
	private ArrayList<account_tenshu> FirstArray;

	public MainGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ==============소켓연결===============
		conn();
		// ==============메니져에서 가져오는 스레드 연결===============
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu MenuPOP = new JMenu("Menu");
		menuBar.add(MenuPOP);

		CaptureMenu = new JMenuItem("Capture");
		MenuPOP.add(CaptureMenu);

		MakePersonMenuPop = new JMenuItem("\uB9CC\uB4E0\uC0AC\uB78C\uB4E4");
		MenuPOP.add(MakePersonMenuPop);

		exitMenu = new JMenuItem("Exit");
		MenuPOP.add(exitMenu);
		// ==============메니져에서 가져오는 스레드 연결===============
		getContentPane().setLayout(null);
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 1014, 799);
		getContentPane().add(mainPanel);
		mainPanel.setLayout(card);
		// ==============로그인 패널 카드 초기에 실행 ===============
		stoneImage = ImageUtils.loadImages();// 1. 바둑판 2. 바둑돌 화이트 3. 검은돌
		// ==============이미지 패널 초기에 실행 ===============

		loginPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[3], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		// image
		ImageIcon loginButtonImage = new ImageIcon("src/images/login.jpg");
		ImageIcon registerButtonImage = new ImageIcon("src/images/register.jpg");
		ImageIcon newsButtonImage = new ImageIcon("src/images/news.jpg");
		// image
		loginPanel.setLayout(null);

		newsButton = new JButton(newsButtonImage);
		newsButton.setBounds(398, 688, 150, 46);
		loginPanel.add(newsButton);

		loginButton = new JButton(loginButtonImage);
		loginButton.setBounds(236, 688, 150, 46);
		loginPanel.add(loginButton);

		NewAccountLoginButton = new JButton(registerButtonImage);
		NewAccountLoginButton.setBounds(74, 688, 150, 46);
		loginPanel.add(NewAccountLoginButton);

		pwLabel = new JLabel("Password");
		pwLabel.setBounds(166, 660, 57, 15);
		loginPanel.add(pwLabel);

		IDField = new JTextField();
		IDField.setColumns(10);
		IDField.setBounds(251, 626, 116, 21);
		loginPanel.add(IDField);

		pwField = new JTextField();
		pwField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginSend();
				}
			}
		});
		pwField.setColumns(10);
		pwField.setBounds(251, 657, 116, 21);
		loginPanel.add(pwField);

		ID_Label = new JLabel("ID");
		ID_Label.setBounds(166, 632, 57, 15);
		loginPanel.add(ID_Label);

		loginStatusLabel = new JLabel(
				"\uC544\uC774\uB514\uC640 \uC554\uD638\uB97C \uC785\uB825\uD574 \uC8FC\uC138\uC694");
		loginStatusLabel.setBounds(196, 601, 201, 15);
		loginPanel.add(loginStatusLabel);

		// ==============2. 대기실 패널 카드 ===============
		roomGUIPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[4], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		roomGUIPanel.setLayout(null);

		chatTextField = new JTextField();
		chatTextField.setColumns(10);
		chatTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // 채팅창 엔터 눌렀을때
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chat();
				}

			}
		});
		chatTextField.setBounds(32, 674, 697, 21);
		chatTextField.setBorder(new EmptyBorder(0, 0, 0, 0));
		roomGUIPanel.add(chatTextField);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(669, 74, 311, 236);
		roomGUIPanel.add(scrollPane);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		// new EmptyBorder(5,5,5,5)

		nowList = new JList();
		scrollPane.setViewportView(nowList);

		chatScroll = new JScrollPane();
		chatScroll.setBounds(32, 426, 590, 211);
		roomGUIPanel.add(chatScroll);
		chatScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		textArea = new JTextArea();
		textArea.setEditable(false);
		chatScroll.setViewportView(textArea);

		roomListScroll = new JScrollPane();
		roomListScroll.setBounds(32, 72, 582, 249);
		roomGUIPanel.add(roomListScroll);

		roomListScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		gameRoom = new JList();
		gameRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("마우스 찍찍");
				if (e.getComponent() == gameRoom) { // 선택된 값
					if (e.getClickCount() == 2) { // 더블클릭을 할때
						int temp = gameRoom.getSelectedIndex();
						System.out.println(temp);
						if (makeRoomGet.get(temp).getInwon() == 1) {
							// 인원이 1이면 들어갈수 있다.
							roomData dataIn = new roomData(roomData.roomIn, id, makeRoomGet.get(temp).getMakeName());
							// makeRoomGet은 현재 방 리스트를 가진 array
							// 방들어검, 내 아이디, 방을 만든 아이디
							mg.send(dataIn);
							player1pLabel.setText(id + "의 전적:: " + winme + "승 | " + defeatme + "패");
							card.show(mainPanel, "battleNetPanel");

						} else if (makeRoomGet.get(temp).getInwon() == 2) {
							int question = JOptionPane.showConfirmDialog(null, "인원이 다 차서 들어갈 수 없습니다.");

						}

					}
				}
			}
		});
		roomListScroll.setViewportView(gameRoom);

		AD_Panel_2 = new JFXPanel();
		AD_Panel_2.setBounds(691, 725, 320, 50);
		roomGUIPanel.add(AD_Panel_2);

		// ==============3. 배틀넷 패널 실행 ===============

		battleNetPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[5], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		battleNetPanel.setLayout(null);
		BattelChat = new JTextField();
		BattelChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chatGame();
				}
			}
		});

		BattelChat.setColumns(10);
		BattelChat.setBounds(768, 585, 225, 21);
		battleNetPanel.add(BattelChat);
		BattelChat.setBorder(new EmptyBorder(0, 0, 0, 0));

		ImageIcon GamereadyButtonImage = new ImageIcon("src/images/ready.jpg");
		GamereadyButton = new JButton(GamereadyButtonImage);
		GamereadyButton.setBounds(844, 642, 144, 105);
		battleNetPanel.add(GamereadyButton);

		ImageIcon outroomImage = new ImageIcon("src/images/out.jpg");
		OutRoom = new JButton(outroomImage);
		OutRoom.setBounds(754, 642, 78, 105);
		battleNetPanel.add(OutRoom);

		charTextAreaScroll = new JScrollPane();
		charTextAreaScroll.setBounds(768, 217, 225, 334);
		battleNetPanel.add(charTextAreaScroll);
		charTextAreaScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

		charTextArea = new JTextArea();
		charTextArea.setEditable(false);
		charTextAreaScroll.setViewportView(charTextArea);

		playerPanel = new JPanel();
		playerPanel.setBounds(768, 57, 230, 63);
		battleNetPanel.add(playerPanel);
		playerPanel.setLayout(new GridLayout(1, 2, 0, 0));
		playerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		playerPanel.setBackground(Color.white);

		player1pLabel = new JLabel("1P");
		playerPanel.add(player1pLabel);
		player1pLabel.setBackground(Color.white);

		baookpans = new JLabel(); // 바둑판이 올려지는 라벨
		baookpans.setBounds(12, 10, 718, 718);
		battleNetPanel.add(baookpans);

		ImageIcon badookpan = new ImageIcon(stoneImage[0]);// 바둑판
		baookpans.setIcon(badookpan); // 바둑판 그림 라벨에 붙이기
		whiteDool = new ImageIcon(stoneImage[1]);// 하얀돌 이미지
		blackDool = new ImageIcon(stoneImage[2]);// 검은돌 이미지

		omokTable = new int[23][23]; // 0~19 0~19 바둑판 배열 (소켓으로 보낼 것)]

		// 0. 회원가입 모뮬============================
		newAccountPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(stoneImage[6], 0, 0, 1020, 805, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		newAccountPanel.setLayout(null);
		JLabel AccountIDLabel = new JLabel("id");
		AccountIDLabel.setBounds(399, 631, 57, 15);
		newAccountPanel.add(AccountIDLabel);

		JLabel AccountPwLabel = new JLabel("Password");
		AccountPwLabel.setBounds(399, 670, 57, 15);
		newAccountPanel.add(AccountPwLabel);

		passWordField = new JTextField();
		passWordField.setBounds(479, 667, 116, 21);
		newAccountPanel.add(passWordField);
		passWordField.setColumns(10);

		pwIDField = new JTextField();
		pwIDField.setBounds(479, 628, 116, 21);
		newAccountPanel.add(pwIDField);
		pwIDField.setColumns(10);
		// 계정 버튼 이미지들
		ImageIcon newAccountCancelButtonImage = new ImageIcon("src/images/accountCancel.jpg");
		ImageIcon newAccountButtonImage = new ImageIcon("src/images/accountNew.jpg");
		ImageIcon modifyButtonImage = new ImageIcon("src/images/accounyModify.jpg");
		ImageIcon deleteButtonImage = new ImageIcon("src/images/accountDelete.jpg");
		//
		newAccountCancelButton = new JButton(newAccountCancelButtonImage); // 취소버튼
		newAccountCancelButton.setBounds(242, 701, 145, 44);
		newAccountPanel.add(newAccountCancelButton);

		newAccountButton = new JButton(newAccountButtonImage); // 새계정 버튼
		newAccountButton.setBounds(386, 701, 145, 44);
		newAccountPanel.add(newAccountButton);

		JScrollPane yakguan = new JScrollPane();
		yakguan.setBounds(31, 77, 949, 492);
		newAccountPanel.add(yakguan);

		JTextArea newAccountTextArea = new JTextArea();
		newAccountTextArea.setText(
				"\uCD1D\uCE59\r\n\r\n \r\n\r\n\uC81C 1 \uC7A5 \uCD1D\uCE59\r\n\r\n \r\n\r\n\uC81C 1 \uC870 (\uBAA9 \uC801)\r\n  \uC774 \uC57D\uAD00\uC740 (\uC8FC)\uC6B0\uC8FC\uC758 \uAE30\uC6B4 ( \uC774\uD558 \"\uD68C\uC0AC\"\uB77C \uD569\uB2C8\uB2E4.)\uC774 \uC81C\uACF5\uD558\uB294 \uC815\uBCF4\uC11C\uBE44\uC2A4( \uC774\uD558 \"\uC11C\uBE44\uC2A4\"\uB77C \uD55C\uB2E4)\uC758 \r\n  \uC774\uC6A9\uC870\uAC74 \uBC0F \uC808\uCC28\uC5D0 \uAD00\uD55C \uC0AC\uD56D\uC744 \uADDC\uC815\uD568\uC744 \uBAA9\uC801\uC73C\uB85C \uD569\uB2C8\uB2E4. \uC81C 2 \uC870 (\uC57D\uAD00\uC758 \uD6A8\uB825 \uBC0F \uBCC0\uACBD)\r\n\u2460 \uC774 \uC57D\uAD00\uC758 \uB0B4\uC6A9\uC740 \uC11C\uBE44\uC2A4 \uD654\uBA74\uC5D0 \uAC8C\uC2DC\uD558\uAC70\uB098 \uAE30\uD0C0\uC758 \uBC29\uBC95\uC73C\uB85C \uD68C\uC6D0\uC5D0\uAC8C \uACF5\uC9C0\uD568\uC73C\uB85C\uC368 \uD6A8\uB825\uC774 \r\n    \uBC1C\uC0DD\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uD569\uB9AC\uC801\uC778 \uC0AC\uC720\uAC00 \uBC1C\uC0DD\uB420 \uACBD\uC6B0\uC5D0\uB294 \uC774 \uC57D\uAD00\uC744 \uBCC0\uACBD\uD560 \uC218 \uC788\uC73C\uBA70, \uC57D\uAD00\uC774 \uBCC0\uACBD\uB41C \uACBD\uC6B0\uC5D0\uB294 \r\n    \uCD5C\uC18C\uD55C 7\uC77C\uC804\uC5D0 \u2460\uD56D\uACFC \uAC19\uC740 \uBC29\uBC95\uC73C\uB85C \uACF5\uC2DC\uD569\uB2C8\uB2E4. \uC81C 3 \uC870 (\uC57D\uAD00 \uC678 \uC900\uCE59)\r\n  \uC774 \uC57D\uAD00\uC5D0 \uBA85\uC2DC\uB418\uC9C0 \uC54A\uC740 \uC0AC\uD56D\uC740 \uC804\uAE30\uD1B5\uC2E0\uAE30\uBCF8\uBC95, \uC804\uAE30\uD1B5\uC2E0\uC0AC\uC5C5\uBC95 \uBC0F \uAE30\uD0C0 \uAD00\uB828\uBC95\uB839\uC758 \uADDC\uC815\uC5D0 \r\n  \uB530\uB985\uB2C8\uB2E4. \uC81C 4 \uC870(\uC6A9\uC5B4\uC758 \uC815\uC758)\r\n  \uC774 \uC57D\uAD00\uC5D0\uC11C \uC0AC\uC6A9\uD558\uB294 \uC6A9\uC5B4\uC758 \uC815\uC758\uB294 \uB2E4\uC74C\uACFC \uAC19\uC2B5\uB2C8\uB2E4.\r\n    \u2460 \uD68C\uC6D0.\uD68C\uC6D0\uC0AC : \uD68C\uC0AC\uC640 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uACC4\uC57D\uC744 \uCCB4\uACB0\uD55C \uAC1C\uC778\uC774\uB098 \uBC95\uC778 \uB610\uB294 \uBC95\uC778\uC5D0 \uC900\uD558\uB294 \uB2E8\uCCB4.\r\n\r\n    \u2461 \uC6B4\uC601\uC790 : \uC11C\uBE44\uC2A4\uC758 \uC804\uBC18\uC801\uC778 \uAD00\uB9AC\uC640 \uC6D0\uD65C\uD55C \uC6B4\uC601\uC744 \uC704\uD558\uC5EC \uD68C\uC0AC\uAC00 \uC120\uC815\uD55C \uC0AC\uB78C.\r\n\r\n    \u2462 \uC544\uC774\uB514(ID): \uD68C\uC6D0\uC2DD\uBCC4\uACFC \uD68C\uC6D0\uC758 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC744 \uC704\uD558\uC5EC \uD68C\uC6D0 \uAC00\uC785 \uC2DC \uC0AC\uC6A9\uD558\uB294 \uBB38\uC790\uC640 \r\n    \uC22B\uC790\uC758 \uC870\uD569.\r\n\r\n    \u2463 \uBE44\uBC00\uBC88\uD638 : \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC758 \uBE44\uBC00 \uBCF4\uD638\uB97C \uC704\uD574 \uD68C\uC6D0 \uC790\uC2E0\uC774 \uC124\uC815\uD55C \uBB38\uC790\uC640 \uC22B\uC790\uC758 \uC870\uD569.\r\n\r\n    \u2464 \uC11C\uBE44\uC2A4\uC911\uC9C0: \uC815\uC0C1\uC774\uC6A9 \uC911 \uD68C\uC0AC\uAC00 \uC815\uD55C \uC77C\uC815\uD55C \uC694\uAC74\uC5D0 \uB530\uB77C \uC77C\uC815\uAE30\uAC04\uB3D9\uC548 \uC11C\uBE44\uC2A4\uC758 \uC81C\uACF5\uC744 \r\n    \uC911\uC9C0\uD558\uB294\uAC83.\r\n\r\n    \u2465 \uD574\uC9C0 : \uD68C\uC0AC \uB610\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4 \uAC1C\uD1B5 \uD6C4 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC57D\uD558\uB294 \uAC83.\r\n\r\n    \u2466 \uBE45\uC7A5 \uD68C\uC6D0 : \uD68C\uC6D0(\uC774\uD558 \"\uD68C\uC6D0\uC0AC\"\uB77C \uCE6D\uD568)\uC774 \uD55C\uB2EC\uC5D0 \uC77C\uC815\uAE08\uC561\uC744 \uB0B4\uACE0 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uB294 \r\n    \uAC1C\uC778\uC774\uB098 \uB2E8\uCCB4.\r\n\r\n    \u2467 \uC804\uC0AC\uB3C5 \uD68C\uC6D0 : \uBB34\uB8CC \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uB294 \uAC1C\uC778\uC774\uB098 \uB2E8\uCCB4.\r\n \r\n\r\n \r\n\uC774\uC6A9\uC57D\uAD00 \uCCB4\uACB0\r\n \t\r\n\uC81C 2\uC7A5 \uC774\uC6A9\uACC4\uC57D \uCCB4\uACB0 \uC81C 5 \uC870 ( \uC11C\uBE44\uC2A4\uC758 \uAD6C\uBD84)\r\n\u2460 \uD68C\uC0AC\uAC00 \uD68C\uC6D0\uC5D0\uAC8C \uC81C\uACF5\uD558\uB294 \uC11C\uBE44\uC2A4\uB294 \uBB34\uB8CC\uC11C\uBE44\uC2A4\uC640 \uC720\uB8CC\uC11C\uBE44\uC2A4 \uB4F1\uC73C\uB85C \uAD6C\uBD84\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uC11C\uBE44\uC2A4\uC758 \uC885\uB958\uC640 \uB0B4\uC6A9 \uB4F1\uC740 \uD68C\uC0AC\uAC00 \uACF5\uC9C0\uB098 \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC548\uB0B4\uC5D0\uC11C \uBCC4\uB3C4\uB85C \uC815\uD558\uB294 \uBC14\uC5D0 \uC758\uD569\uB2C8\uB2E4.\r\n\r\n\uC81C 6 \uC870( \uC774\uC6A9\uACC4\uC57D\uC758 \uC131\uB9BD)\r\n\u2460 \uC544\uB798 \" \uC704\uC758 \uC774\uC6A9\uC57D\uAD00\uC5D0 \uB3D9\uC758\uD558\uC2ED\uB2C8\uAE4C? \" \uB77C\uB294 \uBB3C\uC74C\uC5D0 \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC774 \"\uB3D9\uC758\" \uBC84\uD2BC\uC744 \uB204\uB974\uBA74 \uC774 \r\n    \uC57D\uAD00\uC5D0 \uB3D9\uC758\uD558\uB294 \uAC83\uC73C\uB85C \uAC04\uC8FC\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0(\uD68C\uC6D0\uC0AC)\uC774 \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD558\uC5EC \uD68C\uC0AC\uAC00 \uC2B9\uB099\uD568\uC73C\uB85C\uC368 \uC131\uB9BD\uD569\uB2C8\uB2E4. \uD68C\uC0AC\uB294 \uC2E0\uCCAD\uC77C\r\n    24\uC2DC\uAC04 \uC774\uB0B4\uC5D0 \uC774\uC6A9 \uC2B9\uB099\uC758 \uC758\uC0AC\uB97C \uC774\uC6A9 \uC2E0\uCCAD \uC2DC \uAE30\uC7AC\uD55C e-mail\uC744 \uD1B5\uD558\uC5EC \uC774\uC6A9 \uC2E0\uCCAD\uC790\uC5D0\uAC8C \r\n    \uD1B5\uC9C0\uD569\uB2C8\uB2E4. \uC81C 7 \uC870( \uC774\uC6A9 \uC2E0\uCCAD \uBC0F \uC2B9\uB099)\r\n\u2460 \uC774\uC6A9\uC2E0\uCCAD\uC740 \uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9\uC790\uB4F1\uB85D\uC5D0\uC11C \uB2E4\uC74C\uC0AC\uD56D\uC744 \uAC00\uC785\uC2E0\uCCAD \uC591\uC2DD\uC5D0 \uAE30\uB85D\uD558\uC5EC \uC2E0\uCCAD\uD569\uB2C8\uB2E4. \r\n    \u24D0 \uC774\uB984\r\n    \u24D1 e-mail\r\n    \u24D2 \uC544\uC774\uB514,\uBE44\uBC00\uBC88\uD638\r\n    \u24D3 \uC8FC\uBBFC\uB4F1\uB85D\uBC88\uD638\r\n    \u24D4 \uC8FC\uC18C\r\n    \u24D5 \uC804\uD654\uBC88\uD638 \r\n    \u24D6 \uD68C\uC6D0\uB4F1\uAE09\r\n    \u24D7 \uAC1C\uC778.\uD68C\uC6D0\uC0AC\r\n    \u24D8 \uBE45\uC7A5 \uAD6C\uC0AC \uC5EC\uBD80\r\n\r\n\u2461 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0\uC758 \uC774\uC6A9\uC790\uB4F1\uB85D\uC5D0 \uB300\uD558\uC5EC \uD68C\uC0AC\uC758 \uC774\uC6A9\uC2B9\uB099\uC73C\uB85C \uC131\uB9BD\uD55C\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uB2E4\uC74C\uACFC \uAC19\uC740 \uC0AC\uC720\uAC00 \uBC1C\uC0DD\uD55C \uACBD\uC6B0 \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD55C \uC2B9\uB099\uC744 \uAC70\uBD80\uD569\uB2C8\uB2E4.\r\n    \u24D0\uBD88\uAC74\uC804\uD55C \uC74C\uB780\uBB3C\uC774\uB098 \uBD88\uBC95\uAC70\uB798, \uB300\uD55C\uBBFC\uAD6D \uBC95\uB960\uC5D0 \uC704\uBC18\uB418\uB294 \uB0B4\uC6A9\uC744 \uAE30\uC7AC\uD55C\uC790.\r\n    \u24D1\uAE30\uD0C0 \uC704\uBC95\uD55C \uC774\uC6A9\uC2E0\uCCAD\uC784\uC774 \uD655\uC778\uB41C \uACBD\uC6B0 \uC81C 8 \uC870( \uC774\uC6A9\uACC4\uC57D \uC0AC\uD56D\uC758 \uBCC0\uACBD) \r\n\u2460 \uD68C\uC6D0\uC740 \uC774\uC6A9 \uC2E0\uCCAD\uC2DC \uAE30\uC7AC\uD55C \uC0AC\uD56D\uC774 \uBCC0\uACBD\uB418\uC5C8\uC744 \uACBD\uC6B0\uC5D0\uB294 \uC6B4\uC601\uC790\uC5D0\uAC8C \uC218\uC815\uC694\uCCAD\uC744 \uD574\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\uC81C 9 \uC870( \uC774\uC6A9\uACC4\uC57D\uC758 \uC885\uB8CC )\r\n\u2460 \uC774\uC6A9\uACC4\uC57D\uC740 \uD68C\uC6D0 \uB610\uB294 \uD68C\uC0AC\uC758 \uD574\uC9C0\uC5D0 \uC758\uD558\uC5EC \uC989\uC2DC \uBF08\uC640 \uC0B4\uC774 \uBD84\uB9AC\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC6D0\uC740 \uD574\uC9C0\uC758\uC0AC \uBC1C\uC0DD \uC2DC \uC989\uC2DC \uD68C\uC0AC\uC5D0 e-mail\uC744 \uD1B5\uD55C \uD574\uC9C0 \uC2E0\uCCAD\uC744 \uD569\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uB2E4\uC74C\uACFC \uAC19\uC740 \uC0AC\uC720 \uBC1C\uC0DD\uC2DC \uC774\uC6A9\uC2E0\uCCAD\uC5D0 \uB300\uD55C \uBE45\uC7A5\uC744 \uAD6C\uC0AC \uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n    \u24D0 \uBD88\uAC74\uD55C \uB0B4\uC6A9\uC744 \uC720\uD3EC\uD558\uAC70\uB098 \uD68C\uC0AC\uC5D0 \uC911\uB300\uD55C \uC190\uD574\uB97C \uB07C\uCE5C \uAC1C\uC778\uC774\uB098 \uD68C\uC6D0\uC0AC.\r\n    \u24D1 \uD14C\uB9C8 \uC1FC\uD551\uC5D0 \uBD88\uAC74\uC804\uD55C \uB0B4\uC6A9\uC744 \uB2F4\uACE0 \uC788\uAC70\uB098 \uBD88\uBC95\uAC70\uB798 \uB4F1\uC744 \uC704\uD55C \uBAA9\uC801\uC73C\uB85C \uC6B4\uC601\uB420 \uACBD\uC6B0\r\n    \u24D2 \uAE30\uD0C0 \uD68C\uC0AC\uC758 \uD569\uB9AC\uC801\uC778 \uD310\uB2E8\uC5D0 \uC758\uD558\uC5EC \uC8FC\uC720\uC18C \uD68C\uC6D0<\uD68C\uC6D0\uC0AC>\uC73C\uB85C \uBD80\uC801\uD569\uD558\uB2E4\uACE0 \uD310\uB2E8\uB420 \uACBD\uC6B0 \r\n \t \r\n\uC11C\uBE44\uC2A4 \uC774\uC6A9\r\n \t\r\n\uC81C 3 \uC7A5 \uC11C\uBE44\uC2A4 \uC774\uC6A9\r\n\r\n\uC81C 10 \uC870 (\uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9 \uAC1C\uC2DC)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC758 \uC774\uC6A9\uC2E0\uCCAD\uC744 \uC2B9\uB099\uD55C \uB54C\uBD80\uD130 \uC989\uC2DC \uC11C\uBE44\uC2A4\uB97C \uAC1C\uC2DC\uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uC758 \uC5C5\uBB34\uC0C1 \uB610\uB294 \uAE30\uC220\uC0C1\uC758 \uC7A5\uC560\uB85C \uC778\uD558\uC5EC \uC11C\uBE44\uC2A4\uB97C \uAC1C\uC2DC\uD558\uC9C0 \uBABB\uD558\uB294 \uACBD\uC6B0\uC5D0\uB294 \uC11C\uBE44\uC2A4\uC5D0 \r\n    \uACF5\uC9C0\uD558\uAC70\uB098 \uD68C\uC6D0\uC5D0\uAC8C \uC989\uC2DC \uC774\uB97C \uD1B5\uC9C0\uD569\uB2C8\uB2E4. \uC81C 11 \uC7A5 ( \uC11C\uBE44\uC2A4\uC758 \uB0B4\uC6A9 )\r\n\r\n\u2460 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC758 \uC6B4\uC6A9\uACFC \uAD00\uB828\uD558\uC5EC \uC11C\uBE44\uC2A4 \uD654\uBA74, \uD648\uD398\uC774\uC9C0, \uC774\uBA54\uC77C \uB4F1\uC5D0 \uAD11\uACE0 \uB4F1\uC744 \uAC8C\uC7AC\uD560 \uC218 \r\n    \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uB97C \uC6B4\uC6A9\uD568\uC5D0 \uC788\uC5B4\uC11C \uAC01\uC885 \uC815\uBCF4\uB97C \uC11C\uBE44\uC2A4\uC5D0 \uAC8C\uC7AC\uD558\uB294 \uBC29\uBC95 \uB4F1\uC73C\uB85C \uD68C\uC6D0\uC5D0\uAC8C \uC81C\uACF5\uD560\r\n    \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uC81C 14\uC870 (\uAC8C\uC2DC\uBB3C \uB610\uB294 \uB0B4\uC6A9\uBB3C\uC758 \uC0AD\uC81C)\r\n\uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC758 \uAC8C\uC2DC\uBB3C \uB610\uB294 \uB0B4\uC6A9\uBB3C\uC774  \uADDC\uC815\uC5D0 \uC704\uBC18\uB418\uAC70\uB098 \uAC8C\uC2DC\uAE30\uAC04\uC744 \uCD08\uACFC\uD558\uB294 \uACBD\uC6B0 \uC0AC\uC804 \uD1B5\uC9C0\uB098 \uB3D9\uC758 \uC5C6\uC774 \uC774\uB97C \uC0AD\uC81C\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uC81C 15 \uC870 ( \uC11C\uBE44\uC2A4 \uC81C\uACF5\uC758 \uC911\uC9C0)\r\n\uD68C\uC0AC\uB294 \uC5B8\uC81C\uB4E0\uC9C0, \uADF8\uB9AC\uACE0 \uC218\uC2DC\uB85C \uBCF8 \uC11C\uBE44\uC2A4(\uD639\uC740 \uADF8 \uC77C\uBD80)\uB97C, \uD1B5\uC9C0\uD558\uAC70\uB098 \uB610\uB294 \uD1B5\uC9C0\uD568\uC774 \uC5C6\uC774, \uC77C\uC2DC \uB610\uB294 \uC601\uAD6C\uC801\uC73C\uB85C \uC218\uC815\uD558\uAC70\uB098 \uC911\uB2E8\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4. \uADC0\uD558\uB294 \uC11C\uBE44\uC2A4\uC758 \uC218\uC815, \uC911\uB2E8 \uB610\uB294 \uC911\uC9C0\uC5D0 \uB300\uD574 \uD68C\uC0AC\uAC00 \uADC0\uD558 \uB610\uB294 \uC81C 3\uC790\uC5D0 \uB300\uD558\uC5EC \uC5B4\uB5A0\uD55C \uCC45\uC784\uB3C4 \uC9C0\uC9C0 \uC54A\uC74C\uC5D0 \uB3D9\uC758\uD569\uB2C8\uB2E4. \r\n \t \r\n\uAD8C\uB9AC\uC640 \uC758\uBB34\r\n \t\r\n\uC81C 4 \uC7A5 \uAD8C\uB9AC\uC640 \uC758\uBB34 \uC81C 16 \uC870 ( \uD68C\uC0AC\uC758 \uC758\uBB34)\r\n\u2460 \uD68C\uC0AC\uB294 \uD2B9\uBCC4\uD55C \uC0AC\uC720\uAC00 \uC5C6\uB294 \uD55C \uC11C\uBE44\uC2A4 \uC81C\uACF5\uC124\uBE44\uB97C \uD56D\uC0C1 \uC6B4\uC6A9 \uAC00\uB2A5\uD55C \uC0C1\uD0DC\uB85C \uC720\uC9C0\uBCF4\uC218\uD558\uC5EC\uC57C\uD558\uBA70,\r\n    \uC548\uC815\uC801\uC73C\uB85C \uC11C\uBE44\uC2A4\uB97C \uC81C\uACF5\uD560 \uC218 \uC788\uB3C4\uB85D \uCD5C\uC120\uC758 \uB178\uB825\uC744 \uB2E4\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uAD00\uACC4 \uBC95\uB839\uC5D0 \uC758\uD558\uC5EC \uC218\uC0AC\uC0C1\uC758  \uBAA9\uC801\uC73C\uB85C \uAD00\uACC4\uAE30\uAD00\uC73C\uB85C\uBD80\uD130 \uC694\uAD6C\uBC1B\uC740 \uACBD\uC6B0, \uC815\uBCF4\uD1B5\uC2E0 \uC724\uB9AC\uC704\uC6D0\uD68C\uC758\r\n    \uC694\uCCAD\uC774 \uC788\uB294 \uACBD\uC6B0, \uAE30\uD0C0 \uAD00\uACC4\uBC95\uB839\uC5D0 \uC758\uD55C \uACBD\uC6B0 \uD68C\uC0AC\uB294 \uAC1C\uC778\uC815\uBCF4\uB97C \uC81C\uACF5\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD55C \uD68C\uC6D0\uC758 \uBD88\uB9CC\uC0AC\uD56D\uC774 \uC811\uC218\uB418\uB294 \uACBD\uC6B0 \uC774\uB97C \uC989\uC2DC \uCC98\uB9AC\uD558\uC5EC\uC57C \uD558\uBA70, \uC989\uC2DC \uCC98\r\n    \uB9AC\uAC00 \uACE4\uB780\uD55C \uACBD\uC6B0 \uADF8 \uC0AC\uC720\uC640 \uCC98\uB9AC\uC77C\uC815\uC744 \uC11C\uBE44\uC2A4 \uB610\uB294 \uC804\uC790\uC6B0\uD3B8\uC744 \uD1B5\uD558\uC5EC \uB3D9 \uD68C\uC6D0\uC5D0\uAC8C \uD1B5\uC9C0\uD558\uC5EC\uC57C\r\n    \uD569\uB2C8\uB2E4. \uC81C 17 \uC870 \uD68C\uC6D0\uC758 \uC758\uBB34\r\n\u2460 \uD68C\uC6D0\uC740 \uAD00\uACC4 \uBC95\uB839, \uBCF8 \uC57D\uAD00\uC758 \uADDC\uC815, \uC774\uC6A9\uC548\uB0B4 \uBC0F \uC11C\uBE44\uC2A4\uC0C1\uC5D0 \uACF5\uC9C0\uD55C \uC8FC\uC758\uC0AC\uD56D, \uD68C\uC0AC\uAC00 \uD1B5\uC9C0\uD558\uB294 \r\n    \uC0AC\uD56D\uC744 \uC900\uC218\uD558\uC5EC\uC57C \uD558\uBA70, \uAE30\uD0C0 \uD68C\uC0AC\uC758 \uC5C5\uBB34\uC5D0 \uBC29\uD574\uB418\uB294 \uD589\uC704\uB97C \uD558\uC5EC\uC11C\uB294 \uC544\uB2C8\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC6D0\uC740 \uD68C\uC0AC\uC758 \uC0AC\uC804 \uB3D9\uC758\uC5C6\uC774 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uC5B4\uB5A0\uD55C \uC601\uB9AC\uD589\uC704\uB3C4 \uD560 \uC218 \uC5C6\uC73C\uBA70, \uBC95\uC5D0 \uC800\uCD09\uB418\uB294\r\n    \uC790\uB8CC\uB97C \uBC30\uD3EC \uB610\uB294 \uAC8C\uC7AC\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC6D0\uC740 \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640  \uBE44\uBC00\uBC88\uD638\uB97C \uC720\uC9C0 \uAD00\uB9AC\uD560 \uCC45\uC784\uC774 \uC788\uC73C\uBA70 \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640 \uBE44\uBC00\uBC88\uD638\uB97C \r\n    \uC0AC\uC6A9\uD558\uC5EC \uBC1C\uC0DD\uD558\uB294 \uBAA8\uB4E0 \uACB0\uACFC\uC5D0 \uB300\uD574 \uC804\uC801\uC778 \uCC45\uC784\uC774 \uC788\uC2B5\uB2C8\uB2E4. \uB610\uD55C \uC790\uC2E0\uC758 \uC544\uC774\uB514\uC640 \uBE44\uBC00\uBC88\uD638\uAC00\r\n    \uC790\uC2E0\uC758 \uC2B9\uB099\uC5C6\uC774 \uC0AC\uC6A9\uB418\uC5C8\uC744 \uACBD\uC6B0 \uC989\uC2DC \uD68C\uC0AC\uC5D0  \uC2E0\uACE0\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2463 \uD68C\uC6D0\uC740 \uC774\uC6A9\uC2E0\uCCAD\uC2DC\uC758 \uAE30\uC7AC\uB0B4\uC6A9\uC5D0 \uB300\uD574 \uC9C4\uC2E4\uD558\uACE0 \uC815\uD655\uD558\uBA70 \uD604\uC7AC\uC758 \uC0AC\uC2E4\uACFC \uC77C\uCE58\uD558\uB3C4\uB85D \uC720\uC9C0\uD558\uACE0 \r\n    \uAC31\uC2E0\uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4. \r\n\r\n\u2464 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uC5BB\uC740 \uC815\uBCF4\uB97C \uD68C\uC0AC\uC758 \uC0AC\uC804 \uC2B9\uB099\uC5C6\uC774 \uBCF5\uC0AC, \uBCF5\uC81C, \uBCC0\uACBD, \uBC88\uC5ED, \uCD9C\uD310, \uBC29\uC1A1\r\n    \uAE30\uD0C0\uC758 \uBC29\uBC95\uC73C\uB85C \uC0AC\uC6A9\uD558\uAC70\uB098 \uC774\uB97C \uD0C0\uC778\uC5D0\uAC8C \uC81C\uACF5\uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2465 \uD68C\uC6D0\uC740 \uC74C\uB780\uBB3C \uAC8C\uC7AC, \uC720\uD3EC\uB4F1 \uC0AC\uD68C\uC9C8\uC11C\uB97C \uD574\uCE58\uB294 \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2466 \uD68C\uC6D0\uC740 \uD0C0\uC778\uC758 \uBA85\uC608\uB97C \uD6FC\uC190\uD558\uAC70\uB098 \uBAA8\uC695\uD558\uB294 \uD589\uC704\uC640 \uD0C0\uC778\uC758 \uC9C0\uC801\uC7AC\uC0B0\uAD8C \uB4F1\uC758 \uAD8C\uB9AC\uB97C \uCE68\uD574\uD558\uB294 \r\n    \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2467 \uD68C\uC6D0\uC740 \uD574\uD0B9 \uB610\uB294 \uCEF4\uD4E8\uD130 \uBC14\uC774\uB7EC\uC2A4\uB97C \uC720\uD3EC\uD558\uB294 \uC77C, \uD0C0\uC778\uC758 \uC758\uC0AC\uC5D0 \uBC18\uD558\uC5EC \uAD11\uACE0\uC131 \uC815\uBCF4\uB4F1 \uC77C\uC815\uD55C \r\n    \uB0B4\uC6A9\uC744 \uC9C0\uC18D\uC801\uC73C\uB85C \uC804\uC1A1\uD558\uB294 \uD589\uC704\uB97C \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2468 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC758 \uC6B4\uC601\uC5D0 \uC9C0\uC7A5\uC744 \uC8FC\uAC70\uB098 \uC904 \uC6B0\uB824\uAC00 \uC788\uB294 \uC77C\uCCB4\uC758 \uD589\uC704, \uAE30\uD0C0 \uAD00\uACC4 \uBC95\uB839\uC5D0 \uC704\uBC30\uB418\uB294\r\n    \uC77C\uC744 \uD560 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4. \uC81C 18 \uC870 (\uC591\uB3C4 \uAE08\uC9C0)\r\n  \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC758 \uC774\uC6A9\uAD8C\uD55C, \uAE30\uD0C0 \uC774\uC6A9\uACC4\uC57D\uC0C1 \uC9C0\uC704\uB97C \uD0C0\uC778\uC5D0\uAC8C \uC591\uB3C4, \uC99D\uC5EC\uD560 \uC218 \uC5C6\uC73C\uBA70, \uAC8C\uC2DC\uBB3C\uC5D0 \r\n  \uB300\uD55C \uC800\uC791\uAD8C\uC744 \uD3EC\uD568\uD55C \uBAA8\uB4E0 \uAD8C\uB9AC \uBC0F \uCC45\uC784\uC740 \uC774\uB97C \uAC8C\uC2DC\uD55C \uD68C\uC6D0\uC5D0\uAC8C \uC788\uC2B5\uB2C8\uB2E4.\r\n\uC81C 19 \uC870 ( \uACC4\uC57D\uD574\uC9C0 \uBC0F \uC774\uC6A9\uC81C\uD55C)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC57D\uAD00\uC758 \uB0B4\uC6A9\uC744 \uC704\uBC18\uD558\uACE0, \uD68C\uC0AC \uC18C\uC815\uC758 \uAE30\uAC04 \uC774\uB0B4\uC5D0 \uC774\uB97C \uD574\uC18C\uD558\uC9C0 \uC544\uB2C8\uD558\uB294 \uACBD\uC6B0 \r\n    \uC11C\uBE44\uC2A4 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC9C0\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uC81C \u2460\uD56D\uC5D0 \uC758\uD574 \uD574\uC9C0\uB41C \uD68C\uC6D0\uC774 \uB2E4\uC2DC \uC774\uC6A9\uC2E0\uCCAD\uC744 \uD558\uB294 \uACBD\uC6B0 \uC77C\uC815\uAE30\uAC04 \uADF8 \uC2B9\uB099\uC744 \uC81C\uD55C\uD560 \uC218\r\n    \uC788\uC2B5\uB2C8\uB2E4. \r\n\r\n\u2462 \uD68C\uC6D0\uC774 \uC774\uC6A9\uACC4\uC57D\uC744 \uD574\uC9C0\uD558\uACE0\uC790 \uD558\uB294 \uB54C\uC5D0\uB294 \uD68C\uC6D0 \uBCF8\uC778\uC774 \uC11C\uBE44\uC2A4 \uB610\uB294 \uC804\uC790\uC6B0\uD3B8\uB97C \uD1B5\uD558\uC5EC \uD574\uC9C0\r\n    \uC2E0\uCCAD\uC744 \uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\uC81C 20 \uC870 (\uC190\uD574\uBC30\uC0C1)\r\n  \uD68C\uC0AC\uAC00 \uC81C\uACF5\uB418\uB294 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD558\uC5EC \uD68C\uC6D0\uC5D0\uAC8C \uC5B4\uB5A0\uD55C \uC190\uD574\uAC00 \uBC1C\uC0DD\uD558\uB354\uB77C\uB3C4 \uD68C\uC0AC\uC758 \uC911\uB300\uD55C \uACFC\uC2E4\uC5D0\r\n  \uC758\uD55C \uACBD\uC6B0\uB97C \uC81C\uC678\uD558\uACE0 \uC774\uC5D0 \uB300\uD558\uC5EC \uCC45\uC784\uC744 \uBD80\uB2F4\uD558\uC9C0 \uC544\uB2C8\uD569\uB2C8\uB2E4. \uB2E8, \uC190\uD574\uBC30\uC0C1\uC758 \uBC94\uC704\uB294 \uBCC4\uB3C4\uB85C \r\n  \uD68C\uC0AC\uC758 \uADDC\uC815\uC5D0 \uB530\uB985\uB2C8\uB2E4. \uC81C 21 \uC870 (\uBA74\uCC45 \uC870\uD56D)\r\n\u2460 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4\uC5D0 \uAC8C\uC7AC\uD55C \uC815\uBCF4, \uC790\uB8CC, \uC0AC\uC2E4\uC758 \uC815\uD655\uC131, \uC2E0\uB8B0\uC131\uB4F1  \uB0B4\uC6A9\uC5D0 \uAD00\uD558\uC5EC\uB294 \uC5B4\uB5A0\uD55C \r\n    \uCC45\uC784\uB3C4  \uBD80\uB2F4\uD558\uC9C0 \uC544\uB2C8\uD558\uACE0 \uC11C\uBE44\uC2A4 \uC790\uB8CC\uC5D0 \uB300\uD55C \uCDE8\uC0AC\uC120\uD0DD \uB610\uB294 \uC774\uC6A9\uC73C\uB85C \uBC1C\uC0DD\uD558\uB294 \uC190\uD574\uB4F1\uC5D0 \uB300\r\n    \uD574\uC11C\uB294 \uCC45\uC784\uC774 \uBA74\uC81C\uB429\uB2C8\uB2E4.\r\n\r\n\u2461 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC774 \uC11C\uBE44\uC2A4\uB97C \uC774\uC6A9\uD558\uC5EC \uAE30\uB300\uD558\uB294 \uC190\uC775\uC774\uB098 \uC11C\uBE44\uC2A4\uB97C \uD1B5\uD558\uC5EC \uC5BB\uC740 \uC790\uB8CC\uB85C \uC778\uD55C  \uC190\uD574\uC5D0\r\n    \uAD00\uD558\uC5EC \uCC45\uC784\uC774 \uBA74\uC81C\uB429\uB2C8\uB2E4.\r\n\r\n\u2462 \uD68C\uC0AC\uB294 \uD68C\uC6D0 \uC0C1\uD638\uAC04 \uB610\uB294 \uD68C\uC6D0\uACFC \uC81C 3\uC790 \uC0C1\uD638\uAC04\uC5D0 \uC11C\uBE44\uC2A4\uB97C \uB9E4\uAC1C\uB85C \uBC1C\uC0DD\uD55C \uBD84\uC7C1\uC5D0 \uB300\uD574\uC11C\uB294 \uAC1C\uC785\uD560\r\n    \uC758\uBB34\uAC00 \uC5C6\uC73C\uBA70 \uC774\uB85C \uC778\uD55C \uC190\uD574\uB97C \uBC30\uC0C1\uD560 \uCC45\uC784\uB3C4 \uC5C6\uC2B5\uB2C8\uB2E4.\r\n\r\n\u2463 \uD68C\uC0AC\uB294 \uD68C\uC6D0\uC758 \uADC0\uCC45\uC0AC\uC720\uB85C \uC778\uD558\uC5EC \uC11C\uBE44\uC2A4 \uC774\uC6A9\uC758 \uC7A5\uC560\uAC00 \uBC1C\uC0DD\uD55C \uACBD\uC6B0\uC5D0\uB294 \uCC45\uC784\uC774 \uBA74\uC81C \uB429\uB2C8\uB2E4.\r\n\r\n\u2464 \uD68C\uC0AC\uB294 \uC81C\uD734\uC0AC\uAC00 \uC81C\uACF5\uD558\uB294 \uBD80\uAC00\uC11C\uBE44\uC2A4\uC758 \uD488\uC9C8 \uBC0F \uC7A5\uC560\uC5D0 \uB300\uD55C \uCC45\uC784\uC740 \uBA74\uC81C \uB429\uB2C8\uB2E4.\r\n\r\n\u2465 \uBCF8 \uC57D\uAD00\uC758 \uADDC\uC815\uC744 \uC704\uBC18\uD568\uC73C\uB85C \uC778\uD558\uC5EC \uD68C\uC0AC\uC5D0 \uC190\uD574\uAC00 \uBC1C\uC0DD\uD558\uAC8C \uB418\uB294 \uACBD\uC6B0, \uC774 \uC57D\uAD00\uC744 \uC704\uBC18\uD55C \uD68C\uC6D0\uC740\r\n    \uD68C\uC0AC\uC5D0 \uBC1C\uC0DD\uD558\uB294 \uBAA8\uB4E0 \uC190\uD574\uB97C  \uBC30\uC0C1\uD558\uC5EC\uC57C \uD558\uBA70, \uB3D9 \uC190\uD574\uB85C\uBD80\uD130 \uD68C\uC0AC\uB97C \uBA74\uCC45\uC2DC\uCF1C\uC57C  \uD569\uB2C8\uB2E4. \uC81C 22 \uC870 ( \uBD84\uC7C1\uC758 \uD574\uACB0 )\r\n\u2460 \uD68C\uC0AC\uC640 \uD68C\uC6D0\uC740 \uC11C\uBE44\uC2A4\uC640 \uAD00\uB828\uD558\uC5EC \uBC1C\uC0DD\uD55C \uBD84\uC7C1\uC744 \uC6D0\uB9CC\uD558\uAC8C \uD574\uACB0\uD558\uAE30 \uC704\uD558\uC5EC \uD544\uC694\uD55C \uBAA8\uB4E0 \uB178\uB825\uC744\r\n    \uD558\uC5EC\uC57C \uD569\uB2C8\uB2E4.\r\n\r\n\u2461 \uBAA8\uB4E0 \uB178\uB825\uC5D0\uB3C4 \uBD88\uAD6C\uD558\uACE0 \uC18C\uC1A1\uC774 \uC81C\uAE30\uB420 \uACBD\uC6B0, \uC18C\uC1A1\uC740 \uD68C\uC0AC\uAC00 \uAD00\uD560\uD558\uB294 \uBC95\uC6D0\uC758 \uAD00\uD560\uB85C \uD569\uB2C8\uB2E4.\r\n \t \r\n\uBD80\uCE59\r\n \t\r\n\uC81C 1 \uC870 ( \uC2DC\uD589\uC77C)\r\n    \uC774 \uC57D\uAD00\uC740 2004\uB144 10\uC6D4 05\uC77C\uBD80\uD130 \uC2DC\uD589\uD569\uB2C8\uB2E4.");
		yakguan.setViewportView(newAccountTextArea);
		yakguan.setBorder(new EmptyBorder(0, 0, 0, 0));

		modifyButton = new JButton(modifyButtonImage); // 수정버튼
		modifyButton.setBounds(531, 701, 145, 44);
		newAccountPanel.add(modifyButton);

		deleteButton = new JButton(deleteButtonImage); // 삭제버튼
		deleteButton.setBounds(675, 701, 145, 44);
		newAccountPanel.add(deleteButton);
		loginButton.addActionListener(this);
		GamereadyButton.addActionListener(this);
		OutRoom.addActionListener(this);
		newAccountButton.addActionListener(this);
		newAccountCancelButton.addActionListener(this);
		NewAccountLoginButton.addActionListener(this);
		deleteButton.addActionListener(this);
		modifyButton.addActionListener(this);
		MakePersonMenuPop.addActionListener(this);
		CaptureMenu.addActionListener(this);
		exitMenu.addActionListener(this); // 종료
		newsButton.addActionListener(this); // 뉴스
		baookpans.addMouseListener(new mlistener());

		mainPanel.add("login", loginPanel);
		mainPanel.add("roomGUI", roomGUIPanel);

		// 대기룸 버튼 이미지들
		ImageIcon NewRoomImage = new ImageIcon("src/images/newRoomM.jpg");
		ImageIcon MakeRoomImage = new ImageIcon("src/images/RoomMakeRoom.jpg");
		ImageIcon cancelImage = new ImageIcon("src/images/makeCancel.jpg");
		// 대기룸 버튼 이미지들

		NewRoom = new JButton(NewRoomImage); // 새로운방 만들기 (활성화)
		NewRoom.setBounds(724, 537, 218, 90);
		roomGUIPanel.add(NewRoom);
		NewRoom.setEnabled(true);

		MakeRoom = new JButton(MakeRoomImage); // 만들기
		MakeRoom.setBounds(667, 483, 150, 44);
		roomGUIPanel.add(MakeRoom);
		MakeRoom.setEnabled(false);

		cancel = new JButton(cancelImage); // 만들기 취소
		cancel.setBounds(835, 483, 150, 44);
		roomGUIPanel.add(cancel);
		cancel.setEnabled(false);

		roomTitle = new JTextField();
		roomTitle.setBounds(667, 452, 318, 21);
		roomGUIPanel.add(roomTitle);
		roomTitle.setEnabled(false);
		roomTitle.setColumns(10);

		TodaysMessageLabel = new JLabel("미네랄 399로는 커멘더센터를 지을 수 없습니다. ");
		TodaysMessageLabel.setBounds(200, 739, 520, 15);
		roomGUIPanel.add(TodaysMessageLabel);

		nowTime = new JLabel();
		nowTime.setBounds(662, 427, 318, 15);
		roomGUIPanel.add(nowTime);
		todayArrayList = new tips(); // extend로 구현

		Thread thx = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				while (true) {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int a = (int) (Math.random() * 30);
					Date date = new Date();
					SimpleDateFormat stm = new SimpleDateFormat("aa hh 시 mm 분 ss");
					stm.format(date);
					TodaysMessageLabel.setText(todayArrayList.get(a));
					nowTime.setText("지금시각은: " + stm.format(date) + "초 입니다.");

				}
			}
		};
		thx.start();
		cancel.addActionListener(this);

		// ==============액션버튼 ===============
		MakeRoom.addActionListener(this);
		NewRoom.addActionListener(this);
		mainPanel.add("battleNetPanel", battleNetPanel);
		mainPanel.add("newAccountPanel", newAccountPanel);

		// --------------mainPanelCardlayout연결----------------
		newAccountCancelButton.addActionListener(this);
		Platform.runLater(new Runnable() { // this will run initFX as
			// JavaFX-Thread
			@Override
			public void run() {
				webViewAD(AD_Panel_2);
			}
		});
		// --------------mainPanelCardlayout연결----------------

		// bt.setBackground(Color.red);
		// bt.setBorderPainted(false);
		// bt.setFocusPainted(false);
		// 쓰레드 연결
		Thread th = new Thread(this);
		th.start();
		// 쓰레드 연결
		setVisible(true);
		setSize(1035, 858);
		setResizable(false); // 리사이즈 금지

	}

	@Override
	// ==================== 1. 버튼 부분===========

	public void actionPerformed(ActionEvent e) { // actionPerformed 버튼 부분
		// TODO Auto-generated method stub

		if (e.getSource() == NewRoom) { // 대기룸:: 새로운 방 만들기 선택시
			roomTitle.setEnabled(true);
			MakeRoom.setEnabled(true);
			cancel.setEnabled(true);

		} else if (e.getSource() == cancel) { // 대기룸:: 방 만들기 취소
			roomTitle.setText("");
			roomTitle.setEnabled(false);
			cancel.setEnabled(false);
			MakeRoom.setEnabled(false);
		} else if (e.getSource() == MakeRoom) { // 대기룸 :: 방 만들기
			String x = roomTitle.getText(); // 임시
			x.replaceAll(" ", ""); // 빈칸제거
			if (x.equals("")) {
			} else { //
				roommake(); // 방 만드는 것
			}
		} else if (e.getSource() == loginButton) { // 로그인 :: 로그인 버튼
			loginSend(); // 로그인 하기

		} else if (e.getSource() == GamereadyButton) { // 게임방::게임레디
			boolean readyOne = true;
			for (int i = 0; i < makeRoomGet.size(); i++) {
				if (makeRoomGet.get(i).getMakeName().equals(id)) {
					if (makeRoomGet.get(i).getInwon() == 1) {
						readyOne = false;
					}
				}
			}
			if (readyOne == false) {
				JOptionPane.showConfirmDialog(null, "아직 인원이 1명밖에 되지 않았습니다.");
			} else {
				GamereadyButton.setEnabled(false);
				gameData gameReadyDate = new gameData(gameData.ready, id, makeRoomGet);
				mg.send(gameReadyDate);
			}

		} else if (e.getSource() == OutRoom) { // 게임방:: 방 나가기
			if (gameStart == true) { // 게임중에는 방 나가기 금지
				JOptionPane.showConfirmDialog(null, "게임중에는 방을 나갈수 없습니다.");
			} else {
				roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, false);
				mg.send(outRoomDate);
				roomData refreshRoomWait = new roomData(roomData.roomRefresh, null);
				card.show(mainPanel, "roomGUI");
			}

		} else if (e.getSource() == NewAccountLoginButton) { // 취소::뉴어카운트패널
			card.show(mainPanel, "newAccountPanel");
		} else if (e.getSource() == newAccountButton) { // New Account! 생성하기
			newAccountInsert();
		} else if (e.getSource() == newAccountCancelButton) { // 취소 -> 로그인화으로
			card.show(mainPanel, "login");
			pwIDField.setText(""); // 필드 초기화
			passWordField.setText(""); // 필드 초기화
		} else if (e.getSource() == modifyButton) { // 계정생성::수정하기
			modifyAccount();
		} else if (e.getSource() == deleteButton) { // 계정생성::지우기
			deleteAccount();
		} else if (e.getSource() == CaptureMenu) { // 현재사진 캡쳐하기 기능
			capture();
		} else if (e.getSource() == exitMenu) {// 종료기능
			System.exit(0);
		} else if (e.getSource() == MakePersonMenuPop) { // 만든사람들
			new makePersons().setVisible(true);
		} else if (e.getSource() == newsButton) { // 한국 오목협회 뉴스 불러오기
			new omokNews().setVisible(true);
		}
	}

	// ==================== 2. 마우스 부분===========
	public class mlistener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);
			System.out.println("마우스 찍찍");
			if (e.getComponent() == gameRoom) { // 선택된 값
				if (e.getClickCount() == 2) { // 더블클릭을 할때
					int temp = gameRoom.getSelectedIndex();
					System.out.println(temp);
					if (makeRoomGet.get(temp).getInwon() == 1) {
						// 인원이 1이면 들어갈수 있다.
						roomData dataIn = new roomData(roomData.roomIn, id, makeRoomGet.get(temp).getMakeName());
						// makeRoomGet은 현재 방 리스트를 가진 array
						// 방들어검, 내 아이디, 방을 만든 아이디
						mg.send(dataIn);
						doolSelect = false; // 들어가는 사람은 검은돌로 한다.
						gameplayerSelect = false; // 흙은 나중에 플레이 한다.
						card.show(mainPanel, "battleNetPanel");

					} else if (makeRoomGet.get(temp).getInwon() == 2) {
						int question = JOptionPane.showConfirmDialog(null, "인원이 다 차서 들어갈 수 없습니다");

					}

				}
			} else if (e.getComponent() == baookpans) { // 바둑돌 두는 부분 baookpans

				if (gameplayerSelect == false) { // 자기 순서가 아니라면
					JOptionPane.showConfirmDialog(null, "아직 당신의 순서가 아닙니다.");
				} else if (gameplayerSelect == true) { // 두는게 가능하면
					stoneX = e.getX(); // x →
					stoneY = e.getY(); // y ↓

					if (gameStart) {
						System.out.println(stoneX + " | " + stoneY);
						boolean stoneons = StoneON(); // 데이터 상으로 돌 올리기
						if (stoneons) { // true
							boolean check33 = checkStone33();
							System.out.println(check33);
							if (check33 == false) {
								JOptionPane.showConfirmDialog(null, "그곳은 33이라 둘수 없습니다.");
								fleshBack(); // 무르기
							} else {
								StoneOnSend();
								spriteStone(stoneX, stoneY); // 그래픽 담당 (안에 int값을
																// 보고
																// 한다)
							}
						} else {

						}

					} else {
						JOptionPane.showConfirmDialog(null, "아직 두분다 ready가 되지 않았습니다.");
					}

				}

			}

		}
	} // mlisener ed

	public void resetPlayer() { // player Reset 청소용
		player1 = ""; // 플레이어 1 String초기화
		player2 = ""; // 플레이어 2 String초기화
		gameStart = false; // 둘다 Ready상태 해제
		gameplayerSelect = true; // 게임플레이 초기화
		gameWin = 0; // 게임 이기는 거 체크
		int[][] refreshOmokTable = new int[23][23];
		this.omokTable = refreshOmokTable;
		GamereadyButton.setEnabled(true);
		BattelChat.setText("");
		resetStoneBadookpan();
		player1pLabel.setText("");
		doolSelect = false;

	}

	public void winDefeatConfirm() {
		// TODO Auto-generated method stub
		if (gameWin == 1) { // 하얀돌이 이김
			int gameInduce = JOptionPane.showConfirmDialog(null, "백 님이 백이 이겼습니다. 방을 나가시겠습니까?");
			if (gameInduce >= 0) {
				// 하얀돌이 이겼다면 방장은 +1, 상대방은 -1을 시킨다.
				if (id.equals(player1)) { // 방장이 이겼다면 player1이 이겼다면
					gameData whiteScoreData = new gameData(gameData.winWhite, player1, 1, 0);
					mg.send(whiteScoreData); // 하얀돌 승리 기록
					gameData blackScoreData = new gameData(gameData.defeatBlack, player2, 0, 1);
					mg.send(blackScoreData); // 검은색 패배 기록
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate);
				} else if (id.equals(player2)) { // 방장이 이겼다면 player1이 이겼다면
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate);
				}
				resetPlayer();
				card.show(mainPanel, "roomGUI"); // 패널 넘겨줌
			}
		} else if (gameWin == 2) { // 검은돌이 이길 경우
			int gameInduce2 = JOptionPane.showConfirmDialog(null, "흙 님이 백이 이겼습니다.");
			if (gameInduce2 >= 0) {

				if (id.equals(player1)) { // 검은돌이 이겼다면 player2이 이겼다면
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate); // 방 나가기
				} else if (id.equals(player2)) { // 검은돌이 이겼다면 player2이 이겼다면
					gameData blackScoreData = new gameData(gameData.winBlack, player2, 1, 0);
					mg.send(blackScoreData); // 검은색 승리 기록
					gameData whiteScoreData = new gameData(gameData.defeatWhite, player1, 0, 1);
					mg.send(whiteScoreData); // 하얀돌 패배 기록
					roomData outRoomDate = new roomData(roomData.roomDestroy, id, makeRoomGet, true);
					mg.send(outRoomDate); // 방 나가기
				}
				resetPlayer(); // 플레이어 데이터와 true 초기화
				card.show(mainPanel, "roomGUI"); // 패널 넘겨줌
			}
		} // 승패 확인
	}

	public void newAccountInsert() { // 새로운 계정 생성
		String id = pwIDField.getText();// id
		String pw = passWordField.getText(); // id
		if (id.equals("") || pw.equals("")) { // 둘중 하나라도 공백이 있는 경우
			JOptionPane.showConfirmDialog(null, "공백이 있어서 입력을 할 수 없습니다. ");
		} else { // 공백이 아니라면
			accountData data = new accountData(accountData.newAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // 필드 초기화
		passWordField.setText(""); // 필드 초기화
	}

	public void webViewAD(JFXPanel fxPanel) { // 광고모듈(구글애드센스)
		Group group = new Group();
		Scene scene = new Scene(group);
		AD_Panel_2.setScene(scene);
		WebView webView = new WebView();
		group.getChildren().add(webView);
		webView.setMinSize(320, 50);
		webView.setMaxSize(320, 50);
		// 320 50 size Google Adsense를 사용.
		WebEngine webEngine = webView.getEngine();
		webEngine.load("http://paran.dothome.co.kr/adon.htm");

	}

	private void deleteAccount() { // 계정삭제
		String id = passWordField.getText(); // id
		String pw = pwIDField.getText();// password
		if (id.equals("") || pw.equals("")) { // 둘중 하나라도 공백이 있는 경우
			JOptionPane.showConfirmDialog(null, "공백이 있습니다.");
		} else { // 공백이 아니라면
			accountData data = new accountData(accountData.deleteAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // 필드 초기화
		passWordField.setText(""); // 필드 초기화
	}

	private void modifyAccount() { // 계정수정
		String id = pwIDField.getText();// id
		String pw = passWordField.getText(); // pw
		if (id.equals("") || pw.equals("")) { // 둘중 하나라도 공백이 있는 경우
			JOptionPane.showConfirmDialog(null, "공백이 있어서 입력을 할 수 없습니다. ");
		} else { // 공백이 아니라면
			accountData data = new accountData(accountData.modifiyAccount, id, pw, false);
			mg.send(data);
		}
		pwIDField.setText(""); // 필드 초기화
		passWordField.setText(""); // 필드 초기화
	}

	public void refresh() { // 로그인시 사용
		roomData data = new roomData(roomData.loginAc, this.id, 0, 0, null, null); // 더미
		mg.send(data);
		// 리스트 업을 위한 보내기
	}

	public void roommake() { // 방 만드는 사람만 사용 (하얀돌, 선플레이)
		makeRoom mr = new makeRoom(roomTitle.getText(), id, null, 1, false, false);
		roomData dataBattleRoomMake = new roomData(roomData.roomMake, id, "", mr, null, null, false, false);
		// 프로토콜, 아이디, 상대방 아이디, 방리스트, ?, 1레디, 2레디
		mg.send(dataBattleRoomMake);
		card.show(mainPanel, "battleNetPanel");
		player1 = this.id; // player1은 무조건 방을 만든 사람이다.
		gameplayerSelect = true; // 선플레이어
		doolSelect = true; // 돌 선택 (하얀돌)
		player1pLabel.setText(id + "의 전적:: " + winme + "승 | " + defeatme + "패");

	}

	public void conn() { // 소켓 연결 메소드
		try {
			socket = new Socket("localhost", 8888);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void login(loginData dat) { // 쓰레드에서 받은 값으로 구현
		System.out.println(dat.getId() + " | " + dat.getPw() + " | " + dat.isLoginBoolean());
		if (dat.isLoginBoolean()) {
			winme = dat.getAct().getWin();
			defeatme = dat.getAct().getDefeat();
			card.show(mainPanel, "roomGUI"); // 패널 넘겨줌
		} else {
			loginStatusLabel.setText("잘못된 아이디 혹은 비밀번호를 입력하셨습니다.");
		}
	}

	public void loginSend() { // 로그인 시 사용되는 메소드
		String id = IDField.getText(); // id
		this.id = id; // 내 아이디 값을 가지고 있음 걸 갱신해줌
		String pw = pwField.getText();// password
		if (id.equals("") || pw.equals("")) { // 공백 방지
			JOptionPane.showConfirmDialog(null, "공백은 입력할 수 없습니다. ");
		} else {
			System.out.println("loginSend: " + id);
			loginData data = new loginData(loginData.login, id, pw, false, null);
			System.out.println(data);
			mg.send(data); // send
			refresh();
		}

	}

	public void chat() { // 채팅
		String chat = chatTextField.getText(); // text String

		if (chat.equals("")) { // 빈칸 채팅 방지

		} else if (chat.substring(0, 1).equals("@")) { // 귓속말 모드
			String chatGet = chat.substring(chat.indexOf(" ")); // 채팅 내용
			String idOpposing = chat.substring(1, chat.indexOf(" "));
			roomData chatPerson = new roomData(roomData.p2pchat, id, idOpposing, chatGet);
			mg.send(chatPerson); // output 보내기
			chatTextField.setText(""); // 초기화
		} else { // 채팅
			roomData textData = new roomData(roomData.Allchat, id, chat);
			mg.send(textData); // output 보내기
			chatTextField.setText(""); // 초기화
		}

	}

	public void chatGame() { // 게임상대시에만 채팅
		String chat = BattelChat.getText(); // text String
		String id = this.id;
		if (chat.equals("")) { // 빈칸 채팅 방지
		} else { // 채팅
			gameData textData = new gameData(gameData.chatPerson, id, player1, chat, player2);
			mg.send(textData); // output 보내기
			BattelChat.setText(""); // 초기화
		}

	}

	public void capture() { // 캡쳐
		try {
			Robot robot = new Robot();
			// 내 모니터 화면의 크기를 가져오는 방법
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			// 모니터의 화면을 selectRect에 가로,세로 표현
			// Rectangle selectRect = new Rectangle((int) screen.getWidth(),
			// (int) screen.getHeight());
			Rectangle selectRect = new Rectangle(1035, 858);
			for (int i = 0; i < 1; i++) {
				BufferedImage buffimg = robot.createScreenCapture(selectRect);
				File screenfile = new File("c:/screen" + i + ".jpg");
				ImageIO.write(buffimg, "jpg", screenfile);
			}
			JOptionPane.showConfirmDialog(null, "c:/에 screen.jpg가 생성되었습니다.");
		} catch (AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refreshConnectList(roomData dat) {// 로그인 시 리스트 받아오기

		FirstArray = dat.getUsernames();
		nowList.setListData(FirstArray.toArray()); // 대기아이디들
		System.out.println(FirstArray.toString());
		makeRoomGet = dat.getMakeroom();
		gameRoom.setListData(makeRoomGet.toArray()); // 게임대기룸들

	}

	public void StoneOnSend() {
		int induceOnX = ((stoneX) / intXInt) + 2; // X에 대한 절대값 1~19
		int induceOnY = ((stoneY) / intYInt) + 2; // Y에 대한 절대값 1~19
		if (doolSelect == true) { // 하얀돌차례라면.

			omokTable[induceOnY][induceOnX] = 1;
			// table에 있는 숫자만 바꾼다. 나머지는 아래의 if문에서 처리
			count++;
			// doolSelect = false;
			// 검은색 돌로 바꾸기 위한
			gameData whiteDoolData = new gameData(gameData.whiteGo, id, player1, player2, omokTable, true, stoneX,
					stoneY);
			gameplayerSelect = false;
			mg.send(whiteDoolData);

		}

		else if (doolSelect == false) { // 검은돌
			omokTable[induceOnY][induceOnX] = 2;
			// 검은색 돌착수(2의 값을지닌다)
			count++;
			// doolSelect = true; // 하얀색 돌
			gameData blackDoolData = new gameData(gameData.blackGo, id, player1, player2, omokTable, false, stoneX,
					stoneY);
			gameplayerSelect = false;
			mg.send(blackDoolData);

		} // if end
	}

	public boolean StoneON() // 돌 올리면 intTable에 구현하는 기능
	{
		boolean flags = true;
		// 첫 착수점은 x = 46, y = 63

		if (gameStart == true) { // 둘다 레디가 되면 gameStart는 true가 된다.
			for (int i = 2; i < 21; i++) { // x
				int induceOnX = ((stoneX) / intXInt) + 2; // X에 대한 절대값 1~19
				for (int j = 2; j < 21; j++) {
					int induceOnY = ((stoneY) / intYInt) + 2; // Y에 대한 절대값 1~19
					if (i == induceOnX && j == induceOnY) {
						// 두개다 for 문과 같을 경우
						if (omokTable[induceOnY][induceOnX] == 0) {
							// table에0일 경우.

							if (doolSelect == true) { // 하얀돌차례라면.

								omokTable[induceOnY][induceOnX] = 1;
								// table에 있는 숫자만 바꾼다. 나머지는 아래의 if문에서 처리
								count++;
								// doolSelec기 위한t = false;
								// 검은색 돌로 바꾸

							} else if (doolSelect == false) { // 검은돌
								omokTable[induceOnY][induceOnX] = 2;
								// 검은색 돌착수(2의 값을지닌다)
								count++;
								// doolSelect = true; // 하얀색 돌

							} // if end
						} else {
							JOptionPane.showConfirmDialog(null, "그곳엔 둘 수 없습니다. 이미 돌이 있습니다.");
							flags = false;
						}
						// 여기까지가 착수를 끝내는 부분
					}
				} // 2차 for문 end
			} // 1차 for문 end

		}
		return flags;
	}

	public void spriteStone(int xs, int ys) // 바둑 돌을 올리는 그래픽 담당
	{
		for (int i = 0; i < 21; i++) // x
		{
			int induceOnX = (xs / intXInt); // X에 대한 절대값 1~19

			for (int j = 0; j < 21; j++) {
				int induceOnY = (ys / intYInt); // Y에 대한 절대값 1~19
				int x = 8;
				int y = 8;

				if (i == induceOnX && j == induceOnY) {
					if (omokTable[induceOnY + 2][induceOnX + 2] == 1) {
						// 2번째 처리, 여기서 돌을 그래픽으로 올리는 작업을 한다
						makeWhite();
						baookpans.add(whiteStoneOn);
						whiteStoneOn.setBounds(induceOnX * 37 + x, induceOnY * 37 + y, 34, 34);
					} else if (omokTable[induceOnY + 2][induceOnX + 2] == 2) {
						makeBlack();
						baookpans.add(blackStoneOn);
						blackStoneOn.setBounds(induceOnX * 37 + x, induceOnY * 37 + y, 34, 34);
					}

					for (int q = 2; q < 21; q++) // System 으로 보여주는 임시 UI
					{
						System.out.println();

						for (int w = 2; w < 21; w++) {
							System.out.print(omokTable[q][w]);
						}
					}
				} // if

			} // for
		} // for
	}

	public void resetStoneBadookpan() { // 바둑판 초기화
		// refreshStone = new ImageIcon("src/images/badookpan.jpg");
		// refreshStoneOn = new refreshStoneL(refreshStone); // extends 로 구현
		// baookpans.add(refreshStoneOn);
		// refreshStoneOn.setBounds(0, 0, 718, 718);
		baookpans.removeAll();

	}

	// public void resetStoneBadookpan() { // 바둑판 초기화
	// refreshStone = new ImageIcon("src/images/refresh.jpg");
	// refreshStoneOn = new refreshStoneL(refreshStone); // extends 로 구현
	// baookpans.add(refreshStoneOn);
	// refreshStoneOn.setBounds(0, 0, 718, 718);
	//
	//
	// for (int i = 1; i <= 718; i++) { // x
	// int induceOnX = ((i) / 37) + 2; // X에 대한 절대값 1~19
	// for (int j = 1; j <= 718; j++) {
	// int induceOnY = ((j) / 37) + 2; // Y에 대한 절대값 1~19
	// refreshStoneOn.setBounds(induceOnX * 37 + 8, induceOnY * 37 + 8, 34, 34);
	// } // 2차 for문 end
	// } // 1차 for문 end
	//
	// }

	public void makeWhite() { // 하얀돌 올리는 기능
		whiteStone = new ImageIcon("src/images/whiteStone.png");
		whiteStoneOn = new whiteStone(whiteStone);
	}

	public void makeBlack() { // 검은돌 올리는 기능
		blackStone = new ImageIcon("src/images/blackStone.png");
		blackStoneOn = new blackStone(blackStone); // extends 로 구현
	}

	public void checkStone() // 바둑돌 검사
	{
		int whiteXarrayCheck = 0; // X열 화이트 검사
		int blackXarrayCheck = 0;// X열 블랙돌 검사
		int whiteYarrayCheck = 0;// Y열 화이트 검사
		int blackYarrayCheck = 0;// Y열 블랙돌 검사
		int whitezLowArrayCheck = 0;// 하향 대각선 검사 화이트
		int blackzLowArrayCheck = 0; // 하향 대각선 검사 블랙
		int whitezUpArrayCheck = 0; // 상향 대각선 검사 화이트
		int blackzUpArrayCheck = 0; // 상향 대각선 검사 블랙

		for (int stoneX = 2; stoneX < 21; stoneX++) // x 0~18 stoneY|stoneX
		{
			for (int stoneY = 2; stoneY < 21; stoneY++) // y 0~18 가로 검사
			{
				for (int dool = 1; dool <= 2; dool++) {
					if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY + 1][stoneX] == dool
							&& omokTable[stoneY - 1][stoneX] == dool && omokTable[stoneY + 2][stoneX] == dool
							&& omokTable[stoneY - 2][stoneX] == dool) {
						// black & white 세로 검사
						if (dool == 1) {
							whiteYarrayCheck++;
						}

						else if (dool == 2) {
							blackYarrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY][stoneX + 1] == dool
							&& omokTable[stoneY][stoneX + 2] == dool && omokTable[stoneY][stoneX - 1] == dool
							&& omokTable[stoneY][stoneX - 2] == dool) {
						// black & white 가로 검사

						if (dool == 1) {
							whiteXarrayCheck++;
						}

						else if (dool == 2) {
							blackXarrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY + 1][stoneX + 1] == dool
							&& omokTable[stoneY + 2][stoneX + 2] == dool && omokTable[stoneY - 1][stoneX - 1] == dool
							&& omokTable[stoneY - 2][stoneX - 2] == dool) {
						// black & white 하강 대각선 검사
						if (dool == 1) {
							whitezLowArrayCheck++;
						}

						else if (dool == 2) {
							blackzLowArrayCheck++;
						}
					}

					else if (omokTable[stoneY][stoneX] == dool && omokTable[stoneY - 1][stoneX + 1] == dool
							&& omokTable[stoneY - 2][stoneX + 2] == dool && omokTable[stoneY + 1][stoneX - 1] == dool
							&& omokTable[stoneY + 2][stoneX - 2] == dool) {
						// white Stone 상승 대각선 검사
						if (dool == 1) {
							whitezUpArrayCheck++;
						}

						else if (dool == 2) {
							blackzUpArrayCheck++;
						}
					} // if end
				} // 1, 2 for
			}
		} // 1 / for

		System.out.println();
		System.out.println("하얀색 가로 " + whiteXarrayCheck);
		System.out.println("검은색 가로 " + blackXarrayCheck);
		System.out.println("하얀색 세로 " + whiteYarrayCheck);
		System.out.println("검은색 가로 " + blackYarrayCheck);
		System.out.println("하얀색 로우대각선  " + whitezLowArrayCheck);
		System.out.println("검은색 로우대각선  " + blackzLowArrayCheck);
		System.out.println("하얀색 업대각선  " + whitezUpArrayCheck);
		System.out.println("검은색 업대각선  " + blackzUpArrayCheck);
		System.out.println(count + "수 째");
		int whiteWin = whiteXarrayCheck + whiteYarrayCheck + whitezLowArrayCheck + whitezUpArrayCheck;
		int blackWin = blackXarrayCheck + blackYarrayCheck + blackzLowArrayCheck + blackzUpArrayCheck;
		if (whiteWin >= 1) { // 하얀돌 승리
			gameWin = 1;
		} else if (blackWin >= 1) { // 검은돌 등리
			gameWin = 2;
		} else {
			// 0은 그대로 둔다.
		}
		// 1이 나오는 순간 이기는 거.
	}

	public boolean checkStone33() { // 33바둑돌 검사 false일 경우 33임 다시 reback해줘야ㅕ 함
		boolean gameChanege = true;
		for (int stoneiX = 2; stoneiX < 21; stoneiX++) // x 0~18 stoneY|stoneX
		{

			for (int stoneiY = 2; stoneiY < 21; stoneiY++) // y 0~18 가로 검사
			{
				int whiteXarrayCheck = 0; // X열 화이트 검사
				int blackXarrayCheck = 0;// X열 블랙돌 검사
				int whiteYarrayCheck = 0;// Y열 화이트 검사
				int blackYarrayCheck = 0;// Y열 블랙돌 검사
				int whitezLowArrayCheck = 0;// 하향 대각선 검사 화이트
				int blackzLowArrayCheck = 0; // 하향 대각선 검사 블랙
				int whitezUpArrayCheck = 0; // 상향 대각선 검사 화이트
				int blackzUpArrayCheck = 0; // 상향 대각선 검사 블랙
				for (int dool = 1; dool <= 2; dool++) {

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY + 1][stoneiX] == dool
							&& omokTable[stoneiY - 1][stoneiX] == dool) {
						// black & white 세로 검사
						if (dool == 1) { // 하얀색 돌이
							if (omokTable[stoneiY - 2][stoneiX] == 2 || omokTable[stoneiY + 2][stoneiX] == 2) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								whiteYarrayCheck++;
							}

						}

						else if (dool == 2) { // 검은색 돌이

							if (omokTable[stoneiY - 2][stoneiX] == 1 || omokTable[stoneiY + 2][stoneiX] == 1) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								blackYarrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY][stoneiX + 1] == dool
							&& omokTable[stoneiY][stoneiX - 1] == dool) {
						// black & white 가로 검사

						if (dool == 1) {
							if (omokTable[stoneiY][stoneiX - 2] == 2 || omokTable[stoneiY][stoneiX + 2] == 2) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								whiteXarrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY][stoneiX - 2] == 1 || omokTable[stoneiY][stoneiX + 2] == 1) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								blackXarrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY + 1][stoneiX + 1] == dool
							&& omokTable[stoneiY - 1][stoneiX - 1] == dool) {
						// black & white 하강 대각선 검사
						if (dool == 1) {
							if (omokTable[stoneiY - 2][stoneiX - 2] == 2 || omokTable[stoneiY + 2][stoneiX + 2] == 2) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								whitezLowArrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY - 2][stoneiX - 2] == 1 || omokTable[stoneiY + 2][stoneiX + 2] == 1) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								blackzLowArrayCheck++;
							}

						}
					}

					if (omokTable[stoneiY][stoneiX] == dool && omokTable[stoneiY - 1][stoneiX + 1] == dool
							&& omokTable[stoneiY + 1][stoneiX - 1] == dool) {
						// white Stone 상승 대각선 검사
						if (dool == 1) {
							if (omokTable[stoneiY + 2][stoneiX - 2] == 2 || omokTable[stoneiY - 2][stoneiX + 2] == 2) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								whitezUpArrayCheck++;
							}

						}

						else if (dool == 2) {
							if (omokTable[stoneiY + 2][stoneiX - 2] == 1 || omokTable[stoneiY - 2][stoneiX + 2] == 1) {
								// 끝단에 상대 돌이 있다면 아무런 조치를 취하지 않는다.
							} else {
								blackzUpArrayCheck++;
							}

						}
					} // if end

				} // 1, 2 for
				int white33 = whiteXarrayCheck + whiteYarrayCheck + whitezLowArrayCheck + whitezUpArrayCheck;
				int black33 = blackXarrayCheck + blackYarrayCheck + blackzLowArrayCheck + blackzUpArrayCheck;
				if (white33 >= 2) { // 하얀돌이 33이면
					gameChanege = false;
				} else if (black33 >= 2) { // 검은돌이 33이면
					gameChanege = false;
				}
				// System.out.println("33 상황");
				// System.out.println(count + "수 째");
				// System.out.println("하얀색 가로 " + whiteXarrayCheck);
				// System.out.println("검은색 가로 " + blackXarrayCheck);
				// System.out.println("하얀색 세로 " + whiteYarrayCheck);
				// System.out.println("검은색 가로 " + blackYarrayCheck);
				// System.out.println("하얀색 로우대각선 " + whitezLowArrayCheck);
				// System.out.println("검은색 로우대각선 " + blackzLowArrayCheck);
				// System.out.println("하얀색 업대각선 " + whitezUpArrayCheck);
				// System.out.println("검은색 업대각선 " + blackzUpArrayCheck);
			}
		} // 1 / for

		return gameChanege; // false면 무르기 실행
	}

	public void fleshBack() { // 무르기 & 33일 경우 다시 원위치
		int induceOnX = ((stoneX) / intXInt) + 2; // X에 대한 절대값 1~19
		int induceOnY = ((stoneY) / intYInt) + 2; // Y에 대한 절대값 1~19
		if (doolSelect == true) { // 하얀돌차례라면.
			omokTable[induceOnY][induceOnX] = 0;
		}

		else if (doolSelect == false) { // 검은돌
			omokTable[induceOnY][induceOnX] = 0;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		mg = new clientManager(output, input);
		while (true) {
			Object getData = null;
			try {
				getData = mg.call(); // get
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (getData instanceof loginData) { // 로그인 부분
				// protocol, id, pw, loginboolean
				switch (((loginData) getData).getState()) {
				case loginData.login:
					login((loginData) getData);

					break;
				}

			} else if (getData instanceof roomData) { // 대기룸 부분
				switch (((roomData) getData).getState()) {
				case roomData.loginAc:
					refreshConnectList((roomData) getData);
					System.out.println("메니져의loninAc : " + ((roomData) getData).getUsernames());
					break;
				case roomData.Allchat: // 대기룸에서 전체채팅
					System.out.println(((roomData) getData).getMessage());
					textArea.setCaretColor(Color.BLACK);
					textArea.append(((roomData) getData).getId() + " : " + ((roomData) getData).getMessage() + "\n");
					textArea.setCaretPosition(textArea.getDocument().getLength());
					// 항상 텍스트 아래로 내리기
					// int state, String id, String message
					break;

				case roomData.p2pchat: // 대기룸에서 전체채팅
					if (((roomData) getData).getIdOpposing().equals(id)) {
						textArea.setCaretColor(Color.red); // 귓속말은 RED
						textArea.append(((roomData) getData).getId() + " 님이 보낸 귓속말 > "
								+ ((roomData) getData).getMessage() + "\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					} else if (((roomData) getData).getId().equals(id)) { // 귓속말이
																			// 보낸사람
																			// 채팅창에
																			// 표시
						textArea.setCaretColor(Color.red);
						textArea.append(((roomData) getData).getIdOpposing() + "님께 " + ((roomData) getData).getId()
								+ " 님이 귓속말 > " + ((roomData) getData).getMessage() + "\n");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
					break;

				case roomData.roomMake: // 게임방 생성
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());
					// 리스트를 갱신한다.
					break;

				case roomData.roomIn: // 게임방 들어가기
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());
					// 들어가는 입장은 무조건 player2이다.
					for (int index = 0; index < makeRoomGet.size(); index++) {
						if (makeRoomGet.get(index).getMakeName2().equals(id)) {
							// 만약 내가 getmakename"2"과 같다면(손님이라면)
							player1 = makeRoomGet.get(index).getMakeName();
							player2 = makeRoomGet.get(index).getMakeName2();

						} else if (makeRoomGet.get(index).getMakeName().equals(id)) {
							// 만약 내가 getmakename"1"과 같다면(방장이라면)
							player2 = makeRoomGet.get(index).getMakeName2();

						}
					}
					System.out.println(player2);
					// 들어간다.

					break;
				case roomData.roomDestroy: // 게임방 나가기
					System.out.println(((roomData) getData).getId());
					System.out.println(((roomData) getData).getMakeroom());
					makeRoomGet = ((roomData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray());

					break;
				case roomData.roomRefresh: // 대기자 초기화
					ArrayList<account_tenshu> userslist = ((roomData) getData).getUsernames();
					nowList.setListData(userslist.toArray());
					break;

				}
			} else if (getData instanceof gameData) { // 게임부분

				switch (((gameData) getData).getState()) {
				case gameData.ready: // 레디
					makeRoomGet = ((gameData) getData).getMakeroom();
					gameRoom.setListData(makeRoomGet.toArray()); // 일단 리스트 갱신
					for (int index = 0; index < makeRoomGet.size(); index++) {
						if (makeRoomGet.get(index).getMakeName().equals(id)
								|| makeRoomGet.get(index).getMakeName2().equals(id)) {
							// 내 id 프로토콜이 내 아이디라면
							if (makeRoomGet.get(index).isPlayer1() == true
									&& makeRoomGet.get(index).isPlayer2() == true) {
								gameStart = true;
								BattelChat.setText("게임을 시작합니다.");
							}
						}
					}
					System.out.println("현재 게임 gameStart" + gameStart);
					break;

				case gameData.whiteGo: // 하얀 돌을 올리는 경우 // 안에 true가
										// 있다.gameplayerSelect = true;

					if (((gameData) getData).getIdOpposing().equals(player1)
							|| ((gameData) getData).getplayer2().equals(player2)) { // 2번째
						// 내꺼라면
						System.out
								.println("id: " + id + " | " + "player1: " + player1 + " | " + "player2 : " + player2);
						this.omokTable = ((gameData) getData).getOmokTable();
						int StoneWhiteX = ((gameData) getData).getX();
						int StoneWhiteY = ((gameData) getData).getY();
						spriteStone(StoneWhiteX, StoneWhiteY);

						checkStone(); // 돌 체크, 패배 승리 구현
						winDefeatConfirm(); // 승패확인
					}
					if (((gameData) getData).getplayer2().equals(id)) {
						// 플레이어2의 아이디가 나와 같다면(검정돌을 쥔 사람이라면)
						// true로 변경한다.
						gameplayerSelect = true;
						System.out.println(gameplayerSelect);
					}
					break;
				case gameData.blackGo: // 검은 돌을 올리는 경우 // 안에 false가
										// 있다.gameplayerSelect = true;

					if (((gameData) getData).getIdOpposing().equals(player1)
							|| ((gameData) getData).getplayer2().equals(player2)) {
						System.out
								.println("id: " + id + " | " + "player1: " + player1 + " | " + "player2 : " + player2);
						this.omokTable = ((gameData) getData).getOmokTable();
						int StoneBlackX = ((gameData) getData).getX();
						int StoneBlackY = ((gameData) getData).getY();
						spriteStone(StoneBlackX, StoneBlackY);

						checkStone(); // 돌 체크, 패배 승리 구현
						winDefeatConfirm();
					}

					if (((gameData) getData).getIdOpposing().equals(id)) {
						// 플레이어1의 아이디가 나와 같다면(하얀돌을 쥔 사람이라면)
						// true로 변경한다.
						gameplayerSelect = true;
						System.out.println(gameplayerSelect);
					}

					break;
				case gameData.chatPerson: // 게임중 서로 채팅
					if (((gameData) getData).getIdOpposing().equals(id)
							|| ((gameData) getData).getplayer2().equals(id)) {
						// 둘중하나라도 내 아이디라면
						System.out.println(((gameData) getData).getMessage());
						charTextArea.append(
								((gameData) getData).getId() + " : " + ((gameData) getData).getMessage() + "\n");
						charTextArea.setCaretPosition(charTextArea.getDocument().getLength());
					}
					// 검사는 상대방 클라이언트에서 한다.
					break;
				case gameData.winWhite:

					break;
				case gameData.defeatWhite:

					break;
				case gameData.winBlack:

					break;
				case gameData.defeatBlack:

					break;

				}
			} else if (getData instanceof accountData) { // 회원가입 부분
				switch (((accountData) getData).getState()) {
				case accountData.newAccount:
					if (((accountData) getData).isFlag()) {
						JOptionPane.showConfirmDialog(null, "정상적으로 입력되었습니다.");
						card.show(mainPanel, "login"); // 패널 넘기기
					} else {
						JOptionPane.showConfirmDialog(null, "중복인 아이디가 있어서 계정이 생성되지 않았습니다.");
					}
					break;

				case accountData.deleteAccount:
					if (((accountData) getData).isFlag()) { // true일 경우
						JOptionPane.showConfirmDialog(null, "정상적으로 삭제되었습니다.");
					} else {
						JOptionPane.showConfirmDialog(null, "없는 계정이라 삭제를 할 수 없습니다.");
					}
					break;
				case accountData.modifiyAccount:
					if (((accountData) getData).isFlag()) {
						JOptionPane.showConfirmDialog(null, "정상적으로 수정되었습니다.");
					} else {
						JOptionPane.showConfirmDialog(null, "없는 계정이라 수정을 할 수 없습니다.");
					}
					break;

				}
			}

		} // while

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainGUI();
	}
}
