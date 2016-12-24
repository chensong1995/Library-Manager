package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import databaseConnection.DBHelper;
import userInterface.adminWindows.AdminMainWindow;
import userInterface.readerWindows.ReaderMainWindow;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.SwingConstants;

public class Login {

	private final int standardLimit = 1;
	private final int premiumLimit = 3;
	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField usernameField;
	DBHelper dbHelper;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("\u767B\u5F55");
		frame.setBounds(100, 100, 500, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel mainLabel = new JLabel("\u6B22\u8FCE\u4F7F\u7528\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		mainLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 22));
		mainLabel.setBounds(124, 11, 227, 49);
		frame.getContentPane().add(mainLabel);
		
		JLabel loginLabel = new JLabel("\u8BF7\u767B\u5F55");
		loginLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
		loginLabel.setBounds(206, 56, 63, 24);
		frame.getContentPane().add(loginLabel);
		
		JLabel usernameLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		usernameLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		usernameLabel.setBounds(95, 98, 63, 19);
		frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("\u5BC6\u7801\uFF1A");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		passwordLabel.setBounds(95, 135, 63, 19);
		frame.getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(181, 136, 115, 20);
		frame.getContentPane().add(passwordField);
		
		usernameField = new JTextField();
		usernameField.setBounds(181, 99, 115, 20);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("\u767B\u5F55");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (usernameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "请输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (passwordField.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "请输入密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					dbHelper = new DBHelper();
					if (!dbHelper.isConnectionEstablished()) {
						JOptionPane.showMessageDialog(frame, "连接数据库失败！", "消息提示", JOptionPane.ERROR_MESSAGE);
					} else {
						String status = dbHelper.login(usernameField.getText(), new String(passwordField.getPassword()));
						if (status.equals("admin")) {
							frame.setVisible(false);
							AdminMainWindow adminMainWindow = new AdminMainWindow(usernameField.getText(), dbHelper, frame);
							adminMainWindow.display();
						} else if (status.equals("premium")) {
							frame.setVisible(false);
							ReaderMainWindow readerMainWindow = new ReaderMainWindow(usernameField.getText(), premiumLimit, dbHelper, frame);
							readerMainWindow.display();
						} else if (status.equals("standard")) {
							frame.setVisible(false);
							ReaderMainWindow readerMainWindow = new ReaderMainWindow(usernameField.getText(), standardLimit, dbHelper, frame);
							readerMainWindow.display();
						} else {
							JOptionPane.showMessageDialog(frame, "身份验证失败！", "消息提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		loginButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		loginButton.setBounds(341, 109, 70, 41);
		frame.getContentPane().add(loginButton);
		
		JLabel copyrightLabel = new JLabel("\u7248\u672C\u53F7\uFF1A1.0                                \u8054\u7CFB\u4F5C\u8005\uFF1A386709725@qq.com");
		copyrightLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		copyrightLabel.setBounds(71, 197, 372, 24);
		frame.getContentPane().add(copyrightLabel);
		frame.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{usernameField, passwordField, loginButton}));
	}
}
