package src.discoverylab.telebot.master.arms.model;

public class ServoDataModel {
	
	public ServoDataModel(){
	}
	
	public ServoDataModel(int servoID, long max, long min)
	{
		this.servoID = servoID;
		this.max = max;
		this.min = min;
	}
	
	public int getServoID() {
		return servoID;
	}
	public void setServoID(int servoID) {
		this.servoID = servoID;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getMin() {
		return min;
	}
	public void setMin(long min) {
		this.min = min;
	}
	private int servoID;
	private long max;
	private long min;
}
