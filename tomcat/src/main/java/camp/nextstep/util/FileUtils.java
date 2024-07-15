package camp.nextstep.util;

public class FileUtils {

  public static String getFileExtension(String fileName) {
    if (fileName == null || fileName.lastIndexOf('.') == -1) {
      return "";
    }
    return fileName.substring(fileName.lastIndexOf('.') + 1);
  }

}
