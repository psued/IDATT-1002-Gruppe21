package no.ntnu.idatt1002.app.fileHandling;

import no.ntnu.idatt1002.app.data.User;

import java.io.*;

/**
 * A FileHandling utility class for managing User objects by storing and retrieving them
 * from a file. It provides methods to write a User object to a file and read a User object
 * from a file.
 */
public class FileHandling {

  private static final String file = System.getProperty("user.dir") + "/src/main/resources/user.ser";

  /**
   * Writes a given User object to a file. The file is located in the "src/main/resources"
   * directory and is named "user.ser".
   *
   * @param user The User object to be written to the file.
   * @throws IOException If an error occurs during the file writing process.
   */
  public static void writeUserToFile(User user) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
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
    FileInputStream fileInputStream = new FileInputStream(file);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

    User user = (User) objectInputStream.readObject();
    objectInputStream.close();

    return user;
  }
}
