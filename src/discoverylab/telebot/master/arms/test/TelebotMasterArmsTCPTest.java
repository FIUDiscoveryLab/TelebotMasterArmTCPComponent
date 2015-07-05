package discoverylab.telebot.master.arms.test;

import static discoverylab.util.logging.LogUtils.*;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TOPIC_MASTER_TO_SLAVE_ARMS;
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPComponent;

public class TelebotMasterArmsTCPTest {

	public static String TAG = makeLogTag("TelebotMasterArmsTCPTest");
	public static void maint(String args []) {
		// 1. INITIATE Slave Component DEVICE
		TelebotMasterArmsTCPComponent telebotMasterArms = new TelebotMasterArmsTCPComponent(666);
		telebotMasterArms.initiate();
		
		// 2. INITIATE Transmission PROTOCOL
		telebotMasterArms.initiateTransmissionProtocol(TOPIC_MASTER_TO_SLAVE_ARMS.VALUE
				, TMasterToArms.class);
		
		// 3. INITATE DataWriter
		telebotMasterArms.initiateDataWriter();
	}
}
