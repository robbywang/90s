package org.ddu.java.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class MyURLClassLoader extends URLClassLoader {
	public MyURLClassLoader(URL url, ClassLoader parent) {
		super(new URL[] { url }, parent);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);
//		try {
//			return super.loadClass(name, resolve);
//		} catch (ClassNotFoundException e) {
//			return Class.forName(name, resolve, LoadAndUnloadMain.class.getClassLoader());
//		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println(this.toString() + " - CL Finalized.");
	}
}
