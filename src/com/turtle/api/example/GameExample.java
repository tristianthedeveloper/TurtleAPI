package com.turtle.api.example;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

// the main class.
import com.turtle.api.BrilliantTurtle;
import com.turtle.api.TurtleTesselator;
import com.turtle.api.vec2d;
import com.turtle.api.util.VertexUtils;

public class GameExample {

	static vec2d playerLoc = new vec2d(500, 500);

	private static LinkedList<vec2d> triggeredVertices = new LinkedList<>();

	public static void main(String[] args) {

		BrilliantTurtle turtle = new BrilliantTurtle();

		final AtomicInteger dx = new AtomicInteger(0);
		final AtomicInteger dy = new AtomicInteger(0);

		turtle.addClickHandler((e -> {

			dx.addAndGet(50);
			dy.getAndAdd(50);

			return true;

		}));
		turtle.addKeyHandler((e -> {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				playerLoc = playerLoc.sub(0, 1);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				playerLoc = playerLoc.add(0, 1);
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

				System.out.println("going right");
				playerLoc = playerLoc.add(1, 0);

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				playerLoc = playerLoc.sub(1, 0);
			}
			if (e.getKeyChar() == 'f') {
				fillTrippedVertices();
			}
			System.out.println(e.getKeyChar());
			System.out.println("keypress triggered");
			return true;
		}));

		turtle.setLoop(new Thread(() -> {

			drawPlayer();

		}));
	}

	private static void fillTrippedVertices() {
		// TODO Auto-generated method stub

		vec2d min = VertexUtils.getMinVertex(triggeredVertices);
		vec2d max = VertexUtils.getMaxVertex(triggeredVertices);

		TurtleTesselator tess = TurtleTesselator.getInstance();
		tess.begin(BrilliantTurtle.FILL);
		tess.pos(min.getX(), min.getY()).color(Color.blue);
		;
		tess.pos(max.getX(), max.getY());
		tess.draw();

	}

	public static void drawPlayer() {

		int playerWidth = 15;

		int playerHeight = 15;

		int x = (int) playerLoc.getX();
		int y = (int) playerLoc.getY();
		TurtleTesselator tess = TurtleTesselator.getInstance();
		tess.begin(BrilliantTurtle.STRUCT_2D | BrilliantTurtle.FILL);

		tess.pos(x, y);
		tess.pos(x + playerWidth, y);
		tess.pos(x + playerWidth, y + playerHeight);
		tess.pos(x, y + playerHeight);
		tess.draw();
		if (!triggeredVertices.contains(new vec2d(x, y))) {
			triggeredVertices.add(new vec2d(x, y));
		}

	}
}