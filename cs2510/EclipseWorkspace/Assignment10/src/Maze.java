import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
import java.util.function.Predicate;

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  void updatePrevious(ANode<T> prev) {
    this.prev = prev;
  }

  void updateNext(ANode<T> next) {
    this.next = next;
  }

  int size() {
    return 0;
  }

  int sizeStarter() {
    return this.next.size();
  }

  void addToHead(T data) {
    new Vertex<T>(data, this.next, this);
  }

  void addToTail(T data) {
    new Vertex<T>(data, this, this.prev);
  }

  T removeFromHead() {
    return this.next.removeHead();
  }

  T removeFromTail() {
    return this.prev.removeTail();
  }

  T removeHead() {
    throw new RuntimeException("List is Empty");
  }

  T removeTail() {
    throw new RuntimeException("List is Empty");
  }

  ANode<T> find(Predicate<T> where) {
    return this;
  }

  ANode<T> findStarter(Predicate<T> where) {
    return this.next.find(where);
  }
}

class Vertex<T> extends ANode<T> {
  T data;

  Vertex(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  Vertex(T data, ANode<T> next, ANode<T> prev) {
    if (prev == null || next == null) {
      throw new IllegalArgumentException("Cannot assign null valuesto previous or next nodes");
    }
    this.data = data;
    this.next = next;
    this.next.updatePrevious(this);
    this.prev = prev;
    this.prev.updateNext(this);
  }

  int size() {
    return 1 + this.next.size();
  }

  T removeHead() {
    this.prev.updateNext(this.next);
    this.next.updatePrevious(this.prev);
    return this.data;
  }

  T removeTail() {
    this.next.updatePrevious(this.prev);
    this.prev.updateNext(this.next);
    return this.data;
  }

  ANode<T> find(Predicate<T> where) {
    if (where.test(this.data)) {
      return this;
    }
    return this.next.find(where);
  }
}

class Sentinel<T> extends ANode<T> {

  Sentinel() {
    this.next = this;
    this.prev = this;
  }
}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  int size() {
    return header.sizeStarter();
  }

  void addAtHead(T data) {
    this.header.addToHead(data);
  }

  void addAtTail(T data) {
    this.header.addToTail(data);
  }

  T removeFromHead() {
    return this.header.removeFromHead();
  }

  T removeFromTail() {
    return this.header.removeFromTail();
  }

  ANode<T> find(Predicate<T> where) {
    return this.header.findStarter(where);
  }
}

//Represents a mutable collection of items
interface ICollection<T> {
  // Is this collection empty?
  boolean isEmpty();

  // EFFECT: adds the item to the collection
  void add(T item);

  // Returns the first item of the collection
  // EFFECT: removes that first item
  T remove();
}

class Stack<T> implements ICollection<T> {
  Deque<T> contents;

  Stack() {
    this.contents = new Deque<T>();
  }

  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  public T remove() {
    return this.contents.removeFromHead();
  }

  public void add(T item) {
    this.contents.addAtHead(item);
  }
}

class Queue<T> implements ICollection<T> {
  Deque<T> contents;

  Queue() {
    this.contents = new Deque<T>();
  }

  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  public T remove() {
    return this.contents.removeFromHead();
  }

  public void add(T item) {
    this.contents.addAtTail(item); // NOTE: Different from Stack!
  }
}

// Represents a node
class Node<T> {
  T value;
  int x;
  int y;
  Color c;
  ArrayList<DirectedEdge<T>> edges = new ArrayList<DirectedEdge<T>>();

  // Constructor
  public Node(T value, int x, int y, Color c) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.c = c;
  }

  // Draws the node
  public WorldImage draw(int nodeSize) {
    return new RectangleImage(nodeSize, nodeSize, OutlineMode.SOLID, this.c);
  }

  // Sets the color of the node
  public void setColor(Color c) {
    this.c = c;
  }

  public void addEdge(DirectedEdge<T> edge) {
    this.edges.add(edge);
  }

  public void removeEdge(DirectedEdge<T> edge) {
    this.edges.remove(edge);
  }

  public boolean hasEdgeTo(Node<T> other) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.dest == other) {
        return true;
      }
    }
    return false;
  }

  ArrayList<Node<T>> getNeighbors() {
    ArrayList<Node<T>> ans = new ArrayList<Node<T>>();
    for (DirectedEdge<T> edge : this.edges) {
      ans.add(edge.dest);
    }
    return ans;
  }

  DirectedEdge<T> getEdgeTo(Node<T> node) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.dest.value == node.value) {
        return edge;
      }
    }
    throw new IllegalArgumentException("Node has no edge to given node");
  }

  void clearEdges() {
    this.edges.clear();
  }
}

