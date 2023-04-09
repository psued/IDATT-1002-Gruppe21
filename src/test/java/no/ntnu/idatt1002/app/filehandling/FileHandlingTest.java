package no.ntnu.idatt1002.app.filehandling;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.data.Expense;
import no.ntnu.idatt1002.app.data.Income;
import no.ntnu.idatt1002.app.data.ProjectRegistry;
import no.ntnu.idatt1002.app.data.User;

public class FileHandlingTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();

        ArrayList<Expense> accountingExpenses = new ArrayList<>();
        Expense expense1 = new Expense("Ticket to bus", "Transportation", 100, LocalDate.now());
        Expense expense2 = new Expense("Fuel", "Transportation", 100, LocalDate.now());
        accountingExpenses.add(expense1);
        accountingExpenses.add(expense2);

        ArrayList<Income> accountingIncome = new ArrayList<>();
        Income income1 = new Income("Salary", "Salary", 1000, LocalDate.now());
        Income income2 = new Income("Gift", "Gift", 1000, LocalDate.now());
        accountingIncome.add(income1);
        accountingIncome.add(income2);

        ArrayList<Expense> budgetingExpenses = new ArrayList<>();
        Expense expense3 = new Expense("Ticket to bus", "Transportation", 100, LocalDate.now());
        Expense expense4 = new Expense("Fuel", "Transportation", 100, LocalDate.now());
        budgetingExpenses.add(expense3);
        budgetingExpenses.add(expense4);

        ArrayList<Income> budgetingIncome = new ArrayList<>();
        Income income3 = new Income("Salary", "Salary", 1000, LocalDate.now());
        Income income4 = new Income("Gift", "Gift", 1000, LocalDate.now());
        budgetingIncome.add(income3);
        budgetingIncome.add(income4);

        user.addProject("TestName", "TestDescription", "TestCategory", LocalDate.now(), accountingExpenses,
                accountingIncome, budgetingExpenses, budgetingIncome);
    }

    @Test
    void testReadAndWrite() {
        assertDoesNotThrow(() -> FileHandling.writeUserToFile(user));

        AtomicReference<User> newUser = new AtomicReference<>(new User());
        assertDoesNotThrow(() -> newUser.set(FileHandling.readUserFromFile()));

        ProjectRegistry oldUserRegistry = user.getProjectRegistry();
        ProjectRegistry newUserRegistry = newUser.get().getProjectRegistry();
        
        assertEquals(oldUserRegistry.getCategories(), newUserRegistry.getCategories());
        assertEquals(oldUserRegistry.getProjects(), newUserRegistry.getProjects());
    }
}
