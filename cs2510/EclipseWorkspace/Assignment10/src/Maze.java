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
    return new RectangleImage(this.x + Maze.NODE_SIZE, this.y + Maze.NODE_SIZE, OutlineMode.SOLID,
        this.c);
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
//    System.out.println(this.dest.x);
//    System.out.println(this.dest.y);
    return new RectangleImage(this.src.x, this.src.y, OutlineMode.OUTLINE,
        Color.red);
  }
}

class EdgeWeightedGraph<T> {
  ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
  ArrayList<DirectedEdge<T>> edges = new ArrayList<DirectedEdge<T>>();

  void addNode(Node<T> node) {
    this.nodes.add(node);
  }

  void addEdge(Node<T> src, Node<T> destination, int weight) {
    DirectedEdge<T> edge = new DirectedEdge<T>(src, destination, weight);
    DirectedEdge<T> edge2 = new DirectedEdge<T>(destination, src, weight);
    this.edges.add(edge);
    this.edges.add(edge2);
  }

  WorldImage draw() {
    WorldImage img = new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.white);
    for (Node<T> node : nodes) {
      img = new OverlayImage(img, node.draw());
    }
    for (DirectedEdge<T> edge : edges) {
      img = new OverlayImage(edge.draw(), img);
    }
    return img;
  }

  Node<T> get(int index) {
    return this.nodes.get(index);
  }
}

class Maze extends World {

  static int NODE_SIZE;
  static int SCREEN_SIZE = 500;
  EdgeWeightedGraph<Integer> world = new EdgeWeightedGraph<Integer>();
  Random rand = new Random();

  public Maze(int dimX, int dimY) {
    NODE_SIZE = (SCREEN_SIZE * 2) / Math.max(dimX, dimY);
    for (int i = 0; i < dimX; i++) {
      for (int j = 0; j < dimY; j++) {
        Color c = Color.gray;

        if (i == 0 && j == 0) {
          c = Color.green;
        }
        else if (i == dimX - 1 && j == dimY - 1) {
          c = Color.pink;
        }

        Node<Integer> newNode = new Node<Integer>(((i * dimY) + j), i * NODE_SIZE, j * NODE_SIZE,
            c);
        this.world.addNode(newNode);

        if (i != 0) {
          Node<Integer> top = this.world.get((i * dimY) - dimY + j);
          this.world.addEdge(top, newNode, rand.nextInt(100));
        }
        else if (j != 0) {
          Node<Integer> left = this.world.get(i * dimY + j - 1);
          this.world.addEdge(newNode, left, rand.nextInt(100));
        }
      }
    }
    this.world.edges = this.makeMaze();
  }

  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    background.placeImageXY(this.world.draw(), 0, 0);
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
    while (this.existsDifferentRepresentatives(representatives) && !worklist.isEmpty()) {
      DirectedEdge<Integer> nextEdge = worklist.get(0);
      if (this.findRepresentative(representatives, representatives.get(nextEdge.src.value)) == this
          .findRepresentative(representatives, representatives.get(nextEdge.dest.value))) {
        worklist.remove(nextEdge);
      }
      else {
//        System.out.println("test2");
//        System.out.println(this.findRepresentative(representatives, nextEdge.src.value));
//        System.out.println(representatives.get(nextEdge.src.value));
//        System.out.println(this.findRepresentative(representatives, nextEdge.dest.value));
        edgesInTree.add(nextEdge);
        representatives.put(this.findRepresentative(representatives, nextEdge.src.value),
            this.findRepresentative(representatives, nextEdge.dest.value));
      }
    }
    return edgesInTree;
  }

  boolean existsDifferentRepresentatives(HashMap<Integer, Integer> representatives) {
    int firstRep = representatives.get(0);
    for (int rep : representatives.values()) {
      if (rep != firstRep) {
        return true;
      }
    }
    return false;
  }

  int findRepresentative(HashMap<Integer, Integer> representatives, int start) {
    if (representatives.get(start) == start) {
      return start;
    }
    return this.findRepresentative(representatives, representatives.get(start));
  }
}

class WeightComparator implements Comparator<DirectedEdge<Integer>> {

  @Override
  public int compare(DirectedEdge<Integer> o1, DirectedEdge<Integer> o2) {
    // TODO Auto-generated method stub
    if (o1.weight > o2.weight) {
      return 1;
    }
    if (o2.weight > o1.weight) {
      return -1;
    }
    return 0;
  }

}

class ExamplesMaze {

  World starterWorld = new Maze(10, 10);

  void testWorld(Tester t) {
    this.starterWorld.bigBang(Maze.SCREEN_SIZE, Maze.SCREEN_SIZE);
  }
}