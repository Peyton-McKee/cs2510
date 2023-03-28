
// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {
  /*
   * TEMPLATE:
   * Fields:
   * ... this.first ... Person
   * ... this.rest ... ILoBuddy
   * Methods:
   * ... this.contains(Person name) ... -- boolean
   * ... this.sameDirectBuddy(ILoBuddy buddies) ... -- int
   * ... this.hasThatBuddy(Person that) ... -- boolean
   * ... this.length() ... -- int
   * ... this.buddiesBuddyLength() ... -- int
   * Methods of fields:
   * ... first.hasDirectBuddy(Person that) ... -- boolean
   * ... first.partyCount() ... -- int
   * ... first.countCommonBuddies(Person that) ... -- int
   * ... first.hasExtendedBuddy(Person that) ... -- boolean
   * ... first.addBuddy(Person that) ... void
   * ... first.countOfThisBuddies() ... int
   * ... rest.contains(Person name) ... -- boolean
   * ... rest.sameDirectBuddy(ILoBuddy buddies) ... -- int
   * ... rest.hasThatBuddy(Person that) ... -- boolean
   * ... rest.length() ... -- int
   * ... rest.buddiesBuddyLength() ... -- int
   */
  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }

  // returns if the name given is contained in the list
  public boolean contains(Person name) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- name -- Person
     * Methods on Parameter:
     * -- name.hasDirectBuddy -- boolean
     * -- name.partyCount -- int
     * -- name.countCommonBuddies == int
     * -- name.hasExtendedBuddy -- boolean
     * -- name.addBuddy -- void
     * Fields of Parameters:
     * -- name.username -- string
     * -- name.buddies -- ILoBuddy
     */
    return this.first.equals(name) || this.rest.contains(name);
  }

  // returns the value of the amount of people that appear
  // in both lists
  public int sameDirectBuddy(ILoBuddy buddies) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- buddies -- ILoBuddy
     * Methods on Parameter:
     * -- buddies.contains(Person name) -- boolean
     * -- buddies.sameDirectBuddy(ILoBuddy buddies) -- int
     * -- buddies.hasThatBuddy(Person that) -- boolean
     * Fields of Parameter:
     */
    if (buddies.contains(this.first)) {
      return 1 + this.rest.sameDirectBuddy(buddies);
    }
    else {
      return this.rest.sameDirectBuddy(buddies);
    }
  }

  // returns if anyone on the list has a buddy that equals the one given
  public boolean hasThatBuddy(Person that) {
    /*
     * TEMPLATE:
     * Parameters:
     * -- that -- Person
     * Methods on Parameter:
     * -- that.hasDirectBuddy -- boolean
     * -- that.partyCount -- int
     * -- that.countCommonBuddies == int
     * -- that.hasExtendedBuddy -- boolean
     * -- that.addBuddy -- void
     * Fields of Parameters:
     * -- that.username -- string
     * -- that.buddies -- ILoBuddy
     */
    return this.first.hasDirectBuddy(that) || this.rest.hasThatBuddy(that);
  }

  // returns the length of the list
  public int length() {
    return 1 + this.rest.length();
  }
  
  public ILoBuddy addBuddiesToParty(ILoBuddy partyInvites) {
    return this.rest.addBuddiesToParty(this.first.addToParty(partyInvites));
  }

}