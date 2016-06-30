package com.workshopcraft.simplebarrels.tesr;

import java.util.List;

import com.workshopcraft.simplebarrels.render.renderBarrel;
import com.workshopcraft.simplebarrels.tiles.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;

public class TESRBlockBarrel extends TileEntitySpecialRenderer<TileEntityBarrel>{

	 public static Minecraft mc = Minecraft.getMinecraft();
	 private IModel model;
	 private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
	 private final renderBarrel sign = new renderBarrel();
	 
	    
	
	@Override
	public void renderTileEntityAt(TileEntityBarrel te, double x, double y, double z, float partialTicks,
			int destroyStage) {
			//System.out.println("Foo");
			if (te.barrelContents[0] != null)
			{
				//ItemStack i = new ItemStack(te.barrelContents[0].getItem(),te.barrelContents[0].stackSize);
				GlStateManager.pushMatrix();
				
	        // Translate to the location of our tile entity
				 GlStateManager.translate(x,y, z);
				 GlStateManager.disableRescaleNormal();
				 
	        

	        
	      
	        	//System.out.println(te.barrelContents[0].getUnlocalizedName());
	        	//te.barrelContents[0].getItem().getUnlocalizedName();
	        	ItemStack st = te.barrelContents[0];
	        	//System.out.println(te.barrelContents[0].getUnlocalizedName());
	        	//System.out.println(te.barrelContents[0].stackSize);
	        	//ItemStack st = new ItemStack(Blocks.DIRT,1);
	        	//PropertyDirection f = (PropertyDirection) mc.theWorld.getBlockState(new BlockPos(te.getPos().getX(),te.getPos().getY(),te.getPos().getZ()));
	        	//System.out.println(te.getBlockMetadata());
	        	int f = te.getBlockMetadata();
	        	//PropertyDirection f = mc.theWorld.block(new BlockPos(te.getPos().getX(),te.getPos().getY(),te.getPos().getZ()))
	        	this.renderItem(te.getWorld(),te,st,f, partialTicks);
	        	
	        
	        GlStateManager.popMatrix();
			}
	        
		
	}
	
	private void renderCount(int x, int y, int z)
	{
		//ModelRenderer countBoard = new ModelRenderer(this, 0, 0);
		//countBoard.addBox((float)(x-0.3),(float) y,(float)(z-0.3) , 6, 3, 6)
		
	}
	
