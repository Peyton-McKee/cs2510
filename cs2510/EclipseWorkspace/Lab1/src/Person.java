

class Address {
  String city;
  String state;
  
  Address(String city, String state) {
    this.city = city;
    this.state = state;
  }
}
class Person {
    String name;
    int age;
    String gender;
    Address address;
    
    Person(String name, int age, String gender, Address address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }
}
class ExamplesPerson {
  Person tim = new Person("Time", 23, "Male", new Address("Boston", "MA"));
  Person kate = new Person("Kate", 22, "Female", new Address("Warwick", "RI"));
  Person rebecca = new Person("Rebecca", 31, "Non-binary", new Address("Nashua", "NH"));
  Person john = new Person("John", 31, "Non-binary", new Address("Amesbury", "MA"));
  Person stuart = new Person("Stuart", 10, "Female", new Address("Newburyport", "MA"));

}