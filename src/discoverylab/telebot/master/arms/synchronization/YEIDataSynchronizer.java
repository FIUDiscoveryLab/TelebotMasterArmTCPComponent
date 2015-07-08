package discoverylab.telebot.master.arms.synchronization;

import src.discoverylab.telebot.master.arms.model.String;

public class YEIDataSynchronizer extends Synchronizer {
	
	public YEIDataSynchronizer(){}
	
	public void findMaxMin(Object instance, long maxX, long minX, 
			long maxY, long minY, long maxZ, long minZ)
	{
		long x = instance.getX;
		long y = instance.getY;
		long z = instance.getZ;
		
		if(x < minX)
		{
			instance.setMinX(x);
		}
		else if(x > maxX)
		{
			instance.setMaxX(x);
		}
		
		if(y < minY)
		{
			instance.setMinY(y);
		}
		else if(y > maxY)
		{
			instance.setMaxY(y);
		}
		
		if(z < minZ)
		{
			instance.setMinZ(z);
		}
		else if(z > maxZ)
		{
			instance.setMaxZ(z);
		}
	}
	
	public int synchronize(Object instance, int count)
	{
		long maxX = -360;
		long minX = 360;
		long maxY = -360;
		long minY = 360;
		long maxZ = -360;
		long minZ = 360;
		
		
		if(instance.getJointType().equals("head"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("left_shoulder"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("left_elbow"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("left_wrist"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("right_shoulder"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("right_elbow"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		else if(instance.getJointType().equals("right_wrist"))
		{
			findMaxMin(instance, maxX, minX, maxY, minY, maxZ, minZ);
		}
		
		count++;
		return count;
	}
	
}
