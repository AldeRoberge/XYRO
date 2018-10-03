package rotmg.objects;

import alde.flash.utils.Vector;
import alde.flash.utils.XML;
import flash.display.BitmapData;
import rotmg.util.BitmapUtil;

public class Wall extends GameObject {

	private static final Vector<Double> UVT = new Vector<Double>(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0,
			0.0);

	private static final Vector<Double> sqX = new Vector<Double>(0.0, 1.0, 0.0, -1.0);

	private static final Vector<Double> sqY = new Vector<Double>(-1.0, 0.0, 1.0, 0.0);

	private BitmapData topTexture = null;

	public Wall(XML param1) {
		super(param1);
		hasShadow = false;
		TextureData loc2 = ObjectLibrary.typeToTopTextureData.get(objectType);
		this.topTexture = loc2.getTexture(0);
	}

	public void setObjectId(int param1) {
		super.setObjectId(param1);
		TextureData loc2 = ObjectLibrary.typeToTopTextureData.get(objectType);
		this.topTexture = loc2.getTexture(param1);
	}

	public int getColor() {
		return BitmapUtil.mostCommonColor(this.topTexture);
	}

}
