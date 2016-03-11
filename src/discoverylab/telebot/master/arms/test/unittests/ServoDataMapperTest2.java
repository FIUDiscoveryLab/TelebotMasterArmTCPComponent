package discoverylab.telebot.master.arms.test.unittests;

//ServoDataMapperTest2 class
//@author Curtis Cox
//This is a JUnit test class for the 
//servo position mapping routine 
//for Telebot'a arms and head.
//for FIU Discovery Lab Telebot - Arms

import junit.framework.TestCase;


import discoverylab.telebot.master.arms.*;


public class ServoDataMapperTest2 extends TestCase {
	private TelebotMasterArmsTCPComponent controller;
	
	protected void setUp() throws Exception {
		super.setUp();
		controller = new TelebotMasterArmsTCPComponent(6666, null);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		controller = null;
	}
	
	public void servoDataMapperElbow(){
		controller.callback("<left_elbow 15 64 0>");
		controller.callback("<left_elbow 0 0 0>");
		controller.callback("<left_elbow 0 98 0>");
		controller.callback("<left_elbow 0 -70 0>");
		controller.callback("<left_elbow 0 -1 0>");
		controller.callback("<left_elbow 0 350 0>");
	}
	

}
