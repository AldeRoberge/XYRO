
package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class PetUpgrader extends GameObject implements IInteractiveObject {

	public PetUpgrader(XML objectXML) {
		super(objectXML);
		isInteractive = true;
	}

	@Override
	public Panel getPanel(GameSprite param1) {
		// TODO Auto-generated method stub
		return null;
	}


}
