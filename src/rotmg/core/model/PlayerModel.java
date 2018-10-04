package rotmg.core.model;


import org.osflash.signals.Signal;

import alde.flash.utils.Vector;
import rotmg.account.core.Account;
import rotmg.appengine.SavedCharacter;
import rotmg.appengine.SavedCharactersList;
import rotmg.appengine.SavedNewsItem;
import rotmg.net.LatLong;

public class PlayerModel {

	public static final int[] CHARACTER_SLOT_PRICES = new int[]{600, 800, 1000};

	public static Signal creditsChanged = new Signal<Integer>();

	public static Signal fameChanged = new Signal<Integer>();

	public static Signal tokensChanged = new Signal<Integer>();

	private static PlayerModel instance;
	public SavedCharactersList charList;
	public boolean isInvalidated;
	public int currentCharId;
	public Account account;
	private boolean isAgeVerified;

	public PlayerModel() {
		super();
		this.isInvalidated = true;
	}

	public static PlayerModel getInstance() {
		if (instance == null) {
			instance = new PlayerModel();
		}

		return instance;
	}

	public int getCurrentCharId() {
		return this.currentCharId;
	}

	public void setCurrentCharId(int param1) {
		this.currentCharId = param1;
	}

	public boolean getHasPlayerDied() {
		return charList.hasPlayerDied;
	}

	public void setHasPlayerDied(boolean param1) {
		charList.hasPlayerDied = param1;
	}

	public boolean getIsAgeVerified() {
		return (this.isAgeVerified) || charList.isAgeVerified;
	}

	public void setIsAgeVerified(boolean param1) {
		this.isAgeVerified = true;
	}

	public boolean isNewPlayer() {
		return charList.nextCharId == 1;
	}

	public int getMaxCharacters() {
		return charList.maxNumChars;
	}

	public void setMaxCharacters(int param1) {
		charList.maxNumChars = param1;
	}

	public int getCredits() {
		return charList.credits;
	}

	public void setCredits(int param1) {
		if (charList.credits != param1) {
			charList.credits = param1;
			this.creditsChanged.dispatch(param1);
		}
	}

	public String getSalesForceData() {
		return charList.salesForceData;
	}

	public void changeCredits(int param1) {
		charList.credits = charList.credits + param1;
		this.creditsChanged.dispatch(charList.credits);
	}

	public int getFame() {
		return charList.fame;
	}

	public void setFame(int param1) {
		if (charList.fame != param1) {
			charList.fame = param1;
			this.fameChanged.dispatch(param1);
		}
	}

	public int getTokens() {
		return charList.tokens;
	}

	public void setTokens(int param1) {
		if (charList.tokens != param1) {
			charList.tokens = param1;
			this.tokensChanged.dispatch(param1);
		}
	}

	public int getCharacterCount() {
		return charList.numChars;
	}

	public SavedCharacter getCharById(int param1) {
		return charList.getCharById(param1);
	}

	public void deleteCharacter(int param1) {
		SavedCharacter loc2 = charList.getCharById(param1);

		if (charList.savedChars.contains(loc2)) {
			charList.savedChars.remove(loc2);
			charList.numChars--;
		}
	}

	public String getAccountId() {
		return charList.accountId;
	}

	public boolean hasAccount() {
		return charList.accountId != "";
	}

	public int getNumStars() {
		return charList.numStars;
	}

	public String getGuildName() {
		return charList.guildName;
	}

	public int getGuildRank() {
		return charList.guildRank;
	}

	public int getNextCharSlotPrice() {
		int loc1 = Math.min(CHARACTER_SLOT_PRICES.length - 1, charList.maxNumChars - 1);
		return CHARACTER_SLOT_PRICES[loc1];
	}

	public int getTotalFame() {
		return charList.totalFame;
	}

	public int getNextCharId() {
		return charList.nextCharId;
	}

	public SavedCharacter getCharacterById(int param1) {
		for (SavedCharacter loc2 : charList.savedChars) {
			if (loc2.charId() == param1) {
				return loc2;
			}
		}
		return null;
	}

	public SavedCharacter getCharacterByIndex(int param1) {
		return charList.savedChars.get(param1);
	}

	public boolean isAdmin() {
		return charList.isAdmin;
	}

	public boolean mapEditor() {
		return charList.canMapEdit;
	}

	public Vector<SavedNewsItem> getNews() {
		return charList.news;
	}

	public LatLong getMyPos() {
		return charList.myPos;
	}

	public void setClassAvailability(int param1, String param2) {
		charList.classAvailability.put(param1, param2);
	}

	public String getName() {
		return charList.name;
	}

	public void setName(String param1) {
		charList.name = param1;
	}

	public boolean getConverted() {
		return charList.converted;
	}

	public boolean isNameChosen() {
		return charList.nameChosen;
	}

	public Vector getNewUnlocks(int param1, int param2) {
		return charList.newUnlocks(param1, param2);
	}

	public boolean hasAvailableCharSlot() {
		return charList.hasAvailableCharSlot();
	}

	public int getAvailableCharSlots() {
		return charList.availableCharSlots();
	}

	public Vector<SavedCharacter> getSavedCharacters() {
		return charList.savedChars;
	}

	public Object getCharStats() {
		return charList.charStats;
	}

	public boolean isClassAvailability(String param1, String param2) {
		String loc3 = charList.classAvailability.get(param1);
		return loc3.equals(param2);
	}

	public boolean isLevelRequirementsMet(int param1) {
		return charList.levelRequirementsMet(param1);
	}

	public int getBestFame(int param1) {
		return charList.bestFame(param1);
	}

	public int getBestLevel(int param1) {
		return charList.bestLevel(param1);
	}

	public int getBestCharFame() {
		return charList.bestCharFame;
	}

	public void setCharacterList(SavedCharactersList param1) {
		charList = param1;
	}

	public boolean isNewToEditing() {
		if (charList != null && !charList.isFirstTimeLogin()) {
			return false;
		}
		return true;
	}


}
