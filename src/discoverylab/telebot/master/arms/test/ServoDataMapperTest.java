package discoverylab.telebot.master.arms.test;

import discoverylab.telebot.master.arms.configurations.MasterArmsConfig;
import discoverylab.telebot.master.arms.mapper.ServoDataMapper;
import static discoverylab.util.logging.LogUtils.*;

public class ServoDataMapperTest {
	public static String TAG = makeLogTag("ServoDataMapperTest");
	private ServoDataMapper mapper;
	private ServoDataMapper container;
	
	public ServoDataMapperTest(){
		mapper = new ServoDataMapper();
		container = new ServoDataMapper();
	}
	
	public long testMapper(long val, long sensor_max, long sensor_min, long servo_max, long servo_min)
	{
		long map = mapper.map(val, sensor_max, sensor_min, servo_max, servo_min);
		
		return map;
	}
	
	public static void main(String[] args)
	{
		ServoDataMapperTest test = new ServoDataMapperTest();
		long test_map = test.testMapper(-43, 100, 50, MasterArmsConfig.ELBOW_LEFT_Z_MAX, MasterArmsConfig.ELBOW_LEFT_Z_MIN);
		LOGI(TAG, String.valueOf(test_map));
		test_map = test.testMapper(36, 280, 100, MasterArmsConfig.SHOULDER_LEFT_X_MAX, MasterArmsConfig.SHOULDER_LEFT_X_MIN);
		LOGI(TAG, String.valueOf(test_map));
		test_map = test.testMapper(16, 80, 20, MasterArmsConfig.HEAD_YAW_MAX, MasterArmsConfig.HEAD_YAW_MIN);
		LOGI(TAG, String.valueOf(test_map));
		test_map = test.testMapper(92, 120, 60, MasterArmsConfig.WRIST_RIGHT_X_MAX, MasterArmsConfig.WRIST_RIGHT_X_MIN);
		LOGI(TAG, String.valueOf(test_map));
		test_map = test.testMapper(68, 60, 33, MasterArmsConfig.ELBOW_RIGHT_Z_MAX, MasterArmsConfig.ELBOW_RIGHT_Z_MIN);
		LOGI(TAG, String.valueOf(test_map));
	}
	

}
