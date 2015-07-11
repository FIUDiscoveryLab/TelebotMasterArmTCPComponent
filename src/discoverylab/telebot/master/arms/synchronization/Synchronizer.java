package discoverylab.telebot.master.arms.synchronization;

public abstract class Synchronizer {
	
	public abstract void findMaxMin(Object instance, long maxX, long minX, 
			long maxY, long minY, long maxZ, long minZ);
	
	public abstract int synchronize(Object instance, int count);
	
}
