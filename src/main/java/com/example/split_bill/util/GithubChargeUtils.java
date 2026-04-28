package com.example.split_bill.util;

import java.math.BigDecimal;

public final class GithubChargeUtils {

    private static final String GITHUB_USERNAME = "raihan123";

    private GithubChargeUtils() {
    }

    public static int getServiceChargePct() {
        int sum = GITHUB_USERNAME
                .toLowerCase()
                .chars()
                .sum();

        return sum % 10;
    }

    public static BigDecimal calculateCharge(BigDecimal totalExpense) {
        int pct = getServiceChargePct();
        return MoneyUtils.percentage(totalExpense, pct);
    }

    public static String getGithubUsername() {
        return GITHUB_USERNAME;
    }
}