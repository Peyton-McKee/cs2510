
// Represents a credit line account
class Credit extends Account {

  int creditLine;  // Maximum amount accessible
  double interest; // The interest rate charged
    
  Credit(int accountNum, int balance, String name, int creditLine, double interest) {
    super(accountNum, balance, name);
    this.creditLine = creditLine;
    this.interest = interest;
  }
  
  int withdraw(int amount) {
    if (this.balance + amount >= this.creditLine) {
      throw new IllegalArgumentException("Withdraw exceeds credit line");
    }
    this.balance += amount;
    return this.balance;
  }
  
  int deposit(int amount) {
    if (amount > this.balance) {
      throw new IllegalArgumentException("Deposit exceeds amount owed");
    }
    this.balance -= amount;
    return this.balance;
  }
}
