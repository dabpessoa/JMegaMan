package net.phatcode.rel.assets;
/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.net.URL;

import net.phatcode.rel.utils.ImageTextureData;

public class ImageTextureDataTiles implements ImageTextureData
{
	
	private String fileName = "gfx/tiles.png";
	
	public URL getUrl()
	{
		return this.getClass().getClassLoader().getResource(fileName);
	}

	@Override
	public int[] getArray()
	{
		return null;
	}

	@Override
	public int getNumImages()
	{
		return 0;
	}


}
