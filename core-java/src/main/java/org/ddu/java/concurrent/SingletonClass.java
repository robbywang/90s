package org.ddu.java.concurrent;

/**
 * Date: 2016年5月16日 上午10:03:07 <br/>
 *
 * @author wangtao
 */
public class SingletonClass {

  private static SingletonClass instance;

  private SingletonClass() {};

  public synchronized static SingletonClass getInstance() {
    if (instance == null) {
      instance = new SingletonClass();
    }
    return instance;
  }

  // ----
  private static class SingletonClassHolder {
    public static SingletonClass instance = new SingletonClass();
  }

  public static SingletonClass getInstance2() {
    return SingletonClassHolder.instance;
  }

  public static SingletonClass getInstance3() {
    // 这里instance 要声明为volatile。
    if (instance == null) { // 二次检查，比直接用独占锁效率高
      synchronized (SingletonClass.class) {
        if (instance == null) {
          instance = new SingletonClass();
        }
      }
    }
    return instance;
  }
}
