package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class FunctionPanel extends JPanel{
	private JPanel colorPanel,fuctionPanel,widthPanel;
	private JButton green,pink,blue,black,yellow,red,orange,white,magenta,cyan,lightGray,defined;
	private JButton eraser,ink,pen;
	private JButton width1,width3,width5,width7,width9,defined2;
	public FunctionPanel()
	{	
		setBounds(0,30,190,600);
		
		colorPanel=new JPanel();
		colorPanel.setPreferredSize(new Dimension(130,300));
		colorPanel.setLayout(new GridLayout(6,2));
		colorPanel.setBorder(BorderFactory.createEtchedBorder());
		
		//调色
		green=new JButton();
		green.setBackground(Color.green);
		green.setBorder(BorderFactory.createEtchedBorder());
		green.addActionListener(new ButtonListener());
		pink=new JButton();
		pink.setBackground(Color.pink);
		pink.setBorder(BorderFactory.createEtchedBorder());
		pink.addActionListener(new ButtonListener());
		blue=new JButton();
		blue.setBackground(Color.blue);
		blue.setBorder(BorderFactory.createEtchedBorder());
		blue.addActionListener(new ButtonListener());
		black=new JButton();
		black.setBackground(Color.black);
		black.setBorder(BorderFactory.createEtchedBorder());
		black.addActionListener(new ButtonListener());
		yellow=new JButton();
		yellow.setBackground(Color.yellow);
		yellow.setBorder(BorderFactory.createEtchedBorder());
		yellow.addActionListener(new ButtonListener());
		red=new JButton();
		red.setBackground(Color.red);
		red.setBorder(BorderFactory.createEtchedBorder());
		red.addActionListener(new ButtonListener());
		orange=new JButton();
		orange.setBackground(Color.orange);
		orange.setBorder(BorderFactory.createEtchedBorder());
		orange.addActionListener(new ButtonListener());
		white=new JButton();
		white.setBackground(Color.white);
		white.setBorder(BorderFactory.createEtchedBorder());
		white.addActionListener(new ButtonListener());
		magenta=new JButton();
		magenta.setBackground(Color.magenta);
		magenta.setBorder(BorderFactory.createEtchedBorder());
		magenta.addActionListener(new ButtonListener());
		cyan=new JButton();
		cyan.setBackground(Color.cyan);
		cyan.setBorder(BorderFactory.createEtchedBorder());
		cyan.addActionListener(new ButtonListener());
		lightGray=new JButton();
		lightGray.setBackground(Color.lightGray);
		lightGray.setBorder(BorderFactory.createEtchedBorder());
		lightGray.addActionListener(new ButtonListener());
		defined=new JButton("自定义");
		defined.setBorder(BorderFactory.createEtchedBorder());
		defined.addActionListener(new ButtonListener());
		
		colorPanel.add(green);
		colorPanel.add(yellow);
		colorPanel.add(pink);
		colorPanel.add(white);
		colorPanel.add(cyan);
		colorPanel.add(blue);
		colorPanel.add(orange);
		colorPanel.add(red);
		colorPanel.add(magenta);
		colorPanel.add(black);
		colorPanel.add(lightGray);
		colorPanel.add(defined);
		add(colorPanel);
		
		
		fuctionPanel=new JPanel();
		fuctionPanel.setPreferredSize(new Dimension(130,45));
		fuctionPanel.setLayout(new GridLayout(1,3));
		fuctionPanel.setBorder(BorderFactory.createEtchedBorder());
		
		eraser=new JButton();
		ImageIcon icon1=new ImageIcon("picture/eraser.png");
		eraser.setIcon(icon1);
		eraser.setBackground(Color.white);
		eraser.setBorder(BorderFactory.createEtchedBorder());
		eraser.addActionListener(new ButtonListener());
		ink=new JButton("ink");
		ink.setBorder(BorderFactory.createEtchedBorder());
		ink.addActionListener(new ButtonListener());
		pen=new JButton("pen");
		ImageIcon icon2=new ImageIcon("picture/pen.png");
		pen.setIcon(icon2);
		pen.setBackground(Color.white);
		pen.setBorder(BorderFactory.createEtchedBorder());
		pen.addActionListener(new ButtonListener());
		
		fuctionPanel.add(eraser);
		fuctionPanel.add(pen);
		fuctionPanel.add(ink);
		add(fuctionPanel);
		
		
		widthPanel=new JPanel();
		widthPanel.setPreferredSize(new Dimension(130,220));
		widthPanel.setLayout(new GridLayout(6,1));
		widthPanel.setBorder(BorderFactory.createEtchedBorder());
		
		
		width1=new JButton("*1");
		width1.setBorder(BorderFactory.createEtchedBorder());
		width1.addActionListener(new ButtonListener());
		width3=new JButton("*3");
		width3.setBorder(BorderFactory.createEtchedBorder());
		width3.addActionListener(new ButtonListener());
		width5=new JButton("*5");
		width5.setBorder(BorderFactory.createEtchedBorder());
		width5.addActionListener(new ButtonListener());
		width7=new JButton("*7");
		width7.setBorder(BorderFactory.createEtchedBorder());
		width7.addActionListener(new ButtonListener());
		width9=new JButton("*9");
		width9.setBorder(BorderFactory.createEtchedBorder());
		width9.addActionListener(new ButtonListener());
		defined2=new JButton("自定义");
		defined2.setBorder(BorderFactory.createEtchedBorder());
		defined2.addActionListener(new ButtonListener());
		
		widthPanel.add(width1);
		widthPanel.add(width3);
		widthPanel.add(width5);
		widthPanel.add(width7);
		widthPanel.add(width9);
		widthPanel.add(defined2);
		add(widthPanel);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==green){
				PaintPanel.setcolor(Color.green);PaintPanel.seteraser(false);}
			if(event.getSource()==pink)
				PaintPanel.setcolor(Color.pink);PaintPanel.seteraser(false);
			if(event.getSource()==blue)
				PaintPanel.setcolor(Color.blue);PaintPanel.seteraser(false);
			if(event.getSource()==black)
				PaintPanel.setcolor(Color.black);PaintPanel.seteraser(false);
			if(event.getSource()==yellow)
				PaintPanel.setcolor(Color.yellow);PaintPanel.seteraser(false);
			if(event.getSource()==red)
				PaintPanel.setcolor(Color.red);PaintPanel.seteraser(false);
			if(event.getSource()==orange)
				PaintPanel.setcolor(Color.orange);PaintPanel.seteraser(false);
			if(event.getSource()==white)
				PaintPanel.setcolor(Color.white);PaintPanel.seteraser(false);
			if(event.getSource()==magenta)
				PaintPanel.setcolor(Color.magenta);PaintPanel.seteraser(false);
			if(event.getSource()==cyan)
				PaintPanel.setcolor(Color.cyan);PaintPanel.seteraser(false);
			if(event.getSource()==lightGray)
				PaintPanel.setcolor(Color.lightGray);PaintPanel.seteraser(false);
			if(event.getSource()==defined)
			{
				Color shade=Color.white;
				shade=JColorChooser.showDialog(null,"Pick a Color!",shade);
				PaintPanel.setcolor(shade);
					defined.setBackground(shade);
					PaintPanel.seteraser(false);
			}
			if(event.getSource()==eraser)
			{
				PaintPanel.seteraser(true);
			}
			if(event.getSource()==pen)
			{
				PaintPanel.setlwidth(1);
				PaintPanel.setpwidth(2);
				}
			if(event.getSource()==ink)
			{
				PaintPanel.setlwidth(800);
				PaintPanel.setpwidth(800);
				}
			if(event.getSource()==width1)
			{
				PaintPanel.setlwidth(0.5f);
				PaintPanel.setpwidth(1);
				}
			if(event.getSource()==width3)
			{
				PaintPanel.setlwidth(3);
				PaintPanel.setpwidth(3);
				}
			if(event.getSource()==width5)
			{
				PaintPanel.setlwidth(5);
				PaintPanel.setpwidth(5);
				}
			if(event.getSource()==width7)
			{
				PaintPanel.setlwidth(7);
				PaintPanel.setpwidth(7);
				}
			if(event.getSource()==width9)
			{
				PaintPanel.setlwidth(9);
				PaintPanel.setpwidth(9);
				}
			if(event.getSource()==defined2)
			{
				float a;
				String num=JOptionPane.showInputDialog(null,"输入一个宽度\n（1~200）","Pick a number!",JOptionPane.QUESTION_MESSAGE);
				a=Float.parseFloat(num);
				PaintPanel.setlwidth(a);
				PaintPanel.setpwidth(a);
				if(a==1)
					PaintPanel.setlwidth(0.5f);
				}
		}
	}

}
