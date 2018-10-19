package accountdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import rotmg.account.core.Account;

/**
 * This talks to a database I have on my system (see AccountDatabaseService)
 * 
 * If you want to use XYRO, you'll have to rewrite or customize this code.
 *
 */
public class AccountDatabaseController {

	public static void main(String[] args) {

		System.out.println("Exporting mules to muledump format : ");

		for (Account a : accounts) {

			//'email': 'pass',

			System.out.println("'" + a.userId + "': '" + a.password + "',");

		}

	}

	private static List<Account> accounts = new ArrayList<Account>();

	static {
		accounts = AccountDatabaseService.getAccounts();
	}

	public static Account getAccount() {
		if (accounts.size() == 0) {
			System.err.println("No more accounts!");
		}

		return accounts.remove(0);
	}

	public static List<Account> getAccounts() {
		return accounts;
	}

	@Test
	public void test() {

	}

}

class AccountDatabaseService {

	private static Connection myConn;

	static {
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/RAL", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static List<Account> getAccounts() {

		List<Account> accounts = new ArrayList<>();

		try {

			PreparedStatement myStmt = myConn.prepareStatement("select * from accounts");
			ResultSet myRs = myStmt.executeQuery();

			while (myRs.next()) {

				String email = myRs.getString("email");
				String password = myRs.getString("password");

				Account newAccount = new Account(email, password);

				if (!accounts.contains(newAccount)) {

					String charList = myRs.getString("answer");

					if (!charList.contains("<AccountId>-1</AccountId>")) {
						accounts.add(newAccount);
					}
				}
			}

			System.out.println("Found " + accounts.size() + " accounts.");

			myStmt.close();
			myRs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return accounts;

	}

}
