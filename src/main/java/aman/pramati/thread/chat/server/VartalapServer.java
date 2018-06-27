package aman.pramati.thread.chat.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import aman.pramati.thread.chat.client.ClientHandler;
import aman.pramati.thread.chat.exception.IncorrectConfigurationException;
import aman.pramati.thread.chat.exception.ServerListeningException;
import aman.pramati.thread.chat.exception.UnauthorizedAccessException;
import aman.pramati.thread.chat.exception.UsernameAlreadyTakenException;
import aman.pramati.thread.chat.exception.VartalapCheckedException;
import aman.pramati.thread.chat.server.utils.ServerConfiguration;

@SpringBootApplication
public class VartalapServer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VartalapServer(String user, String password) throws VartalapCheckedException {
		if(!configurations.getUser().equalsIgnoreCase(user) || !configurations.getPassword().equals(password)) {
			throw new UnauthorizedAccessException("Incorrect Username/Password");
		}
		
		try {
			serverSocket = new ServerSocket(configurations.getPort(),configurations.getMaxQueueLength());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IncorrectConfigurationException("Unable to open the port, it may already be bound");
		}
		
		clientMap = new HashMap<>();
		
	}
	
	public void addToClientMap(String username, Socket s) throws UsernameAlreadyTakenException {
		if(clientMap.containsKey(username)) {
			throw new UsernameAlreadyTakenException();
		}
		
		clientMap.put(username, s);
	}
	
	@Autowired
	ServerConfiguration configurations;
	
	private ServerSocket serverSocket;
	private Map<String,Socket> clientMap;

	public void bootstrap() {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		try {
			while(true) {
				Socket s = serverSocket.accept();
				threadPool.submit(new ClientHandler(s, welcomeClient(s)));
			}
		}
		catch(IOException ie) {
			throw new ServerListeningException("Port has been closed");
		}
	}

	private String welcomeClient(Socket s) {
		String name=null;
		try {
			DataInputStream dis = (DataInputStream) s.getInputStream();
			DataOutputStream dos = (DataOutputStream) s.getOutputStream();
			String message;
			dos.writeUTF("UserName#Enter your username to continue in Vartalap: ");
			while ((message = dis.readUTF()).startsWith("UserName")) {
				try {
					addToClientMap(name=message.substring(message.indexOf("UserName#"), message.length()), s);
					break;
				} catch (UsernameAlreadyTakenException uae) {
					dos.writeUTF("UserName#Username already taken, enter a unique username : ");
				}
			}
			dos.writeUTF("Swagat at Vartalap Chat Center");
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return name;
	}

	static VartalapServer intializeServer() {
		VartalapServer vs =  null;
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
			
			System.out.println("Authorize before begin\n\t Username: ");
			String user=reader.readLine();
			System.out.println("\t Password: ");
			String pass=reader.readLine();
			
			vs = new VartalapServer(user,pass);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (VartalapCheckedException ve) {
			System.out.println("Checked Exception: "+ve.getMessage());
			System.err.println(ve);
			ve.printStackTrace();
		}
		return vs;
	}

}
