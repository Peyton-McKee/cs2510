
interface MenuItem {}

class Soup implements MenuItem {
  String name;
  int price;
  boolean isVegetarian;
  
  Soup(String name, int price, boolean isVegetarian){
    this.name = name;
    this.price = price;
    this.isVegetarian = isVegetarian;
  }
}

class Salad implements MenuItem {
  String name;
  int price;
  boolean isVegetarian;
  String dressing;
  
  Salad(String name, int price, boolean isVegetarian, String dressing){
    this.name = name;
    this.price = price;
    this.isVegetarian = isVegetarian;
    this.dressing = dressing;
  }
}

class Sandwich implements MenuItem {
  String name;
  int price;
  String bread;
  String fillings;
  
  Sandwich(String name, int price, String bread, String fillings){
    this.name = name;
    this.price = price;
    this.bread = bread;
    this.fillings = fillings;
  }
}

class ExamplesMenuItem {
  
  MenuItem soup1 = new Soup("Chicken Noodle", 200, false);
  MenuItem soup2 = new Soup("Clam Chowder", 200, false);
  MenuItem salad1 = new Salad("Caeser", 200, true, "Caeser");
  MenuItem salad2 = new Salad("Antipasti", 200, false, "Red Wine Vinagrette");
  MenuItem sandwich1 = new Sandwich("Ham and Cheese", 200, "White", "Ham and Cheese");
  MenuItem sandwich2 = new Sandwich("Peanut Butter and Jelly", 200, "White", "Peanut Butter and Jelly");
  

}
    
