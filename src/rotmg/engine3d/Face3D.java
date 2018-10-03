package rotmg.engine3d;

import alde.flash.utils.Vector;
import flash.display.BitmapData;
import flash.geom.Vector3D;

public class Face3D {

	public BitmapData origTexture;

	public Vector<Double> vin;

	public Vector<Double> uvt;

	public Vector<Double> vout;

	public boolean backfaceCull;

	public double shade = 1.0;

	public boolean blackOut = false;
	private boolean needGen = true;
	private TextureMatrix textureMatrix = null;

	public Face3D(BitmapData param1, Vector<Double> param2, Vector<Double> param3) {
		this(param1, param2, param3, false, false);
	}

	public Face3D(BitmapData param1, Vector<Double> param2, Vector<Double> param3, boolean param4, boolean param5) {
		Vector3D loc7 = null;
		this.vout = new Vector<Double>();
		this.origTexture = param1;
		this.vin = param2;
		this.uvt = param3;
		this.backfaceCull = param4;
		if (param5) {
			loc7 = new Vector3D();
			Plane3D.computeNormalVec(param2, loc7);
			this.shade = Lighting3D.shadeValue(loc7, 0.75);
		}
	}

	public void dispose() {
		this.origTexture = null;
		this.vin = null;
		this.uvt = null;
		this.vout = null;
		this.textureMatrix = null;
	}

	public void setTexture(BitmapData param1) {
		if (this.origTexture == param1) {
			return;
		}
		this.origTexture = param1;
		this.needGen = true;
	}

	public void setUVT(Vector<Double> param1) {
		this.uvt = param1;
		this.needGen = true;
	}

	public double maxY() {
		double loc1 = -Double.MAX_VALUE;
		int loc2 = this.vout.length;
		int loc3 = 0;
		while (loc3 < loc2) {
			if (this.vout.get(loc3 + 1) > loc1) {
				loc1 = this.vout.get(loc3 + 1);
			}
			loc3 = loc3 + 2;
		}
		return loc1;
	}

	public boolean contains(double param1, double param2) {
		if (Triangle.containsXY(this.vout.get(0), this.vout.get(1), this.vout.get(2), this.vout.get(3),
				this.vout.get(4), this.vout.get(5), param1, param2)) {
			return true;
		}
		if (this.vout.length == 8 && Triangle.containsXY(this.vout.get(0), this.vout.get(1), this.vout.get(4),
				this.vout.get(5), this.vout.get(6), this.vout.get(7), param1, param2)) {
			return true;
		}
		return false;
	}

}
