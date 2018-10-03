package rotmg.util;

import alde.flash.utils.XML;
import flash.display.BitmapData;
import flash.geom.ColorTransform;
import rotmg.objects.ObjectLibrary;

public class FameUtil {

	public static final int MAX_STARS = 70;

	public static final Integer[] STARS = new Integer[] { 20, 150, 400, 800, 2000 };

	private static final ColorTransform lightBlueCT = new ColorTransform(138 / 255, 152 / 255, 222 / 255);

	private static final ColorTransform darkBlueCT = new ColorTransform(49 / 255, 77 / 255, 219 / 255);

	private static final ColorTransform redCT = new ColorTransform(193 / 255, 39 / 255, 45 / 255);

	private static final ColorTransform orangeCT = new ColorTransform(247 / 255, 147 / 255, 30 / 255);

	private static final ColorTransform yellowCT = new ColorTransform(255 / 255, 255 / 255, 0 / 255);

	public static final ColorTransform[] COLORS = new ColorTransform[] { lightBlueCT, darkBlueCT, redCT, orangeCT,
			yellowCT };

	public FameUtil() {
		super();
	}

	public static int maxStars() {
		return ObjectLibrary.playerChars.size() * STARS.length;
	}

	public static int numStars(int param1) {
		int loc2 = 0;
		while (loc2 < STARS.length && param1 >= STARS[loc2]) {
			loc2++;
		}
		return loc2;
	}

	public static int nextStarFame(int param1, int param2) {
		int loc3 = Math.max(param1, param2);
		int loc4 = 0;
		while (loc4 < STARS.length) {
			if (STARS[loc4] > loc3) {
				return STARS[loc4];
			}
			loc4++;
		}
		return -1;
	}

	public static int numAllTimeStars(int param1, int param2, XML param3) {
		int loc4 = 0;
		int loc5 = 0;
		for (XML loc6 : param3.children("ClassStats")) {
			if (param1 == loc6.getIntAttribute("objectType")) {
				loc5 = loc6.getIntValue("BestFame");
			} else {
				loc4 = loc4 + FameUtil.numStars(loc6.getIntValue("BestFame"));
			}
		}
		loc4 = loc4 + FameUtil.numStars(Math.max(loc5, param2));
		return loc4;
	}

	public static BitmapData getFameIcon() {
		return AssetLibrary.getImageFromSet("lofiObj3", 224);
	}

}
