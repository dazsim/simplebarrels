package com.workshopcraft.simplebarrels.api;

public class barrelData {
	public String unlocalizedName;
	public String sourceBlock;
	public int sourceMeta;
	public String dependency;
	
	public barrelData(String uName, String sBlock,int sMeta,String dep)
	{
		this.unlocalizedName = uName;
		this.sourceBlock = sBlock;
		this.sourceMeta = sMeta;
		this.dependency = dep;
	}
}
