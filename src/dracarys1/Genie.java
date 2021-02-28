/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dracarys1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Genie extends JPanel {
	
	private final String fileGenieImage = "/dracarys/images/hero1.png",
			fileGenieFireImage = "/dracarys/images/hero2.png",
			fileBackGround = "/dracarys/images/geniebg.png";
	private int width = 110, height = 120;	
	private Image genieImage, genieFireImage;
	private int positionY, imagePosY;
	private double angle;
	private static boolean fire;
	private boolean isAlive=true;
	public static List<bomb> arrow;
	
	
	public Genie() {	
		   arrow = new ArrayList<>();
		positionY = 330 ;
		if(Game.level%2==1)
		{
			positionY = 345 ;
			
		}
		setBounds(0, positionY, width, height);
		setBackground(Color.white); 
		//setOpaque(true);
		//setBackground(new Color(0, 0, 0, 0 )); 
		setFire(false,0,0);
		setImages();
	}
	private void setImages() {
		genieImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/dracarys/images/hero1.png"));
		genieFireImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/dracarys/images/hero2.png"));
		
		//imagePosY = (height / 2) - (genieImage.getHeight(null) / 2) - 15;
		//System.out.println(""+imagePosY);
		imagePosY=20;
		angle = 0;
	}
	public static void setFire(boolean f,int x,int y) {
		fire = f;
		if(fire)
		{
		//arrow.add(new bomb(x,y));
		//System.err.println(""+"clicked");
		}
	}
	public void rotate(int x, int y, boolean fire) {
		setFire(fire,0,0);
		angle = Math.atan2(y - (positionY + imagePosY), x);
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(angle, 15, imagePosY + 60);
		if(!fire) 	
			g2d.drawImage(genieImage, 0, imagePosY, null);
		else
			g2d.drawImage(genieFireImage, 0, imagePosY, null);
	}
	//@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(Game.level%2==0)
		g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/dracarys/images/geniebg.png")), 0, 0, null);
		
	}
}
