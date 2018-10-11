package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class ArenaPortal extends Portal implements IInteractiveObject {

	public ArenaPortal(XML objectXML) {
		super(objectXML);
		isInteractive = true;
		name = "";
	}

	public Panel getPanel(GameSprite gs) {
		return null;
	}

	/**public void draw(Vector<IGraphicsData> graphicsData, Camera camera, int time) {
		super.draw(graphicsData, camera, time);
		drawName(graphicsData, camera);
	}
	
	protected BitmapData makeNameBitmapData() {
		StringBuilder builder = new StaticStringBuilder(name);
		BitmapTextFactory factory = StaticInjectorContext.getInjector().getInstance(BitmapTextFactory);
		return factory.make(builder, 16, 16777215, true, IDENTITY_MATRIX, true);
	}*/
}
