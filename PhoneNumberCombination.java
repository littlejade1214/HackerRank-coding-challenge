class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> set = new LinkedList<String>();
        String[] map = new String[] {"0", "1", "abc", "def", "ghi", 
                                       "jkl", "mno", "pqrs", "tuv", "wxyz"};
        int index = 0, length = digits.length();
        if (length == 0) {
            return set;
        }
        StringBuilder sequence = new StringBuilder();
        letterCombinationsHelper(set, map, digits, index, length, sequence);
        return set;
    }
    
    public void letterCombinationsHelper(List<String> set, String[] map, String digits,
                            int index, int length, StringBuilder sequence) {
        if (index == length) {
            String leaf = sequence.toString();
            set.add(leaf);
        } else {
            int label = digits.charAt(index) - '0';
            if (label <= 1)
                return;
            String charSet = map[label];
            for (int i = 0; i < charSet.length(); i++) {
                sequence.append(charSet.charAt(i));
                letterCombinationsHelper(set, map, digits, index + 1, length, sequence);
                sequence.deleteCharAt(index);
            }
        }
    }
}