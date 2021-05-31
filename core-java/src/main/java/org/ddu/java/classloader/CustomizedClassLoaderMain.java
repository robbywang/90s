package org.ddu.java.classloader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Date: 2016年5月28日 下午7:24:04 <br/>
 * 引导类加载器 == null
 */
public class CustomizedClassLoaderMain {

  public static void main(String[] args) {

    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    System.out.println("系统类装载器:" + systemClassLoader);
    ClassLoader extClassLoader = systemClassLoader.getParent();
    System.out.println("系统类装载器的父类加载器——扩展类加载器:" + extClassLoader);
    ClassLoader bootClassLoader = extClassLoader.getParent();
    System.out.println("扩展类加载器的父类加载器——引导类加载器:" + bootClassLoader);

    URL url = ClassInClassPath.class.getProtectionDomain().getCodeSource().getLocation();
    System.out.println("url : " + url);
    
    // parent set null, 表示用bootstrap ClassLoader为parent.
    MyURLClassLoader myLoaderWithNullParent = new MyURLClassLoader(url, null);
    MyURLClassLoader myLoaderWithParent = new MyURLClassLoader(url, CustomizedClassLoaderMain.class.getClassLoader());
    URLClassLoader urlloaderWithNullParent = new URLClassLoader(new URL[] {url}, null);
    URLClassLoader urlloaderWithParent = new URLClassLoader(new URL[] {url}, CustomizedClassLoaderMain.class.getClassLoader());
    System.out.println(String.format("myLoaderWithNullParent classLoader: %s, parent: %s",
    		myLoaderWithNullParent.getClass().getClassLoader(), myLoaderWithNullParent.getParent()));
    System.out.println(String.format("myLoaderWithParent classLoader: %s, parent: %s",
    		myLoaderWithParent.getClass().getClassLoader(), myLoaderWithParent.getParent()));
    System.out.println(String.format("urlloaderWithNullParent classLoader: %s, parent: %s",
    		urlloaderWithNullParent.getClass().getClassLoader(), urlloaderWithNullParent.getParent()));
    System.out.println(String.format("urlloaderWithParent classLoader: %s, parent: %s",
    		urlloaderWithParent.getClass().getClassLoader(), urlloaderWithParent.getParent()));
 
    Class<?> clazz;
    
    try {
    	clazz = myLoaderWithNullParent.loadClass("org.ddu.java.classloader.ClassInClassPath");
    	System.out.println("myLoaderWithNullParent Loaded class's classloader: " + clazz.getClassLoader().getClass());
    } catch (Exception e) {
    	e.printStackTrace();
    }

    try {
    	clazz = myLoaderWithParent.loadClass("org.ddu.java.classloader.ClassInClassPath");
    	System.out.println("myLoaderWithParent Loaded class's classloader: " + clazz.getClassLoader().getClass());
    } catch (Exception e) {
    	e.printStackTrace();
    }
    
    try {
    	clazz = urlloaderWithNullParent.loadClass("org.ddu.java.classloader.ClassInClassPath");
    	System.out.println("urlloaderWithNullParent Loaded class's classloader: " + clazz.getClassLoader().getClass());
    } catch (Exception e) {
    	e.printStackTrace();
    }
    
    try {
    	clazz = urlloaderWithParent.loadClass("org.ddu.java.classloader.ClassInClassPath");
    	System.out.println("urlloaderWithParent Loaded class's classloader: " + clazz.getClassLoader().getClass());
    } catch (Exception e) {
    	e.printStackTrace();
    }
    
		try {
			URL notInClassPathURL = new URL("file:/Users/taowang/git/ddu/ddu-staging/target/classes/");
			MyURLClassLoader notInClassPathURLLoader = new MyURLClassLoader(notInClassPathURL, null);
			Class<?> clazz2 = notInClassPathURLLoader.loadClass("org.ddu.java.classloader.ClassInClassPath2");
			System.out.println(clazz2.getClassLoader());
			notInClassPathURLLoader.close();
		} catch (Exception e) {
			System.out.println("notInClassPathURLLoader cannot load class in classpath, " + e.getMessage());
		}
    
		try {
			URL notInClassPathURL = new URL("file:/Users/taowang/git/ddu/staging/target/classes/");
			System.out.println("notInClassPathURL : " + notInClassPathURL);
			MyURLClassLoader notInClassPathURLLoader = new MyURLClassLoader(notInClassPathURL, null);
			Class<?> clazzNotInClassPath = notInClassPathURLLoader.loadClass("test.ClassNotInClassPath");
			System.out.println(clazzNotInClassPath.getClassLoader());
			
			// Invoke the dynamic loaded classes.
			Method m = clazzNotInClassPath.getDeclaredMethod("hello", String.class);
			m.invoke(clazzNotInClassPath.newInstance(), "robby");
			
	      // java 类的 ClassLoader 永远是 bootstrap ClassLoader : null.
	      clazz = myLoaderWithNullParent.loadClass("java.net.URL");
	      System.out.println(clazz.getClassLoader());
			
	     
	      Class<?> class1 = notInClassPathURLLoader.loadClass("test.ClassNotInClassPath");
	      System.out.println("class1: " + class1.hashCode());
	      Class<?> class2 = notInClassPathURLLoader.loadClass("test.ClassNotInClassPath");
	      System.out.println("class2: " + class2.hashCode());
	      if (class1.equals(class2)) {
	    	  System.out.println("class1 equals class2");
	      }
	      
	      MyURLClassLoader notInClassPathURLLoader2 = new MyURLClassLoader(notInClassPathURL, null);
	      Class<?> class3 = notInClassPathURLLoader2.loadClass("test.ClassNotInClassPath");
	      // class3 loaded from different classLoader.
	      System.out.println("class3: " + class3.hashCode());
	      if (class3.equals(class1)) {
	    	  System.out.println("class1 equals class3");
	      }
	      
		  notInClassPathURLLoader.close();
		  notInClassPathURLLoader2.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 }

}
