package no.ntnu.idatt1002.app.filehandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import no.ntnu.idatt1002.app.User;

/**
 * A FileHandling utility class for managing User objects by storing and retrieving them
 * from a file. It provides methods to write a User object to a file and read a User object
 * from a file.
 */
public class FileHandling {
  
  private static final String FILE_NAME = "user.ser";
  private static final String DATA_DIR = System.getProperty("user.home") + File.separator
      + ".budgetingandaccountingappdata";
  private static final String FILE_PATH = DATA_DIR + File.separator + FILE_NAME;
  
  /**
   * Writes a User object to a file.
   *
   * @param user The User object to be written to the file.
   * @throws IOException If an error occurs during the file writing process.
   */
  public static void writeUserToFile(User user) throws IOException {
    File dataDir = new File(DATA_DIR);
    if (!dataDir.exists() && !dataDir.mkdirs()) {
      throw new IOException("Failed to create the data directory");
    }
    
    FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    
    objectOutputStream.writeObject(user);
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  /**
   * Reads a User object from a file. The file is located in the "src/main/resources"
   * directory and is named "user.ser".
   *
   * @return A User object read from the file.
   * @throws IOException If an error occurs during the file reading process.
   * @throws ClassNotFoundException If the serialized User class is not found.
   */
  public static User readUserFromFile() throws IOException, ClassNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

    User user = (User) objectInputStream.readObject();
    objectInputStream.close();

    return user;
  }
}
