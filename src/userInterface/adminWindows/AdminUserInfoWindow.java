package userInterface.adminWindows;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import databaseConnection.DBHelper;
import databaseConnection.UserRecord;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Component;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminUserInfoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel isbnLabel;
	private JLabel titleLabel;
	private JTextField usernameField;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton searchButton;
	private JLabel keywordLabel;
	private JTextField keywordField;
	JButton passwordButton;
	private JPasswordField passwordField;
	private JButton returnButton;
	
	public void display() {
		setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public AdminUserInfoWindow(DBHelper dbHelper, JFrame returnFrame) {
		setTitle("\u7528\u6237\u4FE1\u606F\u7BA1\u7406");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel upPanel = new JPanel();
		contentPane.add(upPanel);
		upPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 41, 662, 128);
		upPanel.add(scrollPane);
		
		resultTable = new JTable();
		resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultTable.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int rowIndex = resultTable.getSelectedRow();
				if (rowIndex != -1) {
					usernameField.setText((String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(0));
				}
			}
		});
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u7528\u6237\u540D", "\u6743\u9650", "\u72B6\u6001"
				}) {
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
		                return false;
		            }
				};
		resultTable.setModel(tableModel);
		resultTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		scrollPane.setViewportView(resultTable);
		
		searchButton = new JButton("\u67E5\u627E\u7528\u6237");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleSearch(dbHelper);
			}
		});
		searchButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		searchButton.setBounds(690, 75, 90, 40);
		upPanel.add(searchButton);
		
		keywordLabel = new JLabel("\u7528\u6237\uFF1A");
		keywordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		keywordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		keywordLabel.setBounds(10, 11, 74, 19);
		upPanel.add(keywordLabel);
		
		keywordField = new JTextField();
		keywordField.setBounds(94, 11, 588, 20);
		upPanel.add(keywordField);
		keywordField.setColumns(10);
		
		JPanel downPanel = new JPanel();
		contentPane.add(downPanel);
		downPanel.setLayout(null);
		
		isbnLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		isbnLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		isbnLabel.setBounds(35, 37, 56, 20);
		downPanel.add(isbnLabel);
		
		titleLabel = new JLabel("\u5BC6\u7801\uFF1A");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		titleLabel.setBounds(35, 77, 56, 20);
		downPanel.add(titleLabel);
		
		usernameField = new JTextField();
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				resultTable.clearSelection();
			}
		});
		usernameField.setBounds(110, 37, 129, 20);
		downPanel.add(usernameField);
		usernameField.setColumns(10);
		
		passwordButton = new JButton("\u4FEE\u6539\u5BC6\u7801");
		passwordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.modifyPassword(username, password)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "修改成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		passwordButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		passwordButton.setActionCommand("");
		passwordButton.setBounds(149, 118, 90, 40);
		downPanel.add(passwordButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(110, 79, 129, 20);
		downPanel.add(passwordField);
		
		JButton setAdminButton = new JButton("\u8BBE\u4E3A\u7BA1\u7406\u5458");
		setAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.setAsAdmin(username)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "设置成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		setAdminButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		setAdminButton.setBounds(306, 22, 130, 50);
		downPanel.add(setAdminButton);
		
		JButton setStandardButton = new JButton("\u8BBE\u4E3A\u666E\u901A\u8BFB\u8005");
		setStandardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.setAsStandard(username)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "设置成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		setStandardButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		setStandardButton.setBounds(476, 22, 130, 50);
		downPanel.add(setStandardButton);
		
		JButton setPremiumButton = new JButton("\u8BBE\u4E3A\u9AD8\u7EA7\u8BFB\u8005");
		setPremiumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.setAsPremium(username)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "设置成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		setPremiumButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		setPremiumButton.setBounds(646, 22, 130, 50);
		downPanel.add(setPremiumButton);
		
		JButton banButton = new JButton("\u7981\u7528\u8D26\u53F7");
		banButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.banUser(username)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "禁用成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		banButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		banButton.setBounds(306, 108, 130, 50);
		downPanel.add(banButton);
		
		JButton createUserButton = new JButton("\u521B\u5EFA\u7528\u6237");
		createUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else if (password.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入密码！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.createStandard(username, password)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "创建成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		createUserButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		createUserButton.setActionCommand("");
		createUserButton.setBounds(35, 118, 90, 40);
		downPanel.add(createUserButton);
		
		JButton activeButton = new JButton("\u6FC0\u6D3B\u8D26\u53F7");
		activeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				if (username.isEmpty()) {
					JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "您还没有输入用户名！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					if (dbHelper.activeUser(username)) {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "激活成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						usernameField.setText("");
						passwordField.setText("");
						resultTable.clearSelection();
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminUserInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		activeButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		activeButton.setBounds(476, 108, 130, 50);
		downPanel.add(activeButton);
		
		returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUserInfoWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		returnButton.setBounds(646, 108, 130, 50);
		downPanel.add(returnButton);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{keywordField, searchButton, usernameField, passwordField, createUserButton, passwordButton, setAdminButton, setStandardButton, setPremiumButton, banButton}));
	}

	
	private void toggleSearch(DBHelper dbHelper) {
		List<UserRecord> records = dbHelper.searchUser(keywordField.getText());
		// clear existing records
		tableModel.setRowCount(0);
		for (UserRecord record : records) {
			Object[] data = new Object[] {
					record.getUsername(),
					record.getType(),
					record.getStatus()};
			tableModel.addRow(data);
		}
	}
}
