package mal.core;

import java.lang.annotation.Annotation;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import mal.core.guidebook.GuidebookPage;
import mal.core.guidebook.GuidebookRegistry;
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
    public static final String VERSION = "1.1.6";
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
/*    	GuidebookPage test = new GuidebookPage("mal.page.test.title", "mal.page.test.lower",
    			"mal.page.test.upper", new ItemStack(guidebookItem), new ItemStack[]{new ItemStack(Items.apple), new ItemStack(Items.baked_potato), new ItemStack(Items.cake), new ItemStack(Items.diamond), new ItemStack(Items.egg), new ItemStack(Items.feather), new ItemStack(Items.ghast_tear), new ItemStack(Items.hopper_minecart)});
    	GuidebookRegistry.instance.addPage(test);
    	test = new GuidebookPage("mal.page.test.title", "mal.page.test.lower",
    			"mal.page.test.upper", new ItemStack(guidebookItem), null);
    	GuidebookRegistry.instance.addPage(test);
    	test = new GuidebookPage("mal.page.test.title", "mal.page.test.lower",
    			"mal.page.test.upper");
    	GuidebookRegistry.instance.addPage(test);*/
    	
    	GuidebookPage page = new GuidebookPage("mal.page.intro.title", "mal.page.intro.lower");
    	GuidebookRegistry.instance.addPage(page);
    	
    	CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(new ItemStack(guidebookItem), new Object[]{Items.book, new ItemStack(Items.coal,1,0)}));
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