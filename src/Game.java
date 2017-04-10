import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Runnable;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
public class Game extends JFrame implements Runnable{
	
	public static int alpha = 0xFFFF00FF;
	private int FPS = 60;
	
	private Canvas canvas = new Canvas();
	private RenderHandler renderer;
	
	BufferedImage sheetImage = loadImage("spritesheet.png");
	BufferedImage sheetImage1 = loadImage("tiles16x16.png");
	
	
	private Tiles tile;
	private Map map;
	
	private SpriteSheet sheet;
	private SpriteSheet playerSheet;
	
	private int selectedTileID = 2;	
	private Rectangle testRectangle = new Rectangle(30, 30, 100, 100);
	
	private GameObject[] objects;
	private KeyboardListener keyListener = new KeyboardListener(this); 
	private MouseEventListener mouseListener = new MouseEventListener(this);
	
	private Player player;
	
	private int xZoom = 3;
	private int yZoom = 3;
	
	public Game(){
		//When you close window, it stops program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Sets position
		setBounds(0, 0, 1280, 960);
		//Puts frame in the center of screen
		setLocationRelativeTo(null);
		//Adds graphics
		add(canvas);
		//Makes frame visible
		setVisible(true);
		//Creates object for buffering
		canvas.createBufferStrategy(3);
		renderer = new RenderHandler(getWidth(), getHeight());
		//Load Assets
		testRectangle.generateGraphics(15, 12234);
		sheet = new SpriteSheet(sheetImage);
		sheet.loadSprites(16, 16);
		
		BufferedImage playerSheetImage = loadImage("player.png");
		playerSheet = new SpriteSheet(playerSheetImage);
		playerSheet.loadSprites(20, 26);
		
		//Testing Animated Sprites
		AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 4);
		
		//Load Tiles
		File txtFile = new File("Tiles.txt");
		tile = new Tiles(txtFile, sheet);
		//Load Map 
		map = new Map(new File("Map.txt"), tile);
		
		//Load Objects
		objects = new GameObject[1];
		player = new Player(playerAnimations);
		objects[0] = player;
		

		
		//Add listeners
		canvas.addKeyListener(keyListener);
		canvas.addFocusListener(keyListener);
		canvas.addMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
	}
	
	public void update(){
		for (int i = 0; i < objects.length; i++) {
			objects[i].update(this);
		}
		
	}
	
	
	private BufferedImage loadImage(String path){
		try {
			BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
			return formattedImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void handleCTRL(boolean[] keys){
		if(keys[KeyEvent.VK_S]){
			map.saveMap();
		}
	}
	
	public void leftClick(int x, int y){
		Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
		for (int i = 0; i < objects.length; i++) {
			objects[i].handleMouseClick(mouseRectangle, renderer.getCamera(), xZoom, yZoom);
		}
		
		
		x = (int)Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
		y = (int)Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
		map.setTile(x, y, selectedTileID);

	}
	
	public void rightClick(int x, int y){
		x = (int)Math.floor((x + renderer.getCamera().x) / (16.0 * xZoom));
		y = (int)Math.floor((y + renderer.getCamera().y) / (16.0 * yZoom));
		map.removeTile(x, y);
	}


	
	
	public void render(){
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);
		
		map.render(renderer, xZoom, yZoom);
		for (int i = 0; i < objects.length; i++) {
			objects[i].render(renderer, xZoom, yZoom);
		}

		renderer.render(graphics);

		
		graphics.dispose();
		bufferStrategy.show();
		renderer.clear();
		
	}
	
	public void changeTile(int tileID){
		selectedTileID = tileID;
	}
	
	@Override
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000 / FPS;
		double 	changeInSeconds = 0;
		while(true){
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1){
				update();
				changeInSeconds = 0;
			}
			render();
			lastTime = now;
		}
	} 
	
	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
	
	public KeyboardListener getKeyListener(){
		return keyListener;
	}
	
	public MouseEventListener getMouseListener(){
		return mouseListener;
	}
	
	public RenderHandler getRenderer(){
		return renderer;
	}
}