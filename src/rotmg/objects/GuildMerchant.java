
package rotmg.objects;

import java.awt.Panel;

import alde.flash.utils.XML;
import rotmg.GameSprite;

public class GuildMerchant extends SellableObject implements IInteractiveObject {

	public String description;

	public GuildMerchant(XML objectXML) {
		super(objectXML);
		/*price = int(objectXML.Price);
		currency = Currency.GUILD_FAME;
		this.description = objectXML.Description;
		guildRankReq = GuildUtil.LEADER;*/
	}

	@Override
	public Panel getPanel(GameSprite param1) {
		// TODO Auto-generated method stub
		return null;
	}

}
