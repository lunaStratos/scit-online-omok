package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverMain {
	int port; // 서버의 PORT 번호
	ServerSocket serverSocket; // 서버 소켓

	public static void main(String[] args) {
		serverMain chatServer = new serverMain(8888);
		chatServer.start();
	}

	public serverMain(int port) {
		this.port = port;
	}

	public void start() {
		Socket socket = null;
		ServerThread thread = null;

		try {
			System.out.println("서버 Start.");
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("서버 시작시 오류.");
		}

		while (true) {
			try {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress().getHostAddress() + " 접속.");
				thread = new ServerThread(socket);
				new Thread(thread).start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("하라쇼");
			}
		}
	}

}
