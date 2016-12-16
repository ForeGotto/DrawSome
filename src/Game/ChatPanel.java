package Game;

import java.awt.Color;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatPanel extends JPanel {

	JTextField inputText = new JTextField();
	JTextArea ContentText = new JTextArea();
	private JScrollPane scroll = new JScrollPane(ContentText);
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private JButton send;
//	static Socket socket;

	public ChatPanel() {

		// addMouseListener(new DotsListener());
		// addMouseMotionListener(new a());
		setBackground(Color.black);
		setBounds(840, 230, 220, 380);
		setLayout(null);

		inputText.setBounds(0, 355, 180, 25);
		inputText.addActionListener(new TextListener());
		ContentText.setBackground(Color.white);
		ContentText.setEditable(false);
		ContentText.setLineWrap(true);
		ContentText.setMargin(new Insets(5, 5, 10, 10));
		scroll.setBounds(0, 0, 220, 356);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(ContentText);
		send = new JButton("s");
		send.setBounds(180, 355, 40, 25);
		send.addActionListener(new TextListener());

		add(inputText);
		add(send);
		add(scroll);

		try {
			Socket socket = new Socket("127.0.0.1", 8000);
			System.out.println("local port" + socket.getLocalPort());

			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());

			new Thread(new Runnable() {

				char kind;
				String chat;
				@Override
				public void run() {
					while (true) {
						try {
							
							kind = fromServer.readChar();
							
							switch (kind) {
							case 'c':
								chat = fromServer.readUTF();
								System.out.println("aaa");
								if(!chat.equals(""))
								{
									ContentText.append(chat + "\n");
								}
								break;
								}
							/*SwingUtilities.invokeLater(new Runnable() {
								@Override
								public void run() {
									if(!chat.equals(""))
									ContentText.append(chat + "\n");
								}
							});*/
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		} catch (IOException ex) {
			ContentText.append(ex.toString() + '\n');
		}

	}

	private class TextListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == inputText) {
				String str = inputText.getText().trim();

				try {
					toServer.writeChar('c');
					toServer.writeUTF(str);
					inputText.setText(null);
					toServer.flush();
				} catch (IOException ex) {
					System.err.println(ex);
				}

			}

			if (event.getSource() == send) {
				String str = inputText.getText().trim();
				try {
					toServer.writeChar('c');
					toServer.writeUTF(str);
					inputText.setText(null);
					toServer.flush();
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
		}
	}

}
