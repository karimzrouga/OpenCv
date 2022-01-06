package game;

import java.util.Vector;

public class Pinceau {
	private Vector position;
	private String color;

	public Pinceau(Vector position, String color) {
		super();
		this.position = position;
		this.color = color;
	}

	public Vector getPosition() {
		return position;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
