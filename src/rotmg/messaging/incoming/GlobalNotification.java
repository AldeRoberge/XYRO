package rotmg.messaging.incoming;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import alde.flash.utils.consumer.MessageConsumer;

public class GlobalNotification extends IncomingMessage {

	public int type;
	public String text;

	public GlobalNotification(int id, MessageConsumer callback) {
		super(id, callback);
	}

	@Override
	public void parseFromInput(DataInput in) throws IOException {
		type = in.readInt();
		text = in.readUTF();
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(type);
		out.writeUTF(text);
	}

}
