import java.awt.Color;
import java.util.Random;
import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;

// Represents a game of Simon Says
class SimonWorld extends World {
  /*
   * Template:
   * FIELDS:
   * this.SCREEN_SIZE -- static int
   * this.buttons -- static ILoButton
   * this.ans -- ILoButton
   * this.key -- ILoButton
   * this.isKeyState -- boolean
   * this.isOver -- boolean
   * this.count -- int
   * this.currentKeyIndex -- int
   * this.buttoneState -- WorldImage
   * this.keyState -- WorldImage
   * this.rand -- Random
   * METHODS:
   * ... this.makeScene ... -- WorldScene
   * ... this.onTick ... -- World
   * ... this.lastScene(String msg) -- WorldScene
   * ... this.worldEnds ... -- WorldEnd
   * ... this.onMouseClicked(posn pos) ... -- SimonWorld
   * METHODS OF FIELDS:
   * key.length() -- int
   * key.getButtonAt() -- Button
   * buttons.drawButtons() -- WorldImage
   * buttons.getButtonAt -- Button
   * rand.nextInt() -- int
   */
  static int SCENE_SIZE = 400;
  static ILoButton buttons = new ConsLoButton(new Button(Color.yellow, 0, 0),
      new ConsLoButton(new Button(Color.blue, 200, 0),
          new ConsLoButton(new Button(Color.green, 400, 0),
              new ConsLoButton(new Button(Color.cyan, 600, 0), new MtLoButton()))));
  ILoButton ans = new MtLoButton();
  ILoButton key = new MtLoButton();
  boolean isKeyState = true;
  boolean isOver = false;
  int count = 0;
  int currentKeyIndex = 0;
  WorldImage buttonState = buttons.drawButtons();
  WorldImage keyState = buttons.drawButtons();
  Random rand;

  // Constructor
  public SimonWorld(Random rand) {
    this.rand = rand;
    int next = this.rand.nextInt(4);
    this.key = new ConsLoButton(buttons.getButtonAt(next), this.key);
    this.count = this.key.length();
    this.currentKeyIndex = this.count;
  }

  // Constructor
  public SimonWorld() {
    this(new Random());
  }

