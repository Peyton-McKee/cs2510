import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

// Represents a node
class Node<T> {
  T value;
  int x;
  int y;
  Color c;

  // Constructor
  public Node(T value, int x, int y, Color c) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.c = c;
  }

  // Draws the node
  public WorldImage draw() {
    return new RectangleImage(Maze.NODE_SIZE, Maze.NODE_SIZE, OutlineMode.SOLID, this.c);
  }

  // Sets the color of the node
  public void setColor(Color c) {
    this.c = c;
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
  public WorldImage draw() {
    if (this.src.x == this.dest.x) {
      return this.drawHorizontalEdge();
    }
    else {
      return this.drawVerticalEdge();
    }
  }

  // Draws a vertical edge
  public WorldImage drawVerticalEdge() {
    return new RectangleImage(1, Maze.NODE_SIZE, OutlineMode.OUTLINE, Color.red);
  }

  // draws a horizontal edge
  public WorldImage drawHorizontalEdge() {
    return new RectangleImage(Maze.NODE_SIZE, 1, OutlineMode.OUTLINE, Color.red);
  }
}

// Represents an edge weighted graph
class EdgeWeightedGraph<T> {
  ArrayList<Node<T>> nodes;
  ArrayList<DirectedEdge<T>> edges;

  // Constructor
  public EdgeWeightedGraph() {
    this.nodes = new ArrayList<Node<T>>();
    this.edges = new ArrayList<DirectedEdge<T>>();
  }

  // Adds a node that is given to the graph
  void addNode(Node<T> node) {
    this.nodes.add(node);
  }

  // Adds an edge that is given to the graph
  void addEdge(Node<T> src, Node<T> destination, int weight) {
    DirectedEdge<T> edge = new DirectedEdge<T>(destination, src, weight);
    this.edges.add(edge);
  }

  // Gets the node at the index given
  Node<T> get(int index) {
    return this.nodes.get(index);
  }

  // Determines if there is an edge between these two nodes
  boolean edgeExistsFor(Node<T> node1, Node<T> node2) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.src.value == node1.value && edge.dest.value == node2.value) {
        return true;
      }
    }
    return false;
  }
}

// Represents a maze
class Maze extends World {

  static int NODE_SIZE;
  static int SCREEN_SIZE = 500;
  EdgeWeightedGraph<Integer> world = new EdgeWeightedGraph<Integer>();
  Random rand = new Random();
  int dimX;
  int dimY;

  // Constructor
  public Maze(int dimX, int dimY) {
    this.dimX = dimX;
    this.dimY = dimY;
    NODE_SIZE = (SCREEN_SIZE) / Math.max(dimX, dimY);
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
    this.world.edges = this.makeMaze();
  }