	private void renderItem(World world,TileEntityBarrel te,ItemStack stack,int f, float partialTicks)
    {
        RenderItem itemRenderer = mc.getRenderItem();
        
        if (stack != null)
        {
        	
        	GlStateManager.pushMatrix();
        	FontRenderer fontrenderer = this.getFontRenderer();
        	
        	String s = ""+te.count;
        	if (f==2)
        	{ 
        		GlStateManager.translate(0.5, 0.4, 0.96);//0.5, 0.5, 0.0 NORTH
        	} else if (f==3)//south
        	{
        		//GlStateManager.translate(0.5, 0.4, 0.04);//south
        		GlStateManager.rotate(180, 0.0F, 1.0F, 0);
        		GlStateManager.translate(-0.5, 0.4, -0.04);//south
        	} else if (f==4)
        	{
        		GlStateManager.translate(0.96, 0.4, 0.5);
        		GlStateManager.rotate(90, 0.04F, 1.0F, 0);
        	} else if (f==5) //east
        	{
        		//GlStateManager.translate(0.0, 0.4, 0.5);
        		//GlStateManager.rotate(90, 0.0F, 1.0F, 0);
        		GlStateManager.rotate(-90, 0.0F, 1.0F, 0);
        		GlStateManager.translate(0.5, 0.4, -0.04);
        		
        	} 
        	float f1 = 0.6666667F;
        	float f3 = 0.015625F * f1;
        	
        	GlStateManager.translate(0.0F, 0.5F * f1, 0.01f * f1);
        	
            GlStateManager.scale(f3, -f3, f3);
            GlStateManager.glNormal3f(0.0F, 0.0F, -1.0F * f3);
            GlStateManager.depthMask(false);	
        	
        	fontrenderer.drawString(s, 0-fontrenderer.getStringWidth(s) / 2, 0, 0);
            GlStateManager.popMatrix();
        	if (f==2)
        	{
        		/*GlStateManager.translate(0.6, 0.16, 0.0f);
            	GlStateManager.rotate(90, 0.0F, 0.0F, 1.0f);
            	GlStateManager.rotate(90, 1.0F, 0.0F, 0.0f);
        		GlStateManager.translate( 0.0, 0.45, -0.6f);
            	
            	this.sign.doRender();
            	GlStateManager.translate(0.0, -0.45, 0.6f);
            	GlStateManager.rotate(90, -1.0F, 0.0F, 0.0f);
            	
            	GlStateManager.rotate(90, 0.0F, 0.0F, -1.0f);
            	GlStateManager.translate(-0.6, -0.2, 0.0f);
            	*/
        		GlStateManager.translate(0.5, 0.4, 0.96);//0.5, 0.5, 0.0 NORTH
        		
        	} else if (f==3)
        	{
        		//stuff
        		/*GlStateManager.translate(0.6, 0.16, 0.0f);
        		GlStateManager.rotate(90, 0.0F, 0.0F, 1.0f);
        		GlStateManager.rotate(90, 1.0F, 0.0F, 0.0f);
        		GlStateManager.translate( 0.0, -0.5, -0.6f);
            	this.sign.doRender();
            	GlStateManager.translate(0.0, 0.5, 0.6f);
            	GlStateManager.rotate(90, -1.0F, 0.0F, 0.0f);
            	GlStateManager.rotate(90, 0.0F, 0.0F, -1.0f);
            	GlStateManager.translate(-0.6, -0.2, 0.0f);
            	*/
        		GlStateManager.translate(0.5, 0.4, 0.04);//south
        		
        	}
        	else if (f==4)
        	{
        		/*GlStateManager.translate(0.6, 0.16, 0.0f);//0.5, 0.5, 0.0 NORTH
            	GlStateManager.rotate(90, 0.0F, 0.0F, 1.0f);
            	GlStateManager.translate(0.0, -0.96, 0.0f);//0.5, 0.5, 0.0 NORTH
            	this.sign.doRender();
            	GlStateManager.translate(0.0, 0.96, 0.0f);//0.5, 0.5, 0.0 NORTH
            	GlStateManager.rotate(90, 0.0F, 0.0F, -1.0f);
            	GlStateManager.translate(-0.6, -0.2, 0.0f);//0.5, 0.5, 0.0 NORTH
            	*/
        		GlStateManager.translate(0.96, 0.4, 0.5);
        		GlStateManager.rotate(90, 0.04F, 1.0F, 0);
        	} else if (f==5)
        	{
        		/*GlStateManager.translate(0.6, 0.16, 0.0f);//0.5, 0.5, 0.0 NORTH
            	GlStateManager.rotate(90, 0.0F, 0.0F, 1.0f);
            	this.sign.doRender();
            	GlStateManager.rotate(90, 0.0F, 0.0F, -1.0f);
            	GlStateManager.translate(-0.6, -0.2, 0.0f);//0.5, 0.5, 0.0 NORTH
            	*/
        		GlStateManager.translate(0.0, 0.4, 0.5);
        		GlStateManager.rotate(90, 0.0F, 1.0F, 0);
        	}
        	
        	
        	
            EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D, stack);
            entityitem.getEntityItem().stackSize = 1;
            entityitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            
            float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);
            //GlStateManager.translate(1.0f, 1.0f, -1.0f);
            GlStateManager.rotate(180f,1.0f,0.0f,0.0f);
            GlStateManager.rotate(180f,0.0f,0.0f,1.0f);
            GlStateManager.scale(0.4F, 0.4F, 0.4F);
            GlStateManager.depthMask(true);
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            
            itemRenderer.renderItem(entityitem.getEntityItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}
