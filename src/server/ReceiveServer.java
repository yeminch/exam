package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveServer {
	ServerSocket ss;

	public ReceiveServer() {
		try {
			//构造一个ServerSocket
			ss = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		Socket s;
		while (true) {
			try {
				s = ss.accept();
				System.out.println("----------------已经建立连接--------------");
				new ServerThread(s).start();//传入到多线程里，处理N个客户需求
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ReceiveServer().startServer();
	}
}
