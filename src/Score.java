
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonfpersson
 */
public class Score implements Serializable {
private int score;
private String name;

public Score() {

}

public int getScore() {
    return score;
}

public String getname() {
    return name;
}

public Score(String name, int score) {
    this.score = score;
    this.name = name;
}
    
}
