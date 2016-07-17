package chessbitwise;

public class Rating {
    static int [][] WP_PST=    {{00,00,00,00,00,00,00,00}, 
                                {50,50,50,50,50,50,50,50}, 
                                {10,10,20,30,30,20,10,10}, 
                                {05,05,10,27,27,10,05,05},
                                {00,00,00,25,25,00,00,00}, 
                                {05,-5,-10,00,00,-10,-5,05},   
                                {05,10,10,-25,-25,10,10,05},   
                                {00,00,00,00,00,00,00,00}};
    
    static int [][] WN_PST=    {{-50,-40,-30,-30,-30,-30,-40,-50}, 
                                {-40,-20,00,00,00,00,-20,-40}, 
                                {-30,00,10,15,15,10,00,-30},   
                                {-30,05,15,20,20,15,05,-30},
                                {-30,00,15,20,20,15,00,-30},   
                                {-30,05,10,15,15,10,05,-30},   
                                {-40,-20,00,05,05,00,-20,-40}, 
                                {-50,-40,-30,-30,-30,-40,-50}};
    
    static int [][] WR_PST=    {{00,00,00,00,00,00,00,00}, 
                                {05,10,10,10,10,10,10,05}, 
                                {-05,00,00,00,00,00,00,-05},   
                                {-05,00,00,00,00,00,00,-05},
                                {-05,00,00,00,00,00,00,-05},   
                                {-05,00,00,00,00,00,00,-05},   
                                {-05,00,00,00,00,00,00,-05},   
                                {00,00,00,05,05,00,00,00}};
    
    static int [][] WB_PST=    {{-20,-10,-10,-10,-10,-10,-10,-20}, 
                                {-10,  0,  0,  0,  0,  0,  0,-10},  
                                {-10,  0,  5, 10, 10,  5,  0,-10},  
                                {-10,  5,  5, 10, 10,  5,  5,-10},
                                {-10,  0, 10, 10, 10, 10,  0,-10},  
                                {-10, 10, 10, 10, 10, 10, 10,-10},  
                                {-10,  5,  0,  0,  0,  0,  5,-10},  
                                {-20,-10,-20,-10,-10,-20,-10,-20}};
    
    static int [][] WQ_PST=    {{-20,-10,-10, -5, -5,-10,-10,-20}, 
                                {-10,  0,  0,  0,  0,  0,  0,-10},  
                                {-10,  0,  5,  5,  5,  5,  0,-10},  
                                {-5,  0,  5,  5,  5,  5,  0, -5},   
                                {0,  0,  5,  5,  5,  5,  0, -5,},   
                                {-10,  5,  5,  5,  5,  5,  0,-10,}, 
                                {-10,  0,  5,  0,  0,  0,  0,-10,}, 
                                {-20,-10,-10, -5, -5,-10,-10,-20}};
    
    static int [][] WK_PST = new int[8][8];
    
    static int [][] WK_PST_OG=  {{-30,-40,-40,-50,-50,-40,-40,-30}, 
                                {-30,-40,-40,-50,-50,-40,-40,-30},  
                                {-30,-40,-40,-50,-50,-40,-40,-30},  
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                                {-20,-30,-30,-40,-40,-30,-30,-20},  
                                {-10,-20,-20,-20,-20,-20,-20,-10},  
                                {20, 20,  0,  0,  0,  0, 20, 20},   
                                {20, 30, 10,  0,  0, 10, 30, 20}};
    
    static int [][] WK_PST_EG= {{-50,-40,-30,-20,-20,-30,-40,-50}, 
                                {-30,-20,-10,00,00,-10,-20,-30},  
                                {-30,-10,20,30,30,20,-10,-30},  
                                {-30,-10,30,40,40,30,-10,-30},
                                {-30,-10,30,40,40,30,-10,-30},
                                {-30,-10,20,30,30,20,-10,-30},
                                {-30,-20,-10,00,00,-10,-20,-30},
                                {-50,-40,-30,-20,-20,-30,-40,-50}};

    /*updated*/
    static int [][] BP_PST=    {{00,00,00,00,00,00,00,00}, 
                                {05,10,10,-25,-25,10,10,5}, 
                                {05,-5,-10,00,00,-10,-5,05}, 
                                {00,00,00,25,25,00,00,00}, 
                                {05,05,10,27,27,10,05,05},
                                {10,10,20,30,30,20,10,10},
                                {50,50,50,50,50,50,50,50},   
                                {00,00,00,00,00,00,00,00}};
    
    /*updated*/
    static int [][] BN_PST=    {{-50,-40,-30,-30,-30,-30,-40,-50}, 
                                {-40,-20,00,05,05,00,-20,-40}, 
                                {-30,05,10,15,15,10,05,-30},   
                                {-30,00,15,20,20,15,00,-30},
                                {-30,05,15,20,20,15,05,-30},   
                                {-30,00,10,15,15,10,00,-30},   
                                {-40,-20,00,00,00,00,-20,-40}, 
                                {-50,-40,-30,-30,-30,-40,-50}};
    
