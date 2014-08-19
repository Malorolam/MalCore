package mal.core.network;

import net.minecraft.tileentity.TileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class BasicMessage implements IMessage{

	TileEntity tileentity;
	public BasicMessage(TileEntity te)
	{
		tileentity = te;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int xpos = buf.readInt();
		int ypos = buf.readInt();
		int zpos = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
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