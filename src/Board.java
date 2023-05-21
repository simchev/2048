import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.Randmo;
import util.StopWatch;

public class Board extends JPanel implements KeyListener {
	
	private Tile[][] tabTile;
	private Tile[][] newTabTile;
	private Randmo r;
	private BufferedImage board;
	public static BufferedImage color;
	//private BufferedImage[] colors;
	private boolean left, right, up, down;
	private StopWatch time;
	private int boardSize;
	private int animationTime;
	private boolean animation;
	private boolean hasMoved;
	private int tileSize;
	private String strCouleurs, strBoard;

	public Board(int boardSize, int tileSize, String strCouleurs, String strBoard) {
		this.boardSize = boardSize;
		tabTile = new Tile[boardSize][boardSize];
		r = new Randmo();
		left = false;
		right = false;
		up = false;
		down = false;
		addKeyListener(this);
		setFocusable(true);
		time = new StopWatch();
		animationTime = 150;
		hasMoved = false;
		this.tileSize = tileSize;
		this.strCouleurs = strCouleurs;
		this.strBoard = strBoard;
		animation = false;
		
		try {
			color = ImageIO.read(new File(strCouleurs));
		} catch (IOException e) {
			System.out.println("Pas de tileSet");
		}
		
		try {
			board = ImageIO.read(new File(strBoard));
		} catch (IOException e) {
			System.out.println("Pas de Grille");
		}
		
		SpawnRandom(2);
	}
	
	private void SpawnRandom(int intNombre) {
		int caseVides = getCaseVide();
		if (intNombre > caseVides)
			intNombre = caseVides;
		for (int i = 0; i < intNombre; i++) {
			boolean binContinue = true;
			while (binContinue) {
				int x = r.Next(0, 4);
				int y = r.Next(0, 4);
				int j = r.Next(0, 100);
				if (tabTile[x][y] == null) {
					tabTile[x][y] = new Tile((j < 5) ? 1 : 0, 20 + 20*x + tileSize*x, 20 + 20*y + tileSize*y, tileSize);
					binContinue = false;
				}
			}
		}
	}
	
