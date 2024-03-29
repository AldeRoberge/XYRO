package rotmg.messaging;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import alde.flash.utils.consumer.MessageConsumer;
import rotmg.messaging.incoming.IncomingMessage;

public class QuestFetchResponse extends IncomingMessage {

	public QuestData[] quests;

	public QuestFetchResponse(int param1, MessageConsumer param2) {
		super(param1, param2);
		this.quests = new QuestData[0];
	}

	public void parseFromInput(DataInput param1) throws IOException {
		this.quests = new QuestData[param1.readShort()];

		for (int i = 0; i < quests.length; i++) {
			this.quests[i] = new QuestData();
			this.quests[i].parseFromInput(param1);
		}
	}

	@Override
	public void writeToOutput(DataOutput out) throws IOException {
		for (QuestData q : quests) {
			q.writeToOutput(out);
		}
	}
}
