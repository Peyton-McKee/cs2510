
// Represents the empty List of Accounts
class MtLoAccount implements ILoAccount {
    
    MtLoAccount() {}
    
    public Account findAccountWithNumber(int number) {
      throw new IllegalArgumentException("No account exists with number: " + number);
    }
    
    public ILoAccount removeAccountWithNumber(int number) {
      throw new IllegalArgumentException("No account exists with number: " + number);
    }
}

