package chessbitwise;

public class PrincipalVariation {
    public static String zWSearch(String move,int beta,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,int depth)
    {//fail-hard zero window search, returns either beta-1 or beta
        int score = Integer.MIN_VALUE;
        String moveScore="";
        //alpha == beta - 1
        //this is either a cut- or all-node
        if (depth == UserInterface.searchDepth)
        {
            score = Rating.evaluate(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ,WhiteToMove);
            moveScore = move+score;
            return moveScore;
        }
            String moves;
            if (WhiteToMove) {
                moves=Moves.possibleMovesW(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
            } else {
                moves=Moves.possibleMovesB(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
            }
            //sortMoves();
            for (int i=0;i<moves.length();i+=4) {
                long WPt=Moves.makeMove(WP, moves.substring(i,i+4), 'P'), WNt=Moves.makeMove(WN, moves.substring(i,i+4), 'N'),
                        WBt=Moves.makeMove(WB, moves.substring(i,i+4), 'B'), WRt=Moves.makeMove(WR, moves.substring(i,i+4), 'R'),
                        WQt=Moves.makeMove(WQ, moves.substring(i,i+4), 'Q'), WKt=Moves.makeMove(WK, moves.substring(i,i+4), 'K'),
                        BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'p'), BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'n'),
                        BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'b'), BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'r'),
                        BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'q'), BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'k'),
                        EPt=Moves.makeMoveEP(WP|BP,moves.substring(i,i+4));
                WRt=Moves.makeMoveCastle(WRt, WK|BK, moves.substring(i,i+4), 'R');
                BRt=Moves.makeMoveCastle(BRt, WK|BK, moves.substring(i,i+4), 'r');
                boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
                if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                    int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                    if (((1L<<start)&WK)!=0) {CWKt=false; CWQt=false;}
                    else if (((1L<<start)&BK)!=0) {CBKt=false; CBQt=false;}
                    else if (((1L<<start)&WR&(1L<<63))!=0) {CWKt=false;}
                    else if (((1L<<start)&WR&(1L<<56))!=0) {CWQt=false;}
                    else if (((1L<<start)&BR&(1L<<7))!=0) {CBKt=false;}
                    else if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
                }
                System.out.println("ZW");
                BoardGeneration.drawArray(WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt);
                System.out.println();
                if (((WKt&Moves.unsafeForWhite(WPt,WRt,WNt,WBt,WQt,WKt,BPt,BRt,BNt,BBt,BQt,BKt))==0 && WhiteToMove) ||
                        ((BKt&Moves.unsafeForBlack(WPt,WRt,WNt,WBt,WQt,WKt,BPt,BRt,BNt,BBt,BQt,BKt))==0 && !WhiteToMove)) {
                    moveScore = zWSearch(moves.substring(i,i+4),1 - beta,WPt,WRt,WNt,WBt,WQt,WKt,BPt,BRt,BNt,BBt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt,!WhiteToMove,depth+1);
                    if(Integer.parseInt(moveScore.substring(4)) < 0)
                        moveScore = moves.substring(0, 3)+moveScore.substring(5);
                    else if (Integer.parseInt(moveScore.substring(4)) >0)
                        moveScore = moves.substring(0, 3)+"-"+moveScore.substring(4);
                    
                    score = Integer.parseInt(moveScore.substring(4));
                }
                if (score >= beta)
                {
                    return moveScore;//fail-hard beta-cutoff
                }
            }
        return move+(beta - 1);//fail-hard, return alpha
    }
    public static String moveOrdering(String moves,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String scoreList="",sortedScoreList="",sortedMoves="";
        int currentScore=0,score=0;
        int x1=0,y1=0;
        float x=0,y=0,maxIndex=0;
        
        if(moves.length() != 0)
        {
            for (int i=0;i<moves.length();i+=4) {
                score = Rating.pieceSquare(moves.substring(i, i+4), WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ);
                if(score >=0 )
                    if(score/10==0)
                        scoreList += "00"+ Integer.toString(score);
                    else
                        scoreList += "0"+ Integer.toString(score);
                else 
                    if(score/10 != 0)
                        scoreList += Integer.toString(score);
                    else
                        scoreList += "-0"+Integer.toString(score).substring(1);
            }
            x=scoreList.length();
            y=moves.length();
            float max=Float.parseFloat(scoreList.substring(0,3)+".0");
            for(int j=0;j<scoreList.length();j+=3)
            {
                max=Float.parseFloat(scoreList.substring(0, 3)); 
                maxIndex=0;
                for(int i=0;i<scoreList.length();i+=3)
                {
                    currentScore=Integer.parseInt(scoreList.substring(i, i+3));
                    if(Integer.parseInt(scoreList.substring(i, i+3)) > max)
                    {
                        max=currentScore;
                        maxIndex=i;
                    }
                }
                if((int)max>=0)
                    if((int)max/10 ==0)
                        sortedScoreList+= "00"+(int)max;
                    else
                        sortedScoreList+= "0"+(int)max;
                else
                    if((int)max/10 ==0)
                        sortedScoreList+= "-0"+Integer.toString((int)max).substring(1);
                    else
                        sortedScoreList+=(int)max;
                    
                sortedMoves += moves.substring((int)((maxIndex/(x+0.0))*y), (int)((maxIndex/(x+0.0))*y)+4);
                //replace max in score list.
                if(maxIndex != 0)
                {
                scoreList = scoreList.substring(0, (int)maxIndex) + scoreList.substring((int)maxIndex+3);
                moves = moves.substring(0, (int)((maxIndex/(x+0.0))*y)) + moves.substring((int)((maxIndex/(x+0.0))*y)+4);
                }
                else
                {
                    scoreList=scoreList.substring((int)maxIndex+3);
                    moves=moves.substring((int)((maxIndex/(x+0.0))*y)+4);
                }
                x=x-3;
                y =y-4;
                j=0;
            }
            sortedScoreList+=scoreList;
            sortedMoves+= moves;
            x1=sortedMoves.length();
            y1=sortedScoreList.length();
            
            return sortedMoves;
        }
        return "";
    }
    public static String pvSearch(String move,int alpha,int beta,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,int depth) 
    {//using fail soft with negamax
        int bestScore;
        String moveScore="";
        int bestMoveIndex = -1;
        if (depth == UserInterface.searchDepth)
        {
            bestScore = Rating.evaluate(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP, CWK, CWQ, CBK, CBQ,WhiteToMove);
            moveScore=move+bestScore;
            System.out.println(moveScore);
            return moveScore;
        }
        String moves;
        if (WhiteToMove) {
            moves=Moves.possibleMovesW(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        } else {
            moves=Moves.possibleMovesB(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        }
        //sortMoves();
        int firstLegalMove = 0;
        moves=moveOrdering(moves,WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        if (moves.length() ==0)
        {
            return WhiteToMove ? "0000"+UserInterface.MATE_SCORE : "0000"+-UserInterface.MATE_SCORE;
        }
        long WPt=Moves.makeMove(WP, moves.substring(firstLegalMove,firstLegalMove+4), 'P'), WNt=Moves.makeMove(WN, moves.substring(firstLegalMove,firstLegalMove+4), 'N'),
                WBt=Moves.makeMove(WB, moves.substring(firstLegalMove,firstLegalMove+4), 'B'), WRt=Moves.makeMove(WR, moves.substring(firstLegalMove,firstLegalMove+4), 'R'),
                WQt=Moves.makeMove(WQ, moves.substring(firstLegalMove,firstLegalMove+4), 'Q'), WKt=Moves.makeMove(WK, moves.substring(firstLegalMove,firstLegalMove+4), 'K'),
                BPt=Moves.makeMove(BP, moves.substring(firstLegalMove,firstLegalMove+4), 'p'), BNt=Moves.makeMove(BN, moves.substring(firstLegalMove,firstLegalMove+4), 'n'),
                BBt=Moves.makeMove(BB, moves.substring(firstLegalMove,firstLegalMove+4), 'b'), BRt=Moves.makeMove(BR, moves.substring(firstLegalMove,firstLegalMove+4), 'r'),
                BQt=Moves.makeMove(BQ, moves.substring(firstLegalMove,firstLegalMove+4), 'q'), BKt=Moves.makeMove(BK, moves.substring(firstLegalMove,firstLegalMove+4), 'k'),
                EPt=Moves.makeMoveEP(WP|BP,moves.substring(firstLegalMove,firstLegalMove+4));
        WRt=Moves.makeMoveCastle(WRt, WK|BK, moves.substring(firstLegalMove,firstLegalMove+4), 'R');
        BRt=Moves.makeMoveCastle(BRt, WK|BK, moves.substring(firstLegalMove,firstLegalMove+4), 'r');
        boolean CWKt=CWK,CWQt=CWQ,CBKt=CBK,CBQt=CBQ;
        if (Character.isDigit(moves.charAt(firstLegalMove+3))) {//'regular' move
            int start=(Character.getNumericValue(moves.charAt(firstLegalMove))*8)+(Character.getNumericValue(moves.charAt(firstLegalMove+1)));
            if (((1L<<start)&WK)!=0) {CWKt=false; CWQt=false;}
            else if (((1L<<start)&BK)!=0) {CBKt=false; CBQt=false;}
            else if (((1L<<start)&WR&(1L<<63))!=0) {CWKt=false;}
            else if (((1L<<start)&WR&(1L<<56))!=0) {CWQt=false;}
            else if (((1L<<start)&BR&(1L<<7))!=0) {CBKt=false;}
            else if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
        }
        System.out.println("PVS");
        BoardGeneration.drawArray(WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt);
        System.out.println();
        moveScore = pvSearch(moves.substring(firstLegalMove,firstLegalMove+4),-beta,-alpha,WPt,WRt,WNt,WBt,WQt,WKt,BPt,BRt,BNt,BBt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt,!WhiteToMove,depth+1);
        if(Integer.parseInt(moveScore.substring(4))<0)
            moveScore = moveScore.substring(0, 4)+moveScore.substring(5);
        else if(Integer.parseInt(moveScore.substring(4))>0)
            moveScore = moveScore.substring(0, 4)+"-"+moveScore.substring(4);
        else
            moveScore = moveScore.substring(0, 4)+moveScore.substring(4);
        bestScore= Integer.parseInt(moveScore.substring(4));
        UserInterface.moveCounter++;
        if (bestScore == UserInterface.MATE_SCORE)
        {
            System.out.println(moveScore);
            return moveScore;
        }
        if (bestScore > alpha)
        {
            if (bestScore >= beta)
            {
                //This is a refutation move
                //It is not a PV move
                //However, it will usually cause a cutoff so it can
                //be considered a best move if no other move is found
                System.out.println(moveScore);
                return moveScore;
            }
            alpha = bestScore;
        }
        bestMoveIndex = firstLegalMove;
        for (int i=firstLegalMove;i<moves.length();i+=4) {
            int score;
            String zwmoveScore="";
            zwmoveScore = moves.substring(i,i+4);

            UserInterface.moveCounter++;
            //legal, non-castle move
            WPt=Moves.makeMove(WP, moves.substring(i,i+4), 'P');
            WNt=Moves.makeMove(WN, moves.substring(i,i+4), 'N');
            WBt=Moves.makeMove(WB, moves.substring(i,i+4), 'B');
            WRt=Moves.makeMove(WR, moves.substring(i,i+4), 'R');
            WQt=Moves.makeMove(WQ, moves.substring(i,i+4), 'Q');
            WKt=Moves.makeMove(WK, moves.substring(i,i+4), 'K');
            BPt=Moves.makeMove(BP, moves.substring(i,i+4), 'p');
            BNt=Moves.makeMove(BN, moves.substring(i,i+4), 'n');
            BBt=Moves.makeMove(BB, moves.substring(i,i+4), 'b');
            BRt=Moves.makeMove(BR, moves.substring(i,i+4), 'r');
            BQt=Moves.makeMove(BQ, moves.substring(i,i+4), 'q');
            BKt=Moves.makeMove(BK, moves.substring(i,i+4), 'k');
            EPt=Moves.makeMoveEP(WP|BP,moves.substring(i,i+4));
            WRt=Moves.makeMoveCastle(WRt, WK|BK, moves.substring(i,i+4), 'R');
            BRt=Moves.makeMoveCastle(BRt, WK|BK, moves.substring(i,i+4), 'r');
            CWKt=CWK;
            CWQt=CWQ;
            CBKt=CBK;
            CBQt=CBQ;
            if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                int start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                if (((1L<<start)&WK)!=0) {CWKt=false; CWQt=false;}
                else if (((1L<<start)&BK)!=0) {CBKt=false; CBQt=false;}
                else if (((1L<<start)&WR&(1L<<63))!=0) {CWKt=false;}
                else if (((1L<<start)&WR&(1L<<56))!=0) {CWQt=false;}
                else if (((1L<<start)&BR&(1L<<7))!=0) {CBKt=false;}
                else if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
            }
            System.out.println("PVS");
            BoardGeneration.drawArray(WPt, WRt, WNt, WBt, WQt, WKt, BPt, BRt, BNt, BBt, BQt, BKt);
            System.out.println();
            zwmoveScore = zWSearch(moves.substring(i,i+4),-alpha,WPt,WRt,WNt,WBt,WQt,WKt,BPt,BRt,BNt,BBt,BQt,BKt,EPt,CWKt,CWQt,CBKt,CBQt,!WhiteToMove,depth+1);
            if(Integer.parseInt(zwmoveScore.substring(4)) <0)
                zwmoveScore = moves.substring(0, 4)+zwmoveScore.substring(5);
            else if(Integer.parseInt(zwmoveScore.substring(4)) > 0)
                zwmoveScore = moves.substring(0, 4)+"-"+zwmoveScore.substring(4);
    
            score=Integer.parseInt(zwmoveScore.substring(4));
            if ((score > alpha) && (score < beta))
            {
                //research with window [alpha;beta]
                moveScore = pvSearch(moveScore.substring(0, 4),-beta,-alpha,WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK,EP,CWK,CWQ,CBK,CBQ,!WhiteToMove,depth+1);
                if(Integer.parseInt(moveScore.substring(4))<0)
                    moveScore = moveScore.substring(0, 4)+moveScore.substring(5);
                else if(Integer.parseInt(moveScore.substring(4))<0)
                    moveScore = moveScore.substring(0, 4)+"-"+moveScore.substring(4);

                bestScore = Integer.parseInt(moveScore.substring(4));
                if (score>alpha)
                {
                    bestMoveIndex = i;
                    moveScore = zwmoveScore.substring(0, 4)+bestScore;
                    alpha = score;
                }
            }
            if ((score != UserInterface.NULL_INT) && (score > bestScore))
            {
                if (score >= beta)
                {
                    System.out.println(zwmoveScore);
                    return zwmoveScore;
                }
                moveScore = zwmoveScore.substring(0, 3)+score;
                bestScore = score;
                if (Math.abs(bestScore) == UserInterface.MATE_SCORE)
                {
                    System.out.println(moveScore);
                    return moveScore;
                }
            }
        }
        System.out.println(moveScore);
        return moveScore;
    }

//    public static String alphabeta(String move,int alpha,int beta,long WP,long WN,long WB,long WR,long WQ,long WK,long BP,long BN,long BB,long BR,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove,int depth)
//    {
//        String moves="";
//        if (WhiteToMove) {
//            moves=Moves.possibleMovesW(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
//        } else {
//            moves=Moves.possibleMovesB(WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
//        }
//        int firstLegalMove=0;
//        moves = getFirstLegalMove(moves,WP,WN,WB,WR,WQ,WK,BP,BN,BB,BR,BQ,BK,EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
//        
//        for(int i=0;i<moves.length();i+=4)
//        {
//            
//        }
//        
//        return "";
//    }
}