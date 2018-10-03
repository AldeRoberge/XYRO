package rotmg.messaging.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import alde.flash.utils.consumer.MessageConsumer;
import rotmg.messaging.incoming.IncomingMessage;

public class PetYard extends IncomingMessage {

	public int type;

	public PetYard(int param1, MessageConsumer param2) {
		super(param1, param2);
	}

	public void parseFromInput(DataInput in) throws IOException {
		this.type = in.readInt();
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		out.writeInt(type);
	}

}
