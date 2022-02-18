package star.field;

import java.awt.*;
import java.util.Random;

/**
 * @author uross
 */
public class Star {

	private Random rand = new Random();

	private double x;
	private double y;
	private double z;
	private double pz; // prethodno z, za iscrtavanje linija kod warpa

	private double speed = 1; // ovo je pocetna vrednost, moze se menjati, ako se dole otkomentarise sta treba

	private int width;
	private int height;

	public static int mousePos; // public static da ne bi moralo za svaku zvezdu pojedinacno da se podesava

	public enum StarMode {
		REGULAR, WARP
	}

	public static StarMode mode = StarMode.WARP;

	public Star(int w, int h) {
		this.width = w;
		this.height = h;
		setXYZ();
	}

	private double map(double value, double low1, double high1, double low2, double high2) {
		return (value - low1) / (high1 - low1) * (high2 - low2) + low2;
	}

	private void setXYZ() {
		this.x = rand.nextInt(width) - width / 2;
		this.y = rand.nextInt(height) - height / 2;
		this.z = rand.nextInt(width * 3 / 4) + width / 4;
		this.pz = z;
	}

	public void show(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2)); //deblja linija

		g2d.setColor(Color.WHITE);

		double sx = map(x / z, 0, 1, 0, width);
		double sy = map(y / z, 0, 1, 0, height);

		if (mode == StarMode.REGULAR) {
			int r = (int) map(z, 0, width, 8, 0);
			g2d.fillOval((int) sx, (int) sy, r, r);
		}

		if (mode == StarMode.WARP) {
			double px = map(x / pz, 0, 1, 0, width);
			double py = map(y / pz, 0, 1, 0, height);
			g.drawLine((int) px, (int) py, (int) sx, (int) sy); // warp
		}

		pz = z;

		update();
	}

	public void update() {
		speed = map(mousePos, 0, width, 0, 20); // podesavanje brzine misem
		z = z - speed;
		if (z < 1) {
			setXYZ();
		}
	}
}
