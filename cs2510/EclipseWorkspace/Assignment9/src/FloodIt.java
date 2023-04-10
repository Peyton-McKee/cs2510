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

  // Constructor
  public Cell(int x, int y, Color c) {
    this.x = x;
    this.y = y;
    this.color = c;
  }

  // Draws the cell and outputs the correct image
  public WorldImage drawCell() {
    return new RectangleImage(this.x + FloodItWorld.CELL_SIZE, this.y + FloodItWorld.CELL_SIZE,
        OutlineMode.SOLID, this.color);
  }

  // Creates the array list of cells that are neighbors and are the same color
  ArrayList<Cell> floodNeighborsWithColor(Color c, ArrayList<Cell> seen) {
    ArrayList<Cell> neighborsWithSameColor = new ArrayList<Cell>(4);

    this.floodNeighborsForCell(this.left, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.right, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.bottom, c, seen, neighborsWithSameColor);
    this.floodNeighborsForCell(this.top, c, seen, neighborsWithSameColor);

    return neighborsWithSameColor;
  }

  // Floods the neighbors with the same color
  void floodNeighborsForCell(Cell cell, Color c, ArrayList<Cell> seen,
      ArrayList<Cell> neighborsWithSameColor) {
    if (cell != null && cell.isColor(c) && !seen.contains(cell)) {
      neighborsWithSameColor.add(cell);
      seen.addAll(neighborsWithSameColor);
      for (Cell cellInstance : cell.floodNeighborsWithColor(c, seen)) {
        neighborsWithSameColor.add(cellInstance);
      }
    }
  }

  // Determines if the cell is the color given
  boolean isColor(Color c) {
    return this.color.equals(c);
  }

  // Sets the color of the cell to the color given
  void setColor(Color c) {
    this.color = c;
  }
}

// Represents the board and the big bang
class FloodItWorld extends World {
  // All the cells of the game
  static int SCREEN_SIZE = 400;
  static int CELL_SIZE;
  int size;
  int numColors;
  Random rand;

  // Array of the colors of the game
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
  int currAttempts;
  ArrayList<Cell> flooded;
  ArrayList<Cell> waterfalled;
  ArrayList<Cell> board;
  WorldImage world;

  boolean isFlooding;
  boolean isOver;

  Color selectedColor;

  // Constructor with seed
  public FloodItWorld(int size, int numColors, Random rand) {
    this.size = size;
    this.numColors = numColors;
    this.rand = rand;
    this.attempts = (numColors + size);
    this.currAttempts = 0;
    this.makeBoard(size, numColors, rand);
  }

  // Constructor without seed
  public FloodItWorld(int size, int numColors) {
    this(size, numColors, new Random());
  }

