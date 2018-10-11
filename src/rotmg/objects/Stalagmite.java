
package rotmg.objects;

import alde.flash.utils.XML;

public class Stalagmite extends GameObject {

	private static final double bs = Math.PI / 6;

	private static final double cs = Math.PI / 3;

	public Stalagmite(XML objectXML) {
		super(objectXML);
		double a1 = bs + cs * Math.random();
		double a2 = 2 * cs + bs + cs * Math.random();
		double a3 = 4 * cs + bs + cs * Math.random();
		/**obj3D = new Object3D();
		obj3D.vL.add(Math.cos(a1) * 0.3, Math.sin(a1) * 0.3, 0, Math.cos(a2) * 0.3, Math.sin(a2) * 0.3, 0, Math.cos(a3) * 0.3, Math.sin(a3) * 0.3, 0, 0, 0, 0.6 + 0.6 * Math.random());
		obj3D.faces.add(new ObjectFace3D(obj3D, new Integer[]{0, 1, 3}), new ObjectFace3D(obj3D, new Integer[]{1, 2, 3}), new ObjectFace3D(obj3D, new Integer[]{2, 0, 3}));
		obj3D.uvts.add(0, 1, 0, 0.5, 1, 0, 1, 1, 0, 0.5, 0, 0);*/
	}
}
