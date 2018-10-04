import java.io.*;
import java.net.*;
import java.util.*;
class ClientThread extends Thread{
	Socket client;
	InputStream inData;
	OutputStream outData;
	PrintWriter toClient;
	Scanner data;
	public ClientThread(Socket fromClient){
		try{
			client = fromClient;
			inData = fromClient.getInputStream();
			outData = fromClient.getOutputStream();
			toClient = new PrintWriter(outData, true);
			toClient.println("Type quit to STOP");

		}
		catch(IOException e){
			e.printStackTrace();
		}	
	}

	public void run(){
		data = new Scanner(inData);
		System.out.println("connected to the client:" + this.getName());
//		System.out.println("connected to the client:" + this.getInetAddress());
		while(data.hasNextLine()){
			String line = data.nextLine();
			if(line.equalsIgnoreCase("quit")){
				toClient.println("bye!");
				try{
					client.close();
					inData.close();
					outData.close();
					System.out.println("client:" + this.getName() + " closed");
					break;
				}
				catch(IOException e){
					e.printStackTrace();	
				}
			}		
			toClient.println(line.toUpperCase());
		}
	}
}	
