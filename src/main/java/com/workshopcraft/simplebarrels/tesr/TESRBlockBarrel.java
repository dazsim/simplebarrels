package com.workshopcraft.simplebarrels.tesr;

import com.workshopcraft.simplebarrels.render.renderBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TESRBlockBarrel extends TileEntitySpecialRenderer<TileEntityBarrel>{

	 public static Minecraft mc = Minecraft.getMinecraft();
	 //private IModel model;
	 //private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
	 private final renderBarrel sign = new renderBarrel();
	 
	    
	
	@Override
	public void render(TileEntityBarrel te, double x, double y, double z, float partialTicks, int destroyStage ,float alpha 
			) {
			//System.out.println("Foo");
			int f = te.getBlockMetadata();
			if (te.itemHandler.extractItem(0, 1, true)!=null)
			
			{
				GlStateManager.pushMatrix();
				// Translate to the location of our tile entity
				GlStateManager.translate(x,y, z);
				GlStateManager.disableRescaleNormal();
				ItemStack st = te.itemHandler.barrelContents;
	        	
	        	this.renderItem(te.getWorld(),te,st,f, partialTicks);
	        	this.renderUpgrades(te,f);
		        
		        GlStateManager.popMatrix();
			} else
			{
				GlStateManager.pushMatrix();
				this.renderUpgrades(te,f);
		        
		        GlStateManager.popMatrix();
			}
			
		
	}
	
	private void renderCount(int x, int y, int z)
	{
		//ModelRenderer countBoard = new ModelRenderer(this, 0, 0);
		//countBoard.addBox((float)(x-0.3),(float) y,(float)(z-0.3) , 6, 3, 6)
		
	}
	private void renderUpgrades(TileEntityBarrel te,int f)
	{
		
        	
    	float f1 = 0.6666667F;
    	float f3 = 0.015625F * f1;
    	GlStateManager.pushMatrix();
    	FontRenderer fontrenderer = this.getFontRenderer();
    	
    	String s = ""+(te.itemHandler.size%4096);
    	
    	if (f==2)
    	{ 
    		
    		GlStateManager.translate(-0.23, (0.5F * f1)-0.4, 0.00+(0.01f * f1));//0.5, 0.5, 0.0 NORTH
    		//System.out.println("north");
    	} else if (f==3)//south
    	{
    		GlStateManager.rotate(180, 0.0F, 1.0F, 0);
    		GlStateManager.translate(-0.24, 0.36+(0.5F * f1)-0.8, (0.01f * f1));//south
    		//System.out.println("south"+te.itemHandler.size);
    	} else if (f==4)
    	{
    		
    		GlStateManager.translate(-0.22, -0.4+(0.5F * f1), 0.04+(0.01f * f1));
    		
    		//System.out.println("west");
    	} else if (f==5) //east
    	{
    		
    		GlStateManager.rotate(-180, 0.0F, 1.0F, 0);
    		GlStateManager.translate(-0.25, (0.2 * f1)-0.2, -0.04+(0.01f * f1));
    		//System.out.println("east");
    	} 
    	
    	
    
    	
        GlStateManager.scale(f3, -f3, f3);
       
    	if (te.itemHandler.size==8192)
    	{
    		
    		fontrenderer.drawString("1", 0, 0, 0xFFFFFF00);
    		
    	} else if (te.itemHandler.size==16384)
    	{
    		fontrenderer.drawString("2", 0, 0, 0xFFFFFF00);
    		
    	} else if (te.itemHandler.size==32768)
    	{
    		fontrenderer.drawString("3", 0, 0, 0xFFFFFF00);
    		
    	} else if (te.itemHandler.size==65536)
    	{
    		fontrenderer.drawString("4", 0, 0, 0xFFFFFF00);
    	} else
    	{
    		
    		fontrenderer.drawString(s, 0, 0, 0xFFFFFF00);
    		
    	}
    	GlStateManager.popMatrix();
	}
	private void renderItem(World world,TileEntityBarrel te,ItemStack stack,int f, float partialTicks)
    {
        RenderItem itemRenderer = mc.getRenderItem();
        
        if (stack != null) 
        {
        	
        	float f1 = 0.6666667F;
        	float f3 = 0.015625F * f1;
        	GlStateManager.pushMatrix();
        	FontRenderer fontrenderer = this.getFontRenderer();
        	
        	String s = ""+te.itemHandler.count;
        	if (f==2)
        	{ 
        		GlStateManager.translate(0.5, 0.4+(0.5F * f1), 0.96+(0.01f * f1));//0.5, 0.5, 0.0 NORTH
        	} else if (f==3)//south
        	{
        		GlStateManager.rotate(180, 0.0F, 1.0F, 0);
        		GlStateManager.translate(-0.5, 0.4+(0.5F * f1), -0.04+(0.01f * f1));//south
        	} else if (f==4)
        	{
        		GlStateManager.translate(0.96, 0.4+(0.5F * f1), 0.5+(0.01f * f1));
        		GlStateManager.rotate(90, 0.0F, 1.0F, 0);
        	} else if (f==5) //east
        	{
        		GlStateManager.rotate(-90, 0.0F, 1.0F, 0);
        		GlStateManager.translate(0.5, 0.4+(0.5F * f1), -0.04+(0.01f * f1));
        		
        	} 
        	
        	
        
        	
            GlStateManager.scale(f3, -f3, f3);
            GlStateManager.glNormal3f(0.0F, 0.0F, -1.0F * f3);
            GlStateManager.depthMask(false);	
        	if (!te.comp)
        	{
        		fontrenderer.drawString(s, 0-fontrenderer.getStringWidth(s) / 2, 0, 0);
        	}
            GlStateManager.popMatrix();
        	if (f==2)
        	{
        		
        		GlStateManager.translate(0.5, 0.4, 0.96);//0.5, 0.5, 0.0 NORTH
        		
        	} else if (f==3)
        	{
        		
        		GlStateManager.translate(0.5, 0.4, 0.04);//south
        		
        	}
        	else if (f==4)
        	{
        		
        		GlStateManager.translate(0.96, 0.4, 0.5);
        		GlStateManager.rotate(90, 0.0F, 1.0F, 0);
        	} else if (f==5)
        	{
        		
        		GlStateManager.translate(0.0, 0.4, 0.5);
        		GlStateManager.rotate(90, 0.0F, 1.0F, 0);
        	}
        	
        	
        	
            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
            //entityitem.getEntityItem().stackSize = 1;
            
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            
            GlStateManager.rotate(180f,1.0f,0.0f,0.0f);
            GlStateManager.rotate(180f,0.0f,0.0f,1.0f);
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
            GlStateManager.depthMask(true);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            
            
            if (!te.frame)
            {
            	itemRenderer.renderItem(entityitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
