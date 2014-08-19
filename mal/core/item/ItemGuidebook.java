package mal.core.item;

import java.util.List;

import mal.carbonization.carbonization;
import mal.core.MalCore;
import mal.core.reference.ColorReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGuidebook extends Item{

	public ItemGuidebook()
	{
		super();
		setUnlocalizedName("guidebook");
		maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public void addInformation(ItemStack is, EntityPlayer ep, List list, boolean bool)
	{
		//find the right metadata value, currently doesn't do anything, since there is no metadata
		list.add(setTooltipData("A collection of useful", ColorReference.LIGHTRED));
		list.add(setTooltipData("information for Mal's mods.", ColorReference.LIGHTRED));
	}
	
	//The tool tip information
	private String setTooltipData(String data, ColorReference cr)
	{
		String colorValue = cr.getCode();
		
		return colorValue+data;
	}
	
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		if(world.isRemote)
		{
			player.openGui(MalCore.malcoreInstance, 4, world, 0, 0, 0);
		}
		
		return is;
	}
	
	@Override
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon("malcore:guidebookTexture");
	}
}
