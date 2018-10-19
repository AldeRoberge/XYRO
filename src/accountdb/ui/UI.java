package accountdb.ui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import rotmg.util.AssetLoader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;

public class UI extends JFrame {

	private JPanel contentPane;

	List<ItemCollection> itemCollections = new ArrayList<ItemCollection>();
	private JPanel itemCollectionsPanel;

	public void addItem(int id) {

		boolean exists = false;

		for (ItemCollection i : itemCollections) {
			if (i.id == id) {
				i.incrementAmount();
				exists = true;
				break;
			}
		}

		if (!exists) {
			ItemCollection i = new ItemCollection(id);
			itemCollections.add(i);
			itemCollectionsPanel.add(i.itemPanel);
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		AssetLoader assetLoader = new AssetLoader();
		assetLoader.load();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 782, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setForeground(Color.BLACK);
		splitPane.setDividerLocation(100);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		itemCollectionsPanel = new JPanel();
		itemCollectionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		scrollPane.setViewportView(itemCollectionsPanel);

		addItem(30);
		addItem(304);
		addItem(304);
		addItem(34);
		addItem(304);
		addItem(3234);
		addItem(3234);
		addItem(334);
		addItem(3234);
		addItem(32334);
		addItem(3234);
		addItem(324);
		addItem(323434);
		addItem(3234);
		addItem(334);
		addItem(3234);
		addItem(334);
		addItem(3234);
		addItem(3234);
		addItem(3234);

	}

}
