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
import discoverylab.telebot.master.arms.test.MasterGUI;
//import discoverylab.telebot.master.arms.synchronization.YEIDataSynchronizer;
import discoverylab.telebot.master.core.component.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;


public class TelebotMasterArmsTCPComponent extends CoreMasterTCPComponent implements CoreServerSocket.SocketEventListener{
	
	public static String TAG = makeLogTag(TelebotMasterArmsTCPComponent.class);
	
	private YEIDataParser parser;
	private ServoDataMapper mapper;
	
	private int defaultSpeed = 100;
	
	private int[] jointPositions; //Save the previous value of each joint movement
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPComponent(int portNumber) {
		super(portNumber);
		parser = new YEIDataParser();
		mapper = new ServoDataMapper();
		
		jointPositions = new int[14];
		
		for(int i = 0; i < jointPositions.length; i++)
		{
			jointPositions[i] = -1;
		}
		
		/*
		 * Positions:
		 * 0: 
		 * 1: 
		 * 2: 
		 */
	}
	
	/**
	 * Cast the Writer to our Arms DataWriter
	 * This allows us to publish the appropriate Topic data
	 */
	public void initiateDataWriter(){
		writer = (TMasterToArmsDataWriter) getDataWriter();
	}


	@Override
	public synchronized void callback(String data) { //try synchronized
		
		
		LOGI(TAG, "DATA: " + data );
		YEIDataModel yeiDataInstance = (YEIDataModel) parser.parse(data);
		
		String jointType = yeiDataInstance.getJointType();
		int x, y, z;
		
		/*
		 * This variables are used to storage the values of the differents 
		 * angles for each joint
		 */
		int servoOnePosition, servoTwoPosition , servoThreePosition = 0;
		
		if(jointType.equals("head"))
		{
			x = yeiDataInstance.getX();
			y = yeiDataInstance.getY();
			
			//==========Mapping from sensor values to servo positions==========
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
			//==========END of Mapping==========
			
			
			//========= Publishing Values in DDS =========	
			if(servoOnePosition != jointPositions[0])
			{
				instance.servoId = 10;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[0] = servoOnePosition;
			}

			if(servoTwoPosition != jointPositions[1])
			{
				instance.servoId = 11;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = defaultSpeed;
				writer.write(instance, instance_handle);
				jointPositions[1] = servoTwoPosition;
			}
		}
		else if(jointType.equals("left_shoulder"))
		{
			int lateral = 0;
			int front = 0;
			
			int rotation = yeiDataInstance.getX();
			int adduction = yeiDataInstance.getY();
			int flexion = yeiDataInstance.getZ();
			
			/************************************************************************
			 * Updating Sensor values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues(Integer.toString(rotation),MasterGUI.LS,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(adduction),MasterGUI.LS,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.LS,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
					
			
		
//			
			//Inversion of the angle. Using its complement not the angle itself
			flexion = SensorConfig.LEFT_SHOULDER_FLEXION_MAX - flexion;
			
			/*
			 * Because the scale we are using in the shoulder formula is based on 90 degrees anlges
			 * we are scaling the input values to a 90 degrees max
			 */			
			adduction = (int)((double)adduction/SensorConfig.LEFT_SHOULDER_ADDUCTION_MAX * 90);						
			flexion = (int)((double)flexion/SensorConfig.LEFT_SHOULDER_FLEXION_MAX *90);
			
			//Avoiding issues when the angle is negative
			if(flexion <= 0)
				flexion = 0;
			
			
			try{
				
				front = (int)Math.toDegrees(Math.atan(Math.tan(Math.toRadians(adduction)) * Math.tan(Math.toRadians(flexion)))) ;
				lateral = (int)Math.toDegrees(Math.atan(Math.cos(Math.toRadians(front)) * Math.tan(Math.toRadians(flexion)))    );
			}catch(Exception e){
				
			}
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map( //Left upper
					rotation, 
					SensorConfig.LEFT_SHOULDER_ROTATION_MAX, 
					SensorConfig.LEFT_SHOULDER_ROTATION_MIN, 
					MasterArmsConfig.LEFT_UPPER_MAX, 
					MasterArmsConfig.LEFT_UPPER_MIN,
					MasterArmsConfig.LEFT_UPPER_REST
					);
			
			servoTwoPosition = mapper.map( //Left shoulder Lateral
					lateral, 
					SensorConfig.MAX, 
					SensorConfig.MIN, 
					MasterArmsConfig.LEFT_LATERAL_MAX, 
					MasterArmsConfig.LEFT_LATERAL_MIN,
					MasterArmsConfig.LEFT_LATERAL_REST
					);
			
			servoThreePosition = mapper.map( //Left shoulder Front
					front, 
					SensorConfig.MAX, 
					SensorConfig.MIN, 
					MasterArmsConfig.LEFT_FRONT_MAX, 
					MasterArmsConfig.LEFT_FRONT_MIN,
					MasterArmsConfig.LEFT_FRONT_REST
					);
			//==========END of Mapping==========
			
			/************************************************************************
			 * Updating Sensor values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.LF_SERVO, 20, servoThreePosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.LLAT_SERVO, 21, servoTwoPosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.LU_SERVO, 22, servoOnePosition, 0);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========	
			instance.servoId = 22; //Left upper servo
			instance.servoPositon = servoOnePosition;
			instance.servoSpeed = 150;
			writer.write(instance, instance_handle);
			jointPositions[2] = servoOnePosition;
			
			instance.servoId = 21; //Left shoulder lateral servo
			instance.servoPositon = servoTwoPosition;
			instance.servoSpeed = 100;
			writer.write(instance, instance_handle);
			jointPositions[3] = servoTwoPosition;
			
			instance.servoId = 20; //Left shoulder Front servo
			instance.servoPositon = servoThreePosition;
			instance.servoSpeed = 100;
			writer.write(instance, instance_handle);
			jointPositions[4] = servoThreePosition;
		}
		else if(jointType.equals("left_elbow"))
		{
			
			int flexion = yeiDataInstance.getY();
			
			/************************************************************************
			 * Updating values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues("-",MasterGUI.LE,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.LE,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues("-",MasterGUI.LE,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map( //Left Elbow
					flexion, 
					SensorConfig.LEFT_ELBOW_FLEXION_MAX, 
					SensorConfig.LEFT_ELBOW_FLEXION_MIN, 
					MasterArmsConfig.LEFT_ELBOW_MAX, 
					MasterArmsConfig.LEFT_ELBOW_MIN,
					MasterArmsConfig.LEFT_ELBOW_REST
					);
			//==========END of Mapping==========
			
			/************************************************************************
			 * Updating Servo values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.LE_SERVO, 23, servoOnePosition, 0);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========	
			instance.servoId = 23; //elbow servo Servo
			instance.servoPositon = servoOnePosition;
			instance.servoSpeed = 150;
			writer.write(instance, instance_handle);
			jointPositions[5] = servoOnePosition;
			
			
		}
		else if(jointType.equals("left_wrist"))
		{
			int forearm_rotation = yeiDataInstance.getX() ;
			int flexion = yeiDataInstance.getZ() ;
			
			/************************************************************************
			 * Updating Sensor values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues(Integer.toString(forearm_rotation),MasterGUI.LW,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues("-",MasterGUI.LW,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.LW,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map( //Left Lower
					forearm_rotation, 
					SensorConfig.LEFT_FOREARM_ROTATION_MAX, 
					SensorConfig.LEFT_FOREARM_ROTATION_MIN, 
					MasterArmsConfig.LEFT_FOREARM_MAX, 
					MasterArmsConfig.LEFT_FOREARM_MIN,
					MasterArmsConfig.LEFT_FOREARM_REST
					);
			
			servoTwoPosition = mapper.map( //Left Wrist
					flexion , 
					SensorConfig.LEFT_WRIST_FLEXION_MAX, 
					SensorConfig.LEFT_WRIST_FLEXION_MIN, 
					MasterArmsConfig.LEFT_WRIST_MAX, 
					MasterArmsConfig.LEFT_WRIST_MIN,
					MasterArmsConfig.LEFT_WRIST_REST
					
					);
			//==========END of Mapping==========
			
			/************************************************************************
			 * Updating Servo values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.LL_SERVO, 24, servoOnePosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.LW_SERVO, 25, servoTwoPosition, 0);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========	
			instance.servoId = 25;
				instance.servoPositon = servoTwoPosition;
				instance.servoSpeed = 200;
				writer.write(instance, instance_handle);
				jointPositions[6] = servoTwoPosition;
			

			
				instance.servoId = 24;
				instance.servoPositon = servoOnePosition;
				instance.servoSpeed = 200;
				writer.write(instance, instance_handle);
				jointPositions[7] = servoOnePosition;
			
		}
		else if(jointType.equals("right_shoulder"))
		{
			int lateral = 0;
			int front = 0;
			
			int rotation = yeiDataInstance.getX();
			int adduction = yeiDataInstance.getY();
			int flexion = yeiDataInstance.getZ();
			
			/************************************************************************
			 * Updating values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues(Integer.toString(rotation),MasterGUI.RS,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(adduction),MasterGUI.RS,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.RS,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//Inversion of the angle. Using its complement not the angle itself
			flexion = SensorConfig.RIGHT_SHOULDER_FLEXION_MAX - flexion;		
			
			/*
			 * Because the scale we are using in the shoulder formula is based on 90 degrees anlges
			 * we are scaling the input values to a 90 degrees max
			 */			
			adduction = (int)((double)adduction/SensorConfig.RIGHT_SHOULDER_ADDUCTION_MAX * 90); 			
			flexion = (int)((double)flexion/SensorConfig.RIGHT_SHOULDER_FLEXION_MAX *90);
			
			//Avoiding issues when the angle is negative
			if(flexion <= 0)
				flexion = 0;
			
			
			try{
				
				front = (int)Math.toDegrees(Math.atan(Math.tan(Math.toRadians(adduction)) * Math.tan(Math.toRadians(flexion)))) ;
				lateral = (int)Math.toDegrees(Math.atan(Math.cos(Math.toRadians(front)) * Math.tan(Math.toRadians(flexion)))    );
			}catch(Exception e){
				
			}
			
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map_2( //Right shoulder rotation
					rotation, 
					SensorConfig.RIGHT_SHOULDER_ROTATION_MAX, 
					SensorConfig.RIGHT_SHOULDER_ROTATION_MIN, 
					MasterArmsConfig.RIGHT_UPPER_MAX, 
					MasterArmsConfig.RIGHT_UPPER_MIN,
					MasterArmsConfig.RIGHT_UPPER_REST
					);
			
			servoTwoPosition = mapper.map_1( //Right lateral
					lateral, 
					SensorConfig.MAX, 
					SensorConfig.MIN, 
					MasterArmsConfig.RIGHT_LATERAL_MAX, 
					MasterArmsConfig.RIGHT_LATERAL_MIN,
					MasterArmsConfig.RIGHT_LATERAL_REST
					);
			
			servoThreePosition = mapper.map_1( //Right front
					front, 
					SensorConfig.MAX, 
					SensorConfig.MIN, 
					MasterArmsConfig.RIGHT_FRONT_MAX, 
					MasterArmsConfig.RIGHT_FRONT_MIN,
					MasterArmsConfig.RIGHT_FRONT_REST
					);
			//==========END of Mapping==========
			
			
			
			/************************************************************************
			 * Updating Servo values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.RF_SERVO, 30, servoThreePosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.RLAT_SERVO, 31, servoTwoPosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.RU_SERVO, 32, servoOnePosition, 0);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========
			instance.servoId = 32; //Right upper servo
			instance.servoPositon = servoOnePosition;
			instance.servoSpeed = 150;
			writer.write(instance, instance_handle);
			jointPositions[8] = servoOnePosition;
			
			instance.servoId = 31; //Right shoulder lateral
			instance.servoPositon = servoTwoPosition;
			instance.servoSpeed = 100;
			writer.write(instance, instance_handle);
			jointPositions[9] = servoTwoPosition;
			
			instance.servoId = 30; //Right shoulder Front
			instance.servoPositon = servoThreePosition;
			instance.servoSpeed = 100;
			writer.write(instance, instance_handle);
			jointPositions[10] = servoThreePosition;

				
		}
		else if(jointType.equals("right_elbow"))
		{
			int flexion = yeiDataInstance.getY();
			
			/************************************************************************
			 * Updating Sensor values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues("-",MasterGUI.RE,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.RE,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues("-",MasterGUI.RE,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map( //Right elbow servo
					flexion, 
					SensorConfig.RIGHT_ELBOW_FLEXION_MAX, 
					SensorConfig.RIGHT_ELBOW_FLEXION_MIN, 
					MasterArmsConfig.RIGHT_ELBOW_MAX, 
					MasterArmsConfig.RIGHT_ELBOW_MIN,
					MasterArmsConfig.RIGHT_ELBOW_REST
					);
			//==========END of Mapping==========
			
			/************************************************************************
			 * Updating Servos values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.RE_SERVO, 33, servoOnePosition, 0);

			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========
			instance.servoId = 33; //elbow servo
			instance.servoPositon = servoOnePosition;
			instance.servoSpeed = 200;
			writer.write(instance, instance_handle);
			jointPositions[10] = servoOnePosition;
		}
		else if(jointType.equals("right_wrist"))
		{
			int forearm_rotation = yeiDataInstance.getX() ;
			int flexion = yeiDataInstance.getZ() ;
			
			/************************************************************************
			 * Updating values in the GUI
			 ************************************************************************/
			
			MasterGUI.setSensorLabelValues(Integer.toString(forearm_rotation),MasterGUI.RW,MasterGUI.FIRST_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues("-",MasterGUI.RW,MasterGUI.SECOND_VALUE,MasterGUI.CURRENT_VALUE);
			MasterGUI.setSensorLabelValues(Integer.toString(flexion),MasterGUI.RW,MasterGUI.THIRD_VALUE,MasterGUI.CURRENT_VALUE);
			
			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//==========Mapping from sensor values to servo positions==========
			servoOnePosition = mapper.map( //Right lower
					forearm_rotation, 
					SensorConfig.RIGHT_FOREARM_ROTATION_MAX, 
					SensorConfig.RIGHT_FOREARM_ROTATION_MIN, 
					MasterArmsConfig.RIGHT_FOREARM_MAX, 
					MasterArmsConfig.RIGHT_FOREARM_MIN,
					MasterArmsConfig.RIGHT_FOREARM_REST
					);
			
			servoTwoPosition = mapper.map( //Right Wrist
					flexion , 
					SensorConfig.RIGHT_WRIST_FLEXION_MAX, 
					SensorConfig.RIGHT_WRIST_FLEXION_MIN, 
					MasterArmsConfig.RIGHT_WRIST_MAX, 
					MasterArmsConfig.RIGHT_WRIST_MIN,
					MasterArmsConfig.RIGHT_WRIST_REST
					
					);
			//==========END of Mapping==========
			/************************************************************************
			 * Updating Servos values in the GUI
			 ************************************************************************/
			
			MasterGUI.setServosLabelValues(MasterGUI.RW_SERVO, 35, servoTwoPosition, 0);
			MasterGUI.setServosLabelValues(MasterGUI.RL_SERVO, 34, servoOnePosition, 0);

			/************************************************************************
			 * End of the update
			 ************************************************************************/
			
			//========= Publishing Values in DDS =========
			if(servoTwoPosition != jointPositions[12])
			{
				instance.servoId = 35;
				instance.servoPositon = servoTwoPosition; //Right Wrist servo
				instance.servoSpeed = 200;
				writer.write(instance, instance_handle);
				jointPositions[12] = servoTwoPosition;
			}

			if(servoOnePosition != jointPositions[13])
			{
				instance.servoId = 34;
				instance.servoPositon = servoOnePosition;//Right Lower Servo
				instance.servoSpeed = 200;
				writer.write(instance, instance_handle);
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
			
			instance.servoId = 10;
			instance.servoPositon = MasterArmsConfig.HEAD_PITCH_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 11;
			instance.servoPositon = MasterArmsConfig.HEAD_YAW_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 20;
			instance.servoPositon = MasterArmsConfig.ARM_PITCH_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 21;
			instance.servoPositon = MasterArmsConfig.ARM_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 22;
			instance.servoPositon = MasterArmsConfig.ARM_YAW_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 23;
			instance.servoPositon = MasterArmsConfig.ELBOW_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 24;
			instance.servoPositon = MasterArmsConfig.FOREARM_YAW_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 25;
			instance.servoPositon = MasterArmsConfig.WRIST_ROLL_LEFT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 30;
			instance.servoPositon = MasterArmsConfig.ARM_PITCH_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 31;
			instance.servoPositon = MasterArmsConfig.ARM_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 32;
			instance.servoPositon = MasterArmsConfig.ARM_YAW_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 33;
			instance.servoPositon = MasterArmsConfig.ELBOW_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 34;
			instance.servoPositon = MasterArmsConfig.FOREARM_YAW_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
			writer.write(instance, instance_handle);
			
			instance.servoId = 35;
			instance.servoPositon = MasterArmsConfig.WRIST_ROLL_RIGHT_REST;
			instance.servoSpeed = defaultSpeed;
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
