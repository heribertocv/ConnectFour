/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

/**
 *
 * @author gkwh <cvh.cursos at gmail.com>
 */
public class Player
{
    private final String name;
    private final char token;
    
    Player( String theName, char theToken  )
    {
        name  = theName;
        token = theToken;
    }

    String getName()
    {  return name; }

    char getToken()
    { return token; }
   
    
    @Override
    public String toString()
    {
        String Message = "Player " + name + " \n  Token : " + token + "\n";

        return Message;
    }
    
}
