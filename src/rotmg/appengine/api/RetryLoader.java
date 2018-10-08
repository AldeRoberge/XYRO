package rotmg.appengine.api;

import org.osflash.signals.Signal;

import alde.flash.utils.Vector;

public interface RetryLoader {

	Signal complete = new Signal();

	void setMaxRetries(int param1);

	void setDataFormat(String param1);

	void sendRequest(String param1, Vector param2);

	boolean isInProgress();

}
