package rotmg;

import static flash.utils.timer.getTimer.getTimer;

import org.osflash.signals.Signal;

import rotmg.appengine.SavedCharacter;
import rotmg.constants.GeneralConstants;
import rotmg.core.model.MapModel;
import rotmg.core.model.PlayerModel;
import rotmg.map.AbstractMap;
import rotmg.map.Map;
import rotmg.messaging.GameServerConnection;
import rotmg.messaging.GameServerConnectionConcrete;
import rotmg.messaging.incoming.MapInfo;
import rotmg.net.Server;
import rotmg.objects.GameObject;
import rotmg.objects.IInteractiveObject;
import rotmg.objects.Pet;
import rotmg.objects.Player;
import rotmg.parameters.Parameters;
import rotmg.stage3D.Renderer;
import rotmg.util.PointUtil;
import rotmg.xyro.Servers;

/**
 * This is a compilation of GameSprite, AGameSprite, PlayGameCommand and GameInitData.
 * @author Alde
 *
 */
public class GameSprite {

	public boolean isNexus = false;
	public MapModel mapModel;
	private boolean isGameStarted;
	public final Signal closed = new Signal();
	public boolean isEditor;
	public int lastUpdate;

	public MoveRecords moveRecords;
	public AbstractMap map;

	public PlayerModel playerModel;
	public GameServerConnection gsc;

	public static final int RECONNECT_DELAY = 2000;

	public GameSprite(PlayerModel playerModel, Server server, int gameId, boolean createCharacter, int charId, int keyTime, byte[] key, boolean isNewGame, boolean isFromArena) {

		this.playerModel = playerModel;

		this.updatePet(isNewGame);

		if (server == null) {
			server = Servers.getInstance().getBestServer(playerModel);
		}

		int actual_keyTime = isNewGame ? -1 : keyTime;
		this.playerModel.currentCharId = charId;
		this.moveRecords = new MoveRecords();

		map = new Map(this);
		gsc = new GameServerConnectionConcrete(this, server, gameId, createCharacter, charId, actual_keyTime, key, null, isFromArena);

		connect();
	}

	private void updatePet(boolean isNewGame) {
		SavedCharacter loc1 = this.playerModel.getCharacterById(this.playerModel.currentCharId);
		if (loc1 != null) {
			this.playerModel.petsModel.setActivePet(loc1.getPetVO());
		} else {
			if (this.playerModel.currentCharId != 0 && this.playerModel.petsModel.getActivePet() != null && !isNewGame) {
				return;
			}
			this.playerModel.petsModel.setActivePet(null);
		}
	}

	public void applyMapInfo(MapInfo param1) {
		map.setProps(param1.width, param1.height, param1.name, param1.background, param1.allowPlayerTeleport, param1.showDisplays);
	}

	public void initialize() {
		map.initialize();
		this.isNexus = map.name.equals(Map.NEXUS);
		Parameters.save();
	}

	private void updateNearestInteractive() {
		double loc4 = 0;
		IInteractiveObject loc8 = null;
		if (map == null || map.player == null) {
			return;
		}
		Player loc1 = map.player;
		double loc2 = GeneralConstants.MAXIMUM_INTERACTION_DISTANCE;
		IInteractiveObject loc3 = null;
		double loc5 = loc1.x;
		double loc6 = loc1.y;
		for (GameObject loc7 : map.goDict) {
			loc8 = (IInteractiveObject) loc7;
			if (loc8 != null && (!(loc8 instanceof Pet) || !this.map.isPetYard)) {
				if (Math.abs(loc5 - loc7.x) < GeneralConstants.MAXIMUM_INTERACTION_DISTANCE || Math.abs(loc6 - loc7.y) < GeneralConstants.MAXIMUM_INTERACTION_DISTANCE) {
					loc4 = PointUtil.distanceXY(loc7.x, loc7.y, loc5, loc6);
					if (loc4 < GeneralConstants.MAXIMUM_INTERACTION_DISTANCE && loc4 < loc2) {
						loc2 = loc4;
						loc3 = loc8;
					}
				}
			}
		}
		this.mapModel.currentInteractiveTarget = loc3;
	}

	public void connect() {
		if (!this.isGameStarted) {
			this.isGameStarted = true;
			Renderer.inGame = true;
			gsc.connect();
			lastUpdate = getTimer();
		}
	}

	public void disconnect() {
		if (this.isGameStarted) {
			this.isGameStarted = false;
			Renderer.inGame = false;
			map.dispose();
			gsc.disconnect();
		}
	}

	public boolean evalIsNotInCombatMapArea() {
		return map.name.equals(Map.NEXUS) || map.name.equals(Map.VAULT) || map.name.equals(Map.GUILD_HALL) || map.name.equals(Map.CLOTH_BAZAAR) || map.name.equals(Map.NEXUS_EXPLANATION)
				|| map.name.equals(Map.DAILY_QUEST_ROOM);
	}

}
