package net.phatcode.rel.assets;
/**
 * @author Richard Eric M. Lope (relminator)
 *
 */


import java.net.URL;

import net.phatcode.rel.utils.ImageTextureData;

public class ImageTextureDataDigiFont implements ImageTextureData
{
	
	private String fileName = "gfx/digifont_tiles.bmp";
	
	public URL getUrl()
	{
		return this.getClass().getClassLoader().getResource(fileName);
	}

	@Override
	public int[] getArray()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumImages()
	{
		// TODO Auto-generated method stub
		return 0;
	}




}
