package userInterface.adminWindows;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import databaseConnection.BorrowRecord;
import databaseConnection.DBHelper;

import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class AdminBorrowInfoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField keywordField;
	private JTable table;
	private DefaultTableModel tableModel;


	/**
	 * Create the frame.
	 */
	public AdminBorrowInfoWindow(DBHelper dbHelper, JFrame returnFrame) {
		setTitle("\u501F\u9605\u4FE1\u606F\u7BA1\u7406");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 42, 670, 299);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u6D41\u6C34\u53F7", "\u4E66\u540D", "ISBN", "\u501F\u9605\u4EBA", "\u501F\u51FA\u65F6\u95F4", "\u5F52\u8FD8\u65F6\u95F4", "\u72B6\u6001"
				}
			){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false;
            }
		};
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		JButton searchButton = new JButton("\u67E5\u627E\u8BB0\u5F55");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleSearch(dbHelper);
			}
		});
		searchButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		searchButton.setBounds(690, 42, 90, 40);
		contentPane.add(searchButton);
		
		keywordField = new JTextField();
		keywordField.setColumns(10);
		keywordField.setBounds(130, 11, 552, 20);
		contentPane.add(keywordField);
		
		JLabel keywordLabel = new JLabel("\u7528\u6237\u540D\u6216\u4E66\u540D\uFF1A");
		keywordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		keywordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		keywordLabel.setBounds(10, 11, 111, 19);
		contentPane.add(keywordLabel);
		
		JButton returnBookButton = new JButton("\u5F52\u8FD8\u56FE\u4E66");
		returnBookButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(AdminBorrowInfoWindow.this, "您还没有选择要归还的图书！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					int id = (int) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(0);
					String status = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(6);
					if (status.equals("已归还")) {
						JOptionPane.showMessageDialog(AdminBorrowInfoWindow.this, "您选择的图书已经被归还！", "消息提示", JOptionPane.ERROR_MESSAGE);
					} else {
						if (dbHelper.returnBook(id)) {
							JOptionPane.showMessageDialog(AdminBorrowInfoWindow.this, "还书成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
							toggleSearch(dbHelper);
						} else {
							JOptionPane.showMessageDialog(AdminBorrowInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		returnBookButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnBookButton.setBounds(690, 177, 90, 40);
		contentPane.add(returnBookButton);
		
		JButton returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminBorrowInfoWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnButton.setBounds(690, 301, 90, 40);
		contentPane.add(returnButton);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{keywordField, searchButton, returnBookButton, returnButton}));
	}

	public void display() {
		setVisible(true);
	}
	
	private void toggleSearch(DBHelper dbHelper) {
		List<BorrowRecord> records = dbHelper.searchBorrowRecord(keywordField.getText());
		// clear existing records
		tableModel.setRowCount(0);
		for (BorrowRecord record : records) {
			String status;
			if (record.hasBeenReturned()) {
				status = "已归还";
			} else {
				status = "未归还";
			}
			Object[] data = new Object[] {
					record.getId(),
					record.getTitle(),
					record.getIsbn(),
					record.getUsername(),
					record.getStartDate(),
					record.getEndDate(),
					status
			};
			tableModel.addRow(data);
		}
	}
}
