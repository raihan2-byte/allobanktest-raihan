package com.example.split_bill.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal zero() {
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal scale(BigDecimal value) {
        if (value == null) {
            return zero();
        }

        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return scale(scale(a).add(scale(b)));
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return scale(scale(a).subtract(scale(b)));
    }

    public static BigDecimal divide(BigDecimal amount, int divisor) {
        return scale(amount.divide(BigDecimal.valueOf(divisor), 2, RoundingMode.HALF_UP));
    }

    public static BigDecimal percentage(BigDecimal amount, int pct) {
        return scale(
                amount.multiply(BigDecimal.valueOf(pct))
                        .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
        );
    }

    public static boolean isPositive(BigDecimal value) {
        return scale(value).compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNegative(BigDecimal value) {
        return scale(value).compareTo(BigDecimal.ZERO) < 0;
    }
}
