package rotmg.objects;

import java.util.ArrayList;
import java.util.List;

import rotmg.map.Map;

public class BasicObject {

	private static int nextFakeObjectId = 0;

	public Map map;

	public Square square;

	public int objectId;

	public double x;

	public double y;

	public double z;

	public boolean hasShadow;

	public boolean drawn;

	public List<Double> posW;

	public List<Double> posS;

	public double sortVal;

	public BasicObject() {
		super();
		this.posW = new ArrayList<Double>();
		this.posS = new ArrayList<Double>();
		this.clear();
	}

	public static int getNextFakeObjectId() {
		return 2130706432 | nextFakeObjectId++;
	}

	public void clear() {
		this.map = null;
		this.square = null;
		this.objectId = -1;
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.hasShadow = false;
		this.drawn = false;
		this.posW.clear();
		this.posS.clear();
		this.sortVal = 0;
	}

	public void dispose() {
		this.map = null;
		this.square = null;
		this.posW = null;
		this.posS = null;
	}

	public boolean update(int time, int dt) {
		return true;
	}

	public boolean addTo(Map map, double par1, double par2) {

		int x = (int) par1;
		int y = (int) par2;

		this.map = map;
		this.square = this.map.getSquare(x, y);
		if (this.square == null) {
			
			System.err.println("Adding an object to a non existent tile");
			
			this.map = null;
			return false;
		}
		this.x = x;
		this.y = y;
		return true;
	}

	public void removeFromMap() {
		System.out.println("Removed from map!");
		
		this.map = null;
		this.square = null;
	}
}
