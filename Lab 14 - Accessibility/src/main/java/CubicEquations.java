import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CubicEquations {

    boolean more;

    private MathContext mathContext;

    private BigDecimal a, b, c, d;

    private BigDecimal x1, x2, x3;

    public CubicEquations(double na, double nb, double nc, double nd, int prec){
        mathContext = new MathContext(prec, RoundingMode.HALF_DOWN);
        a = new BigDecimal(na , mathContext);
        b = new BigDecimal(nb, mathContext);
        c = new BigDecimal(nc, mathContext);
        d = new BigDecimal(nd, mathContext);

        BigDecimal temp1 = b.divide(a, mathContext);
        BigDecimal temp2 = c.divide(a, mathContext);
        BigDecimal temp3 = d.divide(a, mathContext);

        BigDecimal q = temp1.pow(2, mathContext);
        BigDecimal tmp = (new BigDecimal(3, mathContext).multiply(temp2, mathContext));
        q = q.subtract(tmp, mathContext);
        q = q.divide(new BigDecimal(9, mathContext), mathContext);

        BigDecimal r = temp1.pow(3, mathContext);
        r = r.multiply(new BigDecimal(2, mathContext), mathContext);
        tmp = temp1.multiply(temp2, mathContext);
        tmp = tmp.multiply(new BigDecimal(9, mathContext), mathContext);
        r = r.subtract(tmp, mathContext);
        tmp = temp3.multiply(new BigDecimal(27, mathContext), mathContext);
        r = r.add(tmp, mathContext);
        r = r.divide(new BigDecimal(54, mathContext), mathContext);

        BigDecimal q3 = q.pow(3, mathContext);

        BigDecimal ddd = q3.subtract(r.pow(2, mathContext), mathContext);

        if (ddd.doubleValue() >= 0) {
            more = true;
            BigDecimal th = BigDecimalMath.sqrt(q3, mathContext);
            th = r.divide(th, mathContext);
            th = BigDecimalMath.acos(th, mathContext);

            BigDecimal sqq = BigDecimalMath.sqrt(q, mathContext);

            x1 = th.divide(new BigDecimal(3, mathContext), mathContext);
            x1 = BigDecimalMath.cos(x1, mathContext);
            x1 = x1.multiply(sqq, mathContext).multiply(new BigDecimal(-2, mathContext), mathContext);
            tmp = temp1.divide(new BigDecimal(3, mathContext), mathContext);
            x1 = x1.subtract(tmp);

            x2 = BigDecimalMath.pi(mathContext).multiply(new BigDecimal(2, mathContext), mathContext);
            x2 = BigDecimalMath.cos(x2.add(th, mathContext).divide(new BigDecimal(3, mathContext), mathContext), mathContext).multiply(sqq, mathContext).multiply(new BigDecimal(-2, mathContext), mathContext);
            x2 = x2.subtract(tmp);

            x3 = BigDecimalMath.pi(mathContext).multiply(new BigDecimal(4, mathContext), mathContext);
            x3 = BigDecimalMath.cos(x3.add(th, mathContext).divide(new BigDecimal(3, mathContext), mathContext), mathContext).multiply(sqq, mathContext).multiply(new BigDecimal(-2, mathContext), mathContext);
            x3 = x3.subtract(tmp);

        }else{
            more = false;
            BigDecimal e = BigDecimalMath.sqrt(ddd.negate(mathContext), mathContext);
            tmp = r.abs(mathContext);
            e = e.add(tmp, mathContext);
            tmp = new BigDecimal(1, mathContext).divide(new BigDecimal(3, mathContext), mathContext);
            e = BigDecimalMath.pow(e, tmp, mathContext);

            if(r.doubleValue() > 0) e = e.negate(mathContext);
            tmp = temp1.divide(new BigDecimal(3, mathContext), mathContext);
            x1 = (q.divide(e, mathContext).add(e, mathContext)).subtract(tmp, mathContext);
        }
        show();
    }

//    public CubicEquations(double a, double b, double c, double d) {
//        double temp1 = b/a;
//        double temp2 = c/a;
//        double temp3 = d/a;
//
//        double q = (Math.pow(temp1, 2) - 3 * temp2) / 9;
//        double r = (2 * Math.pow(temp1, 3) - 9 * temp1 * temp2 + 27 * temp3) / 54;
//
//        double q3 = Math.pow(q, 3);
//        double ddd = q3 - Math.pow(r, 2);
//
//        if (ddd >= 0){
//            more = true;
//            double th = Math.acos(r / Math.sqrt(q3));
//            double sqq = Math.sqrt(q);
//
//            double x1 = -2 * sqq * Math.cos(th / 3) - temp1 / 3;
//            double x2 = -2 * sqq * Math.cos((th + 2 * Math.PI) / 3) - temp1 / 3;
//            double x3 = -2 * sqq * Math.cos((th + 4 * Math.PI) / 3) - temp1 / 3;
//            System.out.println("x1: " + x1 + "\nx2: " + x2 + "\nx3: " + x3);
//        } else {
//            more = false;
//            double e = Math.pow(Math.sqrt(-ddd) + Math.abs(r), 1. / 3.);
//            if(r > 0) e = -e;
//            double x1 = (e + q / e) - temp1 / 3.;
//            System.out.println("x1: " + x1);
//        }
//    }

    public void show(){
        if(more){
            System.out.println("x1: " + x1 + "\nx2: " + x2 + "\nx3: " + x3);
        }else{
            System.out.println("x1: " + x1);
        }
    }

    public BigDecimal getX1() {
        return x1;
    }

    public BigDecimal getX2() {
        return x2;
    }

    public BigDecimal getX3() {
        return x3;
    }

    public boolean isMore() {
        return more;
    }
}
