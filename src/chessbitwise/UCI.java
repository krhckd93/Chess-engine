package chessbitwise;

import java.util.*;
import java.lang.Object;
//Write algebra to move for special moves.

public class UCI {
    static String ENGINENAME="Chess Engine AI";
    static boolean WhiteToMoveTemp=false;
    public static void uciCommunication(){
        Scanner input;
        while(true)
        {
            input = new Scanner(System.in);
            String inputString = input.nextLine();
            if("uci".equals(inputString))
            {
                inputUCI();
            }
            else if(inputString.startsWith("setoption"))
            {
                inputSetOption(inputString);
            }
            else if("isready".equals(inputString))
            {
                inputIsReady();
            }
            else if("ucinewgame".equals(inputString))
            {
                inputUCINewGame();
            }
            else if(inputString.startsWith("position"))
            {
                inputPosition(inputString);
            }
            else if("go".equals(inputString))
            {
                inputGo();
            }
            else if("print".equals(inputString))
            {
                inputPrint();
            }
            else if (inputString.equals("quit"))
            {
                inputQuit();
            }
            
        }
    }
    
    public static void inputUCI()
    {
        System.out.println("id name "+ENGINENAME);
        System.out.println("id author Ashwin");
        //optional go here
        System.out.println("uciok");
    }
    public static void inputSetOption(String inputString)
    {
        
    }
    public static void inputIsReady()
    {
        System.out.println("readyok");
    }
    public static void inputUCINewGame()
    {
        
    }
    public static void inputPosition(String input)
    {
        int moveCounter=0;
        input=input.substring(9).concat(" ");
        if(input.contains("startpos ")){
            input=input.substring(9);  
            BoardGeneration.initiateStandardChess();
        }
        else if (input.contains("fen"))
        {
            input=input.substring(4);
            BoardGeneration.importFEN(input);
        }
        String input1=input;
        input1=input1.substring(input1.indexOf("moves")+6);
        int input1Length=input1.length();
        
        if((input1.length()/5)%2==0)
        {
            System.out.println("Yes, White to Move");
            WhiteToMoveTemp=true;
        }
        else
        {
            WhiteToMoveTemp=false;
        }
            
        if(input.contains("moves"))
        {
            input=input.substring(input.indexOf("moves")+6);
            
            while(input.length()>0)
            {
                if((moveCounter%2)==0)
                    UserInterface.WhiteToMove=true;
                else 
                    UserInterface.WhiteToMove=false;
                
                String moves;
                
                if(UserInterface.WhiteToMove)
                    moves=Moves.possibleMovesW(UserInterface.WP, UserInterface.WR, UserInterface.WN, UserInterface.WB, UserInterface.WQ, UserInterface.WK, UserInterface.BP, UserInterface.BR, UserInterface.BN, UserInterface.BB, UserInterface.BQ, UserInterface.BK, UserInterface.EP, UserInterface.CWK, UserInterface.CWQ, UserInterface.CBK, UserInterface.CBQ,UserInterface.WhiteToMove);
                else
                    moves=Moves.possibleMovesB(UserInterface.WP, UserInterface.WR, UserInterface.WN, UserInterface.WB, UserInterface.WQ, UserInterface.WK, UserInterface.BP, UserInterface.BR, UserInterface.BN, UserInterface.BB, UserInterface.BQ, UserInterface.BK, UserInterface.EP, UserInterface.CWK, UserInterface.CWQ, UserInterface.CBK, UserInterface.CBQ,UserInterface.WhiteToMove);

                algebraToMove(input,moves);
                input=input.substring(input.indexOf(' ')+1);
                moveCounter++;
            }
            
            BoardGeneration.drawArray(UserInterface.WP,UserInterface.WR,UserInterface.WN,UserInterface.WB,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BR,UserInterface.BN,UserInterface.BB,UserInterface.BQ,UserInterface.BK);
        }
        String moves;
        if (WhiteToMoveTemp) {
            moves=Moves.possibleMovesW(UserInterface.WP,UserInterface.WR,UserInterface.WN,UserInterface.WB,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BR,UserInterface.BN,UserInterface.BB,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
            for(int i=0;i<moves.length();i+=4)
            System.out.print(" "+moves.substring(i, i+4));
        } 
        else {
            moves=Moves.possibleMovesB(UserInterface.WP,UserInterface.WR,UserInterface.WN,UserInterface.WB,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BR,UserInterface.BN,UserInterface.BB,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
            for(int i=0;i<moves.length();i+=4)
            System.out.print(moves.substring(i, i+4)+" ");
        }
        if(moves.length()==0)
        {
            System.out.println("bestmove 0000");
        }
        else
        {
            moves=PrincipalVariation.getFirstLegalMove(moves,UserInterface.WP,UserInterface.WN,UserInterface.WB,UserInterface.WR,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BN,UserInterface.BB,UserInterface.BR,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
            System.out.println("bestmove "+moveToAlgebra(moves.substring(0,4)));
            System.out.println(moves.substring(0,4));
        }
    }
    public static void inputGo()
    {
        String moves;
        if (UserInterface.WhiteToMove) {
            moves=Moves.possibleMovesW(UserInterface.WP,UserInterface.WN,UserInterface.WB,UserInterface.WR,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BN,UserInterface.BB,UserInterface.BR,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
        } else {
            moves=Moves.possibleMovesB(UserInterface.WP,UserInterface.WN,UserInterface.WB,UserInterface.WR,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BN,UserInterface.BB,UserInterface.BR,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
        }
        moves=PrincipalVariation.getFirstLegalMove(moves,UserInterface.WP,UserInterface.WN,UserInterface.WB,UserInterface.WR,UserInterface.WQ,UserInterface.WK,UserInterface.BP,UserInterface.BN,UserInterface.BB,UserInterface.BR,UserInterface.BQ,UserInterface.BK,UserInterface.EP,UserInterface.CWK,UserInterface.CWQ,UserInterface.CBK,UserInterface.CBQ,UserInterface.WhiteToMove);
        //System.out.println("bestmove "+moves.subSequence(0, 4));
    }
    public static void inputPrint()
    {
        BoardGeneration.drawArray(UserInterface.WP, UserInterface.WR, UserInterface.WN, UserInterface.WB,UserInterface.WQ, UserInterface.WK, UserInterface.BP, UserInterface.BR, UserInterface.BN, UserInterface.BB, UserInterface.BQ, UserInterface.BK);
    }
    public static String moveToAlgebra(String move) 
    {
        String append="";
        int start=0,end=0;
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
        } else if (move.charAt(3)=='P') {//pawn promotion
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[1]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[6]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[7]);
            }
            append=""+Character.toLowerCase(move.charAt(2));
        } else if (move.charAt(3)=='E') {//en passant
            if (move.charAt(2)=='W') {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[3]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[2]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[4]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[5]);
            }
        }
        
        String returnMove="";
        returnMove+=(char)('a'+(start%8));
        returnMove+=(char)('8'-(start/8));
        returnMove+=(char)('a'+(end%8));
        returnMove+=(char)('8'-(end/8));
        returnMove+=append;
        return returnMove;
    }
    public static void algebraToMove(String input, String moves)
    {
        int start=0,end=0;
        int from = (input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
        int to = (input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
        
        for(int i=0;i<moves.length();i++)
        {
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                start=(Character.getNumericValue(moves.charAt(i+0))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                end=(Character.getNumericValue(moves.charAt(i+2))*8)+(Character.getNumericValue(moves.charAt(i+3)));
            } else if (moves.charAt(i+3)=='P') {//pawn promotion
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[1]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[0]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[6]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[7]);
                }
            } else if (moves.charAt(i+3)=='E') {//en passaSnt
                if (moves.charAt(i+2)=='W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[2]);
                } else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0']&Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0']&Moves.RankMasks8[5]);
                }
            }
            if ((start==from) && (end==to)) {
                if ((input.charAt(4)==' ') || (Character.toUpperCase(input.charAt(4))==Character.toUpperCase(moves.charAt(i+2)))) {
                    if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                        start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                        if (((1L<<start)&UserInterface.WK)!=0) {UserInterface.CWK=false; UserInterface.CWQ=false;}
                        else if (((1L<<start)&UserInterface.BK)!=0) {UserInterface.CBK=false; UserInterface.CBQ=false;}
                        else if (((1L<<start)&UserInterface.WR&(1L<<63))!=0) {UserInterface.CWK=false;}
                        else if (((1L<<start)&UserInterface.WR&(1L<<56))!=0) {UserInterface.CWQ=false;}
                        else if (((1L<<start)&UserInterface.BR&(1L<<7))!=0) {UserInterface.CBK=false;}
                        else if (((1L<<start)&UserInterface.BR&1L)!=0) {UserInterface.CBQ=false;}
                        
                    }
                    UserInterface.EP=Moves.makeMoveEP(UserInterface.WP|UserInterface.BP, moves.substring(i,i+4));
                    UserInterface.WR=Moves.makeMoveCastle(UserInterface.WR, UserInterface.WK|UserInterface.BK, moves.substring(i,i+4), 'R');
                    UserInterface.BR=Moves.makeMoveCastle(UserInterface.BR, UserInterface.WK|UserInterface.BK, moves.substring(i,i+4), 'r');
                    UserInterface.WP=Moves.makeMove(UserInterface.WP, moves.substring(i,i+4),'P');
                    UserInterface.WR=Moves.makeMove(UserInterface.WR, moves.substring(i,i+4),'R');
                    UserInterface.WN=Moves.makeMove(UserInterface.WN, moves.substring(i,i+4),'N');
                    UserInterface.WB=Moves.makeMove(UserInterface.WB, moves.substring(i,i+4),'B');
                    UserInterface.WQ=Moves.makeMove(UserInterface.WQ, moves.substring(i,i+4),'Q');
                    UserInterface.WK=Moves.makeMove(UserInterface.WK, moves.substring(i,i+4),'K');
                    UserInterface.BP=Moves.makeMove(UserInterface.BP, moves.substring(i,i+4),'p');
                    UserInterface.BR=Moves.makeMove(UserInterface.BR, moves.substring(i,i+4),'r');
                    UserInterface.BN=Moves.makeMove(UserInterface.BN, moves.substring(i,i+4),'n');
                    UserInterface.BB=Moves.makeMove(UserInterface.BB, moves.substring(i,i+4),'b');
                    UserInterface.BQ=Moves.makeMove(UserInterface.BQ, moves.substring(i,i+4),'q');
                    UserInterface.BK=Moves.makeMove(UserInterface.BK, moves.substring(i,i+4),'k');
                    
                    UserInterface.WhiteToMove=!UserInterface.WhiteToMove;
                    break;
                }
            }
        }
    }
    public static void inputQuit() {
        System.exit(0);
    }
}
