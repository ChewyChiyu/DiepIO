import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Constants {
	static final int maxFPS = 70;
	static final Font font = new Font("Aerial",Font.PLAIN,20);
	static final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
	static final int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
	static final Rectangle map = new Rectangle(-screenW*2,-screenH*2,screenW*4,screenH*4);
	static final float friction = .01f;

}
