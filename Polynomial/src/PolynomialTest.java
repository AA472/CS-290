import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialTest {
    @Test
    public void constructorTest() {
        Polynomial p1 = new Polynomial("34x^7-y^6z^2+13x^3");
        Polynomial p2 = new Polynomial("2x^7-3x+i-2x^3");
        Polynomial p3 = new Polynomial("5x+5xy^2");
        Polynomial p4 = new Polynomial("2x^4+qr-5xy+9i");
        Polynomial p5 = new Polynomial("y^2-2x");

    }

    //I will use these in the other tests as well
    Polynomial p1 = new Polynomial("34x^7-y^6z^2+13x^3");
    Polynomial p2 = new Polynomial("2x^7-3x+i-2x^3");
    Polynomial p3 = new Polynomial("5x+5xy^2");
    Polynomial p4 = new Polynomial("2x^4+qr-5xy+9i");
    Polynomial p5 = new Polynomial("y^2-2x");

    @Test
    public void toStringTest() {
        assertEquals(p1.toString(),"34x^7-y^6z^2+13x^3");
        assertEquals(p2.toString(),"2x^7-3x+i-2x^3");
        assertEquals(p3.toString(),"5x+5xy^2");
        assertEquals(p4.toString(),"2x^4+qr-5xy+9i");
        assertEquals(p5.toString(),"y^2-2x");
        Polynomial p6 = new Polynomial("x");
        //System.out.println(p6.toString());

        System.out.println();
    }
    @Test
    public void sortvarsTest() {
        Polynomial p6 = new Polynomial("3cdab");
        p6.getTerms().get(0).sortvars();
        assertEquals(p6.toString(),"3abcd");

        System.out.println();
    }
    @Test
    public void simplifyTest() {
        Polynomial p6 = new Polynomial("25x^3+4x^3+y");
        Polynomial p7 = new Polynomial("5x-5x");
        p6.simplify();
        p7.simplify();
        assertEquals(p6.toString(),"29x^3+y");
        assertEquals(p7.toString(),"0");

        System.out.println();
    }

    @Test
    public void addTest() {
        Polynomial p7 = p1.add(p2);
        assertEquals(p7.toString(),"36x^7-y^6z^2+11x^3-3x+i");

        Polynomial p8 = p1.add(p3);
        assertEquals(p8.toString(),"34x^7-y^6z^2+13x^3+5x+5xy^2");

        Polynomial p9 = p3.add(p2);
        assertEquals(p9.toString(),"2x+5xy^2+2x^7+i-2x^3");

        System.out.println();
    }

    @Test
    public void subTest() {
        Polynomial p7 = p1.sub(p2);
        assertEquals(p7.toString(),"32x^7-y^6z^2+15x^3+3x-i");

        Polynomial p8 = p1.sub(p3);
        assertEquals(p8.toString(),"34x^7-y^6z^2+13x^3-5x-5xy^2");

        Polynomial p9 = p3.sub(p2);
        assertEquals(p9.toString(),"8x+5xy^2-2x^7-i+2x^3");

        System.out.println();
    }

    @Test
    public void multTest() {
        Polynomial p6 = new Polynomial("5x");
        Polynomial p7 = p6.mult(p6);
        assertEquals(p7.toString(),"25x^2");

        Polynomial p9 = new Polynomial("3x^2+4y");
        Polynomial p10 = new Polynomial("2x+y");
        Polynomial p11 = p9.mult(p10);
        assertEquals(p11.toString(),"6x^3+3x^2y+8xy+4y^2");

        System.out.println();
    }

    @Test
    public void evaluateTest() {
        ArrayList<Character> variables = new ArrayList<>(Arrays.asList('x', 'y', 'z'));
        ArrayList<Double> values = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0));
        Polynomial p6 = new Polynomial("3xy+z^2");
        assertEquals(p6.evaluate(variables,values),15.0);

        ArrayList<Character> variables2 = new ArrayList<>(Arrays.asList('x', 'y', 'z'));
        ArrayList<Double> values2 = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0));
        Polynomial p7 = new Polynomial("xyz");
        assertEquals(p7.evaluate(variables2,values2),6.0);
    }
}