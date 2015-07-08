package src.discoverylab.telebot.master.arms.mapper;

public class ServoDataMapper extends Mapper{
	
	@Override
	public long constrain(long val, long servo_max, long servo_min)
	{
		if(val <= servo_min)
		{
			val = servo_min;
		}
		else if(val >= servo_max)
		{
			val = servo_max;
		}
		
		return val;
	}
	
	@Override
	public long map(long val, long sensor_max, long sensor_min, long servo_max, long servo_min)
	{
        long mapped = (val - sensor_min) * (servo_max - servo_min) / (sensor_max - sensor_min) + servo_min;

        mapped =  constrain(mapped, servo_max, servo_min);
        
        return mapped;
	}

}
