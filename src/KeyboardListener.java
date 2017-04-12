import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class KeyboardListener implements KeyListener, FocusListener{

	public boolean[] keys = new boolean[120];
	
	private Game game;

	private int mostRecentKey;
	
	public KeyboardListener(Game game){
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		int keyCode = event.getKeyCode();
		mostRecentKey = -1;
		
		if(keyCode < keys.length){
			keys[keyCode] = true;
		}
		
		if(keys[KeyEvent.VK_CONTROL]){
			game.handleCTRL(keys);
		}
		
		
		
		if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
			mostRecentKey = 0;
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
		if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
			mostRecentKey = 1;
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 0;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
		if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
			mostRecentKey = 2;
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
		if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
			mostRecentKey = 3;
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 3;
			}
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub
		int keyCode = event.getKeyCode();
		if(keyCode < keys.length){
			keys[keyCode] = false;
		}
		
		
		if(event.getKeyCode() == 68 || event.getKeyCode() == 39){
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
		
		if(event.getKeyCode() == 65 || event.getKeyCode() == 37){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 0;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
	
		if(event.getKeyCode() == 87 || event.getKeyCode() == 38){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 0;
			}
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey = 3;
			}
		}
		
		if(event.getKeyCode() == 83 || event.getKeyCode() == 40){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey = 0;
			}
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey = 2;
			}
		}

		//System.out.println(event.getKeyCode());
	}
	@Override
	public void focusLost(FocusEvent event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void focusGained(FocusEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean up(){
		return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
	}
	public boolean down(){
		return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
	}
	
	public boolean left(){
		return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
	}
	public boolean right(){
		return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
	}
	
	public int getRecentKey(){
		return mostRecentKey;
	}

}
