package solution_1;

import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

public class Degenerator_file {
	
	 // Method to generate a random alphanumeric string of a given length
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    // Method to generate MD5 hash
    private static String generateMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


	public static void main(String[] args) {
		if (args.length != 2) {
            System.out.println("Usage: java MD5HashGenerator <PRN Number> <JSON File Path>");
            return;
        }

        String prnNumber = args[0].toLowerCase();  // Ensure PRN number is lowercase
        String jsonFilePath = args[1];

        try {
            // Parse the JSON file
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(jsonFilePath));

            // Get the destination value
            String destinationValue = (String) jsonObject.get("destination");
            if (destinationValue == null) {
                System.out.println("Error: 'destination' key not found in the JSON file.");
                return;
            }

            // Generate a random 8-character alphanumeric string
            String randomString = generateRandomString(8);

            // Concatenate the PRN number, destination value, and random string
            String concatenatedString = prnNumber + destinationValue + randomString;

            // Generate the MD5 hash
            String md5Hash = generateMD5Hash(concatenatedString);

            // Print the output in the format <hashed value>;<random string>
            System.out.println(md5Hash + ";" + randomString);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


	}

}