// Represents a directed edge
class DirectedEdge<T> {
  int weight;
  Node<T> src;
  Node<T> dest;

  // Constructor
  public DirectedEdge(Node<T> src, Node<T> dest, int weight) {
    this.src = src;
    this.dest = dest;
    this.weight = weight;
  }

  // Draws the directed edge
  public WorldImage draw(int nodeSize) {
    if (this.src.x == this.dest.x) {
      return this.drawHorizontalEdge(nodeSize);
    }
    else {
      return this.drawVerticalEdge(nodeSize);
    }
  }

  // Draws a vertical edge
  public WorldImage drawVerticalEdge(int nodeSize) {
    return new RectangleImage(1, nodeSize, OutlineMode.OUTLINE, Color.red);
  }

  // draws a horizontal edge
  public WorldImage drawHorizontalEdge(int nodeSize) {
    return new RectangleImage(nodeSize, 1, OutlineMode.OUTLINE, Color.red);
  }

}

// Represents an edge weighted graph
class EdgeWeightedGraph<T> {
  ArrayList<Node<T>> nodes;

  // Constructor
  public EdgeWeightedGraph() {
    this.nodes = new ArrayList<Node<T>>();
  }

  // Adds a node that is given to the graph
  void addNode(Node<T> node) {
    this.nodes.add(node);
  }

  // Adds an edge that is given to the graph
  void addEdge(Node<T> src, Node<T> destination, int weight) {
    DirectedEdge<T> edge = new DirectedEdge<T>(destination, src, weight);
    DirectedEdge<T> edge2 = new DirectedEdge<T>(src, destination, weight);
    destination.addEdge(edge);
    src.addEdge(edge2);
  }

  // Gets the node at the index given
  Node<T> get(int index) {
    return this.nodes.get(index);
  }

  // Determines if there is an edge between these two nodes
  boolean edgeExistsFor(Node<T> node1, Node<T> node2) {
    return node1.hasEdgeTo(node2) || node2.hasEdgeTo(node1);
  }

  ArrayList<DirectedEdge<T>> getTotalEdges() {
    ArrayList<DirectedEdge<T>> ans = new ArrayList<DirectedEdge<T>>();
    for (Node<T> node : this.nodes) {
      ans.addAll(node.edges);
    }
    return ans;
  }

  void assignNewEdges(ArrayList<DirectedEdge<T>> edges) {
    for (Node<T> node : this.nodes) {
      node.clearEdges();
    }
    for (DirectedEdge<T> edge : edges) {
      this.addEdge(edge.src, edge.dest, edge.weight);
    }
  }

  void resetColors() {
    for (Node<T> node : this.nodes) {
      node.setColor(Color.gray);
    }
    this.nodes.get(0).setColor(Color.green);
    this.nodes.get(this.nodes.size() - 1).setColor(Color.pink);
  }
}

// Represents a maze
class Maze extends World {

  static int SCREEN_SIZE = 500;
  EdgeWeightedGraph<Integer> world = new EdgeWeightedGraph<Integer>();
  Random rand = new Random();
  int dimX;
  int dimY;
  int nodeSize;

  // Constructor
  public Maze(int dimX, int dimY) {
    this.dimX = dimX;
    this.dimY = dimY;
    this.nodeSize = (SCREEN_SIZE) / Math.max(dimX, dimY);
    for (int i = 0; i < dimY; i++) {
      for (int j = 0; j < dimX; j++) {
        Color c = Color.gray;

        if (i == 0 && j == 0) {
          c = Color.green;
        }
        else if (i == dimY - 1 && j == dimX - 1) {
          c = Color.pink;
        }

        Node<Integer> newNode = new Node<Integer>(((i * dimX) + j), i, j, c);
        this.world.addNode(newNode);

        if (i != 0) {
          Node<Integer> top = this.world.get((i * dimX) - dimX + j);
          this.world.addEdge(newNode, top, rand.nextInt());
        }
        if (j != 0) {
          Node<Integer> left = this.world.get(i * dimX + j - 1);
          this.world.addEdge(newNode, left, rand.nextInt());
        }
      }
    }
    this.makeMaze();
  }

