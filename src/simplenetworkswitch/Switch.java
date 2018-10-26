package simplenetworkswitch;

import java.util.HashMap;

/**
 *
 * @author darrellpoleon
 */
public class Switch {
    
    /**
     * HashMap<address, port>
     */
    private final HashMap<String, Integer> routingTable;
    private final int numberOfPorts;
    
    /**
     * Switch Constructor
     * @param numberOfPorts 
     */
    public Switch(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
        this.routingTable = new HashMap<>();
    }
    
    /**
     * Update the routing table
     * @param address
     * @param port 
     */
    private void updateRoutingTable(String address, int port) {
        this.routingTable.put(address, port);
    }
    
    /**
     * Rely message to the destination machine
     * @param incomingPort
     * @param message 
     * @return  
     */
    public String relayMessage(int incomingPort, String message) {                
        Frame frame = new Frame(message, this.numberOfPorts);
        
        this.updateRoutingTable(frame.getSrc().toString(), incomingPort);

        String computedChecksum = this.computeChecksum(frame.getPayload());
        
        // Check if the computed checksum matches the checksum of the frame        
        if(!computedChecksum.equals(frame.getCs())) {
            System.out.println("ERROR");
            return null;
        }
        
        if (this.routingTable.containsKey(frame.getDest().toString())) {
            int outgoingPort = this.routingTable.get(frame.getDest().toString());
            
            if (incomingPort != outgoingPort) {
                // Store the destination port in the Send ports list
                frame.addSendPort(outgoingPort);   
            }            
        } else {
            this.floodFrame(incomingPort, frame);
        }
        
        System.out.println(frame);
        return frame.toString();
    }
    
    /**
     * forward the message on all the ports
     * @param incomingPort
     * @param frame 
     */
    private void floodFrame(int incomingPort, Frame frame) {
        for (int i = 0; i < numberOfPorts; i++) {
            if (i != incomingPort) {
                frame.addSendPort(i);                
            }
        }
    }
    
    /**
     * Compute the checksum
     * @param input
     * @return
     */
    private String computeChecksum(String input) {                        
        StringBuilder checksumInput = new StringBuilder(input);
        int checksum = 0;

        for (int i = 0; i < checksumInput.length(); i+= 2) {
            checksum += Integer.parseInt(checksumInput.substring(i, i + 2), 16);
        }

        //  Convert Decimal to Hex
        String checksumHex = Integer.toHexString(checksum % 128);
        
        return checksumHex.length() > 1 ? checksumHex : "0" + checksumHex;
    }
    
}
