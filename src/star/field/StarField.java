package star.field;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author uross
 */
public class StarField extends JPanel implements MouseMotionListener, KeyListener {

	private Star[] stars = new Star[200];
	private int mouseX = 0;
	private int speed = 200;
	private boolean started = false;

	private Timer timer;

	public StarField(int width, int height) {
		this.setBackground(Color.BLACK);

		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.setFocusable(true); // da bi radio KeyListener

		createStars(width, height);

		Thread t = new Thread(() -> {
			try {
				Thread.sleep(300); // improvizovano, delic sekunde, samo da krene aplikacija, a posle toga mouse move moze ugasiti program
			} catch (InterruptedException ex) {
			}
			started = true; // sad moze da se ugasi pomeranjem misa
		});
		t.start();

		timer = new Timer();
		timer.schedule(new TickTimerTask(), 0, 15);
	}

	private class TickTimerTask extends TimerTask {
		@Override
		public void run() {
			repaint();
		}
	}

	public void createStars(int w, int h) {
		for (int i = 0; i < stars.length; i++) {
			stars[i] = new Star(w, h);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		g.translate(getWidth() / 2, getHeight() / 2);

//		Star.mousePos = mouseX; // stari nacin za promenu brzine
		Star.mousePos = speed;

		for (Star star : stars) {
			star.show(g);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//
	}

	@Override
	public void mouseMoved(MouseEvent e) {
//		mouseX = e.getX();  // stari nacin za promenu brzine
		if (started) {
			System.exit(0); // za screensaver
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_ESCAPE) {
			System.exit(0);
		}

		if (e.getKeyCode() == e.VK_1) {
			Star.mode = Star.StarMode.REGULAR;
		} else if (e.getKeyCode() == e.VK_2) {
			Star.mode = Star.StarMode.WARP;
		} else if (e.getKeyCode() == e.VK_UP) {
			speed = Math.min(speed + 50, 1500);
		} else if (e.getKeyCode() == e.VK_DOWN) {
			speed = Math.max(speed - 50, 100);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				frame.setTitle("Star Field");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// frame.setSize(800, 800);
				frame.setLocationRelativeTo(null);
				frame.getContentPane().add(new StarField(800, 800));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.setUndecorated(true);

				// Transparent 16 x 16 pixel cursor image.
				BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				// Create a new blank cursor.
				Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
				// Set the blank cursor to the JFrame.
				frame.getContentPane().setCursor(blankCursor);

				frame.setVisible(true);
			}
		});
	}
}
