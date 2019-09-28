import java.util.ArrayList;
import java.util.Collections;

public class Term {
    private int coef = 0;
    private ArrayList<Character> vars = new ArrayList<>();
    private ArrayList<Integer> exps = new ArrayList<>();

    public Term(ArrayList<Character> vars, ArrayList<Integer> exps, int coef){
        this.coef = coef;
        this.vars.addAll(vars);
        this.exps.addAll(exps);
        sortvars();
    }
    public int getCoef(){return coef;}
    public void setCoef(int coef) {this.coef = coef;}
    public ArrayList<Character> getVars() { return vars;}
    public ArrayList<Integer> getExps() { return exps; }

    public void sortvars(){
        char smallestvar;
        int smallestvarindex;

        for(int i = 0; i < vars.size(); i++){
            smallestvar = vars.get(i);
            smallestvarindex = i;
            for(int j = i+1; j < vars.size();j++){
                if(vars.get(j) < smallestvar){
                    smallestvar = vars.get(j);
                    smallestvarindex = j;
                }
            }
            Collections.swap(vars,i,smallestvarindex);
            Collections.swap(exps,i,smallestvarindex);
        }
    }
    public double evaluate_term(ArrayList<Character> variables, ArrayList<Double> values){
        double term_result = coef;
        int j;
        for (int i=0; i<vars.size();i++){
            j = variables.lastIndexOf(vars.get(i));
            term_result *= Math.pow(values.get(j),exps.get(i));
        }
        return term_result;
    }
    public void TermSimplify(){
        for (int i =0; i<this.vars.size();i++) {
            for (int j = i + 1; j < this.vars.size(); j++) {
                if (vars.get(i) == vars.get(j)) {
                    vars.remove(j);
                    exps.set(i, exps.get(i) + exps.get(j));
                    exps.remove(j);
                    j--;
                }
            }
        }
    }
}
