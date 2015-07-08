package src.discoverylab.telebot.master.arms.parser;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ServoDataParser extends CoreDataParser {

	@Override
	public Object parse(String filename)
	{
		try{
			FileReader readFile = new FileReader(filename);
			BufferedReader input = new BufferedReader(readFile);
			
			String str;
			
			while(input.readLine() != null)
			{
				if(str != null)
				{
					StringTokenizer tokenizer = parseUsingTokenizer(str);
					ServoDataModel instance = new ServoDataModel();
					instance.setServoID(Integer.parseInt(tokenizer.nextToken()));
					instance.setMax(Long.parseLong(tokenizer.nextToken()));
					instance.setMin(Long.parseLong(tokenizer.nextToken()));
					return instance;
				}
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
