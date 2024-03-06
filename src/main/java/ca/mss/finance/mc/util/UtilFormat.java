package ca.mss.finance.mc.util;

import ca.mss.finance.mc.impl.MortgageSettings;
import ca.mss.finance.util.UtilDateTime;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

public class UtilFormat {

    private static NumberFormat numberFormatter = NumberFormat.getInstance();

    static {
        numberFormatter.setMaximumFractionDigits(MortgageSettings.SCALE_CRY);
        numberFormatter.setMinimumFractionDigits(MortgageSettings.SCALE_CRY);
    }

    public static final String format(String s){
        return s;
    }

    public static final String format(int n){
        return Integer.toString(n);
    }

    public static final String format(BigDecimal d){
        return numberFormatter.format(d);
    }

    public static final String format(Date d){
        return UtilDateTime.format(d);
    }

}
