package Game;

import java.awt.Color;
import java.awt.Font;
import java.lang.Thread.State;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public 	class TimerLabel extends JLabel {

    private int maxTime;
    private int count;
    private static final int SECOND = 1000;
    private static final int FONT_SIZE = 36;
    private Thread thread;
    private boolean pause;
    private boolean start;
    private boolean acountinue;

    /**
     * 新建一个计时标签
     * @param maxTime 倒计时起始时间
     */
    public TimerLabel(int maxTime) {
        this.setHorizontalAlignment(JLabel.CENTER);
    	setBounds(200, 0, 200, 80);
    	setBackground(Color.green);
        this.setFont(new Font("Times New Roman", Font.BOLD, FONT_SIZE));
        this.pause = false;
        setMaxTime(maxTime);
    }

    /**
     * 修改倒计时起始时间
     * @param maxTime 新的起始时间
     */
    public void setMaxTime(int maxTime) {
        if (this.start) {
            return;
        }
        this.maxTime = maxTime;
        this.count = maxTime;
        initText();
        this.thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true)
                {
                	if(count!=0)
                	{
                		try {
                			if (!start) {
                				count = 0;
                				initText();
                				break;
                			}
                			if (!pause) {
                				Thread.sleep(SECOND);
                				count--;
                				initText();
                			}
                		} catch (InterruptedException ex) {
                			pause = true;
                		}
                	}
                	else
                		count=30;
                }
                done();
                
            }
        });
        this.start = false;
    }

    /**
     * 倒计时完成后调用此方法
     */
    protected void done() {
        JOptionPane.showMessageDialog(labelFor, "Time up!");
        
        
    }

    /**
     * 标签字符由此方法设置
     */
    protected void initText() {
        String min = String.valueOf(count / 60);
        String sec = String.valueOf(count % 60);
        while (min.length() < 2) {
            min = "0" + min;
        }
        while (sec.length() < 2) {
            sec = "0" + sec;
        }
        this.setText(min + ":" + sec);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (start) {
            thread.interrupt();
        }
    }
    
    /**
     * 检测标签倒计时是否开始
     * @return 如果开始返回true
     */
    public boolean isStart() {
        return start;
    }
    
    /**
     * 得到倒计时起始时间
     * @return 倒计时起始时间
     */
    public int getMaxTime() {
        return maxTime;
    }
    
    /**
     * 检测标签倒计时是否暂停
     * @return 倒计时暂停返回true
     */
    public boolean isPause() {
        return pause;
    }
    
    /**
     * 从暂停中恢复计时
     */
    public void continueDo() {
        if (this.pause) {
            this.pause = false;
        }
    }
    
    
    /**
     * 取消计时
     */
    public void stop() {
        if (start) {
            start = false;
        }
    }


    /**
     * 开始计时
     */
    public void start() {
        if (thread.getState().equals(State.NEW)) {
            start = true;
            thread.start();
        } else if (thread.getState().equals(State.TERMINATED)) {
            setMaxTime(maxTime);
            start = true;
            thread.start();
        }
    }
}
