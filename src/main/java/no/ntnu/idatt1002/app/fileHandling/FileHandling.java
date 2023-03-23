package no.ntnu.idatt1002.app.fileHandling;

import no.ntnu.idatt1002.app.data.User;

import java.io.*;

/**
 * FileHandling class is used to store a user.
 */
public class FileHandling {

  // TODO(ingar): This should not
  private static String file = System.getProperty("user.dir") + "/src/main/resources/user.ser";

  /**
   * This methode is used to write a user object to user.txt file.
   * 
   * @param user is the user object you want to write to the file.
   * @throws IOException
   */
  public static void writeUserToFile(User user) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

    objectOutputStream.writeObject(user);
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  /**
   * This methode is used to read a user object from the user.txt file.
   * 
   * @return the user stored in user.txt file.
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static User readUserFromFile() throws IOException, ClassNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(file);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

    User user = (User) objectInputStream.readObject();
    objectInputStream.close();

    return user;
  }
}
