
public class SDKButton extends GUIButton{


	
	public SDKButton(Sprite sprite, Rectangle rect) {
		super(sprite, rect);
		// TODO Auto-generated constructor stub
		rect.generateGraphics(0xFFFFDB3D);
		
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect){
		Rectangle offsetRectangle = new Rectangle(rect.x + interfaceRect.x, rect.y + interfaceRect.y, rect.w, rect.h);
		renderer.renderRectangle(rect, xZoom, yZoom, fixed);
		renderer.renderSprite(sprite, rect.x + interfaceRect.x, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
	}
	
	public void activate()
	{

	}

}
