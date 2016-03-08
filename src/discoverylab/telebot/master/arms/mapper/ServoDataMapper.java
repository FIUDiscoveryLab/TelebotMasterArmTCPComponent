package discoverylab.telebot.master.arms.mapper;

import static discoverylab.util.logging.LogUtils.*;

public class ServoDataMapper extends Mapper
{
	public static String TAG = makeLogTag(ServoDataMapper.class);
	
	@Override
	public int constrain(int servo_val, int servo_max, int servo_min)
	{
		int pos = servo_val;

		if(servo_val <= servo_min)
		{
			pos = servo_min;
		}
		else if(servo_val >= servo_max)
		{
			pos = servo_max;
		}
		
		return pos;
	}
	
	
	@Override
	public int map(int sensor_val, int sensor_max, int sensor_min, int servo_max, int servo_min)
	{	
        return (sensor_val - sensor_min) * (servo_max - servo_min) / (sensor_max - sensor_min) + servo_min;
	}
	
	
	public int process(int sensor_val, int sensor_max, int sensor_min, int servo_max, int servo_min)
	{
		int mapped = map(sensor_val, sensor_max, sensor_min, servo_max, servo_min); 
		
		int position = constrain(mapped, servo_max, servo_min);
		
		return position;
	}
	
}
