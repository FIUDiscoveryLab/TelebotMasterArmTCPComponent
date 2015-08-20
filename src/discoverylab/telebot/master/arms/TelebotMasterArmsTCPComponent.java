package discoverylab.telebot.master.arms;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.configurations.MasterArmsConfig;
import discoverylab.telebot.master.arms.configurations.SensorConfig;
import discoverylab.telebot.master.arms.mapper.ServoDataMapper;
//import discoverylab.telebot.master.arms.model.ServoDataModel;

import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.publication.DataWriterImpl;

import TelebotDDSCore.DDSCommunicator;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArmsDataWriter;
import discoverylab.telebot.master.arms.model.YEIDataModel;
//import discoverylab.telebot.master.arms.parser.ServoDataParser;
import discoverylab.telebot.master.arms.parser.YEIDataParser;
//import discoverylab.telebot.master.arms.synchronization.YEIDataSynchronizer;
import discoverylab.telebot.master.core.component.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;


public class TelebotMasterArmsTCPComponent extends CoreMasterTCPComponent implements CoreServerSocket.SocketEventListener{
	
	public static String TAG = makeLogTag(TelebotMasterArmsTCPComponent.class);
	
	private YEIDataParser parser;
	private ServoDataMapper mapper;
	
	private long defaultSpeed = 100;
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPComponent(int portNumber) {
		super(portNumber);
		parser = new YEIDataParser();
		mapper = new ServoDataMapper();
	}
	
