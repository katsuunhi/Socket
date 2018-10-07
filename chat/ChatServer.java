import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer{
	private static final int port = 2018;
	private boolean connected = true;
	private Vector<DataOutputStream> clients = new Vector<DataOutputStream>();

	public static void main(String [] arge){
		new ChatServer().server();
	}

	void server(){
		ServerSocket serverSock = null;
		try{
			InetAddress serverAddr = InetAddress.getByName(null);
			System.out.println("Welcome to Chat Server. The server is running...");
			System.out.println("Waiting for " + serverAddr.getHostName() + " on port " + port);
			serverSock = new ServerSocket(port, 50);
		}
		catch(IOException e){
			System.out.println(e.getMessage() + ": failed");

		}

		while(connected){
			try{
				Socket socket = serverSock.accept();
				System.out.println("Acccept " + socket.getInetAddress().getHostName());

				DataOutputStream remoteOut = new DataOutputStream(socket.getOutputStream());

				clients.addElement(remoteOut);
				new ServerHelper(socket, remoteOut, this).start();
			}
			catch(IOException e){
			System.out.println(e.getMessage() + ": failed");

			}
		}
	}

	synchronized Vector getClients(){
		return clients;
	}

	synchronized void removeFromClients(DataOutputStream remoteOut){
		clients.removeElement(remoteOut);
	}
}