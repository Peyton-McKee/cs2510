
// Represents a checking account
class Checking extends Account {

  int minimum; // The minimum account balance allowed

  Checking(int accountNum, int balance, String name, int minimum) {
    super(accountNum, balance, name);
    this.minimum = minimum;
  }
  
  int withdraw(int amount) {
    if (this.balance - amount < this.minimum) {
      throw new IllegalArgumentException("Minimum Balance exceeded");
    }
    this.balance -= amount;
    return this.balance;
  }
  
}
