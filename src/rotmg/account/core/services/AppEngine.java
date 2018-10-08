package rotmg.account.core.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import rotmg.account.core.Account;
import rotmg.appengine.SavedCharactersList;

public class AppEngine {

	public static final String URL = "http://www.realmofthemadgod.appspot.com/";
	public static final String CHAR_LIST = "char/list";

	public static final String getCharListAsString(Account account) {
		return getWebsite(getCharListURL(account));
	}

	public static final String getCharListURL(Account account) {
		return URL + CHAR_LIST + "?guid=" + account.getUserId() + "&password=" + account.getPassword();
	}

	public static final String getWebsite(String url) {

		StringBuilder input = new StringBuilder();

		try {
			URL websiteURL = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(websiteURL.openStream()));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				input.append(inputLine);
			}

			in.close();

		} catch (IOException e) {
			System.err.println("Error reading website : " + url);
			e.printStackTrace();
		}

		return input.toString();

	}

}
