package userInterface.readerWindows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import databaseConnection.DBHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderPasswordWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField checkNewPasswordField;

	public void display() {
		setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public ReaderPasswordWindow(DBHelper dbHelper, String username, JFrame returnFrame) {
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel oldPasswordLabel = new JLabel("\u65E7\u5BC6\u7801\uFF1A");
		oldPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oldPasswordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		oldPasswordLabel.setBounds(151, 43, 64, 19);
		contentPane.add(oldPasswordLabel);
		
		JLabel newPasswordLabel = new JLabel("\u65B0\u5BC6\u7801\uFF1A");
		newPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		newPasswordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		newPasswordLabel.setBounds(151, 133, 64, 19);
		contentPane.add(newPasswordLabel);
		
		JLabel checkPasswordLabel = new JLabel("\u786E\u8BA4\u65B0\u5BC6\u7801\uFF1A");
		checkPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		checkPasswordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		checkPasswordLabel.setBounds(105, 223, 110, 19);
		contentPane.add(checkPasswordLabel);
		
		oldPasswordField = new JPasswordField();
		oldPasswordField.setBounds(225, 44, 335, 20);
		contentPane.add(oldPasswordField);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(225, 134, 335, 20);
		contentPane.add(newPasswordField);
		
		checkNewPasswordField = new JPasswordField();
		checkNewPasswordField.setBounds(225, 224, 335, 20);
		contentPane.add(checkNewPasswordField);
		
		JButton modifyButton = new JButton("\u4FEE\u6539");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oldPassword = new String(oldPasswordField.getPassword());
				String newPassword = new String(newPasswordField.getPassword());
				String checkNewPassword = new String(checkNewPasswordField.getPassword());
				if (oldPassword.isEmpty()) {
					JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "您还没有输入旧密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (newPassword.isEmpty()) {
					JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "您还没有输入新密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (checkNewPassword.isEmpty()) {
					JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "您还没有输入确认密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (!newPassword.equals(checkNewPassword)) {
					JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "您两次输入的新密码不一致！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (dbHelper.login(username, oldPassword).equals("fail")) {
					JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "您输入的旧密码不正确！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.modifyPassword(username, newPassword)) {
						JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "修改成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						clearFields();
					} else {
						JOptionPane.showMessageDialog(ReaderPasswordWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		modifyButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		modifyButton.setBounds(171, 304, 89, 43);
		contentPane.add(modifyButton);
		
		JButton returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderPasswordWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnButton.setBounds(417, 304, 89, 43);
		contentPane.add(returnButton);
	}
	
	private void clearFields() {
		oldPasswordField.setText("");
		newPasswordField.setText("");
		checkNewPasswordField.setText("");
	}
}
