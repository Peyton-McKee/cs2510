import tester.Tester;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

// contains the abstract classes
/*
* TEMPLATE:
* Fields:
* ... this.order ... -- Comparator<T>
* Methods:
* ... this.insert(T item) ... -- ABST<T>
* ... this.present(T item) ... -- boolean
* ... this.getLeftMost() ... -- T
* ... this.checkLeft(Node<T> that) ... -- T
* ... this.sameTree(ABST<T> tree) ... -- boolean
* ... this.sameData(ABST<T> tree) ... -- boolean
* ... this.hasData(ABST<T> tree) ... -- boolean
* ... this.exactData(Node<T> that) ... -- boolean
* ... this.buildList() ... -- IList<T>
* ... this.getRight() ... -- ABST<T>
* Methods on fields:
* ... order.compare(<T> t1, <T> t2) ... -- int
*/
abstract class ABST<T> {
  Comparator<T> order;

  // Inserts the item into the correct location within the tree
  ABST<T> insert(T item) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... item ... -- T
     * Fields of Parameters:
     * Methods on Parameters:
     */
    return new Node<T>(this.order, item, new Leaf<T>(this.order), new Leaf<T>(this.order));
  }

  // Determines if the item is present within the tree
  boolean present(T item) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... item ... -- T
     * Fields of Parameters:
     * Methods on Parameters:
     */
    return false;
  }

  // gets the leftmost item within the tree
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // returns the data from the left side
  T checkLeft(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return that.data;
  }

  // checks if the two trees are identical
  boolean sameTree(ABST<T> tree) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... tree ... -- ABST<T>
     * Fields of Parameters:
     * ... tree.data ... -- T
     * ... tree.left ... -- ABST<T>
     * ... tree.right ... -- ABST<T>
     * Methods on Parameters:
     * ... tree.insert(T item) ... -- ABST<T>
     * ... tree.present(T item) ... -- boolean
     * ... tree.getLeftMost() ... -- T
     * ... tree.checkLeft(Node<T> that) ... -- T
     * ... tree.sameTree(ABST<T> tree) ... -- boolean
     * ... tree.sameData(ABST<T> tree) ... -- boolean
     * ... tree.hasData(ABST<T> tree) ... -- boolean
     * ... tree.exactData(Node<T> that) ... -- boolean
     * ... tree.buildList() ... -- IList<T>
     * ... tree.getRight() ... -- ABST<T>
     */
    return tree.equals(this);
  }

  // determines if the trees contain the exact same data
  boolean sameData(ABST<T> tree) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... tree ... -- ABST<T>
     * Fields of Parameters:
     * ... tree.data ... -- T
     * ... tree.left ... -- ABST<T>
     * ... tree.right ... -- ABST<T>
     * Methods on Parameters:
     * ... tree.insert(T item) ... -- ABST<T>
     * ... tree.present(T item) ... -- boolean
     * ... tree.getLeftMost() ... -- T
     * ... tree.checkLeft(Node<T> that) ... -- T
     * ... tree.sameTree(ABST<T> tree) ... -- boolean
     * ... tree.sameData(ABST<T> tree) ... -- boolean
     * ... tree.hasData(ABST<T> tree) ... -- boolean
     * ... tree.exactData(Node<T> that) ... -- boolean
     * ... tree.buildList() ... -- IList<T>
     * ... tree.getRight() ... -- ABST<T>
     */
    return this.equals(tree);
  }

  // determines if the trees has the same data
  boolean hasData(ABST<T> tree) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... tree ... -- ABST<T>
     * Fields of Parameters:
     * ... tree.data ... -- T
     * ... tree.left ... -- ABST<T>
     * ... tree.right ... -- ABST<T>
     * Methods on Parameters:
     * ... tree.insert(T item) ... -- ABST<T>
     * ... tree.present(T item) ... -- boolean
     * ... tree.getLeftMost() ... -- T
     * ... tree.checkLeft(Node<T> that) ... -- T
     * ... tree.sameTree(ABST<T> tree) ... -- boolean
     * ... tree.sameData(ABST<T> tree) ... -- boolean
     * ... tree.hasData(ABST<T> tree) ... -- boolean
     * ... tree.exactData(Node<T> that) ... -- boolean
     * ... tree.buildList() ... -- IList<T>
     * ... tree.getRight() ... -- ABST<T>
     */
    return true;
  }

  // determines if this node and that node contain the same data
  boolean exactData(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return false;
  }

  // builds a list from the tree
  IList<T> buildList() {
    return new MtList<T>();
  }

  // gets the right of the tree without the leftmost item
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // removes the left most item
  ABST<T> removeLeftMost(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return that.right;
  }
}

