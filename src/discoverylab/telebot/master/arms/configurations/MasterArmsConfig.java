package discoverylab.telebot.master.arms.configurations;

public class MasterArmsConfig {
	
	public static final int HEAD_PITCH_MAX = 2300;
	public static final int HEAD_PITCH_MIN = 1700;
	public static final int HEAD_YAW_MAX = 1500;
	public static final int HEAD_YAW_MIN = 1500;
	
	public static final int SHOULDER_LEFT_X_MAX = 3600; //pitch
	public static final int SHOULDER_LEFT_X_MIN = 2048;
	public static final int SHOULDER_LEFT_Y_MAX = 3072; //yaw
	public static final int SHOULDER_LEFT_Y_MIN = 1024;
	public static final int SHOULDER_LEFT_Z_MAX = 3600; //roll
	public static final int SHOULDER_LEFT_Z_MIN = 2048;

	public static final int SHOULDER_RIGHT_X_MAX = 2048;
	public static final int SHOULDER_RIGHT_X_MIN = 600;
	public static final int SHOULDER_RIGHT_Y_MAX = 3072;
	public static final int SHOULDER_RIGHT_Y_MIN = 1024;
	public static final int SHOULDER_RIGHT_Z_MAX = 2048;
	public static final int SHOULDER_RIGHT_Z_MIN = 600;
	
	public static final int ELBOW_LEFT_Z_MAX = 3072; //roll
	public static final int ELBOW_LEFT_Z_MIN = 2048;
	
	public static final int ELBOW_RIGHT_Z_MAX = 2048;
	public static final int ELBOW_RIGHT_Z_MIN = 1024;
	
	public static final int WRIST_LEFT_X_MAX = 3072; 
	public static final int WRIST_LEFT_X_MIN = 1024;
	public static final int WRIST_LEFT_Z_MAX = 3072; //roll
	public static final int WRIST_LEFT_Z_MIN = 1024;
	
	public static final int WRIST_RIGHT_X_MAX = 3072;
	public static final int WRIST_RIGHT_X_MIN = 1042;
	public static final int WRIST_RIGHT_Z_MAX = 3072;
	public static final int WRIST_RIGHT_Z_MIN = 1024;
}