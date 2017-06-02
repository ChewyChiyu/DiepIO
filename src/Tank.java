import java.awt.Graphics2D;
import java.awt.Image;

public class Tank extends GameObject{
	static final double scale = 1;
	static int W = (int) (Texture.basicBlue.getWidth()*scale);
	static int H = (int) (Texture.basicBlue.getHeight()*scale);
	protected Tank(double x, double y) {
		super(x, y, W, H);
	}

	@Override
	void draw(Graphics2D g) {
		g.drawImage(Texture.basicBlue.getScaledInstance((int)(Texture.basicBlue.getWidth()*scale), (int)(Texture.basicBlue.getHeight()*scale), Image.SCALE_DEFAULT),(int)x-W/2, (int)y-H/2, W, H,null);
		g.drawString("x : " + x + " y : " + y, (int)x-100, (int)y-100);
		g.draw(bounds);
		
		
	}

}
