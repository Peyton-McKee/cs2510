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

  public ArrayList<Cell> floodNeighborsWithColor(Color c, ArrayList<Cell> seen) {
    ArrayList<Cell> neighborsWithSameColor = new ArrayList<Cell>(4);
    
    this.floodNeighborsForCell(this.left, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.right, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.bottom, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.top, c, seen, neighborsWithSameColor);

    return neighborsWithSameColor;
  }

  private void floodNeighborsForCell(Cell cell, Color c, ArrayList<Cell> seen,
      ArrayList<Cell> neighborsWithSameColor) {
    if (cell != null && cell.isColor(c) && !seen.contains(cell)) {
      neighborsWithSameColor.add(cell);
      seen.addAll(neighborsWithSameColor);
      for (Cell cellInstance : cell.floodNeighborsWithColor(c, seen)) {
        neighborsWithSameColor.add(cellInstance);
      }
    }
  }

  private boolean isColor(Color c) {
    return this.color.equals(c);
  }
  
  public void setColor(Color c) {
    this.color = c;
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
  ArrayList<Cell> waterfalled;
  ArrayList<Cell> board;
  WorldImage world;
  
  boolean isFlooding = false;
  
  Color selectedColor; 

  public FloodItWorld(int size, int numColors) {
    this.makeBoard(size, numColors);
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
    if (!this.isFlooding) {
      Color c = this.flooded.get(0).color;
      for (Cell cell : this.board) {
        if (Math.abs(cell.x - (p.x * 2)) <= CELL_SIZE && Math.abs(cell.y - (p.y * 2)) <= CELL_SIZE) {
          c = cell.color;
          break;
        }
      }
      this.selectedColor = c;
      this.floodWithColor(c);
      this.isFlooding = true;
    }
  }

  @Override
  public void onTick() {
    if (this.isFlooding) {
      ArrayList<Cell> temp = new ArrayList<Cell>(this.waterfalled);
      for (Cell cell: temp) {
        this.waterfallCell(cell);
      }
      if (this.waterfalled.size() == this.flooded.size()) {
        this.isFlooding = false;
        this.waterfalled = new ArrayList<Cell>(this.flooded.size());
      }
    }
    this.drawWorld();
  }
  
  private void waterfallCell(Cell cell) {
    cell.color = this.selectedColor;
    this.setColorIfFlooded(cell.left);
    this.setColorIfFlooded(cell.right);
    this.setColorIfFlooded(cell.top);
    this.setColorIfFlooded(cell.bottom);
  }
  
  private void setColorIfFlooded(Cell cell) {
    if (this.flooded.contains(cell) && !this.waterfalled.contains(cell)) {
      cell.setColor(this.selectedColor);
      this.waterfalled.add(cell);
    }
  }
  
  private void makeBoard(int size, int numColors) {
    CELL_SIZE = (SCREEN_SIZE / size) * 2;
    this.board = new ArrayList<Cell>(size * size);
    this.attempts = size * numColors;
    this.flooded = new ArrayList<Cell>(size * size);
    this.waterfalled = new ArrayList<Cell>(size * size);
    Random random = new Random();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        Color color = colors.get(random.nextInt(numColors));
        Cell newCell = new Cell(j * CELL_SIZE, i * CELL_SIZE, color);
        if (i != 0) {
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
    Cell topLeft = this.board.get(0);
    this.flooded.add(topLeft);
    this.selectedColor = topLeft.color;
    this.drawWorld();
  }

  private void floodWithColor(Color c) {
    if (this.flooded.get(0).color.equals(c)) {
      return;
    }
    ArrayList<Cell> seen = new ArrayList<Cell>(this.flooded);
    ArrayList<Cell> temp = new ArrayList<Cell>(this.flooded);
    for (Cell cell : this.flooded) {
      for (Cell neighbor : cell.floodNeighborsWithColor(c, seen)) {
        temp.add(neighbor);
      }
    }
    this.flooded = filterUnique(temp);
    this.waterfalled.add(this.flooded.get(0));
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
