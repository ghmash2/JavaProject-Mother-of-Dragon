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
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Elements extends JPanel {
	 protected int x;
	    protected int y;
	  
		protected double dx,dy;
	    protected int id,i=0;
	    Random rand;
	    public double slop;
	    protected double speed=80;
	    protected int width;
	    protected int height;
	    protected boolean visible,isAlive,isAdd;
	    protected Image image;
	    protected Timer animationTimer;
	    private final int BOARD_WIDTH = 900;
	    
	    public boolean isAlive() {
			return isAlive;
		}
		public void setAlive(boolean isAlive) {
			this.isAlive = isAlive;
		}
		public void setVisible(boolean visible) {
			this.visible = visible;
		}
		private final int bomb_SPEED = 60;
		
		
	    public Elements(int ex, int ey) {
                 
	       
	        setSlop(ex,ey);
	        this.x=0;
	        this.y=0;
	        dx=0.0;
	        dy=390.0;
	        loadImage("/dracarys/images/bomb.png");
	        getImageDimensions();
	        visible = true;
	        isAlive = true;
	        isAdd=false;
	        //this.id=i++;
	        setPreferredSize(new Dimension(width, height));
			setBounds(x, y, width, height);
			
		
			setOpaque(true);
			setBackground(new Color(0, 0, 0, 0));
	        //setOpaque(true);
			//setBackground(new Color(0, 0, 0, 0));
			
			
			
			
	
			animationTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!GameBoard.isGameOver)
					{
						
						update();
						repaint();
					}
						
				}});
			animationTimer.start();
			
	       
	    }
	    public double getSlop() {
			return slop;
		}
		public void setSlop(int xx, int yy) {
			double ey = (double)(410.00-yy);
			double ex=(double) (xx*1.00);
			 
			 double tn= (ey/ex);
			// System.out.println(""+tn+"   "+(ey/ex));
			 
		      slop= Math.atan(tn);
		       //System.out.println(""+slop+"  x"+ex+"  y"+ey);
		        
			
			
		}
		public void setAdd(boolean a) {
	    	isAdd=a;
	    	
	    }
	    public boolean getAdd() {
	    	return isAdd;
	    	
	    }
	    

	    protected void getImageDimensions() {

	        width = image.getWidth(null);
	        height = image.getHeight(null);
	    }

	    protected void loadImage(String imageName) {

	        ImageIcon ii = new ImageIcon(imageName);
	        image = ii.getImage();
	    }
	    
	    
	    public void move() {
	    	//double RandAngl=Math.toRadians(slop);
	    	//System.out.println(""+(speed* Math.cos(slop)));
	    	dx+= (speed* Math.cos(slop));
	    	dy-=(speed* Math.sin(slop));
	      // System.out.println(""+dx+"  "+dy);
	        x = (int) Math.round(dx);
	        y= (int) Math.round(dy);
	        
	        
	        if (x > BOARD_WIDTH)
	            visible = false;
	    }
	    
	 
	    
	    public void update()
	    {
	    	//System.out.println("updating    "+ getX());
	    
	    	move();
	    	repaint();
	    	
	    	
	    }
	    public void paint(Graphics g) {
	    	
			super.paint(g); 
			Graphics2D g2d = (Graphics2D) g;
			
			
			if(Alive())
				g2d.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/dracarys/images/bomb.png")), 100, 100, null);
			else
				setForeground(new Color(0,0,0,0));
		}
		public boolean Alive() {
			return isAlive;
		}

		public Image getImage() {
	        return image;
	    }

	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }

	    public boolean isVisible() {
	        return visible;
	    }
	    

	    public void setVisible(Boolean visible) {
	        this.visible = visible;
	    } 

	    public Rectangle getBounds() {
	        return new Rectangle(x, y, width, height);
	    }
	    public Timer getAnimationTimer() {
			return animationTimer;
		}
	}