  // Makes the scene
  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    for (int row = 0; row < this.dimY; row++) {
      for (int col = 0; col < this.dimX; col++) {
        Node<Integer> node = this.world.nodes.get(row * dimX + col);
        int x = node.x * NODE_SIZE;
        int y = node.y * NODE_SIZE;
        background.placeImageXY(node.draw(), x + NODE_SIZE / 2, y + NODE_SIZE / 2);

        if (col < this.dimX - 1
            && !this.world.edgeExistsFor(node, this.world.nodes.get(row * dimX + col + 1))) {
          background.placeImageXY(new RectangleImage(NODE_SIZE, 1, OutlineMode.SOLID, Color.red),
              x + NODE_SIZE / 2, y + NODE_SIZE);
        }

        if (row < this.dimY - 1
            && !this.world.edgeExistsFor(node, this.world.nodes.get(row * dimX + col + dimX))) {
          background.placeImageXY(new RectangleImage(1, NODE_SIZE, OutlineMode.SOLID, Color.red),
              x + NODE_SIZE, y + NODE_SIZE / 2);
        }
      }
    }
    return background;
  }

  // Runs the big bang based on a tick value
  @Override
  public void onTick() {

  }

  // Resets the game with the 'r' key
  @Override
  public void onKeyEvent(String k) {
  }

  // Makes the maze randomly
  ArrayList<DirectedEdge<Integer>> makeMaze() {
    HashMap<Node<Integer>, Node<Integer>> representatives = new HashMap<Node<Integer>, Node<Integer>>();
    ArrayList<DirectedEdge<Integer>> edgesInTree = new ArrayList<DirectedEdge<Integer>>();
    ArrayList<DirectedEdge<Integer>> worklist = new ArrayList<DirectedEdge<Integer>>(
        this.world.edges);
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
    return edgesInTree;
  }

  // Finds the representative determined by the hash map
  Node<Integer> findRepresentative(HashMap<Node<Integer>, Node<Integer>> representatives,
      Node<Integer> start) {
    while (!(representatives.get(start) == start)) {
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

  World starterWorld;
  World testerWorld2;
  Maze testingMaze;
  Maze testingMaze2;
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
    this.starterWorld = new Maze(5, 5);
    this.testerWorld2 = new Maze(5, 5);
    this.testingMaze = new Maze(9, 9);
    this.testingMaze2 = new Maze(4, 5);
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
    t.checkExpect(this.node1.draw(), new RectangleImage(100, 100, OutlineMode.SOLID, Color.blue));
    t.checkExpect(this.node2.draw(), new RectangleImage(100, 100, OutlineMode.SOLID, Color.red));
    t.checkExpect(this.node3.draw(), new RectangleImage(100, 100, OutlineMode.SOLID, Color.black));

    t.checkExpect(this.directedEdge.draw(),
        new RectangleImage(100, 1, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge1.draw(),
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
    t.checkExpect(this.expectedDirectedEdge1.drawVerticalEdge(),
        new RectangleImage(1, 100, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge2.drawVerticalEdge(),
        new RectangleImage(1, 100, OutlineMode.OUTLINE, Color.red));
  }

  // tests the drawHorizontalEdge method
  void testDrawHorizontalEdge(Tester t) {
    this.init();
    t.checkExpect(this.expectedDirectedEdge1.drawHorizontalEdge(),
        new RectangleImage(100, 1, OutlineMode.OUTLINE, Color.red));
    t.checkExpect(this.expectedDirectedEdge2.drawHorizontalEdge(),
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
    t.checkExpect(this.edgeWeightedGraph.edges.size(), 0);
    this.edgeWeightedGraph.addEdge(this.node1, this.node3, 4);
    t.checkExpect(this.edgeWeightedGraph.edges.get(0), this.expectedDirectedEdge2);
    this.edgeWeightedGraph.addEdge(this.node3, this.node1, 10);
    t.checkExpect(this.edgeWeightedGraph.edges.get(1), this.expectedDirectedEdge1);
    this.edgeWeightedGraph.addEdge(this.node3, this.node4, 25);
    t.checkExpect(this.edgeWeightedGraph.edges.get(2), this.expectedDirectedEdge3);
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
    this.edgeWeightedGraph.addEdge(this.node1, this.node3, 4);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node3), false);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node1), true);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node1, this.node4), false);
    this.edgeWeightedGraph.addEdge(this.node1, this.node4, 40);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node3, this.node4), false);
    t.checkExpect(this.edgeWeightedGraph.edgeExistsFor(this.node4, this.node1), true);
  }

  // tests the makeScene method
  void testMakeScene(Tester t) {
    this.init();
    this.testingMaze.makeScene();
    t.checkExpect(this.testingMaze.world.edges.size(), 80);
    t.checkExpect(this.testingMaze2.world.edges.size(), 19);
    t.checkExpect(this.testingMaze.world.nodes.size(), 81);
    t.checkExpect(this.testingMaze2.world.nodes.size(), 20);
  }

  // tests the makeMaze method
  void testMakeMaze(Tester t) {
    this.init();
    // check if dimX * dimY - 1 = the size
    t.checkExpect(this.testingMaze.makeMaze().size(), 80);
    t.checkExpect(this.testingMaze2.makeMaze().size(), 19);
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