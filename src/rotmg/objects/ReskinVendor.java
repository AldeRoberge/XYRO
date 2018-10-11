
package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class ReskinVendor extends GameObject implements IInteractiveObject {

	public ReskinVendor(XML objectXML) {
		super(objectXML);
		isInteractive = true;
	}

	public Panel getPanel(GameSprite gs) {
		return null;
	}
}