import tester.Tester;

// a piece of media
interface IMedia {
// is this media really old?
  boolean isReallyOld();

// are captions available in this language?
  boolean isCaptionAvailable(String language);

// a string showing the proper display of the media
  String format();
}

//Abstract class that implements Media
abstract class AMedia implements IMedia {
  String title;
  ILoString captionOptions;

  AMedia(String title, ILoString captionOptions) {
    this.title = title;
    this.captionOptions = captionOptions;
  }

  public boolean isReallyOld() {
    return false;
  }

  public boolean isCaptionAvailable(String language) {
    return this.captionOptions.captionEquals(language);
  }

  public abstract String format();
}

// represents a movie
class Movie extends AMedia {
  int year;

  Movie(String title, int year, ILoString captionOptions) {
    super(title, captionOptions);
    this.year = year;
  }

  public boolean isReallyOld() {
    return this.year < 1930;
  }

  public String format() {
    return this.title + " " + "(" + this.year + ")";
  }
}

// represents a TV episode
class TVEpisode extends AMedia {
  String showName;
  int seasonNumber;
  int episodeOfSeason;

  TVEpisode(String title, String showName, int seasonNumber, int episodeOfSeason,
      ILoString captionOptions) {
    super(title, captionOptions);
    this.showName = showName;
    this.seasonNumber = seasonNumber;
    this.episodeOfSeason = episodeOfSeason;
  }

  public String format() {
    return this.showName + " " + String.valueOf(this.seasonNumber) + "."
        + String.valueOf(this.episodeOfSeason) + " - " + this.title;
  }
}

// represents a YouTube video
class YTVideo extends AMedia {
  String channelName;

  public YTVideo(String title, String channelName, ILoString captionOptions) {
    super(title, captionOptions);
    this.channelName = channelName;
  }

  public String format() {
    return this.title + " by " + this.channelName;
  }
}

// lists of strings
interface ILoString {
// Determines if the caption equals any of the strings within the list
  boolean captionEquals(String caption);
}

// an empty list of strings
class MtLoString implements ILoString {
// Determines if the caption equals any of the strings within the list
  public boolean captionEquals(String caption) {
    return false;
  }
}

// a non-empty list of strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

// Determines if the caption equals any of the strings within the list
  public boolean captionEquals(String caption) {
    return this.first.equals(caption) || this.rest.captionEquals(caption);
  }
}

class ExamplesMedia {
// Constructor
  ExamplesMedia() {
  }

  ILoString englishOnly = new ConsLoString("English", new MtLoString());
  ILoString englishSpanish = new ConsLoString("Spanish", englishOnly);
  ILoString englishSpanishItalian = new ConsLoString("Italian", englishSpanish);
  ILoString englishItalian = new ConsLoString("Italian", englishOnly);
  IMedia martian = new Movie("The Martian", 2015, englishSpanishItalian);
  IMedia theJazzSinger = new Movie("The Jazz Singer", 1927, new MtLoString());
  IMedia sealTeamSOneEpTwo = new TVEpisode("Other Lives", "Seal Team", 1, 2, englishOnly);
  IMedia masterChefJrSFourEpFour = new TVEpisode("The Good the Bad & the Smelly",
      "MasterChef Junior", 4, 4, englishSpanishItalian);
  IMedia f1Video = new YTVideo("Lando Norris's Best Moments of 2022!", "Formula 1", englishItalian);
  IMedia flightVideo = new YTVideo("21 Hour Day as a 747 Pilot", "74 Gear", new MtLoString());
}
