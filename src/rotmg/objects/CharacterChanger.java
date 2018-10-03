package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.AGameSprite;

public class CharacterChanger extends GameObject implements IInteractiveObject {

	public CharacterChanger(XML param1) {
		super(param1);
		isInteractive = true;
	}

	@Override
	public Panel getPanel(AGameSprite param1) {
		// TODO Auto-generated method stub
		return null;
	}


}
