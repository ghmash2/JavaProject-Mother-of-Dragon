/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bat extends Alien {
	private final int scorePoint = 250;
	
	protected int strength, shotCount;
	private int limit;
	public Bat(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit , x, y);
		
		setMoveSpeed(6);
		//limit = frameLivingLimit;
		shotCount = 0;
		
		// Die after 3 shots.
		setStrength(5);
	}
	
	public Bat(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}
	
	
	public void setStrength(int strength) {
		if(strength >= 0)
			this.strength = strength;
	
	}

	@Override
	public void shooting() {
		
		if(++shotCount < strength) {
			frameStart = 0;
			
		}
		else
			manIsDown = true;
		repaint();
	}
	@Override
	public int getScorePoint() {
		return this.scorePoint;
	}
}