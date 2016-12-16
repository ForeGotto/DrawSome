package Game;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.*;
import com.sun.javafx.tk.Toolkit;
public class DbConnect {
	String name,password;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	String word1,word2;
	public DbConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		try {			
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myss","xcq", "qqq");
		
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	public void logincheck(String accountStr,String passwordStr){
		
		// �ж��˺Ż������Ƿ�Ϊ��
		if (accountStr.isEmpty() || passwordStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "�˺Ż�����Ϊ��");
			return ;
		}
		String sql = "select * from login";
		  try {
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();	
			int count = 0;
			while (rs.next())
				count++;
			rs = pstm.executeQuery();
			//���ڴ洢 �˺������count �� 2�е� object ���͵� ��λ����
			Object[][] Info = new Object[count][2];
			
			count = 0;
			while (rs.next()) {
				Info[count][0] = rs.getString("name");
				Info[count][1] = rs.getString("password");
				count++;
			}
			boolean select = true;

	
			for (int i = 0; i < count; i++) {
				if (Info[i][0].equals(accountStr)
						&& Info[i][1].equals(passwordStr)) {
					select =false;
				//	JOptionPane.showMessageDialog(null, "��½�ɹ�");
					 new DrawSomething();
					break;
				}
			}
	
			if (select) {

				JOptionPane.showMessageDialog(null, "�˺Ż��������");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	public boolean register(String name,String password){
       String sql1="select name from login where name='"+name+"'";
		
		String sql = "insert into login(name,password)values('"+name+"','"+password+"')";
		boolean have = false;// ����У���˺��Ƿ����
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql1);
			if(result.next()){
				have=true;
				JOptionPane.showMessageDialog(null, "���˺��Ѿ�����");
				return false;
			}
			else{
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "ע��ɹ�");
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return false;
	}
	public void close(){
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
