package org.ddu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest {

  public static void main(String[] args) {
//    String a = new String("a");
//    String a1 = "a";
//    System.out.println(a == a1);
//    System.out.println(a.equals(a1));
//    System.out.println(a == a1.intern());
    try {
      test(new A());
      test(new B());
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static void test(Object obj) throws IOException, ClassNotFoundException {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(obj);

    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);

    Object o = ois.readObject();

    if (o instanceof A) {
      System.out.println(((A) o).num);
      System.out.println(((A) o).num2);
    } else {
      System.out.println(((B) o).num);
      System.out.println(((B) o).num2);
    }
  }

}

class A implements Serializable {

  public int num = 10;
  public int num2 = 101;

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    System.out.println("A readObject");
    num = in.readInt();
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
//    out.defaultWriteObject();
    out.writeInt(num2);
    System.out.println("A writeObject");
  }

//  private Object readResolve() {
//    System.out.println("A readResolve()");
//    return  new B();
//  }
}

class B implements Externalizable {

  public B() {
  }

  ;
  public transient int num = 12;
  public int num2 = 121;

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    System.out.println("B writeExternal");

  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    System.out.println("B readExternal");

  }
}
