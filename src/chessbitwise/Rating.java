package chessbitwise;

public class Rating {
    static int [][] WP_PS_Table=    {{00,00,00,00,00,00,00,00}, {50,50,50,50,50,50,50,50}, {10,10,20,30,30,20,10,10}, {05,05,10,25,25,10,05,05},
                                     {00,00,00,20,20,00,00,00}, {05,-5,-10,00,00,-10,-5,-5},   {05,10,10,-20,-20,10,10,05},   {00,00,00,00,00,00,00,00}};
    static int [][] WN_PS_Table=    {{-50,-40,-30,-30,-30,-30,-40,-50}, {-40,-20,00,00,00,00,-20,-40}, {-30,00,10,15,15,10,00,-30},   {-30,05,15,20,20,15,05,-30},
                                     {-30,00,15,20,20,15,00,-30},   {-30,05,10,15,15,10,05,-30},   {-40,-20,00,05,05,00,-20,-40}, {-50,-40,-30,-30,-30,-40,-50}};
    static int [][] WR_PS_Table=    {{00,00,00,00,00,00,00,00}, {05,10,10,10,10,10,10,05}, {-05,00,00,00,00,00,00,-05},   {-05,00,00,00,00,00,00,-05},
                                     {-05,00,00,00,00,00,00,-05},   {-05,00,00,00,00,00,00,-05},   {-05,00,00,00,00,00,00,-05},   {00,00,00,05,05,00,00,00}};
    static int [][] WB_PS_Table=    {{-20,-10,-10,-10,-10,-10,-10,-20}, {-10,  0,  0,  0,  0,  0,  0,-10},  {-10,  0,  5, 10, 10,  5,  0,-10},  {-10,  5,  5, 10, 10,  5,  5,-10},
                                    {-10,  0, 10, 10, 10, 10,  0,-10},  {-10, 10, 10, 10, 10, 10, 10,-10},  {-10,  5,  0,  0,  0,  0,  5,-10},  {-20,-10,-10,-10,-10,-10,-10,-20}};
    static int [][] WQ_PS_Table=    {{-20,-10,-10, -5, -5,-10,-10,-20}, {-10,  0,  0,  0,  0,  0,  0,-10},  {-10,  0,  5,  5,  5,  5,  0,-10},  {-5,  0,  5,  5,  5,  5,  0, -5},   
                                    {0,  0,  5,  5,  5,  5,  0, -5,},   {-10,  5,  5,  5,  5,  5,  0,-10,}, {-10,  0,  5,  0,  0,  0,  0,-10,}, {-20,-10,-10, -5, -5,-10,-10,-20}};
    static int [][] WK_PS_Table=    {{-30,-40,-40,-50,-50,-40,-40,-30}, {-30,-40,-40,-50,-50,-40,-40,-30},  {-30,-40,-40,-50,-50,-40,-40,-30},  {-30,-40,-40,-50,-50,-40,-40,-30},
                                    {-20,-30,-30,-40,-40,-30,-30,-20},  {-10,-20,-20,-20,-20,-20,-20,-10},  {20, 20,  0,  0,  0,  0, 20, 20},   {20, 30, 10,  0,  0, 10, 30, 20}};
    static int PIECE_VALUE_WP=100;          static int PIECE_VALUE_BP=100;
    static int PIECE_VALUE_WN=300;          static int PIECE_VALUE_BN=300;
    static int PIECE_VALUE_WB=300;          static int PIECE_VALUE_BB=300;
    static int PIECE_VALUE_WR=500;          static int PIECE_VALUE_BR=500;
    static int PIECE_VALUE_WQ=900;          static int PIECE_VALUE_BQ=900;
    static int PIECE_VALUE_WK=1000000000;   static int PIECE_VALUE_BK=1000000000;
    
    
    public static int evaluate(long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ)
    {
        int score=0;
        
        return score;
    }
    public static int pieceSquare(String move, long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ)
    {
        int score=0;
        //Material score
        //pawn
        int i=0;
        i=Long.numberOfTrailingZeros(WP);
        
        
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
        score = Moves.moveScore(WP, move, 'P')+Moves.moveScore(WN, move, 'N')
              + Moves.moveScore(WB, move, 'B') + Moves.moveScore(WR, move, 'R')
              + Moves.moveScore(WQ, move, 'Q') + Moves.moveScore(WK, move, 'K')
              + Moves.moveScore(BP, move, 'p') + Moves.moveScore(BN, move, 'n')
              + Moves.moveScore(BB, move, 'b') + Moves.moveScore(BR, move, 'r')
              + Moves.moveScore(BQ, move, 'q') + Moves.moveScore(BK, move, 'k');
        
        return score;
    }
    
}
