import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	static BufferedImage basicBlue;
	static BufferedImage basicProjBlue;
	protected Texture(){
		try{
		BufferedImage spriteSheet = ImageIO.read(getClass().getResource("DiepIOSpriteSheet.png"));
		basicBlue = spriteSheet.getSubimage(110,31,93,66);
		basicProjBlue = spriteSheet.getSubimage(384,1017,48,47);
		}catch(Exception e) { e.printStackTrace();}
	}
}
