package util;
import java.util.Random;

public class Randmo extends Random {
	
	public int Next(int min, int max) {
		return (int)(min + (max - min) * nextDouble());
	}
}
