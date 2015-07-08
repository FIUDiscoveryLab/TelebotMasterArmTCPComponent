package src.discoverylab.telebot.master.arms.model;

public class YEIDataModel {
	
	public YEIDataModel(){	
	}
	
	public YEIDataModel(String jointType, long x, long maxX, long minX, 
			long y, long maxY, long minY, 
			long z, long maxZ, long minZ){
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
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getMaxX() {
		return maxX;
	}
	public void setMaxX(long x) {
		this.maxX = x;
	}
	public long getMinX() {
		return minX;
	}
	public void setMinX(long x) {
		this.minX = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	public long getMaxY() {
		return maxY;
	}
	public void setMaxY(long x) {
		this.maxY = x;
	}
	public long getMinY() {
		return minY;
	}
	public void setMinY(long x) {
		this.minY = x;
	}
	public long getZ() {
		return z;
	}
	public void setZ(long z) {
		this.z = z;
	}
	public long getMaxZ() {
		return maxZ;
	}
	public void setMaxZ(long x) {
		this.maxZ = x;
	}
	public long getMinZ() {
		return minZ;
	}
	public void setMinZ(long x) {
		this.minZ = x;
	}
	private String jointType;
	private long x;
	private long maxX;
	private long minX;
	private long y;
	private long maxY;
	private long minY;
	private long z;
	private long maxZ;
	private long minZ;
	

}
