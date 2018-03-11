package star.field;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author uross
 */
public class StarField extends JPanel implements MouseMotionListener, KeyListener {

    private Star[] stars = new Star[200];
    private int mouseX = 0;

    public StarField(int width, int height) {
        this.setBackground(Color.BLACK);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        //this.requestFocus();

        createStars(width, height);

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

        Star.mousePos = mouseX;

        for (int i = 0; i < stars.length; i++) {
            stars[i].show(g);
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
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
                //frame.setSize(800, 800);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(new StarField(800, 800));
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.setVisible(true);
            }
        });
    }
}
