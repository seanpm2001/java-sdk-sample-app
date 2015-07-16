package org.familysearch.practice;

import org.familysearch.api.client.ft.FamilySearchFamilyTree;
import org.gedcomx.conclusion.Person;
import org.gedcomx.rs.client.PersonState;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.List;
import java.util.Scanner;

/**
 * Created by tyganshelton on 6/22/2015.
 */
public class App {

  private String username = "lyonrw"; //Put username here
  private String password = "1234pass"; //Put password here
  private String developerKey = "WCQY-7J1Q-GKVV-7DNM-SQ5M-9Q5H-JX3H-CMJK"; //Put developerKey here
  private String pid = null;
  private FamilySearchFamilyTree ft = null;

  private FamilySearchFamilyTree familySearchFamilyTree = null;

  public void setUp(CmdLineParser parser){
    this.familySearchFamilyTree = new FamilySearchFamilyTree(true)    //true signifies sandbox
            .authenticateViaOAuth2Password(username, password, developerKey).ifSuccessful()
            .get().ifSuccessful();
  }

  public void doMain() {

    System.out.println("Enter PID to look up:");
    Scanner scanner = new Scanner(System.in);
    pid = scanner.next();

    //Get person
    PersonState personState = familySearchFamilyTree.readPersonById(pid).ifSuccessful();
    Person person = personState.getPerson();///

    //Get Display Properties
    //Get name
    String personName = personState.getDisplayProperties().getName();
    System.out.println("You have requested the details for " + personName);

    //Get gender
    String gender = personState.getDisplayProperties().getGender();
    System.out.println("Gender: " + gender);

    //Get birth date
    String birthDate = personState.getDisplayProperties().getBirthDate();
    System.out.println("Birth date: " + birthDate);

    //Get birth place
    String birthPlace = personState.getDisplayProperties().getBirthPlace();
    System.out.println("Birth place: " + birthPlace);

    //Get marriage date
    String marriageDate = personState.getDisplayProperties().getMarriageDate();
    if (null != marriageDate) {
      System.out.println("Marriage date: " + marriageDate);
    }
    else {
      System.out.println("No marriage date");
    }

    //Get marriage place
    String marriagePlace = personState.getDisplayProperties().getMarriagePlace();
    if (null != marriagePlace) {
      System.out.println("Marriage place: " + marriagePlace);
    }
    else {
      System.out.println("No marriage place");
    }

    //Get death date
    String deathDate = personState.getDisplayProperties().getDeathDate();
    System.out.println("Death date: " + deathDate);

    //Get death place
    String deathPlace = personState.getDisplayProperties().getDeathPlace();
    System.out.println("Death place: " + deathPlace);

    //Get lifespan
    String lifeSpan = personState.getDisplayProperties().getLifespan();
    System.out.println("Lifespan: " + lifeSpan);

    //Get url
    System.out.println("URL: " + personState.getUri());

    //Get parents
    List<Person> parents = personState.readParents().getPersons();
    if (null != parents) {
      System.out.println(personName + " has " + parents.size() + " parent(s):");
      for (Person p : parents) {
        System.out.println(p.getName().getNameForm().getFullText());
      }
    } else {
      System.out.println(personName + " has no parents");
    }

    //Get spouses
    List<Person> spouses = personState.readSpouses().getPersons();
    if (null != spouses) {
      System.out.println(personName + " has " + spouses.size() + " spouse(s):");
      for (Person p : spouses) {
        System.out.println(p.getName().getNameForm().getFullText());
      }
    }
    else {
      System.out.println(personName + " has no spouses");
    }

    //Get children
    List<Person> children = personState.readChildren().getPersons();
    if (null != children) {
      System.out.println(personName + " has " + children.size() + " child(ren):");
      for (Person p : children) {
        System.out.println(p.getName().getNameForm().getFullText());
      }
    }
    else {
      System.out.println(personName + " has no children");
    }

//    //Get facts
//    for (Fact fact: person.getFacts()) {
//      System.out.println(fact.getKnownType() + ": ");
//      System.out.println(fact.getDate());
//      System.out.println(fact.getPlace());
//    };

    //Get living status
    System.out.println("Living: " + person.getLiving().toString());

    //Get Id
    System.out.println("ID: " + person.getId());

    //AncestryResultsState ancestryResultsState = personState.readAncestry();

   // ancestryResultsState.getSelfRel();
    //PersonState personState2 = ancestryResultsState.readPerson(0);
    //ancestryResultsState.
    //personState.readParents().readPerson();
    //personState.readParents().readParent();
  }

  public void try2(){
    //add a person
//    PersonState person2 = familySearchFamilyTree.addPerson(new Person()
//                    //named John Smith
//                    .name(new Name("Xohn Xmith", new NamePart(NamePartType.Given, "John"), new NamePart(NamePartType.Surname, "Smith")))
//                            //male
//                    .gender(GenderType.Male)
//                            //born in chicago in 1920
//                    .fact(new Fact(FactType.Birth, "1 January 1920", "Chicago, Illinois"))
//                            //died in new york 1980
//                    .fact(new Fact(FactType.Death, "1 January 1980", "New York, New York")),
//            //with a change message.
//            reason("Because I said so.")
//
//    );
//    System.out.println(person2.getPerson().getId());
    pid = "KWHK-FQR";
    PersonState personState = familySearchFamilyTree.readPersonById(pid).ifSuccessful();
    Person person = personState.getPerson();///

  }

  //Read the FamilySearch Family Tree
  public void readFamilyTree (String username, String password, String developerKey) {
    boolean useSandbox = true; //whether to use the sandbox reference.
    this.username = username;
    this.password = password;
    this.developerKey = developerKey;

    //read the Family Tree
    this.ft = new FamilySearchFamilyTree(useSandbox)
      //and authenticate.
      .authenticateViaOAuth2Password(username, password, developerKey);
  }

  public void readPersonByPersistentId () {

  }

  public void readPersonByFTId () {

  }



  public static void main(String[] args){
    App app = new App();
    CmdLineParser parser = new CmdLineParser(app);
    try {
      parser.parseArgument(args);
      app.setUp(parser);
      app.try2();
      //app.doMain();
    }
    catch (CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
