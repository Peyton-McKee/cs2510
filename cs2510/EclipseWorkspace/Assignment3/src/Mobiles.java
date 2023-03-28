import tester.Tester;
import java.awt.Color;
import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;

// This is an implementation of a Mobile art done by Alexander Calder

interface IMobile {

  // Will determine the total weight of the mobile
  int totalWeight();

  // Will determine the total height of the mobile
  int totalHeight();

  // Will determine if the mobile is balanced and has a net 0 torque
  boolean isBalanced();

  // Will make a new balanced mobile after given a balanced mobile, length of
  // string and the length of the strut
  IMobile buildMobile(IMobile balMobileTwo, int stringLength, int strutLength);

  // Will calculate the current width of the mobile
  int curWidth();

  // Will determine the width of the left side
  int leftMost();

  // Will determine the width of the right side
  int rightMost();

  // Will produce the image of this mobile if it were
  // hanging from the nail
  WorldImage drawMobile();

  boolean isSimple();

}

// This class is to represent a simple Mobile
/*
 * Template:
 * FIELDS:
 * this.length -- int
 * this.weight -- int
 * this.color -- Color
 * METHODS:
 * this.totalWeight -- int
 * this.totalHeight -- int
 * this.isBalanced -- boolean
 * this.curWidth -- int
 * this.buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) --
 * IMobile
 * this.rightMost -- int
 * this.leftMost -- int
 */
class Simple implements IMobile {
  int length;
  int weight;
  Color color;

  // Constructor
  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }

  // Will determine the total weight of the mobile
  public int totalWeight() {
    return this.weight;
  }

  // Will determine the total height of the mobile
  public int totalHeight() {
    return this.length + (int) Math.floor((this.weight / 10));
  }

  // Will determine if the mobile is balanced and has a net 0 torque
  public boolean isBalanced() {
    return true;
  }

  public boolean isSimple() {
    return true;
  }

  // Will calculate the current width of the mobile
  public int curWidth() {
    return (int) Math.ceil(this.weight / 10);
  }

  // Will make a new balanced mobile after given a balanced mobile, length of
  // string and the length of the strut
  /*
   * Template: everything in the template for IMobile, plus
   * Fields of parameters:
   * balMobileTwo.length -- int
   * balMobileTwo.weight -- int
   * balMobileTwo.color -- Color
   * balMobileTwo.leftside -- int
   * balMobileTwo.rightside -- int
   * balMobileTwo.left -- IMobile
   * balMobileTwo.right -- IMobile
   * Methods on parameters:
   */
  public IMobile buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) {
    return this;
  }

  // Will determine the width of the left side
  public int leftMost() {
    return this.curWidth() / 2;
  }

  // Will determine the width of the right side
  public int rightMost() {
    return this.curWidth() / 2;
  }

  // Will produce the image of this mobile if it were
  // hanging from the nail at some Posn
  public WorldImage drawMobile() {
    WorldImage line = new LineImage(new Posn(0, this.length * 10), Color.black);
    WorldImage square = new RectangleImage(this.curWidth() * 10, this.curWidth() * 10,
        OutlineMode.SOLID, this.color);

    WorldImage combinedImage = new AboveImage(line, square);
    return combinedImage.movePinhole(0, -combinedImage.getHeight() / 2);
  }
}

// This class is to represent a complex Mobile
/*
 * Template:
 * FIELDS:
 * this.length -- int
 * this.weight -- int
 * this.color -- Color
 * METHODS:
 * this.totalWeight -- int
 * this.totalHeight -- int
 * this.isBalanced -- boolean
 * this.curWidth -- int
 * this.buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) --
 * IMobile
 * this.rightMost -- int
 * this.leftMost -- int
 * Methods of Fields:
 * left.totalWeight -- int
 * left.totalHeight -- int
 * left.isBalanced -- boolean
 * left.curWidth -- int
 * left.buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) --
 * IMobile
 * left.leftMost -- int
 * left.rightMost -- int
 * right.totalWeight -- int
 * right.totalHeight -- int
 * right.isBalanced -- boolean
 * right.curWidth -- int
 * right.buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) --
 * IMobile
 * right.leftMost -- int
 * right.rightMost -- int
 */

