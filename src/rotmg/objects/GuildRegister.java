
package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class GuildRegister extends GameObject implements IInteractiveObject {

	public GuildRegister(XML objectXML) {
		super(objectXML);
		isInteractive = true;
	}

	public Panel getPanel(GameSprite gs) {
		return null;
	}
}