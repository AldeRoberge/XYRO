package rotmg;

import oryx2D.Window;
import rotmg.account.core.Account;
import rotmg.core.model.PlayerModel;
import rotmg.net.Server;
import rotmg.parameters.Parameters;
import rotmg.util.AssetLoader;
import rotmg.xyro.Servers;

public class WebMain {

	// Following is a loose implementation of PlayGameCommand's makeGameView
	public static void main(String[] args) {

		Window.main(null);

		new AssetLoader().load();

		Account account = new Account("clevertonx@gmail.com", "harryporco1");

		PlayerModel i = new PlayerModel(account);
		i.currentCharId = i.charList.savedChars.get(0).charId();

		i.setIsAgeVerified(true);
		Server server = Servers.getInstance().getServerByName("USEast2");

		boolean createCharacter = false;
		int keyTime = -1;
		byte[] key = new byte[0];
		boolean isFromArena = false;
		boolean isNewGame = true;

		GameSprite g = new GameSprite(i, server, Parameters.NEXUS_GAMEID, createCharacter, i.currentCharId, keyTime, key, isNewGame, isFromArena);

	}

}
