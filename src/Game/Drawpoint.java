package Game;

import java.awt.Point;
import java.awt.Color;
import java.io.Serializable;

public class Drawpoint extends Point implements Serializable{
	
	private int x,y;
	private int isline=0;
	private Color color;
	private float width;
	private boolean eraser;

		public Drawpoint(int x2, int y2, int line) 
	{
		x=x2;
		y=y2;
		isline=line;
	}
		
		public Drawpoint(int x2,int y2,int line,Color acolor)
	{
			x=x2;
			y=y2;
		isline=line;
		color=acolor;
	}
		public Drawpoint(int x2,int y2,int line,Color acolor,float awidth)
		{
			x=x2;
			y=y2;
			isline=line;
			color=acolor;
			width=awidth;
		}
		public Drawpoint(int x2,int y2,int line,Color acolor,float awidth,boolean aersaer)
		{
			x=x2;
			y=y2;
			isline=line;
			color=acolor;
			width=awidth;
			eraser=aersaer;
		}
		public String toString()
		{
			String str="x="+x+",y="+y+",line="+isline+",color="+color+",width="+width+",eraser="+eraser;
			return str;
		}
		

	
	//»­Ïß
	public void setline(int line)
	{
		isline=line;
	}
	public int getisline()
	{
		return isline;
	}
	//ÑÕÉ«
	public void setcolor(Color wcolor)
	{
		color=wcolor;
	}
	public Color getcolor()
	{
		return color;
	}
	//´ÖÏ¸
	public void setwidth(float awidth)
	{
		width=awidth;
	}
	public float getwidth()
	{
		return width;
	}
	//ÏðÆ¤
	public void seteraser(boolean aeraser)
	{
		eraser=aeraser;
	}
	public boolean geteraser()
	{
		return eraser;
	}
	
	
	public int x()
	{
		return x;
	}
	public int y()
	{
		return y;
	}

}
