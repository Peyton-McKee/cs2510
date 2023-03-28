import tester.*;

interface IXML {
  // counts the length of all the text
  int contentLength();

  // determines whether or not a tag has the given name
  boolean hasTag(String tagName);

  // determines whether or not an attribute has the given name
  boolean hasAttribute(String attName);

  // transforms the xml file to be a string of all the plaintext
  String renderAsString();
}

/*
 * Template
 * Fields
 * -- txt -- String
 * Methods
 * -- this.contentLength -- int
 * -- this.hasTag -- boolean
 * -- this.hasAttribute -- boolean
 * -- this.renderAsString -- String
 */
class Plaintext implements IXML {
  String txt;

  // constructor
  public Plaintext(String txt) {
    this.txt = txt;
  }

  // determines whether or not a tag has the given name
  public int contentLength() {
    return txt.length();
  }

  // determines whether or not a tag has the given name
  public boolean hasTag(String tagName) {
    return false;
  }

  // determines whether or not an attribute has the given name
  public boolean hasAttribute(String attName) {
    return false;
  }

  // transforms the xml file to be a string of all the plaintext
  public String renderAsString() {
    return txt;
  }
}

/*
 * Template
 * Fields
 * -- content -- ILoXML
 * Methods
 * -- this.contentLength -- int
 * -- this.hasTag -- boolean
 * -- this.hasAttribute -- boolean
 * -- this.renderAsString -- String
 * Methods of Fields
 * -- content.contentLength -- int
 * -- content.hasTag -- boolean
 * -- content.hasAttribute -- boolean
 * -- content.renderAsString -- String
 */
class Untagged implements IXML {
  ILoXML content;

  // constructor
  public Untagged(ILoXML content) {
    this.content = content;
  }

  // counts the length of all the text
  public int contentLength() {
    return this.content.contentLength();
  }

  // determines whether or not a tag has the given name
  public boolean hasTag(String tagName) {
    return this.content.hasTag(tagName);
  }

  // determines whether or not an attribute has the given name
  public boolean hasAttribute(String attName) {
    return this.content.hasAttribute(attName);
  }

  // transforms the xml file to be a string of all the plaintext
  public String renderAsString() {
    return this.content.renderAsString();
  }
}

/*
 * Template
 * Fields
 * -- tag -- Tag
 * -- content -- ILoXML
 * Methods
 * -- this.contentLength -- int
 * -- this.hasTag -- boolean
 * -- this.hasAttribute -- boolean
 * -- this.renderAsString -- String
 * Fields of Methods
 * -- content.contentLength -- int
 * -- content.hasTag -- boolean
 * -- content.hasAttribute -- boolean
 * -- content.renderAsString -- String
 */
class Tagged implements IXML {
  Tag tag;
  ILoXML content;

  // constructor
  public Tagged(Tag tag, ILoXML content) {
    this.tag = tag;
    this.content = content;
  }

  // counts the length of all the text
  public int contentLength() {
    return content.contentLength();
  }

  // determines whether or not a tag has the given name
  public boolean hasTag(String tagName) {
    return this.tag.hasTag(tagName) || this.content.hasTag(tagName);
  }

  // determines whether or not an attribute has the given name
  public boolean hasAttribute(String attName) {
    return this.tag.hasAttribute(attName) || this.content.hasAttribute(attName);
  }

  // transforms the xml file to be a string of all the plaintext
  public String renderAsString() {
    return this.content.renderAsString();
  }
}

interface ILoXML {

  // counts the length of all the text
  int contentLength();

  // determines whether or not a tag has the given name
  boolean hasTag(String tagName);

  // determines whether or not an attribute has the given name
  boolean hasAttribute(String attName);

  String renderAsString();
}

/*
 * Template
 * Methods
 * -- this.contentLength -- int
 * -- this.hasTag -- boolean
 * -- this.hasAttribute -- boolean
 * -- this.renderAsString -- String
 */
class MtLoXML implements ILoXML {

  // constructor
  public MtLoXML() {
  }

  // counts the length of all the text
  public int contentLength() {
    return 0;
  }

  // determines whether or not a tag has the given name
  public boolean hasTag(String tagName) {
    return false;
  }

  // determines whether or not an attribute has the given name
  public boolean hasAttribute(String attName) {
    return false;
  }

  // transforms the xml file to be a string of all the plaintext
  public String renderAsString() {
    return "";
  }
}

