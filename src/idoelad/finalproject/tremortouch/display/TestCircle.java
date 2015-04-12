package idoelad.finalproject.tremortouch.display;

public class TestCircle {
	private float x;
	private float y;
	private float radius;
	private float pressure;
	private int alpha;
	private int touchId;
	
	public TestCircle(float x, float y, float radius, float pressure, int touchId) {
		this.x = x;
		this.y = y;
		this.radius = radiusToPx(radius);
		this.pressure = pressure;
		this.alpha = pressureToAlpha(pressure);
		this.touchId = touchId;
	}
	
	public TestCircle(float x, float y, float radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.pressure = 0;
		this.alpha = 0;
		this.touchId = 0;
	}
	
	private int pressureToAlpha(float pressure){
		return Math.round(pressure*255);
	}
	
	private float radiusToPx(float radius){
		return radius*60;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getPressure() {
		return pressure;
	}

	public void setPressure(float pressure) {
		this.pressure = pressure;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getTouchId() {
		return touchId;
	}

	public void setTouchId(int touchId) {
		this.touchId = touchId;
	}
	
	
	
	
	
	
	
}
