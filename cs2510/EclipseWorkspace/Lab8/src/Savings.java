
// Represents a savings account
class Savings extends Account {

  double interest; // The interest rate

  Savings(int accountNum, int balance, String name, double interest) {
    super(accountNum, balance, name);
    this.interest = interest;
  }
 
  int withdraw(int amount) {
    if (amount > this.balance) {
      throw new IllegalArgumentException("Not enough funds");
    }
    this.balance -= amount;
    return this.balance;
  }
}
