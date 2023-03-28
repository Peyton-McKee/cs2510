
// Represents a bank account
abstract class Account {

  int accountNum;  // Must be unique
  int balance;     // Must remain above zero (others Accts have more restrictions)
  String name;     // Name on the account

  Account(int accountNum, int balance, String name){
    this.accountNum = accountNum;
    this.balance = balance;
    this.name = name;
  }
  
  abstract int withdraw(int amount);
  
  int deposit (int amount) {
    this.balance += amount;
    return this.balance;
  }
  
  boolean sameAcctNumber(int num) {
    return this.accountNum == num;
  }
}
