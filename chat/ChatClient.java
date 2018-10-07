import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ChatClient extends JPanel{
	private static final int port = 2018;
	protected Socket sock;
	protected JTextArea receivedText;
	private GridBagConstraints c;
	private GridBagLayout grudBag;
	private JFrame frame;
	private JLabel label;
	private JButton exit;

	private JTextField sendText;
	private String hostname;
	private String username;
	private DataOutputStream remoteOut;

	public static void main(String args []){
		JFrame frame = new JFrame("Connection to " + args[0]);
		ChatClient chat = new ChatClient(frame, args[0], "localhost");
		frame.add("Center", chat);
		frame.setSize(350, 600);
		frame.setResizable(true);
		frame.setVisible(true);
		chat.client();
	}

	public ChatClient(JFrame frame, String user, String host){
		this.frame = frame;
		frame.addWindowListener(new WindowExitHandler());
		username = user;
		hostname = host;
		label = new JLabel("input");
		add(label);
		sendText = new JTextField(30);
		add(sendText);
		label = new JLabel("chat");
		add(label);
		receivedText = new JTextArea(25, 30);
		receivedText.setLineWrap(true);
		receivedText.setWrapStyleWord(true);
		JScrollPane receivedScroll = new JScrollPane(receivedText);
		add(receivedScroll);
		exit = new JButton("Exit");
		add(exit);
		sendText.addActionListener(new TextActionHandler());
		exit.addActionListener(new EXIT());
	}

	void client(){
		try{
			if(hostname.equals("local"))
				hostname = null;
			InetAddress serverAddr = InetAddress.getByName(hostname);
			sock = new Socket(serverAddr.getHostName(), port);
			remoteOut = new DataOutputStream(sock.getOutputStream());
			System.out.println("Connected to server " + serverAddr.getHostName() + " on port " + sock.getPort() + "user: " + username);
			new ChatClientReceive(this).start();

		}
		catch(IOException e){
			System.out.println(e.getMessage() + "Failed to connect to server.");
		}
	}

	class WindowExitHandler extends WindowAdapter{
		public void windowClosing(WindowEvent e){
			Window w = e.getWindow();
			w.setVisible(false);
			w.dispose();
			System.exit(0);
		}
	}
	class EXIT implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JOptionPane.showMessageDialog(null, "good bye");
			System.exit(0);
		}
	}

	class TextActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				if(e.getSource() == sendText){
					remoteOut.writeUTF(username + " -> " + sendText.getText());
					receivedText.append("\n" + username + " -> " + sendText.getText());
					sendText.setText("");
				}
			}
			catch(IOException x){
				System.out.println(x.getMessage() + " : connection to peer lost.");
			}
		}
	}
}

class ChatClientReceive extends Thread{
	private ChatClient chat;
	ChatClientReceive(ChatClient chat){
		this.chat = chat;

	}
	public synchronized void run(){
		String s;
		DataInputStream remoteIn = null;
		try{
			remoteIn = new DataInputStream(chat.sock.getInputStream());
			while(true){
				s = remoteIn.readUTF();
				chat.receivedText.append("\n" + s);
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage() + " : connection to peer lost.");
		}
	}
}