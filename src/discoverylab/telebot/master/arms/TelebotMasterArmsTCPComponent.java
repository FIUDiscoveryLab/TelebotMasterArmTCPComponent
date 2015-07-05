package discoverylab.telebot.master.arms;

import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.publication.DataWriterImpl;

import TelebotDDSCore.DDSCommunicator;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArmsDataWriter;
import discoverylab.telebot.master.arms.model.YEIDataModel;
import discoverylab.telebot.master.arms.parser.YEIDataParser;
import discoverylab.telebot.master.core.CoreMasterTCPComponent;
import discoverylab.telebot.master.core.socket.CoreServerSocket;

public class TelebotMasterArmsTCPComponent extends CoreMasterTCPComponent implements CoreServerSocket.CallbackInterface{
	
	private CallbackInterface callbackInterface;
	private YEIDataParser parser;
	
	private DDSCommunicator communicator;
	
	private TMasterToArmsDataWriter writer;
	TMasterToArms instance = new TMasterToArms();
	InstanceHandle_t instance_handle = InstanceHandle_t.HANDLE_NIL;
	
	public TelebotMasterArmsTCPComponent(int portNumber) {
		super(portNumber);
		parser = new YEIDataParser();
	
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
		YEIDataModel yeiDataInstance = (YEIDataModel) parser.parse(data);
		
		//NOTE: setDataWriter should be called from the Driver class///
		
		// WRITE X
//		instance.servoId = 
//		instance.servoPositon = 
//		instance.servoSpeed = 
//		writer.write_untyped(instance, instance_handle);
		
		// WRITE Y
//		instance.servoId = 
//		instance.servoPositon = 
//		instance.servoSpeed = 
//		writer.write_untyped(instance, instance_handle);
		
		// WRITE Z
//		instance.servoId = 
//		instance.servoPositon = 
//		instance.servoSpeed = 
//		writer.write_untyped(instance, instance_handle);

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
