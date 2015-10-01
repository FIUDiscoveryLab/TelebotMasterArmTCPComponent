package discoverylab.telebot.master.arms.model;

public class YEIDataModel {
	
	public YEIDataModel(){	
	}
	
	public YEIDataModel(String jointType, int x, int maxX, int minX, 
			int y, int maxY, int minY, 
			int z, int maxZ, int minZ){
		this.jointType = jointType;
		this.x = x;
		this.maxX = maxX;
		this.minX = minX;
		this.y = y;
		this.maxY = maxY;
		this.minY = minY;
		this.z = z;
		this.maxZ = maxZ;
		this.minZ = minZ;
	}
	
	public String getJointType() {
		return jointType;
	}
	public void setJointType(String jointType) {
		this.jointType = jointType;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getMaxX() {
		return maxX;
	}
	public void setMaxX(int x) {
		this.maxX = x;
	}
	public int getMinX() {
		return minX;
	}
	public void setMinX(int x) {
		this.minX = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getMaxY() {
		return maxY;
	}
	public void setMaxY(int x) {
		this.maxY = x;
	}
	public int getMinY() {
		return minY;
	}
	public void setMinY(int x) {
		this.minY = x;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getMaxZ() {
		return maxZ;
	}
	public void setMaxZ(int x) {
		this.maxZ = x;
	}
	public int getMinZ() {
		return minZ;
	}
	public void setMinZ(int x) {
		this.minZ = x;
	}
	private String jointType;
	private int x;
	private int maxX;
	private int minX;
	private int y;
	private int maxY;
	private int minY;
	private int z;
	private int maxZ;
	private int minZ;
	

}
