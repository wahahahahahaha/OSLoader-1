package loader;

public class Boot {
	public static void main(String[] args) {
		System.out.println("Running loader...");
		LoaderWindow loaderWindow = new LoaderWindow();
		loaderWindow.setVisible(true);
		loaderWindow.pack();
		
	} 
}
