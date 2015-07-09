package discoverylab.telebot.master.arms.mapper;

public abstract class Mapper {
	
	public abstract long constrain(long val, long servo_max, long servo_min);
	
	public abstract long map(long val, long sensor_max, long sensor_min, long servo_max, long servo_min);

}