/*
 * Template
 * Fields
 * -- first -- IXML
 * -- Second -- ILoXML
 * Methods
 * -- this.contentLength -- int
 * -- this.hasTag -- boolean
 * -- this.hasAttribute -- boolean
 * -- this.renderAsString -- String
 * Methods of Fields
 * -- first.contentLength -- int
 * -- first.hasTag -- boolean
 * -- first.hasAttribute -- boolean
 * -- first.renderAsString -- String
 * -- rest.contentLength -- int
 * -- rest.hasTag -- boolean
 * -- rest.hasAttribute -- boolean
 * -- rest.renderAsString -- String
 */
class ConsLoXML implements ILoXML {
  IXML first;
  ILoXML rest;

  // constructor
  public ConsLoXML(IXML first, ILoXML rest) {
    this.first = first;
    this.rest = rest;
  }

  // counts the length of all the text
  public int contentLength() {
    return this.first.contentLength() + this.rest.contentLength();
  }

  // determines whether or not a tag has the given name
  public boolean hasTag(String tagName) {
    return this.first.hasTag(tagName) || this.rest.hasTag(tagName);
  }

  // determines whether or not an attribute has the given name
  public boolean hasAttribute(String attName) {
    return this.first.hasAttribute(attName) || this.rest.hasAttribute(attName);
  }

  // transforms the xml file to be a string of all the plaintext=
  public String renderAsString() {
    return this.first.renderAsString() + this.rest.renderAsString();
  }
}

/*
 * Template
 * Fields
 * -- name -- String
 * -- atts -- ILoAtt
 * Methods
 * -- this.hasAttribute -- boolean
 * -- this.hasTag -- boolean
 * Methods of children
 * -- atts.hasAttribute -- boolean
 */
class Tag {
  String name;
  ILoAtt atts;

  // constructor
  public Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }

  boolean hasAttribute(String attName) {
    return this.atts.hasAttribute(attName);
  }

  boolean hasTag(String tagName) {
    return this.name.equals(tagName);
  }
}

/*
 * Template
 * Fields
 * -- name -- String
 * -- value -- String
 * Methods
 * -- hasAttribute -- boolean
 */
class Att {
  String name;
  String value;

  // constructor
  public Att(String name, String value) {
    this.name = name;
    this.value = value;
  }

  // determines whether the attribute's name is the same as the given name
  public boolean hasAttribute(String attName) {
    return this.name.equals(attName);
  }
}

interface ILoAtt {
  // determines whether the list of attributes has an attribute with the given
  // name
  boolean hasAttribute(String attName);
}

/*
 * Template
 * Methods
 * -- hasAttribute -- boolean
 */
class MtLoAtt implements ILoAtt {

  // constructor
  public MtLoAtt() {
  }

  // determines whether the list of attributes has an attribute with the given
  // name
  public boolean hasAttribute(String attName) {
    return false;
  }
}

/*
 * Template
 * Fields
 * -- first -- Att
 * -- rest -- ILoAtt
 * Methods
 * -- this.hasAttribute -- boolean
 * Methods of fields
 * -- first.hasAttribute -- boolean
 * -- rest.hasAttribute -- boolean
 */
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;

  // constructor
  public ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }

  // determines whether the list of attributes has an attribute with the given
  // name
  public boolean hasAttribute(String attName) {
    return this.first.hasAttribute(attName) || this.rest.hasAttribute(attName);
  }
}

