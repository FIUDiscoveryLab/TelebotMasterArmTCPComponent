package discoverylab.telebot.master.arms.mapper;

import static discoverylab.util.logging.LogUtils.*;

public class ServoDataMapper extends Mapper
{
	public static String TAG = makeLogTag(ServoDataMapper.class);
	
	@Override
	public int constrain(int val, int servo_max, int servo_min)
	{
		int pos = val;

		if(val <= servo_min)
		{
			pos = servo_min;
		}
		else if(val >= servo_max)
		{
			pos = servo_max;
		}
		
		return pos;
	}
	
	
	@Override
	public int map(int val, int sensor_max, int sensor_min, int servo_max, int servo_min)
	{	
        return (val - sensor_min) * (servo_max - servo_min) / (sensor_max - sensor_min) + servo_min;
	}
	
	
	public int process(int val, int sensor_max, int sensor_min, int servo_max, int servo_min)
	{
		int mapped = map(val, sensor_max, sensor_min, servo_max, servo_min); 
		
		return constrain(mapped, servo_max, servo_min);
	}
	
}
