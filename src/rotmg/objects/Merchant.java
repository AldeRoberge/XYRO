package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.Vector;
import alde.flash.utils.XML;
import flash.display.BitmapData;
import flash.geom.ColorTransform;
import flash.geom.Matrix;
import rotmg.AGameSprite;
import rotmg.map.Map;
import rotmg.model.AddSpeechBalloonVO;
import rotmg.util.IntPoint;

/**
 * Almost a 100% match, except that it's abstract because it doesnt have the getPanel() method
 */
public class Merchant extends SellableObject implements IInteractiveObject {

	private static final int NONE_MESSAGE = 0;

	private static final int NEW_MESSAGE = 1;

	private static final int MINS_LEFT_MESSAGE = 2;

	private static final int ITEMS_LEFT_MESSAGE = 3;

	private static final int DISCOUNT_MESSAGE = 4;

	private static final double T = 1;

	private static Matrix DOSE_MATRIX;

	static {
		Matrix loc1 = new Matrix();
		loc1.translate(10, 5);
		DOSE_MATRIX = loc1;
	}

	public int merchandiseType = -1;

	public int count = -1;

	public int minsLeft = -1;

	public int discount = 0;

	public BitmapData merchandiseTexture = null;

	public int untilNextMessage = 0;

	public double alpha = 1.0;

	private boolean firstUpdate = true;

	private int messageIndex = 0;

	private ColorTransform ct;

	public Merchant(XML param1) {
		super(param1);
		this.ct = new ColorTransform(1, 1, 1, 1);
		isInteractive = true;
	}

	public void setPrice(int param1) {
		super.setPrice(param1);
		this.untilNextMessage = 0;
	}

	public void setRankReq(int param1) {
		super.setRankReq(param1);
		this.untilNextMessage = 0;
	}

	public boolean addTo(Map param1, double param2, double param3) {
		if (!super.addTo(param1, param2, param3)) {
			return false;
		}
		param1.merchLookup.put(new IntPoint((int) x, (int) y), this);
		return true;
	}

	public void removeFromMap() {
		IntPoint loc1 = new IntPoint((int) x, (int) y);
		if (map.merchLookup.get(loc1) == this) {
			map.merchLookup.put(loc1, null);
		}
		super.removeFromMap();
	}

	public AddSpeechBalloonVO getSpeechBalloon(int param1) {

		return new AddSpeechBalloonVO(this, "", "", false, false, 0, 1, 0, 1, 0, 6, true, false);
	}

	public boolean update(int time, int dt) {

		super.update(time, dt);

		this.untilNextMessage = this.untilNextMessage - dt;
		if (this.untilNextMessage > 0) {
			return true;
		}
		this.untilNextMessage = 5000;
		Vector<Integer> loc3 = new Vector<Integer>();
		if (this.minsLeft == 2147483647) {
			loc3.add(NEW_MESSAGE);
		} else if (this.minsLeft >= 0 && this.minsLeft <= 5) {
			loc3.add(MINS_LEFT_MESSAGE);
		}
		if (this.count >= 1 && this.count <= 2) {
			loc3.add(ITEMS_LEFT_MESSAGE);
		}
		if (this.discount > 0) {
			loc3.add(DISCOUNT_MESSAGE);
		}
		if (loc3.length == 0) {
			return true;
		}
		this.messageIndex = ++this.messageIndex % loc3.length;
		int loc4 = loc3.get(this.messageIndex);

		//this.getSpeechBalloon(loc4)

		return true;
	}

	public String soldObjectName() {
		return ObjectLibrary.typeToDisplayId.get(this.merchandiseType);
	}

	public String soldObjectInternalName() {
		XML loc1 = ObjectLibrary.xmlLibrary.get(this.merchandiseType);
		return String.valueOf(loc1.getIntAttribute("id"));
	}

	public int getSellableType() {
		return this.merchandiseType;
	}

	public BitmapData getIcon() {
		/**BaseSimpleText loc3 = null;
		 BaseSimpleText loc4 = null;
		 BitmapData loc1 = ObjectLibrary.getRedrawnTextureFromType(this.merchandiseType, 80, true);
		 XML loc2 = ObjectLibrary.xmlLibrary.get(this.merchandiseType);
		 if (loc2.hasOwnProperty("Doses")) {
		 loc1 = loc1.clone();
		 loc3 = new BaseSimpleText(12, 16777215, false, 0, 0);
		 loc3.text = String(loc2.Doses);
		 loc3.updateMetrics();
		 loc1.draw(loc3, DOSE_MATRIX);
		 }
		 if (loc2.hasOwnProperty("Quantity")) {
		 loc1 = loc1.clone();
		 loc4 = new BaseSimpleText(12, 16777215, false, 0, 0);
		 loc4.text = String(loc2.Quantity);
		 loc4.updateMetrics();
		 loc1.draw(loc4, DOSE_MATRIX);
		 }
		 return loc1;*/
		return null;
	}

	public int getTex1Id(int param1) {
		XML loc2 = ObjectLibrary.xmlLibrary.get(this.merchandiseType);
		if (loc2 == null) {
			return param1;
		}
		if (loc2.getValue("Activate").equals("Dye") && loc2.hasOwnProperty("Tex1")) {
			return loc2.getIntValue("Tex1");
		}
		return param1;
	}

	public int getTex2Id(int param1) {
		XML loc2 = ObjectLibrary.xmlLibrary.get(this.merchandiseType);
		if (loc2 == null) {
			return param1;
		}
		if (loc2.getValue("Activate").equals("Dye") && loc2.hasOwnProperty("Tex2")) {
			return loc2.getIntValue("Tex2");
		}
		return param1;
	}

	public void setMerchandiseType(int param1) {
		this.merchandiseType = param1;
		//this.merchandiseTexture = ObjectLibrary.getRedrawnTextureFromType(this.merchandiseType, 100, false);
	}

	@Override
	public Panel getPanel(AGameSprite param1) {
		// TODO Auto-generated method stub
		return null;
	}

}