  // Makes the scene
  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    for (int row = 0; row < this.dimY; row++) {
      for (int col = 0; col < this.dimX; col++) {
        Node<Integer> node = this.world.nodes.get(row * dimX + col);
        int x = node.x * this.nodeSize;
        int y = node.y * this.nodeSize;
        background.placeImageXY(node.draw(this.nodeSize), x + this.nodeSize / 2,
            y + this.nodeSize / 2);

        if (col < this.dimX - 1
            && !this.world.edgeExistsFor(node, this.world.nodes.get(row * dimX + col + 1))) {
          background.placeImageXY(
              new RectangleImage(this.nodeSize, 1, OutlineMode.SOLID, Color.red),
              x + this.nodeSize / 2, y + this.nodeSize);
        }

        if (row < this.dimY - 1
            && !this.world.edgeExistsFor(node, this.world.nodes.get(row * dimX + col + dimX))) {
          background.placeImageXY(
              new RectangleImage(1, this.nodeSize, OutlineMode.SOLID, Color.red), x + this.nodeSize,
              y + this.nodeSize / 2);
        }
      }
    }
    return background;
  }

  // Runs the big bang based on a tick value
  @Override
  public void onTick() {

    // Will be implemented in next assignment
  }

  // Resets the game with the 'r' key
  @Override
  public void onKeyEvent(String k) {
    this.world.resetColors();
    if (k.equals("r")) {
      return;
    }
    ICollection<Node<Integer>> collection = new Stack<Node<Integer>>();
    if (k.equals("b")) {
      collection = new Queue<Node<Integer>>();
    }
    Node<Integer> from = this.world.nodes.get(0);
    Node<Integer> to = this.world.nodes.get(this.world.nodes.size() - 1);
    HashMap<Node<Integer>, DirectedEdge<Integer>> map = this.search(from, to, collection);
    for (Node<Integer> node : map.keySet()) {
      node.setColor(Color.cyan);
    }
    System.out.println(to);
    for (Node<Integer> node : reconstruct(to, map, from)) {
      node.setColor(Color.blue);
    } // Will be implemented in next assignment

  }

  // Makes the maze randomly
  void makeMaze() {
    HashMap<Node<Integer>, Node<Integer>> representatives = new HashMap<Node<Integer>, Node<Integer>>();
    ArrayList<DirectedEdge<Integer>> edgesInTree = new ArrayList<DirectedEdge<Integer>>();
    ArrayList<DirectedEdge<Integer>> worklist = new ArrayList<DirectedEdge<Integer>>(
        this.world.getTotalEdges());
    worklist.sort(new WeightComparator());
    for (Node<Integer> node : this.world.nodes) {
      representatives.put(node, node);
    }
    while (!worklist.isEmpty()) {
      DirectedEdge<Integer> nextEdge = worklist.remove(0);

      if (this.findRepresentative(representatives, representatives.get(nextEdge.src)) != this
          .findRepresentative(representatives, representatives.get(nextEdge.dest))) {
        edgesInTree.add(nextEdge);
        representatives.put(this.findRepresentative(representatives, nextEdge.src),
            this.findRepresentative(representatives, nextEdge.dest));
      }
    }
    this.world.assignNewEdges(edgesInTree);
  }

  HashMap<Node<Integer>, DirectedEdge<Integer>> search(Node<Integer> from, Node<Integer> to,
      ICollection<Node<Integer>> collection) {
    HashMap<Node<Integer>, DirectedEdge<Integer>> cameFromEdge = new HashMap<Node<Integer>, DirectedEdge<Integer>>();
    ArrayList<Node<Integer>> seen = new ArrayList<Node<Integer>>();
    ICollection<Node<Integer>> worklist = collection;
    worklist.add(from);

    while (!worklist.isEmpty()) {
      Node<Integer> next = worklist.remove();

      if (next.equals(to)) {
        return cameFromEdge;
      }
      else if (seen.contains(next)) {
        continue;
      }
      else {
        for (Node<Integer> neighbor : next.getNeighbors()) {
          worklist.add(neighbor);
          if (!seen.contains(neighbor)) {
            cameFromEdge.put(neighbor, next.getEdgeTo(neighbor));
          }
        }
        seen.add(next);
      }
    }

    throw new IllegalArgumentException("No path to destination");
  }

  ArrayList<Node<Integer>> reconstruct(Node<Integer> to,
      HashMap<Node<Integer>, DirectedEdge<Integer>> map, Node<Integer> from) {
    ArrayList<Node<Integer>> ans = new ArrayList<Node<Integer>>();
    ans.add(to);
    while (to != from) {
      to = map.get(to).src;
      ans.add(to);
    }
    return ans;
  }

  // Finds the representative determined by the hash map
  Node<Integer> findRepresentative(HashMap<Node<Integer>, Node<Integer>> representatives,
      Node<Integer> start) {
    while (representatives.get(start) != start) {
      start = representatives.get(start);
    }
    return start;
  }
}

