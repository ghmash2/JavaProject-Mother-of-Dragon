
package dracarys1;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


import dracarys1.Game.GameState;






public class GameBoard extends JPanel implements MouseListener, MouseWheelListener{
	
	private String fileBackground = "/dracarys/images/background.png";
	private final String fileBackground1 = "/dracarys/images/background1.png";
	private int bomb_num;
	private Generator generator;
	public static boolean isGameOver = false;
	public static int score;
	public List<Elements> bb;
	public List<Alien> aa;
	public boolean isStopped;
	public static int level;
	private AudioClip gunshot;
	int i;
	
public BufferedImage[] bombs;

	public  int getLevel() {
	return level;
}

public  void setLevel(int level) {
	level = level;
}

	private Genie genie;
	private JLabel lblScore;
	private JPanel headerPanel;
	private JButton restartButton, stopButton;
	private Timer mainTimer;
	private Font defaultFont;
	private JCheckBox effectOn;
	
	public GameBoard() throws IOException {
		isStopped = false;
		bombs=SpriteSheetLoader.createSprites("/dracarys/images/bombSprite.png", 8, 12);
		generator = new Generator();
		isGameOver = false; 
		if(Game.level==0)
		{
		score = 0;
		}
		else
		{
			score= Game.level*10000;
			
		}
		if(Game.level%2==0)
		{
			fileBackground= "/dracarys/images/background1.png";
		}
		/*else if(Game.level%2==1)
		{
			fileBackground= "/16.png";
		}*/
		
		defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
		bomb_num=0;
		setEffectOn();
		URL clipUrl = getClass().getResource("/dracarys/images/laser.wav");
		gunshot = Applet.newAudioClip(clipUrl);
		bb = new ArrayList<>();
		aa= new ArrayList<>();
		
		setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
		setLayout(null);
		
		addMouseWheelListener(this);
		setHeader();
		setGenie();
		addMouseListener(this);
	}
	
	private void setEffectOn() {
		effectOn = new JCheckBox("Sound Effects    ", true);
		effectOn.setBackground(new Color(0, 0, 0, 0)); 
		effectOn.setFont(defaultFont);
		effectOn.setFocusable(false);
	}
	
