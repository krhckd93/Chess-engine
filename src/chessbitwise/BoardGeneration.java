
package chessbitwise;

import static chessbitwise.UserInterface.WP;
import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import static java.awt.image.ImageObserver.WIDTH;


public class BoardGeneration {
    public static void initiateStandardChess()
    {
        long WP=0L,WR=0L,WN=0L,WB=0L,WQ=0L,WK=0L,BP=0L,BR=0L,BN=0L,BB=0L,BQ=0L,BK=0L,EP=0L;
        
        String chessBoard[][]={
        {"r","n","b","q","k","b","n","r"},//0 8
        {"p","p","p","p","p","p","p","p"},//1 7
        {" "," "," "," "," "," "," "," "},//2 6
        {" "," "," "," "," "," "," "," "},//3 5
        {" "," "," "," "," "," "," "," "},//4 4
        {" "," "," "," "," "," "," "," "},//5 3
        {"P","P","P","P","P","P","P","P"},//6 2 
        {"R","N","B","Q","K","B","N","R"} //7 1
        //0   1   2   3   4   5   6   7
        //a   b   c   d   e   f   g   h
    };
        arrayToBitBoards(chessBoard,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK);
    }
    
    public static void importFEN(String fenString) {
        //not chess960 compatible
	UserInterface.WP=0; UserInterface.WN=0; UserInterface.WB=0;
        UserInterface.WR=0; UserInterface.WQ=0; UserInterface.WK=0;
        UserInterface.BP=0; UserInterface.BN=0; UserInterface.BB=0;
        UserInterface.BR=0; UserInterface.BQ=0; UserInterface.BK=0;
        UserInterface.CWK=false; UserInterface.CWQ=false;
        UserInterface.CBK=false; UserInterface.CBQ=false;
	int charIndex = 0;
	int boardIndex = 0;
	while (fenString.charAt(charIndex) != ' ')
	{
		switch (fenString.charAt(charIndex++))
		{
		case 'P': UserInterface.WP |= (1L << boardIndex++);
			break;
		case 'p': UserInterface.BP |= (1L << boardIndex++);
			break;
		case 'N': UserInterface.WN |= (1L << boardIndex++);
			break;
		case 'n': UserInterface.BN |= (1L << boardIndex++);
			break;
		case 'B': UserInterface.WB |= (1L << boardIndex++);
			break;
		case 'b': UserInterface.BB |= (1L << boardIndex++);
			break;
		case 'R': UserInterface.WR |= (1L << boardIndex++);
			break;
		case 'r': UserInterface.BR |= (1L << boardIndex++);
			break;
		case 'Q': UserInterface.WQ |= (1L << boardIndex++);
			break;
		case 'q': UserInterface.BQ |= (1L << boardIndex++);
			break;
		case 'K': UserInterface.WK |= (1L << boardIndex++);
			break;
		case 'k': UserInterface.BK |= (1L << boardIndex++);
			break;
		case '/':
			break;
		case '1': boardIndex++;
			break;
		case '2': boardIndex += 2;
			break;
		case '3': boardIndex += 3;
			break;
		case '4': boardIndex += 4;
			break;
		case '5': boardIndex += 5;
			break;
		case '6': boardIndex += 6;
			break;
		case '7': boardIndex += 7;
			break;
		case '8': boardIndex += 8;
			break;
		default:
			break;
		}
	}
	UserInterface.WhiteToMove = (fenString.charAt(++charIndex) == 'w');
	charIndex += 2;
	while (fenString.charAt(charIndex) != ' ')
	{
		switch (fenString.charAt(charIndex++))
		{
		case '-':
			break;
		case 'K': UserInterface.CWK = true;
			break;
		case 'Q': UserInterface.CWQ = true;
			break;
		case 'k': UserInterface.CBK = true;
			break;
		case 'q': UserInterface.CBQ = true;
			break;
		default:
			break;
		}
	}
	if (fenString.charAt(++charIndex) != '-')
	{
		UserInterface.EP = Moves.FileMasks8[fenString.charAt(charIndex++) - 'a'];
	}
	//the rest of the fenString is not yet utilized
    }

