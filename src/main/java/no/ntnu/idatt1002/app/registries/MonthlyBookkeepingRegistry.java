package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;

public class MonthlyBookkeepingRegistry implements Serializable {

    private List<MonthlyBookkeeping> bookkeepingList;

    public MonthlyBookkeepingRegistry() {
        bookkeepingList = new ArrayList<>();
    }

    public void addMonthlyBookkeeping(MonthlyBookkeeping bookkeeping) {
        bookkeepingList.add(bookkeeping);
    }

    public List<MonthlyBookkeeping> getMonthlyBookkeepingList() {
        return bookkeepingList;
    }

}