package userInterface.readerWindows;

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

public class ReaderMainWindow extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public ReaderMainWindow(String username, int limit, DBHelper dbHelper, JFrame loginWindow) {
		setTitle("\u8BFB\u8005\u9762\u677F");
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
		
		JButton bookInfoButton = new JButton("\u56FE\u4E66\u67E5\u8BE2\u4E0E\u501F\u9605");
		bookInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderMainWindow.this.setVisible(false);
				ReaderBookInfoWindow readerBookInfoWindow = new ReaderBookInfoWindow(dbHelper, username, limit, ReaderMainWindow.this);
				readerBookInfoWindow.display();
			}
		});
		bookInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		bookInfoButton.setBounds(300, 30, 200, 40);
		panel_1.add(bookInfoButton);
		
		JButton borrowInfoButton = new JButton("\u501F\u9605\u7BA1\u7406\u4E0E\u8FD8\u4E66");
		borrowInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderMainWindow.this.setVisible(false);
				ReaderBorrowInfoWindow readerBorrowInfoWindow = new ReaderBorrowInfoWindow(dbHelper, username, ReaderMainWindow.this);
				readerBorrowInfoWindow.display();
			}
		});
		borrowInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		borrowInfoButton.setBounds(300, 110, 200, 40);
		panel_1.add(borrowInfoButton);
		
		JButton userInfoButton = new JButton("\u4FEE\u6539\u5BC6\u7801");
		userInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderMainWindow.this.setVisible(false);
				ReaderPasswordWindow readerPasswordWindow = new ReaderPasswordWindow(dbHelper, username, ReaderMainWindow.this);
				readerPasswordWindow.display();
			}
		});
		userInfoButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
		userInfoButton.setBounds(300, 190, 200, 40);
		panel_1.add(userInfoButton);
		
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
