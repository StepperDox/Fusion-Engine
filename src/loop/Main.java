package loop;

import primary.KeyStone;
import primary.WindowController;
import utility.ConfigLoader;
import utility.Console;
import utility.FPSCounter;

public class Main {
	public static void main(String[] args) {
		//Start the window:
		Console.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ConfigLoader.init();
		ConfigLoader.loadConfig("Default");
		WindowController.start();
		//Initialize everything here:
		KeyStone stone = new KeyStone();
		
		
		//Update everything here:
		while(WindowController.canRun()){
			FPSCounter.update();
			
			
			//Update this last:
			WindowController.update();
		}
		//Exit things out here / clean up:
		stone.cleanUp();
		
		
		//Finally close the window:
		WindowController.exit(0);
	}
}
