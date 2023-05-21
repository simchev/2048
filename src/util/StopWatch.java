package util;

public class StopWatch {
	private long startTime;
	
	public StopWatch() {
		this.start();
	}
	
	public void start() {
		startTime = System.nanoTime();
	}
	
	public void restart() {
		start();
	}
	
	public long getElapsedNano() {
		return System.nanoTime() - startTime;
	}
	
	public long getElapsedMilli() {
		return getElapsedNano() / 1000000;
	}
	
	public int getElapsedSecond() {
		return (int)(getElapsedMilli() / 1000);
	}
}
