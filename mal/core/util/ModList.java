package mal.core.util;

import java.util.ArrayList;

import mal.core.MalCore;
import mal.core.version.VersionInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class ModList {

	private static ArrayList<ModObject> modList = new ArrayList<ModObject>();
	
	public static void addMod(String ModID, String ModVersion, String URL)
	{
		modList.add(new ModObject(ModID, ModVersion, URL));
	}
	
	public static String getModID(int index)
	{
		if(index<modList.size())
			return modList.get(index).getID();
		return null;
	}
	
	public static String getModVersion(int index)
	{
		if(index<modList.size())
			return modList.get(index).getVersion();
		return null;
	}
	
	//this is bad, but customproperties is being a little bitch
	public static String getModURL(int index)
	{
		if(index<modList.size())
			return modList.get(index).getURL();
		return null;
	}
	
	public static void doVersionCheck(PlayerEvent.PlayerLoggedInEvent event)
	{
		for(int i = 0; i < modList.size(); i++)
			VersionInfo.doVersionCheck(ModList.getModID(i), ModList.getModVersion(i), ModList.getModURL(i), event.player);
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