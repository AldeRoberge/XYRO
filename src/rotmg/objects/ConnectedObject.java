package rotmg.objects;


import alde.flash.utils.XML;
import flash.geom.Vector3D;
import flash.utils.Dictionary;

public class ConnectedObject extends GameObject {

	protected static final int DOT_TYPE = 0;

	protected static final int SHORT_LINE_TYPE = 1;

	protected static final int L_TYPE = 2;

	protected static final int LINE_TYPE = 3;

	protected static final int T_TYPE = 4;

	protected static final int CROSS_TYPE = 5;

	private static Dictionary dict = null;

	protected static final Vector3D N0 = new Vector3D(-1, -1, 0);

	protected static final Vector3D N1 = new Vector3D(0, -1, 0);

	protected static final Vector3D N2 = new Vector3D(1, -1, 0);

	protected static final Vector3D N3 = new Vector3D(1, 0, 0);

	protected static final Vector3D N4 = new Vector3D(1, 1, 0);

	protected static final Vector3D N5 = new Vector3D(0, 1, 0);

	protected static final Vector3D N6 = new Vector3D(-1, 1, 0);

	protected static final Vector3D N7 = new Vector3D(-1, 0, 0);

	protected static final Vector3D N8 = new Vector3D(0, 0, 1);

	protected int rotation = 0;

	public ConnectedObject(XML objectXML) {
		super(objectXML);
		hasShadow = false;
	}

	private static void init() {
		dict = new Dictionary();
		/*initHelper(33686018, DOT_TYPE);
		initHelper(16908802, SHORT_LINE_TYPE);
		initHelper(16843266, L_TYPE);
		initHelper(16908546, LINE_TYPE);
		initHelper(16843265, T_TYPE);
		initHelper(16843009, CROSS_TYPE);**/
	}

}

class ConnectedResults {

	public int type;

	public int rotation;

	ConnectedResults(int type, int rotation) {
		super();
		this.type = type;
		this.rotation = rotation;
	}
}
