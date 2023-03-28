interface IMode {}

class Study implements IMode {
  String subject;
  boolean examReview;

  public Study(String subject, boolean examReview) {
    this.subject = subject;
    this.examReview = examReview;
  }
}

class Socialize implements IMode {
  String description;
  int friends;
  
  public Socialize(String description, int friends) {
    this.description = description;
    this.friends = friends;
  }
  
}

class Exercise implements IMode {
  String name;
  boolean aerobic;
  
  public Exercise(String name, boolean aerobic) {
    this.name = name;
    this.aerobic = aerobic;
  }
  
}

interface IPlace {}

class Classroom implements IPlace {
  String name;
  int roomCapacity;
  int occupantCount;
  
  public Classroom(String name, int roomCapacity, int occupantCount) {
    this.name = name;
    this.roomCapacity = roomCapacity;
    this.occupantCount = occupantCount;
  }
  
  
}

class Gym implements IPlace {
  String name;
  int exerciseMachines;
  int patrons;
  boolean open;
  
  public Gym(String name, int exerciseMachines, int patrons, boolean open) {
    this.name = name;
    this.exerciseMachines = exerciseMachines;
    this.patrons = patrons;
    this.open = open;
  }
  
  
}

class StudentCenter implements IPlace {
  String name;
  boolean open;
  
  public StudentCenter(String name, boolean open) {
    this.name = name;
    this.open = open;
  }
  
  
}

class SimStudent {
  String name;
  IMode mode;
  IPlace location;
  double gpa;
  String major;
  
  public SimStudent(String name, IMode mode, IPlace location, double gpa, String major) {
    this.name = name;
    this.mode = mode;
    this.location = location;
    this.gpa = gpa;
    this.major = major;
  }
  
}

class ExamplesSims {
  
  public ExamplesSims() {}
  
  IPlace shillman105 = new Classroom("Shillman 105", 115, 86);
  IPlace marino = new Gym("Marino Recreation Center", 78, 47, true);
  IPlace curry = new StudentCenter("Curry Student Center", false);
  IPlace ryder210 = new Classroom("Ryder 210", 30, 23);
  IPlace dodge417 = new Classroom("Dodge 417", 40, 23);
  IPlace squashBusters = new Gym("SquashBusters", 56, 0, false);
  SimStudent student1 = new SimStudent("Earl", 
      new Study("Math", false), shillman105, 2.7, "Computer Science");
  SimStudent student2 = new SimStudent("Jennette", 
      new Exercise("Squat", false), marino, 3.6, "Finance");
  SimStudent student3 = new SimStudent("Joey", 
      new Socialize("Talking", 2), curry, 3.3, "Business Administration");
  SimStudent student4 = new SimStudent("Bridget", 
      new Study("Enlgish", false), curry, 3.4, "English");

}


