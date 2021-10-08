package com.turtle.api;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JFrame;

import com.turtle.api.managers.TurtleStateManager;

public class BrilliantTurtle {

	private static BrilliantTurtle i;

	public static final int STRUCT_2D = 0x01;

	public static final int STRUCT_PTS = 0x02;

	private Graphics pane;

	private TurtlePane frame;

	private static int scale = 1;

	public static int FILL = 0x04;


	
	
	public List<Function<MouseEvent, Boolean>> clickHandlers = new ArrayList<>();
	public List<Function<KeyEvent, Boolean>> keyboardHandlers= new ArrayList<>();
	
	public BrilliantTurtle() {
		frame = new TurtlePane(1920, 1080);
		pane = frame.getPage();

		i = this;
	}

	public static BrilliantTurtle getInstance() {
		assert i != null : "The turtle doesn't exist yet!";
		return i;
	}

	public void drawLine(int x1, int y1, int x2, int y2) {

		for (int i = 1; i <= scale; i++) {
			pane.drawLine(x1 + i, y1 + i, x2 + i, y2 + i);

			frame.repaint();
		}

	}

	public void drawLine(int x1, int y1, int x2, int y2, Color color) {
		pane.setColor(color);

		for (int i = 1; i <= scale; i++) {
			pane.drawLine(x1 + i, y1 + i, x2 + i, y2 + i);

			frame.repaint();
		}

	}

	public void drawPoint(int x, int y, Color color) {
		pane.setColor(color);
		pane.fillRect(x, y, scale, scale);
	}

	@SuppressWarnings("serial")
	class TurtlePane extends JFrame {
		private static final int EDGE = 3, TOP = 30; // around the JFrame
		private Image itsPicture;
		private Graphics itsPage;

		public TurtlePane(int width, int height) {
			super("Turtle Drawings"); // set the title for the frame
			setDefaultCloseOperation(EXIT_ON_CLOSE); // no WindowListener
			setSize(width + 2 * EDGE, height + TOP + EDGE);
			toFront(); // put this frame in front of the BlueJ window
			setVisible(true); // cause a call to paint
			begin(width, height);
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					clickHandlers.forEach(f -> f.apply(e));
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					keyboardHandlers.forEach(f -> f.apply(e));
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					keyboardHandlers.forEach(f -> f.apply(e));
					// TODO Auto-generated method stub
					
				}
			});

		} // ======================
		


		public void begin(int width, int height) {
			itsPicture = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
			itsPage = itsPicture.getGraphics();
			itsPage.setColor(Color.white);
			itsPage.fillRect(0, 0, width, height);
			itsPage.setColor(Color.black);
		} // ======================

		public Graphics getPage() {
			return itsPage; // itsPicture.getGraphics(); => NO COLORS
		} // ======================

		public void paint(Graphics g) {
			if (itsPicture != null)
				g.drawImage(itsPicture, EDGE, TOP, this);
		} // ======================
	}

	/**
	 * To be called by TurtleStateManager about updating the states of the vertices.
	 */
	public void updateState() {
		scale = (int) TurtleStateManager.scale;
	}

	private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8, r -> new Thread(() -> {
		r.run();
		frame.repaint();
	}, "Main Loop"));

	public TurtleEvent activeEvent;
	
	
	public void addClickHandler(Function<MouseEvent, Boolean> eventHandler) {
		this.clickHandlers.add(eventHandler);
	}	
	public void addKeyHandler(Function<KeyEvent, Boolean> eventHandler) {
		this.keyboardHandlers.add(eventHandler);
	}	

	public void setLoop(Thread loop) {
		executor.scheduleAtFixedRate(loop, 0, 50, TimeUnit.MILLISECONDS);
	}

	public Graphics getCheatyGraphics() {
		return this.pane;
	}
}
