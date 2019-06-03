public class Solution {	
    static long success(String first, String second, String s) {
        int n = second.length();
        long count = 0;
        String temp;
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                temp = first + second;
                if (temp.equals(s)) {
                    count = count + (long) 1;
                }
            } else if (i == n) {
                temp = second + first;
                if (temp.equals(s)) {
                    count = count + (long) 1;
                }
            } else {
                temp = second.substring(0, i) + first + second.substring(i, n);
                if (temp.equals(s)) {
                    count = count + (long) 1;
                }
            }
        }
        return count;
    }
    
    static long countCutsAndInserts(String s) {
        int n = s.length();
        long count = 0;
        for (int i = 0; i < n; i++) {
            if (i % n == i) {
                for(int j = 1; j < n - i + 1; j++) {
                    String str_a = s.substring(j-1, i+j);
                    String str_b = s.substring(0, j-1) + s.substring(i+j, n);
                    count = count + success(str_a, str_b, s);
                }
            }
        }
        return count;
    }

    public static void main(String []args) {
        String s = "aab";
        long result = countCutsAndInserts(s);
        System.out.println(result);
    }
}
