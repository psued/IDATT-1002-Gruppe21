package no.ntnu.idatt1002.app;

import no.ntnu.idatt1002.app.data.User;
import no.ntnu.idatt1002.app.fileHandling.FileHandling;
import no.ntnu.idatt1002.app.data.ProjectRegistry;

import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, ClassNotFoundException {

    User user = new User();

    FileHandling.writeUserToFile(user);
    User user2 = FileHandling.readUserFromFile();

    System.out.println(user.toString());
    System.out.println(user2.toString());

    System.out.println(user.getProjectRegistry().toString());


    BudgetAndAccountingApp.main(args);
  }
}
