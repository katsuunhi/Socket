import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClientTest{
	public static void main(String[] arges){
		try{
			Socket clientSocket = new Socket("localhost", 2018);

			InputStream inData = clientSocket.getInputStream();
			OutputStream outData = clientSocket.getOutputStream();
			PrintWriter toServer = new PrintWriter(outData, true);
			Scanner sc = new Scanner(System.in);
			Scanner data = new Scanner(inData);
			String heading = data.nextLine();
			System.out.println(heading);
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				toServer.println(line);
				String fromServer = data.nextLine();
				System.out.println(fromServer);
				if(fromServer.equals("Bye!")){
					System.out.println("Now is disconnected...");
					break;
				}
			}
			clientSocket.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}