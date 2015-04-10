package base;

public class Test {
	public static void main(String[] args) {
		Window window = new Window("TEST", 800, 600);
		Material.loadMaterial(window);
		Game current = window.newMapEditor();
		window.startGame();
	}

}