    public static void arrayToBitBoards(String chessBoard[][],long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK)//String chessBoard[][],long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK
    {
        String binary;
        for(int i=0;i<64;i++)
        {
            binary="0000000000000000000000000000000000000000000000000000000000000000";
            binary = binary.substring(i+1)+"1"+binary.substring(0, i);

            switch(chessBoard[i/8][i%8])
            {
                case "P" :  WP += convertStringToBitBoard(binary);
                            break;
                case "R" :  WR += convertStringToBitBoard(binary);
                            break;
                case "N" :  WN += convertStringToBitBoard(binary);
                            break;
                case "B" :  WB += convertStringToBitBoard(binary);
                            break;                
                case "Q" :  WQ += convertStringToBitBoard(binary);
                            break;
                case "K" :  WK += convertStringToBitBoard(binary);
                            break;
                case "p" :  BP += convertStringToBitBoard(binary);
                            break;
                case "r" :  BR += convertStringToBitBoard(binary);
                            break;
                case "n" :  BN += convertStringToBitBoard(binary);
                            break;
                case "b" :  BB += convertStringToBitBoard(binary);
                            break;
                case "q" :  BQ += convertStringToBitBoard(binary);
                            break;
                case "k" :  BK += convertStringToBitBoard(binary);
                            break;
                default  :  break;
            }
        }
        UserInterface.WP=WP;UserInterface.WN=WN;UserInterface.WB=WB;UserInterface.WR=WR;UserInterface.WQ=WQ;UserInterface.WK=WK;
        UserInterface.BP=BP;UserInterface.BN=BN;UserInterface.BB=BB;UserInterface.BR=BR;UserInterface.BQ=BQ;UserInterface.BK=BK;
        //drawArray(UserInterface.WP, UserInterface.WR, UserInterface.WN, UserInterface.WB,UserInterface.WQ, UserInterface.WK, UserInterface.BP, UserInterface.BR, UserInterface.BN, UserInterface.BB, UserInterface.BQ, UserInterface.BK);        
    }
    
    public static void drawBitBoard(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK)
    {
        String chessBoard1[][] = new String[8][8];
        for(int i=0;i<64;i++)
        {
            chessBoard1[i/8][i%8]=" ";
        }
        
        for(int i=0;i<64;i++)
        {
            if( ((WP>>i) & 1)== 1) {chessBoard1[i/8][i%8]="P";}
            if( ((WR>>i) & 1)== 1) {chessBoard1[i/8][i%8]="R";}
            if( ((WN>>i) & 1)== 1) {chessBoard1[i/8][i%8]="N";}
            if( ((WB>>i) & 1)== 1) {chessBoard1[i/8][i%8]="B";}
            if( ((WQ>>i) & 1)== 1) {chessBoard1[i/8][i%8]="Q";}
            if( ((WK>>i) & 1)== 1) {chessBoard1[i/8][i%8]="K";}
            if( ((BP>>i) & 1)== 1) {chessBoard1[i/8][i%8]="p";}
            if( ((BR>>i) & 1)== 1) {chessBoard1[i/8][i%8]="r";}
            if( ((BN>>i) & 1)== 1) {chessBoard1[i/8][i%8]="n";}
            if( ((BB>>i) & 1)== 1) {chessBoard1[i/8][i%8]="b";}
            if( ((BQ>>i) & 1)== 1) {chessBoard1[i/8][i%8]="q";}
            if( ((BK>>i) & 1)== 1) {chessBoard1[i/8][i%8]="k";}
        }
        
        for(int i=0;i<8;i++)
            System.out.println(Arrays.toString(chessBoard1[i]));
    }
    
    public static long convertStringToBitBoard(String Binary)
    {
        if(Binary.charAt(0)=='0')
        {
            return Long.parseUnsignedLong(Binary, 2);
        }
        else
        {
            return Long.parseUnsignedLong("1"+Binary.substring(2), 2)*2;
        }
    }
    public static void drawArray(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK) 
    {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]=" ";
        }
        for (int i=0;i<64;i++) {
            if (((WP>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if (((WN>>i)&1)==1) {chessBoard[i/8][i%8]="N";}
            if (((WB>>i)&1)==1) {chessBoard[i/8][i%8]="B";}
            if (((WR>>i)&1)==1) {chessBoard[i/8][i%8]="R";}
            if (((WQ>>i)&1)==1) {chessBoard[i/8][i%8]="Q";}
            if (((WK>>i)&1)==1) {chessBoard[i/8][i%8]="K";}
            if (((BP>>i)&1)==1) {chessBoard[i/8][i%8]="p";}
            if (((BN>>i)&1)==1) {chessBoard[i/8][i%8]="n";}
            if (((BB>>i)&1)==1) {chessBoard[i/8][i%8]="b";}
            if (((BR>>i)&1)==1) {chessBoard[i/8][i%8]="r";}
            if (((BQ>>i)&1)==1) {chessBoard[i/8][i%8]="q";}
            if (((BK>>i)&1)==1) {chessBoard[i/8][i%8]="k";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
    
    public static void IndiBitBoard(long bb)
    {
        
        String board= Long.toBinaryString(bb);
        System.out.println(board.length());
        System.out.println(Long.toBinaryString(bb));
        int counter=0;
        for(int i=board.length()-1;i>=0;i--)
        {
            System.out.print(board.charAt(i)+" ");
            counter++;
            if(counter%8==0)
            System.out.print("\n");
        }
        for(int i=0;i<64-board.length();i++)
        {
            System.out.print("2 ");
            counter++;
            if(counter%8==0)
            System.out.print("\n");
        }
    }
    
    
    public static void main(String[] args) 
    {
    
    }
}