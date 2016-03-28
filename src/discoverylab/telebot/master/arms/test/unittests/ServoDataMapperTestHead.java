package discoverylab.telebot.master.arms.test.unittests;

//ServoDataMapperTestHead class
//@author Curtis Cox
//This is a JUnit test class for the 
//servo position mapping routine 
//for Telebot'a arms and head.
//for FIU Discovery Lab Telebot - Arms

import junit.framework.TestCase;
import TelebotDDSCore.Source.Java.Generated.master.arms.TMasterToArms;
import TelebotDDSCore.Source.Java.Generated.master.arms.TOPIC_MASTER_TO_SLAVE_ARMS;
import discoverylab.telebot.master.arms.*;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPController;
import discoverylab.telebot.master.arms.gui.TelebotMasterArmsTCPView;


public class ServoDataMapperTestHead extends TestCase {
	private TelebotMasterArmsTCPComponent controller;
	
	protected void setUp() throws Exception {
		super.setUp();
		TelebotMasterArmsTCPView view = new TelebotMasterArmsTCPView();
		TelebotMasterArmsTCPController gui = new TelebotMasterArmsTCPController(view);
		controller = new TelebotMasterArmsTCPComponent(6666, gui);
		controller.initiate();
		controller.initiateTransmissionProtocol(TOPIC_MASTER_TO_SLAVE_ARMS.VALUE, TMasterToArms.class);
		controller.initiateDataWriter();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		controller = null;
	}
	
	public void testServoDataMapperHeadYawValid(){
		controller.generatePositions("head", 0, 0, 0);
		controller.generatePositions("head", 0, 0, 15);
		controller.generatePositions("head", 0, 0, 30);
		controller.generatePositions("head", 0, 0, 45);
		controller.generatePositions("head", 0, 0, -15);
		controller.generatePositions("head", 0, 0, -30);
		controller.generatePositions("head", 0, 0, -45);
	}
	
	public void testServoDataMapperHeadYawInvalid(){
		controller.generatePositions("head", 0, 0, 50);
		controller.generatePositions("head", 0, 0, 180);
		controller.generatePositions("head", 0, 0, -50);
		controller.generatePositions("head", 0, 0, -180);
	}
	
	public void testServoDataMapperHeadTiltValid(){
		controller.generatePositions("head", 0, 0, 0);
		controller.generatePositions("head", 15, 0, 0);
		controller.generatePositions("head", 30, 0, 0);
		controller.generatePositions("head", 35, 0, 0);
		controller.generatePositions("head", -15, 0, 0);
		controller.generatePositions("head", -17, 0, 0);
	}
	
	public void testServoDataMapperHeadTiltInvalid(){
		controller.generatePositions("head", 36, 0, 0);
		controller.generatePositions("head", 90, 0, 0);
		controller.generatePositions("head", -18, 0, 0);
		controller.generatePositions("head", -180, 0, 0);
	}

}
