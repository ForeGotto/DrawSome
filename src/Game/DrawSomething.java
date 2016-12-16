package Game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class DrawSomething extends JFrame{
	
	Boolean start=false;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
//	Socket socket;
	
	public DrawSomething() throws UnknownHostException, IOException
	{
		super("Draw Something");
//		socket = new Socket("127.0.0.1", 8000);
		setIconImage(Toolkit.getDefaultToolkit().getImage("picture//图标.jpg"));
		setBounds(115,10,1100,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane=getContentPane();
		contentPane.setLayout(null);
		setMinimumSize(new Dimension(1100,700));
		setResizable(false);
		
		JPanel StartPanel=new JPanel();
		StartPanel.setBounds(0,0,800,700);
		StartPanel.setBackground(Color.white);
		
		ImageIcon backpicture=new ImageIcon("picture//开始.jpg");
		JLabel img=new JLabel(backpicture);
		img.setBounds(0,0,backpicture.getIconWidth(), backpicture.getIconHeight());
		
		StartPanel.setLayout(null);
		JButton startgame=new JButton("开始游戏");
		startgame.setBounds(310,300,100,30);
    	startgame.setFont(getFont());
		StartPanel.add(startgame);
		StartPanel.add(img);
		add(StartPanel);
//		add(new ClientPanel());
//		add(new ChatPanel(socket));
		
		try {
			Socket socket = new Socket("127.0.0.1", 8000);
			System.out.println("local port" + socket.getLocalPort());

			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());

			new Thread(new Runnable() {

				char kind;
				boolean start;
				@Override
				public void run() {
					while (true) {
						try {
							kind = fromServer.readChar();
							switch (kind) {
							case 's':
								start = fromServer.readBoolean();
								break;
								}
							if(start){
								remove(StartPanel);
								start=true;
								add(new FunctionPanel());
								add(new PaintPanel());
								//add(new GuessPanel());
								try {
									add(new GuessPanel());
								} catch (ClassNotFoundException | SQLException e) {
									e.printStackTrace();
								}
								repaint();
								break;
								}
						} catch (IOException e) {
							e.printStackTrace();
						} 
					}
				}
			}).start();
			
			
			//add(new ClientPanel());
			add(new ChatPanel());
			
			setBackground(Color.black);
			repaint();
			setVisible(true);

		} catch (IOException ex) {
			System.out.println(ex.toString() + '\n');
		}

    	
		startgame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean isstart=true;
				if(isstart)
		    	{
					try {
						toServer.writeChar('s');
						toServer.writeBoolean(isstart);
						toServer.flush();
					} catch (IOException ex) {
						System.err.println(ex);
					}
		    	}
				}
		});
		
//		add(new ClientPanel());
//		add(new ChatPanel(socket));
//		
//		setBackground(Color.black);
//		repaint();
//		setVisible(true);
	}
	
	

}