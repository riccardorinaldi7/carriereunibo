package it.whiskstudio.utilities;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Utilities {

    public static List<String> inizializzaMazzo(String file) {
        List<String> deck = new ArrayList<String>();

        try {
            Resource resource = new ClassPathResource("static/" + file);
            InputStream myObj = resource.getInputStream();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);

                deck.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: FileNotFoundException.");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("An error occurred: IOException.");
            e.printStackTrace();
        }

        Collections.shuffle(deck);
        System.out.println(deck.toString());
        return deck;
    }

    /*public static List<String> inizializzaEsperienze() {
        List<String> deck = new ArrayList<String>();

        try {
            Resource resource = new ClassPathResource("static/CarteEsperienza.txt");
            File myObj = resource.getFile();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                //System.out.println(data);

                deck.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: FileNotFoundException.");
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("An error occurred: IOException.");
            e.printStackTrace();
        }

        Collections.shuffle(deck);
        System.out.println(deck.toString());
        return deck;
    }*/

    public static ArrayList<String> inizializzaColori() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("blue");
        list.add("yellow");
        list.add("green");
        list.add("cyan");
        list.add("black");
        return list;
    }
}
