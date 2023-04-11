import tester.*;

/**
 * HtDC Lectures
 * Lecture 5: Methods for unions of classes
 * 
 * Copyright 2013 Viera K. Proulx
 * This program is distributed under the terms of the
 * GNU Lesser General Public License (LGPL)
 * 
 * @since 29 August 2013
 */

/*
 * +----------------------------+
 * | IShape |
 * +----------------------------+
 * | double area() |
 * | double distToOrigin() |
 * | IShape grow(int) |
 * | boolean biggerThan(IShape) |
 * | boolean contains(CartPt) |
 * +----------------------------+
 * |
 * / \
 * ---
 * |
 * -------------------------------------
 * | |
 * +----------------------------+ +----------------------------+
 * | Circle | | Square |
 * +----------------------------+ +----------------------------+
 * +-| CartPt center | +-| CartPt nw |
 * | | int radius | | | int size |
 * | | String color | | | String color |
 * | +----------------------------+ | +----------------------------+
 * | | double area() | | | double area() |
 * | | double distToOrigin() | | | double distToOrigin() |
 * | | IShape grow(int) | | | IShape grow(int) |
 * | | boolean biggerThan(IShape) | | | boolean biggerThan(IShape) |
 * | | boolean contains(CartPt) | | | boolean contains(CartPt) |
 * | +----------------------------+ | +----------------------------+
 * +----+ +-------------------------+
 * | |
 * v v
 * +-----------------------+
 * | CartPt |
 * +-----------------------+
 * | int x |
 * | int y |
 * +-----------------------+
 * | double distToOrigin() |
 * | double distTo(CartPt) |
 * +-----------------------+
 */

// to represent a geometric shape
interface IShape {
  // to compute the area of this shape
  double area();

  // to compute the distance form this shape to the origin
  double distToOrigin();

  // to increase the size of this shape by the given increment
  IShape grow(int inc);

  // is the area of this shape bigger than the area of the given shape?
  boolean biggerThan(IShape that);

  // does this shape (including the boundary) contain the given point?
  boolean contains(CartPt pt);

}

// to represent a circle
class Circle implements IShape {
  CartPt center;
  int radius;
  String color;

  Circle(CartPt center, int radius, String color) {
    this.center = center;
    this.radius = radius;
    this.color = color;
  }

  /*
   * // ** TEMPLATE **
   * public returnType methodName() {
   * ... this.center ... -- CartPt
   * ... this.radius ... -- int
   * ... this.color ... -- String
   * 
   * ... this.area() ... -- double
   * ... this.distToOrigin() ... -- double
   * ... this.grow(int inc) ... -- IShape
   * ... this.biggerThan(IShape that) ... -- boolean
   * ... this.contains(CartPt pt) ... -- boolean
   */
  // to compute the area of this shape
  public double area() {
    return Math.PI * this.radius * this.radius;
  }

  // to compute the distance form this shape to the origin
  public double distToOrigin() {
    return this.center.distToOrigin() - this.radius;
  }

  // to increase the size of this shape by the given increment
  public IShape grow(int inc) {
    return new Circle(this.center, this.radius + inc, this.color);
  }

  public boolean equals(Circle other) {
    return this.center.equals(other.center) && this.radius == other.radius
        && this.color.equals(other.color);
  }

  // is the area of this shape bigger than the area of the given shape?
  public boolean biggerThan(IShape that) {
    /*---------------------------------------------------
     // ** TEMPLATE ** 
     public returnType methodName() {
     ... this.center ...              -- CartPt
     ... this.radius ...              -- int
     ... this.color ...               -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     
     ... that.center ...              -- CartPt
     ... that.radius ...              -- int
     ... that.color ...               -- String
     
     ... that.area() ...                  -- double 
     ... that.distToOrigin() ...          -- double 
     ... that.grow(int inc) ...           -- IShape
     ---------------------------------------------------*/
    return this.area() >= that.area();
  }

  // does this shape (including the boundary) contain the given point?
  public boolean contains(CartPt pt) {
    /*---------------------------------------------------
     // ** TEMPLATE ** 
     public returnType methodName() {
     ... this.center ...              -- CartPt
     ... this.radius ...              -- int
     ... this.color ...               -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     
     ... this.center.distToOrigin() ...      -- double
     ... this.center.distTo(CartPt x) ...    -- double
     
     ... pt.distToOrigin() ...               -- double
     ... pt.distTo(CartPt x) ...             -- double
     ---------------------------------------------------*/
    return this.center.distTo(pt) <= this.radius;
  }

}

// to represent a square
class Square implements IShape {
  CartPt nw;
  int size;
  String color;

  Square(CartPt nw, int size, String color) {
    this.nw = nw;
    this.size = size;
    this.color = color;
  }

  /*
   * // ** TEMPLATE **
   * returnType methodName() {
   * ... this.nw ... -- CartPt
   * ... this.size ... -- int
   * ... this.color ... -- String
   * 
   * ... this.area() ... -- double
   * ... this.distToOrigin() ... -- double
   * ... this.grow(int inc) ... -- IShape
   * }
   */

  // to compute the area of this shape
  public double area() {
    return this.size * this.size;
  }

  public boolean equals(Circle other) {
    return false;
  }

  // to compute the distance form this shape to the origin
  public double distToOrigin() {
    return this.nw.distToOrigin();
  }

  // to increase the size of this shape by the given increment
  public IShape grow(int inc) {
    return new Square(this.nw, this.size + inc, this.color);
  }

  // is the area of this shape bigger than the area of the given shape?
  public boolean biggerThan(IShape that) {
    /*---------------------------------------------------
     // ** TEMPLATE ** 
     public returnType methodName() {
     ... this.nw ...                  -- CartPt
     ... this.size ...                -- int
     ... this.color ...               -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     
     ... that.nw ...                  -- CartPt
     ... that.size ...                -- int
     ... that.color ...               -- String
     
     ... that.area() ...                  -- double 
     ... that.distToOrigin() ...          -- double 
     ... that.grow(int inc) ...           -- IShape
     ---------------------------------------------------*/
    return this.area() >= that.area();
  }

  // does this shape (including the boundary) contain the given point?
  public boolean contains(CartPt pt) {
    /*---------------------------------------------------
     // ** TEMPLATE ** 
     public returnType methodName() {
     ... this.nw ...                  -- CartPt
     ... this.size ...                -- int
     ... this.color ...               -- String
     
     ... this.area() ...                  -- double 
     ... this.distToOrigin() ...          -- double 
     ... this.grow(int inc) ...           -- IShape
     
     ... this.nw.distToOrigin() ...       -- double
     ... this.nw.distTo(CartPt x) ...     -- double
     
     ... pt.distToOrigin() ...               -- double
     ... pt.distTo(CartPt x) ...             -- double
     ---------------------------------------------------*/
    return (this.nw.x <= pt.x) && (pt.x <= this.nw.x + this.size) && (this.nw.y <= pt.y)
        && (pt.y <= this.nw.y + this.size);
  }
}

/*
 * +--------+
 * | CartPt |
 * +--------+
 * | int x |
 * | int y |
 * +--------+
 * 
 */

// to represent a Cartesian point
class CartPt {
  int x;
  int y;

  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // to compute the distance form this point to the origin
  public double distToOrigin() {
    return Math.sqrt(this.x * this.x + this.y * this.y);
  }

  // to compute the distance form this point to the given point
  public double distTo(CartPt pt) {
    return Math.sqrt((this.x - pt.x) * (this.x - pt.x) + (this.y - pt.y) * (this.y - pt.y));
  }
}
