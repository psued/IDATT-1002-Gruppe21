package no.ntnu.idatt1002.app.registers;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.gui.AllProjectsController;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Project class represents a project with a name, description, category, due date, and
 * status. It also has an {@link Accounting accounting} object and a {@link Budgeting budgeting}
 * object that tracks the {@link Transaction transactions} related to the project.
 *
 * <p>It implements the Serializable interface for serialization and deserialization of object.
 *
 * @see Serializable
 * @see Bookkeeping
 * @see Transaction
 */
public class Project implements Serializable {
  private final Accounting accounting;
  private final Budgeting budgeting;
  private final List<File> images;
  private String name;
  private String description;
  private String category;
  private String status;
  private LocalDate dueDate;
  
  /**
   * Constructs a Project object with the specified name, description, category, due date, and
   * status. The accounting and budgeting objects are initialized with empty lists.
   *
   * @param name        The name of the project.
   * @param description The description of the project.
   * @param category    The category of the project.
   * @param dueDate     The due date of the project.
   * @throws IllegalArgumentException If the name, category, or status is null or blank.
   */
  public Project(String name, String description, String category, LocalDate dueDate, String status)
      throws IllegalArgumentException {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    if (category == null || category.isBlank()) {
      throw new IllegalArgumentException("Category cannot be null or blank");
    }
    if (status == null || status.isBlank()) {
      throw new IllegalArgumentException("Status cannot be null or blank");
    }
    
    this.name = name;
    this.description = description;
    this.category = category;
    this.dueDate = dueDate;
    this.status = status;
    accounting = new Accounting();
    budgeting = new Budgeting();
    images = new ArrayList<>();
  }
  
  /**
   * Creates a deep copy of the project. Achieves this by creating a new Project instance and
   * setting all the fields to the same values as the project to be copied. This includes
   * recursively deep copying the accounting and budgeting objects and all their transactions.
   *
   * @param project The project to be copied.
   * @throws IllegalArgumentException If the project is null.
   * @see Accounting#Accounting(Accounting)
   * @see Budgeting#Budgeting(Budgeting)
   * @see Transaction
   */
  public Project(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("Project cannot be null");
    }
    this.name = project.getName();
    this.description = project.getDescription();
    this.category = project.getCategory();
    this.dueDate = project.getDueDate();
    this.status = project.getStatus();
    this.accounting = new Accounting(project.getAccounting());
    this.budgeting = new Budgeting(project.getBudgeting());
    this.images = project.getImages();
  }
  
  /**
   * Get the name of the project.
   *
   * @return The name of the project.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name of the project.
   *
   * @param name The new name of the project.
   * @throws IllegalArgumentException If the new name is null or blank.
   */
  public void setName(String name) throws IllegalArgumentException {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }
    
    this.name = name;
  }
  
  /**
   * Get the description of the project.
   *
   * @return The description of the project.
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Sets the description of the project.
   *
   * @param description The new description of the project.
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Get the category of the project.
   *
   * @return The category of the project.
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Sets the category of the project.
   *
   * @param category The new category of the project.
   * @throws IllegalArgumentException If the new category is null or blank.
   */
  public void setCategory(String category) throws IllegalArgumentException {
    if (category == null || category.isBlank()) {
      throw new IllegalArgumentException("Category cannot be null or blank");
    }
    this.category = category;
  }
  
  /**
   * Get the status of the project.
   *
   * @return The status of the project.
   */
  public String getStatus() {
    return status;
  }
  
  /**
   * Sets the status of the project.
   *
   * @param status The new status of the project.
   * @throws IllegalArgumentException If the new status is null or blank.
   */
  public void setStatus(String status) throws IllegalArgumentException {
    if (status == null || status.isBlank()) {
      throw new IllegalArgumentException("Status cannot be null or blank");
    }
    this.status = status;
  }
  
  /**
   * Get the due date of the project.
   *
   * @return The due date of the project.
   */
  public LocalDate getDueDate() {
    return dueDate;
  }
  
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }
  
  /**
   * Get the accounting object of the project.
   *
   * @return The accounting object of the project.
   */
  public Accounting getAccounting() {
    return accounting;
  }
  
  /**
   * Get the budgeting object of the project.
   *
   * @return The budgeting object of the project.
   */
  public Budgeting getBudgeting() {
    return budgeting;
  }
  
  /**
   * Get a deep copy of the images of the project. Achieves this by creating a new ArrayList and
   * adding new File objects with the same absolute path of the image files in the project.
   *
   * @return a list of files pointing to the images in the project.
   */
  public List<File> getImages() {
    ArrayList<File> imagesCopy = new ArrayList<>();
    for (File image : images) {
      imagesCopy.add(new File(image.getAbsolutePath()));
    }
    return imagesCopy;
  }
  
  /**
   * Get the total income of the project.
   *
   * <p>Is used by the AllProjectsController to display the total accounting total of the project
   * using setCellValueFactory. The method is annotated with {@code @SuppressWarnings("unused")}
   * as the method is necessary for the allProjectsController to work.
   *
   * @return A double representing the total income of the project.
   * @see AllProjectsController
   */
  @SuppressWarnings("unused")
  public double getAccountingTotal() {
    return accounting.getTotalIncome() - accounting.getTotalExpense();
  }
  
  /**
   * Adds an image to the project.
   *
   * @param image The image to add
   * @throws IllegalArgumentException If the image is null.
   */
  public void addImage(File image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    images.add(image);
  }
  
  /**
   * Removes an image from the project.
   *
   * @param image The image to remove
   * @throws IllegalArgumentException If the image is null.
   */
  public void removeImage(File image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    images.remove(image);
  }
  
  /**
   * Returns the index of the specified image.
   *
   * <p>Takes an image object instead of a file object as the controller can only read an image
   * from a FXML imageView object. Runs through the list of files in the project and compares
   * them to the image object. If the image object is found, the index of the file in the list is
   * returned, otherwise -1 is returned.
   *
   * @param image An image object that belongs to a file in the project.
   * @return The index of the specified image in the list of files.
   * @throws IllegalArgumentException If the image is null.
   */
  public int getImageIndex(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("image cannot be null");
    }
    for (int i = 0; i < images.size(); i++) {
      Image img = new Image(images.get(i).toURI().toString());
      if (img.getUrl().equals(image.getUrl())) {
        return i;
      }
    }
    return -1;
  }
  
  /**
   * Indicates whether the specified object is equal to this project object. Checks first if the
   * parameter object is the same instance as this project object. If not, checks if the parameter
   * object is an instance of the Project class. If not, returns false, else compares all the
   * project information to the parameter object.
   *
   * @param obj The object to compare to this project object.
   * @return true if the specified object is equal to this project object, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Project project)) {
      return false;
    }
    
    // As these fields can be null, we need to check if they are null before using the equals
    // method, otherwise we will get an unnecessary NullPointerException.
    boolean equalsDate =
        project.getDueDate() == null ? dueDate == null : project.getDueDate().equals(dueDate);
    boolean equalsDescription = project.getDescription() == null ? description == null :
        project.getDescription().equals(description);
    
    return project.getName().equals(name) && equalsDescription
        && project.getCategory().equals(category) && project.getAccounting().equals(accounting)
        && project.getBudgeting().equals(budgeting) && equalsDate;
  }
}