//Will compare the books by title
class BooksByTitle implements Comparator<Book> {
  /*
   * TEMPLATE:
   * Fields:
   * Methods:
   * ... this.compare(Book b1, Book b2) ... -- int
   */
  // Will return a positive value if the first book comes
  // first in alphabetical order, a negative value if b2 comes first
  // and 0 if the books are the same
  @Override
  public int compare(Book b1, Book b2) {
    /*
     * TEMPLATE:
     * Parameters:
     * b1 -- Book
     * b2 -- Book
     * Fields of Parameters:
     * b1.title -- String
     * b1.author -- String
     * b1.price -- int
     * b2.title -- String
     * b2.author -- String
     * b2.price -- int
     * Methods of Parameters:
     * b1.authorLower() -- String
     * b1.titleLower() -- String
     * b2.authorLower() -- String
     * b2.titleLower() -- String
     */
    return b1.titleLower().compareTo(b2.titleLower());
  }
}

// Will compare the books by Author
class BooksByAuthor implements Comparator<Book> {
  /*
   * TEMPLATE:
   * Fields:
   * Methods:
   * ... this.compare(Book b1, Book b2) ... -- int
   */
  // Will return a positive value if the first book comes
  // first in alphabetical order, a negative value if b2 comes first
  // and 0 if the books are the same
  @Override
  public int compare(Book b1, Book b2) {
    /*
     * TEMPLATE:
     * Parameters:
     * b1 -- Book
     * b2 -- Book
     * Fields of Parameters:
     * b1.title -- String
     * b1.author -- String
     * b1.price -- int
     * b2.title -- String
     * b2.author -- String
     * b2.price -- int
     * Methods of Parameters:
     * b1.authorLower() -- String
     * b1.titleLower() -- String
     * b2.authorLower() -- String
     * b2.titleLower() -- String
     */
    return b1.authorLower().compareTo(b2.authorLower());
  }
}

// WIll compare the books by price
class BooksByPrice implements Comparator<Book> {
  /*
   * TEMPLATE:
   * Fields:
   * Methods:
   * ... this.compare(Book b1, Book b2) ... -- int
   */
  // Will return a positive value if the first book is more expensive
  // will return a negative value if the second book is more expensive
  // will return 0 if the prices are the same
  @Override
  public int compare(Book b1, Book b2) {
    /*
     * TEMPLATE:
     * Parameters:
     * b1 -- Book
     * b2 -- Book
     * Fields of Parameters:
     * b1.title -- String
     * b1.author -- String
     * b1.price -- int
     * b2.title -- String
     * b2.author -- String
     * b2.price -- int
     * Methods of parameters:
     * b1.authorLower() -- String
     * b1.titleLower() -- String
     * b2.authorLower() -- String
     * b2.titleLower() -- String
     */
    if (b1.price > b2.price) {
      return 1;
    }
    else if (b2.price > b1.price) {
      return -1;
    }
    else {
      return 0;
    }
  }
}

// represents a leaf in a tree of values
class Leaf<T> extends ABST<T> {
  // Constructor
  Leaf(Comparator<T> order) {
    super.order = order;
  }
}

// represents a node in a tree of values
class Node<T> extends ABST<T> {
  /*
   * TEMPLATE:
   * Fields:
   * ... this.data ... -- <T>
   * ... this.left ... -- ABST
   * ... this.right ... -- ABST
   * Methods:
   * Same as ABST<T> class
   * Methods on Fields:
   * ... left.insert(T item) ... -- ABST<T>
   * ... left.present(T item) ... -- boolean
   * ... left.getLeftMost() ... -- T
   * ... left.checkLeft(Node<T> that) ... -- T
   * ... left.sameTree(ABST<T> tree) ... -- boolean
   * ... left.sameData(ABST<T> tree) ... -- boolean
   * ... left.hasData(ABST<T> tree) ... -- boolean
   * ... left.exactData(Node<T> that) ... -- boolean
   * ... left.buildList() ... -- IList<T>
   * ... left.getRight() ... -- ABST<T>
   * ... right.insert(T item) ... -- ABST<T>
   * ... right.present(T item) ... -- boolean
   * ... right.getLeftMost() ... -- T
   * ... right.checkLeft(Node<T> that) ... -- T
   * ... right.sameTree(ABST<T> tree) ... -- boolean
   * ... right.sameData(ABST<T> tree) ... -- boolean
   * ... right.hasData(ABST<T> tree) ... -- boolean
   * ... right.exactData(Node<T> that) ... -- boolean
   * ... right.buildList() ... -- IList<T>
   * ... right.getRight() ... -- ABST<T>
   */
  T data;
  ABST<T> left;
  ABST<T> right;

