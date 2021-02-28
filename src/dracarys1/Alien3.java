/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Alien3 extends Alien {

	public Alien3(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(7);
	}

	
	public Alien3(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	
	@Override
	public void shooting() {
		
		manIsDown = true;
	}

}