package mal.core.api;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityInventory {

	public void dumpInventory();
	
	public void writeInventorytoNBT(NBTTagCompound nbt);
	
	public void buildInventoryfromNBT(NBTTagCompound nbt);
}
/*******************************************************************************
* Copyright (c) 2014 Malorolam.
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the included license, which is also
* available at http://carbonization.wikispaces.com/License
* 
*********************************************************************************/