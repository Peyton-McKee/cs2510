import tester.Tester;

interface IGamePiece {
  // returns the value of the GamePiece
  int getValue();

  // merges the two game pieces together
  IGamePiece merge(IGamePiece other);

  // determines whether a game piece was merged according to rules of 2048
  boolean isValid();
}

/*
 * Template
 * Fields
 * -- this.value -- int
 * Methods
 * -- this.getValue -- int
 */
class BaseTile implements IGamePiece {
  int value;

  // constructor
  public BaseTile(int value) {
    this.value = value;
  }

  // returns the value of the gamepiece
  public int getValue() {
    return this.value;
  }

  // merges the two game pieces together
  /*
   * Paramaters
   * -- other -- IGamePiece
   * Methods of Paramater
   * -- other.getValue -- int
   * -- other.merge -- IGamePiece
   * -- other.isValid -- boolean
   */
  public IGamePiece merge(IGamePiece other) {
    return new MergeTile(this, other);
  }

  // determines whether a game piece was merged according to rules of 2048
  public boolean isValid() {
    return this.getValue() == 2 || this.getValue() == 4;
  }
}

/*
 * Template
 * Fields
 * -- this.piece1 -- IGamePiece
 * -- this.piece2 -- IGamePiece
 * Methods
 * -- this.getValue -- int
 * Methods of Fields
 * -- this.piece1.getValue -- int
 * -- this.piece2.getValue -- int
 */
class MergeTile implements IGamePiece {
  IGamePiece piece1;
  IGamePiece piece2;

  // constructor
  public MergeTile(IGamePiece piece1, IGamePiece piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }

  // returns the value of the gamepiece
  public int getValue() {
    return this.piece1.getValue() + this.piece2.getValue();
  }

  // merges the two game pieces together
  /*
   * Paramaters
   * -- other -- IGamePiece
   * Methods of Paramater
   * -- other.getValue -- int
   * -- other.merge -- IGamePiece
   * -- other.isValid -- boolean
   */
  public IGamePiece merge(IGamePiece other) {
    return new MergeTile(this, other);
  }

  // determines whether a game piece was merged according to rules of 2048
  public boolean isValid() {
    return this.piece1.isValid() && this.piece2.isValid()
        && this.piece1.getValue() == this.piece2.getValue();
  }
}

class ExamplesGamePiece {
  IGamePiece base1 = new BaseTile(2);
  IGamePiece base2 = new BaseTile(4);
  IGamePiece base3 = new BaseTile(6);
  IGamePiece invalid6 = new MergeTile(base1, base2);
  IGamePiece valid4 = new MergeTile(base1, base1);
  IGamePiece invalid10 = valid4.merge(invalid6);

  // tests the getValue function
  boolean testGetValueMergeTile(Tester t) {
    return t.checkExpect(invalid6.getValue(), 6) && t.checkExpect(invalid10.getValue(), 10);
  }

  // tests the merge function
  boolean testMergeMergeTile(Tester t) {
    return t.checkExpect(valid4.merge(invalid6), invalid10)
        && t.checkExpect(valid4.merge(valid4), new MergeTile(valid4, valid4));
  }

  // tests the isValid function
  boolean testIsValidMergeTile(Tester t) {
    return t.checkExpect(valid4.isValid(), true) && t.checkExpect(invalid10.isValid(), false)
        && t.checkExpect(invalid6.isValid(), false);
  }

  //tests the getValue function
  boolean testGetValueBaseTile(Tester t) {
    return t.checkExpect(base1.getValue(), 2) && t.checkExpect(base3.getValue(), 6);
  }

  // tests the merge function
  boolean testMergeBaseTile(Tester t) {
    return t.checkExpect(base1.merge(base2), invalid6) && t.checkExpect(base1.merge(base1), valid4)
        && t.checkExpect(base2.merge(base3), new MergeTile(base2, base3));
  }

  // tests the isValid function
  boolean testIsValidBaseTile(Tester t) {
    return t.checkExpect(base1.isValid(), true) && t.checkExpect(base2.isValid(), true)
        && t.checkExpect(base3.isValid(), false);
  }

}