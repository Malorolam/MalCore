package mal.core.tileentity;

import mal.core.api.ITieredItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMultiblockSlave extends TileEntity implements IInventory, ISidedInventory, ITieredItem{
	private TileEntityMultiblockMaster master;
	private int data;
	
	public TileEntityMultiblockSlave(int data)
	{
		this.data = data;
	}
	
	public void setMaster(TileEntityMultiblockMaster m)
	{
		master = m;
	}
	
	public TileEntity getMaster()
	{
		return master;
	}
	
	public void InitMultiblock(TileEntity te)
	{
		if(te instanceof TileEntityMultiblockMaster)
			setMaster((TileEntityMultiblockMaster)te);
	}

	public int getData() {
		// TODO Auto-generated method stub
		return data;
	}
}
