package rotmg.objects;


import alde.flash.utils.XML;

public class ConnectedWall extends ConnectedObject {

	protected XML objectXML;

	protected double bI = 0.5;

	protected double tI = 0.25;

	protected double h = 1.0;

	protected boolean wallRepeat;

	protected boolean topRepeat;

	public ConnectedWall(XML objectXML) {
		super(objectXML);
		this.objectXML = objectXML;
		if (objectXML.hasOwnProperty("BaseIndent")) {
			this.bI = 0.5 - objectXML.getDoubleAttribute("BaseIndent");
		}
		if (objectXML.hasOwnProperty("TopIndent")) {
			this.tI = 0.5 - objectXML.getDoubleAttribute("TopIndent");
		}
		if (objectXML.hasOwnProperty("Height")) {
			this.h = objectXML.getDoubleAttribute("Height");
		}
		this.wallRepeat = !objectXML.hasOwnProperty("NoWallTextureRepeat");
		this.topRepeat = !objectXML.hasOwnProperty("NoTopTextureRepeat");
	}

}
