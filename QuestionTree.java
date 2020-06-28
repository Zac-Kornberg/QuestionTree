import java.io.*;
import java.util.*;
//Zachary Kornberg
//msci240 proj 3
// input is yes or no to all questions + possible file name
//output is console text
// code loads up previous tress from txt files and then asks the user questions until it makes a guess.
//If it is incorrect it adds the answer to its structure to get smarter

public class QuestionTree {

    private QuestionNode root;
    private Scanner input;
    private PrintStream output;
    private int games;
    private int wins;

    public QuestionTree(Scanner input,  PrintStream output ){
        root = new QuestionNode("Jedi");//If no load the first guess will be JEDI * answer node
        this.input = input;
        this.output = output;
        games = 0;
        wins = 0;
    }

    public void play() {
        root = playh(root);
        games++; //tracks games played
    }

    private QuestionNode playh(QuestionNode node){
        if (node.checkA()) { // If a node is an Answer node
        	
            output.print("Would your object happen to be " + node.data); //guess
            if (UserInterface.nextAnswer(input)) {  //if yes
                output.println("I win!"); 
                ++wins;//track computer wins
                
            } else { 
                node = learnedNode(node); // guessed wrong 
            }
        } else { 
        	
            output.print(node.data);//print question
      
            if (UserInterface.nextAnswer(input)) {
                node.yesNode = playh(node.yesNode); //get yes node
            } else {
                node.noNode = playh(node.noNode); //get no node
            }
        }
        return node;
    }
    

    public int totalGames(){
        return games;
    }

    public int gamesWon(){
        return wins;
    }

    private QuestionNode learnedNode(QuestionNode node){
        output.print("I lose. What is your object?");
        QuestionNode newNode = new QuestionNode (input.nextLine()); //object is new answer node
        output.print("Type a new question:");
        String data = input.nextLine();//get question
        
        output.print("What is the answer Y/N?");
        
        if (UserInterface.nextAnswer(input)) //for the new question is the new answer yes or no
               return new QuestionNode (data, newNode, node); 
        //if yes the new answer node is yes and the original incoreect guess is the no node
        else
               return new QuestionNode (data, node, newNode);//opposite original answer is yes new is no
    }


    public void save (PrintStream output){
        saveh(output, root);
    }

    private void saveh(PrintStream output, QuestionNode node){
        if (node.checkA()){ //if answer node
        	output.println("A:" + node.data); //print a: + answer
        } else {
        	output.println("Q:" + node.data + "?"); //print Q: + question + ?
            saveh(output, node.yesNode); //call the yes node until it reaches a answer 
            saveh(output, node.noNode); //call no node
        }
    }

    public void load (Scanner input){
        root = loadh (input);
    }

    private QuestionNode loadh (Scanner input){
        QuestionNode node = null; //new node
        if (input.hasNext()){	
            String data = input.nextLine();
        
            if (data.contains("?")){ //if its a answer node
            	 node = new QuestionNode (data.substring(2, data.length()),   loadh(input), loadh(input));
                 //make question node and make both the yes and no nodes under it
                // since the yes tree will always come first it populates the tree left to right
            } else { 
            	node = new QuestionNode(data.substring(2, data.length()));//make answer node
            }
        }
        return node;
    }

}