  // Draw the current state of the world
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(SCENE_SIZE, SCENE_SIZE);
    if (this.isKeyState) {
      background = background.placeImageXY(keyState, 0, 0);
    }
    else {
      background = background.placeImageXY(this.buttonState, 0, 0);
    }
    this.buttonState = buttons.drawButtons();
    this.keyState = buttons.drawButtons();
    return background;
  }

  // handles ticking of the clock and updating the world if needed
  public World onTick() {
    if (this.count != 0 && this.isKeyState) {
      int index = buttons.getIndexOf(this.key.getButtonAt(count - 1));
      this.keyState = buttons.drawLightButtonAt(index);
      this.count -= 1;
    }
    else if (count == 0) {
      this.isKeyState = false;
    }
    if (this.currentKeyIndex == 0) {
      this.isKeyState = true;
      this.key = new ConsLoButton(buttons.getButtonAt(this.rand.nextInt(4)), this.key);
      this.count = this.key.length();
      this.currentKeyIndex = this.count;
    }
    return this;
  }

  // Returns the final scene with the given message displayed
  public WorldScene lastScene(String msg) {
    /*
     * Template: everything in the template for SimonWorld, plus
     * parameters:
     * -- msg -- String
     */
    WorldImage text = new TextImage(msg, 24, FontStyle.BOLD, Color.RED);

    WorldScene background = new WorldScene(SCENE_SIZE, SCENE_SIZE).placeImageXY(text,
        SCENE_SIZE / 2, SCENE_SIZE / 2);
    return background;
  }

  // Ends the world if the boolean of the game over is true
  public WorldEnd worldEnds() {
    if (this.isOver) {
      return new WorldEnd(true, this.lastScene("YOU LOSE"));
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // handles mouse clicks and is given the mouse location
  public SimonWorld onMouseClicked(Posn pos) {
    /*
     * Template: everything in the template for SimonWorld, plus
     * Parameters:
     * -- pos -- Posn
     */
    if (this.isKeyState) {
      return this;
    }
    this.buttonState = buttons.drawLightButtonFor(pos);
    if (!this.key.getButtonAt(currentKeyIndex - 1).buttonEquals(buttons.getButtonAt(pos))) {
      this.isOver = true;
    }
    else {
      this.currentKeyIndex -= 1;
    }
    return this;
  }
}

// Represents a list of buttons
interface ILoButton {
  // Displays the buttons
  WorldImage drawButtons();

  // Brightens the buttons at the position that is given
  WorldImage drawLightButtonFor(Posn pos);

  // Brightens the buttons at the index that is given
  WorldImage drawLightButtonAt(int index);

  // Gets the button that is at the index given
  Button getButtonAt(int index);

  // Gets the button at the posn that is given
  Button getButtonAt(Posn pos);

  // Gets the index of the button given
  int getIndexOf(Button button);

  // Gets the length of the list
  int length();
}

// Represents an empty list of buttons
class MtLoButton implements ILoButton {
  /*
   * TEMPLATE
   * FIELDS:
   * METHODS:
   * ... this.drawButtons() ... WorldImage
   * ... this.drawLightButtonFor(Posn pos) ... -- WorldImage
   * ... this.getButtonAt(int index) ... -- Button
   * ... this.length() ... -- int
   * ... this.drawLightButtonAt(int index) ... -- WorldImage
   * ... this.getIndexOf(Button button) ... -- int
   * ... this.getButtonAt(Posn pos) ... -- Button
   */
  // Constructor
  MtLoButton() {
  }

  // Displays the buttons
  public WorldImage drawButtons() {
    return new RectangleImage(0, 0, OutlineMode.SOLID, Color.white);
  }

  // Draws the button at the position given
  public WorldImage drawLightButtonFor(Posn pos) {
    /*
     * Template: everything in the template for MtLoButton, plus
     * Parameters:
     * pos -- Posn
     */
    return new RectangleImage(0, 0, OutlineMode.SOLID, Color.white);
  }

  // returns the button at the given index
  public Button getButtonAt(int index) {
    /*
     * Template: everything in the template for MtLoButton, plus
     * Parameters:
     * index -- int
     */
    throw new IllegalArgumentException("Index out of range");
  }

  // Gets the button at the position given
  public Button getButtonAt(Posn pos) {
    /*
     * Template: everything in the template for MtLoButton, plus
     * Parameters:
     * pos -- Posn
     */
    throw new IllegalArgumentException("Did not select a button on screen");
  }

  // returns the length of the list
  public int length() {
    return 0;
  }

  // Brightens the button at the index given
  public WorldImage drawLightButtonAt(int index) {
    /*
     * Template: everything in the template for MtLoButton, plus
     * Parameters:
     * index -- int
     */
    return new RectangleImage(0, 0, OutlineMode.SOLID, Color.white);
  }

  // Gets the index of the button given
  public int getIndexOf(Button button) {
    /*
     * Template: everything in the template for MtLoButton, plus
     * Parameters:
     * button -- Button
     * Fields of Paramers:
     * button.x -- int
     * button.y -- int
     * button.color -- Color
     * METHODS on parameters:
     * ... button.drawDark() ... -- WorldImage
     * ... button.drawLit() ... -- WorldImage
     * ... button.drawButton(Color color) ... -- WorldImage
     */
    throw new IllegalArgumentException("Button does not exist in list");
  }
}

// Represents a non-empty list of buttons
class ConsLoButton implements ILoButton {
  /*
   * TEMPLATE
   * FIELDS:
   * this.first -- Button
   * this.rest -- ILoButton
   * METHODS:
   * ... this.drawButtons() ... WorldImage
   * ... this.drawLightButtonFor(Posn pos) ... -- WorldImage
   * ... this.getButtonAt(int index) ... -- Button
   * ... this.length() ... -- int
   * ... this.drawLightButtonAt(int index) ... -- WorldImage
   * ... this.getIndexOf(Button button) ... -- int
   * ... this.getButtonAt(Posn pos) ... -- Button
   * METHODS OF FIELDS:
   * ... first.drawButtons() ... WorldImage
   * ... first.drawLightButtonFor(Posn pos) ... -- WorldImage
   * ... first.getButtonAt(int index) ... -- Button
   * ... first.length() ... -- int
   * ... first.drawLightButtonAt(int index) ... -- WorldImage
   * ... first.getIndexOf(Button button) ... -- int
   * ... first.getButtonAt(Posn pos) ... -- Button
   * ... rest.drawButtons() ... WorldImage
   * ... rest.drawLightButtonFor(Posn pos) ... -- WorldImage
   * ... rest.getButtonAt(int index) ... -- Button
   * ... rest.length() ... -- int
   * ... rest.drawLightButtonAt(int index) ... -- WorldImage
   * ... rest.getIndexOf(Button button) ... -- int
   * ... rest.getButtonAt(Posn pos) ... -- Button
   */
  Button first;
  ILoButton rest;

  // Constructor
  ConsLoButton(Button first, ILoButton rest) {
    this.first = first;
    this.rest = rest;
  }

  // Displays the buttons
  public WorldImage drawButtons() {
    return new OverlayImage(this.first.drawDark(), this.rest.drawButtons());
  }

  // Brightens the button at the given posn
  public WorldImage drawLightButtonFor(Posn pos) {
    /*
     * Template: everything in the template for ConsLoButton, plus
     * Parameters:
     * pos -- Posn
     */
    if (this.first.x / 2 - pos.x > -100) {
      return new OverlayImage(this.first.drawLit(), this.rest.drawButtons());
    }
    return new OverlayImage(this.first.drawDark(), this.rest.drawLightButtonFor(pos));
  }

  // Gets the button at the index given
  public Button getButtonAt(int index) {
    /*
     * Template: everything in the template for ConsLoButton, plus
     * Parameters:
     * index -- int
     */
    if (index == 0) {
      return this.first;
    }
    return this.rest.getButtonAt(index - 1);
  }

  // gets the button at the given posn
  public Button getButtonAt(Posn pos) {
    /*
     * Template: everything in the template for ConsLoButton, plus
     * Parameters:
     * pos -- Posn
     */
    if (this.first.x / 2 - pos.x > -100) {
      return this.first;
    }
    return this.rest.getButtonAt(pos);
  }

  // Gets the index of the button given in the list
  public int getIndexOf(Button button) {
    /*
     * Template: everything in the template for ConsLoButton, plus
     * Parameters:
     * button -- Button
     * Fields of Paramers:
     * button.x -- int
     * button.y -- int
     * button.color -- Color
     * METHODS on parameters:
     * ... button.drawDark() ... -- WorldImage
     * ... button.drawLit() ... -- WorldImage
     * ... button.drawButton(Color color) ... -- WorldImage
     */
    if (this.first.buttonEquals(button)) {
      return 0;
    }
    return 1 + this.rest.getIndexOf(button);
  }

  // returns the length of the list
  public int length() {
    return 1 + rest.length();
  }

  // brightens the button at the given index
  public WorldImage drawLightButtonAt(int index) {
    /*
     * Template: everything in the template for ConsLoButton, plus
     * Parameters:
     * index -- int
     */
    if (index == 0) {
      return new OverlayImage(this.first.drawLit(), this.rest.drawButtons());
    }
    return new OverlayImage(this.first.drawDark(), this.rest.drawLightButtonAt(index - 1));
  }
}

// Represents one of the four buttons you can click
class Button {
  /*
   * TEMPLATE
   * FIELDS:
   * this.x -- int
   * this.y -- int
   * this.color -- Color
   * METHODS:
   * ... this.drawDark() ... -- WorldImage
   * ... this.drawLit() ... -- WorldImage
   * ... this.drawButton(Color color) ... -- WorldImage
   * ... this.buttonEquals ... -- boolean
   */
  Color color;
  int x;
  int y;

  // Constructor
  Button(Color color, int x, int y) {
    this.color = color;
    this.x = x;
    this.y = y;
  }

  // Draw this button dark
  WorldImage drawDark() {
    return this.drawButton(this.color.darker().darker());
  }

  // decide whether two buttons are the same
  /*
   * Template
   * Paramater
   * -- button -- Button
   * Fields of parameter
   * -- button.x -- int
   * -- button.y -- int
   * -- button.Color -- Color
   * Methods of paramater
   * ... button.drawDark() ... -- WorldImage
   * ... button.drawLit() ... -- WorldImage
   * ... button.drawButton(Color color) ... -- WorldImage
   */
  boolean buttonEquals(Button button) {
    /*
     * Template: everything in the template for Button, plus
     * Parameters:
     * button -- Button
     * Fields of Paramers:
     * button.x -- int
     * button.y -- int
     * button.color -- Color
     * METHODS on parameters:
     * ... button.drawDark() ... -- WorldImage
     * ... button.drawLit() ... -- WorldImage
     * ... button.drawButton(Color color) ... -- WorldImage
     */
    return this.x == button.x && this.y == button.y && this.color.equals(button.color);
  }

  // Draw this button lit
  WorldImage drawLit() {
    return this.drawButton(this.color.brighter().brighter());
  }

  WorldImage drawButton(Color color) {
    /*
     * Template: everything in the template for Button, plus
     * Parameters:
     * color -- Color
     */
    return new RectangleImage(this.x + 200, this.y + 800, OutlineMode.SOLID, color);
  }
}

// Examples
class ExamplesSimon {

  // Constructor
  ExamplesSimon() {
  }

  ILoButton buttons = new ConsLoButton(new Button(Color.yellow, 0, 0),
      new ConsLoButton(new Button(Color.blue, 200, 0),
          new ConsLoButton(new Button(Color.green, 400, 0),
              new ConsLoButton(new Button(Color.cyan, 600, 0), new MtLoButton()))));

  ILoButton emptyButton = new MtLoButton();

  SimonWorld starterWorld = new SimonWorld();

  Random rand = new Random(10);

  SimonWorld predictableStarterWorld = new SimonWorld(this.rand);

  // runs the game by creating a world and calling bigBang
  boolean testSimonSays(Tester t) {
    int sceneSize = SimonWorld.SCENE_SIZE;
    return this.starterWorld.bigBang(sceneSize, sceneSize, 1);
  }

  // tests for make scene
  boolean testMakeScene(Tester t) {
    return t.checkExpect(this.starterWorld.makeScene(),
        new WorldScene(400, 400).placeImageXY(this.buttons.drawButtons(), 0, 0));
  }

  // test for last scene
  boolean testLastScene(Tester t) {
    return t.checkExpect(this.predictableStarterWorld.lastScene("You Lose!"),
        new WorldScene(400, 400)
            .placeImageXY(new TextImage("You Lose!", 24, FontStyle.BOLD, Color.RED), 200, 200));
  }

  // test for on tick
  boolean testOnTick(Tester t) {
    return t.checkExpect(new SimonWorld(this.rand).onTick().makeScene(),
        new WorldScene(400, 400).placeImageXY(this.buttons.drawLightButtonAt(1), 0, 0));
  }

  // test on mouse click and world ends
  boolean testOnMouseClickAndWorldEnds(Tester t) {
    return t.checkExpect(
        this.predictableStarterWorld.onTick().onMouseClicked(new Posn(150, 100)).makeScene(),
        new WorldScene(400, 400).placeImageXY(this.buttons.drawLightButtonAt(2), 0, 0))
        && t.checkExpect(
            this.predictableStarterWorld.onTick().onTick().onTick()
                .onMouseClicked(new Posn(100, 100)).worldEnds(),
            new WorldEnd(true, new WorldScene(400, 400)
                .placeImageXY(new TextImage("YOU LOSE", 24, FontStyle.BOLD, Color.RED), 200, 200)));
  }

  // Tests the getButtonAtIndex method
  boolean testGetButtonAtIndex(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(1), new Button(Color.blue, 200, 0))
        && t.checkException(new IllegalArgumentException("Index out of range"), this.emptyButton,
            "getButtonAt", 1)
        && t.checkException(new IllegalArgumentException("Index out of range"), this.buttons,
            "getButtonAt", 6);
  }

  // Tests the getButtonAtPosn method
  boolean testGetButtonAtPosn(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(new Posn(5, 20)), new Button(Color.yellow, 0, 0))
        && t.checkException(new IllegalArgumentException("Did not select a button on screen"),
            this.emptyButton, "getButtonAt", new Posn(5, 20))
        && t.checkException(new IllegalArgumentException("Did not select a button on screen"),
            this.buttons, "getButtonAt", new Posn(5000, 5000));
  }

  // Tests the getIndexOf method
  boolean testGetIndexof(Tester t) {
    return t.checkExpect(this.buttons.getIndexOf(new Button(Color.blue, 200, 0)), 1)
        && t.checkException(new IllegalArgumentException("Button does not exist in list"),
            this.emptyButton, "getIndexOf", new Button(Color.yellow, 0, 0))
        && t.checkException(new IllegalArgumentException("Button does not exist in list"),
            this.buttons, "getIndexOf", new Button(Color.black, 0, 0));
  }

  // tests the buttonEquals method
  boolean testButtonEquals(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(0).buttonEquals(new Button(Color.yellow, 0, 0)),
        true)
        && t.checkExpect(this.buttons.getButtonAt(1).buttonEquals(this.buttons.getButtonAt(0)),
            false);
  }

  // tests the buttonDrawLit method
  boolean testButtonDrawLit(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(0).drawLit(),
        new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow.brighter().brighter()));
  }

  // tests the buttonDrawDark method
  boolean testButtonDrawDark(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(0).drawDark(),
        new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow.darker().darker()));
  }

  // tests the drawButton method
  boolean testButtonDrawButton(Tester t) {
    return t.checkExpect(this.buttons.getButtonAt(0).drawButton(Color.yellow),
        new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow));
  }

  // tests the drawButtons method
  boolean testDrawButtons(Tester t) {
    return t
        .checkExpect(this.buttons.drawButtons(), new OverlayImage(
            new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow.darker().darker()),
            new OverlayImage(
                new RectangleImage(400, 800, OutlineMode.SOLID, Color.blue.darker().darker()),
                new OverlayImage(
                    new RectangleImage(600, 800, OutlineMode.SOLID, Color.green.darker().darker()),
                    new OverlayImage(
                        new RectangleImage(800, 800, OutlineMode.SOLID,
                            Color.cyan.darker().darker()),
                        new RectangleImage(0, 0, OutlineMode.SOLID, Color.white))))))
        && t.checkExpect(this.emptyButton.drawButtons(),
            new RectangleImage(0, 0, OutlineMode.SOLID, Color.white));
  }

  // tests the drawLightButtonAt method
  boolean testDrawLightButtonAt(Tester t) {
    return t
        .checkExpect(this.buttons.drawLightButtonAt(1), new OverlayImage(
            new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow.darker().darker()),
            new OverlayImage(
                new RectangleImage(400, 800, OutlineMode.SOLID, Color.blue.brighter().brighter()),
                new OverlayImage(
                    new RectangleImage(600, 800, OutlineMode.SOLID, Color.green.darker().darker()),
                    new OverlayImage(
                        new RectangleImage(800, 800, OutlineMode.SOLID,
                            Color.cyan.darker().darker()),
                        new RectangleImage(0, 0, OutlineMode.SOLID, Color.white))))))
        && t.checkExpect(this.emptyButton.drawLightButtonAt(2),
            new RectangleImage(0, 0, OutlineMode.SOLID, Color.white));
  }

  // tests the drawLightButtonFor method
  boolean testDrawLightButtonFor(Tester t) {
    return t
        .checkExpect(this.buttons.drawLightButtonFor(new Posn(0, 0)), new OverlayImage(
            new RectangleImage(200, 800, OutlineMode.SOLID, Color.yellow.brighter().brighter()),
            new OverlayImage(
                new RectangleImage(400, 800, OutlineMode.SOLID, Color.blue.darker().darker()),
                new OverlayImage(
                    new RectangleImage(600, 800, OutlineMode.SOLID, Color.green.darker().darker()),
                    new OverlayImage(
                        new RectangleImage(800, 800, OutlineMode.SOLID,
                            Color.cyan.darker().darker()),
                        new RectangleImage(0, 0, OutlineMode.SOLID, Color.white))))))
        && t.checkExpect(this.emptyButton.drawLightButtonFor(new Posn(2000, 29000)),
            new RectangleImage(0, 0, OutlineMode.SOLID, Color.white));
  }

  // tests the length method
  boolean testlength(Tester t) {
    return t.checkExpect(this.buttons.length(), 4) && t.checkExpect(this.emptyButton.length(), 0);
  }
}
