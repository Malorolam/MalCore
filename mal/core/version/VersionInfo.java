package mal.core.version;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mal.core.MalCore;
import mal.core.util.MalLogger;
import net.minecraft.entity.player.EntityPlayer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;

/*
 * Locate and parse the version info so we can send it if there is a new version
 */
public class VersionInfo {

	
	boolean majorUpdate;
	boolean newVersion;
	boolean newMCVersion;
	boolean versionCheckCompleted;
	
	String latestVersion;
	String modName;
	String modVersion;
	String latestMCVersion = "1.6.4";
	String versionDescription = "";
	String modURL;
	Logger modLogger = FMLLog.getLogger();
	EntityPlayer player;
	
	public VersionInfo(String name, String version, String url, EntityPlayer player)
	{
		modName = name;
		modVersion = (latestVersion = version);
		modURL = url;
		this.player = player;
	}
	
	public VersionInfo(String name, String version, String url, Logger logger, EntityPlayer player)
	{
		this(name, version, url, player);
		this.modLogger = logger;
	}
	
	public static int[] parseVersion(String rawData)
	{
		ArrayList vtoken = new ArrayList();
		String[] tokens = rawData.trim().split("[\\. ]");
		
		for(int i = 0; i < tokens.length; i++)
		{
			tokens[i] = tokens[i].trim();
			if(tokens[i].matches("[0-9]+"))
				vtoken.add(Integer.valueOf(tokens[i]));
			else if(tokens[i].matches("[0-9]+[a-z]"))
			{
				String num = tokens[i].substring(0, tokens[i].length()-1);
				vtoken.add(Integer.valueOf(num));
				vtoken.add(Integer.valueOf(Character.getNumericValue(tokens[i].charAt(tokens[i].length()-1))));
			}
		}
		
		int[] value = new int[vtoken.size()];
		for(int i = 0; i < value.length; i++)
		{
			value[i] = ((Integer)vtoken.get(i)).intValue();
		}
		return value;
	}
	
	public static boolean beforeTargetVersion(String version, String target)
	{
		boolean flag = false;
		int[] versionToken = parseVersion(version);
		int[] targetToken = parseVersion(target);
		
		for(int i = 0; (i<versionToken.length && i<targetToken.length); i++)//array index out of bounds are bad, mmKay?
		{
			if(versionToken[i] < targetToken[i])
			{
				flag = true;
				break;
			}
			else if(versionToken[i] > targetToken[i])
			{
				flag = false;
				break;
			}
			else if(i==versionToken.length && versionToken.length < targetToken.length)
			{
				flag = true;
			}
		}
		
		return flag;
	}
	
	public static boolean afterTargetVersion(String target, String version)
	{
		boolean flag = false;
		int[] versionToken = parseVersion(version);
		int[] targetToken = parseVersion(target);
		
		for(int i = 0; (i<versionToken.length && i<targetToken.length); i++)
		{
			if(versionToken[i] > targetToken[i])
			{
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	public static void doVersionCheck(String modName, String Version, String location, EntityPlayer player)
	{
		VersionInfo vInfo = new VersionInfo(modName, Version, location, player);
		
		vInfo.checkForNewVersion();
		
	}
	
	public void checkForNewVersion()
	{
		Thread versionThread = new VersionCheckThread();
		versionThread.start();
	}
	
	public String getCurrentVersion()
	{
		return modVersion;
	}
	
	public String getLatestVersion()
	{
		return latestVersion;
	}
	
	public String getVersionDescription()
	{
		return versionDescription;
	}
	
	public boolean isMajorUpdate()
	{
		return majorUpdate;
	}
	
	public boolean isNewVersionAvailable()
	{
		return newVersion;
	}
	
	public boolean isMCOutdated()
	{
		return newMCVersion;
	}
	
	public boolean isVersionCheckComplete()
	{
		return versionCheckCompleted;
	}
	
	private class VersionCheckThread extends Thread
	{
		private VersionCheckThread()
		{
		}

		public void run()
		{
			try
			{

				HttpURLConnection connection = null;

				while ((modURL != null) && (!modURL.isEmpty()))
				{
					URL url = new URL(modURL);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.60 Safari/537.17");

					connection.connect();
					modURL = connection.getHeaderField("Location");
				}

				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				VersionInfo.this.latestVersion = reader.readLine();
				VersionInfo.this.majorUpdate = Boolean.parseBoolean(reader.readLine());
				VersionInfo.this.latestMCVersion = reader.readLine();
				VersionInfo.this.versionDescription = reader.readLine();
				reader.close();

				if (VersionInfo.beforeTargetVersion(VersionInfo.this.modVersion, VersionInfo.this.latestVersion))
				{
					MalLogger.addChatMessage(VersionInfo.this.player, "The current version of " + VersionInfo.this.modName + " is: " + VersionInfo.this.getCurrentVersion() + ".");
					MalLogger.addChatMessage(VersionInfo.this.player, "The latest version is: " + VersionInfo.this.latestVersion + ".");
					VersionInfo.this.newVersion = true;
					if (VersionInfo.this.majorUpdate)
					{
						MalLogger.addChatMessage(VersionInfo.this.player, "This update has been marked as major.");
					}
					if (VersionInfo.beforeTargetVersion("1.7.2", VersionInfo.this.latestMCVersion))
					{
						VersionInfo.this.newMCVersion = true;
						MalLogger.addChatMessage(VersionInfo.this.player, "This update is for Minecraft " + VersionInfo.this.latestMCVersion + ".");
					}
				}
				/*else if(MalCore.VERBOSEMODE)
				{
					MalLogger.addChatMessage(VersionInfo.this.player, "The current version of " + VersionInfo.this.modName + " is: " + VersionInfo.this.getCurrentVersion() + ".");
					MalLogger.addChatMessage(VersionInfo.this.player, "The latest version is: " + VersionInfo.this.latestVersion + ".");
					VersionInfo.this.newVersion = true;
					if (VersionInfo.this.majorUpdate)
					{
						MalLogger.addChatMessage(VersionInfo.this.player, "This update has been marked as major.");
					}
					if (VersionInfo.beforeTargetVersion("1.7.2", VersionInfo.this.latestMCVersion))
					{
						VersionInfo.this.newMCVersion = true;
						MalLogger.addChatMessage(VersionInfo.this.player, "This update is for Minecraft " + VersionInfo.this.latestMCVersion + ".");
					}
				}*/

			}
			catch (Exception e)
			{
				MalLogger.addChatMessage(VersionInfo.this.player, "Version Check Failed: " + e.getMessage());
			}
			VersionInfo.this.versionCheckCompleted = true;
		}
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