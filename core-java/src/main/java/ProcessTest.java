import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessTest {
  public static void main(String[] args) {
    execAndWait("ls");

  }

  static boolean execAndWait(String command) {
    boolean success = false;
    Process process = null;
    try {
      
//      List<String> commandList = new ArrayList<>();
//      commandList.add("bash");
//      commandList.add("/Users/taowang/Desktop/test.sh");
//      commandList.add("--cityName");
//      commandList.add("abcde");
      
      String cmd = "bash /Users/taowang/Desktop/test.sh  --macFile file --cityName robby";
      List<String> commandList = Arrays.asList(cmd.split(" +"));
      
//      ProcessBuilder pbuilder = new ProcessBuilder("bash", "/Users/taowang/Desktop/test.sh", "--cityName", "abc");
      ProcessBuilder pbuilder = new ProcessBuilder(commandList);
      pbuilder.redirectErrorStream(true);
      process = pbuilder.start();
      // process = Runtime.getRuntime().exec(command);
      printProcessLog(process);

      process.waitFor();
      System.out.println("command finish : " + command);
      if (process.exitValue() == 0) {
        success = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return success;
  }

  private static void printProcessLog(Process process) throws IOException {
    BufferedReader proessInputReader =
        new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
    // BufferedReader proessErrorReader = new BufferedReader(new
    // InputStreamReader(process.getErrorStream(), "UTF-8"));
    String msg;
    while ((msg = proessInputReader.readLine()) != null) {
      System.out.println(msg);
    }
    // while ((msg = proessErrorReader.readLine()) != null) {
    // log.info(msg);
    // }
  }

}
