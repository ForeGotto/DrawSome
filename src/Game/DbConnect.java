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
		
		// 判断账号或密码是否为空
		if (accountStr.isEmpty() || passwordStr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "账号或密码为空");
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
			//用于存储 账号密码的count 行 2列的 object 类型的 二位数组
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
				//	JOptionPane.showMessageDialog(null, "登陆成功");
					 new DrawSomething();
					break;
				}
			}
	
			if (select) {

				JOptionPane.showMessageDialog(null, "账号或密码错误");
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
		boolean have = false;// 用于校验账号是否存在
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(sql1);
			if(result.next()){
				have=true;
				JOptionPane.showMessageDialog(null, "该账号已经存在");
				return false;
			}
			else{
			stmt.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "注册成功");
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
