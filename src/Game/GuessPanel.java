package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class GuessPanel extends JPanel{
	
	private JPanel guess,name;
	private String[] names=new String[50];
	private int i=0;
	private JTextArea text;
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	public GuessPanel() throws ClassNotFoundException, SQLException{
		
		
//		setBackground(Color.black);
		setBounds(190, 440, 620, 230);
		setLayout(null);

		guess=new JPanel();
		guess.setBounds(0, 0,620,30);
		guess.setBackground(Color.pink);
		JLabel jl=new JLabel("本轮你选择要猜的词汇有：");
		jl.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		JPanel jp=new JPanel();
		text=new JTextArea();
		text.setBounds(0, 0, 620, 200);
		text.setBackground(Color.white);
		guess.add(jl);
		guess.add(text);
		guess.setLayout(new GridLayout(1,2));
	//	guess.add(jp);
		
		name=new JPanel();
		name.setBounds(0,20,620,200);
		name.setLayout(new GridLayout(10,5));
		name.setBorder(BorderFactory.createEtchedBorder());
		names=getString();
		
		while(i<=49){
		JButton a=new JButton(names[i]);
		a.setBorder(BorderFactory.createEtchedBorder());
		a.addActionListener(new ButtonListener());
		name.add(a);
		i++;	
		System.out.println(i);
		}
		add(guess);
		add(name);
		this.setVisible(true);
		
		try {
			Socket socket = new Socket("127.0.0.1", 8000);
			System.out.println("local port" + socket.getLocalPort());

			toServer = new DataOutputStream(socket.getOutputStream());

			new Thread(new Runnable() {

				char kind;
				boolean isright;
				@Override
				public void run() {
					
					while (true) {
						try {
							fromServer = new DataInputStream(socket.getInputStream());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
//							for(int i=0;i<9999999;i++)
//							{
//								kind = fromServer.readChar();
//								if(kind=='z')
//									break;
//							}
//							System.out.println("读到boolean了吗"+"shi  ");
//							System.out.println(kind);
								switch (kind) {
								case 'z':
									isright=fromServer.readBoolean();
									if(isright==true){
										text.setText("");
										}
									break;
									}
										}
						catch (IOException e ) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		} catch (IOException ex) {
			System.out.println(ex.toString() + '\n');
		}
	
	

		
	}
	

	
	
	public String[]  getString() throws ClassNotFoundException, SQLException{		
		Class.forName("com.mysql.jdbc.Driver"); // 装载驱动				
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/myss","xcq", "qqq");
// 连接Mysql数据库,这里各个参数的含义如下：（jdbc:mysql://localhost/student） localhost代表本机主机，student表示刚才创建的数据库，root是mysql的默认用户，是mysql的密码。
		Statement statement = con.createStatement();
// 根据操作的不同选用不同的方法，如果执行的查询操作，有结果集的返回，在这里选用executeQuery方法，如果是执行的增加、删除、修改，返回的时影响行数，选用executeUpdate（）方法。
		ResultSet rs = statement.executeQuery("select * from word");
		System.out.println("success");
		int i=0;
		while (rs.next())
		{
			names[i]=rs.getString("word");
			i++;
			}
		rs.close();
		con.close();
		return names;
		}

	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton temp=(JButton)e.getSource();
			text.setText(null);
			text.setText(text.getText()+temp.getText());
			try {
				toServer.writeChar('a');
				toServer.writeUTF(text.getText());
				toServer.flush();
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
		
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new GuessPanel();
	}

}
