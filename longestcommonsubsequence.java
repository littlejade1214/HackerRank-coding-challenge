public class Solution {

    // Complete the commonChild function below.
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

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = scanner.nextLine();

        String s2 = scanner.nextLine();

        int result = commonChild(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}