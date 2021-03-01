package com.lunatech.training.quarkus.extensions;

import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

@TemplateExtension
public class BigDecimalExtension {

    static RawString monetary(BigDecimal value) {
        return new RawString("&euro; " + value.setScale(2, RoundingMode.HALF_EVEN).toString());
    }

}
