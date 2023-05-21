import javax.swing.JFrame;

import util.StopWatch;

public class Game extends JFrame {
	
	private boolean gameRunning;
	private StopWatch stopwatch;
	private Board board;
	
	public Game(int longueur, int hauteur) {
		setSize(longueur + 6, hauteur + 28);
		setLocationRelativeTo(getRootPane());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		board = new Board(4, 200, "Couleurs3.png", "Grille.png");
		add(board);
		
		setVisible(true);
		run();
	}
	
	private void run() {
		long lastLoopTime = System.nanoTime();
		long FPS = 0;
		long lastFpsTime = 0;
		final int TARGET_FPS = 60;
		final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;
		
		gameRunning = true;
		stopwatch = new StopWatch();
		stopwatch.start();
		
		while (gameRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			FPS++;
			lastFpsTime += updateLength;
			if (lastFpsTime >= 1_000_000_000) {
				setTitle("FPS " + FPS + " Cases Vides : " + board.getCaseVide());
				lastFpsTime = 0;
				FPS = 0;
			}
			board.update();
			repaint();
			try {
				Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
			} catch (Exception e) {
				
			}
		}
	}
}
