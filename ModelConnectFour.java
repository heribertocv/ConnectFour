/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

enum StateGame {
     TURN_PLAYER1, TURN_PLAYER2, WIN_PLAYER1 , WIN_PLAYER2, UNKNOW,  INITIAL;

    public static StateGame fromInt(int i)
    {
        return StateGame.values()[i];
    }
    
    public boolean IsFinalState()
    {
        return ( this.equals( WIN_PLAYER1 )  || this.equals( WIN_PLAYER2 ) );
    }
        
};

/**
 *
 * @author gkwh <cvh.cursos at gmail.com>
 */
public class ModelConnectFour {
    BoardConnectFour theBoard;
    Player thePlayers[];

    StateGame theCurrentState;
    
    ModelConnectFour()
    {
        thePlayers = new Player[2];
        theCurrentState = StateGame.UNKNOW;
    }
            
    void createGame(String namePlayer1, char token1, String namePlayer2, char token2)
    {
        thePlayers[0] = new Player(namePlayer1, token1);
       
        thePlayers[1] = new Player(namePlayer2, token2);
        
        theBoard = new BoardConnectFour();
        
        theCurrentState = StateGame.INITIAL;
    }
    
    StateGame getCurrentState()
    {
       return theCurrentState;    
    }
   
    Player getCurrentPlayer()
    {
        return (theCurrentState == StateGame.TURN_PLAYER1 ? thePlayers[0] : thePlayers[1] ); 
    }
    
    char getCurrentToken()
    {
        return getCurrentPlayer().getToken(); 
    }
    
    int getNumCurrentPlayer()
    {
        return (theCurrentState == StateGame.TURN_PLAYER1 ? 0 : 1 );
    }
    
    private void testFinalState( char token ) 
    {
        if( theBoard.findLineVertical(token)   ||
            theBoard.findLineHorizontal(token) ||            
            theBoard.findLineDiagonalLR(token) ||
            theBoard.findLineDiagonalLR(token)    )
            theCurrentState = token == thePlayers[0].getToken() ? StateGame.WIN_PLAYER1 : StateGame.WIN_PLAYER2;
    }
    
    boolean updateState(int atColumn, char typeToken)
    {
        if( typeToken != getCurrentPlayer().getToken() )
            return false;
        
        if( theBoard.pushToken(atColumn, typeToken) )
        {
            theCurrentState = StateGame.fromInt( (theCurrentState.ordinal()+ 1)%2 ); 
            
            testFinalState(typeToken);
            
            return true;
        }
           
        return false;
    }
    
    @Override
    public String toString()
    {
       return theBoard.toString();    
    }
    
}
