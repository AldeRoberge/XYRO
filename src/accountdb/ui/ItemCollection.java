package accountdb.ui;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import flash.display.BitmapData;
import rotmg.objects.ObjectLibrary;

public class ItemCollection {

	int id; // Item ID
	int amount; // Amount of Items

	ItemCollectionPanel itemPanel;

	/**
	 * Creates an ItemCollection for the specified itemId.
	 * Sets amount to 1
	 */
	public ItemCollection(int id) {

		this.id = id;
		this.itemPanel = new ItemCollectionPanel();

		setTexture();

		setAmount(1);

	}

	private void setTexture() {

		BitmapData texture = ObjectLibrary.getTextureFromType(id);

		if (texture != null) {
			Image imgSmall = texture.image.getScaledInstance(50, 50, Image.SCALE_FAST);
			itemPanel.setIcon(new ImageIcon(imgSmall));
		}
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
