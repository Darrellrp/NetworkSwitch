package simplenetworkswitch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author darrellpoleon
 */
public class SimpleNetworkSwitch {
    
    private static int argsIndex = 2;
    private static final String INPUT_FILE_PATH = "switch-case.input";
    private static final String OUTPUT_FILE_PATH = "switch-case.output";
    private static final List<String> SWITCH_OUTPUT = new ArrayList<>();
    private static List<String> fileOutput = null;
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {  
        Scanner input = new Scanner(new File(INPUT_FILE_PATH));
        
        int numberOfLinks = Integer.parseInt(input.nextLine());
        int numberOfMessages = Integer.parseInt(input.nextLine());
        
        Switch _switch = new Switch(numberOfLinks);
        
        // Reley Messages
        for (int i = 0; i < numberOfMessages; i++) {            
            int incomingPort = Integer.parseInt(input.next());
            String frame = input.next();
            
            String output = _switch.relayMessage(incomingPort, frame);
            
            SWITCH_OUTPUT.add(output);
            argsIndex += 2;
        }                        
        
        if (validateSwitchOutput()) {
            System.out.println("The frames were forwarded correctly!");
        } else {
            System.out.println("he frames were forwarded incorrectly..");
        }
    }
    
    public static List<String> getOutputFile() throws IOException {
        return Files.readAllLines(Paths.get(OUTPUT_FILE_PATH));
    }
    
    public static boolean validateSwitchOutput() throws IOException {
        boolean isValid = true;
        fileOutput = getOutputFile();
        
        for (int i = 0; i < SWITCH_OUTPUT.size(); i++) {
            if (!SWITCH_OUTPUT.get(i).equals(fileOutput.get(i))) {
                isValid = false;
            }
        }
        
        return isValid;
    }
    
}
