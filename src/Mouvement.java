import java.awt.Point;

public class Mouvement {
	
	private Point lastPosition, nextPosition;
	private int time;
	
	public Mouvement(Point lastPosition, Point nextPosition, int time) {
		this.lastPosition = lastPosition;
		this.nextPosition = nextPosition;
		this.time = time;
	}
	
	public Point getMouvement(long elapsedTime) {
		return new Point((int)((nextPosition.x - lastPosition.x) * elapsedTime / time), (int)((nextPosition.y - lastPosition.y) * elapsedTime / time));
	}
}