// Represents a comparator
class WeightComparator implements Comparator<DirectedEdge<Integer>> {

  // Constructor
  public WeightComparator() {
  }

  // Compares two directed edges' weights
  @Override
  public int compare(DirectedEdge<Integer> o1, DirectedEdge<Integer> o2) {
    return Integer.compare(o1.weight, o2.weight);
  }
}

// Represents the examples and tests
class ExamplesMaze {

  // Constructor
  public ExamplesMaze() {
    this.init();
  }

  World testerWorld2 = new Maze(5, 5);
  Maze testingMaze = new Maze(9, 9);
  Maze testingMaze2 = new Maze(4, 5);
  World starterWorld = new Maze(50, 50);
  Node<Integer> node1;
  Node<String> node2;
  Node<Integer> node3;
  Node<Integer> node4;
  Node<String> node5;
  EdgeWeightedGraph<Integer> edgeWeightedGraph;
  DirectedEdge<Integer> expectedDirectedEdge1;
  DirectedEdge<Integer> expectedDirectedEdge2;
  DirectedEdge<Integer> expectedDirectedEdge3;
  DirectedEdge<String> directedEdge;
  HashMap<Node<Integer>, Node<Integer>> hash;

  void init() {
    this.node1 = new Node<Integer>(5, 5, 5, Color.blue);
    this.node2 = new Node<String>("hello", 4, 10, Color.red);
    this.node3 = new Node<Integer>(10, 40, 10, Color.black);
    this.node4 = new Node<Integer>(15, 60, 10, Color.pink);
    this.node5 = new Node<String>("tester", 4, 10, Color.cyan);
    this.edgeWeightedGraph = new EdgeWeightedGraph<Integer>();
    this.expectedDirectedEdge1 = new DirectedEdge<Integer>(this.node1, this.node3, 10);
    this.expectedDirectedEdge2 = new DirectedEdge<Integer>(this.node3, this.node1, 4);
    this.expectedDirectedEdge3 = new DirectedEdge<Integer>(this.node4, this.node3, 25);
    this.directedEdge = new DirectedEdge<String>(this.node2, this.node5, 40);
    this.hash = new HashMap<Node<Integer>, Node<Integer>>();
    this.hash.put(this.node1, this.node3);
    this.hash.put(this.node3, this.node4);
    this.hash.put(this.node4, this.node4);
  }

  // Example maze to play with
  void testWorld(Tester t) {
    this.init();
    this.starterWorld.bigBang(Maze.SCREEN_SIZE, Maze.SCREEN_SIZE);
  }

