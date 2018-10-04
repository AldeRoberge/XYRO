package rotmg.xyro;

import java.util.List;

import alde.flash.utils.Vector;
import alde.flash.utils.XML;
import rotmg.core.model.PlayerModel;
import rotmg.net.LatLong;
import rotmg.net.Server;
import rotmg.parameters.Parameters;

public class Servers {

	private static final Vector<Server> servers = new Vector<Server>(0);
	public PlayerModel model = PlayerModel.getInstance();

	public static Servers instance;

	public static Servers getInstance() {
		if (instance == null) {
			instance = new Servers();
		}
		return instance;
	}

	public Vector<Server> getServers() {
		return servers;
	}

	private Vector<Server> makeListOfServers(XML data) {
		List<XML> loc1 = data.child("Servers").children("Server");
		Vector<Server> loc2 = new Vector<Server>(0);
		for (XML loc3 : loc1) {
			loc2.add(this.makeServer(loc3));
		}
		return loc2;
	}

	private Server makeServer(XML param1) {
		return new Server().setName(param1.getValue("Name")).setAddress(param1.getValue("DNS")).setPort(Parameters.PORT).setLatLong(param1.getDoubleValue("Lat"), param1.getDoubleValue("Long"))
				.setUsage(param1.getDoubleValue("Usage")).setIsAdminOnly(param1.hasOwnProperty("AdminOnly"));
	}

	public Server getBestServer() {
		int loc7 = 0;
		double loc8 = 0;
		boolean loc1 = this.model.isAdmin();
		LatLong loc2 = this.model.getMyPos();
		Server loc3 = null;
		double loc4 = Double.MAX_VALUE;
		int loc5 = Integer.MAX_VALUE;
		for (Server loc6 : servers) {
			if (!(loc6.isFull() && !loc1)) {
				if (loc6.name.equals(Parameters.data.preferredServer)) {
					return loc6;
				}
				loc7 = loc6.priority();
				loc8 = LatLong.distance(loc2, loc6.latLong);
				if (loc7 < loc5 || loc7 == loc5 && loc8 < loc4) {
					loc3 = loc6;
					loc4 = loc8;
					loc5 = loc7;
					Parameters.data.bestServer = loc3.name;
					Parameters.save();
				}
			}
		}
		return loc3;
	}

	public String getServerNameByAddress(String param1) {
		for (Server loc2 : servers) {
			if (loc2.address.equals(param1)) {
				return loc2.name;
			}
		}
		return "";
	}

}
