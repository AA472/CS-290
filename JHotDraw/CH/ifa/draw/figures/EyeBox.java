package CH.ifa.draw.figures;

import CH.ifa.draw.figures.EllipseFigure;
import java.awt.*;

public class EyeBox extends EllipseFigure {
    public EyeBox() {
        super(new Point(0,0), new Point(0,0));
    }
    public EyeBox(Point origin, Point corner) {
        super(origin,corner);
    }
    public void drawFrame(Graphics g) {
        Rectangle r = displayBox();
        g.drawRect(r.x, r.y, r.width-1, r.height-1);
        g.drawOval(r.x, r.y, r.width-1, r.height-1);
    }
    public void drawBackground(Graphics g) {
        Rectangle r = displayBox();
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}