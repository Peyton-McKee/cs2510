// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  // sorts the list of strings in alphabetical order
  ILoString sort();

  // alternates the values of this and the passed in strs
  /*
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  ILoString interleave(ILoString strs);

  /*
   * merges the two lists and sorts them
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  ILoString merge(ILoString strs);

  // reverses the list of strings
  ILoString reverse();

  // determines whether the list of strings is empty or not
  boolean isEmpty();

  // determines whether or not the list is sorted
  boolean isSorted();

  /*
   * creates a new list that tracks past values to help reverse list
   * Parameters
   * -- past -- ILoString
   * methods of paramaters
   * -- past.combine -- String
   * -- past.sort -- ILoString
   * -- interleave -- ILoString
   * -- merge -- ILoString
   * -- reverse -- ILoString
   * -- isEmpty -- boolean
   * -- isSorted -- boolean
   * -- reverseHelper -- ILoString
   * -- isDoubledList -- boolean
   * -- isPalindromeList -- boolean
   * -- length -- int
   * -- lengthOfFirst -- int
   * -- removeFirst -- ILoString
   * -- mergeHelper -- boolean
   */
  ILoString reverseHelper(ILoString past);

  // determines if this list is a doubled list
  boolean isDoubledList();

  // determines if this list is a palindrome list
  boolean isPalindromeList();

  // the length of the list
  int length();

  // length of the first string in the list
  int lengthOfFirst();

  // removes the first string from the list
  ILoString removeFirst();

  // determines whether the the given string comes in front of the this.first
  public boolean mergeHelper(String str);

}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  MtLoString() {
  }

  /*
   * TEMPLATE
   * METHODS
   * -- this.combine -- String
   * -- this.sort -- ILoString
   * -- this.interleave -- ILoString
   * -- this.merge -- ILoString
   * -- this.reverse -- ILoString
   * -- this.isEmpty -- boolean
   * -- this.isSorted -- boolean
   * -- this.reverseHelper -- ILoString
   * -- this.isDoubledList -- boolean
   * -- this.isPalindromeList -- boolean
   * -- this.length -- int
   * -- this.lengthOfFirst -- int
   * -- this.removeFirst -- ILoString
   * -- this.mergeHelper -- boolean
   * 
   */

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // sorts the list of strings in alphabetical order
  public ILoString sort() {
    return this;
  }

  // alternates the values of this and the passed in strs
  /*
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  public ILoString interleave(ILoString strs) {
    return strs;
  }

  /*
   * merges the two lists and sorts them
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  public ILoString merge(ILoString strs) {
    return strs;
  }

  // reverses the list of strings
  public ILoString reverse() {
    return this;
  }

  /*
   * creates a new list that tracks past values to help reverse list
   * Parameters
   * -- past -- ILoString
   * methods of paramaters
   * -- past.combine -- String
   * -- past.sort -- ILoString
   * -- past.interleave -- ILoString
   * -- past.merge -- ILoString
   * -- past.reverse -- ILoString
   * -- past.isEmpty -- boolean
   * -- past.isSorted -- boolean
   * -- past.reverseHelper -- ILoString
   * -- past.isDoubledList -- boolean
   * -- past.isPalindromeList -- boolean
   * -- past.length -- int
   * -- past.lengthOfFirst -- int
   * -- past.removeFirst -- ILoString
   * -- past.mergeHelper -- boolean
   */
  public ILoString reverseHelper(ILoString past) {
    return past;
  }

  // determines whether or not the list is sorted
  public boolean isSorted() {
    return true;
  }

  // determines whether the list of strings is empty or not
  public boolean isEmpty() {
    return true;
  }

  // determines if this list is a doubled list
  public boolean isDoubledList() {
    return true;
  }

  // the length of the list
  public int length() {
    return 1;
  }

  // determines if this list is a palindrome list
  public boolean isPalindromeList() {
    return true;
  }

  // length of the first string in the list
  public int lengthOfFirst() {
    return 0;
  }

  // removes the first string from the list
  public ILoString removeFirst() {
    return this;
  }

  // determines whether the the given string comes in front of the this.first
  public boolean mergeHelper(String str) {
    return true;
  }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE
   * FIELDS:
   * ... this.first ... -- String
   * ... this.rest ... -- ILoString
   * METHODS
   * -- this.combine -- String
   * -- this.sort -- ILoString
   * -- this.interleave -- ILoString
   * -- this.merge -- ILoString
   * -- this.reverse -- ILoString
   * -- this.isEmpty -- boolean
   * -- this.isSorted -- boolean
   * -- this.reverseHelper -- ILoString
   * -- this.isDoubledList -- boolean
   * -- this.isPalindromeList -- boolean
   * -- this.length -- int
   * -- this.lengthOfFirst -- int
   * -- this.removeFirst -- ILoString
   * -- this.mergeHelper -- boolean
   * METHODS FOR FIELDS
   * -- this.second.combine -- String
   * -- this.second.sort -- ILoString
   * -- this.second.interleave -- ILoString
   * -- this.second.merge -- ILoString
   * -- this.second.reverse -- ILoString
   * -- this.second.isEmpty -- boolean
   * -- this.second.isSorted -- boolean
   * -- this.second.reverseHelper -- ILoString
   * -- this.second.isDoubledList -- boolean
   * -- this.second.isPalindromeList -- boolean
   * -- this.second.length -- int
   * -- this.second.lengthOfFirst -- int
   * -- this.second.removeFirst -- ILoString
   * -- this.second.mergeHelper -- boolean
   * 
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sorts the list of strings in alphabetical order
  public ILoString sort() {
    if (this.rest.isEmpty()) {
      return this;
    }
    ILoString ans;
    int compareValue = this.first.toLowerCase().compareTo(this.rest.combine().toLowerCase());
    if (compareValue <= 0) {
      ans = new ConsLoString(this.first, this.rest.sort());
    }
    else {
      ans = this.rest.interleave(new ConsLoString(this.first, new MtLoString()));
    }
    if (!ans.isSorted()) {
      return ans.sort();
    }
    return ans;
  }

  // determines whether or not the list is sorted
  public boolean isSorted() {
    if (this.rest.isEmpty()) {
      return true;
    }
    return this.first.toLowerCase().compareTo(this.rest.combine().toLowerCase()) <= 0
        && this.rest.isSorted();
  }

  /*
   * merges the two lists and sorts them
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  public ILoString merge(ILoString strs) {
    if (strs.mergeHelper(this.first)) {
      return new ConsLoString(this.first, strs.merge(this.rest));
    }
    return strs.merge(this);
  }

  // determines whether the the given string comes in front of the this.first
  public boolean mergeHelper(String str) {
    return str.toLowerCase().compareTo(this.first.toLowerCase()) <= 0;
  }

  /*
   * merges the two lists and sorts them
   * Parameters
   * -- strs -- ILoString
   * methods of paramaters
   * -- strs.combine -- String
   * -- strs.sort -- ILoString
   * -- strs.interleave -- ILoString
   * -- strs.merge -- ILoString
   * -- strs.reverse -- ILoString
   * -- strs.isEmpty -- boolean
   * -- strs.isSorted -- boolean
   * -- strs.reverseHelper -- ILoString
   * -- strs.isDoubledList -- boolean
   * -- strs.isPalindromeList -- boolean
   * -- strs.length -- int
   * -- strs.lengthOfFirst -- int
   * -- strs.removeFirst -- ILoString
   * -- strs.mergeHelper -- boolean
   */
  public ILoString interleave(ILoString strs) {
    return new ConsLoString(this.first, strs.interleave(this.rest));
  }

  // reverses the list of strings
  public ILoString reverse() {
    return this.reverseHelper(new MtLoString());
  }

  /*
   * merges the two lists and sorts them
   * Parameters
   * -- past -- ILoString
   * methods of paramaters
   * -- past.combine -- String
   * -- past.sort -- ILoString
   * -- past.interleave -- ILoString
   * -- past.merge -- ILoString
   * -- past.reverse -- ILoString
   * -- past.isEmpty -- boolean
   * -- past.isSorted -- boolean
   * -- past.reverseHelper -- ILoString
   * -- past.isDoubledList -- boolean
   * -- past.isPalindromeList -- boolean
   * -- past.length -- int
   * -- past.lengthOfFirst -- int
   * -- past.removeFirst -- ILoString
   * -- past.mergeHelper -- boolean
   */
  public ILoString reverseHelper(ILoString past) {
    ILoString ans = new ConsLoString(this.first, past);
    return this.rest.reverseHelper(ans);
  }

  // determines whether the list of strings is empty or not
  public boolean isEmpty() {
    return false;
  }

  // the length of the list
  public int length() {
    return 1 + this.rest.length();
  }

  // determines if this list is a doubled list
  public boolean isDoubledList() {
    if (this.length() % 2 == 1) {
      return true;
    }
    return this.first.equals(this.rest.combine().substring(0, first.length() - 1))
        && this.rest.isDoubledList();
  }

  // determines if this list is a palindrome list
  public boolean isPalindromeList() {
    return this.first.equals(this.reverse().combine().substring(0, this.reverse().lengthOfFirst()))
        && this.rest.reverse().removeFirst().isPalindromeList();
  }

  // length of the first string in the list
  public int lengthOfFirst() {
    return this.first.length();
  }

  // removes the first string from the list
  public ILoString removeFirst() {
    return this.rest;
  }
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  ILoString joe = new ConsLoString("Joe ", new ConsLoString("wants ", new ConsLoString("his ",
      new ConsLoString("large ", new ConsLoString("hen.", new MtLoString())))));

  ILoString empty = new MtLoString();

  ILoString palindrome = new ConsLoString("hat",
      new ConsLoString("cat", new ConsLoString("cat", new ConsLoString("hat", new MtLoString()))));

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method sort for the lists of Strings
  boolean testSort(Tester t) {
    return t.checkExpect(this.mary.sort(),
        new ConsLoString("a ",
            new ConsLoString("had ",
                new ConsLoString("lamb.",
                    new ConsLoString("little ", new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(this.joe.sort(),
            new ConsLoString("hen.", new ConsLoString("his ", new ConsLoString("Joe ",
                new ConsLoString("large ", new ConsLoString("wants ", new MtLoString()))))));
  }

  // tests the method interleave for the list of Strings
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.mary.interleave(this.joe), new ConsLoString("Mary ",
        new ConsLoString("Joe ", new ConsLoString("had ", new ConsLoString("wants ",
            new ConsLoString("a ", new ConsLoString("his ", new ConsLoString("little ",
                new ConsLoString("large ",
                    new ConsLoString("lamb.", new ConsLoString("hen.", new MtLoString())))))))))))
        && t.checkExpect(this.mary.interleave(this.empty),
            new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
                new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString()))))));
  }

  // tests the method merge for the list of strings
  boolean testMerge(Tester t) {
    return t.checkExpect(this.mary.sort().merge(this.joe.sort()), new ConsLoString("a ",
        new ConsLoString("had ", new ConsLoString("hen.", new ConsLoString("his ",
            new ConsLoString("Joe ", new ConsLoString("lamb.", new ConsLoString("large ",
                new ConsLoString("little ",
                    new ConsLoString("Mary ", new ConsLoString("wants ", new MtLoString())))))))))))
        && t.checkExpect(this.mary.sort().merge(this.empty), this.mary.sort());
  }

  // tests the method reverse for the list of strings
  boolean testReverse(Tester t) {
    return t.checkExpect(this.mary.reverse(),
        new ConsLoString("lamb.",
            new ConsLoString("little ",
                new ConsLoString("a ",
                    new ConsLoString("had ", new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(this.empty.reverse(), this.empty);
  }

  // tests the method isSorted for the list of strings
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.mary.sort().isSorted(), true)
        && t.checkExpect(this.empty.isSorted(), true);
  }

  // tests the method isDoubledList for the list of strings
  boolean testDoubledList(Tester t) {
    return t.checkExpect(this.mary.isDoubledList(), false)
        && t.checkExpect(this.mary.interleave(this.mary).isDoubledList(), true)
        && t.checkExpect(this.empty.isDoubledList(), true);
  }

  // tests the method isPalindromeList for the list of strings
  boolean testIsPalindrome(Tester t) {
    return t.checkExpect(this.mary.isPalindromeList(), false)
        && t.checkExpect(this.empty.isPalindromeList(), true)
        && t.checkExpect(this.palindrome.isPalindromeList(), true);
  }

  // test the method length for the list of strings
  boolean testLength(Tester t) {
    return t.checkExpect(this.empty.length(), 1) && t.checkExpect(this.mary.length(), 6);
  }

  // tests the reverseHelper function for the list of strings
  boolean testReverseHelper(Tester t) {
    return t.checkExpect(this.empty.reverseHelper(this.empty), this.empty)
        && t.checkExpect(this.mary.reverseHelper(this.empty),
            new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("a ",
                new ConsLoString("had ", new ConsLoString("Mary ", this.empty))))));
  }

  // tests the removeFirst function
  boolean testRemoveFirst(Tester t) {
    return t.checkExpect(this.empty.removeFirst(), this.empty)
        && t.checkExpect(this.mary.removeFirst(), new ConsLoString("had ", new ConsLoString("a ",
            new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));
  }

  // tests the mergeHelper function
  boolean testMergeHelper(Tester t) {
    return t.checkExpect(this.empty.mergeHelper("hi"), true)
        && t.checkExpect(this.mary.mergeHelper("zoo"), false)
        && t.checkExpect(this.mary.mergeHelper("alpacca"), true);
  }
}