	private void setHeader() {
		JLabel scoreMsg = new JLabel("Your Score: ");
		scoreMsg.setForeground(Color.BLACK);
		scoreMsg.setFont(defaultFont);
		
		lblScore = new JLabel(Integer.toString(score));
		lblScore.setForeground(Color.BLACK);
		lblScore.setFont(defaultFont);
		
		headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		headerPanel.setBounds(0, 0, Game.WIDTH, 50);
		headerPanel.setOpaque(true);
		headerPanel.setBackground(new Color(0, 0, 0, 0));
		
		headerPanel.add(effectOn);
		headerPanel.add(scoreMsg);
		headerPanel.add(lblScore);
		
		BufferedImage[] buttonIcons = new BufferedImage[2];
		try {
			buttonIcons[0] = ImageIO.read(GameBoard.class.getResourceAsStream("/dracarys/images/Button4.png"));
			buttonIcons[1] = ImageIO.read(GameBoard.class.getResourceAsStream("/dracarys/images/Button3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon[] icons = new ImageIcon[buttonIcons.length];
		for(int i = 0; i < buttonIcons.length; ++i)
			icons[i] = new ImageIcon(buttonIcons[i]);
		restartButton = createButton(icons[1]);
		stopButton = createButton(icons[0]);
		headerPanel.add(restartButton);
		headerPanel.add(stopButton);
		//add(MouseWheelListener);
		add(headerPanel);
		headerPanel.setVisible(true);
	}
	
	
	private JButton createButton(Icon icon0) {


		JButton button = new JButton(icon0);
		button.setPreferredSize(new Dimension(icon0.getIconWidth(), icon0.getIconHeight()));
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.setFocusPainted(false);
		button.setRolloverEnabled(false);
		//button.setRolloverIcon(icon1);
		//button.setPressedIcon(icon2);
		button.setContentAreaFilled(false);
		button.addActionListener(new InnerButtonListener());
		
		return button;
	}
	
	private void setGenie() {
		genie = new Genie();
		add(genie);
		
		addMouseMotionListener(new MouseMotionListener() {		
			@Override
			public void mouseMoved(MouseEvent e) {
				genie.rotate(e.getX(), e.getY(), false);
			}		
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Genie.setFire(true,10 , 20);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				//Genie.setFire(true,10, 20);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Genie.setFire(false,10,20);
			}		
		});
	}
	
	public Thread generator() {
		
		for(int i = 0; i < 5; ++i)
			add(generator.generateNewEnemy());
		return new Thread(new Runnable() {	

			@Override
			public void run() {
				int s = 1000;
				while(!isGameOver && !isStopped) {
					try {
						Thread.sleep(s);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(isStopped)
						return;
					if(!isGameOver)
						add(generator.generateNewEnemy());
					if(s > Game.REFRESH_TIME + 50)
					{
						s -=4;
					}
					else
						{
						s = Game.REFRESH_TIME + 50;
						//s=200;
						}
				}
			}});
	}
	
	
	public void addBomb()
	{
		for(i=0;i<Genie.arrow.size();i++)
		{
			bomb b= Genie.arrow.get(i);
			
			add(b);
			
		}
		for(i=0;i<Genie.arrow.size();i++)
		{
			Genie.arrow.remove(i);
		}
		
	}
	
	public void gameLoop() {
		generator().start();
		addBomb();
		mainTimer = new Timer(10, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBomb();
				repaint();
			
				if(isGameOver) {
					gameOver();
					return;
				}
				
				
				for(Component e : getComponents()) {
					if(e instanceof Alien){
						Alien i = (Alien) e;
						
						if(!i.isAlive()) {
							
							score += i.getScorePoint();
							
							lblScore.setText(Integer.toString(score));
							
							remove(e);
							if(score==(10000+(10000*Game.level)))
							{
								LevelUp();
								break;
							}
							
						}
						else
						{
							aa.add(i);
						}
					}
					else if(e instanceof Elements)
					{
						Elements i = (Elements) e;
						bb.add(i);
						
						//g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground)), 0, 0, null);
						//System.err.println("yes yes....." +e.getX());
					}
				}
				
				int k,kk;
				for(k=0;k<aa.size();k++)
				{
					for(kk=0;kk<bb.size();kk++)
					{
						Rectangle RectB= bb.get(kk).getBounds();
						//Rectangle r= new Rectangle(30, 30, 40, 40);
						Rectangle alienrect= aa.get(k).getBounds();
					
						//Rectangle rr= new Rectangle(40, 40, 40, 40);
						//System.err.println(""+rr.x+"  "+rr.y);
						//System.err.println(""+r.x+"  "+r.y);
						//System.out.println(""+rr.intersects(r));
						//System.out.println(""+r.x +"  "+r.y);
						//System.out.println(""+r.intersects(alienrect));
						
						/*
						 * if (alienrect.X1 < RectB.X2 && alienrect.X2 > RectB.X1 &&
    alienrect.Y1 < RectB.Y2 && alienrect.Y2 > RectB.Y1)
						 */
						//x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
						if (alienrect.x < RectB.x+RectB.width && alienrect.x+alienrect.width > RectB.x &&  alienrect.y< RectB.y+RectB.height && alienrect.y +alienrect.height> RectB.y)
						{
							
							bb.get(kk).setAlive(false);
							remove(bb.get(kk));
							bb.remove(kk);
							aa.get(k).shooting();
							aa.remove(k);
							break;
							
						}
					}
				}
				aa.clear();
				bb.clear();
			}
		});
		mainTimer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground)), 0, 0, null);
		
		for(Component e : getComponents()) {
			if(e instanceof bomb){
				bomb i = (bomb) e;
				if(i.Alive()) {
					if(i.bomb_num==0)
					{
					g.drawImage(bombs[86], i.getX(), i.getY(), null);
					}
					else if(i.bomb_num==1)
					{
						g.drawImage(bombs[26], i.getX(), i.getY(), null);
					}
					else if(i.bomb_num==1)
					{
						g.drawImage(bombs[47], i.getX(), i.getY(), null);
					}
					else
					{
						g.drawImage(bombs[92], i.getX(), i.getY(), null);
					}
				}
			}
		}
	}
	public void addBomb(bomb b)
	{
		add(b);
	}
	
	public void gameOver() {
		mainTimer.stop();
		for(Component c : getComponents()) {
			if(c instanceof Alien)
			{
				((Alien) c).getAnimationTimer().stop();
			remove(c);
			}
			else if(c instanceof Elements)
			{

				((Elements) c).getAnimationTimer().stop();
			remove(c);
			}
		}
		
		int selectedOption = JOptionPane.showConfirmDialog(this,
				("Your Score: " + score + "\nDo you want to play a new Game?"),
				"GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		
		//Game.setHighScore("name",score);
		
		switch (selectedOption) {
		case JOptionPane.YES_OPTION:
			Game.setState(GameState.CONTINUE);
			break;

		case JOptionPane.NO_OPTION:
		{Game.level=0;
			Game.setHighScore(score);
			//Game.setState(GameState.OVER);
			//Game.setState(GameState.OVER);
			break;
		}
		}
	}
	void LevelUp()
	{
		isGameOver=true;
		mainTimer.stop();
		
		for(Component c : getComponents()) {
			if(c instanceof Alien)
			{
				((Alien) c).getAnimationTimer().stop();
				
			remove(c);
			}
			else if(c instanceof Elements)
			{

				((Elements) c).getAnimationTimer().stop();
			remove(c);
			}
		}
		
			Game.level+=1;
			int selectedOption = JOptionPane.showConfirmDialog(this,
					("Your Score: " + score + "\nDo you want to play next level ?"),
					"GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			//Game.setHighScore("name" ,score);
			switch (selectedOption) {
			case JOptionPane.YES_OPTION:
				Game.setState(GameState.CONTINUE);
				break;

			case JOptionPane.NO_OPTION:
			{Game.level=0;
				Game.setHighScore(score);
				//Game.setState(GameState.OVER);
				//Game.setState(GameState.OVER);
				break;
			}
			}
			
		
		
		
		
		
		
		}
	
	
	private class InnerButtonListener implements ActionListener {
		public void gameStop() {
			isStopped = true;
			mainTimer.stop();
			for(Component c : getComponents()) {
				if(c instanceof Alien)
					((Alien) c).getAnimationTimer().stop();
				remove(c);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == restartButton) {
				gameStop();
				Game.setState(GameState.CONTINUE);
			}
			else if(e.getSource() == stopButton) {
				gameStop();
				Game.setState(GameState.OVER);
			}
			
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		genie.setFire(true, e.getX(), e.getY());
		//System.out.println("clicked in mlis");
		Genie.arrow.add(new bomb(e.getX(),e.getY(),bomb_num));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		genie.setFire(true, e.getX(), e.getY());
		Genie.arrow.add(new bomb(e.getX(),e.getY(),bomb_num));
		if(effectOn.isSelected())
			gunshot.play();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		genie.setFire(false, 1000, 2000);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		bomb_num= (bomb_num+1)%4;
	}
}
