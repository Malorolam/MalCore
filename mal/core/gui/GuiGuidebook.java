package mal.core.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mal.core.guidebook.GuidebookRegistry;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiGuidebook extends GuiScreen{

	private EntityPlayer player;
	private World world;
	private int xSize=256;
	private int ySize=210;
	private int currentPage=0;
	GuidebookRegistry instance = GuidebookRegistry.instance;
	private int maxPages;
	
	private GuiButton prevBtn;
	private GuiButton nextBtn;
	private GuiButton escBtn;
	
    /**
     * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiLeft;
    /**
     * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
     */
    protected int guiTop;
	
	public GuiGuidebook(EntityPlayer player, World world)
	{
		this.player = player;
		this.world = world;
		maxPages = instance.getPageCount()-1;
	}
	
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(this.prevBtn = new GuiButton(0, this.width / 2 -124, this.height / 2 +80, 28, 20, "Prev"));
		this.buttonList.add(this.nextBtn = new GuiButton(1, this.width / 2 + 94, this.height / 2 +80, 28, 20, "Next"));
		this.buttonList.add(this.escBtn = new GuiButton(2, this.width / 2 + 103, this.height / 2 -100, 20, 20, "X"));
		
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
	}
	
	/**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        switch (par1GuiButton.id)
        {
        case 0:
        	if(currentPage>0)
        		currentPage--;
        	else
        		currentPage=maxPages;
        	break;
        case 1:
        	if(currentPage<maxPages)
        		currentPage++;
        	else
        		currentPage=0;
        	break;
        case 2:
			player.closeScreen();
        	break;
        }
    }
	
	/**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(boolean item, boolean recipe, boolean uppertext)
    {
    	//put text stuff here later

        this.fontRendererObj.drawString(currentPage+"/"+maxPages, this.width/2-30, this.height/2+86, 4210752);
        
        //draw the page name if there is one
        String name = instance.getPage(currentPage).getPageTitle();
        if(name != null && name != "")
        	this.fontRendererObj.drawString(name, this.width/2-124, this.height/2-100, 4210752);
        
		GL11.glScalef(0.5f, 0.5f, 0.5f);
        //draw the upper page text if there is any
        int width = (recipe&&item)?(230):((recipe||item)?(300):(450));
        String upperText = instance.getPage(currentPage).getUpperText();
        if(upperText != null && upperText != "")
        	this.fontRendererObj.drawSplitString(upperText, guiLeft*2+10, guiTop*2+30, width, 4210752);
        
        //draw the lower page text if there is any
    	int l = (recipe||item||uppertext)?(160):(60);
        String text = instance.getPage(currentPage).getLowerText();
        if(text != null && text != "")
        	this.fontRendererObj.drawSplitString(text, guiLeft*2+10, guiTop*2+l, 450, 4210752);
		GL11.glScalef(2.0f, 2.0f, 2.0f);
		
    }
    
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(boolean item, boolean recipe, boolean uppertext)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation("malcore", "textures/gui/guidebook.png"));
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
        
    	int l = (recipe)?(150):(115);
    	int offset = (uppertext)?(50):(0);
        if(item)
            this.drawTexturedModalRect(guiLeft+l+offset, guiTop+35, 0, 210, 18, 18);
        if(recipe)
        {
        	this.drawTexturedModalRect(guiLeft+126+offset, guiTop+36, 0, 228, 22, 18);
        	
        	this.drawTexturedModalRect(guiLeft+71+offset, guiTop+17, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+89+offset, guiTop+17, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+107+offset, guiTop+17, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+71+offset, guiTop+35, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+89+offset, guiTop+35, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+107+offset, guiTop+35, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+71+offset, guiTop+53, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+89+offset, guiTop+53, 0, 210, 18, 18);
        	this.drawTexturedModalRect(guiLeft+107+offset, guiTop+53, 0, 210, 18, 18);
        }
    }
    
    private void drawItems(boolean item, boolean r, boolean uppertext)
    {
        ItemStack is = instance.getPage(currentPage).getItem();
        //RenderHelper.disableStandardItemLighting();
        GL11.glEnable(GL11.GL_LIGHTING);
        
    	int offset = (uppertext)?(50):(0);
    	
        if(is != null)
        {
        	int l = (r)?(151):(116);
        	itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), is, guiLeft+l+offset, guiTop+36);
        	itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), is, guiLeft+l+offset, guiTop+36);
        }
        
        ItemStack[] recipe = instance.getPage(currentPage).getRecipe();
        
        if(recipe != null && recipe.length==9)
        {
        	int baseLeft = guiLeft+72+offset;
        	int baseTop = guiTop+18;
        	
        	for(int i = 0; i<3; i++)
	        	for(int j = 0; j<3; j++)
	        	{
	        		ItemStack ii = recipe[i+j*3];
	        		if(ii != null)
	        		{
	        			drawItemStack(ii, baseLeft+i*18, baseTop+j*18, null);
	        		}
	        	}
        }
    }
    
    private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
    {
        //GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_, p_146982_4_);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
    
    @Override
    public void drawScreen(int i, int j, float f)
    {
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    	boolean item = instance.getPage(currentPage).getItem()!=null;
    	boolean recipe = instance.getPage(currentPage).getRecipe()!=null;
    	boolean upperText = instance.getPage(currentPage).getUpperText()!=null && instance.getPage(currentPage).getUpperText()!="";
    	
    	drawGuiContainerBackgroundLayer(item, recipe, upperText);
    	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    	super.drawScreen(i, j, f);
    	RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        //GL11.glTranslatef((float)guiLeft, (float)guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        short short1 = 240;
        short short2 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
    	drawGuiContainerForegroundLayer(item, recipe, upperText);
        GL11.glEnable(GL11.GL_LIGHTING);
        drawItems(item, recipe, upperText);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
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