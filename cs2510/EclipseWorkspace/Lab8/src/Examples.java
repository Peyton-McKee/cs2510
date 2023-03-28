import tester.*;

// Bank Account Examples and Tests
class Examples {
  Account check1;
  Account savings1;
  Account credit1;
  Account check2;
  Account savings2;
  Account credit2;
  Account check3;
  Account savings3;
  Account credit3;
  Account check4;
  Account savings4;
  Account credit4;
  Account check5;
  Account savings5;
  Account credit5;
  Bank bank1;
  Bank bank2;

  Examples() { 
    reset(); 
  }

  // Initializes accounts to use for testing with effects.
  // We place inside reset() so we can "reuse" the accounts
  void reset() {

    // Initialize the account examples
    this.check1 = new Checking(1, 100, "First Checking Account", 50);
    this.savings1 = new Savings(2, 200, "First Savings Account", 2.5);
    this.credit1 = new Credit(3, 200, "First credit Account", 250, 18);
    this.check2 = new Checking(4, 1000, "Second Checking Account", 100);
    this.savings2 = new Savings(5, 500, "Second Savings Account", 2.5);
    this.credit2 = new Credit(6, 600, "Second credit Account", 1000, 20);
    this.check3 = new Checking(7, 400, "Third Checking Account", 20);
    this.savings3 = new Savings(8, 300, "Third Savings Account", 1);
    this.credit3 = new Credit(9, 420, "Third credit Account", 500, 17);
    this.check4 = new Checking(10, 0, "Fourth Checking Account", 20);
    this.savings4= new Savings(11, 8000, "Fourth Savings Account", 3.2);
    this.credit4 = new Credit(12, 260, "Fourth credit Account", 300, 14);
    this.check5 = new Checking(13, 420, "Fourth Checking Account", 80);
    this.savings5 = new Savings(14, 690, "Fourth Savings Account", 2.5);
    this.credit5 = new Credit(15, 2, "Fourth credit Account", 7, 15);
    
    this.bank1 = new Bank("Chase");
    this.bank2 = new Bank("Wells Fargo");
    
    this.bank1.add(this.check1);
    this.bank1.add(this.savings2);
    this.bank1.add(this.credit5);
    this.bank1.add(this.check3);
    this.bank1.add(this.savings4);
    this.bank1.add(this.credit2);
    this.bank1.add(this.check5);
    this.bank1.add(this.savings1);
    this.bank1.add(this.credit4);

    this.bank2.add(this.check2);
    this.bank2.add(this.check4);
    this.bank2.add(this.savings5);
    this.bank2.add(this.credit1);
    this.bank2.add(this.savings3);
    this.bank2.add(this.credit5);
  }

  // Tests the exceptions we expect to be thrown when
  //   performing an "illegal" action.
  void testExceptions(Tester t) {
    reset();
    t.checkException("Test for invalid Checking withdraw",
        new IllegalArgumentException("Minimum Balance exceeded"),
        this.check1,
        "withdraw",
        1000);
    t.checkException("Test for invalid savings withdraw", new IllegalArgumentException("Not enough funds"), this.savings1, "withdraw", 300);
    t.checkException("Test for invalid credit withdraw", new IllegalArgumentException("Withdraw exceeds credit line"), this.credit3, "withdraw", 100);
    t.checkException("Test for invalid credit deposit", new IllegalArgumentException("Deposit exceeds amount owed"), this.credit1, "deposit", 300);
    t.checkException("Test for invalid acccount number when depositing", new IllegalArgumentException("No account exists with number: 20"), this.bank1, "deposit", 20, 100);
    t.checkException("Test for invalid acccount number when withdrawing", new IllegalArgumentException("No account exists with number: 20"), this.bank2, "withdraw", 20, 20);
    t.checkException("Test for invalid acccount number when removing acct", new IllegalArgumentException("No account exists with number: 20"), this.bank1, "removeAccount", 20);
  }
  
  // Tests the add method
  void testAdd(Tester t) {
    this.reset();
    this.bank1.add(this.credit5);
    t.checkExpect(this.bank1.accounts.findAccountWithNumber(this.credit5.accountNum), this.credit5);
    this.reset();
    this.bank2.add(this.check1);
    t.checkExpect(this.bank1.accounts.findAccountWithNumber(this.check1.accountNum), this.check1);
  }
  
  void testRemove(Tester t) {
    
  }
}
