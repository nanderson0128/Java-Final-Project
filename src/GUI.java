
public class GUI implements GameObject{

	private Sprite backgroundSprite;
	private GUIButton[] buttons;
	private Rectangle rect = new Rectangle();
	private boolean fixed;
	private Game game;
	public GUI(Sprite backgroundSprite, GUIButton[] buttons, int x, int y, boolean fixed){
		this.backgroundSprite = backgroundSprite;
		this.buttons = buttons;
		this.fixed = fixed;
		
		rect.x = x;
		rect.y = y;
				
		if(backgroundSprite != null){
			rect.w = backgroundSprite.getWidth();
			rect.h = backgroundSprite.getHeight();
		}
		
	}
	
	public GUI(GUIButton[] buttons, int x, int y, boolean fixed){
		
		this(null, buttons, x, y, fixed);
		
	}
	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		while(KeyboardListener.chestOpened){
			game.getInvImage().createGraphics();
		}
		if(backgroundSprite != null){
			renderer.renderSprite(backgroundSprite, rect.x, rect.y, xZoom, yZoom, fixed );
		}
		if(buttons != null){
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].render(renderer, xZoom, yZoom, rect);
			}
		}
	}

	@Override
	public void update(Game game) {
		// TODO Auto-generated method stub
		if (buttons != null) {
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].update(game);
			}
		}
	}

	@Override
	public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
		// TODO Auto-generated method stub
		boolean stopChecking = false;
		if(!fixed){
			mouseRectangle = new Rectangle(mouseRectangle.x + camera.x, mouseRectangle.y + camera.y, 1, 1);	
		}
			
		if(rect.w == 0 || rect.h == 0 || mouseRectangle.intersects(rect)){
			mouseRectangle.x -= rect.x;
			mouseRectangle.y -= rect.y;
			for (int i = 0; i < buttons.length; i++) {
				boolean result = buttons[i].handleMouseClick(mouseRectangle, camera, xZoom, yZoom);	
				if(stopChecking == false){
					stopChecking = result;
				}
			}
		 }
		return stopChecking;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return rect;
	}
}
