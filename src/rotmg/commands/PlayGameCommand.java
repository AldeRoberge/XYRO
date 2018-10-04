package rotmg.commands;

import flash.utils.Date;
import rotmg.AGameSprite;
import rotmg.account.core.services.GetCharListTask;
import rotmg.appengine.SavedCharacter;
import rotmg.core.model.PlayerModel;
import rotmg.lib.tasks.tasks.TaskMonitor;
import rotmg.model.GameInitData;
import rotmg.net.Server;
import rotmg.parameters.Parameters;
import rotmg.pets.data.PetsModel;
import rotmg.xyro.Servers;

public class PlayGameCommand {

	public static final int RECONNECT_DELAY = 2000;

	public GameInitData data;

	public PlayerModel model;

	public PetsModel petsModel;

	public Servers servers;

	public GetCharListTask task;

	public TaskMonitor monitor;

	public PlayGameCommand() {
		super();
		data = GameInitData.getInstance();
		model = PlayerModel.getInstance();
		petsModel = PetsModel.getInstance();
		servers = Servers.getInstance();
		task = GetCharListTask.getInstance();
		monitor = TaskMonitor.getInstance();
	}

	public void execute() {
		if (!this.data.isNewGame) {
			/*this.socketServerModel.connectDelayMS = PlayGameCommand.RECONNECT_DELAY;
			public int connectDelayMS;**/
		}
		this.recordCharacterUseInSharedObject();
		this.makeGameView();
		this.updatePet();
	}

	private void updatePet() {
		SavedCharacter loc1 = this.model.getCharacterById(this.model.currentCharId);
		if (loc1 != null) {
			this.petsModel.setActivePet(loc1.getPetVO());
		} else {
			if (this.model.currentCharId != 0 && this.petsModel.getActivePet() != null && !this.data.isNewGame) {
				return;
			}
			this.petsModel.setActivePet(null);
		}
	}

	private void recordCharacterUseInSharedObject() {
		Parameters.data.charIdUseMap.put(this.data.charId, new Date().getTime());
		Parameters.save();
	}

	public void makeGameView() {
		Server loc1;

		if (this.data.server != null) {
			loc1 = this.data.server;
		} else {
			loc1 = this.servers.getBestServer();
		}

		int loc2 = this.data.isNewGame ? this.getInitialGameId() : this.data.gameId;
		boolean loc3 = this.data.createCharacter;
		int loc4 = this.data.charId;
		int loc5 = this.data.isNewGame ? -1 : this.data.keyTime;
		byte[] loc6 = this.data.key;
		this.model.currentCharId = loc4;
		new AGameSprite(loc1, loc2, loc3, loc4, loc5, loc6, this.model, null, this.data.isFromArena);
	}

	private int getInitialGameId() {
		int loc1 = 0;
		if (Parameters.data.needsTutorial) {
			loc1 = Parameters.TUTORIAL_GAMEID;
		} else if (Parameters.data.needsRandomRealm) {
			loc1 = Parameters.RANDOM_REALM_GAMEID;
		} else {
			loc1 = Parameters.NEXUS_GAMEID;
		}
		return loc1;
	}

}
