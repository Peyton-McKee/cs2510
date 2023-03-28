import tester.Tester;

/* TEMPLATE
 * Fields 
 * -- this.flour -- double
 * -- this.water -- double 
 * -- this.yeasat -- double
 * -- this.salt -- double 
 * -- this.malt -- double
 * METHODS
 * -- this.throwError -- IllegalExceptionArgument
 * -- this.sameRecipe -- boolean
 * -- this.checkSalt -- void 
 * -- this.sameWeightIngredients - boolean
 */
class BagelRecipe {
  double flour;
  double water;
  double yeast;
  double salt;
  double malt;

  // constructor for all fields being passed in throws error if invlaid arguments
  // supplied
  public BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    this.flour = flour;
    this.water = water;
    this.yeast = yeast;
    this.salt = salt;
    this.malt = malt;

    checkSalt();
    if (flour != water) {
      this.throwError("Water", water);
    }
    if (yeast != malt) {
      this.throwError("Malt", malt);
    }
  }

  // constructor for providing weight of flour and yeast, determines the right
  // amount of other ingrdients automatically
  public BagelRecipe(double flour, double yeast) {
    this.flour = flour;
    this.yeast = yeast;
    this.malt = yeast;
    this.water = flour;
    this.salt = (flour - (20 * yeast)) / 20;
  }

  // constructor for providing volume of flour yeast and salt, throws error if not
  // balanced properly
  public BagelRecipe(double flour, double yeast, double salt) {
    this.flour = 4.25 * flour;
    this.water = this.flour;
    this.yeast = yeast / 9.6;
    this.malt = this.yeast;
    this.salt = salt / 4.8;
    checkSalt();
  }

  /*
   * Throws error with given parameters
   * Parameters
   * -- key -- String
   * -- value-- double
   */
  IllegalArgumentException throwError(String key, double value) {
    throw new IllegalArgumentException("Invalid value " + String.valueOf(value) + " for " + key);
  }

  /*
   * Method for determining if two bagel recipes have the same amount of
   * ingredients
   * Paramters
   * -- other BagelRecipe --
   * Methods of parameters
   * -- other.throwError -- IllegalExceptionArgument
   * -- other.sameRecipe -- boolean
   * -- other.checkSalt -- void
   * -- other.sameWeightIngredients - boolean
   */
  boolean sameRecipe(BagelRecipe other) {
    return (sameWeightIngredients(this.flour, other.flour)
        && sameWeightIngredients(this.yeast, other.yeast)
        && sameWeightIngredients(this.malt, other.malt)
        && sameWeightIngredients(this.water, other.water)
        && sameWeightIngredients(this.salt, other.salt));
  }

  // Determines if the recipes salt is valid and if not throws an error
  void checkSalt() {
    if (Math.abs(this.salt + this.yeast - ((this.flour) / 20)) > 0.001) {
      throwError("Salt", this.salt);
    }
  }

  // determines if the given ingredients have the same weight
  /*
   * parameters
   * -- ingredient1 -- double
   * -- ingredient2 -- double
   */
  boolean sameWeightIngredients(double ingredient1, double ingredient2) {
    return Math.abs(ingredient1 - ingredient2) <= 0.001;
  }
}

//Class for examples of bagelrecipes
class ExamplesBagels {

  BagelRecipe bagel1 = new BagelRecipe(21.25, 21.25, 0.208, 0.854, 0.208);
  BagelRecipe bagel2 = new BagelRecipe(5, 2);
  BagelRecipe bagel3 = new BagelRecipe(5, 2, 4.1);

  // Tests the throwError method
  boolean testThrowError(Tester t) {
    return t.checkException(new IllegalArgumentException("Invalid value 10.0 for salt"),
        this.bagel1, "throwError", "salt", 10.0);
  }

  // tests the sameRecipe method
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(this.bagel2.sameRecipe(this.bagel1), false)
        && t.checkExpect(this.bagel1.sameRecipe(this.bagel3), true);
  }

  // tests the sameWeightIngredients method
  boolean testSameWeightIngredients(Tester t) {
    return t.checkExpect(this.bagel1.sameWeightIngredients(2, 2), true)
        && t.checkExpect(this.bagel1.sameWeightIngredients(2, 2.001), true)
        && t.checkExpect(this.bagel1.sameWeightIngredients(2, 2.002), false);
  }
}