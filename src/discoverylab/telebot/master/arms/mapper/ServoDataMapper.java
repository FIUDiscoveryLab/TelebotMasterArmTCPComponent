package discoverylab.telebot.master.arms.mapper;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPComponent;
import discoverylab.telebot.master.arms.model.YEIDataModel;

public class ServoDataMapper extends Mapper{
	public static String TAG = makeLogTag(ServoDataMapper.class);
	@Override
	public int constrain(int val, int servo_max, int servo_min)
	{
		int ret = val;

		if(val <= servo_min)
		{
			ret = servo_min;
		}
		else if(val >= servo_max)
		{
			ret = servo_max;
		}
		
		return ret;
	}
	
	@Override
	public int map(int val, int sensor_max, int sensor_min, int servo_max, int servo_min)
	{
        int mapped = (val - sensor_min) * (servo_max - servo_min) / (sensor_max - sensor_min) + servo_min;
        
        int constrained =  constrain(mapped, servo_max, servo_min);
        
        return constrained;
	}
	
	@Override
	public int map(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest)
	{
		double mapped = 0;
		
			if( val >= 0 ){
				mapped = (((servo_max - servo_rest)/ sensor_max) * val) + servo_rest;
			}
			else
			{
				mapped = servo_rest - ((servo_rest - servo_min)/sensor_min) * val;
			}
        
		
        int constrained =  constrain((int)mapped, servo_max, servo_min);
        
        return (int)mapped;
	}
	
	public int map_2(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest)
	{
		double mapped = 0;
		
			if( val >= 0 ){
				mapped = servo_rest - (((servo_max - servo_rest)/ sensor_max) * val) ;
			}
			else
			{
				mapped = servo_rest + ((servo_rest - servo_min)/sensor_min) * val;
			}
        
		
        int constrained =  constrain((int)mapped, servo_max, servo_min);
        
        return (int)mapped;
	}
	public int map_1(int val, int sensor_max, int sensor_min, int servo_max, int servo_min, int servo_rest)
	{
		double mapped = 0;
		
		mapped = servo_max - (((servo_max - servo_min)/sensor_max) * val   );
	return (int)mapped;
	}
	
}
