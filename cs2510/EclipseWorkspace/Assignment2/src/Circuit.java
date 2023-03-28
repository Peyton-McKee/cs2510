import tester.*;

interface ICircuit {
  // counts the number of batteries and resistors in circuit
  int countComponents();

  // counts the total amount of voltage in the circuit
  double totalVoltage();

  // counts the total amount of resistance in the circuit
  double totalResistance();

  // calculates the total current of the circuit
  double totalCurrent();

  // reverses the amount of voltage for each battery
  ICircuit reversePolarity();
}

/*
 * Template
 * Fields
 * -- name -- string
 * -- voltage -- double
 * -- nominalResistance -- double
 * Methods
 * -- this.countComponets -- int
 * -- this.totalVoltage -- double
 * -- this.totalResistance -- double
 * -- this.totalCurrent -- double
 * -- this.reversePolarity -- ICircuit
 */
class Battery implements ICircuit {
  String name;
  double voltage;
  double nominalResistance;

  // constructor
  public Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }

  // Counts the number of batteries and resistors in the circuit
  public int countComponents() {
    return 1;
  }

  // Counts the total amount of voltage in the circuit
  public double totalVoltage() {
    return this.voltage;
  }

  // counts the total amount of resistance in the circuit
  public double totalResistance() {
    return this.nominalResistance;
  }

  // calculates the total current of the circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // reverses the amount of voltage for each battery
  public ICircuit reversePolarity() {
    return new Battery(this.name, -this.voltage, this.nominalResistance);
  }
}

/*
 * Template
 * Fields
 * -- name -- String
 * -- resistance -- double
 * Methods
 * -- this.countComponets -- int
 * -- this.totalVoltage -- double
 * -- this.totalResistance -- double
 * -- this.totalCurrent -- double
 * -- this.reversePolarity -- ICircuit
 */
class Resistor implements ICircuit {
  String name;
  double resistance;

  // constructor
  public Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }

  // Counts the number of batteries and resistors in the circuit
  public int countComponents() {
    return 1;
  }

  // counts the total amount of voltage in the circuit
  public double totalVoltage() {
    return 0;
  }

  // counts the total amount of resistance in the circuit
  public double totalResistance() {
    return this.resistance;
  }

  // calculates the total current of the circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // reverses the amount of voltage for each battery
  public ICircuit reversePolarity() {
    return new Resistor(this.name, this.resistance);
  }
}

/*
 * Template
 * Fields
 * -- first -- ICircuit
 * -- second -- ICircuit
 * Methods
 * -- this.countComponets -- int
 * -- this.totalVoltage -- double
 * -- this.totalResistance -- double
 * -- this.totalCurrent -- double
 * -- this.reversePolarity -- ICircuit
 * Methods of Fields
 * -- first.countComponets -- int
 * -- first.totalVoltage -- double
 * -- first.totalResistance -- double
 * -- first.totalCurrent -- double
 * -- first.reversePolarity -- ICircuit
 * -- second.countComponets -- int
 * -- second.totalVoltage -- double
 * -- second.totalResistance -- double
 * -- second.totalCurrent -- double
 * -- second.reversePolarity -- ICircuit
 */
class Series implements ICircuit {
  ICircuit first;
  ICircuit second;

  // constructor
  public Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  // Counts the number of batteries and resistors in the circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // counts the total amount of voltage in the circuit
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  }

  // counts the total amount of resistance in the circuit
  public double totalResistance() {
    return this.first.totalResistance() + this.second.totalResistance();
  }

  // calculates the total current of the circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // reverses the amount of voltage for each battery
  public ICircuit reversePolarity() {
    return new Series(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

/*
 * Template
 * Fields
 * -- first -- ICircuit
 * -- second -- ICircuit
 * Methods
 * -- this.countComponets -- int
 * -- this.totalVoltage -- double
 * -- this.totalResistance -- double
 * -- this.totalCurrent -- double
 * -- this.reversePolarity -- ICircuit
 * Methods of Fields
 * -- first.countComponets -- int
 * -- first.totalVoltage -- double
 * -- first.totalResistance -- double
 * -- first.totalCurrent -- double
 * -- first.reversePolarity -- ICircuit
 * -- second.countComponets -- int
 * -- second.totalVoltage -- double
 * -- second.totalResistance -- double
 * -- second.totalCurrent -- double
 * -- second.reversePolarity -- ICircuit
 */
class Parallel implements ICircuit {
  ICircuit first;
  ICircuit second;

  // constructor
  public Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }

  // Counts the number of batteries and resistors in the circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }

  // counts the total amount of voltage in the circuit
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  }

  // counts the total amount of resistance in the circuit
  public double totalResistance() {
    return 1 / ((1 / this.first.totalResistance()) + (1 / this.second.totalResistance()));
  }

  // calculates the total current of the circuit
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }

  // reverses the amount of voltage for each battery
  public ICircuit reversePolarity() {
    return new Parallel(this.first.reversePolarity(), this.second.reversePolarity());
  }
}

