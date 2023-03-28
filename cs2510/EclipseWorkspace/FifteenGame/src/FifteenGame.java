import java.util.ArrayList;

import javax.sql.rowset.Predicate;

import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

class Utils {
  <T> ArrayList<T> filter(ArrayList<T> arr, Predicate pred) {
    ArrayList<T> ans = new ArrayList<T>(arr.size());
    for (T element : arr) {
      if (pred.equals(element)) {
        ans.add(element);
      }
    }
    return ans;
  }

  <T> void removeExcept(ArrayList<T> arr, Predicate pred) {
    ArrayList<T> temp = new ArrayList<T>(arr);
    for (T element : arr) {
      if (pred.equals(element)) {
        continue;
      }
      temp.remove(element);
    }
    arr = temp;
  }
}

//Represents an individual tile
class Tile {
  // The number on the tile.  Use 0 to represent the hole
  int value;

  // Draws this tile onto the background at the specified logical coordinates
  WorldImage drawAt(int col, int row, WorldImage background) { 
    WorldImage img;
    if(value == 0) {
      img = new RectangleImage(col * 40 + 40, row * 40 + 40, OutlineMode.SOLID, Color.black);
    }
    else {
      img = new OverlayImage(new RectangleImage(col * 40 + 40, row * 40 + 40, OutlineMode.SOLID, Color.gray), new TextImage(Integer.toString(value), Color.black));
    }
    return new OverlayImage(background, img);
  }
}

class FifteenGame extends World {
  // represents the rows of tiles
  ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>(16);
  

  // draws the game
  WorldImage world;
  
  public FifteenGame() {
    
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j ++) {
        tiles.get(i).add(new Tile())
      }
    }
  }
  public WorldScene makeScene() { 
    WorldScene background = new WorldScene(120, 120);
    background.placeImageXY(world, 0, 0);
    return background;
  }

  // handles keystrokes
  public void onKeyEvent(String k) {
    // needs to handle up, down, left, right to move the hole
    // extra: handle "u" to undo moves
    switch (k) {
    case "w":
      break;
    case "a":
      break;
    case "s":
      break;
    case "d":
      break;
    }
  }
}

class ExampleFifteenGame {
  void testGame(Tester t) {
    FifteenGame g = new FifteenGame();
    g.bigBang(120, 120);
  }
}
