package ca.mss.finance;

import ca.mss.finance.mc.AmortizationType;
import ca.mss.finance.mc.PaymentFrequency;
import ca.mss.finance.mc.impl.*;
import ca.mss.finance.mc.printers.TextPrinterHelper;
import ca.mss.finance.mc.util.UtilFormat;
import ca.mss.finance.util.UtilDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MorgagePlanner {

    static public BigDecimal ANUAL_RATE = new BigDecimal("5.74");
    static public Date START_DATE = UtilDateTime.parse("01/01/2025", "MM/dd/yyyy");
    static public BigDecimal YEAR_TERM = new BigDecimal(3);

    static boolean doPrint = false;

    public static void main(String[] args) {
        //calculate(new BigDecimal("775000.00", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0));
        //forecast();
        //realPlan24Feb13();
        //rightPlan24Feb13();
        //table380_850_35();
        //table380_850_30();
        //agilePlanner();
        table550_700_25();
    }


    public static void table550_700_25(){

        BigDecimal year3 = new BigDecimal("3.0", MortgageSettings.MC_CRY);
        BigDecimal hundred = new BigDecimal("100.0", MortgageSettings.MC_CRY);

        BigDecimal returns45 = new BigDecimal("4.50", MortgageSettings.MC_CRY).divide(hundred, MortgageSettings.MC_CRY).multiply(year3);
        BigDecimal returns50 = new BigDecimal("50.0", MortgageSettings.MC_CRY).divide(hundred, MortgageSettings.MC_CRY).multiply(year3);

        BigDecimal maxInvestment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);
        BigDecimal minPaymentMo = new BigDecimal("2500.00", MortgageSettings.MC_CRY);
        BigDecimal maxPaymentMo = new BigDecimal("3000.00", MortgageSettings.MC_CRY);
        BigDecimal coupleThousand = new BigDecimal("1000.00", MortgageSettings.MC_CRY);
        int amortization = 30;
        List<Result> resultList = new ArrayList<>(100);
        BigDecimal minDownPrc = new BigDecimal("20.0", MortgageSettings.MC_CRY).divide(hundred, MortgageSettings.MC_CRY);

        int N = (700000-550000)/10000+1;
        BigDecimal[] housePrices = new BigDecimal[N];
        for(int i=0, maxi=housePrices.length; i<maxi; ++i) {
            BigDecimal housePrice = new BigDecimal(550000+i*10000, MortgageSettings.MC_CRY);
            BigDecimal downpayment = housePrice.multiply(minDownPrc);
            BigDecimal mortgage = housePrice.subtract(downpayment);
            MorgagePlanner.Result result = calculate(housePrice, mortgage, new AmortizationPheriod(amortization, 0));

            if( result.monthlyPayment.compareTo(minPaymentMo) <= 0 ){
                resultList.add(result);
                continue; // 20% down, less then 25000 mo!!! Skip any other mortgages
            }

            // Find better mortgage with more to down
            boolean is3K = false;
            while( result.monthlyPayment.compareTo(minPaymentMo) > 0){
                downpayment = downpayment.add(coupleThousand);
                mortgage = housePrice.subtract(downpayment);
                if( downpayment.compareTo(maxInvestment) > 0 ){ // no more for down
                    break;
                }
                if(  !is3K && result.monthlyPayment.compareTo(maxPaymentMo) <= 0 ){ // Found mortgage under 3000
                    resultList.add(result);
                    is3K = true;
                }
                result = calculate(housePrice, mortgage, new AmortizationPheriod(amortization, 0));
            }
            resultList.add(result); // is less than 2500 or wthe best from worst with 0 invest
        }

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"; // 20

        System.out.printf(fmt, "Years",
                "House", "Down,20%", "Invest","Returns 4.5%", "Returns 50%",
                "Mortgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Savings", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "Pay, ttl", "Intrest, ttl");
        for(Result row: resultList ){

            BigDecimal toInvest = maxInvestment.subtract(row.houseprice.subtract(row.morgage));
            BigDecimal toDown = row.houseprice.subtract(row.morgage);

            BigDecimal toSavings = toInvest.subtract(row.termPayment).add(row.termPrincipal).add(toInvest.multiply(returns45));

            System.out.printf(fmt,
                    UtilFormat.format(row.term),

                    UtilFormat.format(row.houseprice),
                    UtilFormat.format(toDown),
                    UtilFormat.format(toInvest),

                    // Returns
                    UtilFormat.format(toInvest.multiply(returns45)),
                    UtilFormat.format(toInvest.multiply(returns50)),

                    UtilFormat.format(row.startBalance),
                    UtilFormat.format(row.monthlyPayment),

                    UtilDateTime.format(row.termDate),
                    UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                    UtilFormat.format(row.termIntrest), UtilFormat.format(toSavings), UtilFormat.format(row.termPayment),

                    UtilDateTime.format(row.termDate10),
                    UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                    UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                    UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
        }

    }


    public static void agilePlanner(){

        BigDecimal maxInvestment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);

        BigDecimal maxPaymentMo = new BigDecimal("3000.00", MortgageSettings.MC_CRY);
        BigDecimal midPaymentMo = new BigDecimal("2500.00", MortgageSettings.MC_CRY);
        BigDecimal minPaymentMo = new BigDecimal("2300.00", MortgageSettings.MC_CRY);

        BigDecimal coupleThousand = new BigDecimal("20000.00", MortgageSettings.MC_CRY);

        int N = (850000-380000)/10000+1;
        BigDecimal[] housePrices = new BigDecimal[N];
        for(int i=0, maxi=housePrices.length; i<maxi; ++i) {
            housePrices[i] = new BigDecimal(380000+i*10000, MortgageSettings.MC_CRY);
        }

        int[] amortization = new int[]{30,25,20};

        BigDecimal[] downpayment = new BigDecimal[housePrices.length];
        BigDecimal[] mortgage = new BigDecimal[housePrices.length];
        boolean[] isPrinted = new boolean[housePrices.length];

        List<Result> result = new ArrayList<>(100);
        BigDecimal minDownPrc = new BigDecimal("20.0", MortgageSettings.MC_CRY).divide(new BigDecimal("100.0"), MortgageSettings.MC_CRY);

        for(int i=0,maxi=housePrices.length; i<maxi; ++i){
            downpayment[i] = housePrices[i].multiply(minDownPrc);
            mortgage[i] = housePrices[i].subtract(downpayment[i]);
            for(int n=0,maxn=amortization.length; n<maxn; ++n){
                result.add(calculate(housePrices[i], mortgage[i], new AmortizationPheriod(amortization[n], 0)));
            }
        }

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"; // 20

        System.out.printf(fmt, "Years",
                "House", "Down,5%", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.size(); ++i){
            int index = i / amortization.length;
            if( isPrinted[index] ){
                continue;
            }
            Result row = result.get(i);
/*
            if( row.monthlyPayment.compareTo(minPaymentMo) <= 0 ){
                continue;
            }
*/
            if( !isPrinted[index] ) {
                isPrinted[index] = true;
                System.out.printf(fmt,
                        UtilFormat.format(row.term),

                        UtilFormat.format(housePrices[index]),
                        UtilFormat.format(downpayment[index]),
                        UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                        UtilFormat.format(row.startBalance),
                        UtilFormat.format(row.monthlyPayment),

                        UtilDateTime.format(row.termDate),
                        UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                        UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                        UtilDateTime.format(row.termDate10),
                        UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                        UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                        UtilDateTime.format(row.endYear),
                        UtilDateTime.format(row.endYear - 1971),
                        UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
            }
            if( row.monthlyPayment.compareTo(maxPaymentMo) > 0 ) {
                for (int v=0; row.monthlyPayment.compareTo(midPaymentMo) > 0 && v<2; ++v ) {
                    if (downpayment[index].add(coupleThousand).compareTo(maxInvestment) > 0) {
                        break;
                    }
                    downpayment[index] = downpayment[index].add(coupleThousand);
                    mortgage[index] = housePrices[index].subtract(downpayment[index]);
                    row = calculate(housePrices[index], mortgage[index],  new AmortizationPheriod(row.term, 0));
                    System.out.printf(fmt,
                            UtilFormat.format(row.term),

                            UtilFormat.format(housePrices[index]),
                            UtilFormat.format(downpayment[index]),
                            UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                            UtilFormat.format(row.startBalance),
                            UtilFormat.format(row.monthlyPayment),

                            UtilDateTime.format(row.termDate),
                            UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                            UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                            UtilDateTime.format(row.termDate10),
                            UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                            UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                            UtilDateTime.format(row.endYear),
                            UtilDateTime.format(row.endYear - 1971),
                            UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
                }
            }
        }

    }

    public static void table380_850_30(){

        BigDecimal maxInvestment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);

        BigDecimal maxPaymentMo = new BigDecimal("3000.00", MortgageSettings.MC_CRY);
        BigDecimal midPaymentMo = new BigDecimal("2500.00", MortgageSettings.MC_CRY);
        BigDecimal minPaymentMo = new BigDecimal("2300.00", MortgageSettings.MC_CRY);

        BigDecimal coupleThousand = new BigDecimal("20000.00", MortgageSettings.MC_CRY);

        int N = (850000-380000)/10000+1;
        BigDecimal[] housePrices = new BigDecimal[N];
        for(int i=0, maxi=housePrices.length; i<maxi; ++i) {
            housePrices[i] = new BigDecimal(380000+i*10000, MortgageSettings.MC_CRY);
        }

        int[] amortization = new int[]{30,25,20};

        BigDecimal[] downpayment = new BigDecimal[housePrices.length];
        BigDecimal[] mortgage = new BigDecimal[housePrices.length];
        boolean[] isPrinted = new boolean[housePrices.length];

        List<Result> result = new ArrayList<>(100);
        BigDecimal minDownPrc = new BigDecimal("20.0", MortgageSettings.MC_CRY).divide(new BigDecimal("100.0"), MortgageSettings.MC_CRY);

        for(int i=0,maxi=housePrices.length; i<maxi; ++i){
            downpayment[i] = housePrices[i].multiply(minDownPrc);
            mortgage[i] = housePrices[i].subtract(downpayment[i]);
            for(int n=0,maxn=amortization.length; n<maxn; ++n){
                result.add(calculate(housePrices[i], mortgage[i], new AmortizationPheriod(amortization[n], 0)));
            }
        }

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"; // 20

        System.out.printf(fmt, "Years",
                "House", "Down,5%", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.size(); ++i){
            int index = i / amortization.length;
            if( isPrinted[index] ){
                continue;
            }
            Result row = result.get(i);
/*
            if( row.monthlyPayment.compareTo(minPaymentMo) <= 0 ){
                continue;
            }
*/
            if( !isPrinted[index] ) {
                isPrinted[index] = true;
                System.out.printf(fmt,
                        UtilFormat.format(row.term),

                        UtilFormat.format(housePrices[index]),
                        UtilFormat.format(downpayment[index]),
                        UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                        UtilFormat.format(row.startBalance),
                        UtilFormat.format(row.monthlyPayment),

                        UtilDateTime.format(row.termDate),
                        UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                        UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                        UtilDateTime.format(row.termDate10),
                        UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                        UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                        UtilDateTime.format(row.endYear),
                        UtilDateTime.format(row.endYear - 1971),
                        UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
            }
            if( row.monthlyPayment.compareTo(maxPaymentMo) > 0 ) {
                for (int v=0; row.monthlyPayment.compareTo(midPaymentMo) > 0 && v<2; ++v ) {
                    if (downpayment[index].add(coupleThousand).compareTo(maxInvestment) > 0) {
                        break;
                    }
                    downpayment[index] = downpayment[index].add(coupleThousand);
                    mortgage[index] = housePrices[index].subtract(downpayment[index]);
                    row = calculate(housePrices[index], mortgage[index],  new AmortizationPheriod(row.term, 0));
                    System.out.printf(fmt,
                            UtilFormat.format(row.term),

                            UtilFormat.format(housePrices[index]),
                            UtilFormat.format(downpayment[index]),
                            UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                            UtilFormat.format(row.startBalance),
                            UtilFormat.format(row.monthlyPayment),

                            UtilDateTime.format(row.termDate),
                            UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                            UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                            UtilDateTime.format(row.termDate10),
                            UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                            UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                            UtilDateTime.format(row.endYear),
                            UtilDateTime.format(row.endYear - 1971),
                            UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
                }
            }
        }

    }

    public static void table380_850_35(){

        BigDecimal maxInvestment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);

        BigDecimal maxPaymentMo = new BigDecimal("3000.00", MortgageSettings.MC_CRY);
        BigDecimal midPaymentMo = new BigDecimal("2500.00", MortgageSettings.MC_CRY);
        BigDecimal minPaymentMo = new BigDecimal("2300.00", MortgageSettings.MC_CRY);

        BigDecimal coupleThousand = new BigDecimal("20000.00", MortgageSettings.MC_CRY);

        int N = (850000-380000)/10000+1;
        BigDecimal[] housePrices = new BigDecimal[N];
        for(int i=0, maxi=housePrices.length; i<maxi; ++i) {
            housePrices[i] = new BigDecimal(380000+i*10000, MortgageSettings.MC_CRY);
        }

        int[] amortization = new int[]{30,25,20};

        BigDecimal[] downpayment = new BigDecimal[housePrices.length];
        BigDecimal[] mortgage = new BigDecimal[housePrices.length];
        boolean[] isPrinted = new boolean[housePrices.length];

        List<Result> result = new ArrayList<>(100);
        BigDecimal minDownPrc = new BigDecimal("5.0", MortgageSettings.MC_CRY).divide(new BigDecimal("100.0"), MortgageSettings.MC_CRY);

        for(int i=0,maxi=housePrices.length; i<maxi; ++i){
            downpayment[i] = housePrices[i].multiply(minDownPrc);
            mortgage[i] = housePrices[i].subtract(downpayment[i]);
            for(int n=0,maxn=amortization.length; n<maxn; ++n){
                result.add(calculate(housePrices[i], mortgage[i], new AmortizationPheriod(amortization[n], 0)));
            }
        }

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"; // 20

        System.out.printf(fmt, "Years",
                "House", "Down,5%", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.size(); ++i){
            int index = i / amortization.length;
            if( isPrinted[index] ){
                continue;
            }
            Result row = result.get(i);
/*
            if( row.monthlyPayment.compareTo(minPaymentMo) <= 0 ){
                continue;
            }
*/
            if( !isPrinted[index] ) {
                isPrinted[index] = true;
                System.out.printf(fmt,
                        UtilFormat.format(row.term),

                        UtilFormat.format(housePrices[index]),
                        UtilFormat.format(downpayment[index]),
                        UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                        UtilFormat.format(row.startBalance),
                        UtilFormat.format(row.monthlyPayment),

                        UtilDateTime.format(row.termDate),
                        UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                        UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                        UtilDateTime.format(row.termDate10),
                        UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                        UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                        UtilDateTime.format(row.endYear),
                        UtilDateTime.format(row.endYear - 1971),
                        UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
            }
            if( row.monthlyPayment.compareTo(maxPaymentMo) > 0 ) {
                for (int v=0; row.monthlyPayment.compareTo(midPaymentMo) > 0 && v<2; ++v ) {
                    if (downpayment[index].add(coupleThousand).compareTo(maxInvestment) > 0) {
                        break;
                    }
                    downpayment[index] = downpayment[index].add(coupleThousand);
                    mortgage[index] = housePrices[index].subtract(downpayment[index]);
                    row = calculate(housePrices[index], mortgage[index],  new AmortizationPheriod(row.term, 0));
                    System.out.printf(fmt,
                            UtilFormat.format(row.term),

                            UtilFormat.format(housePrices[index]),
                            UtilFormat.format(downpayment[index]),
                            UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                            UtilFormat.format(row.startBalance),
                            UtilFormat.format(row.monthlyPayment),

                            UtilDateTime.format(row.termDate),
                            UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                            UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                            UtilDateTime.format(row.termDate10),
                            UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                            UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                            UtilDateTime.format(row.endYear),
                            UtilDateTime.format(row.endYear - 1971),
                            UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
                }
            }
        }

    }

    public static void rightPlan24Feb13(){

        BigDecimal maxInvestment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);
        BigDecimal minInvestment = new BigDecimal("80000.00", MortgageSettings.MC_CRY);

        BigDecimal maxPaymentMo = new BigDecimal("3000.00", MortgageSettings.MC_CRY);
        BigDecimal midPaymentMo = new BigDecimal("2500.00", MortgageSettings.MC_CRY);
        BigDecimal minPaymentMo = new BigDecimal("2300.00", MortgageSettings.MC_CRY);

        BigDecimal coupleThousand = new BigDecimal("20000.00", MortgageSettings.MC_CRY);

        BigDecimal[] housePrices = new BigDecimal[]{
                new BigDecimal("640000.00", MortgageSettings.MC_CRY),
                new BigDecimal("670000.00", MortgageSettings.MC_CRY),
                new BigDecimal("700000.00", MortgageSettings.MC_CRY),
                new BigDecimal("790000.00", MortgageSettings.MC_CRY),
                new BigDecimal("800000.00", MortgageSettings.MC_CRY),
        };

        int[] amortization = new int[]{35,30,25,20};

        BigDecimal[] downpayment = new BigDecimal[housePrices.length];
        BigDecimal[] mortgage = new BigDecimal[housePrices.length];
        boolean[] isPrinted = new boolean[housePrices.length];

        List<Result> result = new ArrayList<>(100);
        BigDecimal minDownPrc = new BigDecimal("20.0", MortgageSettings.MC_CRY).divide(new BigDecimal("100.0"), MortgageSettings.MC_CRY);

        for(int i=0,maxi=housePrices.length; i<maxi; ++i){
            downpayment[i] = housePrices[i].multiply(minDownPrc);
            mortgage[i] = housePrices[i].subtract(downpayment[i]);
            for(int n=0,maxn=amortization.length; n<maxn; ++n){
                result.add(calculate(housePrices[i], mortgage[i], new AmortizationPheriod(amortization[n], 0)));
            }
        }

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"; // 20

        System.out.printf(fmt, "Years",
                "House", "Down", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.size(); ++i){
            int index = i / amortization.length;
            if( isPrinted[index] ){
                continue;
            }
            Result row = result.get(i);
            if( row.monthlyPayment.compareTo(minPaymentMo) <= 0 ){
                continue;
            }
            if( !isPrinted[index] ) {
                isPrinted[index] = true;
                System.out.printf(fmt,
                        UtilFormat.format(row.term),

                        UtilFormat.format(housePrices[index]),
                        UtilFormat.format(downpayment[index]),
                        UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                        UtilFormat.format(row.startBalance),
                        UtilFormat.format(row.monthlyPayment),

                        UtilDateTime.format(row.termDate),
                        UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                        UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                        UtilDateTime.format(row.termDate10),
                        UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                        UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                        UtilDateTime.format(row.endYear),
                        UtilDateTime.format(row.endYear - 1971),
                        UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
            }
            if( row.monthlyPayment.compareTo(maxPaymentMo) > 0 ) {
                for (int v=0; row.monthlyPayment.compareTo(midPaymentMo) > 0 && v<2; ++v ) {
                    if (downpayment[index].add(coupleThousand).compareTo(maxInvestment) > 0) {
                        break;
                    }
                    downpayment[index] = downpayment[index].add(coupleThousand);
                    mortgage[index] = housePrices[index].subtract(downpayment[index]);
                    row = calculate(housePrices[index], mortgage[index],  new AmortizationPheriod(row.term, 0));
                    System.out.printf(fmt,
                            UtilFormat.format(row.term),

                            UtilFormat.format(housePrices[index]),
                            UtilFormat.format(downpayment[index]),
                            UtilFormat.format(maxInvestment.subtract(downpayment[index])),

                            UtilFormat.format(row.startBalance),
                            UtilFormat.format(row.monthlyPayment),

                            UtilDateTime.format(row.termDate),
                            UtilFormat.format(row.termBalance), UtilFormat.format(row.termPrincipal),
                            UtilFormat.format(row.termIntrest), UtilFormat.format(row.termPayment),

                            UtilDateTime.format(row.termDate10),
                            UtilFormat.format(row.termBalance10), UtilFormat.format(row.termPrincipal10),
                            UtilFormat.format(row.termIntrest10), UtilFormat.format(row.termPayment10),

                            UtilDateTime.format(row.endYear),
                            UtilDateTime.format(row.endYear - 1971),
                            UtilFormat.format(row.endPayment), UtilFormat.format(row.endIntrest));
                }
            }
        }

    }

    public static void realPlan24Feb13(){

        BigDecimal investment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);

        Result[] result = new Result[]{
                calculate(new BigDecimal("474000.0", MortgageSettings.MC_CRY), new BigDecimal("474000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(25, 0)),
                calculate(new BigDecimal("474000.0", MortgageSettings.MC_CRY), new BigDecimal("474000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(30, 0)),
                calculate(new BigDecimal("474000.0", MortgageSettings.MC_CRY), new BigDecimal("474000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
                calculate(new BigDecimal("525000.0", MortgageSettings.MC_CRY), new BigDecimal("525000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(25, 0)),
                calculate(new BigDecimal("525000.0", MortgageSettings.MC_CRY), new BigDecimal("525000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(30, 0)),
                calculate(new BigDecimal("525000.0", MortgageSettings.MC_CRY), new BigDecimal("525000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
                calculate(new BigDecimal("553000.0", MortgageSettings.MC_CRY), new BigDecimal("553000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(30, 0)),
                calculate(new BigDecimal("553000.0", MortgageSettings.MC_CRY), new BigDecimal("553000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
                calculate(new BigDecimal("592000.0", MortgageSettings.MC_CRY), new BigDecimal("592000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
                calculate(new BigDecimal("632000.0", MortgageSettings.MC_CRY), new BigDecimal("632000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
        };

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n";

        System.out.printf(fmt, "Years",
                "House MAX", "Down", "Invest",
                "House MID", "Down", "Invest",
                "House MIN", "Down, 21%", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.length; ++i){

            BigDecimal downPayment21 = result[i].startBalance.divide(new BigDecimal(100.0/21.0-1.0), MortgageSettings.MC_CRY);
            BigDecimal minHousePrice = downPayment21.add(result[i].startBalance);

            BigDecimal maxInvestment = new BigDecimal("0.00", MortgageSettings.MC_CRY);
            BigDecimal maxDownPayment = investment.subtract(maxInvestment);
            BigDecimal maxHousePrice = maxDownPayment.add(result[i].startBalance);

            BigDecimal midInvestment = new BigDecimal("70000.00", MortgageSettings.MC_CRY);
            BigDecimal midDownPayment = investment.subtract(midInvestment);
            BigDecimal midHousePrice = midDownPayment.add(result[i].startBalance);

            System.out.printf(fmt,
                    UtilDateTime.format(result[i].term),

                    UtilFormat.format(maxHousePrice),
                    UtilFormat.format(maxDownPayment),
                    UtilFormat.format(maxInvestment),

                    UtilFormat.format(midHousePrice),
                    UtilFormat.format(midDownPayment),
                    UtilFormat.format(midInvestment),

                    UtilFormat.format(minHousePrice),
                    UtilFormat.format(downPayment21),
                    UtilFormat.format(investment.subtract(downPayment21)),

                    UtilFormat.format(result[i].startBalance),
                    UtilFormat.format(result[i].monthlyPayment),

                    UtilDateTime.format(result[i].termDate),
                    UtilFormat.format(result[i].termBalance), UtilFormat.format(result[i].termPrincipal),
                    UtilFormat.format(result[i].termIntrest), UtilFormat.format(result[i].termPayment),

                    UtilDateTime.format(result[i].termDate10),
                    UtilFormat.format(result[i].termBalance10), UtilFormat.format(result[i].termPrincipal10),
                    UtilFormat.format(result[i].termIntrest10), UtilFormat.format(result[i].termPayment10),

                    UtilDateTime.format(result[i].endYear),
                    UtilDateTime.format(result[i].endYear-1971),
                    UtilFormat.format(result[i].endPayment), UtilFormat.format(result[i].endIntrest));
        }

    }

    public static void forecast(){

        BigDecimal investment = new BigDecimal("230000.00", MortgageSettings.MC_CRY);

        Result[] result = new Result[]{
                calculate(new BigDecimal("230000.0", MortgageSettings.MC_CRY), new BigDecimal("230000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(10, 0)),
                calculate(new BigDecimal("310000.0", MortgageSettings.MC_CRY), new BigDecimal("310000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(15, 0)),
                calculate(new BigDecimal("420000.0", MortgageSettings.MC_CRY), new BigDecimal("420000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(25, 0)),
                calculate(new BigDecimal("490000.0", MortgageSettings.MC_CRY), new BigDecimal("490000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
                calculate(new BigDecimal("590000.0", MortgageSettings.MC_CRY), new BigDecimal("590000.0", MortgageSettings.MC_CRY), new AmortizationPheriod(35, 0)),
         };

        //String fmt = "%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s%15s\n";
        String fmt = "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n";

        System.out.printf(fmt, "Years",
                "House MAX", "Down", "Invest",
                "House MID", "Down", "Invest",
                "House MIN", "Down, 21%", "Invest",
                "Morgage", "Pay, mo",
                "Term, 3y", "Balance, 3y", "Principal, 3y", "Intrest, 3y", "Pay, 3y",
                "Term, 10y", "Balance, 10y", "Principal, 10y", "Intrest, 10y", "Pay, 10y",
                "End", "Age",
                "Pay, ttl", "Intrest, ttl");
        for(int i=0; i<result.length; ++i){

            BigDecimal downPayment21 = result[i].startBalance.divide(new BigDecimal(100.0/21.0-1.0), MortgageSettings.MC_CRY);
            BigDecimal minHousePrice = downPayment21.add(result[i].startBalance);

            BigDecimal maxInvestment = new BigDecimal("0.00", MortgageSettings.MC_CRY);
            BigDecimal maxDownPayment = investment.subtract(maxInvestment);
            BigDecimal maxHousePrice = maxDownPayment.add(result[i].startBalance);

            BigDecimal midInvestment = new BigDecimal("70000.00", MortgageSettings.MC_CRY);
            BigDecimal midDownPayment = investment.subtract(midInvestment);
            BigDecimal midHousePrice = midDownPayment.add(result[i].startBalance);

            System.out.printf(fmt,
                    UtilDateTime.format(result[i].term),

                    UtilFormat.format(maxHousePrice),
                    UtilFormat.format(maxDownPayment),
                    UtilFormat.format(maxInvestment),

                    UtilFormat.format(midHousePrice),
                    UtilFormat.format(midDownPayment),
                    UtilFormat.format(midInvestment),

                    UtilFormat.format(minHousePrice),
                    UtilFormat.format(downPayment21),
                    UtilFormat.format(investment.subtract(downPayment21)),

                    UtilFormat.format(result[i].startBalance),
                    UtilFormat.format(result[i].monthlyPayment),

                    UtilDateTime.format(result[i].termDate),
                    UtilFormat.format(result[i].termBalance), UtilFormat.format(result[i].termPrincipal),
                    UtilFormat.format(result[i].termIntrest), UtilFormat.format(result[i].termPayment),

                    UtilDateTime.format(result[i].termDate10),
                    UtilFormat.format(result[i].termBalance10), UtilFormat.format(result[i].termPrincipal10),
                    UtilFormat.format(result[i].termIntrest10), UtilFormat.format(result[i].termPayment10),

                    UtilDateTime.format(result[i].endYear),
                    UtilDateTime.format(result[i].endYear-1971),
                    UtilFormat.format(result[i].endPayment), UtilFormat.format(result[i].endIntrest));
        }

    }

    public static Result calculate(BigDecimal houseprice, BigDecimal morgage, AmortizationPheriod amortization){
        Mortgage mortgage = new Mortgage(new MortgageSettings());
        MortgageContext mortgageContext = new MortgageContext(ANUAL_RATE, morgage, amortization, START_DATE, YEAR_TERM);
        mortgage.computate(mortgageContext);

        AmortizationTable amortizationTable = new AmortizationTable(mortgageContext, PaymentFrequency.MONTHLY);

        if( doPrint ) TextPrinterHelper.printInput(System.out, mortgageContext, "CANADIAN MORTGAGE CALCULATOR");
        if( doPrint ) TextPrinterHelper.printPayments(System.out, mortgageContext);
        if( doPrint ) TextPrinterHelper.printAmortization(System.out, mortgageContext, amortizationTable, AmortizationType.BY_MONTH);

        Iterator<MortgageAmortizationRow> iter = amortizationTable.getIterator(AmortizationType.BY_MONTH, 0, 0);

        MortgageAmortizationRow row = null;
        Result result = new Result(houseprice, morgage);

        while( iter.hasNext() ){
            if( row == null ){
                row = iter.next();
                result.populateBegin(row);
            } else {
                row = iter.next();
                if( row.nop == 36){
                    result.populateTerm3(row);
                } else if( row.nop == 120){
                    result.populateTerm10(row);
                }
            }
        }
        result.populateEnd(row);

        return result;
    }

    static class Result {
        public Date startDate;
        public Date termDate;
        public Date termDate10;
        public Date endDate;
        public BigDecimal startBalance;
        public BigDecimal termBalance;
        public BigDecimal termBalance10;
        public BigDecimal monthlyPayment;
        public BigDecimal termIntrest;
        public BigDecimal termIntrest10;
        public BigDecimal endIntrest;
        public BigDecimal termPrincipal;
        public BigDecimal termPrincipal10;
        public BigDecimal endPayment;
        public BigDecimal termPayment;
        public BigDecimal termPayment10;
        public Integer term;
        public Integer endYear;
        public BigDecimal houseprice;
        public BigDecimal morgage;

        public Result(BigDecimal houseprice, BigDecimal morgage){
            this.houseprice = houseprice;
            this.morgage = morgage;
        }

        public Result(){
        }

        public void populateBegin(MortgageAmortizationRow row) {
            startDate = new Date(row.balanceInDate.getTime());
            startBalance = new BigDecimal(row.balanceIn.toPlainString());
            monthlyPayment = new BigDecimal(row.payment.toPlainString());
        }

        public void populateTerm3(MortgageAmortizationRow row) {
            termDate = new Date(row.balanceOutDate.getTime());
            termBalance = new BigDecimal(row.balanceOut.toPlainString());
            termIntrest = new BigDecimal(row.totalInterest.toPlainString());
            termPrincipal = new BigDecimal(row.totalPrincipal.toPlainString());
            termPayment = new BigDecimal(row.totalPayment.toPlainString());
        }

        public void populateTerm10(MortgageAmortizationRow row) {
            termDate10 = new Date(row.balanceOutDate.getTime());
            termBalance10 = new BigDecimal(row.balanceOut.toPlainString());
            termIntrest10 = new BigDecimal(row.totalInterest.toPlainString());
            termPrincipal10 = new BigDecimal(row.totalPrincipal.toPlainString());
            termPayment10 = new BigDecimal(row.totalPayment.toPlainString());
        }

        public void populateEnd(MortgageAmortizationRow row) {
            endDate = new Date(row.balanceOutDate.getTime());
            endIntrest = new BigDecimal(row.totalInterest.toPlainString());
            endPayment = new BigDecimal(row.totalPayment.toPlainString());
            term = row.nop/12;
            endYear = row.balanceOutYear;
        }
    }
}
