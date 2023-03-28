interface IAppSet {}

class Folder implements IAppSet {
  String title;
  
  Folder(String title) {
    this.title = title;
  }
  
}

class Apps implements IAppSet {
  String appName;
  IAppSet others;
  
  Apps(String appName, IAppSet others) {
    this.appName = appName;
    this.others = others;
  }
}

class ExamplesSets {
  
  ExamplesSets() {}
  
  IAppSet travelApps = new Apps("Orbitz", new Apps("Moovit", 
      new Apps("mTicket", new Apps("Uber", new Folder("Travel")))));
  IAppSet foodApps = new Apps("Gong Cha", new Apps("B. Good", 
      new Apps("Grubhub", new Folder("Food"))));
}