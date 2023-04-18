import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;
import java.util.function.Predicate;

// Represents a node
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  // updates previous node to the one given
  void updatePrevious(ANode<T> prev) {
    this.prev = prev;
  }

  // updates the next node to the one given
  void updateNext(ANode<T> next) {
    this.next = next;
  }

  // gives the size
  int size() {
    return 0;
  }

  // helper for the size method
  int sizeStarter() {
    return this.next.size();
  }

  // adds this data to the head
  void addToHead(T data) {
    new Vertex<T>(data, this.next, this);
  }

  // adds this data to the tail
  void addToTail(T data) {
    new Vertex<T>(data, this, this.prev);
  }

  // removes the data at the head and returns the value of it
  T removeFromHead() {
    return this.next.removeHead();
  }

  // removes the data at the tail and returns the value of it
  T removeFromTail() {
    return this.prev.removeTail();
  }

  // removes the head
  T removeHead() {
    throw new RuntimeException("List is Empty");
  }

  // removes the tail
  T removeTail() {
    throw new RuntimeException("List is Empty");
  }

  // finds based on the predicate given
  ANode<T> find(Predicate<T> where) {
    return this;
  }

  // helper for the find method
  ANode<T> findStarter(Predicate<T> where) {
    return this.next.find(where);
  }
}

// represents a vertex
class Vertex<T> extends ANode<T> {
  T data;

  // constructor
  Vertex(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }

  // constructor with next and prev given
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

  // returns the size
  int size() {
    return 1 + this.next.size();
  }

  // removes from the head of the list
  T removeHead() {
    this.prev.updateNext(this.next);
    this.next.updatePrevious(this.prev);
    return this.data;
  }

  // removes from the tail of the list
  T removeTail() {
    this.next.updatePrevious(this.prev);
    this.prev.updateNext(this.next);
    return this.data;
  }

  // finds based on a predicate
  ANode<T> find(Predicate<T> where) {
    if (where.test(this.data)) {
      return this;
    }
    return this.next.find(where);
  }
}

// represents a sentinel
class Sentinel<T> extends ANode<T> {
// constructor
  Sentinel() {
    this.next = this;
    this.prev = this;
  }
}

// represents a deque
class Deque<T> {
  Sentinel<T> header;

  // constructor
  Deque() {
    this.header = new Sentinel<T>();
  }

  // constructor with the header given
  Deque(Sentinel<T> header) {
    this.header = header;
  }

  // returns the size
  int size() {
    return header.sizeStarter();
  }

  // adds the data given to the head
  void addAtHead(T data) {
    this.header.addToHead(data);
  }

  // adds the data given to the tail
  void addAtTail(T data) {
    this.header.addToTail(data);
  }

  // removes the data from the head and returns its value
  T removeFromHead() {
    return this.header.removeFromHead();
  }

  // removes the data from the tail and returns its value
  T removeFromTail() {
    return this.header.removeFromTail();
  }

  // finds a node based on the predicate given
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

// represents a stack
class Stack<T> implements ICollection<T> {
  Deque<T> contents;

  // constructor
  Stack() {
    this.contents = new Deque<T>();
  }

  // returns if the contents are empty
  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  // removes from the head of the contents
  public T remove() {
    return this.contents.removeFromHead();
  }

  // adds the item given to the head of the contents
  public void add(T item) {
    this.contents.addAtHead(item);
  }
}

// represents a queue
class Queue<T> implements ICollection<T> {
  Deque<T> contents;

  // constructor
  Queue() {
    this.contents = new Deque<T>();
  }

  // returns if empty
  public boolean isEmpty() {
    return this.contents.size() == 0;
  }

  // removes from the head of the contents
  public T remove() {
    return this.contents.removeFromHead();
  }

