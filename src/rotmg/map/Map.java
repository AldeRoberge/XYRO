package rotmg.map;

import alde.flash.utils.Vector;
import flash.display.BitmapData;
import flash.geom.ColorTransform;
import flash.geom.Point;
import flash.utils.Dictionary;
import rotmg.AGameSprite;
import rotmg.model.GameModel;
import rotmg.objects.BasicObject;
import rotmg.objects.GameObject;
import rotmg.objects.Party;
import rotmg.objects.Square;
import rotmg.stage3D.Renderer;
import rotmg.stage3D.graphic3D.Object3DStage3D;
import rotmg.stage3D.graphic3D.Program3DFactory;
import rotmg.stage3D.graphic3D.TextureFactory;
import rotmg.util.ConditionEffect;

/**
 * 99% match
 */
public class Map extends AbstractMap {

	public static final String CLOTH_BAZAAR = "Cloth Bazaar";

	public static final String NEXUS = "Nexus";

	public static final String DAILY_QUEST_ROOM = "Daily Quest Room";

	public static final String DAILY_LOGIN_ROOM = "Daily Login Room";

	public static final String PET_YARD_1 = "Pet Yard";

	public static final String PET_YARD_2 = "Pet Yard 2";

	public static final String PET_YARD_3 = "Pet Yard 3";

	public static final String PET_YARD_4 = "Pet Yard 4";

	public static final String PET_YARD_5 = "Pet Yard 5";

	public static final String GUILD_HALL = "Guild Hall";

	public static final String NEXUS_EXPLANATION = "Nexus_Explanation";

	public static final String VAULT = "Vault";
	private static final Vector<String> VISIBLE_SORT_FIELDS = new Vector<>("sortVal_", "objectId_");

	public static boolean forceSoftwareRender = false;
	public static BitmapData texture;
	protected static ColorTransform BREATH_CT = new ColorTransform(1, 55 / 255, 0 / 255, 0);
	public boolean ifDrawEffectFlag = true;

	//private RollingMeanLoopMonitor loopMonitor;
	public Vector<BasicObject> visible;
	public Vector<BasicObject> visibleUnder;
	public Vector<Square> visibleSquares;
	public Vector<Square> topSquares;
	private boolean inUpdate = false;
	private Vector<BasicObject> objsToAdd;
	private Vector<Integer> idsToRemove;
	private boolean lastSoftwareClear = false;
	private Vector<Object3DStage3D> graphicsData3d;

	public Map(AGameSprite param1) {
		super();
		this.objsToAdd = new Vector<>();
		this.idsToRemove = new Vector<>();
		//this.darkness = new EmbeddedAssets.DarknessBackground();
		this.graphicsData3d = new Vector<>();
		this.visible = new Vector<>();
		this.visibleUnder = new Vector<>();
		this.visibleSquares = new Vector<>();
		this.topSquares = new Vector<>();
		gs = param1;
		party = new Party(this);
		quest = new Quest(this);
		//this.loopMonitor = RollingMeanLoopMonitor.getInstance();
		GameModel.getInstance().gameObjects = goDict;
	}

	public void setProps(int param1, int param2, String param3, int param4, boolean param5, boolean param6) {
		width = param1;
		height = param2;
		name = param3;
		back = param4;
		allowPlayerTeleport = param5;
		showDisplays = param6;
	}

