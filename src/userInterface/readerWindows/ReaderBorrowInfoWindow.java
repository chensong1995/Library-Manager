package userInterface.readerWindows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import databaseConnection.BorrowRecord;
import databaseConnection.DBHelper;

import javax.swing.JButton;
import java.awt.Font;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderBorrowInfoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;


	public void display() {
		setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public ReaderBorrowInfoWindow(DBHelper dbHelper, String username, JFrame returnFrame) {
		setTitle("\u501F\u9605\u7BA1\u7406\u4E0E\u8FD8\u4E66");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderBorrowInfoWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnButton.setBounds(690, 301, 90, 40);
		contentPane.add(returnButton);
		
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
					"\u6D41\u6C34\u53F7", "\u4E66\u540D", "ISBN", "\u501F\u51FA\u65F6\u95F4", "\u5F52\u8FD8\u65F6\u95F4", "\u72B6\u6001"
				}
			){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
                return false;
            }
		};
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		
		JButton returnBookButton = new JButton("\u5F52\u8FD8\u56FE\u4E66");
		returnBookButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				int rowIndex = table.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(ReaderBorrowInfoWindow.this, "您还没有选择要归还的图书！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					int id = (int) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(0);
					String status = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(5);
					if (status.equals("已归还")) {
						JOptionPane.showMessageDialog(ReaderBorrowInfoWindow.this, "您选择的图书已经被归还！", "消息提示", JOptionPane.ERROR_MESSAGE);
					} else {
						if (dbHelper.returnBook(id)) {
							JOptionPane.showMessageDialog(ReaderBorrowInfoWindow.this, "还书成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
							toggleSearch(dbHelper, username);
						} else {
							JOptionPane.showMessageDialog(ReaderBorrowInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		returnBookButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnBookButton.setBounds(690, 229, 90, 40);
		contentPane.add(returnBookButton);
		
		JLabel lblNewLabel = new JLabel("\u60A8\u7684\u501F\u4E66\u8BB0\u5F55\uFF1A");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 159, 14);
		contentPane.add(lblNewLabel);
		toggleSearch(dbHelper, username);
	}

	private void toggleSearch(DBHelper dbHelper, String username) {
		List<BorrowRecord> records = dbHelper.searchBorrowRecordByUsername(username);
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
					record.getStartDate(),
					record.getEndDate(),
					status
			};
			tableModel.addRow(data);
		}
	}
}
