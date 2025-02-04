package com.costa.expense_tracker_api.domain.expense;

import lombok.Getter;

@Getter
public enum ExpenseCategory {

    GROCERIES ("Groceries"),
    LEISURE ("Leisure"),
    ELECTRONICS ("Electronics"),
    UTILITIES ("Utilities"),
    CLOTHING ("Clothing"),
    HEALTH ("Health"),
    OTHERS ("Others");

    private final String category;

    ExpenseCategory(String category){
        this.category = category;
    }
}
