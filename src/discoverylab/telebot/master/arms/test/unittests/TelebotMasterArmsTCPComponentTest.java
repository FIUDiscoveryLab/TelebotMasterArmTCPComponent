package discoverylab.telebot.master.arms.test.unittests;

import static org.junit.Assert.*;
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
import discoverylab.telebot.master.arms.TelebotMasterArmsTCPComponent;

import org.junit.Test;

public class TelebotMasterArmsTCPComponentTest {

	int dummyPort = 6666;
	
	TelebotMasterArmsTCPComponent telebotMasterArms = new TelebotMasterArmsTCPComponent(dummyPort);
	
	@Test
	public void testCallback() 
	{
		assertArrayEquals();
	}

}
