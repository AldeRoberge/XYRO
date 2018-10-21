package accountdb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
Original credits :

@author Andrew Thompson
@version 2011-06-08
@see http://codereview.stackexchange.com/q/4446/7784
*/
public class AccountUIManager extends JPanel implements ActionListener {

	static Logger log = LoggerFactory.getLogger(AccountUIManager.class);

	public ArrayList<CachedAccount> selectedAccounts = new ArrayList<>();

	/** Directory listing */
	private JTable table;

	/** Table model for CachedAccount array. */
	private AccountTableModel tableModel;
	private ListSelectionListener listSelectionListener;

	/** CachedAccount labels. */

	private JLabel nameLabel;
	private JLabel pathLabel;
	private JLabel dateLabel;
	private JLabel sizeLabel;

	/** CachedAccount details. */
	private JLabel fileName;
	private JTextField path;
	private JLabel date;
	private JLabel size;

	/** Popup menu */

	private JPopupMenu popupMenu = new JPopupMenu();

	private boolean cellSizesSet;

	//private ObjectSerializer<CachedAccount> currentPath = new ObjectSerializer<CachedAccount>(LibraryManager.getFileFile());

	public AccountUIManager() {

		setLayout(new BorderLayout(3, 3));
		//setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel detailView = new JPanel(new BorderLayout(3, 3));

		table = new JTable();
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setShowVerticalLines(false);
		table.setSelectionBackground(new Color(136, 23, 152));
		table.setSelectionForeground(Color.WHITE);

		table.setComponentPopupMenu(popupMenu);

		tableModel = new AccountTableModel();
		table.setModel(tableModel);

		//set font bold for column 1 (see CellRenderer at the bottom of this class)
		table.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());

