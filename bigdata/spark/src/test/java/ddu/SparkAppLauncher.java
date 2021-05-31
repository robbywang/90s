package ddu;

import java.io.IOException;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.junit.Test;

public class SparkAppLauncher {

  @Test
  public void startApplication() throws IOException {
    SparkAppHandle handle = new SparkLauncher()
        .setAppResource("target/app.jar")
        .setMainClass("my.spark.app.Main")
        .setMaster("local")
        .setConf(SparkLauncher.DRIVER_MEMORY, "512m")
        .startApplication();
    // Use handle API to monitor / control application.
  }

  @Test
  public void launch() throws IOException, InterruptedException {
    Process spark = new SparkLauncher()
        .setAppResource("target/ddu-spark.jar")
        .setMainClass("ddu.spark.rdd.ScalaParallelismTest")
        .setMaster("local")
        .setConf(SparkLauncher.DRIVER_MEMORY, "512m")
        .setDeployMode("client")
        .launch();
    spark.waitFor();
  }
}
