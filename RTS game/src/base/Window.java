package base;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;

public class Window {
	private JFrame frame;
	private JPanel panel;
	private int width, height;
	private String title;
	
	private boolean gameReady;
	
	private Game game;
	
	public Window(String title, int w, int h) {
		this.title = title;
		this.width = w;
		this.height = h;
		
		this.frame = new JFrame(title);
		this.panel = new JPanel();
			
			this.frame.add(this.panel);
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Test test(Entity[][] gam, Material[][] grd) {
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Test newGame = new Test(this.width, this.height, gam, grd);
		JFrame a = this.frame;
		a.add(newGame);
		a.addKeyListener(newGame);
//		a.addMouseListener(newGame);
//		a.addMouseMotionListener(newGame);
//		a.addMouseWheelListener(newGame);
		
		a.pack();
		a.setLocationRelativeTo(null);
		
		this.gameReady = true;
		
		this.game = newGame;
		
		return newGame;
	}
	
	public Game newGame() {
		Game newGame = new Game(this.width, this.height);
		JFrame a = this.frame;
		a.add(newGame);
		a.addKeyListener(newGame);
//		a.addMouseListener(newGame);
//		a.addMouseMotionListener(newGame);
//		a.addMouseWheelListener(newGame);
		
		a.pack();
		a.setLocationRelativeTo(null);
		
		this.gameReady = true;
		
		this.game = newGame;
		
		return newGame;
	}
	
	public MapEditor newMapEditor() {
		MapEditor newGame = new MapEditor(this.width, this.height);
		JFrame a = this.frame;
		a.add(newGame);
		a.addKeyListener(newGame);
//		a.addMouseListener(newGame);
//		a.addMouseMotionListener(newGame);
//		a.addMouseWheelListener(newGame);
		
		newGame.addMenu(this);
		
		a.pack();
		a.setLocationRelativeTo(null);
		
		this.gameReady = true;
		
		this.game = newGame;
		
		return newGame;
	}
	
	public boolean startGame() {
		if (gameReady) {
			this.frame.setVisible(true);
			this.game.start();
			return true;
		} else {
			return false;
		}
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
