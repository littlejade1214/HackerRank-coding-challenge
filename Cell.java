/**
 * 
 */
package SignifydTest;

import java.util.ArrayList;

/**
 *
 *
 * 
 */
public class Cell {
    
    public Cell(String expr) {
	this.expr = expr.split("\\s");
	refer = new ArrayList<Integer>();
    }
    
    //private String name;
    private String[] expr;
    private ArrayList<Integer> refer;
    
    public String[] getExpr() {
	return expr;
    }
    
    public ArrayList<Integer> getRefs() {
	return refer;
    }
    
//    public void addRefered(int id) {
//	refered.add(id);
//    }
    
    public void checkRef(int nRows, int nCols) {
	for (String str : expr) {
	    int num = SpreadSheet.getIndex(nRows, nCols, str);
	    if (num >= 0) {
		refer.add(num);
	    }		
	}
    }
    
}
