package rotmg.account.core.services;

import alde.flash.utils.XML;
import flash.utils.timer.Timer;
import rotmg.account.core.WebAccount;
import rotmg.account.core.signals.CharListDataSignal;
import rotmg.account.securityQuestions.data.SecurityQuestionsModel;
import rotmg.appengine.api.AppEngineClient;
import rotmg.core.model.PlayerModel;
import rotmg.core.signals.SetLoadingMessageSignal;
import rotmg.lib.tasks.tasks.BaseTask;
import rotmg.parameters.Parameters;

public class GetCharListTask extends BaseTask {

	private static final int ONE_SECOND_IN_MS = 1000;

	private static final int MAX_RETRIES = 7;
	public static GetCharListTask instance;
	public WebAccount account;
	public AppEngineClient client;
	public PlayerModel model;
	public SetLoadingMessageSignal setLoadingMessage;
	public CharListDataSignal charListData;
	public SecurityQuestionsModel securityQuestionsModel;
	private Object requestData;
	private Timer retryTimer;
	private int numRetries = 0;
	private boolean fromMigration = false;

	public GetCharListTask() {
		super();
	}

	public static GetCharListTask getInstance() {
		if (instance == null) {
			instance = new GetCharListTask();
		}
		return instance;
	}

	protected void startTask() {
		this.requestData = this.makeRequestData();
		this.sendRequest();
		Parameters.sendLogin = false;
	}

	private void sendRequest() {
		//this.client.complete.addOnce(new SignalConsumer<>(this::onComplete));

		this.client.sendRequest("/char/list", this.requestData);
	}

	private void onComplete(boolean param1, String param2) {
		if (param1) {
			this.onListComplete(param2);
		} else {
			this.onTextError(param2);
		}
	}

	public Object makeRequestData() {
		/*var loc1:Object = {};
		loc1.game_net_user_id = this.account.gameNetworkUserId();
		loc1.game_net = this.account.gameNetwork();
		loc1.play_platform = this.account.playPlatform();
		loc1.do_login = Parameters.sendLogin;
		MoreObjectUtil.addToObject(loc1, this.account.getCredentials());
		return loc1;*/
		return null;
	}

	private void onListComplete(String param1) {
		double loc3 = 0;
		XML loc2 = new XML(param1);
		if (loc2.hasOwnProperty("MigrateStatus")) {
			loc3 = loc2.getDoubleValue("MigrateStatus");
			if (loc3 == 5) {
				this.sendRequest();
			}
		} else {
			if (loc2.hasOwnProperty("Account")) {
				if (this.account instanceof WebAccount) {
					this.account.userDisplayName = loc2.child("Account").getValue("Name");
					this.account.paymentProvider = loc2.child("Account").getValue("PaymentProvider");
					if (loc2.child("Account").hasOwnProperty("PaymentData")) {
						this.account.paymentData = loc2.child("Account").getValue("PaymentData");
					}
				}
				if (loc2.children("Account").get(0).hasOwnProperty("SecurityQuestions")) {
					this.securityQuestionsModel.showSecurityQuestionsOnStartup = loc2.child("Account")
							.child("SecurityQuestions").getValue("ShowSecurityQuestionsDialog").equals("1");
					this.securityQuestionsModel.clearQuestionsList();
					for (XML loc5 : loc2.child("Account").child("SecurityQuestions").child("SecurityQuestionsKeys")
							.children("SecurityQuestionsKey")) {
						this.securityQuestionsModel.addSecurityQuestion(loc5.toString());
					}
				}
			}
			this.charListData.dispatch(param1);
			completeTask(true);
		}
	}

	private void onTextError(String param1) {
		this.setLoadingMessage.dispatch("error.loadError");
		if (param1.equals("Account credentials not valid")) {
			if (this.fromMigration) {
				System.err.println("Wrong email");
			}
		} else if (param1.equals("Account is under maintenance")) {
			this.setLoadingMessage.dispatch("This account has been banned");
			System.err.println("Account has been banned");
		} else {

		}

		System.err.println("Error : " + param1);

	}

}
