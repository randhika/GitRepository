package com.game.releases.models;

import com.game.releases.R;

public enum Platforms {
	XBOX360("Xbox 360", R.drawable.x360_icon_mini), PS3("Playstation 3",
			R.drawable.ps3_icon_48), WII("Wii", R.drawable.wii_icon_48), PC(
			"Pc Games", R.drawable.pc_icon_48);

	private String name;
	private int icon;
	private int index;

	private Platforms(String name, int icon, int index) {
		this.name = name;
		this.icon = icon;
		this.index = index;
	}

	private Platforms(String name, int icon) {
		this.name = name;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
