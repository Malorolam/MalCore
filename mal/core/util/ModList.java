package mal.core.util;

import mal.core.MalCore;
import mal.core.version.VersionInfo;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class ModList {

	private static MalCore malCore;
	
	public static void addMod(Object mod)
	{
		if(mod instanceof MalCore)
			malCore = (MalCore) mod;
	}
	
	public static String getModID(int index)
	{
		if(index == 0 && malCore != null)
			return malCore.MODID;
		return null;
	}
	
	public static String getModVersion(int index)
	{
		if(index == 0 && malCore != null)
			return malCore.VERSION;
		return null;
	}
	
	//this is bad, but customproperties is being a little bitch
	public static String getModURL(int index)
	{
		if(index == 0 && malCore != null)
			return malCore.VersionURL;
		return null;
	}
	
	public static void doVersionCheck(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(malCore != null)
			VersionInfo.doVersionCheck(ModList.getModID(0), ModList.getModVersion(0), ModList.getModURL(0), event.player);
	}
}
