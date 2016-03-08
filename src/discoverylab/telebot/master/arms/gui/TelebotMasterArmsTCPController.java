package discoverylab.telebot.master.arms.gui;


public class TelebotMasterArmsTCPController 
{
	private TelebotMasterArmsTCPView view;
	
	public TelebotMasterArmsTCPController(TelebotMasterArmsTCPView view)
	{
		this.view = view;
	}

	public void changeText(String jointType, int x, int y, int z)
	{
		if(jointType.equals("head"))
		{
			view.setHeadText(x + " " + y + " " + z);
		}
		else if(jointType.equals("left_shoulder"))
		{
			view.setLeftShoulderText(x + " " + y + " " + z);
		}
		else if(jointType.equals("left_elbow"))
		{
			view.setLeftElbowText(x + " " + y + " " + z);
		}
		else if(jointType.equals("left_wrist"))
		{
			view.setLeftWristText(x + " " + y + " " + z);
		}
		else if(jointType.equals("right_shoulder"))
		{
			view.setRightShoulderText(x + " " + y + " " + z);
		}
		else if(jointType.equals("right_elbow"))
		{
			view.setRightElbowText(x + " " + y + " " + z);
		}
		else if(jointType.equals("right_wrist"))
		{
			view.setRightWristText(x + " " + y + " " + z);
		}
	}
}

