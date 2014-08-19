package mal.core;

import net.minecraft.item.ItemStack;

/**
 * This contains all relevant information for a single page of the guidebook to be accessed by the gui.
 * Elements that are null are assumed to not have any data and will be skipped
 * @author Mal
 *
 */
public class GuidebookPage {

	private String pagetitle;
	private String text;
	private ItemStack item;
	private ItemStack[] recipe = new ItemStack[9];
	
	/**
	 * 
	 * @param pagetitle: the title of the page
	 * @param text: any text about the page to include on screen, it will be wrapped in the gui
	 * @param item: the item in question, if any
	 * @param recipe: the recipe for the item in a 3x3 grid  
	 * Note: the recipe is intended for crafting and the grid->is[] layout is
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * if the is[] is less than 9, the system casts it to a 9 itemstack array with
	 * extra slot being empty
	 * if it's more, the extra slots are removed.
	 * if you want a 2x2 recipe to show up correctly, use a array with 5 elements
	 * with is[2]=null
	 */
	public GuidebookPage(String pagetitle, String text, ItemStack item, ItemStack[] recipe)
	{
		this.pagetitle = (pagetitle!=null)?(pagetitle):("");
		this.text = text;
		this.item = item;
		
		if(recipe == null)
			this.recipe = null;
		else
		{
			for(int i = 0; i<this.recipe.length; i++)
			{
				if(i<recipe.length)
					this.recipe[i]=recipe[i];
				else
					this.recipe[i]=null;
			}

			boolean nullcheck = true;
			for(int i = 0; i < this.recipe.length; i++)
			{
				if(this.recipe[i] != null)
					nullcheck = false;
			}
			if(nullcheck)
				this.recipe = null;
		}
	}
	
	public GuidebookPage(String pagetitle, String text)
	{
		this(pagetitle, text, null, null);
	}
	
	public String getPageTitle()
	{
		return pagetitle;
	}
	
	public String getText()
	{
		return text;
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public ItemStack[] getRecipe()
	{
		return recipe;
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