package rotmg.appengine;

import alde.flash.utils.XML;
import rotmg.constants.GeneralConstants;
import rotmg.objects.ObjectLibrary;
import rotmg.objects.Player;
import rotmg.parameters.Parameters;
import rotmg.pets.data.PetVO;
import rotmg.pets.data.PetsModel;

public class SavedCharacter {

	public XML charXML;

	public String name = null; // Player name

	private PetVO pet;

	public SavedCharacter(XML param1, String param2, PetsModel petsModel) {
		XML loc3 = null;
		int loc4 = 0;
		PetVO loc5 = null;
		this.charXML = param1;
		this.name = param2;
		if (this.charXML.hasOwnProperty("Pet")) {
			loc3 = this.charXML.child("Pet");
			loc4 = loc3.getIntAttribute("instanceId");
			loc5 = petsModel.getPetVO(loc4);
			loc5.apply(loc3);
			this.setPetVO(loc5);
		}
	}

	public static double compare(SavedCharacter param1, SavedCharacter param2) {
		double loc3 = !!Parameters.data.charIdUseMap.hasOwnProperty(param1.charId())
				? Parameters.data.charIdUseMap.get(param1.charId())
				: 0F;
		double loc4 = !!Parameters.data.charIdUseMap.hasOwnProperty(param2.charId())
				? Parameters.data.charIdUseMap.get(param2.charId())
				: 0F;
		if (loc3 != loc4) {
			return loc4 - loc3;
		}
		return param2.xp() - param1.xp();
	}

	public int charId() {
		return this.charXML.getIntAttribute("id");
	}

	public int fameBonus() {
		int loc4 = 0;
		XML loc5 = null;
		Player loc1 = Player.fromPlayerXML("", this.charXML);
		int loc2 = 0;
		int loc3 = 0;
		while (loc3 < GeneralConstants.NUM_EQUIPMENT_SLOTS) {
			if (loc1.equipment != null && loc1.equipment.length > loc3) {
				loc4 = loc1.equipment.get(loc3);
				if (loc4 != -1) {
					loc5 = ObjectLibrary.xmlLibrary.get(loc4);
					if (loc5 != null && loc5.hasOwnProperty("FameBonus")) {
						loc2 = loc2 + loc5.getIntValue("FameBonus");
					}
				}
			}
			loc3++;
		}
		return loc2;
	}

	public String name() {
		return this.name;
	}

	public int objectType() {
		return this.charXML.getIntValue("ObjectType");
	}

	public int skinType() {
		return this.charXML.getIntValue("Texture");
	}

	public int level() {
		return this.charXML.getIntValue("Level");
	}

	public int tex1() {
		return this.charXML.getIntValue("Tex1");
	}

	public int tex2() {
		return this.charXML.getIntValue("Tex2");
	}

	public int xp() {
		return this.charXML.getIntValue("Exp");
	}

	public int fame() {
		return this.charXML.getIntValue("CurrentFame");
	}

	public int hp() {
		return this.charXML.getIntValue("MaxHitPoints");
	}

	public int mp() {
		return this.charXML.getIntValue("MaxMagicPoints");
	}

	public int att() {
		return this.charXML.getIntValue("Attack");
	}

	public int def() {
		return this.charXML.getIntValue("Defense");
	}

	public int spd() {
		return this.charXML.getIntValue("Speed");
	}

	public int dex() {
		return this.charXML.getIntValue("Dexterity");
	}

	public int vit() {
		return this.charXML.getIntValue("HpRegen");
	}

	public int wis() {
		return this.charXML.getIntValue("MpRegen");
	}

	public String displayId() {
		return ObjectLibrary.typeToDisplayId.get(this.objectType());
	}

	public String bornOn() {
		if (!this.charXML.hasOwnProperty("CreationDate")) {
			return "Unknown";
		}
		return this.charXML.getValue("CreationDate");
	}

	public PetVO getPetVO() {
		return this.pet;
	}

	public void setPetVO(PetVO param1) {
		this.pet = param1;
	}

}
