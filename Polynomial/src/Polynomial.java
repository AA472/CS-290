import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;

public class Polynomial {
    private ArrayList<Term> terms = new ArrayList<>();
    public ArrayList<Term> getTerms(){return terms;}

    Polynomial() {}
    Polynomial(String s) {
        PushbackInputStream s2 = new PushbackInputStream(new ByteArrayInputStream(s.getBytes()));
        try {
            while (s2.available() > 1) {
                Term T = nextTermFrom(s2);
                terms.add(T);
            }
        } catch (IOException ex) {
        }
    }
    public static Term nextTermFrom(PushbackInputStream s) {
        int sign = 1;
        int coef = 1;
        ArrayList<Character> vars = new ArrayList<>();
        ArrayList<Integer> exps = new ArrayList<>();
        try {
            if (s.available() == 0)
                return null;

            char ch = (char) s.read();
            if (ch == '-') {
                sign = -1;
                ch = (char) s.read();
            } else if (ch == '+') {
                ch = (char) s.read();
            }
            if (!Character.isDigit(ch)) {
                coef = 1;
            } else {
                coef = 0;
                while (Character.isDigit(ch)) {
                    coef = coef * 10 + ch - '0';
                    ch = (char) s.read();
                }
            }
            coef *= sign;
            while (Character.isLowerCase(ch)) {
                vars.add(ch);
                ch = (char) s.read();
                int exp;
                if (ch == '^') {
                    exp = 0;
                    ch = (char) s.read();
                    while (Character.isDigit(ch)) {
                        exp = exp * 10 + ch - '0';
                        ch = (char) s.read();
                    }
                } else {
                    exp = 1;
                }
                exps.add(exp);
            }
            s.unread(ch);
            // At this point the variables hold the following information about the Term:
            // coef - The coefficient of the Term
            // vars - The variables in the Term
            // exps - The exponent of the corresponding variable from vars
        } catch (IOException e) {
            return null;
        }
        return new Term(vars, exps, coef);
    }
    public String toString() {
        StringBuilder strb = new StringBuilder();
        if (terms.size() == 0) {
            strb.append('0');
            return strb.toString();
        }
        if (terms.get(0).getCoef() != 1)
             strb.append(terms.get(0).getCoef());

        for (int i = 0; i < terms.get(0).getVars().size(); i++) {
            strb.append(terms.get(0).getVars().get(i));
            if (terms.get(0).getExps().get(i) > 1)
                strb.append('^').append(terms.get(0).getExps().get(i));
        }
        for (int j = 1; j < terms.size(); j++) {
            if (terms.get(j).getCoef() > 1)
                strb.append('+').append(terms.get(j).getCoef());
            else if (terms.get(j).getCoef() == 1)
                strb.append('+');
            else if (terms.get(j).getCoef() == -1)
                strb.append('-');
            else
                strb.append(terms.get(j).getCoef());

            for (int i = 0; i < terms.get(j).getVars().size(); i++) {
                strb.append(terms.get(j).getVars().get(i));
                if (terms.get(j).getExps().get(i) != 1 && terms.get(j).getExps().get(i) != 0)
                    strb.append('^').append(terms.get(j).getExps().get(i));
            }
        }
        return strb.toString();
    }
    public void simplify() {
        for (int i = 0; i < terms.size(); i++) {
            for (int j = i + 1; j < terms.size(); j++) {
                Term t1 = terms.get(i);
                Term t2 = terms.get(j);
                if (t1.getVars().equals(t2.getVars()) && t1.getExps().equals(t2.getExps())) {
                    terms.get(i).setCoef(t1.getCoef() + t2.getCoef());
                    terms.remove(j);
                    j--;
                }
            }
        }
        for (int i = 0; i < terms.size(); i++) {
            Term t1 = terms.get(i);
            if (t1.getCoef() == 0) {
                terms.remove(i);
                i--;
            }
        }
    }
    public Polynomial add(Polynomial p2) {
        Polynomial sum = new Polynomial();
        for (Term t1 : terms) {
            Term temp = new Term(t1.getVars(), t1.getExps(), t1.getCoef());
            sum.terms.add(temp);
        }

        for (Term t2 : p2.terms) {
            Term temp = new Term(t2.getVars(), t2.getExps(), t2.getCoef());
            sum.terms.add(temp);
        }
        //sum.terms.addAll(terms);
        //sum.terms.addAll(p2.terms);
        sum.simplify();
        return sum;
    }
    public Polynomial sub(Polynomial p2) {
        Polynomial sum = new Polynomial();
        /*sum.terms.addAll(p2.terms);
        for (Term t: sum.terms)
            t.coef = t.coef * (-1);
        sum.terms.addAll(p1.terms);*/
        for (Term t1 : terms) {
            Term temp = new Term(t1.getVars(), t1.getExps(), t1.getCoef());
            sum.terms.add(temp);
        }

        for (Term t2 : p2.terms) {
            Term temp = new Term(t2.getVars(), t2.getExps(), t2.getCoef() * (-1));
            sum.terms.add(temp);
        }
        sum.simplify();
        return sum;
    }
    public Polynomial mult(Polynomial p2){
        Polynomial prod = new Polynomial();
        for(Term t1 : terms) {
            for (Term t2 : p2.terms) {
                ArrayList <Character> varsProd = new ArrayList<>();
                ArrayList <Integer> expsProd = new ArrayList<>();
                varsProd.addAll(t1.getVars());
                expsProd.addAll(t1.getExps());
                varsProd.addAll(t2.getVars());
                expsProd.addAll(t2.getExps());
                Term temp = new Term(varsProd,expsProd,t1.getCoef()*t2.getCoef());
                temp.TermSimplify();
                prod.terms.add(temp);
            }
        }
        prod.simplify();
        return prod;
    }
    public double evaluate(ArrayList<Character> variables, ArrayList<Double> values){
        double result=0;
        try {
            for(Term t: terms){
                result+= t.evaluate_term(variables,values);
            }
        }catch (ArithmeticException E){}
        return result;
    }
}

