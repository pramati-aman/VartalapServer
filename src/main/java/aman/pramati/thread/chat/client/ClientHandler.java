package aman.pramati.thread.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{

	Socket client;
	DataInputStream dis;
	DataOutputStream dos;
	String username;
	
	public ClientHandler(Socket client, String username) {
		this.client=client;
		try {
			dis=(DataInputStream) client.getInputStream();
			dos=(DataOutputStream) client.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.username=username;
	}
	
	@Override
	public void run() {
//		String message;
		try {
			
		}
		catch(Exception ie) {
			ie.printStackTrace();
		}
		
	}
	

}