  // Constructor
  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super.order = order;
    this.data = data;
    this.left = left;
    this.right = right;
  }

  @Override
  // Inserts the item into the correct location within the tree
  public ABST<T> insert(T item) {
    if (super.order.compare(this.data, item) < 0) {
      return new Node<T>(super.order, this.data, this.left, this.right.insert(item));
    }
    else if (super.order.compare(this.data, item) > 0) {
      return new Node<T>(super.order, this.data, this.left.insert(item), this.right);
    }
    return new Node<T>(super.order, this.data, this.left,
        new Node<T>(super.order, item, new Leaf<T>(super.order), this.right));
  }

  @Override
  // Determines if the item is present within the tree
  public boolean present(T item) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... item ... -- T
     * Fields of Parameters:
     * Methods on Parameters:
     */
    if (super.order.compare(this.data, item) < 0) {
      return this.right.present(item);
    }
    else if (super.order.compare(this.data, item) > 0) {
      return this.left.present(item);
    }
    return true;
  }

  @Override
  // gets the leftmost item within the tree
  public T getLeftmost() {
    return this.left.checkLeft(this);
  }

  @Override
  // returns the data from the left side
  public T checkLeft(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return this.left.checkLeft(this);
  }

  @Override
  // checks if the two trees are identical
  public boolean sameTree(ABST<T> tree) {
    return tree.exactData(this);
  }

  @Override
  // determines if this node and that node contain the same data
  public boolean exactData(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return this.data.equals(that.data) && that.left.sameTree(this.left)
        && that.right.sameTree(this.right);
  }

  @Override
  // determines if the trees has the same data
  public boolean hasData(ABST<T> tree) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... tree ... -- ABST<T>
     * Fields of Parameters:
     * ... tree.data ... -- T
     * ... tree.left ... -- ABST<T>
     * ... tree.right ... -- ABST<T>
     * Methods on Parameters:
     * ... tree.insert(T item) ... -- ABST<T>
     * ... tree.present(T item) ... -- boolean
     * ... tree.getLeftMost() ... -- T
     * ... tree.checkLeft(Node<T> that) ... -- T
     * ... tree.sameTree(ABST<T> tree) ... -- boolean
     * ... tree.sameData(ABST<T> tree) ... -- boolean
     * ... tree.hasData(ABST<T> tree) ... -- boolean
     * ... tree.exactData(Node<T> that) ... -- boolean
     * ... tree.buildList() ... -- IList<T>
     * ... tree.getRight() ... -- ABST<T>
     */
    return tree.present(this.data) && this.left.hasData(tree) && this.right.hasData(tree);
  }

  @Override
  // determines if the trees contain the exact same data
  public boolean sameData(ABST<T> tree) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... tree ... -- ABST<T>
     * Fields of Parameters:
     * ... tree.data ... -- T
     * ... tree.left ... -- ABST<T>
     * ... tree.right ... -- ABST<T>
     * Methods on Parameters:
     * ... tree.insert(T item) ... -- ABST<T>
     * ... tree.present(T item) ... -- boolean
     * ... tree.getLeftMost() ... -- T
     * ... tree.checkLeft(Node<T> that) ... -- T
     * ... tree.sameTree(ABST<T> tree) ... -- boolean
     * ... tree.sameData(ABST<T> tree) ... -- boolean
     * ... tree.hasData(ABST<T> tree) ... -- boolean
     * ... tree.exactData(Node<T> that) ... -- boolean
     * ... tree.buildList() ... -- IList<T>
     * ... tree.getRight() ... -- ABST<T>
     */
    return this.hasData(tree) && tree.hasData(this);
  }

  // builds a list from the tree
  IList<T> buildList() {
    return new ConsList<T>(this.getLeftmost(), this.getRight().buildList());
  }

  // gets the right of the tree without the leftmost item
  ABST<T> getRight() {
    return this.left.removeLeftMost(this);
  }

  @Override
  // removes the left most node
  ABST<T> removeLeftMost(Node<T> that) {
    /*
     * TEMPLATE:
     * Parameters:
     * ... that ... -- Node<T>
     * Fields of Parameters:
     * ... that.data ... -- T
     * ... that.left ... -- ABST<T>
     * ... that.right ... -- ABST<T>
     * Methods on Parameters:
     * ... that.insert(T item) ... -- ABST<T>
     * ... that.present(T item) ... -- boolean
     * ... that.getLeftMost() ... -- T
     * ... that.checkLeft(Node<T> that) ... -- T
     * ... that.sameTree(ABST<T> tree) ... -- boolean
     * ... that.sameData(ABST<T> tree) ... -- boolean
     * ... that.hasData(ABST<T> tree) ... -- boolean
     * ... that.exactData(Node<T> that) ... -- boolean
     * ... that.buildList() ... -- IList<T>
     * ... that.getRight() ... -- ABST<T>
     */
    return new Node<T>(super.order, that.data, this.left.removeLeftMost(this), that.right);
  }
}

