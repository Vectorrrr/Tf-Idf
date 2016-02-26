package test;

import logic.TfCalcer;
import model.UserAnswerFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by igladush on 26.02.16.
 */
public class MainTester {
  private final static String ERROR_WRITE = "I cna't write in the file";
  private final static String PATH = "/home/igladush/IdeaProjectsSecretssss";
  private final static String FIRST_FILE = "/first.txt";
  private final static String SECOND_FILE = "/second.txt";
  private final static String THIRD_FILE = "/third.txt";
  private final static double ACCURACY=0.000000001;
  private TfCalcer calcer;

  @Before
  public void init() throws FileNotFoundException {

    calcer=new TfCalcer();

    File file = new File(PATH);

    if (!file.exists()) {
      file.mkdirs();
      try (Writer fw = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(PATH + FIRST_FILE), "utf-8"))) {
        fw.write("Vasya, Vanya\nPetya Katya");
      } catch (IOException e) {
        System.out.println(ERROR_WRITE);
      }

      try (Writer fw = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(PATH + SECOND_FILE), "utf-8"))) {
        fw.write("Vasya.\n Vanya\nPetya\nVanya");
      } catch (IOException e) {
        System.out.println(ERROR_WRITE);
      }

      try (Writer fw = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(PATH + THIRD_FILE), "utf-8"))) {
        fw.write("Petya\nSveta");
      } catch (IOException e) {
        System.out.println(ERROR_WRITE);
      }
    }

  }

  @org.junit.Test
  public void existWordTest() {
    List<UserAnswerFormat> ans= calcer.getTfIdf(PATH + " VVVV");
    assertEquals(0,ans.size());
  }
  @Test
  public void oneAnswerWordTest(){
      List<UserAnswerFormat> usFormat=calcer.getTfIdf(PATH+" Sveta");
      assertEquals(1,usFormat.size());
      assertTrue((PATH+THIRD_FILE).equals(usFormat.get(0).getPath()));
      assertEquals(1.5,usFormat.get(0).getTfIdf(),ACCURACY);
  }

  @Test
  public void twoAnswerWordTest(){
    List<UserAnswerFormat> usFormat=calcer.getTfIdf(PATH+" Vanya");
    assertEquals(2,usFormat.size());
    assertTrue((PATH+SECOND_FILE).equals(usFormat.get(0).getPath()));
    assertEquals(0.75,usFormat.get(0).getTfIdf(),ACCURACY);

    assertTrue((PATH+FIRST_FILE).equals(usFormat.get(1).getPath()));
    assertEquals(0.375,usFormat.get(1).getTfIdf(),ACCURACY);
  }

  @Test
  public void threeAnswerWordTest(){
    List<UserAnswerFormat> usFormat=calcer.getTfIdf(PATH+" Petya");
    assertEquals(3,usFormat.size());
    assertTrue((PATH+THIRD_FILE).equals(usFormat.get(0).getPath()));
    assertEquals(0.5,usFormat.get(0).getTfIdf(),ACCURACY);

    assertTrue((PATH+FIRST_FILE).equals(usFormat.get(1).getPath()));
    assertEquals(0.25,usFormat.get(1).getTfIdf(),ACCURACY);

    assertTrue((PATH+SECOND_FILE).equals(usFormat.get(2).getPath()));
    assertEquals(0.25,usFormat.get(2).getTfIdf(),ACCURACY);
  }

}
