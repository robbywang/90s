/**
 * Project Name:r1jo
 * File Name:Tester.java
 * Package Name:org.r1jo.jdk.classload
 * Date:2016年4月10日上午8:40:41
 *
*/

package org.ddu.java.classloader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * ClassName:Tester <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月10日 上午8:40:41 <br/>
 * @author   wangtao
 * @version
 * @see
 */
public class ClassLoaderNamesapceTest {

  private static final String path1 = "/Users/wangtao/Work/tmp/path1/test.jar";
  private static final String path2 = "/Users/wangtao/Work/tmp/path2/test.jar";

  public static void main(String[] args) {
    File dir1 = new File(path1);
    File dir2 = new File(path2);
    URL url1, url2;
    try {
      url1 = dir1.toURL();
      url2 = dir2.toURL();
      System.out.println(url1.toString());
      System.out.println(url2.toString());

      ClassLoader cL1 = new URLClassLoader(new URL[] {url1});
      ClassLoader cL2 = new URLClassLoader(new URL[] {url2});

      System.out.println(String.format("%s,%s, %s", ClassLoaderNamesapceTest.class.getName(),
          ClassLoaderNamesapceTest.class.getClassLoader(), ClassLoaderNamesapceTest.class.getClassLoader().getParent()));

      // class1 and class2 belongs to different namespace.
      // they can not see each other.
      // ToLoadClass is not in current classpath.
      String className1 = "org.r1jo.jdk.classload.ToLoadClass";
      Class<?> c1 = Class.forName(className1, false, cL1);
      System.out.println(String.format("%s,%s, %s", c1.getName(), c1.getClassLoader(),
          c1.getClassLoader().getParent()));

      String className2 = "org.r1jo.jdk.classload.ToLoadClass";
      Class<?> c2 = Class.forName(className2, false, cL2);
      System.out.println(String.format("%s,%s, %s", c2.getName(), c2.getClassLoader(),
          c2.getClassLoader().getParent()));

      Object o1 = c1.newInstance();
      Object o2 = c2.newInstance();

      Field f1 = c1.getDeclaredField("i");
      Field f2 = c2.getDeclaredField("i");
      f1.setAccessible(true);
      f2.setAccessible(true);

      f1.set(o1, 2);
      Method m1 = c1.getDeclaredMethod("getValue");
      int r1 = (Integer) m1.invoke(o1);
      System.out.println(r1);

      Method m2 = c2.getDeclaredMethod("getValue");
      int r2 = (Integer) m2.invoke(o2);
      System.out.println(r2);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
