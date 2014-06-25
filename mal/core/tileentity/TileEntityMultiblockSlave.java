package mal.core.tileentity;

import mal.core.api.ITieredItem;
import mal.core.api.ITileEntityMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityMultiblockSlave extends TileEntity implements IInventory, ISidedInventory, ITieredItem{
	protected ITileEntityMultiblock masterEntity;
	public boolean loaded;
	public int mx,my,mz;
	protected int data;
	
	public TileEntityMultiblockSlave()
	{
		masterEntity=null;
	}
	
	public TileEntityMultiblockSlave(ITileEntityMultiblock entity)
	{
		masterEntity = entity;
	}
	
	public void setMaster(TileEntityMultiblockMaster m)
	{
		masterEntity = m;
	}
	
	public ITileEntityMultiblock getMaster()
	{
		return masterEntity;
	}
	
	public int getData()
	{
		return data;
	}
	/*
	 * FIRST PARAM MUST BE A TILE ENTITY
	 * SECOND PARAM MUST BE MULTIBLOCK OR NULL
	 * OR I WILL BREAK
	 */
	public void initilize(Object[] params) {
		//System.out.println("Dummy Initilized");
		masterEntity = (ITileEntityMultiblock) params[0];
		mx = masterEntity.getX() - this.xCoord;
		my = masterEntity.getY() - this.yCoord;
		mz = masterEntity.getZ() - this.zCoord;

		loaded = true;
	}
	
	/*
	 * Clean up ourselves if there is still a block here
	 */
	public void revert() {
		if(masterEntity != null)
			masterEntity.revert();
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setInteger("masterX", mx);
		nbt.setInteger("masterY", my);
		nbt.setInteger("masterZ", mz);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		mx = nbt.getInteger("masterX");
		my = nbt.getInteger("masterY");
		mz = nbt.getInteger("masterZ");
		
		if(mx==1000 && my==1000 && mz==1000)
			return;
		
		if(worldObj.getTileEntity(mx, my, mz) != null && worldObj.getTileEntity(mx, my, mz) instanceof ITileEntityMultiblock)
			masterEntity = (ITileEntityMultiblock)worldObj.getTileEntity(mx, my, mz);
		else
			masterEntity = null;
	}
	
	public void InitMultiblock(TileEntity te)
	{
		if(te instanceof ITileEntityMultiblock)
			setMaster((TileEntityMultiblockMaster)te);
	}
}
