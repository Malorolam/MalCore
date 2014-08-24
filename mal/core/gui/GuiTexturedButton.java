package mal.core.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

public class GuiTexturedButton extends GuiButton{

	private String modname;
	private String imageLocation;
	
	private int u, v;
	public int state;
	private int maxState;
	
	public GuiTexturedButton(int id, int xpos, int ypos, int width, int height, int u, int v, String text, String mod, String location, int maxState, int startingstate) {
		super(id, xpos, ypos, width, height, text);
		
		modname = mod;
		imageLocation = location;
		this.u = u;
		this.v = v;
		state = 0;
		this.maxState = maxState;
		state = startingstate;
	}

	public void cycleState()
	{
		if(state<maxState-1)
			state++;
		else
			state=0;
			
	}
	/**
     * Draws this button to the screen.
     */
	@Override
    public void drawButton(Minecraft mc, int mx, int my)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(new ResourceLocation(modname, imageLocation));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mx >= this.xPosition && my >= this.yPosition && mx < this.xPosition + this.width && my < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.drawTexturedModalRect(this.xPosition*2, this.yPosition*2, u*2, v*2+(height)*2*state, this.width*2+2, this.height*2+1);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            //this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mx, my);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
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