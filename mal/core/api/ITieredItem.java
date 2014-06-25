package mal.core.api;

public interface ITieredItem {

	public double[] getTier(int damage);
	
	public double getInsulationTier();
	public double getConductionTier();

	int getX();

	int getY();

	int getZ();
}
