package src.discoverylab.telebot.master.arms.parser;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ServoDataParser extends CoreDataParser {

	@Override
	public Object parse(String filename)
	{
		final int number_of_servos = 14;
		
		try{
			FileReader readFile = new FileReader(filename);
			BufferedReader input = new BufferedReader(readFile);
			
			String str;
			int count = 0;
			
			for(int i = 0; i < number_of_servos; i++)
			{
				if(input.readLine() != null)
				{
					if(str != null)
					{
						StringTokenizer tokenizer = parseUsingTokenizer(str);
						ServoDataModel[] servoData = new ServoDataModel[number_of_servos];
						ServoDataModel instance = new ServoDataModel();
						instance.setServoID(Integer.parseInt(tokenizer.nextToken()));
						instance.setMax(Long.parseLong(tokenizer.nextToken()));
						instance.setMin(Long.parseLong(tokenizer.nextToken()));
						servoData[count] = instance;
						count++;
					}
				}
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
