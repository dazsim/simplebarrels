package com.workshopcraft.simplebarrels.api;

import com.google.gson.JsonObject;

public class BarrelData {
	private String unlocalizedName;
	private String resourceDomain;
	private String resourcePath;
	private int resourcePathIndex;

	BarrelData(String modId, String domain, JsonObject data)
	{
		resourceDomain = domain;
		resourcePath = data.get("resource_path").getAsString();
		unlocalizedName = (modId + data.get("wood_name").getAsString() + "barrel").toLowerCase();
		resourcePathIndex = data.get("resource_index").getAsInt();
	}

	String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	String getFullResourcePath() {
		return resourceDomain + ":" + resourcePath;
	}

	int getResourceIndex() {
		return resourcePathIndex;
	}
}