  // tests the draw method
  void testDraw(Tester t) {
    this.init();
    t.checkExpect(this.node1.draw(100),
        new RectangleImage(100, 100, OutlineMode.SOLID, Color.blue));
    t.checkExpect(this.node2.draw(100), new RectangleImage(100, 100, OutlineMode.SOLID, Color.red));
    t.checkExpect(this.node3.draw(100),
        new RectangleImage(100, 100, OutlineMode.SOLID, Color.black));

    t.checkExpect(this.directedEdge.draw(100),
        new RectangleImage(100, 1, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge1.draw(100),
        new RectangleImage(1, 100, OutlineMode.OUTLINE, Color.red));
  }

  // tests the setColor method
  void testSetColor(Tester t) {
    this.init();
    t.checkExpect(this.node1.c, Color.blue);
    this.node1.setColor(Color.red);
    t.checkExpect(this.node1.c, Color.red);

    t.checkExpect(this.node2.c, Color.red);
    this.node2.setColor(Color.black);
    t.checkExpect(this.node2.c, Color.black);
  }

  // tests the drawVerticalEdge method
  void testDrawVerticalEdge(Tester t) {
    this.init();
    t.checkExpect(this.expectedDirectedEdge1.drawVerticalEdge(100),
        new RectangleImage(1, 100, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge2.drawVerticalEdge(100),
        new RectangleImage(1, 100, OutlineMode.OUTLINE, Color.red));
  }

  // tests the drawHorizontalEdge method
  void testDrawHorizontalEdge(Tester t) {
    this.init();
    t.checkExpect(this.expectedDirectedEdge1.drawHorizontalEdge(100),
        new RectangleImage(100, 1, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge2.drawHorizontalEdge(100),
        new RectangleImage(100, 1, OutlineMode.OUTLINE, Color.red));
  }

  // tests the addNode method
  void testAddNode(Tester t) {
    this.init();
    t.checkExpect(this.edgeWeightedGraph.nodes.size(), 0);
    this.edgeWeightedGraph.addNode(this.node1);
    t.checkExpect(this.edgeWeightedGraph.nodes.get(0), this.node1);
    t.checkExpect(this.edgeWeightedGraph.nodes.size(), 1);

    this.edgeWeightedGraph.addNode(this.node3);
    t.checkExpect(this.edgeWeightedGraph.nodes.get(0), this.node1);
    t.checkExpect(this.edgeWeightedGraph.nodes.get(1), this.node3);
    t.checkExpect(this.edgeWeightedGraph.nodes.size(), 2);
  }

  // tests the addEdge method
  void testAddEdge(Tester t) {
    this.init();
    t.checkExpect(this.edgeWeightedGraph.getTotalEdges().size(), 0);
    this.edgeWeightedGraph.addNode(this.node1);
    this.edgeWeightedGraph.addNode(this.node3);
    this.edgeWeightedGraph.addEdge(this.node1, this.node3, 4);
    t.checkExpect(this.edgeWeightedGraph.getTotalEdges().get(0), this.expectedDirectedEdge2);
    this.edgeWeightedGraph.addEdge(this.node3, this.node1, 10);
    t.checkExpect(this.edgeWeightedGraph.getTotalEdges().get(1), this.expectedDirectedEdge1);
    this.edgeWeightedGraph.addNode(this.node4);
    this.edgeWeightedGraph.addEdge(this.node3, this.node4, 25);
    t.checkExpect(this.edgeWeightedGraph.getTotalEdges().get(2), this.expectedDirectedEdge3);
  }

  // tests the get method
  void testGet(Tester t) {
    this.init();
    this.edgeWeightedGraph.addNode(this.node1);
    this.edgeWeightedGraph.addNode(this.node3);
    this.edgeWeightedGraph.addNode(this.node4);
    t.checkExpect(this.edgeWeightedGraph.get(0), this.node1);
    t.checkExpect(this.edgeWeightedGraph.get(1), this.node3);
    t.checkExpect(this.edgeWeightedGraph.get(2), this.node4);
  }

  // tests the edgeExistsFor method
  void testEdgeExistsFor(Tester t) {
    this.init();
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node3), false);
    this.edgeWeightedGraph.addNode(this.node1);
    this.edgeWeightedGraph.addNode(this.node3);
    this.edgeWeightedGraph.addEdge(this.node1, this.node3, 4);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node3), false);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node1), true);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node4), false);
    this.edgeWeightedGraph.addNode(this.node4);
    this.edgeWeightedGraph.addEdge(this.node1, this.node4, 40);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node4), false);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node4, this.node1), true);
  }

  // tests the makeScene method
  void testMakeScene(Tester t) {
    this.init();
    this.testingMaze.makeScene();
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 80);
    t.checkExpect(this.testingMaze2.world.getTotalEdges().size(), 19);
    t.checkExpect(this.testingMaze.world.nodes.size(), 81);
    t.checkExpect(this.testingMaze2.world.nodes.size(), 20);
  }

  // tests the makeMaze method
  void testMakeMaze(Tester t) {
    this.init();
    // check if dimX * dimY - 1 = the size
//    t.checkExpect(this.testingMaze.makeMaze(), 80);
//    t.checkExpect(this.testingMaze2.makeMaze(), 19);
  }

  // tests the onTick method
  // Not Implemented yet in Maze

  // tests the onKeyEvent method
  // Not Implemented yet in Maze

  // tests the findRepresentative method
  void testFindRepresentative(Tester t) {
    this.init();
    t.checkExpect(this.testingMaze.findRepresentative(this.hash, this.node1), this.node4);
    t.checkExpect(this.testingMaze.findRepresentative(this.hash, node3), this.node4);
  }

  // tests the compare method
  void testCompare(Tester t) {
    this.init();
    t.checkExpect(
        new WeightComparator().compare(this.expectedDirectedEdge1, this.expectedDirectedEdge2), 1);
    t.checkExpect(
        new WeightComparator().compare(this.expectedDirectedEdge2, this.expectedDirectedEdge1), -1);
    t.checkExpect(
        new WeightComparator().compare(this.expectedDirectedEdge3, this.expectedDirectedEdge2), 1);
  }

}