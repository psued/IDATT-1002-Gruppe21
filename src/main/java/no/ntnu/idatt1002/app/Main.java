package no.ntnu.idatt1002.app;

import no.ntnu.idatt1002.app.fileHandling.FileHandling;
import no.ntnu.idatt1002.app.registry.ProjectRegistry;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, ClassNotFoundException {

    ProjectRegistry projectRegistry = new ProjectRegistry();
    System.out.println(projectRegistry.toString());
    FileHandling.writeProjectRegistryToFile(projectRegistry);
    ProjectRegistry pr2 = FileHandling.readProjectRegistryFromFile();
    System.out.println(pr2.toString());

    BudgetAndAccountingApp.main(args);
  }
}
