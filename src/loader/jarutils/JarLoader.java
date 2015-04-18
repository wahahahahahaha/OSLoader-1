package loader.jarutils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import loader.Settings;
import loader.game.RSAppletStub;
import loader.hooks.HookLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class JarLoader extends ClassLoader {

	private RSAppletStub appleStub;
	private String gamepackUrl;
	private Hashtable<String, ClassNode> classnodes;
	private HookLoader hookLoader;

	private ClassLoader classLoader;

	public JarLoader() {
		appleStub = new RSAppletStub();
		classnodes = new Hashtable<String, ClassNode>();
		gamepackUrl = appleStub.getLink() + appleStub.getParameter("initial_jar");
		loadJar();
		
		JarInjector injector = new JarInjector(classnodes);
		injector.run();
		classnodes = injector.getClassnodes();

		try {
			classLoader = URLClassLoader.newInstance(new URL[] { injector.getInjectedJar().toURI().toURL() });
		} catch (Exception e) {
			e.printStackTrace();
		}

		hookLoader = new HookLoader(this);
		hookLoader.manualLoad();

	}

	public RSAppletStub getAppletStub() {
		return appleStub;
	}

	public Hashtable<String, ClassNode> getClassnodes() {
		return classnodes;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return classLoader.loadClass(name);
	}
	
	private void loadJar() {
		try {
			if (Settings.DOWNLOAD_GAMEPACK) {
				long start = System.currentTimeMillis();
				ReadableByteChannel rbc = Channels.newChannel(new URL(gamepackUrl).openStream());
				FileOutputStream fos = new FileOutputStream("gamepack.jar");
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

				System.out.println("Gamepack download took " + (System.currentTimeMillis() - start) + "ms");

			} else {
				System.out.println("Using cached gamepack");
			}
			
			JarFile jar = new JarFile(new File("gamepack.jar"));
			Enumeration<JarEntry> en = jar.entries();
			while (en.hasMoreElements()) {
				JarEntry entry = en.nextElement();
				if (entry.getName().endsWith(".class")) {
					ClassReader cr = new ClassReader(jar.getInputStream(entry));
					ClassNode cn = new ClassNode();
					cr.accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
					classnodes.put(cn.name, cn);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}