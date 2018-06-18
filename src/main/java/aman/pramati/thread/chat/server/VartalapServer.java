package aman.pramati.thread.chat.server;

import java.io.BufferedReader;
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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import aman.pramati.thread.chat.client.ClientHandler;
import aman.pramati.thread.chat.exception.IncorrectConfigurationException;
import aman.pramati.thread.chat.exception.ServerListeningException;
import aman.pramati.thread.chat.exception.UnauthorizedAccessException;
import aman.pramati.thread.chat.exception.UsernameAlreadyTakenException;
import aman.pramati.thread.chat.exception.VartalapCheckedException;
import aman.pramati.thread.chat.sever.utils.ServerConfiguration;

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
			throw new IncorrectConfigurationException("Unable to open the port, it may be already bound");
		}
		
		clientMap = new HashMap<>();
		
	}
	
	public void addToClientMap(String username, Socket s) throws UsernameAlreadyTakenException {
		if(clientMap.containsKey(username)) {
			throw new UsernameAlreadyTakenException();
		}
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
				threadPool.submit(new ClientHandler(s));
			}
		}
		catch(IOException ie) {
			throw new ServerListeningException("Port has been closed");
		}
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
