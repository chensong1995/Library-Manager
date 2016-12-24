package userInterface.readerWindows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import databaseConnection.BookRecord;
import databaseConnection.DBHelper;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class ReaderBookInfoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField keywordField;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton borrowButton;
	private JButton returnButton;


	public void display() {
		setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public ReaderBookInfoWindow(DBHelper dbHelper, String username, int limit, JFrame returnFrame) {
		setTitle("\u56FE\u4E66\u67E5\u8BE2\u4E0E\u501F\u9605");
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
		scrollPane.setBounds(20, 41, 662, 319);
		contentPane.add(scrollPane);
		
		resultTable = new JTable();
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u5E8F\u53F7", "ISBN", "\u4E66\u540D", "\u7C7B\u522B", "\u4EF7\u683C", "\u51FA\u7248\u793E", "\u4F5C\u8005", "\u51FA\u7248\u65E5\u671F", "\u72B6\u6001"
				}) {
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
		                return false;
		            }
				};
		resultTable.setModel(tableModel);
		resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		resultTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		resultTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		resultTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		resultTable.getColumnModel().getColumn(8).setPreferredWidth(50);
		scrollPane.setViewportView(resultTable);
		
		JButton searchButton = new JButton("\u641C\u7D22");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleSearch(dbHelper);
			}
		});
		searchButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		searchButton.setBounds(692, 41, 90, 40);
		contentPane.add(searchButton);
		
		JLabel keywordLabel = new JLabel("\u5173\u952E\u8BCD\uFF1A");
		keywordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		keywordLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		keywordLabel.setBounds(10, 11, 74, 19);
		contentPane.add(keywordLabel);
		
		keywordField = new JTextField();
		keywordField.setColumns(10);
		keywordField.setBounds(94, 11, 588, 20);
		contentPane.add(keywordField);
		
		borrowButton = new JButton("\u501F\u9605");
		borrowButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				int rowIndex = resultTable.getSelectedRow();
				if (rowIndex == -1) {
					JOptionPane.showMessageDialog(ReaderBookInfoWindow.this, "您还没有选择要借阅的图书！", "消息提示", JOptionPane.ERROR_MESSAGE);
				} else {
					int bid = (int) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(0);
					String status = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(8);
					if (!status.equals("可以借用")) {
						JOptionPane.showMessageDialog(ReaderBookInfoWindow.this, "您选择的图书当前不可借用！", "消息提示", JOptionPane.ERROR_MESSAGE);
					} else if (dbHelper.getAlreadyBorrowedCount(username) >= limit) {
						JOptionPane.showMessageDialog(ReaderBookInfoWindow.this, "您最多借阅" + limit + "本书，先还一些再借吧！", "消息提示", JOptionPane.ERROR_MESSAGE);
					} else {
						if (dbHelper.borrowBook(username, bid)) {
							JOptionPane.showMessageDialog(ReaderBookInfoWindow.this, "借书成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
							toggleSearch(dbHelper);
						} else {
							JOptionPane.showMessageDialog(ReaderBookInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		borrowButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		borrowButton.setBounds(692, 182, 90, 40);
		contentPane.add(borrowButton);
		
		returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReaderBookInfoWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnButton.setBounds(692, 320, 90, 40);
		contentPane.add(returnButton);
	}

	private void toggleSearch(DBHelper dbHelper) {
		List<BookRecord> records = dbHelper.searchBook(keywordField.getText());
		// clear existing records
		tableModel.setRowCount(0);
		for (BookRecord record : records) {
			String status;
			if (dbHelper.isBookBorrowed(record.getId())) {
				status = "已借出";
			} else {
				status = "可以借用";
			}
			Object[] data = new Object[] {
					record.getId(),
					record.getIsbn(),
					record.getTitle(),
					record.getType(),
					record.getPrice()/100 + "." + record.getPrice()%100,
					record.getPress(),
					record.getAuthor(),
					record.getDate(),
					status};
			tableModel.addRow(data);
		}
	}
}
