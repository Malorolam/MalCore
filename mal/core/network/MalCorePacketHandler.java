package mal.core.network;

import mal.carbonization.network.MultiblockInitMessage;
import mal.carbonization.tileentity.TileEntityMultiblockInit;
import mal.core.MalCore;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


public class MalCorePacketHandler {
	public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(MalCore.MODID.toLowerCase());
	
	public static void init()
	{
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