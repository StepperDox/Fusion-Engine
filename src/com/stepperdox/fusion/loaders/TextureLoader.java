package com.stepperdox.fusion.loaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import com.stepperdox.fusion.data.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

/**
*  @author StepperDox
*  Created on Oct 28, 2019
*/

public class TextureLoader {
	private static List<Integer> textures = new ArrayList<Integer>();
	
	public static boolean af = true;
	public static float levels = 4f;
	
	public static Texture loadTexture(String texture){
		Texture ret = null;
		org.newdawn.slick.opengl.Texture tex = null;
		try {
			tex = org.newdawn.slick.opengl.TextureLoader.getTexture("PNG", new FileInputStream("res/test/textures/" + texture + ".png"));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
			if(af && GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic){
				float level = Math.min(levels, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
				GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, level);
			}else if(!af){
				System.out.println("Anisotropic Filtering is not enabled.");
			}else{
				System.err.println("Anisotropic Filtering is not supported.");
			}
		}catch (IOException e) {
			System.err.println("Ran into an issue while loading a texture! Returning null.");
			System.err.println(e.getLocalizedMessage());
			return null;
		}
		int tID = tex.getTextureID();
		textures.add(tID);
		ret = constructTexture(texture);
		ret.setTexID(tID);
		return ret;
	}
	
	private static Texture constructTexture(String fileName) {
		int width = 0;
		int height = 0;
		ByteBuffer buffer = null;
		try {
			FileInputStream in = new FileInputStream("res/test/textures/" + fileName + ".png");
			PNGDecoder decoder = new PNGDecoder(in);
			width = decoder.getWidth();
			height = decoder.getHeight();
			buffer = ByteBuffer.allocateDirect(4 * width * height);
			decoder.decode(buffer, width * 4, Format.RGBA);
			buffer.flip();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Failed to construct the texture: " + fileName);
			return null;
		}
		return new Texture(buffer, width, height);
	}
	
	public static void callExit(){
		for(int i:textures){
			GL11.glDeleteTextures(i);
		}
		System.out.println("Removed all textures from memory.");
	}
}
