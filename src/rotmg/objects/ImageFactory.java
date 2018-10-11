
package rotmg.objects;

import flash.display.BitmapData;
import rotmg.util.AssetLibrary;

public class ImageFactory {

	public ImageFactory() {
		super();
	}

	public BitmapData getImageFromSet(String name, int id) {
		return AssetLibrary.getImageFromSet(name, id);
	}

}