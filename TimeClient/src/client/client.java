package client;

import java.io.*;
import java.net.*;

/**
 *
 * @author Sukhmeet
 */
public class client implements Runnable {
    
    InputStream input;
    OutputStream output;
    Socket clientSocket ;
    servermessagehandler.serverMessageHandler myServerMessageHandler;
    userinterface.userInterface myUI;
    boolean stopTask = false;
    int portNumber = 7777;
    boolean serverConnected = false;
    String hostAddress;
    
    public client(int portNumber, clientgui.clientGUI myUI){
        this.portNumber = portNumber;
        this.myUI = myUI;
        this.myServerMessageHandler = new servermessagehandler.serverMessageHandler(this);
    }
  
    
    public void connectToServer(){
        if (clientSocket != null){
            sendMessage2UI("Client is already connected to server");
        } else {
            try{
                serverConnected = true;
                //hostAddress = "192.168.1.121";
                clientSocket = new Socket(hostAddress,portNumber);
                input = clientSocket.getInputStream();
                output = clientSocket.getOutputStream();
                Thread myRead = new Thread(this);
                myRead.start();
                stopThread();
                myUI.update("Connection to Server was successful \n ");
            } catch(IOException ioe){
                myUI.update("Connection to Server was successful \n " );
            }
        }
    }
    
    public void disconnectServer(){
        try{
            stopTask=true; // to stop the run to  read input.
            serverConnected = false;
            input = null;
            output = null;
            clientSocket = null;
            myUI.update("Disconnect from Server was successful \n ");
        }  catch(NullPointerException npe){
            sendMessage2UI("The server is not connected.");
        }
    }
    
    public void quit(){
        try{
            disconnectServer();
        } catch(NullPointerException npe){
            sendMessage2UI("The Server has already been disconnected:");
        } finally{
            System.exit(0);
        }
    }
    
    public void getTime(){
        try{
            sendMessage2Server((byte) 't');
            sendMessage2Server((byte)0xFFFF);
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED1on(){
        try{
            sendStringMessageToServer("L1on");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED2on(){
        try{
            sendStringMessageToServer("L2on");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED3on(){
        try{
            sendStringMessageToServer("L3on");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED4on(){
        try{
            sendStringMessageToServer("L4on");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED1off(){
        try{
            sendStringMessageToServer("L1off");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED2off(){
        try{
            sendStringMessageToServer("L2off");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED3off(){
        try{
            sendStringMessageToServer("L3off");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void LED4off(){
        try{
            sendStringMessageToServer("L4off");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void Button1(){
        try{
            sendStringMessageToServer("gpb1");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void Button2(){
        try{
            sendStringMessageToServer("gpb2");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void Button3(){
        try{
            sendStringMessageToServer("gpb3");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void GetTemp(){
        try{
            sendStringMessageToServer("getTemp");
        }catch(NullPointerException npe){
            sendMessage2UI("The server is not connected!");
            //myUI.update("Good Time Received from server");
        }
    }
    
    public void sendMessage2Server(byte msg){
        try{
            output.write(msg);
            output.flush();
        } catch(IOException e){
            sendMessage2UI(e.getMessage());
            System.exit(0);
        } 
    }
    
    
    public void sendStringMessageToServer(String theMessage) {
        for (int i = 0; i < theMessage.length(); i++) {
            byte msg = (byte) theMessage.charAt(i);
            sendMessage2Server(msg);
        }
        sendMessage2Server((byte)0xFF);
    }
    
    public void stopThread(){
        stopTask = false;
    }
    
    public void sendMessage2UI(String msg){
        myUI.update(msg);
    }
    
    public void setPort(int portNumb){
        portNumber = portNumb;
    }
    
    public int getPort(){
        return this.portNumber;
    }
    
    public boolean serverConnected(){
        return serverConnected;
    }
    
    @Override
    public void run(){
        byte msg;
        String theString;
        while(stopTask == false){
            try{
                msg = (byte) input.read();
                theString = Character.toString((char)msg);
                myServerMessageHandler.messageHandler(this,theString);
            } catch(IOException ioe){
                sendMessage2UI(ioe.getMessage());
            } 
        }
    }
}

