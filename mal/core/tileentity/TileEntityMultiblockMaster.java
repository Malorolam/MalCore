package mal.core.tileentity;

import mal.core.api.ITileEntityInventory;
import mal.core.api.ITileEntityMultiblock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityMultiblockMaster extends TileEntity implements IInventory, ISidedInventory, ITileEntityMultiblock, ITileEntityInventory{
	public boolean properlyActivated = false;
	private int xSize;
	private int ySize;
	private int zSize;
	private String type;

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return xCoord;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return yCoord;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return zCoord;
	}
	
	@Override
	public int getXSize()
	{
		return xSize;
	}
	
	@Override
	public int getYSize()
	{
		return ySize;
	}
	
	@Override
	public int getZSize()
	{
		return zSize;
	}
	
	@Override
	public String getType()
	{
		return type;
	}

}
/*******************************************************************************
* Copyright (c) 2014 Malorolam.
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the included license, which is also
* available at http://carbonization.wikispaces.com/License
* 
*********************************************************************************/