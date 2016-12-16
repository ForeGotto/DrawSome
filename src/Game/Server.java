package Game;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame {
	private JTextArea jta = new JTextArea();
	private ArrayList<Socket> listeners = new ArrayList<>();
	private int clientNo = 1;
	private String[] answers=new String[5];
	private boolean start=false;
	private boolean answerright=false;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		answers[0]="弓箭";answers[1]="铃铛";answers[2]="花瓣";answers[3]="电灯";answers[4]="豆芽";
		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server started at " + new Date() + '\n');


			while (true) {
				
				Socket socket = serverSocket.accept();

				InetAddress inetAddress = socket.getInetAddress();
				jta.append("Client " + clientNo + "'s IP name is "
						+ inetAddress.getHostAddress() + "\n");
				
				listeners.add(socket);
				Chat chat=new Chat(socket);
				new Thread(chat).start();
				DataOutputStream a=new DataOutputStream(socket.getOutputStream());
				
				jta.append("Client " + clientNo + "'s IP name is "
						+socket.getPort()  + "\n");

				clientNo++;
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

	class Chat implements Runnable {

		private DataInputStream inputFromClient;
		private DataOutputStream outputToClient;
		private char kind;
		private Socket socket;
		private int n=0;
		
		public Chat(Socket socket) {
			this.socket = socket;
			try {
				inputFromClient = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				
				while (true) {
					
					if(listeners.size()==2){
					outputToClient = new DataOutputStream(listeners.get(1).getOutputStream());
					outputToClient.writeChar('c');
					outputToClient.writeUTF("答案为： "+answers[0]);
					outputToClient.flush();
					}
                    /**
                     * @TODO 在这里把判断改为循环判断9个词语
                     */
					kind = inputFromClient.readChar();
					switch (kind) {
					case 'c':
						String chat = inputFromClient.readUTF();
					for (int i = 0; i < listeners.size(); i++) {
						Socket c = listeners.get(i);
						outputToClient = new DataOutputStream(c.getOutputStream());
						outputToClient.writeChar('c');
						outputToClient.writeUTF(chat);
						outputToClient.flush();
						}
					jta.append(chat + '\n');
						break;
					case 'a':
						String answer = inputFromClient.readUTF();
						jta.append(answer+'\n');
						if(answers[n].equals(answer)){
						answerright=true;
							jta.append(""+answerright);
							System.out.println("1");
							for (int i = 0; i < listeners.size(); i++) {
								Socket c = listeners.get(i);
								outputToClient = new DataOutputStream(c.getOutputStream());
								outputToClient.writeChar('z');
								System.out.println('z');
								outputToClient.writeBoolean(true);
								outputToClient.flush();
								}
							n++;	
							}
						if(answerright){
							for (int i = 0; i < listeners.size();i++)
							{
								if(i==1)
								{
							outputToClient = new DataOutputStream(listeners.get(1).getOutputStream());
							outputToClient.writeChar('c');
							String s="答案为"+answers[n];
							outputToClient.writeUTF(s);
							outputToClient.flush();
								}
//								else
//								{
//									outputToClient = new DataOutputStream(listeners.get(i).getOutputStream());
//									outputToClient.writeChar('c');
//									outputToClient.writeUTF(" ");
//									outputToClient.flush();
//								}
							}
							for (int i = 0; i < listeners.size();i++) {
								Socket c = listeners.get(i);
								outputToClient = new DataOutputStream(c.getOutputStream());
								outputToClient.writeChar('c');
								outputToClient.writeUTF("You get it!");
								outputToClient.flush();
//								answerright=false;
								}
							answerright=false;
							}
							
//						}
							
//						}
						break;
					case 'd':
						int x,y,line,rgb;
						float linewidth;
						boolean eraser;
						x = inputFromClient.readInt();
						y = inputFromClient.readInt();
						line = inputFromClient.readInt();
						rgb = inputFromClient.readInt();
						linewidth = inputFromClient.readFloat();
						eraser = inputFromClient.readBoolean();
						for (int i = 0; i < listeners.size(); i++) {
							Socket c = listeners.get(i);
							outputToClient = new DataOutputStream(new BufferedOutputStream(c.getOutputStream()));
								outputToClient.writeChar('d');
								outputToClient.writeInt(x);
								outputToClient.writeInt(y);
								outputToClient.writeInt(line);
								outputToClient.writeInt(rgb);
								outputToClient.writeFloat(linewidth);
								outputToClient.writeBoolean(eraser);
								outputToClient.flush();
						}
						jta.append(socket.getPort()+"x=" + x + " y=" + y + " line=" + line+ " rgb=" + rgb + '\n'+"linewidth=" + linewidth + '\n'+"eraser=" + eraser + '\n');
						break;
					case 's':
						boolean start = inputFromClient.readBoolean();
					for (int i = 0; i < listeners.size(); i++) {
						Socket c = listeners.get(i);
						outputToClient = new DataOutputStream(c.getOutputStream());
						outputToClient.writeChar('s');
						outputToClient.writeBoolean(start);
						outputToClient.flush();
						}
						break;
						}
					}
				} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

}
