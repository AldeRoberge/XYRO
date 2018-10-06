package rotmg.map;

import org.osflash.signals.Signal;

import alde.flash.utils.Vector;
import flash.geom.Point;
import flash.utils.Dictionary;
import rotmg.AGameSprite;
import rotmg.objects.BasicObject;
import rotmg.objects.GameObject;
import rotmg.objects.Merchant;
import rotmg.objects.Party;
import rotmg.objects.Player;
import rotmg.objects.Square;
import rotmg.util.IntPoint;

/**
 * 90% match (except I made everything abstract)
 * made some stuff static for Oryx2D
 */
public abstract class AbstractMap {

	public final Vector<Square> squares = new Vector<Square>(); // Tiles
	public final Dictionary<Integer, BasicObject> boDict = new Dictionary<>(); // Basic Objects
	public final Dictionary<Integer, GameObject> goDict = new Dictionary<>(); // Game Objects

	public AGameSprite gs;
	public String name;
	public Player player = null;
	public boolean showDisplays;
	public int width;
	public int height;
	public int back;
	public Vector<Square> squareList;
	public Dictionary<IntPoint, Merchant> merchLookup;
	public Party party = null;
	public Quest quest = null;
	public Signal<Boolean> signalRenderSwitch;
	public boolean isPetYard = false;
	protected boolean allowPlayerTeleport;
	protected boolean wasLastFrameGpu = false;

	public AbstractMap() {
		this.squareList = new Vector<Square>();
		this.merchLookup = new Dictionary<>();
		this.signalRenderSwitch = new Signal<Boolean>();
	}

	public abstract void setProps(int param1, int param2, String param3, int param4, boolean param5, boolean param6);

	public abstract void addObj(BasicObject param1, double param2, double param3);

	public abstract void setGroundTile(int param1, int param2, int param3);

	public abstract void initialize();

	public abstract void dispose();

	public abstract void update(int param1, int param2);

	public Point pSTopW(double param1, double param2) {
		return null;
	}

	public abstract void removeObj(int param1);

	public boolean allowPlayerTeleport() {
		return this.name != Map.NEXUS && this.allowPlayerTeleport;
	}

}
