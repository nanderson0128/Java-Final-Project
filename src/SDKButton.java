
public class SDKButton extends GUIButton{


	
	public SDKButton(Sprite sprite, Rectangle rect) {
		super(sprite, rect);
		// TODO Auto-generated constructor stub
		rect.generateGraphics(0xFFDB3D);
		
	}

	@Override
	public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect){
		
		renderer.renderRectangle(rect, interfaceRect, xZoom, yZoom, fixed);
		renderer.renderSprite(sprite, rect.x + interfaceRect.x + (xZoom - (xZoom - 1)) * rect.w/2, rect.y + interfaceRect.y + (yZoom - (yZoom - 1)) * rect.h/2, xZoom - 1, yZoom - 1, fixed);
	}
	
	public void activate()
	{
		System.out.println("Clicked");
	}

}
