import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util. Iterator;
import java.util.Set;

public class SelectorServer{
	public static void main(String[] arge) throws IOException{
		InetSocketAddress addr1 = new InetSocketAddress(2018);
		ServerSocketChannel sch1 = ServerSocketChannel.open();
		sch1.configureBlocking(false);
		sch1.socket().bind(addr1);

		InetSocketAddress addr2 = new InetSocketAddress(2019);
		ServerSocketChannel sch2 = ServerSocketChannel.open();
		sch2.configureBlocking(false);
		sch2.socket().bind(addr2);

		InetSocketAddress addr3 = new InetSocketAddress(2020);
		ServerSocketChannel sch3 = ServerSocketChannel.open();
		sch3.configureBlocking(false);
		sch3.socket().bind(addr3);

		Selector selector = Selector.open();
		sch1.register(selector, SelectionKey.OP_ACCEPT);
		sch2.register(selector, SelectionKey.OP_ACCEPT);
		sch3.register(selector, SelectionKey.OP_ACCEPT);

		while(selector.select() > 0){
			Set keys = selector.selectedKeys();
			Iterator i = keys.iterator();
			while(i.hasNext()){
				SelectionKey key = (SelectionKey)i.next();
				ServerSocketChannel sch = (ServerSocketChannel)key.channel();
				SocketChannel ch = sch.accept();
				handleClient(ch);

				i.remove();
			}
		}
	}
		private static void handleClient(SocketChannel ch){
			int port = ch.socket().getLocalPort();
			System.out.println("listen to client address : " + ch.socket().getInetAddress());
			System.out.println("Port: " + port);
			if(port == 2018){
				writeClient(ch, "HELLO,SERVER GREETING TO CLIENT ONE FROM:" + port + "\n");
			}

			if(port == 2019){
				writeClient(ch, "hello, server greeting to client two from:" + port + "\n");
			}

			if(port == 2020){
				writeClient(ch, "HELLO,SERVER GREETING TO CLIENT THREE FROM:(hello, server greeting to client three from:)" + port + "\n");
			}



		}
		private static void writeClient(SocketChannel client, String message){
				Charset charset = Charset.forName("UTF-8");
				ByteBuffer buf = ByteBuffer.allocate(256);
				try{
					buf = charset.encode(message);
					client.write(buf);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
	

} 