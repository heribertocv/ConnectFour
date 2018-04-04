/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.io.*;
import java.net.*;

/**
 *
 * @author Haruso
 */
public class GameConnectFourServer {

    public static void main(String[] args)
    {
      final int numPlayers = 2;   
      
      ControllerGameConnectFour theGame;
      
      try
      {  
        Socket socketPlayers[];
        socketPlayers = new Socket[numPlayers];
        
        try{
            ServerSocket s = new ServerSocket(8189);

            System.out.println("Server for the game connect four is runnnig ");
            System.out.println("Waiting for players ...");

            for(int ii=0; ii<numPlayers; ++ii )
            {
               socketPlayers[ii] = s.accept();
               System.out.println("Player " + ii+1 + " entered to play");
            }

            System.out.println("All ready to play");

            theGame = new ControllerGameConnectFour(socketPlayers[0], socketPlayers[1]);
            theGame.play();
        
        }finally
        {
            for( Socket x : socketPlayers)
                x.close();
        }
      }catch (IOException e)
      {  
      }
    }
  
}
