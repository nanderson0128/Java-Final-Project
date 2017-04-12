
public class Player implements GameObject{

	Rectangle playerRectangle;
	private int speed = 8;

	//0 = Right, 1 = Left, 2 = Up, 3 = Down
	private int direction = 0;
	private Sprite sprite;
	private AnimatedSprite animatedSprite = null;
	private int mostRecentKey = -1;

	
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
		mostRecentKey = keyListener.getRecentKey();
		boolean didMove = false;
		int newDirection = direction;
		
		
		if(keyListener.right() && mostRecentKey == 0){
			playerRectangle.x += speed;
			newDirection = 0;
			didMove = true;	
		}
		
		if(keyListener.left() && mostRecentKey == 1){
			playerRectangle.x -= speed;
			newDirection = 1;
			didMove = true;
		}

		if(keyListener.up() && mostRecentKey == 2){
			playerRectangle.y -= speed;
			newDirection = 2;
			didMove = true;
		}
		
		if(keyListener.down() && mostRecentKey == 3){
			playerRectangle.y += speed;
			newDirection = 3;
			didMove = true;
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