	public int getCaseVide() {
		int caseVides = 0;
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (tabTile[i][j] == null)
					caseVides++;
			}
		}
		return caseVides;
	}
	
	public void paint(Graphics g) {
		Graphics2D paper = (Graphics2D) g;
		paper.drawImage(board, 0, 0, getWidth(), getHeight(), 0, 0, getWidth(), getHeight(), null);
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (tabTile[i][j] != null) {
					tabTile[i][j].paint(paper);
				}
			}
		}
	}
	
	public void update() {
		if (time.getElapsedMilli() > animationTime) {
			if (right)
				moveRight();
			else if (left)
				moveLeft();
			else if (up)
				moveUp();
			else if (down)
				moveDown();
			
			if (hasMoved) {
				time.restart();
				hasMoved = false;
				SpawnRandom(1);
			}
		}
	}
	
	private void moveUp() {
		copieTableau();
		int currentInt;
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 1; j < boardSize; j++) {
				currentInt = j;
				if (newTabTile[i][j] != null) {
					while ((j - 1) >= 0 && newTabTile[i][j - 1] == null) {
						j--;
					}
					if (j == 0) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j - currentInt)) + (200 * (j - currentInt)));
						newTabTile[i][j] = newTabTile[i][currentInt];
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
					else if (newTabTile[i][currentInt].getIntValue() == newTabTile[i][j - 1].getIntValue() && !newTabTile[i][j - 1].isFusionner()) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j - 1 - currentInt)) + (200 * (j - 1 - currentInt)));
						newTabTile[i][j - 1] = newTabTile[i][currentInt];
						newTabTile[i][j - 1].moreIntValue();
						newTabTile[i][j - 1].setFusion(true);
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
					else if (j != currentInt) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j - currentInt)) + (200 * (j - currentInt)));
						newTabTile[i][j] = newTabTile[i][currentInt];
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
				}
				j = currentInt;
			}
		}
		
		recopieTableau();
	}
	
	private void moveDown() {
		copieTableau();
		int currentInt;
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = boardSize - 2; j >= 0; j--) {
				currentInt = j;
				if (newTabTile[i][j] != null) {
					while ((j + 1) < boardSize && newTabTile[i][j + 1] == null) {
						j++;
					}
					if (j == boardSize - 1) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j - currentInt)) + (200 * (j - currentInt)));
						newTabTile[i][j] = newTabTile[i][currentInt];
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
					else if (newTabTile[i][currentInt].getIntValue() == newTabTile[i][j + 1].getIntValue() && !newTabTile[i][j + 1].isFusionner()) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j + 1 - currentInt)) + (200 * (j + 1 - currentInt)));
						newTabTile[i][j + 1] = newTabTile[i][currentInt];
						newTabTile[i][j + 1].moreIntValue();
						newTabTile[i][j + 1].setFusion(true);
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
					else if (j != currentInt) {
						newTabTile[i][currentInt].setPosition(newTabTile[i][currentInt].x, newTabTile[i][currentInt].y + (20 * (j - currentInt)) + (200 * (j - currentInt)));
						newTabTile[i][j] = newTabTile[i][currentInt];
						newTabTile[i][currentInt] = null;
						hasMoved = true;
					}
				}
				j = currentInt;
			}
		}
		
		recopieTableau();
	}
	
	private void moveRight() {
		copieTableau();
		int currentInt;
		
		for (int j = 0; j < boardSize; j++) {
			for (int i = boardSize - 2; i >= 0; i--) {
				currentInt = i;
				if (newTabTile[i][j] != null) {
					while ((i + 1) < boardSize && newTabTile[i + 1][j] == null) {
						i++;
					}
					if (i == boardSize - 1) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i - currentInt)) + (200 * (i - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i][j] = newTabTile[currentInt][j];
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
					else if (newTabTile[currentInt][j].getIntValue() == newTabTile[i + 1][j].getIntValue() && !newTabTile[i + 1][j].isFusionner()) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i + 1 - currentInt)) + (200 * (i + 1 - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i + 1][j] = newTabTile[currentInt][j];
						newTabTile[i + 1][j].moreIntValue();
						newTabTile[i + 1][j].setFusion(true);
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
					else if (i != currentInt) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i - currentInt)) + (200 * (i - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i][j] = newTabTile[currentInt][j];
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
				}
				i = currentInt;
			}
		}
		
		recopieTableau();
	}
	
	private void moveLeft() {
		copieTableau();
		int currentInt;
		
		for (int j = 0; j < boardSize; j++) {
			for (int i = 1; i < boardSize; i++) {
				currentInt = i;
				if (newTabTile[i][j] != null) {
					while ((i - 1) >= 0 && newTabTile[i - 1][j] == null) {
						i--;
					}
					if (i == 0) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i - currentInt)) + (200 * (i - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i][j] = newTabTile[currentInt][j];
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
					else if (newTabTile[currentInt][j].getIntValue() == newTabTile[i - 1][j].getIntValue() && !newTabTile[i - 1][j].isFusionner()) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i - 1 - currentInt)) + (200 * (i - 1 - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i - 1][j] = newTabTile[currentInt][j];
						newTabTile[i - 1][j].moreIntValue();
						newTabTile[i - 1][j].setFusion(true);
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
					else if (i != currentInt) {
						newTabTile[currentInt][j].setPosition(newTabTile[currentInt][j].x + (20 * (i - currentInt)) + (200 * (i - currentInt)), newTabTile[currentInt][j].y);
						newTabTile[i][j] = newTabTile[currentInt][j];
						newTabTile[currentInt][j] = null;
						hasMoved = true;
					}
				}
				i = currentInt;
			}
		}
		
		recopieTableau();
	}
	
	private void copieTableau() {
		newTabTile = new Tile[4][4];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (tabTile[i][j] != null)
					newTabTile[i][j] = new Tile(tabTile[i][j]);
			}
		}
	}
	
	private void recopieTableau() {
		tabTile = new Tile[4][4];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (newTabTile[i][j] != null) {
					tabTile[i][j] = new Tile(newTabTile[i][j], false);
				}
			}
		}
	}
	
	/*private void bouge() {
		long elapsed = time.getElapsedMilli();
		if (elapsed < animationTime) {
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (tabTile[i][j] != null)
						if (tabTile[i][j].mouvementNotNull())
							tabTile[i][j].setPosition(tabTile[i][j].getNewPosition(elapsed));
				}
			}
			animation = true;
		}
		else {
			animation = false;
			recopieTableau();
			if (hasMoved) {
				SpawnRandom(1);
				hasMoved = false;
			}
		}
	}*/

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (int)KeyEvent.VK_RIGHT:
			right = true;
			break;
		case (int)KeyEvent.VK_LEFT:
			left = true;
			break;
		case (int)KeyEvent.VK_UP:
			up = true;
			break;
		case (int)KeyEvent.VK_DOWN:
			down = true;
			break;
		case (int)KeyEvent.VK_R:
			tabTile = new Tile[4][4];
		 	SpawnRandom(2);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (int)KeyEvent.VK_RIGHT:
			right = false;
			break;
		case (int)KeyEvent.VK_LEFT:
			left = false;
			break;
		case (int)KeyEvent.VK_UP:
			up = false;
			break;
		case (int)KeyEvent.VK_DOWN:
			down = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
