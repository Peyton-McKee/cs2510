
// Represents a Bank with list of accounts
class Bank {
    
  String name;
  ILoAccount accounts;
    
  Bank(String name) {
    this.name = name;

    // Each bank starts with no accounts
    this.accounts = new MtLoAccount();
  }

  void add(Account acct) {
     this.accounts = new ConsLoAccount(acct, this.accounts);
  }
  
  void deposit(int acctNumber, int amount) {
    this.accounts.findAccountWithNumber(acctNumber).deposit(amount);
  }
  
  void withdraw(int acctNumber, int amount) {
    this.accounts.findAccountWithNumber(acctNumber).withdraw(amount);
  }
  
  void removeAccount(int acctNumber) {
    this.accounts = this.accounts.removeAccountWithNumber(acctNumber);
  }
}
