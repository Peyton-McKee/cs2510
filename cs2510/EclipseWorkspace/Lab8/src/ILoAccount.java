
// Represents a List of Accounts
interface ILoAccount {
  Account findAccountWithNumber(int number);
  
  ILoAccount removeAccountWithNumber(int number);
}

