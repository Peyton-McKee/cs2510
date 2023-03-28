import java.util.ArrayList;
import java.util.Random;
import tester.Tester;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  public Cell(int x, int y, Color c) {
    this.x = x;
    this.y = y;
    this.color = c;
  }

  public WorldImage drawCell() {
    return new RectangleImage(this.x + FloodItWorld.CELL_SIZE, this.y + FloodItWorld.CELL_SIZE,
        OutlineMode.SOLID, this.color);
  }

  public ArrayList<Cell> floodNeighborsWithColor(Color c) {
    this.color = c;
    ArrayList<Cell> neighborsWithSameColor = new ArrayList<Cell>(4);
    if (this.left != null && this.left.isColor(c)) {
      neighborsWithSameColor.add(this.left);
    }
    if (this.right != null && this.right.isColor(c)) {
      neighborsWithSameColor.add(this.right);
    }
    if (this.top != null && this.top.isColor(c)) {
      neighborsWithSameColor.add(this.top);
    }
    if (this.bottom != null && this.bottom.isColor(c)) {
      System.out.println("bottom Check");
      neighborsWithSameColor.add(this.bottom);
    }
    return neighborsWithSameColor;
  }

  private boolean isColor(Color c) {
    return this.color.equals(c);
  }
}

class FloodItWorld extends World {
  // All the cells of the game
  static int SCREEN_SIZE = 400;
  static int CELL_SIZE;

  @SuppressWarnings("serial")
  ArrayList<Color> colors = new ArrayList<Color>(8) {
    {
      add(Color.blue);
      add(Color.green);
      add(Color.red);
      add(Color.black);
      add(Color.orange);
      add(Color.yellow);
      add(Color.cyan);
      add(Color.pink);
    }
  };
  int attempts;
  ArrayList<Cell> flooded;
  ArrayList<Cell> board;
  WorldImage world;

  public FloodItWorld(int size, int numColors) {
    this.makeBoard(size, numColors);
  }

  private void makeBoard(int size, int numColors) {
    CELL_SIZE = (SCREEN_SIZE / size) * 2;
    this.board = new ArrayList<Cell>(size * size);
    this.attempts = size * numColors;
    this.flooded = new ArrayList<Cell>(size * size);
    Random random = new Random();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Color color = colors.get(random.nextInt(numColors));
        Cell newCell = new Cell(j * CELL_SIZE, i * CELL_SIZE, color);
        if (i != 0) {
          System.out.println("Adding top and bottom cells");
          Cell top = this.board.get((i * size) - size + j);
          newCell.top = top;
          top.bottom = newCell;
        }
        if (j != 0) {
          Cell left = this.board.get(i * size + j - 1);
          newCell.left = left;
          left.right = newCell;
        }
        this.board.add(newCell);
      }
    }
    this.flooded.add(this.board.get(0));
    this.drawWorld();
  }

  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    background.placeImageXY(this.world, 0, 0);
    return background;
  }

  @Override
  public void onKeyEvent(String k) {
    if (k.equals("r")) {
      this.reset();
    }
  }

  @Override
  public void onMouseClicked(Posn p) {
    Color c = this.flooded.get(0).color;
    for (Cell cell : this.board) {
      if (Math.abs(cell.x - (p.x * 2)) <= CELL_SIZE && Math.abs(cell.y - (p.y * 2)) <= CELL_SIZE) {
        c = cell.color;
        break;
      }
    }
    this.floodWithColor(c);
  }

  @Override
  public void onTick() {
    this.drawWorld();
  }

  private void floodWithColor(Color c) {
    if (this.flooded.get(0).color.equals(c)) {
      return;
    }
    ArrayList<Cell> temp = new ArrayList<Cell>(this.flooded);
    for (Cell cell : this.flooded) {
      for (Cell neighbor : cell.floodNeighborsWithColor(c)) {
        System.out.println("Adding cell to flood");
        temp.add(neighbor);
      }
    }
    this.flooded = filterUnique(temp);
    System.out.println(this.flooded);
  }

  private ArrayList<Cell> filterUnique(ArrayList<Cell> arr) {
    ArrayList<Cell> unique = new ArrayList<Cell>(arr.size());
    for (Cell cell : arr) {
      if (unique.contains(cell)) {
        continue;
      }
      unique.add(cell);
    }
    return unique;
  }

  private void reset() {
    Random random = new Random();
    int size = random.nextInt(49) + 1;
    int numColors = random.nextInt(7) + 1;
    this.makeBoard(size, numColors);
  }

  private void drawWorld() {
    WorldImage world = new RectangleImage(0, 0, OutlineMode.SOLID, Color.black);
    for (Cell cell : this.board) {
      world = new OverlayImage(world, cell.drawCell());
    }
    this.world = world;
  }
}

class ExamplesFloodIt {
  FloodItWorld starterWorld = new FloodItWorld(5, 4);

  void testWorld(Tester t) {
    this.starterWorld.bigBang(FloodItWorld.SCREEN_SIZE, FloodItWorld.SCREEN_SIZE, 1);
  }
}
