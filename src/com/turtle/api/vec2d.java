package com.turtle.api;

public class vec2d {

	private double x, y;

	public vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public vec2d add(double x, double y) {

		return new vec2d(this.x + x, this.y + y);
	}

	public vec2d add(vec2d other) {

		return new vec2d(this.x + other.x, this.y + other.y);
	}

	public vec2d times(double scalar) {

		return new vec2d(this.x * scalar, this.y * scalar);
	}

	public vec2d times(vec2d scalar) {
		return new vec2d(this.x * scalar.x, this.y * scalar.y);
	}

	public vec2d sub(vec2d scalar) {
		return new vec2d(this.x - scalar.x, this.y - scalar.y);
	}

	public vec2d sub(double x, double y) {
		return new vec2d(this.x - x, this.y - y);
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY() {
		this.y = y;
	}
	public double magnitude() {
		return (double) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}

}