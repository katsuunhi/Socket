import java.io.*;
import java.net.*;
import java.util.*;
public class MultiClientSocketServer {
	public static void main(String [] arge){
		try{
			System.out.println("WELLOCOME !");
			ServerSocket server = new ServerSocket(2018);
			while(true){
				Socket fromClient = server.accept();
				ClientThread clientThread = new ClientThread(fromClient);
				clientThread.start();		
			}
		}	
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