	/**
	 * Cast the Writer to our Arms DataWriter
	 * This allows us to publish the appropriate Topic data
	 */
	public void initiateDataWriter(){
		writer = (TMasterToArmsDataWriter) getDataWriter();
	}
	
	
	@Override
	public void callback(String data) {
		
		LOGI(TAG, "DATA: " + data );
		YEIDataModel yeiDataInstance = (YEIDataModel) parser.parse(data);
		
		String jointType = yeiDataInstance.getJointType();
		long x = yeiDataInstance.getX();
		long y = yeiDataInstance.getY();
		long z = yeiDataInstance.getZ();
		long positionX, positionY, positionZ;
		
		if(jointType.equals("head"))
		{
			positionX = mapper.map(
					x, 
					SensorConfig.HEAD_X_MAX, 
					SensorConfig.HEAD_X_MIN, 
					MasterArmsConfig.HEAD_PITCH_MAX, 
					MasterArmsConfig.HEAD_PITCH_MIN
					);
			
			positionY = mapper.map(
					y, 
					SensorConfig.HEAD_Y_MAX, 
					SensorConfig.HEAD_Y_MIN, 
					MasterArmsConfig.HEAD_YAW_MAX, 
					MasterArmsConfig.HEAD_YAW_MIN
					);
			
			// WRITE X
			instance.servoId = 10;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			// WRITE Y
			instance.servoId = 11;
			instance.servoPositon = (int)positionY;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
		}
		else if(jointType.equals("left_shoulder"))
		{
			positionX = mapper.map(
					y, 
					SensorConfig.SHOULDER_LEFT_Y_MAX, 
					SensorConfig.SHOULDER_LEFT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_LEFT_MAX, 
					MasterArmsConfig.ARM_ROLL_LEFT_MIN
					);
			
			positionY = mapper.map(
					x, 
					SensorConfig.SHOULDER_LEFT_X_MAX, 
					SensorConfig.SHOULDER_LEFT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_LEFT_MAX, 
					MasterArmsConfig.ARM_PITCH_LEFT_MIN
					);
			
			// WRITE X
//			instance.servoId = 20;
//			instance.servoPositon = (int)positionY;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
			
//			// WRITE Y
//			instance.servoId = 21; 
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);

		}
		else if(jointType.equals("left_elbow"))
		{
			positionX = mapper.map(
					y, 
					SensorConfig.ELBOW_LEFT_Y_MAX, 
					SensorConfig.ELBOW_LEFT_Y_MIN, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_LEFT_MIN
					);
			
			positionZ = mapper.map(
					x, 
					SensorConfig.ELBOW_LEFT_X_MAX, 
					SensorConfig.ELBOW_LEFT_X_MIN, 
					MasterArmsConfig.ARM_YAW_LEFT_MAX, 
					MasterArmsConfig.ARM_YAW_LEFT_MIN
					);
			
			// WRITE Z
//			instance.servoId = 22;
//			instance.servoPositon = (int)positionZ;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
//			
//			instance.servoId = 23;
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
		}
		else if(jointType.equals("left_wrist"))
		{
			
			positionX = mapper.map(
					-z, //x
					SensorConfig.WRIST_LEFT_Y_MAX, 
					SensorConfig.WRIST_LEFT_Y_MIN, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MAX, 
					MasterArmsConfig.WRIST_ROLL_LEFT_MIN
					);
			
			positionZ = mapper.map(
					x, //100-y
					SensorConfig.WRIST_LEFT_X_MAX, 
					SensorConfig.WRIST_LEFT_X_MIN, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MAX, 
					MasterArmsConfig.FOREARM_YAW_LEFT_MIN
					);
			
			// WRITE X
//			instance.servoId = 24;
//			instance.servoPositon = (int)positionZ;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
			
//			// WRITE Z
//			instance.servoId = 25;
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
		}
		else if(jointType.equals("right_shoulder"))
		{
			positionX = mapper.map(
					y, 
					SensorConfig.SHOULDER_RIGHT_Y_MAX, 
					SensorConfig.SHOULDER_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ARM_ROLL_RIGHT_MIN
					);
			
			positionY = mapper.map(
					180 - x, 
					SensorConfig.SHOULDER_RIGHT_X_MAX, 
					SensorConfig.SHOULDER_RIGHT_X_MIN, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MAX, 
					MasterArmsConfig.ARM_PITCH_RIGHT_MIN
					);
			
			// WRITE X
//			instance.servoId = 30;
//			instance.servoPositon = (int)positionY;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write_untyped(instance, instance_handle);
//			
//			// WRITE Y
//			instance.servoId = 31;
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write_untyped(instance, instance_handle);
			
		}
		else if(jointType.equals("right_elbow"))
		{
			positionX = mapper.map(
					-x, //50 - x
					SensorConfig.ELBOW_RIGHT_X_MAX, 
					SensorConfig.ELBOW_RIGHT_X_MIN, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MAX, 
					MasterArmsConfig.ELBOW_ROLL_RIGHT_MIN
					);
			
			positionZ = mapper.map(
					y, //130 - y
					SensorConfig.ELBOW_RIGHT_Y_MAX, 
					SensorConfig.ELBOW_RIGHT_Y_MIN, 
					MasterArmsConfig.ARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.ARM_YAW_RIGHT_MIN
					);
			
			//WRITE X
//			instance.servoId = 32;
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write_untyped(instance, instance_handle);
//			
////			// WRITE Z
//			instance.servoId = 33;
//			instance.servoPositon = (int)positionZ;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("right_wrist"))
		{
			positionX = mapper.map(
					-z, 
					SensorConfig.WRIST_RIGHT_X_MAX, 
					SensorConfig.WRIST_RIGHT_X_MIN, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MAX, 
					MasterArmsConfig.WRIST_ROLL_RIGHT_MIN);
			
			positionZ = mapper.map(
					-x, 
					SensorConfig.WRIST_RIGHT_Y_MAX, 
					SensorConfig.WRIST_RIGHT_Y_MIN, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MAX, 
					MasterArmsConfig.FOREARM_YAW_RIGHT_MIN);

//			// WRITE X
//			instance.servoId = 34;
//			instance.servoPositon = (int)positionZ;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
//		
			// WRITE Z
//			instance.servoId = 35;
//			instance.servoPositon = (int)positionX;
//			instance.servoSpeed = (int)defaultSpeed;
//			writer.write(instance, instance_handle);
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
			
			instance.servoId = 10;
			instance.servoPositon = (int)MasterArmsConfig.HEAD_PITCH_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 11;
			instance.servoPositon = (int)MasterArmsConfig.HEAD_YAW_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 20;
			instance.servoPositon = (int)MasterArmsConfig.ARM_PITCH_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 21;
			instance.servoPositon = (int)MasterArmsConfig.ARM_ROLL_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 22;
			instance.servoPositon = (int)MasterArmsConfig.ARM_YAW_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 23;
			instance.servoPositon = (int)MasterArmsConfig.ELBOW_ROLL_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 24;
			instance.servoPositon = (int)MasterArmsConfig.FOREARM_YAW_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 25;
			instance.servoPositon = (int)MasterArmsConfig.WRIST_ROLL_LEFT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 30;
			instance.servoPositon = (int)MasterArmsConfig.ARM_PITCH_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 31;
			instance.servoPositon = (int)MasterArmsConfig.ARM_ROLL_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 32;
			instance.servoPositon = (int)MasterArmsConfig.ARM_YAW_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 33;
			instance.servoPositon = (int)MasterArmsConfig.ELBOW_ROLL_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 34;
			instance.servoPositon = (int)MasterArmsConfig.FOREARM_YAW_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 35;
			instance.servoPositon = (int)MasterArmsConfig.WRIST_ROLL_RIGHT_REST;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write(instance, instance_handle);		
		}

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
