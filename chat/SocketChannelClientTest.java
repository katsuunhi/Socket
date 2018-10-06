import java.nio.channels.*;
import java.nio.charset.*;
import java.nio.*;
import java.net.*;
import java.util.*;

public class SocketChannelClientTest{
	public static void main(String[] arge){
		try{
			InetSocketAddress addr = new InetSocketAddress("l ocalhost", 8000);
			Charset charset = Charset.forName("US-ASCII");
			SocketChannel channel = SocketChannel.open(addr);
			ByteBuffer buf = ByteBuffer.allocate(256);
			channel.read(buf);
			buf.flip();
			CharBuffer chBuffer = charset.decode(buf);

			System.out.println("The current date and time from Server :" + chBuffer);

			channel.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}