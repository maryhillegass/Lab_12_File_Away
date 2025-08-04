import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {
        /*
        Open text file in JFileChooser in src
        Open and read line by line
        Echo lines to the screen
        Print a summary report
            Name of the file
            # lines
            # words
            # char
         */
        //JFileChooser
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        ArrayList<String> lines = new ArrayList<>();

        try
        {
            //Getting the working directory
            File workingDirectory = new File(System.getProperty("user.dir"));

            //putting the chooser by default in the wd
            chooser.setCurrentDirectory(workingDirectory);

            //All the work goes in the if statement in case that user doesn't pick a file
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                //actually setting the file to selected file
                selectedFile = chooser.getSelectedFile();
                //getting the file path
                Path file = selectedFile.toPath();

                //I'm not sure that I understand the logic here.
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader((new InputStreamReader(in)));

                //Onto reading the file start at line 0
                int line = 0;
                int word = 0;
                int character = 0;
                while (reader.ready())
                {
                    //read the line
                    rec = reader.readLine();
                    //add to the array list
                    lines.add(rec);
                    //increment the lines counter
                    line++;
                    //print the read line to the screen
                    System.out.printf("\nLine %4d %-60s ", line, rec);


                    //number of words
                    String words[] = rec.split("\\W");
                    word = word + words.length;

                    //number of characters.
                    character = character + rec.length();
                }
                reader.close();

                //Summary
                System.out.println("\nRead file: " + selectedFile.getName());
                System.out.printf("File has %d lines, %d words, and %d characters.\n", line, word, character);

            }
            else //not file selected and dialog closed
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }   //end of try
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
