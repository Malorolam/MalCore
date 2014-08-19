package mal.core.network;

import mal.core.MalCore;
import mal.core.gui.GuiGuidebook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;


public class ClientProxy extends CommonProxy{

	@Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
        if(id == 4 && player.getCurrentEquippedItem() != null && Item.getIdFromItem(player.getCurrentEquippedItem().getItem()) == Item.getIdFromItem(MalCore.guidebookItem))
        {
        	return new GuiGuidebook(player, player.worldObj);
        }
        return null;
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