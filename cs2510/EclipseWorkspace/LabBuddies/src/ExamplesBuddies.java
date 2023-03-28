import tester.Tester;

/**
 * runs tests for the buddies problem
 *
 * @author peytonmckee
 *
 */
public class ExamplesBuddies {
  Person ann;
  Person bob;
  Person cole;
  Person ed;
  Person hank;
  Person dan;
  Person fay;
  Person gabi;
  Person jan;
  Person kim;
  Person len;

  public ExamplesBuddies() {
    this.initBuddies();
  }

  /**
   * resets the values of the examples since we are mutating them
   */
  public void initBuddies() {
    this.ann = new Person("Ann");
    this.bob = new Person("Bob");
    this.cole = new Person("Cole");
    this.ed = new Person("Ed");
    this.hank = new Person("Hank");
    this.dan = new Person("Dan");
    this.fay = new Person("Fay");
    this.gabi = new Person("Gabi");
    this.jan = new Person("Jan");
    this.kim = new Person("Kim");
    this.len = new Person("Len");
    this.ann.addBuddy(this.bob);
    this.ann.addBuddy(this.cole);
    this.bob.addBuddy(this.ann);
    this.bob.addBuddy(this.ed);
    this.bob.addBuddy(this.hank);
    this.cole.addBuddy(this.dan);
    this.dan.addBuddy(this.cole);
    this.ed.addBuddy(this.fay);
    this.fay.addBuddy(this.ed);
    this.fay.addBuddy(this.gabi);
    this.gabi.addBuddy(this.ed);
    this.gabi.addBuddy(this.fay);
    this.jan.addBuddy(this.kim);
    this.jan.addBuddy(this.len);
    this.kim.addBuddy(this.jan);
    this.kim.addBuddy(this.len);
    this.len.addBuddy(this.jan);
    this.len.addBuddy(this.kim);
  }

  // tests the add buddy method
  void testAddBuddy(Tester t) {
    this.initBuddies();
    this.kim.addBuddy(this.hank);
    this.hank.addBuddy(this.kim);
    t.checkExpect(this.kim.buddies, new ConsLoBuddy(this.hank,
        new ConsLoBuddy(this.len, new ConsLoBuddy(this.jan, new MTLoBuddy()))));
    t.checkExpect(this.hank.buddies, new ConsLoBuddy(this.kim, new MTLoBuddy()));
  }

  // tests the contains method
  void testContains(Tester t) {
    this.initBuddies();
    t.checkExpect(
        new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy())).contains(this.ann),
        true);
    t.checkExpect(new MTLoBuddy().contains(this.ann), false);
    t.checkExpect(
        new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy())).contains(this.jan),
        false);
  }

  // test the hasDirectBuddy method
  void testHasDirectBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.hasDirectBuddy(this.ann), false);
    t.checkExpect(this.bob.hasDirectBuddy(this.ann), true);
    t.checkExpect(this.bob.hasDirectBuddy(this.kim), false);
  }

  // test the countCommonBuddies method
  void testCountCommonBuddies(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.countCommonBuddies(this.hank), 0);
    t.checkExpect(this.ann.countCommonBuddies(this.ann), 2);
    t.checkExpect(this.fay.countCommonBuddies(this.gabi), 1);
  }

  // test the sameDirectBuddy
  void testSameDirectBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy()))
        .sameDirectBuddy(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy()))), 2);
    t.checkExpect(new MTLoBuddy()
        .sameDirectBuddy(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy()))), 0);
    t.checkExpect(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy()))
        .sameDirectBuddy(new MTLoBuddy()), 0);
    t.checkExpect(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.jan, new MTLoBuddy()))
        .sameDirectBuddy(new ConsLoBuddy(this.ed,
            new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, new MTLoBuddy())))),
        1);
  }

  // test the hasExtendedBuddy method
  void testHasExtendedBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.hasExtendedBuddy(this.dan), true);
    t.checkExpect(this.hank.hasExtendedBuddy(this.ann), false);
    t.checkExpect(this.ann.hasExtendedBuddy(this.kim), false);
  }

  // test the hasThatBuddy method
  void testHasThatBuddy(Tester t) {
    this.initBuddies();
    t.checkExpect(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy()))
        .hasThatBuddy(this.ann), true);
    t.checkExpect(new MTLoBuddy().hasThatBuddy(this.cole), false);
    t.checkExpect(new ConsLoBuddy(this.cole,
        new ConsLoBuddy(this.jan, new ConsLoBuddy(this.gabi, new MTLoBuddy())))
            .hasThatBuddy(this.ann),
        false);
  }

  // test the length method
  void testLength(Tester t) {
    this.initBuddies();
    t.checkExpect(new ConsLoBuddy(this.bob, new ConsLoBuddy(this.ann, new MTLoBuddy())).length(),
        2);
    t.checkExpect(new MTLoBuddy().length(), 0);
    t.checkExpect(new ConsLoBuddy(this.cole,
        new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim, new MTLoBuddy()))).length(), 3);
  }

  // test partyCount method
  void testPartyCount(Tester t) {
    this.initBuddies();
    t.checkExpect(this.ann.partyCount(), 8);
    t.checkExpect(this.hank.partyCount(), 1);
    t.checkExpect(this.cole.partyCount(), 2);
    t.checkExpect(this.gabi.partyCount(), 3);
  }
}
