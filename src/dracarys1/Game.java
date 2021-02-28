package dracarys1;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import dracarys1.Game.GameState;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Game extends JFrame implements ActionListener {

    public static List<ScoreData> Scores = new ArrayList<ScoreData>();
    protected final static int WIDTH = 900, HEIGHT = 500;
    public final static int REFRESH_TIME = 150;
	public static int level; 
        public static int highScore=0;

   
    private Graphics g;
    private String fileBackground = "/dracarys/images/gamebg.png";
    static File file = new File("H:\\Java project\\Mother of Dragons\\res\\score.txt");
   
    private Font defaultFont;
    private JPanel menuPanel, highScorePanel;
    private JButton playButton, quitButton, highScoreButton,
            backButton0, backButton1;
    private JTextField txtHighScore;

    public enum GameState {
        NEW, CONTINUE, OVER, HIGHSCORE, WAIT, QUIT
    };
    public static GameState state;

    public Game() throws HeadlessException, IOException {
        this("MOTHER OF DRAGON");
    }

    public Game(String title) throws HeadlessException, IOException {
        super(title);
        //System.out.println("1");
        level=0;
        Canvas canvas= new Canvas();
        
        loadHighScore();
        setBackButtons();

        
        defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
        //System.out.println("2");
        setMenuPanel();

        setHighScorePanel();

        setLayout(null);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setBackground(Color.WHITE);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(true);
       
        setVisible(true);
        
        pack();
       

    }

   
    private void setBackButtons() {
        backButton0 = createButton(new String[]{"/dracarys/images/back.png",
            "/dracarys/images/back.png",
            "/dracarys/images/back.png"});
        backButton1 = createButton(new String[]{"/dracarys/images/back.png",
            "/dracarys/images/back.png",
            "/dracarys/images/back.png"});
    }

    public void loadHighScore() throws IOException {
		
		
			BufferedReader reader = new BufferedReader(new FileReader(file));
		//	BufferedReader reader1 = new BufferedReader(new FileReader(file1));
			//int i=0;
			String line;
		   // BufferedReader reader = new BufferedReader(new FileReader(filePath));
		    while ((line = reader.readLine()) != null)
		    {
		        String[] parts = line.split(":", 2);
		        if (parts.length >= 2)
		        {
		            String key = parts[0];
		            String value = parts[1];
		            ScoreData ob= new ScoreData(parts[0], Integer.parseInt(parts[1]));
		            Scores.add(ob);
		          
		        } else {
		            System.out.println("ignoring line: " + line);
		        }
		    }
			Collections.sort(Scores, new Comparator<ScoreData>() {

				@Override
				public int compare(ScoreData o1, ScoreData o2) {
					
					if(o1.score>=o2.score)
					{
						return -1;
					}
					else
					{
						return 1;
					}
				}
				
			});
			//ScoreData ob = new ScoreData();
		//	Integer[] arr= new Integer[5];
			//String line = reader.readLine();
			
		
			reader.close();
			//reader1.close();
			//setHighScore("rick", 89);
			
	}

    public static void setState(GameState state) {
        Game.state = state;
    }

    public static void setHighScore( Integer score) {
		if(Scores.get(4).score<score)
		{
		JFrame ob= new JFrame("New Highscore...!");
		JPanel pan= new JPanel();
		JTextField text= new JTextField(10);
		text.setBounds(170, 20, 100, 40);
		JLabel Level= new JLabel("Enter your name: ");
		Level.setBounds(40, 20, 100, 40);
		JButton B= new JButton("Submit");
		B.setBounds(180, 280 , 30, 20 );
		B.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name= text.getText();
				ScoreData ob1= new ScoreData(name, score);
				
				Scores.add(ob1);
				Collections.sort(Scores, new Comparator<ScoreData>() {
					
					@Override
					public int compare(ScoreData o1, ScoreData o2) {
						
						if(o1.score>=o2.score)
						{
							return -1;
						}
						else
						{
							return 1;
						}
					}
					
				});
				if(Scores.size()>=7)
				{
				    Scores.remove(6);
				    
				}
				
				if(score > highScore)
				{
					highScore = score;
				}
				try {
					saveHighScore();
					
				} catch (SecurityException ex) {
					
					ex.printStackTrace();
				} catch (IOException ex) {
			
					ex.printStackTrace();
				}
				ob.setVisible(false);
				
				ob.dispose();
				
				Game.level=0;
				
			}
		});
		pan.add(Level);
		pan.add(text);
		pan.add(B);
		pan.setBackground(Color.CYAN);
		ob.add(pan);
		ob.setSize(300,150);
		ob.setBackground(Color.CYAN);
		ob.setVisible(true);
		ob.setResizable(false);
		
		}
		
		setState(GameState.OVER);
	}
	
	public static int getHighScore() {
		return highScore;
	}

	public static void saveHighScore() throws IOException, SecurityException {
		//System.out.println("inside hs "+getHighScore());
		//File file = new File("/score.txt");
		
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		//BufferedWriter writer1 = new BufferedWriter(new FileWriter(file1));
		int i=0;
		while(i<=5 && i<Scores.size())
		{
			writer.write(Scores.get(i).name+":"+Integer.toString(Scores.get(i).score)+System.lineSeparator());
			//writer1.write(Scores.get(i).name);
			i=i+1;
		}
		//writer.write(Integer.toString(getHighScore()));
		writer.close();
		
		//writer1.close();
	}
	
	


    public final void setMenuPanel() {
        //System.out.println("4");
        menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        menuPanel.setBounds(0, 0, WIDTH, HEIGHT);
        menuPanel.setBackground(Color.WHITE);
       

        JPanel innerPanel = new JPanel(new GridLayout(3, 1, 1, 2));
        innerPanel.setPreferredSize(new Dimension(350, 440));
        innerPanel.setBackground(Color.WHITE);
        //System.out.println("6");

        playButton = createButton(new String[]{"/dracarys/images/play.png",
            "/dracarys/images/play.png",
            "/dracarys/images/play.png"});
        //System.out.println("7");

        highScoreButton = createButton(new String[]{"/dracarys/images/score.png",
            "/dracarys/images/score.png",
            "/dracarys/images/score.png"});

        quitButton = createButton(new String[]{"/dracarys/images/quit.png",
            "/dracarys/images/quit.png",
            "/dracarys/images/quit.png"});
        innerPanel.add(playButton);
        innerPanel.add(highScoreButton);

        innerPanel.add(quitButton);

        menuPanel.add(innerPanel);

    }

    public void setHighScorePanel() {
        highScorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        highScorePanel.setBounds(0, 0, WIDTH, HEIGHT);
        highScorePanel.setBackground(Color.WHITE);
      
        JPanel innerPanel = new JPanel(new GridLayout(7, 1, 2, 2));
        innerPanel.setPreferredSize(new Dimension(800, 220));
        innerPanel.setBackground(Color.WHITE);

        JLabel lblMessage = new JLabel("High Score", JLabel.CENTER);
        lblMessage.setForeground(Color.BLACK);
        lblMessage.setFont(defaultFont);
        lblMessage.setHorizontalTextPosition(JLabel.CENTER);
        innerPanel.add(lblMessage, BorderLayout.NORTH);

        txtHighScore = new JTextField("1. " + Scores.get(0).name + "  " + Integer.toString(Scores.get(0).score));
        txtHighScore.setFont(defaultFont);
        txtHighScore.setHorizontalAlignment(JTextField.CENTER);
        txtHighScore.setEditable(false);
        innerPanel.add(txtHighScore, BorderLayout.CENTER);

        txtHighScore = new JTextField("2. " + Scores.get(1).name + "  " + Integer.toString(Scores.get(1).score));
        txtHighScore.setFont(defaultFont);
        txtHighScore.setHorizontalAlignment(JTextField.CENTER);
        txtHighScore.setEditable(false);

        innerPanel.add(txtHighScore, BorderLayout.CENTER);

        txtHighScore = new JTextField("3. " + Scores.get(2).name + "  " + Integer.toString(Scores.get(2).score));
        txtHighScore.setFont(defaultFont);
        txtHighScore.setHorizontalAlignment(JTextField.CENTER);
        txtHighScore.setEditable(false);
        
        innerPanel.add(txtHighScore, BorderLayout.CENTER); 
        
        innerPanel.add(txtHighScore, BorderLayout.CENTER);
        innerPanel.add(txtHighScore, BorderLayout.CENTER);

        highScorePanel.add(innerPanel);
        highScorePanel.add(backButton0);
    }

    // Helper function to create a button.
    // Gets 3 file paths to creates 3 icons.
    // icon[0]: button icon
    // icon[1]: roll over icon.
    // icon[2]: pressed icon.
   private JButton createButton(String[] iconFiles) {
		ImageIcon[] icon = new ImageIcon[iconFiles.length];
		for(int i = 0; i < iconFiles.length; ++i)
			icon[i] = new ImageIcon(getClass().getResource(iconFiles[i]));

		JButton button = new JButton(icon[0]);
		button.setBorderPainted(false);
		button.setFocusable(true);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(icon[1]);
		button.setPressedIcon(icon[2]);
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		
		return button;
	}

    public void startGame() throws IOException {
		
		
		Thread t = new Thread(new Runnable() {		
			@Override
			public void run() {
				
				setState(GameState.WAIT);
				add(menuPanel);
				GameBoard board = null;
				
				
				while(!state.equals(GameState.QUIT)) {
					switch (state) {
					
					case WAIT:
						try {
							Thread.sleep(200);
						} catch (Exception e) {
e.printStackTrace();
						}
						break;
					
					case NEW:
						remove(menuPanel);
						repaint();
						try {
							board = new GameBoard();
						} catch (Exception e) {
							e.printStackTrace();
						}
						add(board);
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
					
					case CONTINUE:
						try {
							saveHighScore();
						} catch (Exception e) {
							e.printStackTrace();
						}
						remove(board);
						repaint();
						try {
							board = new GameBoard();
						} catch (Exception e) {
							e.printStackTrace();
						}
						add(board);
						repaint();
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
					
					case OVER:
						try {
							saveHighScore();
						} catch (Exception e) {
							e.printStackTrace();
						}
						remove(board);
						repaint();
						add(menuPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					
					case HIGHSCORE:
						setHighScorePanel();
						//txtHighScore.setText(Integer.toString(getHighScore()));
						remove(menuPanel);
						repaint();
						add(highScorePanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					
					
					case QUIT:
						break;
					}				}
				
				dispose();
				System.exit(0);
			}
		});
		t.start();
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(playButton))
			setState(GameState.NEW);
		
		else if(e.getSource().equals(highScoreButton))
			setState(GameState.HIGHSCORE);
		
		else if(e.getSource().equals(backButton0) || e.getSource().equals(backButton1)) {
			remove(((JButton) e.getSource()).getParent());
			repaint();
			add(menuPanel);
			pack();
			setState(GameState.WAIT);
		}
		
		else if(e.getSource().equals(quitButton))
			setState(GameState.QUIT);
		
		
	}
	
	//@Override
	/*	public void paintComponents(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponents(g);
			g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground)), 0, 0, null);
		}*/
}