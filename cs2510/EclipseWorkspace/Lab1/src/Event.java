
class Date {
  int day;
  int month;
  int year;
  
  Date(int day, int month, int year){
    this.day = day;
    this.month = month;
    this.year = year;
  }
}

class Event {
  String title;
  Date date;
  Person host;
  
  Event(String title, Date date, Person host) {
    this.title = title;
    this.date = date;
    this.host = host;
  }
}

class ExamplesEvent {
  ExamplesPerson ep = new ExamplesPerson();
  Event event1 = new Event("Event 1", new Date(10, 12, 2022), ep.tim);
  Event event2 = new Event("Event 2", new Date(12, 8, 2022), ep.kate);
  
}