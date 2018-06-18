package aman.pramati.thread.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{

	Socket client;
	DataInputStream dis;
	DataOutputStream dos;
	
	public ClientHandler(Socket s) {
		client=s;
		try {
			dis=(DataInputStream) s.getInputStream();
			dos=(DataOutputStream) s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		String message;
		try {
			message = dis.readUTF();
			while( message.startsWith("UserName")) {
				
			}
			dos.writeUTF("Swagat at Vartalap Chat Center");
		}
		catch(IOException ie) {
			ie.printStackTrace();
		}
		
	}
	

}
