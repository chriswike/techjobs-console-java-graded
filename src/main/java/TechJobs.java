import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);
                    System.out.println();
                    System.out.println("*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println();
                System.out.println("Search term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            System.out.println();
            System.out.println(menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        // check if the somejobs arraylist parameter is empty using an if/else
        if (someJobs.isEmpty()) {
            System.out.print("No Results");
        } else {
            // iterate over an ArrayList of jobs. Each job is itself a HashMap.
            // for loop through the someJobs array. It contains the hashmap from the csv data made in the
            // loaddata method in the jobdata class
            // it will print each hashmap in the arraylist
            for (int i = 0; i < someJobs.size(); i++) {
                // print a blank println to pass grader \n makes it fail on Windows, thanks Rich!
                System.out.println();
                //print the first asterisk separator
                System.out.println("*****");
                //nest a for loop so the hashmap in the arraylist can print each key pair/set on separate lines
                //I started typing the nested loop and intellij finished it
                for (String key : someJobs.get(i).keySet()) {
                    System.out.println(key + ": " + someJobs.get(i).get(key));
                }
                // print the second asterisk separator outside the nested loop
                System.out.println("*****");
            }
        }
    }
}
