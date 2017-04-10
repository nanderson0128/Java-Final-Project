
public class Player implements GameObject{

	Rectangle playerRectangle;
	private int speed = 8;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;

	
	public Player(Sprite sprite){
		this.sprite = sprite;
		if(sprite != null && sprite instanceof AnimatedSprite){
			animatedSprite = (AnimatedSprite) sprite;
		}
		
		updateDirection();
		playerRectangle = new Rectangle(32, 16, 16, 32);
		playerRectangle.generateGraphics(3, 0xFF00FF90);
	}
	
	private void updateDirection(){
		if(animatedSprite != null)
		{
			animatedSprite.setAnimationRange(direction * 8, direction * 8 + 7);
		}
			
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub

		if(animatedSprite != null){
				renderer.renderSprite(animatedSprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else if (sprite != null){
			renderer.renderSprite(sprite, playerRectangle.x, playerRectangle.y, xZoom, yZoom, false);
		}
		else{
			renderer.renderRectangle(playerRectangle, xZoom, yZoom, false);
		}
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		KeyboardListener keyListener = game.getKeyListener();
		
		int recentKey = -1;
		boolean didMove = false;
		int newDirection = direction;
		
		if(keyListener.left() && !keyListener.right() && !keyListener.up() && !keyListener.down()){
			playerRectangle.x -= speed;
			newDirection = 1;
			didMove = true;
			recentKey = 1;
			System.out.println(recentKey);
		}
		if(keyListener.right() && !keyListener.up() && !keyListener.down()){
			playerRectangle.x += speed;
			newDirection = 0;
			didMove = true;	
			recentKey = 0;
			System.out.println(recentKey);
		}
		if(keyListener.up() && !keyListener.down()){
			playerRectangle.y -= speed;
			newDirection = 2;
			didMove = true;
			recentKey = 2;
			System.out.println(recentKey);
		}
		if(keyListener.down()){
			playerRectangle.y += speed;
			newDirection = 3;
			didMove = true;
			recentKey = 3;
			System.out.println(recentKey);
		}
		
		
		if(newDirection != direction){
			direction = newDirection;
			updateDirection();
		}

		if(!didMove){
			animatedSprite.reset();
		}
		
		updateCamera(game.getRenderer().getCamera());
		if(didMove){
			animatedSprite.update(game);
		}
		
	}
	
	public void updateCamera(Rectangle camera){
		camera.x = playerRectangle.x - (camera.w / 2);
		camera.y = playerRectangle.y - (camera.h / 2);
	}
	public void handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom){
		
	}
}
