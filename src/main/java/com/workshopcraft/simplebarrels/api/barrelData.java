package com.workshopcraft.simplebarrels.api;

public class barrelData {
	public String unlocalizedName;
	public String sourceBlock;
	public int sourceMeta;
	
	public barrelData(String uName, String sBlock,int sMeta)
	{
		this.unlocalizedName = uName;
		this.sourceBlock = sBlock;
		this.sourceMeta = sMeta;
	}
}
