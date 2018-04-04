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
public class BoardConnectFour
{
    static final int SizeWinnerClique = 4;
            
    final short numColumns = 8;
    final short numRows = 8;

    int topColumns[];
    char Board[][];

    public BoardConnectFour() {
        topColumns = new int [numColumns];
        Board = new char [numRows][numColumns];
        
        clearAll();
     }
  
    int numColumns()
    {
        return numColumns;
    }
    
    int numRows()
    {
        return numRows;
    }
    
    final void clearAll()
    {
       for (int row=0; row<numRows; ++row) {
            for (int col=0; col < numColumns; ++col) {
                Board[row][col]=' ';
            }
        } 
       
       for(int x = 0; x< topColumns.length; ++x )
           topColumns[x] = numRows-1;
    
      }
    
    
    boolean pushToken(int column, char token )
    {
        if( topColumns[column]>=0 )
        {
            Board[topColumns[column]-1][column] = token;
            --topColumns[column];
            return true;
        }
        
        return false;
    }
        
    int numTokensAtColumn(int column)
    {
        return topColumns[column];
    }
    
    boolean findLineHorizontal( char token )
    {
        int lengthClique;
        
        for (char[] row : Board)
        {
            lengthClique = 0;
            for (int ii=0; ii<numColumns && lengthClique < SizeWinnerClique; ++ii)
                lengthClique =  row[ii] ==  token ? lengthClique+1 : 0;
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }         
        return false;        
    }
    
    boolean findLineVertical( char token )
    {
        int lengthClique;
        
        for (int column = 0; column<numColumns; ++column)
        {
            lengthClique = 0;
            for (int ii=0; ii<numRows && lengthClique < SizeWinnerClique; ++ii)
                lengthClique =  Board[ii][column] ==  token ? lengthClique+1 : 0;
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }         
        return false;        
    }
    
    /**
     * look for diagonal lines at Left to Right direction
     * @param token
     * @return true if found diagonal line, otherwise false
     */
    boolean findLineDiagonalLR( char token )
    {
        int lengthClique;
        
        for (int column = 0; column<= numColumns - SizeWinnerClique; ++column)
        {
            lengthClique = 0;
            int rr=0;
            
            for (int cc=column; cc<numColumns &&
                                rr<numRows &&
                                lengthClique < SizeWinnerClique; ++cc, ++rr) {
                lengthClique =  Board[rr][cc] ==  token ? lengthClique+1 : 0;
            }
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }
        
        for (int row = 1; row<= numRows - SizeWinnerClique; ++row)
        {
            lengthClique = 0;
            int cc=0;
            
            for (int rr=row; rr<numRows &&
                             cc<numColumns &&
                             lengthClique < SizeWinnerClique; ++cc, ++rr) {
                lengthClique =  Board[rr][cc] ==  token ? lengthClique+1 : 0;
            }
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }  
        
        return false;        
    }
    
    
        /**
     * look for diagonal lines at Right to Left direction
     * @param token
     * @return true if found diagonal line, otherwise false
     */
    boolean findLineDiagonalRL( char token )
    {
        int lengthClique;
        
        for (int column = numColumns-1; column>= SizeWinnerClique; --column)
        {
            lengthClique = 0;
            int rr=0;
            
            for (int cc=column; cc<numColumns &&
                                rr<numRows &&
                                lengthClique < SizeWinnerClique; --cc, ++rr) {
                lengthClique =  Board[rr][cc] ==  token ? lengthClique+1 : 0;
            }
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }
        
        for (int row = 1; row<= numRows - SizeWinnerClique; ++row)
        {
            lengthClique = 0;
            int cc=numColumns-1;
            
            for (int rr=row; rr<numRows &&
                             cc>0 &&
                             lengthClique < SizeWinnerClique; --cc, ++rr) {
                lengthClique =  Board[rr][cc] ==  token ? lengthClique+1 : 0;
            }
            
            if(lengthClique == SizeWinnerClique)
                return true;
        }  
        
        return false;        
    }
    
    @Override
    public String toString()
    {
       String theBoard = "";
       
       for(int ii=0; ii<numColumns; ++ii)
           theBoard += " | " + (ii+1);
       theBoard += "|\n";
       
       for (char[] a : Board) {
            for (char c : a) {
                theBoard += " | " + c;
            }
            theBoard += " | \n";
       }   
       
       return theBoard;    
    }
    
}
