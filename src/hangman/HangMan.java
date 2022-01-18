/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;
import java.io.File;

/**
 *
 * @author Valentina
 */
public class HangMan {
     static String usedWords = "   ";
    static String word;
    static byte attempts = 0;
    static char[] face= {'q','(','X','_','X',')','p'};
    static char[] globalHiddenWord;
    static char[] globalWordToArray;
    static String globalHiddenWordToString;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          word = generateWord();
        globalHiddenWord = new char[word.length()];
        globalWordToArray  =  new char[word.length()];


        char userInput;
        for(int i =0; i<word.length(); i++){
            globalWordToArray[i] = word.charAt(i);
        }

        for (int i = 0; i < word.length(); i++) {
            globalHiddenWord[i] ='-';
        }

        System.out.println("Guess the secret word");
        System.out.println(globalHiddenWord);
       do {
        userInput = receiveUserInput();
        compareLetters(userInput);
        globalHiddenWordToString = new String(globalHiddenWord);
       } while(attempts < 7 && ! new String(globalHiddenWord).equals(word));

       if (attempts == 7){
           System.out.print("You Lose, the secret word is: " + word);
           System.exit(0);
       }
       System.out.print("You Win!!");
    }
    
     public static String generateWord () {
        String [] words = new String[12];
        String word = "";
        Random randgen = new Random();
        int i = 0;
        try {
            File infile = new File("diccionario.txt");
            Scanner pointer =  new Scanner(infile);    
            while (pointer.hasNextLine()) {
                words[i++] = pointer.nextLine();
            }
            int word_index = randgen.nextInt(words.length);
            word = words[word_index];
            pointer.close();
        } catch (FileNotFoundException err) {
            System.out.println("No se pudo abrir el archivo diccionario.txt");
        }
        return word;
    }


    public static char receiveUserInput(){
        //ASK INPUT TO USER
        char userInput;
        Scanner letter = new Scanner(System.in);
        System.out.println("Enter a letter");
        userInput= letter.next().charAt(0);
        return userInput;
    }

    public static void compareLetters(char userInput){
        boolean is_in = false;
        for(int i =0; i<word.length();i++){
            if(userInput==globalWordToArray[i]){
                globalHiddenWord[i] = userInput;
                is_in = true;
            }
        }
        System.out.print(globalHiddenWord);

        System.out.print("  ");//SPACE TO DISTANCE HIDDEN WORD AND FACE
        String characterToString = Character.toString(userInput);
        usedWords += characterToString;
        if (!is_in) {
            attempts++;
        }
        String faceToString = new String(face, 0, attempts);
        System.out.print(faceToString + "\t");
        System.out.println(usedWords);
    }
    
}