  // Makes the scene
  @Override
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCREEN_SIZE, SCREEN_SIZE);
    background.placeImageXY(this.world, 0, 0);
    if (this.isOver) {
      if (this.flooded.size() == this.board.size()) {
        background.placeImageXY(new TextImage("You won in " + this.currAttempts + " attempts!", 35,
            FontStyle.BOLD, Color.white), SCREEN_SIZE / 2, SCREEN_SIZE / 2);
      }
      else {
        background.placeImageXY(new TextImage("YOU LOSE", 50, FontStyle.BOLD, Color.white),
            SCREEN_SIZE / 2, SCREEN_SIZE / 2);
      }
      this.isFlooding = true;
    }
    return background;
  }

  // Calls the reset if the r key is pressed
  @Override
  public void onKeyEvent(String k) {
    if (k.equals("r")) {
      this.reset();
    }
  }

  // Starts the flooding if the mouse is clicked
  @Override
  public void onMouseClicked(Posn p) {
    if (!this.isFlooding) {
      Color c = this.flooded.get(0).color;
      for (Cell cell : this.board) {
        if (Math.abs(cell.x - (p.x * 2)) <= CELL_SIZE
            && Math.abs(cell.y - (p.y * 2)) <= CELL_SIZE) {
          c = cell.color;
          break;
        }
      }
      if (this.selectedColor == c) {
        return;
      }
      this.selectedColor = c;
      this.floodWithColor(c);
      this.isFlooding = true;

      if (this.currAttempts > this.attempts) {
        this.isOver = true;
      }
      this.currAttempts++;
    }
  }

  // Floods the board on tick
  @Override
  public void onTick() {
    if (this.isFlooding) {
      ArrayList<Cell> temp = new ArrayList<Cell>(this.waterfalled);
      for (Cell cell : temp) {
        this.waterfallCell(cell);
      }
      if (this.waterfalled.size() == this.flooded.size()) {
        this.isFlooding = false;
        this.waterfalled = new ArrayList<Cell>(this.flooded.size());
      }
    }
    if (!isFlooding
        && ((this.currAttempts >= this.attempts) || (this.flooded.size() == this.board.size()))) {
      this.isOver = true;
    }
    this.drawWorld();
  }

  // Floods the board
  public void waterfallCell(Cell cell) {
    cell.color = this.selectedColor;
    this.setColorIfFlooded(cell.left);
    this.setColorIfFlooded(cell.right);
    this.setColorIfFlooded(cell.top);
    this.setColorIfFlooded(cell.bottom);
  }

  // Sets the new color of the flooded cells
  void setColorIfFlooded(Cell cell) {
    if (this.flooded.contains(cell) && !this.waterfalled.contains(cell)) {
      cell.setColor(this.selectedColor);
      this.waterfalled.add(cell);
    }
  }

  // Makes the board and outputs the image
  public void makeBoard(int size, int numColors, Random rand) {
    CELL_SIZE = (SCREEN_SIZE / size) * 2;
    this.board = new ArrayList<Cell>(size * size);
    this.attempts = (size + numColors);
    this.currAttempts = 0;
    this.flooded = new ArrayList<Cell>(size * size);
    this.waterfalled = new ArrayList<Cell>(size * size);
    this.isOver = false;
    this.isFlooding = false;
    Random random = rand;
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

  // Floods with the color given
  void floodWithColor(Color c) {
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

  // Filters the unique cells
  ArrayList<Cell> filterUnique(ArrayList<Cell> arr) {
    ArrayList<Cell> unique = new ArrayList<Cell>(arr.size());
    for (Cell cell : arr) {
      if (unique.contains(cell)) {
        continue;
      }
      unique.add(cell);
    }
    return unique;
  }

  // Resets the board to a new state
  public void reset() {
    int size = this.size;
    int numColors = this.numColors;
    Random rand = new Random();
    this.makeBoard(size, numColors, rand);
  }

  // Draws the world
  public void drawWorld() {
    WorldImage world = new RectangleImage(0, 0, OutlineMode.SOLID, Color.black);
    for (Cell cell : this.board) {
      world = new OverlayImage(world, cell.drawCell());
    }
    this.world = world;
  }
}

class ExamplesFloodIt {

  FloodItWorld starterWorld = new FloodItWorld(5, 4, new Random(3));
  // FloodItWorld seededWorldOne = new FloodItWorld(10, 6, new Random(5));
  // FloodItWorld seededWorldTwo = new FloodItWorld(7, 3, new Random(10));
  // FloodItWorld blankWorld = new FloodItWorld(1, 1);

  WorldScene backgroundTest = new WorldScene(400, 400);

  Cell cellOne = new Cell(0, 0, Color.blue);
  Cell cellTwo = new Cell(0, 0, Color.green);
  Cell cellThree = new Cell(40, 40, Color.yellow);
  Cell cellFour = new Cell(0, 0, Color.cyan);
  Cell cellFive = new Cell(100, 100, Color.blue);

  // Constructor
  ExamplesFloodIt() {
    this.init();
  }

  void init() {
    this.starterWorld = new FloodItWorld(5, 4, new Random(3));

    this.backgroundTest = new WorldScene(400, 400);

    this.cellOne = new Cell(0, 0, Color.blue);
    this.cellTwo = new Cell(0, 0, Color.green);
    this.cellThree = new Cell(40, 40, Color.yellow);
    this.cellFour = new Cell(0, 0, Color.cyan);
    this.cellFive = new Cell(100, 100, Color.blue);
    this.cellFive.left = this.cellOne;
  }

  // tests the drawCell method
  void testDrawCell(Tester t) {
    this.init();
    t.checkExpect(this.cellOne.drawCell(), new RectangleImage(0 + FloodItWorld.CELL_SIZE,
        0 + FloodItWorld.CELL_SIZE, OutlineMode.SOLID, Color.blue));
    t.checkExpect(this.cellTwo.drawCell(), new RectangleImage(0 + FloodItWorld.CELL_SIZE,
        0 + FloodItWorld.CELL_SIZE, OutlineMode.SOLID, Color.green));
    t.checkExpect(this.cellThree.drawCell(), new RectangleImage(40 + FloodItWorld.CELL_SIZE,
        40 + FloodItWorld.CELL_SIZE, OutlineMode.SOLID, Color.yellow));
    t.checkExpect(this.cellFour.drawCell(), new RectangleImage(0 + FloodItWorld.CELL_SIZE,
        0 + FloodItWorld.CELL_SIZE, OutlineMode.SOLID, Color.cyan));
    t.checkExpect(this.cellFive.drawCell(), new RectangleImage(100 + FloodItWorld.CELL_SIZE,
        100 + FloodItWorld.CELL_SIZE, OutlineMode.SOLID, Color.blue));
  }

  // Outputs the world function with the given world
  void testWorld(Tester t) {
    this.init();
    this.starterWorld.bigBang(FloodItWorld.SCREEN_SIZE, FloodItWorld.SCREEN_SIZE, 1);
  }

  // tests the floodNeightborsWithColor method
  void testFloodNeighborsWithColor(Tester t) {
    this.init();
    this.cellFive.floodNeighborsWithColor(Color.blue, this.starterWorld.waterfalled);
    t.checkExpect(this.starterWorld.waterfalled.contains(this.cellOne), true);
  }

  // tests the floodNeighborsForCell method
  void testFloodNeighborsForCell(Tester t) {
    this.init();
    this.cellFive.floodNeighborsForCell(this.cellFive.left, Color.blue, this.starterWorld.flooded,
        this.starterWorld.waterfalled);
    t.checkExpect(this.cellOne.color, Color.blue);
  }

  // tests the isColor method
  void testIsColor(Tester t) {
    t.checkExpect(this.cellFour.isColor(Color.blue), false);
    t.checkExpect(this.cellOne.isColor(Color.blue), true);
    t.checkExpect(this.cellThree.isColor(Color.yellow), true);
    t.checkExpect(this.cellThree.isColor(Color.black), false);
  }

  // tests the setColor method
  void testSetColor(Tester t) {
    this.init();
    t.checkExpect(this.cellFour.isColor(Color.cyan), true);
    this.cellFour.setColor(Color.red);
    t.checkExpect(this.cellFour.isColor(Color.red), true);

    t.checkExpect(this.cellTwo.isColor(Color.black), false);
    this.cellTwo.setColor(Color.black);
    t.checkExpect(this.cellTwo.isColor(Color.black), true);
  }

  // tests the makeScene method
  void testMakeScence(Tester t) {
    FloodItWorld seededWorldOne = new FloodItWorld(10, 6, new Random(5));

    WorldScene expectedImage = new WorldScene(400, 400);
    expectedImage.placeImageXY(this.starterWorld.world, 0, 0);
    t.checkExpect(this.starterWorld.makeScene(), expectedImage);

    WorldScene expectedImageTwo = new WorldScene(400, 400);
    expectedImageTwo.placeImageXY(seededWorldOne.world, 0, 0);
    t.checkExpect(seededWorldOne.makeScene(), expectedImageTwo);

    WorldScene expectedImageGameOver = new WorldScene(400, 400);
    expectedImageGameOver.placeImageXY((this.starterWorld.world), 0, 0);
    expectedImageGameOver.placeImageXY((new TextImage("YOU LOSE", 50, FontStyle.BOLD, Color.white)),
        200, 200);

    this.starterWorld.isOver = true;
    t.checkExpect(this.starterWorld.makeScene(), expectedImageGameOver);
  }

  // tests the onKeyEvent method
  void testOnKeyEvent(Tester t) {
    this.init();
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));

    this.starterWorld.onKeyEvent("a");
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));

    this.starterWorld.onKeyEvent("");
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));

    this.starterWorld.onKeyEvent("abr");
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));

    this.starterWorld.onKeyEvent("r");
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand.equals(new Random(3)), false);
    this.init();
  }

  // tests the onMouseClicked method
  // this inadvertently tests the floodWithColor method
  void testOnMouseClicked(Tester t) {
    this.init();
    t.checkExpect(this.starterWorld.currAttempts, 0);
    t.checkExpect(this.starterWorld.isFlooding, false);
    t.checkExpect(this.starterWorld.selectedColor, Color.red);

    this.starterWorld.onMouseClicked(new Posn(0, 0));
    t.checkExpect(this.starterWorld.currAttempts, 0);
    t.checkExpect(this.starterWorld.isFlooding, false);
    t.checkExpect(this.starterWorld.selectedColor, Color.red);

    this.starterWorld.onMouseClicked(new Posn(206, 190));
    t.checkExpect(this.starterWorld.currAttempts, 1);
    t.checkExpect(this.starterWorld.isFlooding, true);
    t.checkExpect(this.starterWorld.selectedColor, Color.black);

    this.init();
    this.starterWorld.onMouseClicked(new Posn(275, 207));
    t.checkExpect(this.starterWorld.currAttempts, 1);
    t.checkExpect(this.starterWorld.isFlooding, true);
    t.checkExpect(this.starterWorld.selectedColor, Color.blue);
  }

  // tests the waterfallCell method
  void testWaterfallCell(Tester t) {
    this.init();
    this.starterWorld.waterfallCell(this.cellFive);
    t.checkExpect(this.cellFive.color, this.starterWorld.selectedColor);
    this.starterWorld.waterfallCell(this.cellFour);
    t.checkExpect(this.cellFour.color, Color.red);
  }

  // tests the setColorIfFlooded method
  void testSetColorIfFlooded(Tester t) {
    this.init();
    this.starterWorld.setColorIfFlooded(this.cellFive);
    t.checkExpect(this.cellFive.color, Color.blue);
    this.starterWorld.setColorIfFlooded(this.cellFour);
    t.checkExpect(this.cellFour.color, Color.cyan);
  }

  // tests the makeBoard method
  void testMakeBoard(Tester t) {
    this.init();
    this.starterWorld.makeBoard(this.starterWorld.size, this.starterWorld.numColors,
        this.starterWorld.rand);
    t.checkExpect(this.starterWorld.board.size(), 25);
    t.checkExpect(this.starterWorld.attempts, 9);
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));
  }

  // tests the floodWithColor method
  void testFloodWithColor(Tester t) {
    this.init();
    this.starterWorld.floodWithColor(Color.red);
    t.checkExpect(this.starterWorld.flooded.get(0).color, Color.red);
  }

  // tests the reset method
  void testReset(Tester t) {
    this.init();
    FloodItWorld seededWorldOne = new FloodItWorld(10, 6, new Random(5));
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand, new Random(3));

    this.starterWorld.reset();
    t.checkExpect(this.starterWorld.size, 5);
    t.checkExpect(this.starterWorld.numColors, 4);
    t.checkExpect(this.starterWorld.rand.equals(new Random(3)), false);

    this.init();
    t.checkExpect(seededWorldOne.size, 10);
    t.checkExpect(seededWorldOne.numColors, 6);
    t.checkExpect(seededWorldOne.rand, new Random(5));

    this.starterWorld.reset();
    t.checkExpect(seededWorldOne.size, 10);
    t.checkExpect(seededWorldOne.numColors, 6);
    t.checkExpect(seededWorldOne.rand.equals(new Random(3)), false);
  }

  // tests the filterUnique method
  void testFilterUnique(Tester t) {
    this.init();
    this.starterWorld.filterUnique(this.starterWorld.flooded);
    t.checkExpect(this.starterWorld.flooded.size(), 1);
  }

  // tests the drawWorld method
  void testDrawWorld(Tester t) {
    FloodItWorld seededWorldOne = new FloodItWorld(10, 6, new Random(5));

    WorldImage expectedWorld = seededWorldOne.world;
    FloodItWorld testingFlood = new FloodItWorld(10, 6);

    testingFlood.world = new RectangleImage(0, 0, OutlineMode.SOLID, Color.black);
    testingFlood.board = seededWorldOne.board;
    t.checkExpect(
        testingFlood.world.equals(new RectangleImage(0, 0, OutlineMode.SOLID, Color.black)), true);
    testingFlood.drawWorld();
    t.checkExpect(testingFlood.world.equals(expectedWorld), true);

    FloodItWorld seededWorldTwo = new FloodItWorld(7, 3, new Random(10));

    WorldImage expectedWorldTwo = seededWorldTwo.world;
    FloodItWorld testingFloodTwo = new FloodItWorld(7, 3);

    testingFloodTwo.world = new RectangleImage(0, 0, OutlineMode.SOLID, Color.black);
    testingFloodTwo.board = seededWorldTwo.board;
    t.checkExpect(
        testingFloodTwo.world.equals(new RectangleImage(0, 0, OutlineMode.SOLID, Color.black)),
        true);
    testingFloodTwo.drawWorld();
    t.checkExpect(testingFloodTwo.world.equals(expectedWorldTwo), true);
  }
}