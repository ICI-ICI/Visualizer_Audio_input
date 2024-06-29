import javax.swing.*;

import java.awt.*;
import java.awt.geom.Line2D;

public class Video {
    JFrame window = new JFrame();   //main frame
    Dimension dimension;
    static int c = 10;  //pixel counter for debug visualisation
    static int d = 50;  //vertical pixel counter for debug visualisation

    // create main frame
    public void CreateWindow() {
        window.setVisible(true);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        window.setBounds(0, 0, dimension.width, dimension.height-30);

        window.getContentPane().setBackground(Color.BLACK);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    // create class <Point>
    public void drawPoint (int thick, float ampl, int range) {
        window.setVisible(true);
        window.add(new Point(thick, ampl, c, d, range));
        ++c;
        if (c >= 1500) {
            c = 0;
            d += 50;
        }
    }

    class Ex extends JPanel {

        @Override
        protected void paintComponent (Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            g2.drawLine( window.getWidth()/2, window.getHeight()/2,window.getWidth()/2, window.getHeight()/2);

            g2.drawLine(window.getWidth()/2 + 150, 0,window.getWidth()/2 + 150,window.getHeight());
            g2.drawLine(window.getWidth()/2 + 250, 0,window.getWidth()/2 + 250,window.getHeight());

            g2.drawLine(window.getWidth()/2 - 150, 0,window.getWidth()/2 - 150,window.getHeight());
            g2.drawLine(window.getWidth()/2 - 250, 0,window.getWidth()/2 - 250,window.getHeight());

            g2.drawLine(0, window.getHeight()/2 - 200, window.getWidth(),window.getHeight()/2 - 200);
            g2.drawLine(0, window.getHeight()/2 - 300, window.getWidth(),window.getHeight()/2 - 300);

            g2.drawLine(0, window.getHeight()/2 + 50, window.getWidth(),window.getHeight()/2 + 50);
            g2.drawLine(0, window.getHeight()/2 + 200, window.getWidth(),window.getHeight()/2 + 200);

            g2.drawLine(window.getWidth()/2, window.getHeight()/2 - 300, window.getWidth()/2 - 60, window.getHeight()/2 - 200);
            g2.drawLine(window.getWidth()/2 - 60, window.getHeight()/2 - 200, window.getWidth()/2 + 60, window.getHeight()/2 - 200);
            g2.drawLine(window.getWidth()/2 + 60, window.getHeight()/2 - 200, window.getWidth()/2, window.getHeight()/2 - 300);
        }
    }

    class Triangle extends JPanel {
        Line2D up = new Line2D.Float(window.getWidth()/2, window.getHeight()/2, window.getWidth()/2, window.getHeight()/2 - 300);
        Line2D left = new Line2D.Float(window.getWidth()/2, window.getHeight()/2, window.getWidth()/2 - 60, window.getHeight()/2 - 200);
        Line2D right = new Line2D.Float(window.getWidth()/2, window.getHeight()/2, window.getWidth()/2 + 60, window.getHeight()/2 - 200);
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
        }
    }
    class Point extends JComponent {
        int x;
        int y;
        int thick;
        float ampl;

        Point(int thick, float ampl, int x, int y, int range) {
            this.thick = thick;
            this.ampl = ampl;
            this.x = x;
            this.y = (int) (y-range*ampl);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(thick));
            g2.drawLine(x, y, x, y);
        }
    }
}