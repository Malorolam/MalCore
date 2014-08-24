package mal.core.reference;

import cpw.mods.fml.common.registry.GameRegistry;
import mal.core.api.IFuelContainer;
import mal.core.api.ITieredItem;
import mal.core.item.ItemFuelPotentialBucket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;

/**
 * @author Mal
 *
 * A bunch of random methods that I use a lot
 */
public class UtilReference {
	
	/**
	 * For some reason it doesn't like it when value==0.... whatever
	 */
	public static int getItemBurnTime(ItemStack par0ItemStack, int value, boolean useItem)
	{
		if (par0ItemStack == null)
		{
			return 0;
		}
		else if(par0ItemStack.getItem() instanceof IFuelContainer)
        {
        	//get the value
        	long fuelValue = ((IFuelContainer)par0ItemStack.getItem()).getFuelValue(par0ItemStack);
        	
        	//if it's a number, reduce it by some amount, we're using standard coal or the value, whichever is smaller
        	if(fuelValue == 0)
        		return 0;
        	else if(fuelValue >= value)
        	{
        		if(useItem)
        			((IFuelContainer)par0ItemStack.getItem()).setFuel(par0ItemStack, fuelValue-value, true);
        		return (int) value;
        	}
        	else
        	{
        		if(useItem)
        			((IFuelContainer)par0ItemStack.getItem()).setFuel(par0ItemStack, 0, true);
        		return (int) fuelValue;
        	}
        }
		else if(par0ItemStack.getItem() instanceof ItemFuelPotentialBucket)
		{
			return 1000;
		}
		else
		{
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock && Block.getBlockFromItem(item) != null)
			{
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab)
				{
					//return 150;
				}

				if (block.getMaterial() == Material.wood)
				{
					//return 300;
				}

				if (block == Blocks.coal_block)
				{
					return 16000;
				}
				
	            //if (block == Blocks.sapling) return 100;
			}

			//if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
            //if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
            //if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD")) return 200;
            //if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Items.blaze_rod) return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}
	
	/*
	 * Helper method to get the tier of a structure block, either average or cond/ins
	 */
	public static double getTier(ItemStack item, boolean useAverage, boolean conduction)
	{
		if(item == null)
			return 0;
		if(!(item.getItem() instanceof 	ITieredItem))
			return 0;
		double[] tier = ((ITieredItem)item.getItem()).getTier(item.getItemDamage());
		if(useAverage)
			return (tier[0]+tier[1])/2;
		if(conduction)
			return tier[1];
		return tier[0];
	}
	
	public static double getTier(ItemStack item)
	{
		return getTier(item, true, false);
	}
	
	/*
	 * Helper method for determining if a point is in a region of a gui
	 */
	public static boolean isPointInRegion(int left, int top, int width, int height, int pointx,
			int pointy, int guiLeft, int guiTop) {
        pointx -= guiLeft;
        pointy -= guiTop;
        return pointx >= left - 1 && pointx < left + width + 1 && pointy >= top - 1 && pointy < top + height + 1;
	}
	
	//Slightly easier way to get a block ID, and allows for null cases without breaking much
	public static int getBlockID(Block block)
	{
		if(block == null)
			return 0;
		return Block.getIdFromBlock(block);
	}
	
	/**
     * Inserts a stack into an inventory. Args: Inventory, stack, side. Returns leftover items.
     */
    public static ItemStack insertStack(IInventory par0IInventory, ItemStack par1ItemStack, int par2)
    {
        if (par0IInventory instanceof ISidedInventory && par2 > -1)
        {
            ISidedInventory isidedinventory = (ISidedInventory)par0IInventory;
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(par2);

            for (int j = 0; j < aint.length && par1ItemStack != null && par1ItemStack.stackSize > 0; ++j)
            {
                par1ItemStack = func_102014_c(par0IInventory, par1ItemStack, aint[j], par2);
            }
        }
        else
        {
            int k = par0IInventory.getSizeInventory();

            for (int l = 0; l < k && par1ItemStack != null && par1ItemStack.stackSize > 0; ++l)
            {
                par1ItemStack = func_102014_c(par0IInventory, par1ItemStack, l, par2);
            }
        }

        if (par1ItemStack != null && par1ItemStack.stackSize == 0)
        {
            par1ItemStack = null;
        }

        return par1ItemStack;
    }
    
    //mystical magical helper method....
    private static ItemStack func_102014_c(IInventory par0IInventory, ItemStack par1ItemStack, int par2, int par3)
    {
        ItemStack itemstack1 = par0IInventory.getStackInSlot(par2);

        if (canInsertItemToInventory(par0IInventory, par1ItemStack, par2, par3))
        {
            boolean flag = false;

            if (itemstack1 == null)
            {
                int max = Math.min(par1ItemStack.getMaxStackSize(), par0IInventory.getInventoryStackLimit());
                if (max >= par1ItemStack.stackSize)
                {
                    par0IInventory.setInventorySlotContents(par2, par1ItemStack);
                    par1ItemStack = null;
                }
                else
                {
                    par0IInventory.setInventorySlotContents(par2, par1ItemStack.splitStack(max));
                }
                flag = true;
            }
            else if (areItemStacksEqualItem(itemstack1, par1ItemStack, true, true))
            {
                int max = Math.min(par1ItemStack.getMaxStackSize(), par0IInventory.getInventoryStackLimit());
                if (max > itemstack1.stackSize)
                {
                    int l = Math.min(par1ItemStack.stackSize, max - itemstack1.stackSize);
                    par1ItemStack.stackSize -= l;
                    itemstack1.stackSize += l;
                    flag = l > 0;
                }
            }

            if (flag)
                par0IInventory.markDirty();
        }

        return par1ItemStack;
    }
    
    /**
     * Args: inventory, item, slot, side
     */
    private static boolean canInsertItemToInventory(IInventory par0IInventory, ItemStack par1ItemStack, int par2, int par3)
    {
        return !par0IInventory.isItemValidForSlot(par2, par1ItemStack) ? false : !(par0IInventory instanceof ISidedInventory) || ((ISidedInventory)par0IInventory).canInsertItem(par2, par1ItemStack, par3);
    }
    
    public static boolean areItemStacksEqualItem(ItemStack is1, ItemStack is2, boolean considerDamage, boolean considerNBT)
    {
    	if(Item.getIdFromItem(is1.getItem()) != Item.getIdFromItem(is2.getItem()))
    		return false;
    	if(considerDamage && is1.getItemDamage() != is2.getItemDamage())
    		return false;
    	if(considerNBT && !ItemStack.areItemStackTagsEqual(is1, is2))
    		return false;
    	return true;
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