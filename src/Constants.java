import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Constants {
	static final int maxFPS = 70;
	static final Font font = new Font("Aerial",Font.PLAIN,20);
	static final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
	static final int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
	static final int mapX1 = -screenW*2;
	static final int mapY1 = -screenH*2;
	static final int mapX2 = screenW*2;
	static final int mapY2 = screenH*2;
	static final Rectangle map = new Rectangle(-screenW*2,-screenH*2,screenW*4,screenH*4);
	

}
