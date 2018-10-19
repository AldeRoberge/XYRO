package rotmg;

import accountdb.AccountDatabaseController;
import alde.flash.utils.XML;
import oryx2D.Window;
import rotmg.account.core.Account;
import rotmg.account.core.services.AppEngine;
import rotmg.core.model.PlayerModel;
import rotmg.net.Server;
import rotmg.parameters.Parameters;
import rotmg.util.AssetLoader;
import rotmg.xyro.Servers;

public class WebMain {

	// Following is a loose implementation of PlayGameCommand's makeGameView
	public static void main(String[] args) {

		//Start Oryx2D
		Window.main(null);

		//Load assets
		new AssetLoader().load();

		//Bootstrap servers
		Servers.getInstance().makeListOfServers(new XML(AppEngine.getCharListAsString()));

		//Launch bots
		Server useast2 = Servers.getInstance().getServerByName("USEast2");

		connectNewBot(useast2, AccountDatabaseController.getAccount());

	}

	private static void connectNewBot(Server server, Account account) {

		PlayerModel i = new PlayerModel(account);

		boolean createCharacter = false;

		if (i.charList.savedChars.size() > 0) {
			i.currentCharId = i.charList.savedChars.get(0).charId();
		} else {
			createCharacter = true;
		}

		i.setIsAgeVerified(true);

		int keyTime = -1;
		byte[] key = new byte[0];
		boolean isFromArena = false;
		boolean isNewGame = true;

		GameSprite g = new GameSprite(i, server, Parameters.NEXUS_GAMEID, createCharacter, i.currentCharId, keyTime, key, isNewGame, isFromArena);

	}

}
