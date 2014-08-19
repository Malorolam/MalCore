package mal.core.tileentity;

import mal.core.api.ITieredItem;
import mal.core.api.ITileEntityMultiblock;
import mal.core.util.MalLogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface ITileEntityMultiblockSlave {
	
	public void setMaster(TileEntityMultiblockMaster m);
	
	public ITileEntityMultiblock getMaster();
	
	public int getData();
	/*
	 * FIRST PARAM MUST BE A TILE ENTITY
	 * SECOND PARAM MUST BE MULTIBLOCK OR NULL
	 * OR I WILL BREAK
	 */
	public void initilize(Object[] params);
	
	/*
	 * Clean up ourselves if there is still a block here
	 */
	public void revert();
	
	public void InitMultiblock(ITileEntityMultiblock master);
}