//Examples and tests for classes and methods representing circuits
class ExamplesCircuits {
  ICircuit exampleCircuit = new Series(
      new Series(new Battery("B 1", 10, 0), new Battery("B 2", 10, 0)),
      new Series(new Resistor("R 1", 20), new Resistor("R 2", 20)));
  ICircuit batteryOne = new Battery("B 1", 10, 25);
  ICircuit resistorOne = new Resistor("R 1", 100);
  ICircuit simpleSeries = new Series(batteryOne, resistorOne);
  ICircuit complexCircuit = new Series(
      new Series(new Battery("B 1", 10, 0), new Battery("B 2", 20, 0)),
      new Parallel(
          new Parallel(new Parallel(new Series(new Resistor("R 4", 200), new Resistor("R 5", 50)),
              new Resistor("R 1", 100)), new Resistor("R 2", 250)),
          new Resistor("R 3", 500)));

  // test to make sure totalVoltage function works
  boolean testTotalVoltageForBattery(Tester t) {
    return t.checkExpect(batteryOne.totalVoltage(), 10.0);
  }

  // test to make sure countComponent function works
  boolean testCountComponentForBattery(Tester t) {
    return t.checkExpect(batteryOne.countComponents(), 1);
  }

  // test to make sure totalResistance function works
  boolean testTotalResistanceForBattery(Tester t) {
    return t.checkExpect(batteryOne.totalResistance(), 25.0);
  }

  // test to make sure totalCurrent function works
  boolean testTotalCurrentForBattery(Tester t) {
    return t.checkInexact(batteryOne.totalCurrent(), 10.0 / 25.0, 0.001);
  }

  // test to make sure reversePolarity function works
  boolean testReversePolarityForBattery(Tester t) {
    return t.checkExpect(batteryOne.reversePolarity(), new Battery("B 1", -10, 25));
  }

  // test to make sure totalVoltage function works
  boolean testTotalVoltageForResistor(Tester t) {
    return t.checkExpect(resistorOne.totalVoltage(), 0.0);
  }

  // test to make sure countComponent function works
  boolean testCountComponentForResistor(Tester t) {
    return t.checkExpect(resistorOne.countComponents(), 1);
  }

  // test to make sure totalResistance function works
  boolean testTotalResistanceForResistor(Tester t) {
    return t.checkExpect(resistorOne.totalResistance(), 100.0);
  }

  // test to make sure totalCurrent function works
  boolean testTotalCurrentForResistor(Tester t) {
    return t.checkInexact(resistorOne.totalCurrent(), 0.0, 0.001);
  }

  // test to make sure reversePolarity function works
  boolean testReversePolarityForResistor(Tester t) {
    return t.checkExpect(resistorOne.reversePolarity(), resistorOne);
  }

  // test to make sure totalVoltage function works
  boolean testTotalVoltageForSeries(Tester t) {
    return t.checkExpect(simpleSeries.totalVoltage(), 10.0);
  }

  // test to make sure countComponent function works
  boolean testCountComponentForSeries(Tester t) {
    return t.checkExpect(simpleSeries.countComponents(), 2);
  }

  // test to make sure totalResistance function works
  boolean testTotalResistanceForSeries(Tester t) {
    return t.checkExpect(simpleSeries.totalResistance(), 125.0);
  }

  // test to make sure totalCurrent function works
  boolean testTotalCurrentForSeries(Tester t) {
    return t.checkInexact(simpleSeries.totalCurrent(), 10.0 / 125.0, 0.001);
  }

  // test to make sure reversePolarity function works
  boolean testReversePolarityForSeries(Tester t) {
    return t.checkExpect(simpleSeries.reversePolarity(),
        new Series(new Battery("B 1", -10, 25), new Resistor("R 1", 100)));
  }

  // test to make sure totalVoltage function works
  boolean testTotalVoltageForParallel(Tester t) {
    return t.checkExpect(complexCircuit.totalVoltage(), 30.0);
  }

  // test to make sure countComponent function works
  boolean testCountComponentForParallel(Tester t) {
    return t.checkExpect(complexCircuit.countComponents(), 7);
  }

  // test to make sure totalResistance function works
  boolean testTotalResistanceForParallel(Tester t) {
    return t.checkInexact(complexCircuit.totalResistance(), 50.0, 0.001);
  }

  // test to make sure totalCurrent function works
  boolean testTotalCurrentForParallel(Tester t) {
    return t.checkInexact(complexCircuit.totalCurrent(), 30.0 / 50.0, 0.001);
  }

  // test to make sure reversePolarity function works
  boolean testReversePolarityForParallel(Tester t) {
    return t.checkExpect(complexCircuit.reversePolarity(),
        new Series(new Series(new Battery("B 1", -10, 0), new Battery("B 2", -20, 0)),
            new Parallel(
                new Parallel(
                    new Parallel(new Series(new Resistor("R 4", 200), new Resistor("R 5", 50)),
                        new Resistor("R 1", 100)),
                    new Resistor("R 2", 250)),
                new Resistor("R 3", 500))));
  }
}