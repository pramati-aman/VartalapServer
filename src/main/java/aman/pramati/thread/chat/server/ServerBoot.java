package aman.pramati.thread.chat.server;

import org.springframework.boot.SpringApplication;

import aman.pramati.thread.chat.exception.VartalapRuntimeException;

public class ServerBoot {

	public static void main(String[] args) {
		SpringApplication.run(VartalapServer.class, args);
		
		try {
			VartalapServer server = VartalapServer.intializeServer();
			server.bootstrap();
		}
		catch(VartalapRuntimeException ve) {
			System.out.println(ve.getMessage());
			ve.printStackTrace();
		}
	}

}
