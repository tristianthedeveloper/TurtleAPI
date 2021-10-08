package com.turtle.api;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.turtle.api.util.VertexUtils;

public class TurtleTesselator {

	private static TurtleTesselator i;

	private BrilliantTurtle turtle;

	private final ArrayDeque<TurtleVertex> vertices = new ArrayDeque<>();

	private boolean started = false;

	private int mode;

	public void begin(int mode) {
		if (started) {
			throw new RuntimeException("Turtle Tesselator has already begun!");
		}
		this.mode = mode;
		this.started = true;
	}

	public void draw() {
		if (!started) {
			throw new RuntimeException("Turtle Tesselator has not started!");
		}

		started = false;

		switch (mode & ~BrilliantTurtle.FILL) {
		case BrilliantTurtle.STRUCT_2D: {
//        connects all the vertices, and connects the last one to the first one.
			drawStruct2d();
			return;
		}
		case BrilliantTurtle.STRUCT_PTS: {
			drawPoints();
			return;
		}
		default:
			break;
		}
		if (mode == BrilliantTurtle.FILL) {

			vec2d min = VertexUtils.getMinVertex(
					this.vertices.stream().map(e -> new vec2d(e.getX(), e.getY())).collect(Collectors.toList()));
			vec2d max = VertexUtils.getMaxVertex(
					this.vertices.stream().map(e -> new vec2d(e.getX(), e.getY())).collect(Collectors.toList()));

			turtle.getCheatyGraphics().fillRect((int) min.getX(), (int) min.getY(),
					(int) max.getX() - (int) min.getX() + 1, (int) (max.getY() - min.getY()));
		}

	}

	public TurtleVertex pos(double x, double y) {
		if (!started) {
			throw new RuntimeException("Turtle Tesselator has not started!");
		}
		TurtleVertex vertex = new TurtleVertex(new vec2d(x, y));
		vertices.push(vertex);
		return vertex;
	}

	public TurtleVertex pos(int x, int y) {
		if (!started) {
			throw new RuntimeException("Turtle Tesselator has not started!");
		}

		TurtleVertex vertex = new TurtleVertex(new vec2d(x, y));
		vertices.push(vertex);
		return vertex;
	}

	public static TurtleTesselator getInstance() {
		if (i == null)
			i = new TurtleTesselator();
		if (i.turtle == null) {
			i.turtle = BrilliantTurtle.getInstance();
		}
		if (i.turtle == null) {
			throw new NullPointerException("There is no turtle!");
		}
		return i;
	}

	public static class TurtleVertex {
		private int color;

		private final vec2d pos;

		public TurtleVertex(vec2d vec, Color color) {
			this.color = color.getRGB();
			this.pos = vec;
		}

		public TurtleVertex(vec2d vec, int color) {
			this.color = color;
			this.pos = vec;
		}

		public TurtleVertex(vec2d vec) {
			this.color = 0;
			this.pos = vec;
		}

		public void color(int color) {
			this.color = color;
		}

		public void color(Color color) {
			this.color = color.getRGB();
		}

		public int getX() {
			return (int) pos.getX();
		}

		private int getY() {
			return (int) pos.getY();
		}
	}

	private void drawPoints() {

		TurtleVertex vertex;
		while ((vertex = vertices.poll()) != null) {
			turtle.drawPoint(vertex.getX(), vertex.getY(), new Color(vertex.color));
		}
	}

	private void drawStruct2d() {

		TurtleVertex first = vertices.getFirst();
		TurtleVertex vertex;
		TurtleVertex last = vertices.getLast();

		if ((mode & BrilliantTurtle.FILL) != 0) {

			AtomicReference<TurtleVertex> min = new AtomicReference<>(vertices.getFirst()),
					max = new AtomicReference<>(last);

			vertices.forEach(e -> {
				if (e.getX() == min.get().getX()) {
					if (e.getY() < min.get().getY()) {
						min.set(e);
					}
				}
				if (e.getX() < min.get().getX() && e.getY() == min.get().getY()) {
					min.set(e);
				}

				if (e.getX() < min.get().getX() && e.getY() < min.get().getY()) {
					min.set(e);
				}

				if (e.getX() == max.get().getX()) {
					if (e.getY() > max.get().getY()) {
						max.set(e);
					}
				}
				if (e.getX() > max.get().getX() && e.getY() == max.get().getY()) {
					max.set(e);
				}
				if (e.getX() > max.get().getX() && e.getY() > max.get().getY()) {
					max.set(e);
				}
			});
			turtle.getCheatyGraphics().fillRect(min.get().getX(), min.get().getY(),
					max.get().getX() - min.get().getX() + 1, max.get().getY() - min.get().getY());

		}
		while ((vertex = vertices.poll()) != null) {
			turtle.drawLine(vertex.getX(), vertex.getY(), last.getX(), last.getY(), new Color(vertex.color));
			last = vertex;
		}

	}

}