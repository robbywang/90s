package org.ddu.java.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerImpl implements InvocationHandler {

  // 目标对象
  private Object target;

  /**
   * 构造方法
   *
   * @param target 目标对象
   */
  public InvocationHandlerImpl(Object target) {
    super();
    this.target = target;
  }

  /**
   * 执行目标对象的方法
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    // 在目标对象的方法执行之前简单的打印一下
    System.out.println("------------------before------------------");

    // 执行目标对象的方法
    Object result = method.invoke(target, args);

    // 在目标对象的方法执行之后简单的打印一下
    System.out.println("-------------------after------------------");

    return result;
  }
}