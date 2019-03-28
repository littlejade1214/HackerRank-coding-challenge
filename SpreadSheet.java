package SignifydTest;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A spreadsheet consists of a two-dimensional array of cells, labeled A1, B1,
 * ...., A2, B2, .... Each cell contains either a number (its value) or an
 * expression.
 * 
 * <p>
 * For simplicity, expressions are given as RPN (reverse-polish notation). They
 * contain space-separated terms that are either numbers (in our case
 * non-negative values), cell references, and the operators '+', '-', '*', '/'.
 * 
 * <p>
 * Examples:
 * 
 * +----------------------+------------------+--------------+ | RPN | infix
 * notation | evaluates to |
 * +----------------------+------------------+--------------+ | "10 4 /" |
 * "10/4" | 2.5 | +----------------------+------------------+--------------+ |
 * "10 1 1 + / 1 +" | "10/(1+1) + 1 | 6.0 |
 * +----------------------+------------------+--------------+ |
 * "10 2 3 + / 4 5 + *" | "10/(2+3)*(4+5)" | 18.0 |
 * +----------------------+------------------+--------------+
 * 
 * <p>
 * Your task is to implement a functional SpreadSheet. The main functionality
 * is:
 * <li>1. constructor: construct the spreadsheet from the provided expressions.
 * <li>2. dump(): solve the spreadsheet and return the result. If a cycle of
 * references exists, detect it and throw a CircularReferenceException.
 * 
 * <p>
 * Comments: You can assume that there are no more than 26 columns (A-Z) in the
 * spreadsheet.
 */
public class SpreadSheet {
    /**
     * Construct a nRows x nCols SpreadSheet, with cells containing the
     * expressions passed in the exprArray.
     * 
     * <p>
     * The expressions passed in the exprArray String array are in row by row
     * order, i.e.:
     * 
     * +----+----+----+ | A1 | B1 | C1 | +----+----+----+ | A2 | B2 | C2 |
     * +----+----+----+
     * 
     * etc.
     * 
     * @param nRows
     * @param nCols
     * @param exprArray
     */

    // store the express for each cell, and also the reference graph
    private Cell[] table = null;
    // store the order of the evaluation, used in dump()
    private int[] order = null;
    private int nRows, nCols;
    // store the in-degree of the reference graph, used in checking cycle
    private int[] indegs = null;

    public SpreadSheet(int nRows, int nCols, String... exprArray) {
	// TODO: put your code here
	table = new Cell[nRows * nCols];
	this.nRows = nRows;
	this.nCols = nCols;
	int cnt = 0;
	for (String str : exprArray) {
	    table[cnt] = new Cell(str);
	    cnt++;
	}
    }

    /**
     * map the cell reference to Cell
     * 
     * @param ref
     * @return
     */
    public Cell getCell(String ref) {
	int rdx = (int) (ref.charAt(0)) - (int) 'A';
	if (rdx < 0 || rdx >= nRows) {
	    return null;
	}
	int cdx = Integer.parseInt(ref.substring(1)) - 1;
	if (cdx < 0 || cdx >= nCols) {
	    return null;
	}
	return table[rdx * nCols + cdx];
    }

    public String getName(int row, int col) {
	return Character.toString((char) (col + (int) 'A'))
		+ Integer.toString(row + 1);
    }

    public int getIndex(String ref) {
	return getIndex(nRows, nCols, ref);
    }

    /**
     * get index for a cell reference
     * 
     * @param nRows
     * @param nCols
     * @param ref
     * @return
     */
    public static int getIndex(int nRows, int nCols, String ref) {
	int cdx = (int) (ref.charAt(0)) - (int) 'A';
	if (cdx < 0 || cdx >= nCols) {
	    return -1;
	}
	int rdx = Integer.parseInt(ref.substring(1)) - 1;
	if (rdx < 0 || rdx >= nRows) {
	    return -1;
	}
	return rdx * nCols + cdx;
    }

    /**
     * check the cycle of the graph also sort the evaluation order
     * 
     * @return
     */
    private boolean checkCycle() {
	order = new int[nRows * nCols];
	int i = 0;
	int j;
	indegs = new int[nRows * nCols];
	for (i = 0; i < table.length; i++) {
	    indegs[i] = 0;
	    order[i] = -1;
	}
	for (i = 0; i < table.length; i++) {
	    table[i].checkRef(nRows, nCols);
	    for (int k : table[i].getRefs()) {
		indegs[k]++;
	    }
	}

	ordermark = 0;

	for (i = 0; i < indegs.length; i++) {
	    if (indegs[i] == 0) {
		if (!visit(i, new HashSet<Integer>())) {
		    return false;
		}
	    }
	}
	for (i = 0; i < indegs.length; i++) {
	    if (order[i] < 0) {
		return false;
	    }
	}
	return true;
    }

    // topological sort
    private int ordermark = 0;

    private boolean visit(int id, HashSet<Integer> path) {
	if (path.contains(id)) {
	    return false;
	}
	if (order[id] < 0) {
	    path.add(id);
	    for (int k : table[id].getRefs()) {
		if (!visit(k, path)) {
		    return false;
		}
	    }
	    order[id] = ordermark;
	    ordermark++;
	    path.remove(id);
	}
	return true;
    }

    /**
     * @return the values from a "solved" SpreadSheet
     */
    public Double[] dump() throws CircularReferenceException {

	if (!checkCycle()) {
	    throw new CircularReferenceException("There is cycle!");
	}

	Double[] values = new Double[nRows * nCols];
	int[] dumporder = new int[nRows * nCols];
	int i, id;
	for (i = 0; i < order.length; i++) {
	    dumporder[order[i]] = i;
	}
	for (i = 0; i < dumporder.length; i++) {
	    id = dumporder[i];
	    values[id] = evaluate(table[id].getExpr(), values);
	}
	return values;
    }

    /**
     * evaluate a single cell
     * 
     * @param expr
     * @param values
     * @return
     */
    private Double evaluate(String[] expr, Double[] values) {
	ArrayList<Double> stk = new ArrayList<Double>();
	int id;
	double a, b;
	for (String str : expr) {
	    if (str.equals("+")) {
		b = stk.remove(stk.size() - 1);
		a = stk.remove(stk.size() - 1);
		stk.add(a + b);
	    } else if (str.equals("-")) {
		b = stk.remove(stk.size() - 1);
		a = stk.remove(stk.size() - 1);
		stk.add(a - b);
	    } else if (str.equals("*")) {
		b = stk.remove(stk.size() - 1);
		a = stk.remove(stk.size() - 1);
		stk.add(a * b);
	    } else if (str.equals("/")) {
		b = stk.remove(stk.size() - 1);
		a = stk.remove(stk.size() - 1);
		stk.add(a / b);
	    } else {
		id = getIndex(str);
		if (id >= 0) {
		    stk.add(values[id]);
		} else {
		    stk.add(Double.parseDouble(str));
		}
	    }
	}
	return stk.get(0);
    }

    public class CircularReferenceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CircularReferenceException(String msg) {
	    super(msg);
	}
    }
}