class Complex implements IMobile {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;

  // Constructor
  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }

  // Will determine the total weight of the mobile
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }

  public boolean isSimple() {
    return false;
  }

  // Will determine the total height of the mobile
  public int totalHeight() {
    return Math.max(this.left.totalHeight(), this.right.totalHeight()) + this.length;
  }

  // Will determine if the mobile is balanced and has a net 0 torque
  public boolean isBalanced() {
    return (this.rightside * this.right.totalWeight() == this.leftside * this.left.totalWeight())
        && this.right.isBalanced() && this.left.isBalanced();
  }

  // Will make a new balanced mobile after given a balanced mobile, length of
  // string and the length of the strut
  /*
   * Template: everything in the template for IMobile, plus
   * Fields of parameters:
   * balMobileTwo.length -- int
   * balMobileTwo.weight -- int
   * balMobileTwo.color -- Color
   * balMobileTwo.leftside -- int
   * balMobileTwo.rightside -- int
   * balMobileTwo.left -- IMobile
   * balMobileTwo.right -- IMobile
   * Methods on parameters:
   * balMobileTwo.totalWeight() -- int
   */
  public IMobile buildMobile(IMobile balMobileTwo, int stringLength, int strutLength) {
    if (!this.isBalanced()) {
      return this;
    }
    else {
      int mobileOneWeight = this.totalWeight();
      int mobileTwoWeight = balMobileTwo.totalWeight();
      int rightSide = (mobileOneWeight * strutLength) / (mobileOneWeight + mobileTwoWeight);
      int leftSide = strutLength - rightSide;
      return new Complex(stringLength, leftSide, rightSide, this, balMobileTwo);
    }
  }

  public int leftMost() {
    return Math.max(this.leftside + this.left.leftMost(),
        this.right.leftMost() - this.rightside - 1);
  }

  // Will determine the width of the right side
  public int rightMost() {
    return Math.max(this.rightside + this.right.rightMost(),
        this.left.rightMost() - this.leftside - 1);
  }

  // Will calculate the current width of the mobile
  public int curWidth() {
    return this.rightMost() + this.leftMost() + 1;
  }

  // Will produce the image of this mobile if it were
  // hanging from the nail at some Posn
  public WorldImage drawMobile() {
    WorldImage lineDown = new LineImage(new Posn(0, this.length * 10), Color.black).movePinhole(0,
        this.length * 5);
    WorldImage lineRight = new LineImage(new Posn(this.rightside * 10, 0), Color.black)
        .movePinhole(this.rightside * 5, 0);
    WorldImage lineLeft = new LineImage(new Posn(-this.leftside * 10, 0), Color.black)
        .movePinhole(-this.leftside * 5, 0);

    WorldImage leftSide = this.left.drawMobile();
    WorldImage rightSide = this.right.drawMobile();

    WorldImage leftSideCombined = new OverlayImage(lineLeft, leftSide)
        .movePinhole(lineLeft.getWidth(), 0);

    WorldImage rightSideCombined = new OverlayImage(lineRight, rightSide)
        .movePinhole(-lineRight.getWidth(), 0);

    WorldImage rightLeftCombined = new OverlayImage(rightSideCombined, leftSideCombined);

    WorldImage totalCombined = new OverlayImage(rightLeftCombined, lineDown).movePinhole(0,
        -lineDown.getHeight());

    return totalCombined;
    // new OverlayOffsetImage(leftSide, this.leftside * 5, this.length * 10, lines);
  }

}

