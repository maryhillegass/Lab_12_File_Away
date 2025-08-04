import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        /*
        •	First Name
        •	Last Name
        •	ID Number (a zero replaced string of 6 digits 000001, 000002, etc.)
        •	Email
        •	Year of Birth (a four digit integer 1978, etc.)
Here is a sample CSV record:
    Bilbo, Baggins, 000001, BBaggins@shire.net, 1044
         */
        ArrayList<String> lines = new ArrayList<>();
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        int line = 0;
        boolean done = false;
        Scanner in = new Scanner(System.in);
        //get the data, as many lines as the user wants
        do
        {
            line++;
            String rec;
            String firstName = SafeInput.getNonZeroLenString(in,"First name");
            String lastName = SafeInput.getNonZeroLenString(in,"Last name");
            String IDNumber = SafeInput.getRegExString(in, "ID Number", "^\\d{6}$");
            String email = SafeInput.getRegExString(in, "Email", EMAIL_REGEX);
            int birthYear = SafeInput.getRangedInt(in, "4 digit Birth Year", 1000, 9999);
            rec = firstName + ", " + lastName + ", " + IDNumber + ", " + email + ", " + birthYear;
            System.out.println(rec);
            lines.add(rec);
            done = !SafeInput.getYNConfirm(in, "Would you like to enter another record?");
        }
        while(!done);

        String fileName = SafeInput.getNonZeroLenString(in, "Enter a file name") + ".csv";
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!
            for(String a : lines)
            {
                writer.write(a, 0, a.length());
                writer.newLine();
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
       catch (IOException e)
       {
            e.printStackTrace();
       }
    }
}


