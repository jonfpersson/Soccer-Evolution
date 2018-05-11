
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonfpersson
 */
public class Highscore {
 // An arraylist of the type "score" we will use to work with the scores inside the class
private ArrayList<Score> scores;

// The name of the file where the highscores will be saved
private static final String highscorefile = System.getProperty("user.home") + "\\Football game\\scores.dat";

//Initialising an in and outputStream for working with the file
ObjectOutputStream output = null;
ObjectInputStream input = null;

public Highscore() {
    //initialising the scores-arraylist
    scores = new ArrayList<Score>();
}
public ArrayList<Score> getScores() {
    loadScoreFile();
    sort();
    return scores;
}
private void sort() {
    CompareScore comparator = new CompareScore();
    Collections.sort(scores, comparator);
}
public void addScore(String name, int score) {
    loadScoreFile();
    scores.add(new Score(name, score));
    updateScoreFile();
}
public void loadScoreFile() {
    try {
        input = new ObjectInputStream(new FileInputStream(highscorefile));
        scores = (ArrayList<Score>) input.readObject();
    } catch (FileNotFoundException e) {
        System.out.println("[Laad] FNF Error: " + e.getMessage());
    } catch (IOException e) {
        System.out.println("[Laad] IO Error: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.out.println("[Laad] CNF Error: " + e.getMessage());
    } finally {
        try {
            if (output != null) {
                output.flush();
                output.close();
            }
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        }
    }
}
public void updateScoreFile() {
    try {
        File file = new File(highscorefile);
        file.getParentFile().mkdirs();
        output = new ObjectOutputStream(new FileOutputStream(highscorefile));
        output.writeObject(scores);
    } catch (FileNotFoundException e) {
        System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
    } catch (IOException e) {
        System.out.println("[Update] IO Error: " + e.getMessage());
    } finally {
        try {
            if (output != null) {
                output.flush();
                output.close();
            }
        } catch (IOException e) {
            System.out.println("[Update] Error: " + e.getMessage());
        }
    }
}
public String getHighscoreString() {
    String highscoreString = "";
       int max = 5;

    ArrayList<Score> scores;
    scores = getScores();

    int i = 0;
    int x = scores.size();
    if (x > max) {
        x = max;
    }
    while (i < x) {
        highscoreString += (i + 1) + ".\t" + scores.get(i).getname() + "\t" + scores.get(i).getScore() + "\n";
        i++;
    }
    return highscoreString;
}
public int getMinHighscore() {
       int max = 5;
       int minHighscore = 0;

    ArrayList<Score> scores;
    scores = getScores();

    int i = 0;
    int x = scores.size();
    if (x > max) {
        x = max;
    }
    while (i < x) {
        minHighscore = scores.get(i).getScore();
        i++;
    }
    return minHighscore;
}
}
