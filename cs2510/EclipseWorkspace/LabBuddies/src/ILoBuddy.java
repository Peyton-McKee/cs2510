
// represents a list of Person's buddies
interface ILoBuddy {
  // returns if the name given is contained in the list
  boolean contains(Person name);

  // returns the value of the amount of people that appear
  // in both lists
  int sameDirectBuddy(ILoBuddy buddies);

  // returns if anyone on the list has a buddy that equals the one given
  boolean hasThatBuddy(Person that);

  // returns the length of the list
  int length();
  
  //adds buddies to party
  ILoBuddy addBuddiesToParty(ILoBuddy partyInvites);
}