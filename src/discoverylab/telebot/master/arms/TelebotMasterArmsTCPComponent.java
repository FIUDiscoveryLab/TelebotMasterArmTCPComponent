package discoverylab.telebot.master.arms;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.configurations.MasterArmsConfig;
import discoverylab.telebot.master.arms.configurations.SensorConfig;
import discoverylab.telebot.master.arms.mapper.ServoDataMapper;
import com.rti.dds.infrastructure.InstanceHandle_t;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArmsDataWriter;
import discoverylab.telebot.master.arms.model.YEIDataModel;
import discoverylab.telebot.master.arms.parser.YEIDataParser;
import discoverylab.telebot.master.core.component.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;


public class TelebotMasterArmsTCPComponent extends CoreMasterTCPComponent implements CoreServerSocket.SocketEventListener{
	
	public static String TAG = makeLogTag(TelebotMasterArmsTCPComponent.class);
	
	private YEIDataParser parser;
	private ServoDataMapper mapper;
	
	private int defaultSpeed = 100;
	
	private int[] jointPositions;
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPComponent(int portNumber) 
	{
		super(portNumber);
		parser = new YEIDataParser();
		mapper = new ServoDataMapper();
		
		jointPositions = new int[14];
		
		for(int i = 0; i < jointPositions.length; i++)
		{
			jointPositions[i] = -1;
		}
	}
	
	/**
	 * Cast the Writer to our Arms DataWriter
	 * This allows us to publish the appropriate Topic data
	 */
	public void initiateDataWriter(){
		writer = (TMasterToArmsDataWriter) getDataWriter();
	}

	public void writeServoData(int servoID, int servoPosition, int servoSpeed)
	{
		instance.servoId = servoID;
		instance.servoPositon = servoPosition;
		instance.servoSpeed = servoSpeed;
		writer.write(instance, instance_handle);
		LOGI(TAG, instance.servoId + " " + instance.servoPositon + " " + instance.servoSpeed);
	}
	
