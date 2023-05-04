package usercommandhandler;

/**
 *
 * @author Sukhmeet
 */
public class userCommandHandler implements Runnable{
    userinterface.userInterface myUI;
    client.client myClient;
    String theCommand = "";
    
     public userCommandHandler(userinterface.userInterface myUI,client.client myClient) {
        this.myUI = myUI;
        this.myClient = myClient;
    }
     
     public void handleUserCommand(String theCommand){
         this.theCommand = theCommand;
         Thread myCommandThread = new Thread(this);
         myCommandThread.start();
     }

    @Override
    public void run() {
        switch(Integer.parseInt(theCommand)){
            case 1:
                myUI.update("Quit program by the user command!");
                myClient.sendMessage2Server((byte) 'q');
                myClient.sendMessage2Server((byte)0xFF);
                myClient.quit();
                break;
            case 2:
                myClient.connectToServer();
                break;
            case 3:
                myClient.sendMessage2Server((byte) 'd');
                myClient.sendMessage2Server((byte)0xFF);
                myClient.disconnectServer();
                break;
            case 4:
                myClient.getTime();
                break;
            case 5:
                myClient.LED1on();
                break;
            case 6:
                myClient.LED2on();
                break;
            case 7:
                myClient.LED3on();
                break;
            case 8:
                myClient.LED4on();
                break;
            case 9:
                myClient.LED1off();
                break;
            case 10:
                myClient.LED2off();
                break;
            case 11:
                myClient.LED3off();
                break;
            case 12:
                myClient.LED4off();
                break;
            case 13:
                myClient.Button1();
                break;
            case 14:
                myClient.Button2();
                break;
            case 15:
                myClient.Button3();
            case 16:
                myClient.GetTemp();
            break;
        }
    }
}
