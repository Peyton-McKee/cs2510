import java.util.ArrayList;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Node<T> {
  T value;
  int x;
  int y;
  Color c;
  private ArrayList<DirectedEdge<T>> edges = new ArrayList<DirectedEdge<T>>();
  
  public Node(T value, int x, int y, Color c) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.c = c;
  }
  
  public void addEdge(DirectedEdge<T> edge) {
    this.edges.add(edge);
  }
  
  public WorldImage draw() {
    return new RectangleImage(this.x + Maze.NODE_SIZE, this.y + Maze.NODE_SIZE, OutlineMode.SOLID, this.c);
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
    this.weight = weight;
  }
}

class EdgeWeightedGraph<T> {
  private ArrayList<Node<T>> nodes = new ArrayList<Node<T>>();
  
  void addNode(Node<T> node) {
    this.nodes.add(node);
  }
  
  void addEdge(Node<T> src, Node<T> destination, int weight) {
    DirectedEdge<T> edge = new DirectedEdge<T>(src, destination, weight);
    DirectedEdge<T> edge2 = new DirectedEdge<T>(destination, src, weight);
    src.addEdge(edge);
    destination.addEdge(edge2);
  }
  
  WorldImage draw() {
    WorldImage img = new RectangleImage(0, 0, OutlineMode.OUTLINE, Color.white);
    for (Node<T> node: nodes) {
      img = new OverlayImage(img, node.draw());
    }
    return img;
  }
}

class Maze extends World {

  static int NODE_SIZE;
  static int SCREEN_SIZE = 500;
  EdgeWeightedGraph<Integer> world = new EdgeWeightedGraph<Integer>();
  
  public Maze(int dimX, int dimY) {
    NODE_SIZE = (SCREEN_SIZE * 2) / Math.max(dimX, dimY);
    for (int i = 0; i < dimX; i++) {
      for (int j = 0; j < dimY; j++) {
        Color c = Color.gray;
        
        if (i == 0 && j == 0) {
          c = Color.green;
        }
        else if (i == dimX - 1 && j == dimY - 1f) {
          c = Color.pink;
        }
        world.addNode(new Node<Integer>(((i * dimY) + j), i * NODE_SIZE, j * NODE_SIZE, c));
      }
    }
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
}

class ExamplesMaze {
  
  World starterWorld = new Maze(10, 10);
  
  void testWorld(Tester t) {
    this.starterWorld.bigBang(Maze.SCREEN_SIZE, Maze.SCREEN_SIZE);
  }
}