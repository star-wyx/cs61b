public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> l = new LinkedListDeque<>();
        for (char c : word.toCharArray()) {
            l.addLast(c);
        }
        return l;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> l = wordToDeque(word);
        int size = l.size();
        while (size != 0 && size != 1) {
            if (l.removeFirst() != l.removeLast()) {
                return false;
            }
            size -= 2;
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> l = wordToDeque(word);
        int size = l.size();
        while (size != 0 && size != 1) {
            if (!cc.equalChars(l.removeFirst(), l.removeLast())) {
                return false;
            }
            size -= 2;
        }
        return true;
    }
}
