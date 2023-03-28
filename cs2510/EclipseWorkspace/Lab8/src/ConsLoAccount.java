
// Represents a non-empty List of Accounts...
class ConsLoAccount implements ILoAccount {

  Account first;
  ILoAccount rest;

  ConsLoAccount(Account first, ILoAccount rest) {
    this.first = first;
    this.rest = rest;
  }
    
  public Account findAccountWithNumber(int number) {
    if (this.first.sameAcctNumber(number)) {
      return this.first;
    }
    return this.rest.findAccountWithNumber(number);
  }
  
  public ILoAccount removeAccountWithNumber(int number) {
    if (this.first.sameAcctNumber(number)) {
      return this.rest;
    }
    return new ConsLoAccount(this.first, this.rest.removeAccountWithNumber(number));
  }
}