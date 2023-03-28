import tester.Tester;
import java.awt.Color;

class Ball {
  CartPt center;
  int radius;
  Color color;

  Ball(CartPt center, int radius, Color color) {
    this.center = center;
    this.radius = radius;
    this.color = color;
  }
  // Template
  /*
   * ... this.center ... -- CarrPt ... this.radius ... -- int ... this.color ...
   * -- Color
   */

  // Returns the area of this ball
  double area() {
    return Math.PI * Math.pow(this.radius, 2);
  }

  // Returns the circumference of this ball
  double circumference() {
    return Math.PI * 2 * this.radius;
  }

  // Returns the distance from the center of this ball to the given ball
  double distanceTo(Ball ball) {
    return Math.sqrt(
        Math.pow(ball.center.x - this.center.x, 2) + Math.pow(ball.center.y - this.center.y, 2));
  }

  // Returns whether or not this ball and the given ball overlap
  boolean overlaps(Ball ball) {
    return distanceTo(ball) - ball.radius < this.radius;
  }
}

class CartPt {
  int x;
  int y;

  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Template
  /*
   * ... this.x ... -- int ... this.y ... -- int
   */

}

class ExamplesBall {
  Ball b = new Ball(new CartPt(0, 0), 5, Color.BLUE);
  Ball b2 = new Ball(new CartPt(1, 1), 2, Color.BLACK);

  boolean testBall(Tester t) {
    return t.checkInexact(b.area(), 78.5, 0.001) && t.checkInexact(b.circumference(), 31.42, 0.001)
        && t.checkInexact(b.distanceTo(b2), Math.sqrt(2), 0.001)
        && t.checkExpect(b.overlaps(b2), true);
  }
}