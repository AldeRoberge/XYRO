package accountdb.ui;

import java.awt.Image;

import flash.display.BitmapData;
import rotmg.objects.ObjectLibrary;

public class Item {

	public int type;
	public Image image;
	public String id;
	public boolean isSoulbound;

	public Item(int type) {
		this.type = type;
		
		BitmapData texture = ObjectLibrary.getTextureFromType(type);
		Image imgSmall = null;
		if (texture != null) {
			imgSmall = texture.image.getScaledInstance(25, 25, Image.SCALE_FAST);
		}
		
		this.image = imgSmall;
		this.id = ObjectLibrary.getIdFromType(type);
		this.isSoulbound = ObjectLibrary.isSoulbound(type);
	}

}
