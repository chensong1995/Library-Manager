package userInterface.adminWindows;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import databaseConnection.DBHelper;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMainWindow extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public AdminMainWindow(String username, DBHelper dbHelper, JFrame loginWindow) {
		setTitle("\u7BA1\u7406\u5458\u9762\u677F");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel welcomeLabel = new JLabel("\u6B22\u8FCE\u60A8\uFF0C" + username + "£¡");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(welcomeLabel);
		welcomeLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 26));
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JButton bookInfoButton = new JButton("\u56FE\u4E66\u4FE1\u606F\u7BA1\u7406");
		bookInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMainWindow.this.setVisible(false);
				AdminBookInfoWindow adminBookInfoWindow = new AdminBookInfoWindow(dbHelper, AdminMainWindow.this);
				adminBookInfoWindow.display();
			}
		});
		bookInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		bookInfoButton.setBounds(300, 30, 200, 40);
		panel_1.add(bookInfoButton);
		
		JButton userInfoButton = new JButton("\u7528\u6237\u4FE1\u606F\u7BA1\u7406");
		userInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMainWindow.this.setVisible(false);
				AdminUserInfoWindow adminUserInfoWindow = new AdminUserInfoWindow(dbHelper, AdminMainWindow.this);
				adminUserInfoWindow.display();
			}
		});
		userInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		userInfoButton.setBounds(300, 110, 200, 40);
		panel_1.add(userInfoButton);
		
		JButton borrowInfoButton = new JButton("\u501F\u9605\u4FE1\u606F\u7BA1\u7406");
		borrowInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMainWindow.this.setVisible(false);
				AdminBorrowInfoWindow adminBorrowInfoWindow = new AdminBorrowInfoWindow(dbHelper, AdminMainWindow.this);
				adminBorrowInfoWindow.display();
			}
		});
		borrowInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		borrowInfoButton.setBounds(300, 190, 200, 40);
		panel_1.add(borrowInfoButton);
		
		JButton logoutButton = new JButton("\u767B\u51FA\u7CFB\u7EDF");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.closeConnection();
				setVisible(false);
				loginWindow.setVisible(true);
			}
		});
		logoutButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		logoutButton.setBounds(300, 270, 200, 40);
		panel_1.add(logoutButton);
	}
	
	public void display() {
		setVisible(true);
	}
}
