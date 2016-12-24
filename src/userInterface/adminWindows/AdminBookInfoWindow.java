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

import databaseConnection.BookRecord;
import databaseConnection.DBHelper;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Component;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class AdminBookInfoWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel isbnLabel;
	private JLabel titleLabel;
	private JLabel typeLabel;
	private JLabel priceLabel;
	private JLabel pressLabel;
	private JLabel authorLabel;
	private JLabel dateLabel;
	private JTextField isbnField;
	private JTextField titleField;
	private JTextField typeField;
	private JTextField priceField;
	private JTextField pressField;
	private JTextField authorField;
	private JTextField dateField;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton searchButton;
	private JLabel keywordLabel;
	private JTextField keywordField;
	JButton addButton;
	JButton modifyButton;
	JButton returnButton;
	
	public void display() {
		setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public AdminBookInfoWindow(DBHelper dbHelper, JFrame returnFrame) {
		setTitle("\u56FE\u4E66\u4FE1\u606F\u7BA1\u7406");
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
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int rowIndex = resultTable.getSelectedRow();
				if (rowIndex != -1) {
					fillBookRecord(rowIndex);
					modifyButton.setEnabled(true);
				}
			}
			@SuppressWarnings("unchecked")
			private void fillBookRecord(int rowIndex) {
				String isbn = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(1);
				String title = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(2);
				String type = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(3);
				String price = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(4);
				String press = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(5);
				String author = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(6);
				String date = (String) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(7);
				isbnField.setText(isbn);
				titleField.setText(title);
				typeField.setText(type);
				priceField.setText(price);
				pressField.setText(press);
				authorField.setText(author);
				dateField.setText(date);
			}
		});
		tableModel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"\u5E8F\u53F7", "ISBN", "\u4E66\u540D", "\u7C7B\u522B", "\u4EF7\u683C", "\u51FA\u7248\u793E", "\u4F5C\u8005", "\u51FA\u7248\u65E5\u671F"
				}) {
					private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int row, int column) {
		                return false;
		            }
				};
		resultTable.setModel(tableModel);
		resultTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		resultTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		resultTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		resultTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		scrollPane.setViewportView(resultTable);
		
		searchButton = new JButton("\u641C\u7D22");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				toggleSearch(dbHelper);
			}
		});
		searchButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		searchButton.setBounds(690, 75, 90, 40);
		upPanel.add(searchButton);
		
		keywordLabel = new JLabel("\u5173\u952E\u8BCD\uFF1A");
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
		
		isbnLabel = new JLabel("ISBN\uFF1A");
		isbnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		isbnLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		isbnLabel.setBounds(35, 10, 56, 20);
		downPanel.add(isbnLabel);
		
		titleLabel = new JLabel("\u4E66\u540D\uFF1A");
		titleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		titleLabel.setBounds(35, 50, 56, 20);
		downPanel.add(titleLabel);
		
		typeLabel = new JLabel("\u7C7B\u522B\uFF1A");
		typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		typeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		typeLabel.setBounds(35, 90, 56, 20);
		downPanel.add(typeLabel);
		
		priceLabel = new JLabel("\u4EF7\u683C\uFF1A");
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		priceLabel.setBounds(35, 130, 56, 20);
		downPanel.add(priceLabel);
		
		pressLabel = new JLabel("\u51FA\u7248\u793E\uFF1A");
		pressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pressLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		pressLabel.setBounds(333, 10, 56, 20);
		downPanel.add(pressLabel);
		
		authorLabel = new JLabel("\u4F5C\u8005\uFF1A");
		authorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		authorLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		authorLabel.setBounds(333, 50, 56, 20);
		downPanel.add(authorLabel);
		
		dateLabel = new JLabel("\u51FA\u7248\u65E5\u671F\uFF1A");
		dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dateLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		dateLabel.setBounds(319, 90, 70, 20);
		downPanel.add(dateLabel);
		
		isbnField = new JTextField();
		isbnField.setBounds(110, 10, 129, 20);
		downPanel.add(isbnField);
		isbnField.setColumns(10);
		
		titleField = new JTextField();
		titleField.setColumns(10);
		titleField.setBounds(110, 50, 129, 20);
		downPanel.add(titleField);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(110, 90, 129, 20);
		downPanel.add(typeField);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(110, 130, 129, 20);
		downPanel.add(priceField);
		
		pressField = new JTextField();
		pressField.setColumns(10);
		pressField.setBounds(408, 10, 129, 20);
		downPanel.add(pressField);
		
		authorField = new JTextField();
		authorField.setColumns(10);
		authorField.setBounds(408, 50, 129, 20);
		downPanel.add(authorField);
		
		dateField = new JTextField();
		dateField.setColumns(10);
		dateField.setBounds(408, 90, 129, 20);
		downPanel.add(dateField);
		
		addButton = new JButton("\u6DFB\u52A0\u56FE\u4E66");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookRecord record = parseBookInfo();
				if (record != null) { // legal input
					if (dbHelper.addBook(record)) {
						JOptionPane.showMessageDialog(AdminBookInfoWindow.this, "添加成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						clearBookInfoFields();
					} else {
						JOptionPane.showMessageDialog(AdminBookInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		addButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		addButton.setActionCommand("");
		addButton.setBounds(628, 10, 90, 40);
		downPanel.add(addButton);
		
		modifyButton = new JButton("\u4FEE\u6539\u4FE1\u606F");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookRecord record = parseBookInfo();
				int rowIndex = resultTable.getSelectedRow();
				if (record != null && rowIndex != -1) { // legal input
					@SuppressWarnings("unchecked")
					int id = (int) ((Vector<Object>)tableModel.getDataVector().elementAt(rowIndex)).elementAt(0);
					record.setId(id);
					if (dbHelper.modifyBook(record)) {
						JOptionPane.showMessageDialog(AdminBookInfoWindow.this, "修改成功！", "消息提示", JOptionPane.INFORMATION_MESSAGE);
						clearBookInfoFields();
						modifyButton.setEnabled(false);
						toggleSearch(dbHelper);
					} else {
						JOptionPane.showMessageDialog(AdminBookInfoWindow.this, "系统拒绝了您的请求！", "消息提示", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		modifyButton.setEnabled(false);
		modifyButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		modifyButton.setBounds(628, 70, 90, 40);
		downPanel.add(modifyButton);
		
		returnButton = new JButton("\u8FD4\u56DE");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminBookInfoWindow.this.setVisible(false);
				returnFrame.setVisible(true);
			}
			
		});
		returnButton.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		returnButton.setBounds(628, 130, 90, 40);
		downPanel.add(returnButton);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{keywordField, searchButton, isbnField, titleField, typeField, priceField, pressField, authorField, dateField, addButton, modifyButton, returnButton}));
	}

	private BookRecord parseBookInfo() {
		int price = 0;
		if (isbnField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入ISBN！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		if (titleField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入书名！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		if (typeField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入类别！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		if (priceField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入价格！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} else {
			try {
				BigDecimal bg = new BigDecimal(priceField.getText());
				bg = bg.multiply(new BigDecimal(100)); // convert to FEN
				price = bg.intValue();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "您输入的价格不合法！", "消息提示", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if (pressField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入出版社！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		if (authorField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入作者！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		
		if (dateField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "您还没有输入日期！", "消息提示", JOptionPane.ERROR_MESSAGE);
			return null;
		} 
		return new BookRecord(isbnField.getText(), titleField.getText(), typeField.getText(), price, pressField.getText(), authorField.getText(), dateField.getText());
	}

	private void clearBookInfoFields() {
		isbnField.setText("");
		titleField.setText("");
		typeField.setText("");
		priceField.setText("");
		pressField.setText("");
		authorField.setText("");
		dateField.setText("");
	}
	
	private void toggleSearch(DBHelper dbHelper) {
		List<BookRecord> records = dbHelper.searchBook(keywordField.getText());
		// clear existing records
		tableModel.setRowCount(0);
		for (BookRecord record : records) {
			Object[] data = new Object[] {
					record.getId(),
					record.getIsbn(),
					record.getTitle(),
					record.getType(),
					record.getPrice()/100 + "." + record.getPrice()%100,
					record.getPress(),
					record.getAuthor(),
					record.getDate()};
			tableModel.addRow(data);
		}
	}
}
