package com.workshopcraft.simplebarrels;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public final class Reference {
	public static final String MOD_ID = "simplebarrels";
	public static final String NAME = "Simple Barrels";
	public static final String VERSION = "1.3.0";
	public static final String TabLebel = MOD_ID+ ".tab_label";
	public static final CreativeTabs CreativeTab = new SimpleBarrelsTab();
	public static class Blocks {
		//public static final ResourceLocation
		public static final ResourceLocation barrel = resource("barrel");
		private Blocks() { }
	}
	
	public static class Items {
		//public static final ResourceLocation
		public static final ResourceLocation itemdolly = resource("itemdolly");
		public static final ResourceLocation itemupgradecomparator = resource("itemupgradecomparator");
		public static final ResourceLocation itemupgradeitemframe = resource("itemupgradeitemframe");
		public static final ResourceLocation itemupgrademark1compressor = resource("itemupgrademark1compressor");
		public static final ResourceLocation itemupgrademark2compressor = resource("itemupgrademark2compressor");
		public static final ResourceLocation itemupgrademark3compressor = resource("itemupgrademark3compressor");
		public static final ResourceLocation itemupgrademark4compressor = resource("itemupgrademark4compressor");
		
		private Items() { }
	}
	
	public static final class TileEntities { 
		//TE's
		
		private TileEntities() { }
		
		private static String tileEntityName(String name) { 
			return "tile." + name;
		}
	}
	
	public static final class NBT {
		//NBT values public static's.
		
		private NBT() { }
	}
	
	private Reference() { }
	
	private static ResourceLocation resource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