    static int [][] BR_PST=    {{  0, 0, 0, 5, 5, 0, 0, 0}, 
                                {  5, 0, 0, 0, 0, 0, 0, 5}, 
                                { -5,00,00,00,00,00,00,-05},   
                                { -5,00,00,00,00,00,00,-05},
                                { -5,00,00,00,00,00,00,-05},   
                                { -5,00,00,00,00,00,00,-05},   
                                {  5,10,10,10,10,10,10,-05},   
                                { 00,00,00,00,00,00,00,00}};

    static int [][] BB_PST=    {{-20,-10,-20,-10,-10,-20,-10,-20}, 
                                {-10, 05,  0,  0,  0,  0, 05,-10},  
                                {-10, 10, 10, 10, 10, 10, 10,-10},  
                                {-10,  5, 10, 10,10,  10,  5,-10},
                                {-10,  5,  5, 10, 10,  5,  5,-10},  
                                {-10,  0,  5, 10, 10,  5,  0,-10},  
                                {-10,  0,  0,  0,  0,  0,  0,-10},  
                                {-20,-10,-10,-10,-10,-10,-10,-20}};
    
    static int [][] BQ_PST=    {{-20,-10,-10, -5, -5,-10,-10,-20}, 
                                {-10,  0,  0,  0,  0,  0,  0,-10},  
                                {-10,  5,  5,  5,  5,  5,  0,-10}, 
                                { -5,  0,  5,  5,  5,  5,  0, -5},   
                                { -5,  0,  5,  5,  5,  5,  0, -5},   
                                {-10,  0,  5,  5,  5,  5,  0,-10},  
                                {-10,  0,  5,  0,  0,  5,  0,-10}, 
                                {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int [][] BK_PST  = new int [8][8];                              
    static int [][] BK_PST_OG= {{ 20, 30, 10,  0,  0, 10, 30, 20},
                                { 20, 20,  0,  0,  0,  0, 20, 20},
                                {-10,-20,-20,-20,-20,-20,-20,-10},
                                {-20,-30,-30,-40,-40,-30,-30,-20},                                  
                                {-30,-40,-40,-50,-50,-40,-40,-30},
                                {-30,-40,-40,-50,-50,-40,-40,-30},  
                                {-30,-40,-40,-50,-50,-40,-40,-30},  
                                {-30,-40,-40,-50,-50,-40,-40,-30}};

    static int [][] BK_PST_EG ={{-50,-40,-30,-30,-30,-30,-40,-50},
                                {-30,-30,  0,  0,  0,  0,-30,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 30, 40, 40, 30,-10,-30},
                                {-30,-10, 20, 30, 30, 20,-10,-30},
                                {-30,-30,-10,  0,  0,-10,-30,-30},
                                {-50,-40,-30,-20,-20,-30,-20,-50}};
                                

    static int PIECE_VALUE_WP=100;          static int PIECE_VALUE_BP=100;
    static int PIECE_VALUE_WN=300;          static int PIECE_VALUE_BN=300;
    static int PIECE_VALUE_WB=300;          static int PIECE_VALUE_BB=300;
    static int PIECE_VALUE_WR=500;          static int PIECE_VALUE_BR=500;
    static int PIECE_VALUE_WQ=900;          static int PIECE_VALUE_BQ=900;
    static int PIECE_VALUE_WK=1000000000;   static int PIECE_VALUE_BK=1000000000;
    static boolean openingGame=true,midGame=false,endGame=false;
    
    public static void openingGame()
    {
        openingGame=true;
        midGame=false;
        endGame=false;
        WK_PST=WK_PST_OG;
    }
    public static void midGame()
    {
        openingGame=false;
        midGame=true;
        endGame=false;
        WK_PST=WK_PST_OG;
    }
    public static void endGame()
    {
        openingGame=false;
        midGame=false;
        endGame=true;
        WK_PST=WK_PST_EG;
    }
    
    public static int evaluate(String move,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ, boolean WhiteToMove)
    {
        int score=boardEvaluation(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ,WhiteToMove);
        score += pieceSquare(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ);
        score += positionEvaluation(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ,WhiteToMove);
        score += mobility(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ,WhiteToMove);
        return score;
    }
    
    public static int pieceSquare(String move, long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ)
    {
        //moveScore
        long WPt=Moves.makeMove(WP, move, 'P'), WNt=Moves.makeMove(WN, move, 'N'),
                WBt=Moves.makeMove(WB, move, 'B'), WRt=Moves.makeMove(WR, move, 'R'),
                WQt=Moves.makeMove(WQ, move, 'Q'), WKt=Moves.makeMove(WK, move, 'K'),
                BPt=Moves.makeMove(BP, move, 'p'), BNt=Moves.makeMove(BN, move, 'n'),
                BBt=Moves.makeMove(BB, move, 'b'), BRt=Moves.makeMove(BR, move, 'r'),
                BQt=Moves.makeMove(BQ, move, 'q'), BKt=Moves.makeMove(BK, move, 'k'),
                EPt=Moves.makeMoveEP(WP|BP,move);
        WRt=Moves.makeMoveCastle(WRt, WK|BK, move, 'R');
        BRt=Moves.makeMoveCastle(BRt, WK|BK, move, 'r');
        boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            if (((1L<<start)&WK)!=0) {CWKt=false; CWQt=false;}
            else if (((1L<<start)&BK)!=0) {CBKt=false; CBQt=false;}
            else if (((1L<<start)&WR&(1L<<63))!=0) {CWKt=false;}
            else if (((1L<<start)&WR&(1L<<56))!=0) {CWQt=false;}
            else if (((1L<<start)&BR&(1L<<7))!=0) {CBKt=false;}
            else if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
        }
        int score = Moves.moveScore(WP, move, 'P')+Moves.moveScore(WN, move, 'N')
              + Moves.moveScore(WB, move, 'B') + Moves.moveScore(WR, move, 'R')
              + Moves.moveScore(WQ, move, 'Q') + Moves.moveScore(WK, move, 'K')
              + Moves.moveScore(BP, move, 'p') + Moves.moveScore(BN, move, 'n')
              + Moves.moveScore(BB, move, 'b') + Moves.moveScore(BR, move, 'r')
              + Moves.moveScore(BQ, move, 'q') + Moves.moveScore(BK, move, 'k');
        
        return score;
    }
    public static int boardEvaluation(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ, boolean WhiteToMove)
    {
        int score=0;
        int wp=0,wr=0,wn=0,wb=0,wq=0,wk=0,bp=0,br=0,bn=0,bb=0,bq=0,bk=0;
        long possibility=0;
        //pawns
        possibility= WP&~(WP-1);
        while(possibility != 0)
        {
            wp++;
            WP&=~possibility;
            possibility = WP&~(WP-1);
        }
        possibility=0;
        //rooks
        possibility= WR&~(WR-1);
        while(possibility != 0)
        {
            wr++;
            WR&=~possibility;
            possibility = WR&~(WR-1);
        }
        possibility=0;
        //Knight
        possibility= WN&~(WN-1);
        while(possibility != 0)
        {
            wn++;
            WN&=~possibility;
            possibility = WN&~(WN-1);
        }
        possibility=0;
        //Bishop
        possibility= WB&~(WB-1);
        while(possibility != 0)
        {
            wb++;
            WB&=~possibility;
            possibility = WB&~(WB-1);
        }
        possibility=0;
        //QUEEN
        possibility= WQ&~(WQ-1);
        while(possibility != 0)
        {
            wq++;
            WQ&=~possibility;
            possibility = WQ&~(WQ-1);
        }
        possibility=0;
        //KING
        possibility= WK&~(WK-1);
        while(possibility != 0)
        {
            wk++;
            WK&=~possibility;
            possibility = WK&~(WK-1);
        }
        possibility=0;
        //BLACK PIECES
        //Pawn
        possibility=0;
        possibility= BP&~(BP-1);
        while(possibility != 0)
        {
            bp++;
            BP&=~possibility;
            possibility = BP&~(BP-1);
        }
        possibility=0;
        //rooks
        possibility= BR&~(BR-1);
        while(possibility != 0)
        {
            br++;
            BR&=~possibility;
            possibility = BR&~(BR-1);
        }
        possibility=0;
        //Knight
        possibility= BN&~(BN-1);
        while(possibility != 0)
        {
            bn++;
            BN&=~possibility;
            possibility = BN&~(BN-1);
        }
        possibility=0;
        //Bishop
        possibility= BB&~(BB-1);
        while(possibility != 0)
        {
            bb++;
            BB&=~possibility;
            possibility = BB&~(BB-1);
        }
        possibility=0;
        //QUEEN
        possibility= BQ&~(BQ-1);
        while(possibility != 0)
        {
            bq++;
            BQ&=~possibility;
            possibility = BQ&~(BQ-1);
        }
        possibility=0;
        //KING
        possibility= BK&~(BK-1);
        while(possibility != 0)
        {
            bk++;
            BK&=~possibility;
            possibility = BK&~(BK-1);
        }

        score += (wk-bk)*PIECE_VALUE_WK 
                +(wq-bq)*PIECE_VALUE_WQ
                +(wr-br)*PIECE_VALUE_WR
                +(wn-bn)*PIECE_VALUE_WN
                +(wb-bb)*PIECE_VALUE_WB
                +(wp-bp)*PIECE_VALUE_WP;
        if(WhiteToMove)
            return score;
        else 
            return -score;
    }
    public static int positionEvaluation(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ, boolean WhiteToMove)
    {
        
        return 0;
    }
    
    public static int mobility(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ, boolean WhiteToMove)
    {
        
        return 0;
    }
}
