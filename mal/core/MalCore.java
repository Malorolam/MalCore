package mal.core;

import java.lang.annotation.Annotation;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import mal.core.item.ItemGuidebook;
import mal.core.network.CommonProxy;
import mal.core.network.MalCorePacketHandler;
import mal.core.util.MalEvents;
import mal.core.util.ModList;
import mal.core.version.VersionInfo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MalCore.MODID, version = MalCore.VERSION)
public class MalCore {
    public static final String MODID = "MalCore";
    public static final String VERSION = "1.1.0";
    public static final String VersionURL = "https://www.dropbox.com/s/ao6p1iye6w2suec/malcoreVersion.info?dl=1";
	public static boolean VERBOSEMODE = true;
	
	public static ItemGuidebook guidebookItem = new ItemGuidebook();
	
	@SidedProxy(clientSide = "mal.core.network.ClientProxy", serverSide = "mal.core.network.CommonProxy")
	public static CommonProxy prox;
	
	@Instance(value = MalCore.MODID)
	public static MalCore malcoreInstance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new MalEvents());
    	MalCorePacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(malcoreInstance, prox);
		
		GameRegistry.registerItem(guidebookItem, "itemguidebook");
    	
    	ModList.addMod(this.MODID, this.VERSION, VersionURL);
    }

    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	GuidebookPage page = new GuidebookPage("Guidebook Introduction", "This is the guidebook for all of my mods.  If MalCore is all you have installed " +
    			"then the only page will be this one.  The next button will progress the page once forward, with the prev button progressing the " +
    			"page once backwards.  The x button closes the gui, as well as the esc key.  ");
    	GuidebookRegistry.instance.addPage(page);
    	
    	CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(new ItemStack(guidebookItem), new Object[]{Items.book, new ItemStack(Items.coal,1,0)}));
    }
}