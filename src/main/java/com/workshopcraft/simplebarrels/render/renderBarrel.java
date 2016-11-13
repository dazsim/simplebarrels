package com.workshopcraft.simplebarrels.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class renderBarrel extends ModelBase{
	
	public ModelRenderer barrelSign = new ModelRenderer(this, 0, 0);
	
	public renderBarrel()
	{
		this.barrelSign.addBox(6, 2, 6, 4, 16, 4);
	}
	
	public void doRender()
	{
		barrelSign.render(0.1725F);
	}
}