class ExamplesMobiles {
  // Constructor
  ExamplesMobiles() {
  }

  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);

  IMobile exampleComplex = new Complex(1, 9, 3, new Simple(1, 36, Color.BLUE),
      new Complex(2, 8, 1, new Simple(1, 12, Color.RED),
          new Complex(2, 5, 3, new Simple(2, 36, Color.RED), new Simple(1, 60, Color.GREEN))));

  IMobile exampleMoreComplex = new Complex(1, 9, 3, new Simple(1, 36, Color.BLUE), new Complex(2, 8,
      1, new Simple(1, 12, Color.RED),
      new Complex(2, 5, 3, new Simple(2, 36, Color.RED), new Complex(2, 8, 1,
          new Complex(2, 8, 1, exampleSimple, new Simple(5, 20, Color.RED)),
          new Complex(3, 12, 6, new Simple(2, 55, Color.RED), new Simple(4, 12, Color.YELLOW))))));
  IMobile exampleBalancedTwo = new Complex(1, 18, 6, new Simple(1, 72, Color.YELLOW),
      new Complex(2, 16, 2, new Simple(1, 24, Color.BLUE),
          new Complex(2, 10, 6, new Simple(2, 72, Color.GREEN), new Simple(1, 120, Color.ORANGE))));

  // To test the totalWeight method within the Simple class
  boolean testTotalWeightSimple(Tester t) {
    return t.checkExpect(this.exampleSimple.totalWeight(), 20);
  }

  // To test the totalWeight method within the Complex class
  boolean testTotalWeightComplex(Tester t) {
    return t.checkExpect(this.exampleComplex.totalWeight(), 144)
        && t.checkExpect(this.exampleMoreComplex.totalWeight(), 191);
  }

  // To test the totalHeight method within the Simple class
  boolean testTotalHeightSimple(Tester t) {
    return t.checkExpect(this.exampleSimple.totalHeight(), 4);
  }

  // To test the totalHeight method within the Complex class
  boolean testTotalHeightComplex(Tester t) {
    return t.checkExpect(this.exampleComplex.totalHeight(), 12)
        && t.checkExpect(this.exampleMoreComplex.totalHeight(), 17);
  }

  // To test the isBalanced method within the Simple class
  boolean testIsBalancedSimple(Tester t) {
    return t.checkExpect(this.exampleSimple.isBalanced(), true);
  }

  // To test the isBalanced method within the Complex class
  boolean testIsBalancedComplex(Tester t) {
    return t.checkExpect(this.exampleComplex.isBalanced(), true)
        && t.checkExpect(this.exampleMoreComplex.isBalanced(), false)
        && t.checkExpect(this.exampleBalancedTwo.isBalanced(), true);
  }

  // To test the buildMobile method within the Simple class
  boolean testBuildMobileSimple(Tester t) {
    return t.checkExpect(this.exampleSimple.buildMobile(exampleComplex, 4, 5), exampleSimple);
  }

  // To test the buildMobile method within the Complex class
  boolean testBuildMobileComplex(Tester t) {
    return t.checkExpect(this.exampleComplex.buildMobile(exampleBalancedTwo, 12, 6),
        new Complex(12, 4, 2, exampleComplex, exampleBalancedTwo));
  }

  // To test the curWidth method within the Simple class
  boolean testCurWidthSimple(Tester t) {
    return t.checkExpect(this.exampleSimple.curWidth(), 2);
  }

  // To test the curWidth method within the Complex class
  boolean testCurWidthComplex(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidth(), 21)
        && t.checkExpect(this.exampleMoreComplex.curWidth(), 25);
  }

  // To test the drawMobile method within the Simple class
  boolean testDrawMobileSimple(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(this.exampleSimple.drawMobile(), 250, 250)) && c.show();
  }

  // To test the drawMobile method within the Simple class
  boolean testDrawMobileComplex(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);
    return c.drawScene(s.placeImageXY(this.exampleComplex.drawMobile(), 250, 250)) && c.show();
  }

}