		listSelectionListener = e -> {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();

			if (!lsm.isSelectionEmpty()) {
				// Find out which indexes are selected.
				int minIndex = lsm.getMinSelectionIndex();
				int maxIndex = lsm.getMaxSelectionIndex();

				selectedAccounts.clear();

				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {

						//Fixes row being incorrect after sortings
						int row = table.convertRowIndexToModel(i);

						selectedAccounts.add(tableModel.getObject(row));
					}
				}

			}

		};
		table.getSelectionModel().addListSelectionListener(listSelectionListener);

		JScrollPane tableScroll = new JScrollPane(table);
		Dimension d = tableScroll.getPreferredSize();
		tableScroll.setPreferredSize(new Dimension((int) d.getWidth(), (int) d.getHeight() / 2));
		detailView.add(tableScroll, BorderLayout.CENTER);

		// details 
		JPanel fileMainDetails = new JPanel(new BorderLayout(4, 2));
		fileMainDetails.setBorder(new EmptyBorder(0, 6, 0, 6));

		JPanel fileDetailsLabels = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsLabels, BorderLayout.WEST);

		JPanel fileDetailsValues = new JPanel(new GridLayout(0, 1, 2, 2));
		fileMainDetails.add(fileDetailsValues, BorderLayout.CENTER);

		nameLabel = new JLabel("File", JLabel.TRAILING);
		fileDetailsLabels.add(nameLabel);
		fileName = new JLabel();
		fileDetailsValues.add(fileName);

		pathLabel = new JLabel("Path", JLabel.TRAILING);
		fileDetailsLabels.add(pathLabel);
		path = new JTextField(5);
		path.setBorder(null); //Removes the border
		path.setEditable(false);
		fileDetailsValues.add(path);

		sizeLabel = new JLabel("Null Size", JLabel.TRAILING);
		fileDetailsLabels.add(sizeLabel);
		size = new JLabel();
		fileDetailsValues.add(size);

		dateLabel = new JLabel("Last Modified", JLabel.TRAILING);
		fileDetailsLabels.add(dateLabel);
		date = new JLabel();
		fileDetailsValues.add(date);

		JPanel fileView = new JPanel(new BorderLayout(3, 3));
		fileView.add(fileMainDetails, BorderLayout.CENTER);

		detailView.add(fileView, BorderLayout.SOUTH);

		add(detailView, BorderLayout.CENTER);

		updateEnabledFields(0);

	}

	/** Update the table on the EDT */
	void setTableData(List<CachedAccount> newFiles) {

		final List<CachedAccount> currentFiles = tableModel.getAccounts();

		for (Iterator<CachedAccount> iterator = newFiles.iterator(); iterator.hasNext();) {

			CachedAccount file = iterator.next();

			if (currentFiles.contains(file)) {
				iterator.remove();
			}

		}

		//Update table

		if (tableModel == null) {
			tableModel = new AccountTableModel();
			table.setModel(tableModel);
		}
		table.getSelectionModel().removeListSelectionListener(listSelectionListener);
		tableModel.setFiles(newFiles);
		table.getSelectionModel().addListSelectionListener(listSelectionListener);
		if (!cellSizesSet) {

			table.setRowHeight(40);
			//setColumnWidth(0, -1);
			cellSizesSet = true;

		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {

	}

	/*private void setColumnWidth(int column, int width) {
		TableColumn tableColumn = table.getColumnModel().getColumn(column);
		if (width < 0) {
			// use the preferred width of the header..
			JLabel label = new JLabel((String) tableColumn.getHeaderValue());
			Dimension preferred = label.getPreferredSize();
			// altered 10->14 as per camickr comment.
			width = (int) preferred.getWidth() + 14;
		}
		tableColumn.setPreferredWidth(width);
		tableColumn.setMaxWidth(width);
		tableColumn.setMinWidth(width);
	}*/

	private void setDetails(ArrayList<CachedAccount> accounts) {

		fileName.setIcon(null);
		fileName.setText("");
		path.setText("");
		date.setText("");
		size.setText("");

		if (accounts == null) { //used to reset

			updateEnabledFields(0);
			selectedAccounts.clear();

		} else {

			updateEnabledFields(accounts.size());

			selectedAccounts = accounts;

		}

		repaint();

	}

	private void updateEnabledFields(int size2) {

		Color enabled = Color.BLACK;
		Color disabled = Color.GRAY;

		if (size2 == 1) {
			nameLabel.setForeground(enabled);
			pathLabel.setForeground(enabled);
			dateLabel.setForeground(enabled);
			sizeLabel.setForeground(enabled);
		} else if (size2 > 1) {
			nameLabel.setForeground(disabled);
			pathLabel.setForeground(disabled);
			dateLabel.setForeground(disabled);
			sizeLabel.setForeground(enabled);
		} else if (size2 == 0) {
			nameLabel.setForeground(disabled);
			pathLabel.setForeground(disabled);
			dateLabel.setForeground(disabled);
			sizeLabel.setForeground(disabled);
		}

	}

}

class AccountTableModel extends AbstractTableModel {

	static Logger log = LoggerFactory.getLogger(AccountTableModel.class);

	private List<CachedAccount> accounts;
	private String[] columns = { "Email", "Password" };

	AccountTableModel() {
		accounts = new ArrayList<>();
	}

	public Object getValueAt(int row, int column) {
		CachedAccount file = accounts.get(row);
		switch (column) {
		case 0:
			return file.email;
		case 1:
			return file.password;
		default:
			System.err.println("Logic Error");
		}
		return "";
	}

	public int getColumnCount() {
		return columns.length;
	}

	public Class<?> getColumnClass(int column) {
		/*switch (column) {
		case 0:
			return ImageIcon.class;
		case 3:
			return Long.class;
		case 4:
			return Date.class;
		}*/
		return String.class;
	}

	public String getColumnName(int column) {
		return columns[column];
	}

	public int getRowCount() {
		return accounts.size();
	}

	public CachedAccount getObject(int row) {
		return accounts.get(row);
	}

	public void setFiles(List<CachedAccount> accounts) {
		this.accounts = accounts;
		fireTableDataChanged();
	}

	public List<CachedAccount> getAccounts() {
		return accounts;
	}

	public void addAccount(CachedAccount account) {
		accounts.add(account);
		fireTableDataChanged();
	}

}

class CellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// if (value>17 value<26) {
		this.setValue(table.getValueAt(row, column));
		this.setFont(this.getFont().deriveFont(Font.PLAIN));
		//}
		return this;
	}
}