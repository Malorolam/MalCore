package mal.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class MalLogger {

	public static void addLogMessage(Level level, String message)
	{
		FMLLog.log(level, message);
	}
	
	public static void addLogMessage(String message)
	{
		addLogMessage(Level.INFO,message);
	}
	
	public static void addChatMessage(EntityPlayer player, String message)
	{
		player.addChatComponentMessage(new ChatComponentText(message));
	}
	
	
	public static void sendMessageToAllPlayers(World world, String message)
	{
		for(Object p: world.playerEntities)
		{
			if(p instanceof EntityPlayer)
			{
				addChatMessage((EntityPlayer)p, message);
			}
		}
	}
	
	public static void sendMessageToClosestPlayer(World world, String message, int x, int y, int z)
	{
		EntityPlayer p = world.getClosestPlayer(x, y, z, 32);
		if(p != null)
			addChatMessage(p, message);
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