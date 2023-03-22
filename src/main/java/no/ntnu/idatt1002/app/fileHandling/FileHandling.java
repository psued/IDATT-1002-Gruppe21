package no.ntnu.idatt1002.app.fileHandling;

import no.ntnu.idatt1002.app.data.Project;
import no.ntnu.idatt1002.app.data.ProjectRegistry;

import java.io.*;

public class FileHandling {

  private static String file = System.getProperty("user.dir") + "/src/main/resources/projectRegistry.txt";

  public static void writeProjectRegistryToFile(ProjectRegistry projectRegistry) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

    objectOutputStream.writeObject(projectRegistry);
    objectOutputStream.flush();
    objectOutputStream.close();
  }

  public static ProjectRegistry readProjectRegistryFromFile() throws IOException, ClassNotFoundException {
    FileInputStream fileInputStream = new FileInputStream(file);
    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

    ProjectRegistry projectRegistry = (ProjectRegistry) objectInputStream.readObject();
    objectInputStream.close();

    return projectRegistry;
  }
}
