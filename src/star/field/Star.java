package star.field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author uross
 */
public class Star {

    private Random rand = new Random();

    private double x;
    private double y;
    private double z;
    private double pz;  //prethodno z, za iscrtavanje linija kod warpa

    private double speed = 1;   // ovo je pocetna vrednost, moze se menjati, ako se dole otkomentarise sta treba

    private int width;
    private int height;

    public static int mousePos; // stavljeno je public da ne bi moralo za svaku zvezdu pojedinano da se podesava

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
        this.z = rand.nextInt(width);
        this.pz = z;
    }

    public void show(Graphics g) {
        //Graphics2D g2 = (Graphics2D) g;
        //g2.setStroke(new BasicStroke(2)); //deblja linija

        g.setColor(Color.WHITE);

        double sx = map(x / z, 0, 1, 0, width);
        double sy = map(y / z, 0, 1, 0, height);

        int r = (int) map(z, 0, width, 10, 0);
        g.fillOval((int) sx, (int) sy, r, r);

        // Ako hocemo simulaciju warpa, ovo ovde otkomentarisati, a red iznad iskomentarisati
//        double px = map(x / pz, 0, 1, 0, width);
//        double py = map(y / pz, 0, 1, 0, height);
//
//        g.drawLine((int) px, (int) py, (int) sx, (int) sy);   //warp
//
//        pz = z;

        update();
    }

    public void update() {
        //speed = map(mousePos, 0, width, 0, 20);   //podesavanje brzine misem
        z = z - speed;
        if (z < 1) {
            setXYZ();
        }
    }
}
