package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A project is a way to plan, categorize and track expenses and income related
 * to a specific goal.
 */
public class Project implements Serializable {

  private String name;

  private String description;

  private String category;

  private final LocalDate dueDate;

  private final Accounting accounting;

  private final Budgeting budgeting;

  /**
   * Creates a project with a name, description and category.
   *
   * @param name        The name of the project.
   * @param description The description of the project.
   * @param category    The category of the project.
   * @throws IllegalArgumentException if name is null or blank.
   */
  public Project(String name, String description, String category, LocalDate dueDate) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Name cannot be null or blank");
    }

    this.name = name;
    this.description = description;
    this.category = category;
    this.dueDate = dueDate;
    this.accounting = new Accounting();
    this.budgeting = new Budgeting();
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name cannot be null or blank");
    }

    this.name = name;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets category.
   *
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets category.
   *
   * @param category the category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Gets creation date.
   *
   * @return the creation date
   */
  public LocalDate getDueDate() {
    return dueDate;
  }

  /**
   * Gets accounting object.
   *
   * @return the accounting object
   */
  public Accounting getAccounting() {
    return accounting;
  }

  /**
   * Gets budgeting object.
   *
   * @return the budgeting object
   */
  public Budgeting getBudgeting() {
    return budgeting;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (!(obj instanceof Project))
      return false;

    Project project = (Project) obj;
    return project.getName().equals(this.name) && project.getDescription().equals(this.description)
        && project.getCategory().equals(this.category) && project.getDueDate().equals(this.dueDate)
        && project.getAccounting().equals(this.accounting) && project.getBudgeting().equals(this.budgeting);
  }

  @Override
  public String toString() {
    return "Project{" + "name='" + name + '\'' + ", description='" + description + '\'' +
        ", category='" + category + '\'' + ", creationDate=" + dueDate + ", accounting=" +
        accounting + ", budgeting=" + budgeting + '}';
  }

}
