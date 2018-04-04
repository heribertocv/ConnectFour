/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *  Implements the controller for the game connect four
 *  
 * @author gkwh cvh.cursos@gmail.com
 */
public class ControllerGameConnectFour
{
   ModelC4 theModelConnectFour;
   
   final char tokenPlayer1; 
   final char tokenPlayer2;
   
   Socket theSocketPlayer[];
    
    public ControllerGameConnectFour(Socket socketPlayer1, Socket socketPlayer2 ) {
        tokenPlayer1 = 'X';
        tokenPlayer2 = 'O';
        
        theSocketPlayer = new Socket[2];
        theSocketPlayer[0]= socketPlayer1;
        theSocketPlayer[1]= socketPlayer2;
        
        theModelConnectFour = new ModelC4();
    }
    
    private String captureNamePlayer(Socket channelComunication, char token){
        String namePlayer= "";
        
        try
        {
            InputStream inStream = channelComunication.getInputStream();
            OutputStream outStream = channelComunication.getOutputStream();
        
            Scanner in = new Scanner(inStream, "UTF-8");
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"),
                    true /* autoFlush */);

            out.println( "---------------------------------------------------------" );
            out.println( "\t=== Welcome to the fabulous Connect Four ===" );
            out.println( "---------------------------------------------------------" );

           
            out.println("Please insert your nickname: ");
            namePlayer = in.next().trim();

              
            out.println("\n\nYour nickname registered is : " + namePlayer + " and your token is "+ token);
            
            
            out.println("------------------------------\n\n");
        }
        catch (IOException e)
        {
        }
        return namePlayer;
    }
        
    private void initializeGame(){
        String namePlayer1;
        String namePlayer2;
        
        namePlayer1=captureNamePlayer(theSocketPlayer[0],tokenPlayer1);
        namePlayer2=captureNamePlayer(theSocketPlayer[1],tokenPlayer2);
        
        theModelConnectFour.createGame(namePlayer1, tokenPlayer1, namePlayer2, tokenPlayer2);
    }
    
    private String captureMove()
    {
        String theMove= "";
        try
        {
            Socket channelComunication = theSocketPlayer[ theModelConnectFour.getNumCurrentPlayer() ];
                    
            InputStream inStream = channelComunication.getInputStream();
            OutputStream outStream = channelComunication.getOutputStream();
        
            Scanner in = new Scanner(inStream, "UTF-8");
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(outStream, "UTF-8"),
                    true /* autoFlush */);

            out.println( "Your turn ..." );
            out.println( "Enter your move: " );
            
            theMove = in.next().trim();
        }
        catch (IOException e)
        {
        }
        return theMove;
    }

    private void updateViewers()
    {
        for(Socket channelComunication : theSocketPlayer ){
            try
            {           
                OutputStream outStream = channelComunication.getOutputStream();

                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(outStream, "UTF-8"),
                        true /* autoFlush */);

                String[] parts = theModelConnectFour.toString().split("\n");
                
                for(String x : parts)
                    out.println( x );

            }
            catch (IOException e)
            {
            }
        }
    }
     
    private boolean validateFormatMove( String theMove )
    {
        /** TODO: Using magic numbers for board dimension!!!
         *  please update to numColumns value defined at model.board
         */

        if ( !theMove.matches( "[1-8]" ) ) {
            System.out.println("\t*** Unknown format to move piece");
            return false;
        }
        return true;
    }
     
    public void play()
    {
        String theMove;
        
        initializeGame();
        
        while( !theModelConnectFour.getCurrentState().IsFinalState() )
        {
            updateViewers();
            
            theMove = captureMove();
        
            if( !validateFormatMove(theMove) ){
                /**
                 * TODO: Send error messsage to player
                 *      ERROR: Unknown format to move piece
                 *      expected number in range 1 to model.board.columns
                 */
                continue;
            }
            
            /**
             * TODO: Again, validate the update if is really made at the model
             */
            theModelConnectFour.updateState( Integer.parseInt(theMove), theModelConnectFour.getCurrentToken());
            
            System.out.println("\tCurrentState: "+theModelConnectFour.getCurrentToken());
            
        }
        
        ///Game is over, proclame the winner
        announceEndGame();
    }
     
    private void announceEndGame()
    {
        for(int ii=0;ii<2;++ii){
            try
            {           
                Socket channelComunication = theSocketPlayer[ii]; 
                OutputStream outStream = channelComunication.getOutputStream();

                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(outStream, "UTF-8"),
                        true /* autoFlush */);

                String messageToPlayer = StateGame.fromInt(ii + StateGame.WIN_PLAYER1.ordinal()) == theModelConnectFour.getCurrentState() ?
                                            "You WIN the game!!!" : "You LOST the game...";
                
                out.println( messageToPlayer );
            }
            catch (IOException e)
            {
            }
        }
        
    }
}
