package servermessagehandler;
/**
 *
 * @author Sukhmeet
 */
public class serverMessageHandler {
    String theCommand = "";
    
    public serverMessageHandler(client.client myClient) {
    }
    
    public void messageHandler(client.client myClient,String theMsg){
        if (theMsg.charAt(0) != 0xFFFF) { //Character.toString((char(-1)) = 0xFFF)
            theCommand += theMsg;
        } else {
            handleCompleteServerMessage(myClient, theCommand);
            theCommand = "";
        }
    }
    
    public void handleCompleteServerMessage(client.client myClient,String theMsg){
        myClient.sendMessage2UI("Server Acknowledged:");
        myClient.sendMessage2UI(theMsg);
    }    
}
