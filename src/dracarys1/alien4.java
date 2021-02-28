/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class alien4 extends Alien {
	private final int scorePoint = 550;
int Life = 5,shot_cnt=0;

	public alien4(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(9);
		
	}

	
	public alien4(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	
	
	public void shooting() {
		
		shot_cnt++;
		
		if(shot_cnt>5)
		manIsDown = true;
	}
	public int getScorePoint() {
		return this.scorePoint;
	}
}