//Class to show examples of xml instances
class ExamplesXML {
  IXML xml1 = new Plaintext("I am XML!");
  IXML xml2 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      new ConsLoXML(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXML(new Plaintext("XML"), new MtLoXML())),
          new ConsLoXML(new Plaintext("!"), new MtLoXML()))));
  IXML xml3 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      new ConsLoXML(
          new Tagged(new Tag("yell", new MtLoAtt()),
              new ConsLoXML(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXML(new Plaintext("X"), new MtLoXML())),
                  new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
          new ConsLoXML(new Plaintext("!"), new MtLoXML()))));
  IXML xml4 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      new ConsLoXML(
          new Tagged(new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), new MtLoAtt())),
              new ConsLoXML(
                  new Tagged(new Tag("italic", new MtLoAtt()),
                      new ConsLoXML(new Plaintext("X"), new MtLoXML())),
                  new ConsLoXML(new Plaintext("ML"), new MtLoXML()))),
          new ConsLoXML(new Plaintext("!"), new MtLoXML()))));

  ILoXML mtxml = new MtLoXML();
  Tag tag = new Tag("yell", new ConsLoAtt(new Att("volume", "30db"), new MtLoAtt()));
  Att att = new Att("volume", "30db");
  ILoAtt consAtt = new ConsLoAtt(att, new MtLoAtt());
  ILoAtt mtAtt = new MtLoAtt();

  // tests to see if the contentLength function works
  boolean testContentLengthPlaintext(Tester t) {
    return t.checkExpect(xml1.contentLength(), 9);
  }

  // tests to see if the hasTag function works
  boolean testHasTagPlaintext(Tester t) {
    return t.checkExpect(xml1.hasTag("uhh"), false);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributePlaintext(Tester t) {
    return t.checkExpect(xml1.hasAttribute("uhhh"), false);
  }

  // tests to see if the renderAsString function works
  boolean testRenderAsStringPlaintext(Tester t) {
    return t.checkExpect(xml1.renderAsString(), "I am XML!");
  }

  //tests to see if the contentLength function works
  boolean testContentLengthUntagged(Tester t) {
    return t.checkExpect(xml2.contentLength(), 9);
  }

  // tests to see if the hasTag function works
  boolean testHasTagUntagged(Tester t) {
    return t.checkExpect(xml2.hasTag("uhh"), false);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeUntagged(Tester t) {
    return t.checkExpect(xml2.hasAttribute("uhhh"), false);
  }

  // tests to see if the renderAsString function works
  boolean testRenderAsStringUnTagged(Tester t) {
    return t.checkExpect(xml2.renderAsString(), "I am XML!");
  }

  //tests to see if the contentLength function works
  boolean testContentLengthTagged(Tester t) {
    return t.checkExpect(xml3.contentLength(), 9);
  }

  // tests to see if the hasTag function works
  boolean testHasTagTagged(Tester t) {
    return t.checkExpect(xml3.hasTag("uhh"), false) && t.checkExpect(xml3.hasTag("yell"), true);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeTagged(Tester t) {
    return t.checkExpect(xml3.hasAttribute("uhhh"), false);
  }

  // tests to see if the renderAsString function works
  boolean testRenderAsStringTagged(Tester t) {
    return t.checkExpect(xml3.renderAsString(), "I am XML!");
  }

  //tests to see if the contentLength function works
  boolean testContentLengthMtLoXML(Tester t) {
    return t.checkExpect(mtxml.contentLength(), 0);
  }

  // tests to see if the hasTag function works
  boolean testHasTagMtLoXML(Tester t) {
    return t.checkExpect(mtxml.hasTag("uhh"), false);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeMtLoXML(Tester t) {
    return t.checkExpect(mtxml.hasAttribute("uhhh"), false);
  }

  // tests to see if the renderAsString function works
  boolean testRenderAsStringMtLoXML(Tester t) {
    return t.checkExpect(mtxml.renderAsString(), "");
  }

  //tests to see if the contentLength function works
  boolean testContentLengthConsLoXML(Tester t) {
    return t.checkExpect(xml4.contentLength(), 9);
  }

  // tests to see if the hasTag function works
  boolean testHasTagsConsLoXML(Tester t) {
    return t.checkExpect(xml4.hasTag("uhh"), false) && t.checkExpect(xml4.hasTag("yell"), true);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeConsLoXML(Tester t) {
    return t.checkExpect(xml4.hasAttribute("uhhh"), false)
        && t.checkExpect(xml4.hasAttribute("volume"), true);
  }

  // tests to see if the renderAsString function works
  boolean testRenderAsStringConsLoXML(Tester t) {
    return t.checkExpect(xml4.renderAsString(), "I am XML!");
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeTag(Tester t) {
    return t.checkExpect(tag.hasAttribute("uhhh"), false)
        && t.checkExpect(tag.hasAttribute("volume"), true);
  }

  // tests to see if hasTag function works
  boolean testHasTagTag(Tester t) {
    return t.checkExpect(tag.hasTag("uhh"), false) && t.checkExpect(tag.hasTag("yell"), true);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeAtt(Tester t) {
    return t.checkExpect(att.hasAttribute("uhhh"), false)
        && t.checkExpect(att.hasAttribute("volume"), true);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeConsLoAtt(Tester t) {
    return t.checkExpect(consAtt.hasAttribute("uhhh"), false)
        && t.checkExpect(consAtt.hasAttribute("volume"), true);
  }

  // tests to see if the hasAttribute function works
  boolean testHasAttributeMtLoAtt(Tester t) {
    return t.checkExpect(mtAtt.hasAttribute("uhh"), false);
  }
}