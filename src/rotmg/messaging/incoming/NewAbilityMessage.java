package rotmg.messaging.incoming;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import alde.flash.utils.consumer.MessageConsumer;

public class NewAbilityMessage extends IncomingMessage {

	private int type;

	public NewAbilityMessage(int id, MessageConsumer callback) {
		super(id, callback);
	}

	@Override
	public void parseFromInput(DataInput in) throws IOException {
		type = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(type);
	}

}
