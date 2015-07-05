package discoverylab.telebot.master.arms.model;

public class YEIDataModel {
	
	public YEIDataModel(){	
	}
	
	public YEIDataModel(String jointType, long x, long y, long z){
		this.jointType = jointType;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String getJointType() {
		return jointType;
	}
	public void setJointType(String jointType) {
		this.jointType = jointType;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	public long getZ() {
		return z;
	}
	public void setZ(long z) {
		this.z = z;
	}
	private String jointType;
	private long x;
	private long y;
	private long z;
	

}
