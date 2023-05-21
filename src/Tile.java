import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile extends Rectangle {
	
	private int intValue;
	private boolean fusion;
	private Mouvement mouvement;
	private int tileSize;

	public Tile(int intValue, int x, int y, int size) {
		this.intValue = intValue;
		this.tileSize = size;
		fusion = false;
		setBounds(new Rectangle(x, y, tileSize, tileSize));
	}
	
	public Tile(Tile tile) {
		intValue = tile.getIntValue();
		fusion = tile.isFusionner();
		tileSize = tile.getTileSize();
		setBounds(new Rectangle(tile.x, tile.y, tile.width, tile.height));
	}
	
	public Tile(Tile tile, boolean fusion) {
		intValue = tile.getIntValue();
		this.fusion = fusion;
		tileSize = tile.getTileSize();
		setBounds(new Rectangle(tile.x, tile.y, tile.width, tile.height));
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(Board.color, x, y, x + 200, y + 200, 
				intValue * 200, 0, (intValue * 200) + 200, 200, null);
	}
	
	public int getIntValue() {
		return intValue;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public void moreIntValue() {
		if (intValue < 11)
			intValue++;
	}
	
	public boolean isFusionner() {
		return fusion;
	}
	
	public void setFusion(boolean fusionne) {
		fusion = fusionne;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Point p) {
		x = p.x;
		y = p.y;
	}
	
	public void setMouvement(Point p1, Point p2, int time) {
		mouvement = new Mouvement(p1, p2, time);
	}
	
	public Point getNewPosition(long elapsedTime) {
		return mouvement.getMouvement(elapsedTime);
	}
	
	public boolean mouvementNotNull() {
		return (mouvement != null);
	}
}
