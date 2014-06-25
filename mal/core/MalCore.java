package mal.core;

import java.lang.annotation.Annotation;

import mal.core.network.MalCorePacketHandler;
import mal.core.util.MalEvents;
import mal.core.util.ModList;
import mal.core.version.VersionInfo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MalCore.MODID, version = MalCore.VERSION)
public class MalCore {
    public static final String MODID = "MalCore";
    public static final String VERSION = "0.0.1";
    public static final String VersionURL = "https://www.dropbox.com/s/ao6p1iye6w2suec/malcoreVersion.info?dl=1";
	public static boolean VERBOSEMODE = true;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new MalEvents());
    	MalCorePacketHandler.init();
    	
    	ModList.addMod(this);
    }

}