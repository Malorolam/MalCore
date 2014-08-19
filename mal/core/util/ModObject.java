package mal.core.util;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Mod;

public class ModObject {

	private String modID;
	private String modVersion;
	private String modURL;
	
	public ModObject(String ID, String Version, String URL)
	{
		modID = ID;
		modVersion = Version;
		modURL = URL;
	}
	
	/**
	 * Not working, have to figure it out later
	 * 
	 */
	@Deprecated
	public ModObject(Object mod, String URL)
	{
		if(mod instanceof Mod)
		{
			modID = ((Mod) mod).modid();
			modVersion = ((Mod) mod).version();
			modURL = URL;
		}
		else
			MalLogger.addLogMessage(Level.ERROR, "Mod " + mod.toString() + " not actually a mod.");
	}
	
	public String getID()
	{
		return modID;
	}
	
	public String getVersion()
	{
		return modVersion;
	}
	
	public String getURL()
	{
		return modURL;
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