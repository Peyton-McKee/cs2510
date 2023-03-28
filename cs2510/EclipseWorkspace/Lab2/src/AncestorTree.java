interface IAncestorTree {}

class Tree implements IAncestorTree {
  Person person;
  IAncestorTree ancestor1;
  IAncestorTree ancestor2;
  
  Tree(Person person, IAncestorTree ancestor1, IAncestorTree ancestor2) {
    this.person = person;
    this.ancestor1 = ancestor1;
    this.ancestor2 = ancestor2;
  } 
}

class Unknown implements IAncestorTree {
  Unknown() {}
}

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