// To represent a book
class Book {
  /*
   * TEMPLATE:
   * Fields:
   * ... this.title ... -- String
   * ... this.author ... -- String
   * ... this.price ... -- int
   * Methods:
   * ... this.titleLower() ... -- String
   * ... this.authorLower() ... -- String
   */
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }

  // Makes the title lower case
  public String titleLower() {
    return this.title.toLowerCase();
  }

  // Makes the author lower case
  public String authorLower() {
    return this.author.toLowerCase();
  }
}

//Represents a list of any
interface IList<T> {
}

//Represents the empty list
class MtList<T> implements IList<T> {
  MtList() {
  }
}

//Represents the list that contains items
class ConsList<T> implements IList<T> {
  /*
   * TEMPLATE:
   * FIELDS:
   * this.first -- T
   * this.rest -- IList<T>
   */
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
}

// Representing the examples
class ExamplesBST {
  Book book1 = new Book("Harry P", "JK R", 10);
  Book book2 = new Book("back G", "Aflred Joe", 8);
  Book book3 = new Book("quag F", "Joey Diaz", 25);
  Book book4 = new Book("fet S", "Jo Jo", 100);
  Book book5 = new Book("Symphony", "Jared Jenkins", 53);
  Book book6 = new Book("Sally jenkins", "harriet fischer", 27);
  Book book7 = new Book("Cory", "Coke", 14);
  Book book8 = new Book("leman", "robinhood", 15);
  Book book9 = new Book("Goo goo", "ga ga", 1);
  ABST<Book> titleSort = new Node<Book>(new BooksByTitle(), this.book1,
      new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()));
  ABST<Book> titleSort2 = new Node<Book>(new BooksByTitle(), this.book1,
      new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()));
  ABST<Book> authorSort = new Node<Book>(new BooksByAuthor(), this.book1,
      new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()));
  ABST<Book> priceSort = new Node<Book>(new BooksByPrice(), this.book1,
      new Leaf<Book>(new BooksByPrice()), new Leaf<Book>(new BooksByPrice()));
  ABST<Book> emptyBook = new Leaf<Book>(new BooksByTitle());

  void reset() {
    this.titleSort = new Node<Book>(new BooksByTitle(), this.book1,
        new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()));
    this.titleSort = this.titleSort.insert(this.book2);
    this.titleSort = this.titleSort.insert(this.book3);
    this.titleSort = this.titleSort.insert(this.book4);

    this.titleSort2 = new Node<Book>(new BooksByTitle(), this.book2,
        new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()));
    this.titleSort2 = this.titleSort2.insert(this.book4);
    this.titleSort2 = this.titleSort2.insert(this.book2);
    this.titleSort2 = this.titleSort2.insert(this.book3);

    this.authorSort = new Node<Book>(new BooksByAuthor(), this.book1,
        new Leaf<Book>(new BooksByAuthor()), new Leaf<Book>(new BooksByAuthor()));
    this.authorSort = this.authorSort.insert(this.book5);
    this.authorSort = this.authorSort.insert(this.book6);
    this.authorSort = this.authorSort.insert(this.book7);
    this.priceSort = new Node<Book>(new BooksByPrice(), this.book1,
        new Leaf<Book>(new BooksByPrice()), new Leaf<Book>(new BooksByPrice()));
    this.priceSort = this.priceSort.insert(this.book7);
    this.priceSort = this.priceSort.insert(this.book8);
    this.priceSort = this.priceSort.insert(this.book9);
  }

  // Constructor
  ExamplesBST() {
    this.reset();
  }

  // Tests the insert method
  boolean testInsert(Tester t) {
    this.reset();
    return t
        .checkExpect(this.titleSort.insert(this.book9), new Node<Book>(new BooksByTitle(),
            this.book1,
            new Node<Book>(new BooksByTitle(), this.book2, new Leaf<Book>(new BooksByTitle()),
                new Node<Book>(new BooksByTitle(), this.book4, new Leaf<Book>(new BooksByTitle()),
                    new Node<Book>(new BooksByTitle(), this.book9,
                        new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle())))),
            new Node<Book>(new BooksByTitle(), this.book3, new Leaf<Book>(new BooksByTitle()),
                new Leaf<Book>(new BooksByTitle()))))
        && t.checkExpect(this.authorSort.insert(this.book9), new Node<Book>(new BooksByAuthor(),
            this.book1,
            new Node<Book>(new BooksByAuthor(), this.book5, new Node<Book>(new BooksByAuthor(),
                this.book6,
                new Node<Book>(new BooksByAuthor(), this.book7, new Leaf<Book>(new BooksByAuthor()),
                    new Node<Book>(new BooksByAuthor(), this.book9,
                        new Leaf<Book>(new BooksByAuthor()), new Leaf<Book>(new BooksByAuthor()))),
                new Leaf<Book>(new BooksByAuthor())), new Leaf<Book>(new BooksByAuthor())),
            new Leaf<Book>(new BooksByAuthor())))
        && t.checkExpect(this.priceSort.insert(this.book2),
            new Node<Book>(new BooksByPrice(), this.book1,
                new Node<Book>(new BooksByPrice(), this.book9, new Leaf<Book>(new BooksByPrice()),
                    new Node<Book>(new BooksByPrice(), this.book2,
                        new Leaf<Book>(new BooksByPrice()), new Leaf<Book>(new BooksByPrice()))),
                new Node<Book>(new BooksByPrice(), this.book7, new Leaf<Book>(new BooksByPrice()),
                    new Node<Book>(new BooksByPrice(), this.book8,
                        new Leaf<Book>(new BooksByPrice()), new Leaf<Book>(new BooksByPrice())))))
        && t.checkExpect(this.emptyBook.insert(this.book1), new Node<Book>(new BooksByTitle(),
            this.book1, new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle())));
  }

  // Tests the present method
  boolean testPresent(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.present(this.book1), true)
        && t.checkExpect(this.titleSort.present(this.book5), false)
        && t.checkExpect(this.authorSort.present(this.book5), true)
        && t.checkExpect(this.authorSort.present(this.book9), false)
        && t.checkExpect(this.priceSort.present(this.book1), true)
        && t.checkExpect(this.priceSort.present(this.book2), false)
        && t.checkExpect(this.emptyBook.present(this.book5), false);
  }

  // Tests the getLeftMost method
  boolean testGetLeftMost(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.getLeftmost(), this.book2)
        && t.checkExpect(this.authorSort.getLeftmost(), this.book7)
        && t.checkExpect(this.priceSort.getLeftmost(), this.book9)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.emptyBook, "getLeftmost");
  }

  // Tests the sameTree method
  boolean testSameTree(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.sameTree(this.titleSort), true)
        && t.checkExpect(this.authorSort.sameTree(this.priceSort), false)
        && t.checkExpect(this.emptyBook.sameTree(this.titleSort), false)
        && t.checkExpect(this.titleSort2.sameTree(this.titleSort), false);
  }

  // Tests the sameData method
  boolean testSameData(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.sameData(this.titleSort), true)
        && t.checkExpect(this.authorSort.sameData(this.priceSort), false)
        && t.checkExpect(this.emptyBook.sameData(this.titleSort), false)
        && t.checkExpect(this.titleSort2.sameData(this.titleSort), true);
  }

  // Tests the hasData method
  boolean testhasData(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.hasData(this.titleSort), true)
        && t.checkExpect(this.authorSort.hasData(this.priceSort), false)
        && t.checkExpect(this.emptyBook.hasData(this.titleSort), true)
        && t.checkExpect(this.titleSort.hasData(this.emptyBook), false)
        && t.checkExpect(this.titleSort2.hasData(this.titleSort), true);
  }

  // Tests the exactData method
  boolean testExactData(Tester t) {
    this.reset();
    return t
        .checkExpect(this.titleSort.exactData(new Node<Book>(new BooksByTitle(), this.book1,
            new Node<Book>(new BooksByTitle(), this.book2, new Leaf<Book>(new BooksByTitle()),
                new Node<Book>(new BooksByTitle(), this.book4, new Leaf<Book>(new BooksByTitle()),
                    new Leaf<Book>(new BooksByTitle()))),
            new Node<Book>(new BooksByTitle(), this.book3, new Leaf<Book>(new BooksByTitle()),
                new Leaf<Book>(new BooksByTitle())))),
            true)
        && t.checkExpect(this.authorSort.exactData(new Node<Book>(new BooksByTitle(), this.book3,
            new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()))), false)
        && t.checkExpect(this.emptyBook.exactData(new Node<Book>(new BooksByTitle(), this.book3,
            new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()))), false)
        && t.checkExpect(this.titleSort2.exactData(new Node<Book>(new BooksByTitle(), this.book1,
            new Node<Book>(new BooksByTitle(), this.book2, new Leaf<Book>(new BooksByTitle()),
                new Node<Book>(new BooksByTitle(), this.book4, new Leaf<Book>(new BooksByTitle()),
                    new Leaf<Book>(new BooksByTitle()))),
            new Node<Book>(new BooksByTitle(), this.book3, new Leaf<Book>(new BooksByTitle()),
                new Leaf<Book>(new BooksByTitle())))),
            false);
  }

  // Tests the buildList method
  boolean testBuildList(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.buildList(), new ConsList<Book>(this.book2,
        new ConsList<Book>(this.book4,
            new ConsList<Book>(this.book1, new ConsList<Book>(this.book3, new MtList<Book>())))))
        && t.checkExpect(this.authorSort.buildList(),
            new ConsList<Book>(this.book7,
                new ConsList<Book>(this.book6,
                    new ConsList<Book>(this.book5,
                        new ConsList<Book>(this.book1, new MtList<Book>())))))
        && t.checkExpect(this.priceSort.buildList(),
            new ConsList<Book>(this.book9,
                new ConsList<Book>(this.book1,
                    new ConsList<Book>(this.book7,
                        new ConsList<Book>(this.book8, new MtList<Book>())))))
        && t.checkExpect(new Leaf<Book>(new BooksByPrice()).buildList(), new MtList<Book>());
  }

  // Tests the getRight method
  boolean testGetRight(Tester t) {
    this.reset();
    return t.checkExpect(this.titleSort.getRight(),
        new Node<Book>(new BooksByTitle(), this.book1,
            new Node<Book>(new BooksByTitle(), this.book4, new Leaf<Book>(new BooksByTitle()),
                new Leaf<Book>(new BooksByTitle())),
            new Node<Book>(new BooksByTitle(), this.book3, new Leaf<Book>(new BooksByTitle()),
                new Leaf<Book>(new BooksByTitle()))));
  }

  // Tests the compare method
  boolean testCompare(Tester t) {
    this.reset();
    return t.checkExpect(new BooksByTitle().compare(this.book1, this.book2) > 0, true)
        && t.checkExpect(new BooksByTitle().compare(this.book2, this.book1) < 0, true)
        && t.checkExpect(new BooksByTitle().compare(this.book1, this.book1) == 0, true)
        && t.checkExpect(new BooksByAuthor().compare(this.book4, this.book5) > 0, true)
        && t.checkExpect(new BooksByAuthor().compare(this.book5, this.book4) < 0, true)
        && t.checkExpect(new BooksByAuthor().compare(this.book4, this.book4) == 0, true)
        && t.checkExpect(new BooksByPrice().compare(this.book6, this.book7) > 0, true)
        && t.checkExpect(new BooksByPrice().compare(this.book7, this.book6) < 0, true)
        && t.checkExpect(new BooksByPrice().compare(this.book7, this.book7) == 0, true);
  }

  // Tests the checkLeft method
  boolean testCheckLeft(Tester t) {
    this.reset();
    return t
        .checkExpect(
            this.titleSort.checkLeft(new Node<Book>(new BooksByTitle(), this.book1,
                new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()))),
            this.book2)
        && t.checkExpect(
            this.emptyBook.checkLeft(new Node<Book>(new BooksByTitle(), this.book1,
                new Leaf<Book>(new BooksByTitle()), new Leaf<Book>(new BooksByTitle()))),
            this.book1);
  }
}