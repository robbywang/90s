/**
 * Project Name:r1jo
 * File Name:Tester.java
 * Package Name:org.r1jo.jdk.reflect
 * Date:2016年4月9日下午5:41:55
 *
*/

package org.ddu.java.dynamicproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ClassName:Tester <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年4月9日 下午5:41:55 <br/>
 * @version
 * @see
 */
public class DynamicProxyMain {

  public static void main(String[] args) {
    try {
      testProxy();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
  }

  public static void testProxy()
      throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException, InstantiationException {
    // 实例化目标对象
    TargetService targetService = new TargetServiceImpl();

    // 1. 直接调用。
    System.out.println("1. 直接调用。");
    targetService.add();

    // 2. 反射调用。
    System.out.println("2. 反射调用。");
    Class<TargetServiceImpl> targetServiceClass = TargetServiceImpl.class;
    Method addMethod = targetServiceClass.getDeclaredMethod("add");
    addMethod.invoke(targetServiceClass.newInstance());

    // 3. 动态代理调用。
    System.out.println("3. 动态代理调用。");
    InvocationHandlerImpl invocationHandler = new InvocationHandlerImpl(targetService);
    Object obj = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
        targetService.getClass().getInterfaces(), invocationHandler);
    TargetService proxyOfTarget = (TargetService) obj;
    // 调用代理对象的方法
    proxyOfTarget.add();
  }
}
