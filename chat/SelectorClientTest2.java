import java.nio.channels.*;
import java.nio.charset.*;
import java.nio.*;
import java.net.*;

public class SelectorClientTest2{
	public static void main(String[] arge){
		try{
			InetSocketAddress addr = new InetSocketAddress("localhost", 2019);

			Charset charset = Charset.forName("UTF-8");
			SocketChannel channel = SocketChannel.open(addr);

			System.out.println("address: " + addr);
			ByteBuffer buf = ByteBuffer.allocate(256);
			channel.read(buf);
			buf.flip();
			CharBuffer chBuffer = charset.decode(buf);
			System.out.println(chBuffer);
			channel.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}