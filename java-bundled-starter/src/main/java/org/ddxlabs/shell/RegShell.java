package org.ddxlabs.shell;

import java.util.Scanner;

/**
 * Created on 5/18/2019.
 */
public class RegShell {

    public static void main(String[] args) {
        String text = "Lorem epsum.";
        System.out.println(text);
        System.out.println("");
        System.out.print("> ");

        //BufferedReader lineOfText = new BufferedReader(new InputStreamReader(System.in));
        //String textLine = lineOfText.readLine();

        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        System.out.println("");
        System.out.println("Processing " + s);
    }
}
