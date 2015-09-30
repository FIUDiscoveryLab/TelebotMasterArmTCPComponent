package discoverylab.telebot.master.arms.mapper;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPComponent;
import discoverylab.telebot.master.arms.model.YEIDataModel;

public class ServoDataMapper extends Mapper{
	public static String TAG = makeLogTag(ServoDataMapper.class);
	@Override
	public long constrain(long val, long servo_max, long servo_min)
	{
		long ret = val;

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
	public long map(long val, long sensor_max, long sensor_min, long servo_max, long servo_min)
	{
        long mapped = (val - sensor_min) * (servo_max - servo_min) / (sensor_max - sensor_min) + servo_min;
        
        long constrained =  constrain(mapped, servo_max, servo_min);
        
        return constrained;
	}
	
}
