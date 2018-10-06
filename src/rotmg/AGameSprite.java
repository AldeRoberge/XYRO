package rotmg;

import static flash.utils.timer.getTimer.getTimer;

import org.osflash.signals.Signal;

import flash.events.Event;
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

public class AGameSprite {

	public boolean isNexus = false;

	public MapModel mapModel;

	private boolean isGameStarted;

	public final Signal closed = new Signal();

	public boolean isEditor;

	public int lastUpdate;

	public MoveRecords moveRecords;

	public AbstractMap map;

	public PlayerModel model;

	public GameServerConnection gsc;

	public AGameSprite(Server server, int gameId, boolean createCharacter, int charId, int keyTime, byte[] key, PlayerModel model, byte[] mapJSON, boolean isFromArena) {
		super();

		this.model = model;

		this.moveRecords = new MoveRecords();

		map = new Map(this);
		gsc = new GameServerConnectionConcrete(this, server, gameId, createCharacter, charId, keyTime, key, mapJSON, isFromArena);
	}

	public void setFocus(GameObject param1) {
		param1 = map.player;
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

	private boolean isPetMap() {
		return true;
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

	private void onMoneyChanged(Event param1) {
		gsc.checkCredits();
	}

	public boolean evalIsNotInCombatMapArea() {
		return map.name.equals(Map.NEXUS) || map.name.equals(Map.VAULT) || map.name.equals(Map.GUILD_HALL) || map.name.equals(Map.CLOTH_BAZAAR) || map.name.equals(Map.NEXUS_EXPLANATION)
				|| map.name.equals(Map.DAILY_QUEST_ROOM);
	}

	public void showPetToolTip(boolean param1) {
	}
}
