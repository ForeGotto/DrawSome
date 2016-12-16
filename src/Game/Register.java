package Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import java.sql.*;
public class Register extends JFrame {
	   private JPanel usernamePanel,passwordPanel1,passwordPanel2,selectPanel,buttonPanel;
	   private JLabel usernameLabel,passwordLabel1,passwordLabel2,selectLabel;
	   private JPasswordField  passwordField1,passwordField2;
	   private JTextField usernameField;
	   private JButton confirmButton,cancelButton;
      //  private Container reg;
       private picturePanel reg;
	   private JComboBox select;
	   public Register(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		this.setTitle("用户注册");
		setSize(500,340);
		setLocationRelativeTo(null);
		setResizable(false);
		//用户名Panel		
		usernamePanel = new JPanel();
		usernamePanel.setOpaque(false);
		usernameLabel = new JLabel("用  户  名：");
		usernameLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
		usernameField = new JTextField(10);
		usernameField.setFont(new Font("微软雅黑",Font.PLAIN,15));	
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameField);
		//密码Panel1
		passwordPanel1 = new JPanel();
		passwordPanel1.setOpaque(false);
		passwordLabel1 = new JLabel("输入密码：");
		passwordField1 = new JPasswordField(10);
		passwordLabel1.setFont(new Font("微软雅黑",Font.PLAIN,15));
		passwordField1.setFont(new Font("微软雅黑",Font.PLAIN,15));
		passwordPanel1.add(passwordLabel1);
		passwordPanel1.add(passwordField1);
		//密码Panel2
		passwordPanel2 = new JPanel();
		passwordPanel2.setOpaque(false);
		passwordLabel2 = new JLabel("确认密码：");
		passwordLabel2.setFont(new Font("微软雅黑",Font.PLAIN,15));
		passwordField2 = new JPasswordField(10);
		passwordField2.setFont(new Font("微软雅黑",Font.PLAIN,15	));
		passwordPanel2.add(passwordLabel2);
		passwordPanel2.add(passwordField2);
		
	
		//按钮
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		confirmButton = new JButton("确 认");
		cancelButton = new JButton("取 消");
		confirmButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
		cancelButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
    	confirmButton.addActionListener(new buttonListener());
		cancelButton.addActionListener(new buttonListener());
		JLabel jl=new JLabel("           ");
		buttonPanel.add(confirmButton);
		buttonPanel.add(jl);
		buttonPanel.add(cancelButton);
		confirmButton.setPreferredSize(new Dimension(80,30));
		cancelButton.setPreferredSize(new Dimension(80,30));
		//容器
	    reg= new picturePanel();
	    reg.setLayout(new GridLayout(4,1));
	    reg.add(usernamePanel);
		reg.add(passwordPanel1);
		reg.add(passwordPanel2);
		reg.add(buttonPanel);
		this.add(reg);
		setVisible(true);
	}

	private class picturePanel extends JPanel{
		ImageIcon icon;
		Image img;
		
		public picturePanel(){
			icon = new ImageIcon("picture/注册.jpg");
			img = icon.getImage();
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0,0,this);
		}
	}
	private class buttonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String userName = usernameField.getText();
			String password1 =new String(passwordField1.getPassword());
			String password2 = new String(passwordField2.getPassword());
			if(e.getSource().equals(confirmButton)){
				if(userName.isEmpty()){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "用户名不能为空","警告！",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(!(password1.equals(password2))){
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null,"两次密码输入不一致","警告！",JOptionPane.WARNING_MESSAGE);
					return ;
				}	
				else{
					DbConnect dbcon=new DbConnect();
					boolean boole=dbcon.register(userName, password1);
					if(boole){
						setVisible(false);
					}
					//new sqlRegister(userName, password1);
					
				}
			}
			else if(e.getSource().equals(cancelButton)){
				setVisible(false);
			}
		}
		
	}

	
	     public static void main(String[] args){
	    	 Register re= new Register();
	     }
}
