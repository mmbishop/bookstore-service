package com.improving.bookstore.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceNormalizer {

    public static BigDecimal normalizePrice(BigDecimal price) {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

}
