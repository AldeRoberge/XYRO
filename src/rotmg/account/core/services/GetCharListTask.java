package rotmg.account.core.services;

import alde.flash.utils.XML;
import rotmg.account.core.WebAccount;
import rotmg.account.securityQuestions.data.SecurityQuestionsModel;
import rotmg.appengine.api.AppEngineClient;
import rotmg.core.model.PlayerModel;
import rotmg.parameters.Parameters;
import rotmg.xyro.Servers;

public class GetCharListTask {

	public static GetCharListTask instance;
	public WebAccount account;
	public AppEngineClient client;
	public PlayerModel model;
	public SecurityQuestionsModel securityQuestionsModel;
	private Object requestData;
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
		return null;
	}

	private void onListComplete(String param1) {
		double loc3 = 0;

		System.out.println("Got list answer...");

		XML charList = new XML(param1);

		Servers.getInstance().makeListOfServers(charList);

		if (charList.hasOwnProperty("MigrateStatus")) {
			loc3 = charList.getDoubleValue("MigrateStatus");
			if (loc3 == 5) {
				this.sendRequest();
			}
		} else {
			if (charList.hasOwnProperty("Account")) {
				if (this.account instanceof WebAccount) {
					this.account.userDisplayName = charList.child("Account").getValue("Name");
					this.account.paymentProvider = charList.child("Account").getValue("PaymentProvider");
					if (charList.child("Account").hasOwnProperty("PaymentData")) {
						this.account.paymentData = charList.child("Account").getValue("PaymentData");
					}
				}
				if (charList.children("Account").get(0).hasOwnProperty("SecurityQuestions")) {
					this.securityQuestionsModel.showSecurityQuestionsOnStartup = charList.child("Account").child("SecurityQuestions").getValue("ShowSecurityQuestionsDialog").equals("1");
					this.securityQuestionsModel.clearQuestionsList();
					for (XML loc5 : charList.child("Account").child("SecurityQuestions").child("SecurityQuestionsKeys").children("SecurityQuestionsKey")) {
						this.securityQuestionsModel.addSecurityQuestion(loc5.toString());
					}
				}
			}
		}

	}

	private void onTextError(String param1) {
		if (param1.equals("Account credentials not valid")) {
			if (this.fromMigration) {
				System.err.println("Wrong email");
			}
		} else if (param1.equals("Account is under maintenance")) {
			System.err.println("This account has been banned");
		} else {

		}

		System.err.println("Error : " + param1);

	}

}