	public void initialize() {

		//squares.length = width * height;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				squares.add(new Square(this, x, y));
			}
		}

		isPetYard = name.substring(0, 8).equals("Pet Yard");
	}

	public void dispose() {
		gs = null;

		for (Square loc1 : squareList) {
			loc1.dispose();
		}
		squareList.length = 0;
		squareList = null;
		squares.length = 0;
		squares.clear();
		for (GameObject loc2 : goDict) {
			loc2.dispose();
		}
		goDict.clear();
		for (BasicObject loc3 : boDict) {
			loc3.dispose();
		}
		boDict.clear();
		merchLookup = null;
		player = null;
		party = null;
		quest = null;
		this.objsToAdd = null;
		this.idsToRemove = null;
		TextureFactory.disposeTextures();
		Program3DFactory.getInstance().dispose();
	}

	public void update(int param1, int param2) {
		this.inUpdate = true;
		for (BasicObject loc3 : goDict) {
			if (!loc3.update(param1, param2)) {
				this.idsToRemove.add(loc3.objectId);
			}
		}
		for (BasicObject loc3 : boDict) {
			if (!loc3.update(param1, param2)) {
				this.idsToRemove.add(loc3.objectId);
			}
		}
		this.inUpdate = false;
		for (BasicObject loc3 : this.objsToAdd) {
			this.internalAddObj(loc3);
		}
		this.objsToAdd.length = 0;
		for (int loc4 : this.idsToRemove) {
			this.internalRemoveObj(loc4);
		}
		this.idsToRemove.length = 0;
		party.update(param1, param2);
	}

	public Point pSTopW(double param1, double param2) {
		for (Square loc3 : this.visibleSquares) {
			if (loc3.faces.length != 0 && loc3.faces.get(0).face.contains(param1, param2)) {
				return new Point(loc3.center.x, loc3.center.y);
			}
		}
		return null;
	}

	@Override
	public void setGroundTile(int x, int y, int tileType) {

		//System.out.println("Ground type : " + tileType + " for x : " + x + " y : " + y);

		int yi = 0;
		int ind = 0;
		Square n = null;
		Square square = this.getSquare(x, y);
		square.setTileType(tileType);
		int xend = x < this.width - 1 ? x + 1 : x;
		int yend = y < this.height - 1 ? y + 1 : y;
		for (int xi = x > 0 ? x - 1 : x; xi <= xend; xi++) {
			for (yi = y > 0 ? y - 1 : y; yi <= yend; yi++) {
				ind = xi + yi * this.width;
				n = this.squares.get(ind);
				if (n != null && (n.props.hasEdge || n.tileType != tileType)) {
					n.faces.length = 0;
				}
			}
		}
	}

	public void addObj(BasicObject param1, double param2, double param3) {
		param1.x = param2;
		param1.y = param3;
		if (this.inUpdate) {
			this.objsToAdd.add(param1);
		} else {
			this.internalAddObj(param1);
		}
	}

	public void internalAddObj(BasicObject param1) {
		if (!param1.addTo(this, param1.x, param1.y)) {
			return;
		}
		Dictionary loc2 = param1 instanceof GameObject ? goDict : boDict;
		if (loc2.get(param1.objectId) != null) {
			if (!isPetYard) {
				return;
			}
		}
		loc2.put(param1.objectId, param1);
	}

	public void removeObj(int param1) {
		if (this.inUpdate) {
			this.idsToRemove.add(param1);
		} else {
			this.internalRemoveObj(param1);
		}
	}

	public void internalRemoveObj(int param1) {
		BasicObject loc3 = goDict.get(param1);
		if (loc3 == null) {
			loc3 = boDict.get(param1);
			if (loc3 == null) {
				return;
			}
		}
		loc3.removeFromMap();
		boDict.remove(param1);
	}

	public Square getSquare(double par1, double par2) {

		int param1 = (int) par1;
		int param2 = (int) par2;

		if (param1 < 0 || param1 >= width || param2 < 0 || param2 >= height) {
			return null;
		}
		int loc3 = (int) (param1 + param2 * width);
		Square loc4 = squares.get(loc3);
		if (loc4 == null) {
			loc4 = new Square(this, param1, param2);
			squares.put(loc3, loc4);
			squareList.add(loc4);
		}
		return loc4;
	}

	public Square lookupSquare(double param1, double param2) {
		if (param1 < 0 || param1 >= width || param2 < 0 || param2 >= height) {
			return null;
		}
		return squares.get((int) (param1 + param2 * width));
	}

	private int getFilterIndex() {
		int loc1 = 0;
		if (player != null && (player.condition.get(ConditionEffect.CE_FIRST_BATCH) & ConditionEffect.MAP_FILTER_BITMASK) != 0) {
			if (player.isPaused()) {
				loc1 = Renderer.STAGE3D_FILTER_PAUSE;
			} else if (player.isBlind()) {
				loc1 = Renderer.STAGE3D_FILTER_BLIND;
			} else if (player.isDrunk()) {
				loc1 = Renderer.STAGE3D_FILTER_DRUNK;
			}
		}
		return loc1;
	}
}