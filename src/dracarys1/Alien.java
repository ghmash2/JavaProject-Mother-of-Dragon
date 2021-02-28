/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract  class Alien extends JPanel  {
	private final int scorePoint = 100;
	bomb B;
	public int deadLine;
	public boolean manIsDown;
	public int x, y, width, height;
	public int moveSpeed;
	
	protected BufferedImage[] frames;
	protected int frameLivingLimit, frameDeadLimit, frameCurrent, frameStart;
	
	public Timer animationTimer;
	  public int life=2,level=1;
	    public int getLife() {
			return life;
		}
		public void setLife(int life) {
			this.life = life;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
	
	public Alien(BufferedImage[] frames,int frameLivingLimit, int x, int y) {
		
		setFrames(frames, frameLivingLimit);
		setX(x);
		setY(y);
		
		
		width = frames[0].getWidth();
		height = frames[0].getHeight();
		
		manIsDown = false;

		deadLine = 10;
		
		
		setPreferredSize(new Dimension(width, height));
		setBounds(x, y, width, height);
		setOpaque(true);
		setBackground(new Color(0, 0, 0, 0));
		
		
		animationTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!GameBoard.isGameOver)
					update();
			}});
		animationTimer.start();
	}
	
	
	
	
	
	public Alien(String filePath, int row, int col, int frameLivingLimit, int x, int y) 
			throws IOException {
		this(SpriteSheetLoader.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}


	public void setFrames(BufferedImage[] frames, int frameLivingLimit) {
		this.frames = frames;
		if(frameLivingLimit > 0)
			this.frameLivingLimit = frameLivingLimit;
	

		this.frameDeadLimit = frames.length;
		this.frameStart = 0;
		this.frameCurrent = frameStart;
	}
	
	public void setY(int y) {
		if(y >= 0)
			this.y = y;
	
	}

	public void setX(int x) {
		if(x >= 0)
			this.x = x;
		
	}
	
	public void setMoveSpeed(int moveSpeed) {
		if(moveSpeed >= 0)
			this.moveSpeed = moveSpeed;
		
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}
	
	
	public abstract void shooting();
	
	
	public Timer getAnimationTimer() {
		return animationTimer;
	}
	
	
	public void move() {
		
		if(!manIsDown){
			if(x > deadLine)
				x -= moveSpeed;
			else {
				x = deadLine;
				GameBoard.isGameOver = true; 
				animationTimer.stop();
			}
		
			setLocation(x, y); 
		}
	}

	
	public boolean isAlive() {
		return (frameCurrent < frameDeadLimit);
	}
	

	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2d = (Graphics2D) g;
		
	
		if(isAlive())
			g2d.drawImage(frames[frameCurrent], 0, 0, null);
		else
			setForeground(new Color(0,0,0,0));
	}
	
	
	public void update() {
		
		move();
		
		
		if((++frameCurrent == frameLivingLimit) && !manIsDown)
			frameCurrent = frameStart;
		else if(manIsDown) 
			frameCurrent = (frameCurrent < frameLivingLimit) ? frameLivingLimit : (frameCurrent + 1);
		
		
		repaint();
	}

	public int getScorePoint() {
		return this.scorePoint;
	}

	//@Override
	/*public void mouseClicked(MouseEvent e) {
		if(GameBoard.isGameOver)
			{return;}
		System.out.println(""+e.getX()+"   "+e.getY());
		Cannon.setFire(true,e.getX(),e.getY());
		
	}*/

	/*@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Game.CURSOR_LOCKED);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Game.CURSOR_UNLOCKED);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(GameBoard.isGameOver)
			return;
		System.out.println(""+e.getX()+"   "+e.getY());
		Cannon.setFire(true,e.getX(),e.getY());
		shooting();
		update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(GameBoard.isGameOver)
			return;
		Cannon.setFire(false,9,9);
	}
*/
	 public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }
}
