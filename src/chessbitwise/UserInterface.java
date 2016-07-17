
package chessbitwise;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UserInterface extends JPanel {
    static long WP=0L,WR=0L,WN=0L,WB=0L,WQ=0L,WK=0L,BP=0L,BR=0L,BN=0L,BB=0L,BQ=0L,BK=0L,EP=0L;
    static boolean CWK=true,CWQ=true,CBK=true,CBQ=true;
    static boolean UniversalCastleWK=true,UniversalCastleWQ=true,UniversalCastleBK=true,UniversalCastleBQ=true;
    int humanIsWhite=1;
    static int rating =0;
    static int searchDepth=7,moveCounter;
    static int MATE_SCORE=5000,NULL_INT=Integer.MIN_VALUE;
    static int index;
    static boolean WhiteToMove=false;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);
//        for(int i=0;i<64;i+=2)
//        {
//            g.setColor(Color.WHITE);
//            g.fillRect((i%8+(i/8)%2)*squareSize, i/8*squareSize, squareSize, squareSize);
//            g.setColor(Color.darkGray);
//            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, (i+1)/8*squareSize, squareSize, squareSize);
//
//        }
        
        Image chessPieceImage;
        //chessPieceImage = new ImageIcon("C:\\Users\\hp\\Desktop\\Chess_Pieces_Sprite.svg").getImage();//H:\\Chess Project\\Images\\Chess_bdt60.png
        chessPieceImage = new ImageIcon("H:\\Chess Project\\Images\\New folder\\Chess_Pieces_256px_1094000_easyicon.png").getImage();
        //g.drawImage(chessPieceImage, x, y,x+100,y+100, this);
        
//        for(int i=0;i<64;i++)
//        {
//            int j=-1,k=-1;
//            switch(BoardGeneration.chessBoard[i/8][i%8])
//            {
//                case "P" : j=5; k=0;
//                           break;
//                case "p" : j=5; k=1;
//                           break;
//                case "R" : j=4;k=0;
//                           break;
//                case "r" : j=4;k=1;
//                           break;           
//                case "N" : j=3;k=0;
//                           break;
//                case "n" : j=3;k=1;
//                           break;
//                case "B" : j=2;k=0;
//                           break;                
//                case "b" : j=2;k=1;
//                           break;
//                case "Q" : j=1;k=0;
//                           break;                
//                case "q" : j=1;k=1;
//                           break;
//                case "K" : j=0;k=0;
//                           break;  
//                case "k" : j=0;k=1;
//                           break;           
//            
//            }
            
//            if(j!= -1 && k!=-1)
//            {
//                g.drawImage(chessPieceImage,(i%8)*squareSize,(i/8)*squareSize,(i%8+1)*squareSize,(i/8+1)*squareSize,j*333,k*333,(j+1)*333,(k+1)*333,this);
//            }   
//        }
    }
    public static void newGame()
    {
        BoardGeneration.initiateStandardChess();
        String list = "";
        list += Moves.possibleMovesW(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        list += Moves.possibleMovesB(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        Perft.perft(WP, WN, WB, WR, WQ, WK, BP, BN, BB, BR, BQ, BK, EP, CWK, CWQ, CBK, CBQ, WhiteToMove, WIDTH);
    }

    public static void main(String[] args){       
        //UCI.uciCommunication();
        BoardGeneration.initiateStandardChess();
//        System.out.println(Moves.possibleMovesW(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ, WhiteToMove));
        String moveScore = PrincipalVariation.pvSearch("",-10000,10000,WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove,0);
//        String moveScore = Moves.possibleMovesB(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ, WhiteToMove);
        System.out.println("moveScore :"+ moveScore);
    }
}