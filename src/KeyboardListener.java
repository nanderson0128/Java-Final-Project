import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;


public class KeyboardListener implements KeyListener, FocusListener{

	public boolean[] keys = new boolean[120];
	public String[] chestItems = {"Sword.1", "Sword.2", "Sword.3", "Sword.4", "Sword.5", "Sword.6", };
	
	public static boolean nextToChest = false;
	private Game game;
	public static boolean chestOpened = false;

	private int[] mostRecentKey;

	
	public KeyboardListener(Game game){
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub
		
		int keyCode = event.getKeyCode();
		mostRecentKey = new int[4];
		if(keyCode < keys.length){
			keys[keyCode] = true;
		}
		
		if(keys[KeyEvent.VK_CONTROL]){
			game.handleCTRL(keys);
		}
		

		if(event.getKeyCode() == 32 && nextToChest){
			double RNG = Math.random() * 6;
			System.out.println(chestItems[(int)(RNG)]);
			chestOpened = true;
		}
		
		if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
			if(mostRecentKey[0] == 0){
				if(mostRecentKey[1] == 0 && mostRecentKey[2] == 0 && mostRecentKey[3] == 0){
					mostRecentKey[0] = 1;
				}
				if(mostRecentKey[1] == 1){
					mostRecentKey[0] = 1;
					mostRecentKey[1] = 2;
				}
				if(mostRecentKey[2] == 1){
					mostRecentKey[0] = 1;
					mostRecentKey[2] = 2;
				}
				if(mostRecentKey[3] == 1){
					mostRecentKey[0] = 1;
					mostRecentKey[3] = 2;
				}
					
			}

		}
		if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
			if(mostRecentKey[1] == 0){
				if(mostRecentKey[0] == 0 && mostRecentKey[2] == 0 && mostRecentKey[3] == 0){
					mostRecentKey[1] = 1;
				}
				if(mostRecentKey[0] == 1){
					mostRecentKey[1] = 1;
					mostRecentKey[0] = 2;
				}
				if(mostRecentKey[2] == 1){
					mostRecentKey[1] = 1;
					mostRecentKey[2] = 2;
				}
				if(mostRecentKey[3] == 1){
					mostRecentKey[1] = 1;
					mostRecentKey[3] = 2;
				}
					
			}

		}
		if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
			if(mostRecentKey[2] == 0){
				if(mostRecentKey[1] == 0 && mostRecentKey[0] == 0 && mostRecentKey[3] == 0){
					mostRecentKey[2] = 1;
				}
				if(mostRecentKey[0] == 1){
					mostRecentKey[2] = 1;
					mostRecentKey[0] = 2;
				}
				if(mostRecentKey[1] == 1){
					mostRecentKey[2] = 1;
					mostRecentKey[1] = 2;
				}
				if(mostRecentKey[3] == 1){
					mostRecentKey[2] = 1;
					mostRecentKey[3] = 2;
				}
					
			}
		}
		if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
			if(mostRecentKey[3] == 0){
				if(mostRecentKey[1] == 0 && mostRecentKey[2] == 0 && mostRecentKey[0] == 0){
					mostRecentKey[3] = 1;
				}
				if(mostRecentKey[1] == 1){
					mostRecentKey[3] = 1;
					mostRecentKey[1] = 2;
				}
				if(mostRecentKey[2] == 1){
					mostRecentKey[3] = 1;
					mostRecentKey[2] = 2;
				}
				if(mostRecentKey[0] == 1){
					mostRecentKey[3] = 1;
					mostRecentKey[0] = 2;
				}
					
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
				mostRecentKey[0] = 0;
				mostRecentKey[1] = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey[0] = 0;
				mostRecentKey[2] = 1;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey[0] = 0;
				mostRecentKey[2] = 1;
			}
		}
		
		if(event.getKeyCode() == 65 || event.getKeyCode() == 37){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey[1] = 0;
				mostRecentKey[0] = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey[1] = 0;
				mostRecentKey[2] = 1;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey[1] = 0;
				mostRecentKey[3] = 1;
			}
		}
	
		if(event.getKeyCode() == 87 || event.getKeyCode() == 38){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey[2] = 0;
				mostRecentKey[0] = 1;
			}
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey[2] = 0;
				mostRecentKey[1] = 1;
			}
			if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
				mostRecentKey[2] = 0;
				mostRecentKey[3] = 1;
			}
		}
		
		if(event.getKeyCode() == 83 || event.getKeyCode() == 40){
			if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				mostRecentKey[3] = 0;
				mostRecentKey[0] = 1;
			}
			if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
				mostRecentKey[3] = 0;
				mostRecentKey[1] = 1;
			}
			if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]){
				mostRecentKey[3] = 0;
				mostRecentKey[2] = 1;
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
	
	public int[] getRecentKey(){
		return mostRecentKey;
	}
	public boolean getSpacePress(){
		return nextToChest;
	}

}
