public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> l = new LinkedListDeque<>();
        for(int i=0;i<word.length();i++){
            l.addLast(word.charAt(i));
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
//        int size = l.size();
//        while (size != 0 && size != 1) {
//            if (!cc.equalChars(l.removeFirst(), l.removeLast())) {
//                return false;
//            }
//            size -= 2;
//        }
        while(l.size()>1){
            if(!cc.equalChars(l.removeFirst(),l.removeLast())){
                return false;
            }
        }
        return true;
    }
}
