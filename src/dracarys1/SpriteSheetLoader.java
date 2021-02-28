/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javax.imageio.ImageIO;

public class SpriteSheetLoader {
	
	
	
	public static BufferedImage[] createSprites(String filePath, int row, int col) 
			throws IOException {
		
		
		
		BufferedImage[] sprites = new BufferedImage[row * col];
		
		//System.out.println(""+filePath);
		BufferedImage spriteSheet = ImageIO.read(SpriteSheetLoader.class.getResourceAsStream(filePath)); 
		
		
		int width = spriteSheet.getWidth() / col;
		int height = spriteSheet.getHeight() / row;

	
		for(int i = 0; i < row; ++i)
			for(int j = 0; j < col; ++j)
				sprites[(i * col) + j] = spriteSheet.getSubimage(j * width, i * height, width, height);
		
		return sprites;
	}
}

