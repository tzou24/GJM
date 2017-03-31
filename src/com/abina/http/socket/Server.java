package com.abina.http.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public void start(int port) {
		try {
			ServerSocket serverSocket  = new ServerSocket(port);
			System.out.println("服务器启动成功");
			while(true){
				Socket Socket= serverSocket.accept();
				new Client(Socket).start();				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int port = 8181 ;
		if(args.length ==1) port  = Integer.parseInt(args[0]);
		
		Server server =  new Server();
		server.start(port);
	
	}
}
