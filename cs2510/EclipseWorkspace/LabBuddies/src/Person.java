// represents a Person with a user name and a list of buddies
class Person {
  /*
   * TEMPLATE:
   * Fields:
   * ... this.username ... -- String
   * ... this.buddies ... -- ILoBuddy
   * Methods:
   * ... this.hasDirectBuddy(Person that) ... -- boolean
   * ... this.partyCount() ... -- int
   * ... this.countCommonBuddies(Person that) ... -- int
   * ... this.hasExtendedBuddy(Person that) ... -- boolean
   * ... this.addBuddy(Person that) ... void
   * ... this.countOfThisBuddies() ... int
   * Methods on Fields:
   * ... buddies.contains(Person name) ... -- boolean
   * ... buddies.sameDirectBuddy(ILoBuddy buddies) ... -- int
   * ... buddies.hasThatBuddy(Person that) ... -- boolean
   * ... buddies.length() ... -- int
   * ... buddies.buddiesBuddyLength() ... -- int
   */
  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- that -- Person
     * Methods on Parameter
     * -- that.hasDirectBuddy -- boolean
     * -- that.partyCount -- int
     * -- that.countCommonBuddies == int
     * -- that.hasExtendedBuddy -- boolean
     * -- that.addBuddy -- void
     * Fields of Parameters
     * -- that.username -- string
     * -- that.buddies -- ILoBuddy
     */
    return this.buddies.contains(that);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return this.buddies.addBuddiesToParty(new ConsLoBuddy(this, new MTLoBuddy())).length();
  }

  //returns the amount of buddies this person has
  ILoBuddy addToParty(ILoBuddy partyInvites) {
    if (!partyInvites.contains(this)) {
      return this.buddies.addBuddiesToParty(new ConsLoBuddy(this, partyInvites));
    }
    return partyInvites;
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- that -- Person
     * Methods on Parameter
     * -- that.hasDirectBuddy -- boolean
     * -- that.partyCount -- int
     * -- that.countCommonBuddies == int
     * -- that.hasExtendedBuddy -- boolean
     * -- that.addBuddy -- void
     * Fields of Parameters
     * -- that.username -- string
     * -- that.buddies -- ILoBuddy
     */
    return this.buddies.sameDirectBuddy(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- that -- Person
     * Methods on Parameter
     * -- that.hasDirectBuddy -- boolean
     * -- that.partyCount -- int
     * -- that.countCommonBuddies == int
     * -- that.hasExtendedBuddy -- boolean
     * -- that.addBuddy -- void
     * Fields of Parameters
     * -- that.username -- string
     * -- that.buddies -- ILoBuddy
     */
    return this.hasDirectBuddy(that) || this.buddies.hasThatBuddy(that);
  }

  // Adds the buddy to buddies
  /*
   * TEMPLATE
   * Parameters
   * -- buddy -- Person
   * Methods on Parameter
   * -- buddy.hasDirectBuddy -- boolean
   * -- buddy.partyCount -- int
   * -- buddy.countCommonBuddies == int
   * -- buddy.hasExtendedBuddy -- boolean
   * -- buddy.addBuddy -- void
   * Fields of Parameters
   * -- buddy.username -- string
   * -- buddy.buddies -- ILoBuddy
   */
  void addBuddy(Person buddy) {
    this.buddies = new ConsLoBuddy(buddy, this.buddies);
  }
}
