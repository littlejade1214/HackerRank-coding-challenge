/**
*
Subset Sum DP Solution
*
*
**/

public boolean isSubsetSum(int[] set, int sum){
    int size = set.length;
    boolean[][] subset = new boolean[sum+1][size+1];
    for(int i = 0; i <= size; i++){
	subset[0][i] = true;
    }
    for(int i = 1; i <= sum; i++){
	subset[i][0] = false;
    }
    for(int i = 1; i <= sum; i++){
	for(int j = 1; j <= size; j++){
	    subset[i][j] = subset[i][j-1];
	    if(set[j-1] <= i){
		subset[i][j] = subset[i][j] || subset[i-set[j-1]][j-1];
	    }
	}
    }
    return subset[sum][size];
}


public void main(int[] set, int sum){
    int size = set.length;
    if(isSubsetSum(set, size, sum) == true){
	System.out.print("Found a subset with the given sum.");
    }else{
	System.out.print("No subset with the given sum.");
    }
}
