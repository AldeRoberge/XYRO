package rotmg;

import flash.system.Capabilities;
import rotmg.account.core.WebAccount;
import rotmg.core.model.PlayerModel;
import rotmg.net.Server;
import rotmg.parameters.Parameters;
import rotmg.util.AssetLoader;

//[SWF(width=800,height=600,frameRate=60,backgroundColor=000000)]
public class WebMain {

	public WebMain() {
		super();
		this.setup();
	}

	private void setup() {
		new AssetLoader().load();
		this.configureForAirIfDesktopPlayer();

		// Following is a loose implementation of PlayGameCommand's makeGameView

		PlayerModel p = PlayerModel.getInstance();
		p.account = WebAccount.getInstance();
		p.currentCharId = 2;
		p.setIsAgeVerified(true);

		Server loc1 = new Server().setAddress("54.183.179.205").setPort(2050);

		boolean createCharacter = false;
		int keyTime = -1;
		byte[] loc6 = new byte[0];
		boolean isFromArena = false;

		AGameSprite g = new AGameSprite(loc1, Parameters.NEXUS_GAMEID, createCharacter, p.currentCharId, keyTime, loc6, p,
				null, isFromArena);
		g.connect();
	}

	private void configureForAirIfDesktopPlayer() {
		if (Capabilities.playerType.equals("Desktop")) {
			Parameters.data.fullscreenMode = false;
			Parameters.save();
		}
	}
}
