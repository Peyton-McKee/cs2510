
class RealEstateListing {
  boolean singleFamily;
  int year;
  int squareFootage;
  int price;
  String city;
  
  RealEstateListing(boolean singleFamily, int year, int squareFootage, int price, String city) {
    this.singleFamily = singleFamily;
    this.year = year;
    this.squareFootage = squareFootage;
    this.price = price;
    this.city = city;
  }
  
}

class ExamplesListings {
  public ExamplesListings() {} 
  
  RealEstateListing bostonCondo = new RealEstateListing(false, 2010, 700, 350000, "Boston");
  RealEstateListing beachHouse = new RealEstateListing(true, 1995, 2000, 699999, "Yarmouth");
  RealEstateListing otherHouse = new RealEstateListing(false, 1965, 4000, 1000000, "Sim City");
}