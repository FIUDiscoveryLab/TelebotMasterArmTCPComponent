package discoverylab.telebot.master.arms.parser;

import java.util.StringTokenizer;

import discoverylab.telebot.master.arms.model.YEIDataModel;
import discoverylab.telebot.master.core.parser.CoreDataParser;

public class YEIDataParser extends CoreDataParser{

	@Override
	public Object parse(String str) {
		StringTokenizer tokenizer = parseUsingTokenizer(str);
		YEIDataModel instance = new YEIDataModel();
		instance.setJointType(tokenizer.nextToken());
		instance.setX(Long.parseLong(tokenizer.nextToken()));
		instance.setY(Long.parseLong(tokenizer.nextToken()));
		instance.setZ(Long.parseLong(tokenizer.nextToken()));
		return instance;
	}
}
