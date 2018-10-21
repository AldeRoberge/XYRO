package accountdb.item;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import accountdb.ui.Item;
import flash.display.BitmapData;
import rotmg.objects.ObjectLibrary;

public class ItemCollection {

	public Item item;
	public ItemCollectionPanel itemPanel;

	int amount = 0;

	/**
	 * Creates an ItemCollection for the specified itemId.
	 * Sets amount to 1
	 */
	public ItemCollection(Item item) {

		this.item = item;
		this.itemPanel = new ItemCollectionPanel();

		if (!(item.image == null)) {
			this.itemPanel.setIcon(new ImageIcon(item.image));
		}

		setAmount(1);

	}

	public boolean isSelected() {
		return itemPanel.isSelected;
	}

	public void incrementAmount() {
		setAmount(amount + 1);
	}

	private void setAmount(int amount) {

		this.amount = amount;

		itemPanel.setQuantity("" + amount);

		itemPanel.revalidate();
		itemPanel.repaint();
	}
}