	public void generatePositions(String jointType, int x, int y, int z)
	{
		int servoOnePosition, servoTwoPosition;
		
		if(jointType.equals("head"))
		{
			servoOnePosition = mapper.map(
					x, 
					SensorConfig.HEAD_X_MAX, 
					SensorConfig.HEAD_X_MIN, 
					MasterArmsConfig.HEAD_PITCH_MAX, 
					MasterArmsConfig.HEAD_PITCH_MIN
					);
			
			servoTwoPosition = mapper.map(
					y, 
					SensorConfig.HEAD_Y_MAX, 
					SensorConfig.HEAD_Y_MIN, 
					MasterArmsConfig.HEAD_YAW_MAX, 
					MasterArmsConfig.HEAD_YAW_MIN
					);
			
			if(servoOnePosition != jointPositions[0])
			{
				writeServoData(10, servoOnePosition, defaultSpeed);
				jointPositions[0] = servoOnePosition;
			}

			if(servoTwoPosition != jointPositions[1])
			{
				writeServoData(11, servoTwoPosition, defaultSpeed);
				jointPositions[1] = servoTwoPosition;
			}
		}
		else if(jointType.equals("left_shoulder"))
		{
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.SHOULDER_LEFT_Y_MAX, 
					SensorConfig.SHOULDER_LEFT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_LEFT_MAX, 
					MasterArmsConfig.ARM_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, 
					SensorConfig.SHOULDER_LEFT_X_MAX, 
					SensorConfig.SHOULDER_LEFT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_LEFT_MAX, 
					MasterArmsConfig.ARM_PITCH_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[2])
			{
				writeServoData(20, servoTwoPosition, defaultSpeed);
				jointPositions[2] = servoTwoPosition;
			}
			
			if(servoOnePosition != jointPositions[3])
			{
				writeServoData(21, servoOnePosition, defaultSpeed);
				jointPositions[3] = servoOnePosition;
			}
		}
		else if(jointType.equals("left_elbow"))
		{
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.ELBOW_LEFT_Y_MAX, 
					SensorConfig.ELBOW_LEFT_Y_MIN, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, 
					SensorConfig.ELBOW_LEFT_X_MAX, 
					SensorConfig.ELBOW_LEFT_X_MIN, 
					MasterArmsConfig.ARM_YAW_LEFT_MAX, 
					MasterArmsConfig.ARM_YAW_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[4])
			{
				writeServoData(22, servoTwoPosition, defaultSpeed);
				jointPositions[4] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[5])
			{
				writeServoData(23, servoOnePosition, defaultSpeed);
				jointPositions[5] = servoOnePosition;
			}
		}
		else if(jointType.equals("left_wrist"))
		{
			servoOnePosition = mapper.map( 
					-z, //x
					SensorConfig.WRIST_LEFT_Y_MAX, 
					SensorConfig.WRIST_LEFT_Y_MIN, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MAX, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					x, //100-y
					SensorConfig.WRIST_LEFT_X_MAX, 
					SensorConfig.WRIST_LEFT_X_MIN, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MAX, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MIN
					);
			
			if(servoTwoPosition != jointPositions[6])
			{
				writeServoData(24, servoTwoPosition, defaultSpeed);
				jointPositions[6] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[7])
			{
				writeServoData(25, servoOnePosition, defaultSpeed);
				jointPositions[7] = servoOnePosition;
			}
		}
		else if(jointType.equals("right_shoulder"))
		{
			servoOnePosition = mapper.map( 
					y, 
					SensorConfig.SHOULDER_RIGHT_Y_MAX, 
					SensorConfig.SHOULDER_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MIN
					);
			
			servoTwoPosition = mapper.map(
					120 - x, 
					SensorConfig.SHOULDER_RIGHT_X_MAX, 
					SensorConfig.SHOULDER_RIGHT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MAX, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MIN
					);
			
			if(servoTwoPosition != jointPositions[8])
			{
				writeServoData(30, servoTwoPosition, defaultSpeed);
				jointPositions[8] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[9])
			{
				writeServoData(31, servoOnePosition, defaultSpeed);
				jointPositions[9] = servoOnePosition;
			}			
		}
		else if(jointType.equals("right_elbow"))
		{
			servoOnePosition = mapper.map( 
					-x, //50 - x
					SensorConfig.ELBOW_RIGHT_X_MAX, 
					SensorConfig.ELBOW_RIGHT_X_MIN, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MIN
					);
			
			servoTwoPosition = mapper.map( 
					y, //130 - y
					SensorConfig.ELBOW_RIGHT_Y_MAX, 
					SensorConfig.ELBOW_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.ARM_YAW_RIGHT_MIN
					);
			
			if(servoOnePosition != jointPositions[10])
			{
				writeServoData(32, servoOnePosition, defaultSpeed);
				jointPositions[10] = servoOnePosition;
			}

			if(servoTwoPosition != jointPositions[11])
			{
				writeServoData(33, servoTwoPosition, defaultSpeed);
				jointPositions[11] = servoTwoPosition;
			}
		}
		else if(jointType.equals("right_wrist"))
		{
			servoOnePosition = mapper.map( 
					-z, 
					SensorConfig.WRIST_RIGHT_X_MAX, 
					SensorConfig.WRIST_RIGHT_X_MIN, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MAX, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MIN);
			
			servoTwoPosition = mapper.map( 
					-x, 
					SensorConfig.WRIST_RIGHT_Y_MAX, 
					SensorConfig.WRIST_RIGHT_Y_MIN, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MIN);

			if(servoTwoPosition != jointPositions[12])
			{
				writeServoData(34, servoTwoPosition, defaultSpeed);
				jointPositions[12] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[13])
			{
				writeServoData(35, servoOnePosition, defaultSpeed);
				jointPositions[13] = servoOnePosition;
			}
		}
		else if(jointType.equals("null"))
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			writeServoData(10, MasterArmsConfig.HEAD_PITCH_REST, defaultSpeed);
			
			writeServoData(11, MasterArmsConfig.HEAD_YAW_REST, defaultSpeed);
			
			writeServoData(20, MasterArmsConfig.ARM_PITCH_LEFT_REST, defaultSpeed);

			writeServoData(21, MasterArmsConfig.ARM_ROLL_LEFT_REST, defaultSpeed);
			
			writeServoData(22, MasterArmsConfig.ARM_YAW_LEFT_REST, defaultSpeed);
			
			writeServoData(23, MasterArmsConfig.ELBOW_ROLL_LEFT_REST, defaultSpeed);
			
			writeServoData(24, MasterArmsConfig.FOREARM_YAW_LEFT_REST, defaultSpeed);

			writeServoData(25, MasterArmsConfig.WRIST_ROLL_LEFT_REST, defaultSpeed);

			writeServoData(30, MasterArmsConfig.ARM_PITCH_RIGHT_REST, defaultSpeed);
			
			writeServoData(31, MasterArmsConfig.ARM_ROLL_RIGHT_REST, defaultSpeed);

			writeServoData(32, MasterArmsConfig.ARM_YAW_RIGHT_REST, defaultSpeed);

			writeServoData(33, MasterArmsConfig.ELBOW_ROLL_RIGHT_REST, defaultSpeed);

			writeServoData(34, MasterArmsConfig.FOREARM_YAW_RIGHT_REST, defaultSpeed);

			writeServoData(35, MasterArmsConfig.WRIST_ROLL_RIGHT_REST, defaultSpeed);		
		}


	}
	
	@Override
	public void callback(String data) { //try synchronized
		
//		LOGI(TAG, "DATA: " + data );
		YEIDataModel yeiDataInstance = (YEIDataModel) parser.parse(data);
		
		String jointType = yeiDataInstance.getJointType();
		int x = yeiDataInstance.getX();
		int y = yeiDataInstance.getY();
		int z = yeiDataInstance.getZ();
		
		generatePositions(jointType, x, y, z);
	}

	/**
	 * CallbackInterface
	 * @author Irvin Steve Cardenas
	 * 
	 * Callback interface to retrieve parsed Data Object
	 * Purpose: GUI or other components can implement this to receive a  YEIDataModel object object
	 *
	 */
	public interface CallbackInterface{
		public void callback(YEIDataModel data);
	}
}
