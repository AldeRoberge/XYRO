package rotmg;

import rotmg.account.core.Account;
import rotmg.account.core.services.AppEngine;
import rotmg.appengine.SavedCharacter;
import rotmg.appengine.SavedCharactersList;
import rotmg.core.model.PlayerModel;
import rotmg.net.Server;
import rotmg.parameters.Parameters;
import rotmg.pets.data.PetsModel;
import rotmg.util.AssetLoader;
import rotmg.xyro.Servers;

public class WebMain {

	// Following is a loose implementation of PlayGameCommand's makeGameView
	public static void main(String[] args) {
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

		RealmClient g = new RealmClient(i, server, Parameters.NEXUS_GAMEID, createCharacter, i.currentCharId, keyTime, key, isNewGame, isFromArena);
	
	
		
	
	}

}
