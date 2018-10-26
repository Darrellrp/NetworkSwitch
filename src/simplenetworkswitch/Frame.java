package simplenetworkswitch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author darrellpoleon
 */
public class Frame {
    
    private final String message;
    
    private final StringBuilder dest;
    private final StringBuilder src;
    private final int len;
    private final String payload;
    private final String cs;
    
    private final List<Integer> portsSendOn;
    
    /**
     * Frame constructor
     * @param message
     * @param numberOfPorts 
     */
    public Frame(String message, int numberOfPorts) {
        this.message = message;
        
        StringBuilder frame = new StringBuilder(message);
                
        this.dest = new StringBuilder(frame.substring(0, 4));
        this.src = new StringBuilder(frame.substring(4, 8));            
        this.len = Integer.valueOf(frame.substring(8, 12), 16);
        this.payload = frame.substring(12, frame.length() - 2);
        this.cs = frame.substring(frame.length() - 2, frame.length());
        
        this.portsSendOn = new ArrayList(numberOfPorts);
    }
    
    /**
     * Add the port that the frame is send on
     * @param port 
     */
    public void addSendPort(int port) {
        portsSendOn.add(port);
    }
        
    public String getMessage() {
        return message;
    }

    public StringBuilder getDest() {
        return dest;
    }

    public StringBuilder getSrc() {
        return src;
    }

    public int getLen() {
        return len;
    }

    public String getPayload() {
        return payload;
    }

    public String getCs() {
        return cs;
    }

    public List<Integer> getPortsSendOn() {
        return portsSendOn;
    }
    
    @Override
    public String toString() {
        String output = message;
        
        for (int i = 0; i < portsSendOn.size(); i++) {
            output += " " + portsSendOn.get(i);
        }
        
        return output;
    }
}
