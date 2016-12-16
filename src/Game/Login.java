package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Login extends JFrame {
    private JPanel FontPanel, accountPanel, passwordPanel, selectPanel, validatePanel,
            buttonPanel, registerPanel;
    private JLabel FontLabel, accountLabel, passwordLabel, selectLabel,
            RegisteredLabel, validatelLabel;
    private JTextField account, validateField;
    private JPasswordField psword;
    private JComboBox select;
    //  private captchaLabel label;
    private JButton confirm, cancel;
    private picturePanel log;
    private String accountStr, passwordStr, validateStr, randomText;
    private captchaLabel label;

    //  private String accountStr, randomText, validateStr;
    public Login() {

        super("登陆");
//		WebLookAndFeel.globalControlFont  = new FontUIResource("微软雅黑",0, 12);
//		WebLookAndFeel.install();
        try {
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        this.setTitle("你画我猜");
        this.setSize(600, 450);
        setLocationRelativeTo(null);//设置JFrame 出现时居中
        setResizable(true);//不能 改变窗口大小

        // account Panel
        accountPanel = new JPanel();
        accountPanel.setOpaque(false);
        //accountPanel.setLayout(new GridLayout(2,6));
        accountLabel = new JLabel("用户名:");
        account = new JTextField(10);
        accountPanel.add(accountLabel);
        accountPanel.add(account);
        accountLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        account.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        //password Panel
        passwordPanel = new JPanel();
        passwordPanel.setOpaque(false);
        psword = new JPasswordField(10);
        passwordLabel = new JLabel("密    码:");// 多几个空格就能对齐
        passwordPanel.add(passwordLabel);
        passwordPanel.add(psword);
        passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        psword.setFont(new Font("微软雅黑", Font.PLAIN, 15));

        //validatePannel
        validatePanel = new JPanel();
        validatePanel.setOpaque(false);
        validatelLabel = new JLabel("验证码:");
        validatelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        validateField = new JTextField(5);
        validateField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        randomText = getRandomString(5);
        label = new captchaLabel(randomText);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        validatePanel.add(validatelLabel);
        validatePanel.add(validateField);
        validatePanel.add(label);
        // register
        registerPanel = new JPanel();
        registerPanel.setOpaque(false);
        RegisteredLabel = new JLabel("还没有账号？");
        RegisteredLabel.setFont(new Font("宋体", Font.BOLD, 15));
        RegisteredLabel.setForeground(Color.green);
        RegisteredLabel.setBackground(Color.BLACK);

        RegisteredLabel.addMouseListener(new labelListener());

        registerPanel.add(RegisteredLabel);


        // Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        confirm = new JButton("登录");
        confirm.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        //confirm.setBackground(Color.blue);
        cancel = new JButton("退出");
        cancel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        //添加按钮监听
        confirm.addActionListener(new buttonListener());
        cancel.addActionListener(new buttonListener());
        JLabel jl = new JLabel("           ");
        buttonPanel.add(confirm);
        buttonPanel.add(jl);
        buttonPanel.add(cancel);
        confirm.setPreferredSize(new Dimension(80, 30));
        cancel.setPreferredSize(new Dimension(80, 30));


        log = new picturePanel(1);
        picturePanel ll = new picturePanel();
        log.setLayout(new GridLayout(5, 1)); //6行1列
        //log.add(FontPanel);
        log.add(accountPanel);
        log.add(passwordPanel);
        log.add(validatePanel);
        log.add(buttonPanel);
        log.add(registerPanel);
        this.setLayout(new GridLayout(2, 1));

        this.add(ll);
        this.add(log);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private String getRandomString(int length) {
        String a = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(a.length());
            m.append(a.charAt(number));
        }
        return m.toString();
    }

    public class picturePanel extends JPanel {
        ImageIcon icon;
        Image img;

        public picturePanel(int i) {

        }

        public picturePanel() {
            icon = new ImageIcon("picture/登录1.jpg");
            img = icon.getImage();
            this.setOpaque(false);
            this.setLayout(new BorderLayout());
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, this);
        }
    }

    private class labelListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            new Register();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

    }

    private class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == confirm) {
//                validateConfirm();
                loginWithoutValidate();
            } else if (e.getSource().equals(cancel)) {
                System.exit(0);
            }
        }

        void validateConfirm() {
            System.out.println(randomText+" "+validateField.getText());
            if (validateField.getText().toLowerCase().equals(randomText.toLowerCase())) {
                loginWithoutValidate();
            } else {
                JOptionPane.showConfirmDialog(null, "验证码输入错误");
            }
        }

        void loginWithoutValidate() {
            accountStr = account.getText();
            passwordStr = new String(psword.getPassword());
            DbConnect dbcon = new DbConnect();
            dbcon.logincheck(accountStr, passwordStr);
        }
    }


    // 绘制验证码
    private class captchaLabel extends JLabel {
        private String text;

        public captchaLabel(String text) {
            this.text = text;
            setPreferredSize(new Dimension(60, 36));// 设置标签的大小
        }

        public void paint(Graphics g) {
            super.paint(g);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            g.drawString(text, 5, 25);
        }
    }

    public static void main(String[] args) {
        new Login();
    }

}

