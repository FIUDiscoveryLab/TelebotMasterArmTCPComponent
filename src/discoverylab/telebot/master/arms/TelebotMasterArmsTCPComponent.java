package discoverylab.telebot.master.arms;

import static discoverylab.util.logging.LogUtils.*;
import discoverylab.telebot.master.arms.configurations.MasterArmsConfig;
import discoverylab.telebot.master.arms.mapper.ServoDataMapper;
import discoverylab.telebot.master.arms.model.ServoDataModel;

import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.publication.DataWriterImpl;

import TelebotDDSCore.DDSCommunicator;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArmsDataWriter;
import discoverylab.telebot.master.arms.model.YEIDataModel;
import discoverylab.telebot.master.arms.parser.ServoDataParser;
import discoverylab.telebot.master.arms.parser.YEIDataParser;
import discoverylab.telebot.master.arms.synchronization.YEIDataSynchronizer;
import discoverylab.telebot.master.core.component.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;

public class TelebotMasterArmsTCPComponent extends CoreMasterTCPComponent implements CoreServerSocket.SocketEventListener{
	
	public static String TAG = makeLogTag(TelebotMasterArmsTCPComponent.class);
	
	private CallbackInterface callbackInterface;
	private YEIDataParser parser;
	private YEIDataSynchronizer synchronizer;
	private ServoDataMapper mapper;
	
	private long defaultSpeed = 100;
	
	private DDSCommunicator communicator;
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPComponent(int portNumber) {
		super(portNumber);
		parser = new YEIDataParser();
		synchronizer = new YEIDataSynchronizer();
		mapper = new ServoDataMapper();
	}
	
	/**
	 * Cast the Writer to our Arms DataWriter
	 * This allows us to publish the appropriate Topic data
	 */
	public void initiateDataWriter(){
		writer = (TMasterToArmsDataWriter) getDataWriter();
	}
	
	boolean isSynchronized = false;
	int timer = 1000000;
	int count = 0;
	
	@Override
	public void callback(String data) {
		
		LOGI(TAG, "DATA: " + data );
		YEIDataModel yeiDataInstance = (YEIDataModel) parser.parse(data);
		
		//NOTE: setDataWriter should be called from the Driver class///
		
		if(isSynchronized)
		{
			if(count < timer)
			{
				count = synchronizer.synchronize(yeiDataInstance, count);
			}
			else
			{
				isSynchronized = true;
			}
		}
		
		String jointType = yeiDataInstance.getJointType();
		long x = yeiDataInstance.getX();
		long y = yeiDataInstance.getY();
		long z = yeiDataInstance.getZ();
		long sensorMaxX = yeiDataInstance.getMaxX();
		long sensorMinX = yeiDataInstance.getMinX();
		long sensorMaxY = yeiDataInstance.getMaxY();
		long sensorMinY = yeiDataInstance.getMinY();
		long sensorMaxZ = yeiDataInstance.getMaxZ();
		long sensorMinZ = yeiDataInstance.getMinZ();
		long positionX, positionY, positionZ;
		
		if(jointType.equals("head"))
		{
			positionX = mapper.map(
					x, 
					sensorMaxX, 
					sensorMinX, 
					MasterArmsConfig.HEAD_YAW_MAX, 
					MasterArmsConfig.HEAD_YAW_MIN
					);
			
			positionY = mapper.map(
					y, 
					sensorMaxY, 
					sensorMinY, 
					MasterArmsConfig.HEAD_PITCH_MAX, 
					MasterArmsConfig.HEAD_PITCH_MIN
					);
			
			// WRITE X
			instance.servoId = 10;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Y
			instance.servoId = 11;
			instance.servoPositon = (int)positionY;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("left_shoulder"))
		{
			positionX = mapper.map(
					x, 
					sensorMaxX, 
					sensorMinX, 
					MasterArmsConfig.SHOULDER_LEFT_X_MAX, 
					MasterArmsConfig.SHOULDER_LEFT_X_MIN
					);
			
			positionY = mapper.map(
					y, 
					sensorMaxY, 
					sensorMinY, 
					MasterArmsConfig.SHOULDER_LEFT_Y_MAX, 
					MasterArmsConfig.SHOULDER_LEFT_Y_MIN
					);
			
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.SHOULDER_LEFT_Z_MAX, 
					MasterArmsConfig.SHOULDER_LEFT_Z_MIN
					);
			
			// WRITE X
			instance.servoId = 20;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Y
			instance.servoId = 21;
			instance.servoPositon = (int)positionY;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Z
			instance.servoId = 22;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("left_elbow"))
		{
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.ELBOW_LEFT_Z_MAX, 
					MasterArmsConfig.ELBOW_LEFT_Z_MIN
					);
			
			// WRITE Z
			instance.servoId = 23;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("left_wrist"))
		{
			positionX = mapper.map(
					x, 
					sensorMaxX, 
					sensorMinX, 
					MasterArmsConfig.WRIST_LEFT_X_MAX, 
					MasterArmsConfig.WRIST_LEFT_X_MIN
					);
			
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.WRIST_LEFT_Z_MAX, 
					MasterArmsConfig.WRIST_LEFT_Z_MIN
					);
			
			// WRITE X
			instance.servoId = 24;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Z
			instance.servoId = 25;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("right_shoulder"))
		{
			positionX = mapper.map(
					x, 
					sensorMaxX, 
					sensorMinX, 
					MasterArmsConfig.SHOULDER_RIGHT_X_MAX, 
					MasterArmsConfig.SHOULDER_RIGHT_X_MIN
					);
			
			positionY = mapper.map(
					y, 
					sensorMaxY, 
					sensorMinY, 
					MasterArmsConfig.SHOULDER_RIGHT_Y_MAX, 
					MasterArmsConfig.SHOULDER_RIGHT_Y_MIN
					);
			
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.SHOULDER_RIGHT_Z_MAX, 
					MasterArmsConfig.SHOULDER_RIGHT_Z_MIN
					);
			
			// WRITE X
			instance.servoId = 30;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Y
			instance.servoId = 31;
			instance.servoPositon = (int)positionY;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Z
			instance.servoId = 32;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("right_elbow"))
		{
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.ELBOW_RIGHT_Z_MAX, 
					MasterArmsConfig.ELBOW_RIGHT_Z_MIN
					);
			
			// WRITE Z
			instance.servoId = 33;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		else if(jointType.equals("right_wrist"))
		{
			positionX = mapper.map(
					x, 
					sensorMaxX, 
					sensorMinX, 
					MasterArmsConfig.WRIST_RIGHT_X_MAX, 
					MasterArmsConfig.WRIST_RIGHT_X_MIN);
			
			positionZ = mapper.map(
					z, 
					sensorMaxZ, 
					sensorMinZ, 
					MasterArmsConfig.WRIST_RIGHT_Z_MAX, 
					MasterArmsConfig.WRIST_RIGHT_Z_MIN);
			
			// WRITE X
			instance.servoId = 34;
			instance.servoPositon = (int)positionX;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
			
			// WRITE Z
			instance.servoId = 35;
			instance.servoPositon = (int)positionZ;
			instance.servoSpeed = (int)defaultSpeed;
			writer.write_untyped(instance, instance_handle);
		}
		callbackInterface.callback(yeiDataInstance);

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
