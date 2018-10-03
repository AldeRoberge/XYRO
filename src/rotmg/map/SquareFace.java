package rotmg.map;

import alde.flash.utils.Vector;
import flash.display.BitmapData;
import rotmg.engine3d.Face3D;

public class SquareFace {

	public int animate;

	public Face3D face;
	
	public double xOffset = 0;

	public double yOffset = 0;

	public double animateDx = 0;

	public double animateDy = 0;

	public SquareFace(BitmapData param1, Vector<Double> param2, double param3, double param4, int param5, double param6,
			double param7) {
		super();

		this.xOffset = param3;
		this.yOffset = param4;

		this.animate = param5;

		this.animateDx = param6;
		this.animateDy = param7;
	}
	
	public void dispose() {
		
	}

}
