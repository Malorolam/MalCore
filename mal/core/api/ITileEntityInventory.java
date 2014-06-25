package mal.core.api;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityInventory {

	public void dumpInventory();
	
	public void writeInventorytoNBT(NBTTagCompound nbt);
	
	public void buildInventoryfromNBT(NBTTagCompound nbt);
}
