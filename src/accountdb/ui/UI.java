package accountdb.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import accountdb.AccountDatabaseController;
import accountdb.item.ItemCollection;
import accountdb.item.ItemCollectionComparator;
import alde.commons.util.WrapLayout;
import alde.flash.utils.XML;
import rotmg.appengine.SavedCharacter;
import rotmg.appengine.SavedCharactersList;
import rotmg.objects.ObjectLibrary;
import rotmg.util.AssetLoader;

public class UI extends JFrame {

	private static final boolean DEBUG = true;

	private JPanel contentPane;

	AccountUIManager list = new AccountUIManager();

	ArrayList<ItemCollection> itemCollections = new ArrayList<ItemCollection>();
	private JPanel itemCollectionsPanel;

	public boolean isRunning = false;

	List<CachedAccount> cachedAccounts = new ArrayList<>();
	private JCheckBoxMenuItem chckbxmntmOnlyTradeable;

	public void addItem(Item item) {

		boolean exists = false;

		for (ItemCollection i : itemCollections) {
			if (item.type == i.item.type) {
				i.incrementAmount();
				exists = true;
				break;
			}
		}

		if (!exists) {
			ItemCollection i = new ItemCollection(item);

			if (!i.itemPanel.noIcon()) { // Only add itemPanels with icons

				itemCollections.add(i);
				itemCollectionsPanel.add(i.itemPanel);

				i.itemPanel.registerMouseClickListener(new Runnable() {
					@Override
					public void run() {

						if (!isRunning) {
							isRunning = true;

							List<CachedAccount> accountsContainingItem = new ArrayList<>();

							System.out.println("Item collections : " + itemCollections.size());

							for (ItemCollection i : itemCollections) {
								if (i.isSelected()) {

									for (CachedAccount c : cachedAccounts) {

										System.out.println(c.items.size() + " size");

										if (c.items.contains(i.item.type)) {
											System.out.println("Found accoutn with item");

											if (!accountsContainingItem.contains(c)) {
												accountsContainingItem.add(c);
											}

										}
									}

								}
							}

							list.setTableData(accountsContainingItem);

							isRunning = false;
						} else {
							System.out.println("Is already running...");
						}

					}
				});
			}
		}

		revalidate();
		repaint();

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		AssetLoader assetLoader = new AssetLoader();
		assetLoader.load();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
					frame.parseAccounts();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setTitle("💩 Aldump");
		setIconImage(ObjectLibrary.getTextureFromType(0x1805).image);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 472);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem menuItem = new JMenuItem(new AbstractAction("Sort") {
			@Override
			public void actionPerformed(ActionEvent e) {
				sort();
			}

		});
		mnEdit.add(menuItem);

		chckbxmntmOnlyTradeable = new JCheckBoxMenuItem(new AbstractAction("Only tradeable") {
			@Override
			public void actionPerformed(ActionEvent e) {
				sort();
			}

		});

		mnEdit.add(chckbxmntmOnlyTradeable);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		splitPane.setForeground(Color.BLACK);
		splitPane.setDividerLocation(200);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); //Scroll fas
		splitPane.setLeftComponent(scrollPane);
		itemCollectionsPanel = new JPanel();
		itemCollectionsPanel.setBackground(new Color(13, 13, 13));
		itemCollectionsPanel.setLayout(new WrapLayout());
		scrollPane.setViewportView(itemCollectionsPanel);

		JPanel panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		splitPane.setRightComponent(list);

	}

	private void sort() {

		System.out.println("Action performed");

		itemCollectionsPanel.removeAll();

		Collections.sort(itemCollections, new ItemCollectionComparator());

		for (ItemCollection i : itemCollections) {

			if (filter(i)) {
				itemCollectionsPanel.add(i.itemPanel);
			}

		}

		itemCollectionsPanel.revalidate();
		itemCollectionsPanel.repaint();
	}

	/**
	 * True if item should be displayed
	 */
	private boolean filter(ItemCollection i) {

		if (chckbxmntmOnlyTradeable.isSelected()) {
			return !i.item.isSoulbound;
		}

		return true;
	}

	public void parseAccounts() {

		for (CachedAccount c : AccountDatabaseController.getCachedAccounts()) {

			if (c.charList.contains("<Account>")) {

				cachedAccounts.add(c);

				try {

					SavedCharactersList s = new SavedCharactersList(new XML(c.charList));

					System.out.println(c.charList);

					for (SavedCharacter savedChar : s.savedChars) {
						for (int i : savedChar.getInventory()) {

							Item item = new Item(i);

							addItem(item);
							c.addItem(i);
							cachedAccounts.add(c);
						}
					}

					System.out.println("One complete...");

				} catch (Exception e) {
					e.printStackTrace();

					/*if (DEBUG) {
					
						break; // TODO remove this for debugging
					
					}*/

				}
			} else {
				System.out.println("Invalid charlist ; " + c.email);
			}

		}

		System.out.println("Ended.");

	}

}