  // adds to the tail of the contents
  public void add(T item) {
    this.contents.addAtTail(item);
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

  // adds the edge given to the list of edges
  public void addEdge(DirectedEdge<T> edge) {
    this.edges.add(edge);
  }

  // removes the edge given
  public void removeEdge(DirectedEdge<T> edge) {
    this.edges.remove(edge);
  }

  // determines if there is an edge between these two
  public boolean hasEdgeTo(Node<T> other) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.dest == other) {
        return true;
      }
    }
    return false;
  }

  // returns the list of neighbors
  ArrayList<Node<T>> getNeighbors() {
    ArrayList<Node<T>> ans = new ArrayList<Node<T>>();
    for (DirectedEdge<T> edge : this.edges) {
      ans.add(edge.dest);
    }
    return ans;
  }

  // gets the edge from these two
  DirectedEdge<T> getEdgeTo(Node<T> node) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.dest.value == node.value) {
        return edge;
      }
    }
    throw new IllegalArgumentException("Node has no edge to given node");
  }

  // clears all the edges
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

  // gets the first node
  Node<T> getFirst() {
    return this.nodes.get(0);
  }

  // gets the last node
  Node<T> getLast() {
    return this.nodes.get(this.nodes.size() - 1);
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

  // gets all the edges compiled into one list
  ArrayList<DirectedEdge<T>> getTotalEdges() {
    ArrayList<DirectedEdge<T>> ans = new ArrayList<DirectedEdge<T>>();
    for (Node<T> node : this.nodes) {
      ans.addAll(node.edges);
    }
    return ans;
  }

  // assigns new edges
  void assignNewEdges(ArrayList<DirectedEdge<T>> edges) {
    for (Node<T> node : this.nodes) {
      node.clearEdges();
    }
    for (DirectedEdge<T> edge : edges) {
      this.addEdge(edge.src, edge.dest, edge.weight);
    }
  }

  // resets the colors
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
  static int SCREEN_SIZE = 1000;
  EdgeWeightedGraph<Integer> world;
  Random rand = new Random();
  int dimX;
  int dimY;
  int nodeSize;
  boolean displayingSolution;
  HashMap<Node<Integer>, DirectedEdge<Integer>> solution;
  ArrayList<Node<Integer>> waterfall;

  // Constructor
  public Maze(int dimX, int dimY) {
    this.init(dimX, dimY);
  }

  // Makes the scene
  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    for (int row = 0; row < this.dimY; row++) {
      for (int col = 0; col < this.dimX; col++) {
        Node<Integer> node = this.world.get(row * dimX + col);
        int x = node.x * this.nodeSize;
        int y = node.y * this.nodeSize;
        background.placeImageXY(node.draw(this.nodeSize), x + this.nodeSize / 2,
            y + this.nodeSize / 2);
        if (col < this.dimX - 1
            && !this.world.edgeExistsFor(node, this.world.get(row * dimX + col + 1))) {
          background.placeImageXY(
              new RectangleImage(this.nodeSize, 1, OutlineMode.SOLID, Color.red),
              x + this.nodeSize / 2, y + this.nodeSize);
        }
        if (row < this.dimY - 1
            && !this.world.edgeExistsFor(node, this.world.get(row * dimX + col + dimX))) {
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
    if (this.displayingSolution) {
      if (this.waterfall.size() == 0) {
        for (Node<Integer> node : reconstruct(this.world.getLast(), this.solution,
            this.world.getFirst())) {
          node.setColor(Color.blue);
        }
        this.displayingSolution = false;
      }
      else {
        this.waterfall.remove(0).setColor(Color.cyan);
      }
    }
  }

  // Resets the game with the 'r' key and displays different
  // search algorithms. Bredth first search with 'b' and
  // depth first search with any other key
  @Override
  public void onKeyEvent(String k) {
    if (this.displayingSolution) {
      return;
    }
    this.world.resetColors();
    if (k.equals("r")) {
      this.init(this.dimX, this.dimY);
      return;
    }
    this.waterfall.clear();
    ICollection<Node<Integer>> collection = new Stack<Node<Integer>>();
    if (k.equals("b")) {
      collection = new Queue<Node<Integer>>();
    }
    Node<Integer> from = this.world.getFirst();
    Node<Integer> to = this.world.getLast();
    this.solution = this.search(from, to, collection);
    this.displayingSolution = true;
  }

  // initializes the variables
  void init(int dimX, int dimY) {
    this.dimX = dimX;
    this.dimY = dimY;
    this.nodeSize = (SCREEN_SIZE) / Math.max(dimX, dimY);
    this.world = new EdgeWeightedGraph<Integer>();
    this.displayingSolution = false;
    this.waterfall = new ArrayList<Node<Integer>>();
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

  // searches for the hash of two given nodes
  HashMap<Node<Integer>, DirectedEdge<Integer>> search(Node<Integer> from, Node<Integer> to,
      ICollection<Node<Integer>> collection) {
    HashMap<Node<Integer>, DirectedEdge<Integer>> cameFromEdge = new HashMap<Node<Integer>, DirectedEdge<Integer>>();
    ArrayList<Node<Integer>> seen = new ArrayList<Node<Integer>>();
    ICollection<Node<Integer>> worklist = collection;
    worklist.add(from);
    this.waterfall.add(from);
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
          this.waterfall.add(neighbor);
          if (!seen.contains(neighbor)) {
            cameFromEdge.put(neighbor, next.getEdgeTo(neighbor));
          }
        }
        seen.add(next);
      }
    }
    throw new IllegalArgumentException("No path to destination");
  }

  // reconstructs a list of nodes
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

  World testerWorld2;
  Maze testingMaze;
  Maze testingMaze2;
  World starterWorld;
  Node<Integer> node1;
  Node<String> node2;
  Node<Integer> node3;
  Node<Integer> node4;
  Node<String> node5;
  EdgeWeightedGraph<Integer> edgeWeightedGraph;
  DirectedEdge<Integer> expectedDirectedEdge1;
  DirectedEdge<Integer> expectedDirectedEdge2;
  DirectedEdge<Integer> expectedDirectedEdge3;
  ArrayList<DirectedEdge<Integer>> edges;
  DirectedEdge<String> directedEdge;
  HashMap<Node<Integer>, Node<Integer>> hash;
  Deque<String> deque1 = new Deque<String>();
  Deque<String> deque2 = new Deque<String>();
  Deque<Integer> deque3 = new Deque<Integer>();
  Deque<Integer> deque4 = new Deque<Integer>();
  ANode<String> testNode1 = new Vertex<String>("testing");
  ANode<String> testNode2 = new Vertex<String>("testing2");
  ANode<String> sentinelNode = new Sentinel<String>();

  void init() {
    this.testerWorld2 = new Maze(5, 5);
    this.testingMaze = new Maze(9, 9);
    this.testingMaze2 = new Maze(4, 5);
    this.starterWorld = new Maze(100, 60);
    this.node1 = new Node<Integer>(5, 5, 5, Color.blue);
    this.node2 = new Node<String>("hello", 4, 10, Color.red);
    this.node3 = new Node<Integer>(10, 40, 10, Color.black);
    this.node4 = new Node<Integer>(15, 60, 10, Color.pink);
    this.node5 = new Node<String>("tester", 4, 10, Color.cyan);
    this.edgeWeightedGraph = new EdgeWeightedGraph<Integer>();
    this.expectedDirectedEdge1 = new DirectedEdge<Integer>(this.node1, this.node3, 10);
    this.expectedDirectedEdge2 = new DirectedEdge<Integer>(this.node1, this.node3, 4);
    this.expectedDirectedEdge3 = new DirectedEdge<Integer>(this.node3, this.node4, 25);
    this.edges = new ArrayList<DirectedEdge<Integer>>(3);
    this.edges.add(this.expectedDirectedEdge1);
    this.edges.add(this.expectedDirectedEdge2);
    this.edges.add(this.expectedDirectedEdge3);
    this.directedEdge = new DirectedEdge<String>(this.node2, this.node5, 40);
    this.hash = new HashMap<Node<Integer>, Node<Integer>>();
    this.hash.put(this.node1, this.node3);
    this.hash.put(this.node3, this.node4);
    this.hash.put(this.node4, this.node4);
    this.deque1 = new Deque<String>();
    this.deque2 = new Deque<String>();
    this.deque3 = new Deque<Integer>();
    this.deque4 = new Deque<Integer>();
    this.testNode1 = new Vertex<String>("testing");
    this.testNode2 = new Vertex<String>("testing2");
    this.sentinelNode = new Sentinel<String>();
    new Vertex<String>("abc",
        new Vertex<String>("bcd", new Vertex<String>("cde",
            new Vertex<String>("def", this.deque2.header, this.deque2.header), this.deque2.header),
            this.deque2.header),
        this.deque2.header);
    new Vertex<Integer>(420,
        new Vertex<Integer>(69, new Vertex<Integer>(7,
            new Vertex<Integer>(560, this.deque3.header, this.deque3.header), this.deque3.header),
            this.deque3.header),
        this.deque3.header);
    new Vertex<Integer>(21, new Vertex<Integer>(2,
        new Vertex<Integer>(635, this.deque4.header, this.deque4.header), this.deque4.header),
        this.deque4.header);
  }

  // Example maze to play with
  void testWorld(Tester t) {
    this.init();
    this.starterWorld.bigBang(Maze.SCREEN_SIZE, Maze.SCREEN_SIZE, .0001);
  }

  // tests the updatePrevious method
  void testUpdatePrevious(Tester t) {
    this.init();
    t.checkExpect(this.testNode1.prev, null);
    this.testNode1.updatePrevious(this.testNode2);
    t.checkExpect(this.testNode1.prev, this.testNode2);
  }

  // tests the updateNext method
  void testUpdateNext(Tester t) {
    this.init();
    t.checkExpect(this.testNode1.next, null);
    this.testNode1.updateNext(this.testNode2);
    t.checkExpect(this.testNode1.next, this.testNode2);
  }

  // tests the size method
  void testSize(Tester t) {
    this.init();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 4);
    t.checkExpect(this.deque4.size(), 3);
  }

  // tests the sizeStarter method
  void testSizeStarter(Tester t) {
    this.init();
    t.checkExpect(this.deque1.header.sizeStarter(), 0);
    t.checkExpect(this.deque2.header.sizeStarter(), 4);
    t.checkExpect(this.deque3.header.sizeStarter(), 4);
    t.checkExpect(this.deque4.header.sizeStarter(), 3);
  }

  // tests the addAtHead method
  void testAddAtHead(Tester t) {
    this.init();
    this.deque1.addAtHead("aaa");
    t.checkExpect(this.deque1.removeFromHead(), "aaa");
    this.deque2.addAtHead("zbc");
    t.checkExpect(this.deque2.removeFromHead(), "zbc");
  }

  // tests the addAtTail method
  void testAddAtTail(Tester t) {
    this.init();
    this.deque1.addAtTail("aaa");
    t.checkExpect(this.deque1.removeFromTail(), "aaa");
    this.deque2.addAtTail("zbc");
    t.checkExpect(this.deque2.removeFromTail(), "zbc");
  }

  // tests the removeFromHead method
  void testRemoveFromHead(Tester t) {
    this.init();
    t.checkException(new RuntimeException("List is Empty"), this.deque1, "removeFromHead");
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.deque2.removeFromHead(), "bcd");
    t.checkExpect(this.deque2.removeFromHead(), "cde");
    t.checkExpect(this.deque2.removeFromHead(), "def");
    t.checkException(new RuntimeException("List is Empty"), this.deque2, "removeFromHead");
    t.checkException(new RuntimeException("List is Empty"), this.sentinelNode, "removeFromHead");
  }

  // tests the removeFromTail method
  void testRemoveFromTail(Tester t) {
    this.init();
    t.checkException(new RuntimeException("List is Empty"), this.deque1, "removeFromTail");
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.deque2.removeFromTail(), "cde");
    t.checkExpect(this.deque2.removeFromTail(), "bcd");
    t.checkExpect(this.deque2.removeFromTail(), "abc");
    t.checkException(new RuntimeException("List is Empty"), this.deque2, "removeFromTail");
    t.checkException(new RuntimeException("List is Empty"), this.sentinelNode, "removeFromHead");
  }

  // tests the find method

  // tests the findStarter method

  // tests the isEmpty method

  // tests the add method

  // tests the remove method

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

  // tests the removeEdge method

  // tests the hasEdgeTo method

  // tests the getNeighbors method

  // tests the getEdgeTo method

  // tests the clearEdges method

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

  // tests the getFirst method
  void testGetFirst(Tester t) {

  }

  // tests the getLast method
  void testGetLast(Tester t) {

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
    t.checkExpect(this.edgeWeightedGraph.getTotalEdges().get(4), this.expectedDirectedEdge3);
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
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node3), true);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node1), true);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node4), false);
    this.edgeWeightedGraph.addNode(this.node4);
    this.edgeWeightedGraph.addEdge(this.node1, this.node4, 40);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node4), false);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node4, this.node1), true);
  }

  // tests the getTotalEdges method
  void testGetTotalEdges(Tester t) {
    this.init();
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 160);
    t.checkExpect(this.testingMaze2.world.getTotalEdges().size(), 38);
  }

  // tests the assignNewEdges method
  void testAssignNewEdges(Tester t) {
    this.init();
    this.testingMaze.world.addNode(this.node1);
    this.testingMaze.world.addNode(this.node3);
    this.testingMaze.world.addNode(this.node4);
    this.testingMaze.world.assignNewEdges(this.edges);
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 6);
  }

  // tests the resetColors method
  void testResetColors(Tester t) {
    this.init();
    this.testingMaze.world.resetColors();
    for (int i = 0; i < this.testingMaze.world.nodes.size(); i++) {
      if (i == 0) {
        t.checkExpect(this.testingMaze.world.get(i).c, Color.green);
        continue;
      }
      else if (i == this.testingMaze.world.nodes.size() - 1) {
        t.checkExpect(this.testingMaze.world.get(i).c, Color.pink);
        continue;
      }
      t.checkExpect(this.testingMaze.world.get(i).c, Color.gray);
    }
  }

  // tests the makeScene method
  void testMakeScene(Tester t) {
    this.init();
    this.testingMaze.makeScene();
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 160);
    t.checkExpect(this.testingMaze2.world.getTotalEdges().size(), 38);
    t.checkExpect(this.testingMaze.world.nodes.size(), 81);
    t.checkExpect(this.testingMaze2.world.nodes.size(), 20);
  }

  // tests the onTick method
  void testOnTick(Tester t) {
    this.init();
    this.testingMaze.onTick();
    t.checkExpect(this.testingMaze.waterfall.size(), 0);
    
  }

  // tests the onKeyEvent method
  void testOnKeyEvent(Tester t) {
    this.init();
    this.testingMaze.onKeyEvent("d");
    t.checkNumRange(this.testingMaze.waterfall.size(), 40, 200);
  }

  // tests the init method
  void testInit(Tester t) {
    this.init();
    this.testingMaze.init(10, 10);
    t.checkExpect(this.testingMaze.dimX, 10);
    t.checkExpect(this.testingMaze.dimY, 10);
    t.checkExpect(this.testingMaze.world.nodes.size(), 100);
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 198);
  }

  // tests the search method
  void testSearch(Tester t) {
    this.init();
    this.testingMaze.world.addNode(this.node1);
    this.testingMaze.world.addNode(this.node3);
    this.testingMaze.world.addNode(this.node4);
    this.testingMaze.world.assignNewEdges(this.edges);
    t.checkExpect(this.testingMaze.search(node3, node1, new Queue<Node<Integer>>()).keySet().size(), 2);
  }

  // tests the makeMaze method
  void testMakeMaze(Tester t) {
    this.init();
    // check if (dimX * dimY - 1) * 2 = the size because undirected edges are just
    // two directed edges
    this.testingMaze.makeMaze();
    t.checkExpect(this.testingMaze.world.getTotalEdges().size(), 160);
    t.checkExpect(this.testingMaze2.world.getTotalEdges().size(), 38);
  }

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