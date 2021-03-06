import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class DiepPanel extends JPanel implements Runnable{
	int currentFPS = 0;
	int currentDraw = 0;
	
	
	int mouseX = 0;
	int mouseY = 0;
	
	
	
	boolean gameRun;
	Thread game;
	static ArrayList<GameObject> sprites = new ArrayList<GameObject>();
	Tank player = new Tank(Constants.screenW/2,Constants.screenH/2,TankType.BASIC, 1f);

	double renderX, renderY;
	Rectangle currentScreen = new Rectangle((int)renderX,(int)renderY,Constants.screenW,Constants.screenH);



	protected DiepPanel(){

		sprites.add(player);


		keys();
		panel();
		start();




	}
	void keys(){
		getInputMap().put(KeyStroke.getKeyStroke("A"), "A");
		getInputMap().put(KeyStroke.getKeyStroke("S"), "S");
		getInputMap().put(KeyStroke.getKeyStroke("D"), "D");
		getInputMap().put(KeyStroke.getKeyStroke("W"), "W");

		getInputMap().put(KeyStroke.getKeyStroke("released A"), "rA");
		getInputMap().put(KeyStroke.getKeyStroke("released S"), "rS");
		getInputMap().put(KeyStroke.getKeyStroke("released D"), "rD");
		getInputMap().put(KeyStroke.getKeyStroke("released W"), "rW");

		getActionMap().put("A", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dx=-player.speed;
				player.A = true;
			}

		});
		getActionMap().put("D", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dx=player.speed;
				player.D = true;
			}

		});
		getActionMap().put("W", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dy=-player.speed;
				player.W = true;
			}

		});
		getActionMap().put("S", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dy=player.speed;
				player.S = true;
			}

		});
		getActionMap().put("rA", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dx=0;
				player.A = false;
			}

		});
		getActionMap().put("rD", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dx=0;
				player.D = false;
			}

		});
		getActionMap().put("rW", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dy=0;
				player.W = false;
			}

		});
		getActionMap().put("rS", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//player.dy=0;
				player.S = false;
			}

		});
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				player.shoot();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});


	}
	void panel(){
		JFrame frame = new JFrame();
		frame.add(this);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}
	synchronized void start(){
		game = new Thread(this);
		gameRun = true;
		game.start();
	}
	public void run(){
		long previousTime = System.currentTimeMillis();
		long currentTime = previousTime;
		long elapsedTime;
		long totalElapsedTime = 0;
		int frameCount = 0;

		while(gameRun)
		{
			currentTime = System.currentTimeMillis();
			elapsedTime = (currentTime - previousTime); 
			totalElapsedTime += elapsedTime;

			if (totalElapsedTime > 1000)
			{
				currentFPS = frameCount;
				frameCount = 0;
				totalElapsedTime = 0;
			}

			update();

			try
			{
				Thread.sleep(getFpsDelay(Constants.maxFPS));
			} catch (Exception e) {
				e.printStackTrace();
			}

			previousTime = currentTime;
			frameCount++;

		}

	}
	int getFpsDelay(int desiredFps)
	{
		return 1000 / desiredFps;
	}

	void update(){
		updateAngle();
		move();
		boundCheck();
		repaint();
	}
	void updateAngle(){
		mouseX = (int) (MouseInfo.getPointerInfo().getLocation().x +  renderX);
		mouseY = (int) (MouseInfo.getPointerInfo().getLocation().y + renderY );
		 double angle = Math.atan2((player.x - mouseX ), (player.y - mouseY));
		    if (angle < 0) {
		         angle+=Math.PI*2;
		    }
		 player.angle =(-angle) - Math.PI/2;
		 
		
	}
	void boundCheck(){
		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				GameObject o = sprites.get(index);
				//bound check via map
				boolean shouldRemove = false;
				if(o.x-o.W/2 < Constants.map.x){
					o.x += o.speed;
					shouldRemove = true;
				}
				if(o.x+o.W/2 > Constants.map.x + Constants.map.width){
					o.x -= o.speed;
					shouldRemove = true;

				}
				if(o.y-o.H/2 < Constants.map.y){
					o.y += o.speed;
					shouldRemove = true;

				}
				if(o.y+o.H/2 > Constants.map.y + Constants.map.height){
					o.y -= o.speed;
					shouldRemove = true;
				}
				if(o instanceof Projectile){
					if(shouldRemove){
						sprites.remove(o);
					}
				}
			}

		}
	}
	void move(){
		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				//map edge check
				GameObject o = sprites.get(index);
				o.x+=sprites.get(index).dx;
				o.y+=sprites.get(index).dy;
			}
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);


		Graphics2D g2d = (Graphics2D) g;
		renderX = player.x-Constants.screenW/2;
		renderY = player.y-Constants.screenH/2;
		if(renderX<Constants.map.x){
			renderX = Constants.map.x;
		}
		if(renderY<Constants.map.y){
			renderY = Constants.map.y;
		}
		if(renderX+Constants.screenW>Constants.map.x + Constants.map.width){
			renderX = Constants.map.x + Constants.map.width-Constants.screenW;
		}
		if(renderY+Constants.screenH>Constants.map.y + Constants.map.height){
			renderY = Constants.map.y + Constants.map.height-Constants.screenH;
		}



		//sets focus on player

		//makes sure player screen renders only the map

		g2d.translate(-renderX, -renderY);
		currentDraw = 0;
		currentScreen.setLocation((int)renderX, (int)renderY);
		(g2d).draw(Constants.map);
		drawMap(g2d);
		drawMiniMap(g2d);
		drawSprites(g2d);
		drawInfo(g2d);

	}
	void drawMiniMap(Graphics2D g2d){
		int scale = 15;
		//scale down map
		g2d.drawRect((int)renderX+scale,(int)renderY+ scale, Constants.map.width/scale ,Constants.map.height/scale);
		//scaled down screen
		g2d.drawRect((int)renderX+scale +  ( currentScreen.x - Constants.map.x )/scale, (int)renderY + scale + ( currentScreen.y - Constants.map.y )/scale, currentScreen.width/scale, currentScreen.height/scale);
	}
	void drawMap(Graphics2D g2d){
		int scale = 250;
		Rectangle r = new Rectangle();
		r.setSize(scale, scale);
		for(int index = Constants.map.x; index <= Constants.map.x + Constants.map.width; index+=scale){
			for(int index2 = Constants.map.y; index2 <= Constants.map.y + Constants.map.height; index2+=scale){
				r.setLocation(index, index2);
				if(currentScreen.contains(r)||currentScreen.intersects(r)){
					g2d.draw(r);
					currentDraw++;
				}
			}
		}




	}
	void drawSprites(Graphics2D g2d){

		synchronized(sprites){
			for(int index = 0; index < sprites.size(); index++){
				//partial render
				GameObject o = sprites.get(index);
				if(currentScreen.contains(o.getBounds())||currentScreen.intersects(o.getBounds())){
					o.draw(g2d);
					currentDraw++;
				}
			}
		}
	}
	void drawInfo(Graphics g){
		g.setFont(Constants.font);
		g.drawString("FPS: "+currentFPS, (int)renderX, (int)renderY+300);
		g.drawString("Drawn Currently : " + currentDraw, (int)renderX, (int)renderY+325);
		g.drawString("renderX " + (int)renderX + " renderY"+  (int)renderY  , (int)renderX, (int)renderY+350) ;
		g.drawString("mouseX " + (int)mouseX + " mouseY"+  (int)mouseY  , (int)renderX, (int)renderY+375) ;

	}
}
