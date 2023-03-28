import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> ans = new ArrayList<Character>(26);
    ArrayList<Character> temp = new ArrayList<Character>(this.alphabet);
    for (int i = 0; i < 26; i++) {
      int next = this.rand.nextInt(26 - i);
      ans.add(i, temp.get(next));
      temp.remove(next);
    }
    return ans;
  }

  // produce an encoded String from the given String
  String encode(String msg) {
    return code(msg, this.alphabet, this.code);
  }

  // produce a decoded String from the given String
  String decode(String msg) {
    return code(msg, this.code, this.alphabet);
  }

  String code(String msg, ArrayList<Character> from, ArrayList<Character> to) {
    String ans = "";
    for (char c : msg.toLowerCase().toCharArray()) {
      int index = from.indexOf(c);
      ans += to.get(index);
    }
    return ans;
  }
}

class ExamplesPermutation {

  PermutationCode perm = new PermutationCode();

  void testInit(Tester t) {
    t.checkExpect(this.perm.encode("Hello"), "Hello");
  }
}