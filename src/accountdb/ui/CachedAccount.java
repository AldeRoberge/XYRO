package accountdb.ui;

import java.util.ArrayList;
import java.util.List;

public class CachedAccount {

	String email;
	String password;
	String charList;

	List<Integer> items = new ArrayList<Integer>();

	public CachedAccount(String email, String password, String charList) {
		this.email = email;
		this.password = password;
		this.charList = charList;
	}

	public void addItem(int item) {
		items.add(item);
	}

}
