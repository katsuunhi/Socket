
import java.net.*;
import java.io.*;
import java.util.*;

class ServerHelper extends Thread{
	private Socket sock;
	private DataOutputStream remoteOut;
	private ChatServer server;
	private boolean connected = true;
	private DataInputStream remoteIn;

	ServerHelper(Socket sock, DataOutputStream remoteOut, ChatServer server)throws IOException{
		this.sock = sock;
		this.remoteOut = remoteOut;
		this.server = server;
		remoteIn = new DataInputStream(sock.getInputStream());
	}

	public synchronized void run(){
		String s;
		try{
			while(connected){
				s = remoteIn.readUTF();
				broadcast(s);
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage() + ": failed");

		}
	}

	private void broadcast(String s){
		Vector clients = server.getClients();
		DataOutputStream dataOut = null;
		for(Enumeration e = clients.elements(); e.hasMoreElements();){
			dataOut = (DataOutputStream)(e.nextElement());

			if(!dataOut.equals(remoteOut)){
				try{
					dataOut.writeUTF(s);
				}
				catch(IOException a){
				System.out.println(a.getMessage() + ": failed");
				server.removeFromClients(dataOut);

				}
			}
		}
	}
}