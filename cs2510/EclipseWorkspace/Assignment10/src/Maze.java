import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.Random;

class Node<T> {
  T value;
  int x;
  int y;
  Color c;

  public Node(T value, int x, int y, Color c) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.c = c;
  }

//  public void addEdge(DirectedEdge<T> edge) {
//    this.edges.add(edge);
//  }
//  
//  public void removeEdge(DirectedEdge<T> edge) {
//    this.edges.remove(edge);
//  }

  public WorldImage draw() {
    return new RectangleImage(Maze.NODE_SIZE, Maze.NODE_SIZE, OutlineMode.SOLID, this.c);
  }

  public void setColor(Color c) {
    this.c = c;
  }
}

class DirectedEdge<T> {
  int weight;
  Node<T> src;
  Node<T> dest;

  public DirectedEdge(Node<T> src, Node<T> dest, int weight) {
    this.src = src;
    this.dest = dest;
    this.weight = weight;
  }

  public WorldImage draw() {
    if (this.src.x == this.dest.x) {
      return this.drawHorizontalEdge();
    }
    else {
      return this.drawVerticalEdge();
    }
  }

  public WorldImage drawVerticalEdge() {
    return new RectangleImage(1, Maze.NODE_SIZE, OutlineMode.OUTLINE, Color.red);
  }

  public WorldImage drawHorizontalEdge() {
    return new RectangleImage(Maze.NODE_SIZE, 1, OutlineMode.OUTLINE, Color.red);
  }
}

class EdgeWeightedGraph<T> {
  ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
  ArrayList<DirectedEdge<T>> edges = new ArrayList<DirectedEdge<T>>();

  void addNode(Node<T> node) {
    this.nodes.add(node);
  }

  void addEdge(Node<T> src, Node<T> destination, int weight) {
    DirectedEdge<T> edge = new DirectedEdge<T>(destination, src, weight);
    this.edges.add(edge);
  }

  Node<T> get(int index) {
    return this.nodes.get(index);
  }

  boolean edgeExistsFor(Node<T> node1, Node<T> node2) {
    for (DirectedEdge<T> edge : this.edges) {
      if (edge.src.value == node1.value && edge.dest.value == node2.value) {
        return true;
      }
    }
    return false;
  }
}

class Maze extends World {

  static int NODE_SIZE;
  static int SCREEN_SIZE = 500;
  EdgeWeightedGraph<Integer> world = new EdgeWeightedGraph<Integer>();
  Random rand = new Random();
  int dimX;
  int dimY;

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
          this.world.addEdge(top, newNode, rand.nextInt());
        }
        if (j != 0) {
          Node<Integer> left = this.world.get(i * dimX + j - 1);
          this.world.addEdge(newNode, left, rand.nextInt());
        }
      }
    }
    this.world.edges = this.makeMaze();
  }

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
          background.placeImageXY(new RectangleImage(1, NODE_SIZE, OutlineMode.SOLID, Color.red),
              x, y + NODE_SIZE / 2);
        }

        if (row < this.dimY - 1
            && !this.world.edgeExistsFor(node, this.world.nodes.get(row * dimX + 1 + col))) {
          background.placeImageXY(new RectangleImage(NODE_SIZE, 1, OutlineMode.SOLID, Color.red),
              x  + NODE_SIZE / 2, y);
        }
      }
    }
    return background;
  }

  @Override
  public void onTick() {

  }

  @Override
  public void onKeyEvent(String k) {

  }

  ArrayList<DirectedEdge<Integer>> makeMaze() {
    HashMap<Integer, Integer> representatives = new HashMap<Integer, Integer>();
    ArrayList<DirectedEdge<Integer>> edgesInTree = new ArrayList<DirectedEdge<Integer>>();
    ArrayList<DirectedEdge<Integer>> worklist = new ArrayList<DirectedEdge<Integer>>(
        this.world.edges);
    worklist.sort(new WeightComparator());
    for (Node<Integer> node : this.world.nodes) {
      representatives.put(node.value, node.value);
    }
    System.out.println(worklist.size());
    while (edgesInTree.size() < this.dimX * this.dimY - 1) {
      DirectedEdge<Integer> nextEdge = worklist.get(0);
      
      if (this.findRepresentative(representatives,
          representatives.get(nextEdge.src.value)) == this.findRepresentative(representatives,
              representatives.get(nextEdge.dest.value))) {
        worklist.remove(nextEdge);
      } else 
//        System.out.println("test2");
//        System.out.println(this.findRepresentative(representatives, nextEdge.src.value));
//        System.out.println(representatives.get(nextEdge.src.value));
//        System.out.println(this.findRepresentative(representatives, nextEdge.dest.value));
        edgesInTree.add(nextEdge);
        representatives.put(this.findRepresentative(representatives, nextEdge.dest.value),
            this.findRepresentative(representatives, nextEdge.src.value));
      }
    System.out.println(edgesInTree.size());
    return edgesInTree;
  }

  int findRepresentative(HashMap<Integer, Integer> representatives, int start) {
    while (!(representatives.get(start) == start)) {
      start = representatives.get(start);
    }
    return start;
  }
}

class WeightComparator implements Comparator<DirectedEdge<Integer>> {
  @Override
  public int compare(DirectedEdge<Integer> o1, DirectedEdge<Integer> o2) {
    return Integer.compare(o1.weight, o2.weight);
  }
}

class ExamplesMaze {

  World starterWorld = new Maze(10, 10);

  void testWorld(Tester t) {
    this.starterWorld.bigBang(Maze.SCREEN_SIZE, Maze.SCREEN_SIZE);
  }
}