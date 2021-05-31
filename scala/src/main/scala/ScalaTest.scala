object ScalaTest {
  def main(args: Array[String]): Unit = {
    val v = 1
    //    v = 2
    println(v)

    // block
    println(
      {
        var a = 5
        a + 3
      }
    )

    var myTrait0: MyTrait = new MyCalss0()
    myTrait0.test()

    myTrait0 = new MyCalss1("abc")
    myTrait0.test()

    val myTrait1 = new MyCalss1("robby")
    myTrait1.test()

    val myCaseClass = MyCaseClass("robby", 18)
    println(myCaseClass.age)

    myCaseClass.test()

    ScalaTest.objTest("Robby")

    var a : AnyRef = new MyCalss1("")

  }

  def objTest(param: String) : Unit = {
    println(param)

  }
}
