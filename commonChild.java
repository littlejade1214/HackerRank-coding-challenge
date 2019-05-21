public class Solution {
    static int commonChild(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int state[][] = new int[m+1][n+1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    state[i][j] = 0;
                } else if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    state[i][j] = state[i-1][j-1] + 1;
                } else {
                    state[i][j] = Math.max(state[i-1][j], state[i][j-1]);
                }
            }
        }
        return state[m][n];
    }
}
