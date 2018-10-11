
package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class NameChanger extends GameObject implements IInteractiveObject {

	public int rankRequired = 0;

	public NameChanger(XML objectXML) {
		super(objectXML);
		isInteractive = true;
	}

	public void setRankRequired(int rank) {
		this.rankRequired = rank;
	}

	@Override
	public Panel getPanel(GameSprite param1) {
		// TODO Auto-generated method stub
		return null;
	}

}
