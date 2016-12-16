package Game;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread.State;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

	public class PaintPanel extends JPanel
	{
		private static Color color=Color.black;
		private static float linewidth=1f;
		private static float pointwidth=2f;
		ArrayList<Drawpoint> pointList=new ArrayList<Drawpoint>();
		ArrayList<Drawpoint> readpointList=new ArrayList<Drawpoint>();
		private static boolean eraser=false;
		private DataOutputStream toServer;
		private DataInputStream fromServer;
		private static Boolean isstart=false;
		private TimerLabel a;
		
	public PaintPanel()
	{
		addMouseListener(new DotsListener());
		addMouseMotionListener(new LineListener());
		setBackground(Color.white);
		setPreferredSize(new Dimension(700,500));
		setBounds(190,35,620,400);
		setLayout(null);
		isstart=true;
		while(true)
		{
			if(isstart)
			{
				start(false);
				removeAll();
			pointList.clear();
			a=new TimerLabel(30);
			add(a);
			a.start();
			if(!a.isStart()){
//				JOptionPane.showConfirmDialog(null,"Time up!","Continue£¿",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
				
			}
			}
			else
				break;
			}

    	
		try {
			Socket socket = new Socket("127.0.0.1", 8000);
			System.out.println("drawlocal port" + socket.getLocalPort());
			toServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	
			new Thread(new Runnable() {
				
				char kind;
				int x, y, line,rgb;
				float linewidth;
				boolean eraser;

				@Override
				public void run() {
					while (true) {

						try {
							fromServer = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							kind = fromServer.readChar();
								switch (kind) {
								case 'd':
									x=fromServer.readInt();
									y=fromServer.readInt();
									line=fromServer.readInt();
									rgb=fromServer.readInt();
									linewidth=fromServer.readFloat();
									eraser=fromServer.readBoolean();
									Color color= new Color(rgb);
									Drawpoint point=new Drawpoint(x,y,line,color,linewidth,eraser);
									readpointList.add(point);
									repaint();
									break;
									}
										}
									catch (IOException e) {
								e.printStackTrace();
								}
						}
					
				}
			}).start();
			}
		catch (IOException ex) {
			System.out.println(ex.toString() + '\n');
			}
		}

		


	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		Graphics2D page2d=(Graphics2D)page;
		int i=1;
		while(i<readpointList.size())
		{	
			Drawpoint p1=readpointList.get(i-1);
			Drawpoint p2=readpointList.get(i);
			float radius=p2.getwidth();
			int r2=(int) radius;
			page.setColor(p2.getcolor());
			if(p2.getisline()!=3&&p2.getisline()!=2)
				if(p2.geteraser())
				{page2d.setColor(Color.white);
				page2d.fillRect(p2.x()-r2, p2.y()-r2, r2*2, r2*2);
				}
				else
			page2d.fillOval(p2.x()-r2, p2.y()-r2, r2*2, r2*2);
		
			if(p1.getisline()==1||p1.getisline()==0&&p2.getisline()==1)
			{if(p2.geteraser())
				page.setColor(Color.white);
			page2d.setStroke(new BasicStroke(radius*2,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
			page2d.drawLine(p1.x(),p1.y(),p2.x(),p2.y());
			i++;
			}
			else
			{
				i++;
				}
		}
		
	}
	
		public void setback(Color wcolor)
		{
			setBackground(wcolor);
		}
	
	    //ÑÕÉ«
		public static void setcolor(Color wcolor)
		{
			color=wcolor;
		}
		public static Color getcolor()
		{
			return color;
		}
		//Ïß´ÖÏ¸
		public static void setlwidth(float awidth)
		{
			linewidth=awidth;
		}
		public float getlwidth()
		{
			return linewidth;
		}
		//µã´ÖÏ¸
		public static void setpwidth(float awidth)
		{
			pointwidth=awidth;
		}
		public float getpwidth()
		{
			return pointwidth;
		}
		//ÏðÆ¤
		public static void seteraser(boolean aeraser)
		{
			eraser=aeraser;
		}
		public boolean geteraser()
		{
			return eraser;
		}
		public static void start(boolean a)
		{
			isstart=a;
		}
	
	
	//Êó±ê¼àÌý
	private class LineListener implements MouseMotionListener
	{

		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			Drawpoint a=new Drawpoint(e.getX(),e.getY(),1,color,linewidth,eraser);
			pointList.add(a);
			try {
				toServer.writeChar('d');
				toServer.writeInt(e.getX());
				toServer.writeInt(e.getY());
				toServer.writeInt(1);
				toServer.writeInt(color.getRGB());
				toServer.writeFloat(linewidth);
				toServer.writeBoolean(eraser);
				
				toServer.flush();
			} catch (IOException ex) {
				System.err.println(ex);
			}
			repaint();
		}

		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private class DotsListener implements MouseListener
	{
		public void mousePressed(MouseEvent event)
		{
			Drawpoint a=new Drawpoint(event.getX(),event.getY(),0,color,pointwidth,eraser);
				pointList.add(a);
			try {
				toServer.writeChar('d');
				toServer.writeInt(event.getX());
				toServer.writeInt(event.getY());
				toServer.writeInt(0);
				toServer.writeInt(color.getRGB());
				toServer.writeFloat(linewidth);
				toServer.writeBoolean(eraser);
				toServer.flush();
			} catch (IOException ex){
				System.err.println(ex);
			}
			repaint();
		}

		
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent event2) {
			Drawpoint a=new Drawpoint(event2.getX(),event2.getY(),2,color,linewidth,eraser);
			pointList.add(a);
			try {
				toServer.writeChar('d');
				toServer.writeInt(event2.getX());
				toServer.writeInt(event2.getY());
				toServer.writeInt(2);
				toServer.writeInt(color.getRGB());
				toServer.writeFloat(linewidth);
				toServer.writeBoolean(eraser);
				toServer.flush();
				
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
			repaint();
		}
	}



	
}