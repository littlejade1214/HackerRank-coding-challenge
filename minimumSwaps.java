    
public class Solution {
    static int getIndex(int[] arr, int val, int n) {
        int index = -1;
        for (int i = val; i < n; i++) {
            if (arr[i] == val) {
                index = i;
                break;
            }
        }
        return index;
    }

    static int minimumSwaps(int[] arr, int n) {
        int count = 0;
        int pos = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == i + 1) {
                continue;
            } else {
                pos = getIndex(arr, i+1, n);
                int temp = arr[i];
                arr[i] = arr[pos];
                arr[pos] = temp;
                count++;
            }
        }
        return count;
    }

    private static final Scanner scanner = new Scanner(System.in);

    int n = scanner.nextInt();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
        int arrItem = Integer.parseInt(arrItems[i]);
        arr[i] = arrItem;
    }

    int res = minimumSwaps(arr, n);

    scanner.close();

}