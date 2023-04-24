package no.ntnu.idatt1002.app.registers;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * A Project class representing a project with a name, description, category, due date,
 * accounting, and budgeting information, as well as a list of images.
 */
public class Project implements Serializable {
  private String name;
  private String description;
  private String category;
  private String status;
  private LocalDate dueDate;
  private final Accounting accounting;
  private final Budgeting budgeting;
  private final List<File> images;

  /**
   * Constructs a Project object with the specified name, description, category, and due date.
   * Initializes the accounting and budgeting objects.
   *
   * @param name The name of the project.
   * @param description The description of the project.
   * @param category The category of the project.
   * @param dueDate The due date of the project.
   * @throws IllegalArgumentException If the name or category is null or blank.
   */
  public Project(String name, String description, String category, LocalDate dueDate, String status) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }
    if (category == null || category.isBlank()) {
      throw new IllegalArgumentException("Category cannot be null or blank");
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
   * Creates a deep copy of the project.
   *
   * @param project The project to be copied.
   */
  public Project(Project project) {
    this.name = project.getName();
    this.description = project.getDescription();
    this.category = project.getCategory();
    this.dueDate = project.getDueDate();
    this.status = project.getStatus();
    this.accounting = project.getAccounting();
    this.budgeting = project.getBudgeting();
    this.images = project.getImages();
  }

  /**
   * Returns the name of the project.
   *
   * @return The name of the project.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the description of the project.
   *
   * @return The description of the project.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the category of the project.
   *
   * @return The category of the project.
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Returns the status of the project.
   *
   * @return The status of the project.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Returns the due date of the project.
   *
   * @return The due date of the project.
   */
  public LocalDate getDueDate() {
    return dueDate;
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
   * Get a deep copy of the images of the project.
   *
   * @return The images of the project.
   */
  public List<File> getImages() {
    List<File> copy = new ArrayList<>();
    for (File image : images) {
      copy.add(new File(image.getAbsolutePath()));
    }
    return copy;
  }
  
  
  /**
   * Returns the total income of the project.
   *
   * <p>Is used by the AllProjectsController to display the total income of the project.
   *
   * @return The total income of the project.
   */
  public double getAccountingTotal() {
    return accounting.getTotalIncome() - accounting.getTotalExpense();
  }

  /**
   * Sets the name of the project.
   *
   * @param name The new name of the project.
   * @throws IllegalArgumentException If the name is null or blank.
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }

    this.name = name;
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
   * Sets the category of the project.
   *
   * @param category The new category of the project.
   */
  public void setCategory(String category) {
    this.category = category;
  }
  
  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }
  
  /**
   * Sets the status of the project.
   *
   * @param status The new status of the project.
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Adds an image to the project
   *
   * @param image The image to add
   */
  public void addImage(File image) {
    images.add(image);
  }
  
  /**
   * Removes an image from the project
   *
   * @param image The image to remove
   */
  public void removeImage(File image) {
    images.remove(image);
  }
  
  /**
   * Indicates whether the specified object is equal to this project object.
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
    
    boolean equalsDate = project.getDueDate() == null ? dueDate == null
        : project.getDueDate().equals(dueDate);
    
    return project.getName().equals(name) && project.getDescription().equals(this.description)
        && project.getCategory().equals(category)
        && project.getAccounting().equals(accounting)
        && project.getBudgeting().equals(budgeting) && equalsDate;
  }

  /**
   * Returns a string representation of the project object.
   *
   * @return A string representation of the project object.
   */
  @Override
  public String toString() {
    return "Project{" + "name='" + name + '\'' + ", description='" + description + '\''
        + ", category='" + category + '\'' + ", creationDate=" + dueDate + ", accounting="
        + accounting + ", budgeting=" + budgeting + '}';
  }

}
