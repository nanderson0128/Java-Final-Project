import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject{

	private Sprite[] sprites;
	private int currentSprite = 0;
	private int speed;
	private int counter = 0;
	
	private int startSprite;
	private int endSprite;
	
	public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed){
		sprites = new Sprite[positions.length];
		this.speed = speed;
		this.endSprite = positions.length - 1;
		
		for (int i = 0; i < positions.length; i++) {
			sprites[i] = new Sprite(sheet, positions[i].x,  positions[i].y,  positions[i].w,  positions[i].h);
		}
	}
	
	public AnimatedSprite(SpriteSheet sheet, int speed){
		sprites = sheet.getLoadedSprites();
		this.speed = speed;
		this.endSprite = sprites.length - 1;
		

	}
	
	
	public AnimatedSprite(BufferedImage[] images, int speed) {
		// TODO Auto-generated constructor stub
		sprites = new Sprite[images.length];
		this.speed = speed;
		this.startSprite = images.length - 1;
		
		for (int i = 0; i < images.length; i++) {
			sprites[i] = new Sprite(images[i]);
		}
		
		
		}

	
	//Render is dealt specifically with the Layer class.
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
	
	
	}

	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom){
		return false;
	}
	
	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		counter++;
		if (counter >= speed) {
			counter = 0;
			incrementSprite();
		}
	
	}
	
	public void reset(){
		counter = 0;
		currentSprite = startSprite;
	}
	
	public void setAnimationRange(int startSprite, int endSprite){
		this.startSprite = startSprite;
		this.endSprite = endSprite;
		reset();
	}
	
	
	public int getWidth(){
		return sprites[currentSprite].getWidth();
	}
	public int getHeight(){
		return sprites[currentSprite].getHeight();
	}
	public int[] getPixels(){
		return sprites[currentSprite].getPixels();
	}
	
	public void incrementSprite(){
		currentSprite++;
		if (currentSprite >= endSprite) {
			currentSprite = startSprite;
		}
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		
		return -1;
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

}
