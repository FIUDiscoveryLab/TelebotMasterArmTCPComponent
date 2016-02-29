package discoverylab.telebot.master.arms.mapper;

public abstract class Mapper {
	
	public abstract int constrain(int val, int servo_max, int servo_min);
	
	public abstract int map(int val, int sensor_max, int sensor_min, int servo_max, int servo_min);
	
	public abstract int map(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest);
	
	public abstract int map_1(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest);
	
	public abstract int map_2(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest);
	
}
