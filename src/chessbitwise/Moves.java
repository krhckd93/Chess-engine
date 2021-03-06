
package chessbitwise;

import java.util.Arrays;
public class Moves {
    static long FILE_A=72340172838076673L;
    static long FILE_H=-9187201950435737472L;
    static long FILE_AB=217020518514230019L;
    static long FILE_GH=-4557430888798830400L;
    static long RANK_1=-72057594037927936L;
    static long RANK_4=1095216660480L;
    static long RANK_5=4278190080L;
    static long RANK_8=255L;
    static long CENTRE=103481868288L;
    static long EXTENDED_CENTRE=66229406269440L;
    static long KING_SIDE=-1085102592571150096L;
    static long QUEEN_SIDE=1085102592571150095L;
    static long KING_SPAN=460039L;
    static long KNIGHT_SPAN=43234889994L;
    static long NOT_MY_PIECES;
    static long MY_PIECES;
    static long OCCUPIED;
    static long EMPTY;
    static long CASTLE_ROOKS[]={63,56,7,0};
    static long attackWP=0L;static long attackWR=0L;static long attackWN=0L;static long attackWB=0L;static long attackWQ=0L;static long attackWK=0L;
    static long attackBP=0L;static long attackBR=0L;static long attackBN=0L;static long attackBB=0L;static long attackBQ=0L;static long attackBK=0L;
    static long blackAttack=0L, whiteAttack=0L;
    static long RankMasks8[] =/*from rank1 to rank8*/
    {
        0xFFL, 0xFF00L, 0xFF0000L, 0xFF000000L, 0xFF00000000L, 0xFF0000000000L, 0xFF000000000000L, 0xFF00000000000000L
    };
    static long FileMasks8[] =/*from fileA to FileH*/
    {
        0x101010101010101L, 0x202020202020202L, 0x404040404040404L, 0x808080808080808L,
        0x1010101010101010L, 0x2020202020202020L, 0x4040404040404040L, 0x8080808080808080L
    };
    static long DiagonalMasks8[] =/*from top left to bottom right*/
    {
	0x1L, 0x102L, 0x10204L, 0x1020408L, 0x102040810L, 0x10204081020L, 0x1020408102040L,
	0x102040810204080L, 0x204081020408000L, 0x408102040800000L, 0x810204080000000L,
	0x1020408000000000L, 0x2040800000000000L, 0x4080000000000000L, 0x8000000000000000L
    };
    static long AntiDiagonalMasks8[] =/*from top right to bottom left*/
    {
	0x80L, 0x8040L, 0x804020L, 0x80402010L, 0x8040201008L, 0x804020100804L, 0x80402010080402L,
	0x8040201008040201L, 0x4020100804020100L, 0x2010080402010000L, 0x1008040201000000L,
	0x804020100000000L, 0x402010000000000L, 0x201000000000000L, 0x100000000000000L
    };
    
    static long HAndVMoves(int s) {
        //NOTE: requires OCCUPIED to be up to date
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED&FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal&RankMasks8[s / 8]) | (possibilitiesVertical&FileMasks8[s % 8]);
    }
    static long DAndAntiDMoves(int s) {
        //NOTE: requires OCCUPIED to be up to date
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesDiagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
    }
    static long HAndVMoves1(int s,long OCCUPIED) {
        //NOTE: requires OCCUPIED to be up to date
        long binaryS=1L<<s;
        long possibilitiesHorizontal = (OCCUPIED - 2 * binaryS) ^ Long.reverse(Long.reverse(OCCUPIED) - 2 * Long.reverse(binaryS));
        long possibilitiesVertical = ((OCCUPIED&FileMasks8[s % 8]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&FileMasks8[s % 8]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesHorizontal&RankMasks8[s / 8]) | (possibilitiesVertical&FileMasks8[s % 8]);
    }
    static long DAndAntiDMoves1(int s,long OCCUPIED) {
        //NOTE: requires OCCUPIED to be up to date
        long binaryS=1L<<s;
        long possibilitiesDiagonal = ((OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&DiagonalMasks8[(s / 8) + (s % 8)]) - (2 * Long.reverse(binaryS)));
        long possibilitiesAntiDiagonal = ((OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * binaryS)) ^ Long.reverse(Long.reverse(OCCUPIED&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]) - (2 * Long.reverse(binaryS)));
        return (possibilitiesDiagonal&DiagonalMasks8[(s / 8) + (s % 8)]) | (possibilitiesAntiDiagonal&AntiDiagonalMasks8[(s / 8) + 7 - (s % 8)]);
    }
    
    public static long makeMove(long board, String move, char type) 
    {
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);} else { board&=~(1L<<end); }
        } else if (move.charAt(3)=='P') {//pawn promotion
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[1]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[6]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[7]);
            }
            if (type==move.charAt(2)) {board|=(1L<<end);} else {board&=~(1L<<start); board&=~(1L<<end);}
        } else if (move.charAt(3)=='E') {//en passant
            int start, end;
            if (move.charAt(2)=='W') {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[3]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[2]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[3]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[4]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[5]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[4]);
            }
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);}
        } else {
            System.out.print("ERROR: Invalid move type");
        }
        return board;
    }
    public static long unmakeMove(long board, String move, char type) 
    {
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int end=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int start=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);} else { board&=~(1L<<end); }
        } else if (move.charAt(3)=='P') {//pawn promotion
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[1]);
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[0]);
            } else {
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[6]);
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[7]);
            }
            if (type==move.charAt(2)) {board|=(1L<<end);} else {board&=~(1L<<start); board&=~(1L<<end);}
        } else if (move.charAt(3)=='E') {//en passant
            int start, end;
            if (move.charAt(2)=='W') {
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[3]);
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[2]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[3]);
            } else {
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[4]);
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[5]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[4]);
            }
            if (((board>>>start)&1)==1) {board&=~(1L<<start); board|=(1L<<end);}
        } else {
            System.out.print("ERROR: Invalid move type");
        }
        return board;
    }
    public static int moveScore(long board, String move, char type) 
    {
        int score=0;
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            int end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
            if (((board>>>start)&1)==1) 
            {
                board&=~(1L<<start); board|=(1L<<end);
                switch (type)
                {
                    case 'P' :  score += Rating.WP_PST[end/8][end%8]-Rating.WP_PST[start/8][start%8];
                            break;
                    case 'R' :  score += Rating.WR_PST[end/8][end%8]-Rating.WR_PST[start/8][start%8];
                            break;
                    case 'N' :  score += Rating.WN_PST[end/8][end%8]-Rating.WN_PST[start/8][start%8];
                            break;
                    case 'B' :  score += Rating.WB_PST[end/8][end%8]-Rating.WB_PST[start/8][start%8];
                            break;        
                    case 'Q' :  score += Rating.WQ_PST[end/8][end%8]-Rating.WQ_PST[start/8][start%8];
                            break;       
                    case 'K' :  score += Rating.WK_PST[end/8][end%8]-Rating.WK_PST[start/8][start%8];
                            break;       
                    case 'p' :  score += Rating.BP_PST[end/8][end%8]-Rating.BP_PST[start/8][start%8];
                            break;
                    case 'r' :  score += Rating.BR_PST[end/8][end%8]-Rating.BR_PST[start/8][start%8];
                            break;
                    case 'n' :  score += Rating.BN_PST[end/8][end%8]-Rating.BN_PST[start/8][start%8];
                            break;
                    case 'b' :  score += Rating.BB_PST[end/8][end%8]-Rating.BB_PST[start/8][start%8];
                            break;        
                    case 'q' :  score += Rating.BQ_PST[end/8][end%8]-Rating.BQ_PST[start/8][start%8];
                            break;       
                    case 'k' :  score += Rating.BK_PST[end/8][end%8]-Rating.BK_PST[start/8][start%8];
                            break;       
                }
            }
            else { board&=~(1L<<end); }
        } else if (move.charAt(3)=='P') {//pawn promotion
            int start, end;
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[1]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[6]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[7]);
            }
            if (type==move.charAt(2)) 
            {
                switch (type)
                {
                    case 'P' :  score += Rating.WP_PST[end/8][end%8]-Rating.WP_PST[start/8][start%8];
                            break;
                    case 'R' :  score += Rating.WR_PST[end/8][end%8]-Rating.WR_PST[start/8][start%8];
                            break;
                    case 'N' :  score += Rating.WN_PST[end/8][end%8]-Rating.WN_PST[start/8][start%8];
                            break;
                    case 'B' :  score += Rating.WB_PST[end/8][end%8]-Rating.WB_PST[start/8][start%8];
                            break;        
                    case 'Q' :  score += Rating.WQ_PST[end/8][end%8]-Rating.WQ_PST[start/8][start%8];
                            break;       
                    case 'K' :  score += Rating.WK_PST[end/8][end%8]-Rating.WK_PST[start/8][start%8];
                            break;       
                    case 'p' :  score += Rating.WP_PST[end/8][end%8]-Rating.WP_PST[start/8][start%8];
                            break;
                    case 'r' :  score += Rating.WR_PST[end/8][end%8]-Rating.WR_PST[start/8][start%8];
                            break;
                    case 'n' :  score += Rating.WN_PST[end/8][end%8]-Rating.WN_PST[start/8][start%8];
                            break;
                    case 'b' :  score += Rating.WB_PST[end/8][end%8]-Rating.WB_PST[start/8][start%8];
                            break;        
                    case 'q' :  score += Rating.WQ_PST[end/8][end%8]-Rating.WQ_PST[start/8][start%8];
                            break;       
                    case 'k' :  score += Rating.WK_PST[end/8][end%8]-Rating.WK_PST[start/8][start%8];
                            break;       
                }
            } 
            
        } else if (move.charAt(3)=='E') {//en passant
            int start, end;
            if (move.charAt(2)=='W') {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[3]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[2]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[3]);
            } else {
                start=Long.numberOfTrailingZeros(FileMasks8[move.charAt(0)-'0']&RankMasks8[4]);
                end=Long.numberOfTrailingZeros(FileMasks8[move.charAt(1)-'0']&RankMasks8[5]);
                board&=~(FileMasks8[move.charAt(1)-'0']&RankMasks8[4]);
            }
            if (((board>>>start)&1)==1) 
            {board&=~(1L<<start); board|=(1L<<end);
            switch (type)
                {
                    case 'P' :  score += Rating.WP_PST[end/8][end%8]-Rating.WP_PST[start/8][start%8];
                            break;
                    case 'R' :  score += Rating.WR_PST[end/8][end%8]-Rating.WR_PST[start/8][start%8];
                            break;
                    case 'N' :  score += Rating.WN_PST[end/8][end%8]-Rating.WN_PST[start/8][start%8];
                            break;
                    case 'B' :  score += Rating.WB_PST[end/8][end%8]-Rating.WB_PST[start/8][start%8];
                            break;        
                    case 'Q' :  score += Rating.WQ_PST[end/8][end%8]-Rating.WQ_PST[start/8][start%8];
                            break;       
                    case 'K' :  score += Rating.WK_PST[end/8][end%8]-Rating.WK_PST[start/8][start%8];
                            break;       
                    case 'p' :  score += Rating.WP_PST[end/8][end%8]-Rating.WP_PST[start/8][start%8];
                            break;
                    case 'r' :  score += Rating.WR_PST[end/8][end%8]-Rating.WR_PST[start/8][start%8];
                            break;
                    case 'n' :  score += Rating.WN_PST[end/8][end%8]-Rating.WN_PST[start/8][start%8];
                            break;
                    case 'b' :  score += Rating.WB_PST[end/8][end%8]-Rating.WB_PST[start/8][start%8];
                            break;        
                    case 'q' :  score += Rating.WQ_PST[end/8][end%8]-Rating.WQ_PST[start/8][start%8];
                            break;       
                    case 'k' :  score += Rating.WK_PST[end/8][end%8]-Rating.WK_PST[start/8][start%8];
                            break;       
                }}
        } else {
            System.out.print("ERROR: Invalid move type");
        }
        return score;
    }
    
    
    public static long makeMoveCastle(long rookBoard, long kingBoard, String move, char type) {
        int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
        if ((((kingBoard>>>start)&1)==1)&&(("0402".equals(move))||("0406".equals(move))||("7472".equals(move))||("7476".equals(move)))) {//'regular' move
            if (type=='R') {
                switch (move) {
                    case "7472": rookBoard&=~(1L<<CASTLE_ROOKS[1]); rookBoard|=(1L<<(CASTLE_ROOKS[1]+3));
                        break;
                    case "7476": rookBoard&=~(1L<<CASTLE_ROOKS[0]); rookBoard|=(1L<<(CASTLE_ROOKS[0]-2));
                        break;
                }
            } else {
                switch (move) {
                    case "0402": rookBoard&=~(1L<<CASTLE_ROOKS[3]); rookBoard|=(1L<<(CASTLE_ROOKS[3]+3));
                        break;
                    case "0406": rookBoard&=~(1L<<CASTLE_ROOKS[2]); rookBoard|=(1L<<(CASTLE_ROOKS[2]-2));
                        break;
                }
            }
        }
        return rookBoard;
    }
    public static long makeMoveEP(long board,String move) {
        if (Character.isDigit(move.charAt(3))) {
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            if ((Math.abs(move.charAt(0)-move.charAt(2))==2)&&(((board>>>start)&1)==1)) {//pawn double push
                return FileMasks8[move.charAt(1)-'0'];
            }
        }
        return 0;
    }
    public static long unmakeMoveEP(long board,String move) {
        if (Character.isDigit(move.charAt(3))) {
            int start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            if ((Math.abs(move.charAt(0)-move.charAt(2))==2)&&(((board>>>start)&1)==1)) {//pawn double push
                return FileMasks8[move.charAt(1)-'0'];
            }
        }
        return 0;
    }
    
    public static String possibleMovesW(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        NOT_MY_PIECES=~(WP|WN|WB|WR|WQ|WK|BK);//added BK to avoid illegal capture
        MY_PIECES=WP|WN|WB|WR|WQ;//omitted WK to avoid illegal capture
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        EMPTY=~OCCUPIED;
        
        String list=
                possibleWP(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleN(OCCUPIED,WN,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleB(OCCUPIED,WB,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleR(OCCUPIED,WR,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleQ(OCCUPIED,WQ,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleK(OCCUPIED,WK,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
                    possibleCW(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        return list;
    }
    public static String possibleMovesB(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        NOT_MY_PIECES=~(BP|BN|BB|BR|BQ|BK|WK);//added WK to avoid illegal capture
        MY_PIECES=BP|BN|BB|BR|BQ;//omitted BK to avoid illegal capture
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        EMPTY=~OCCUPIED;
        
//        String list=possibleBP(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleN(OCCUPIED,BN,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleB(OCCUPIED,BB,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleR(OCCUPIED,BR,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleQ(OCCUPIED,BQ,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleK(OCCUPIED,BK,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove)+
//                possibleCB(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
//        
        String list=possibleBP(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list += possibleN(OCCUPIED,BN,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list +=possibleB(OCCUPIED,BB,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list +=possibleR(OCCUPIED,BR,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list +=possibleQ(OCCUPIED,BQ,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list +=possibleK(OCCUPIED,BK,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
                list +=possibleCB(WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove);
        return list;
    }
    
    public static String possibleWP(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        //x1,y1,x2,y2
        long PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&OCCUPIED&~RANK_8&~FILE_A;//capture right
        long possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            
            move=""+(index/8+1)+(index%8-1)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&OCCUPIED&~RANK_8&~FILE_H;//capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8+1)+(index%8+1)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>8)&EMPTY&~RANK_8;//move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8+1)+(index%8)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>16)&EMPTY&(EMPTY>>8)&RANK_4;//move 2 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8+2)+(index%8)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,Promotion Type,"P"
        PAWN_MOVES=(WP>>7)&NOT_MY_PIECES&OCCUPIED&RANK_8&~FILE_A;//pawn promotion by capture right
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        String move1="";
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8-1)+(index%8)+"QP"+(index%8-1)+(index%8)+"RP"+(index%8-1)+(index%8)+"BP"+(index%8-1)+(index%8)+"NP";
            move1=move.substring(0,4);
            for(int i=4;i<16;i+=4)
            {
                if(kingSafe(move1,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                {list+=move1;}
                move1=move.substring(i, i+4);
            }
            PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(WP>>9)&NOT_MY_PIECES&OCCUPIED&RANK_8&~FILE_H;//pawn promotion by capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        move1="";
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8+1)+(index%8)+"QP"+(index%8+1)+(index%8)+"RP"+(index%8+1)+(index%8)+"BP"+(index%8+1)+(index%8)+"NP";
            move1=move.substring(0,4);
            for(int i=4;i<16;i+=4)
            {
                if(kingSafe(move1,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                {list+=move1;}
                move1=move.substring(i, i+4);
            }
            PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        move1="";
        PAWN_MOVES=(WP>>8)&EMPTY&RANK_8;//pawn promotion by move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8)+(index%8)+"QP"+(index%8)+(index%8)+"RP"+(index%8)+(index%8)+"BP"+(index%8)+(index%8)+"NP";
            move1=move.substring(0,4);
            for(int i=4;i<16;i+=4)
            {
                if(kingSafe(move1,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                {list+=move1;}
                move1=move.substring(i, i+4);
            }
            PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,"WE"
        //en passant right
        possibility = (WP << 1)&BP&RANK_5&~FILE_A&EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8-1)+(index%8)+"WE";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            
        }
        //en passant left
        possibility = (WP >> 1)&BP&RANK_5&~FILE_H&EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8+1)+(index%8)+"WE";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
        }
       return list;
    }
    public static String possibleBP(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        //x1,y1,x2,y2
        long PAWN_MOVES=(BP<<7)&NOT_MY_PIECES&OCCUPIED&~RANK_1&~FILE_H;//capture right
        long possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8-1)+(index%8+1)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(BP<<9)&NOT_MY_PIECES&OCCUPIED&~RANK_1&~FILE_A;//capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8-1)+(index%8-1)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(BP<<8)&EMPTY&~RANK_1;//move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8-1)+(index%8)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(BP<<16)&EMPTY&(EMPTY<<8)&RANK_5;//move 2 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index/8-2)+(index%8)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,Promotion Type,"P"
        PAWN_MOVES=(BP<<7)&NOT_MY_PIECES&OCCUPIED&RANK_1&~FILE_H;//pawn promotion by capture right
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8+1)+(index%8)+"qP"+(index%8+1)+(index%8)+"rP"+(index%8+1)+(index%8)+"bP"+(index%8+1)+(index%8)+"nP";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(BP<<9)&NOT_MY_PIECES&OCCUPIED&RANK_1&~FILE_A;//pawn promotion by capture left
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8-1)+(index%8)+"qP"+(index%8-1)+(index%8)+"rP"+(index%8-1)+(index%8)+"bP"+(index%8-1)+(index%8)+"nP";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        PAWN_MOVES=(BP<<8)&EMPTY&RANK_1;//pawn promotion by move 1 forward
        possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        while (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8)+(index%8)+"qP"+(index%8)+(index%8)+"rP"+(index%8)+(index%8)+"bP"+(index%8)+(index%8)+"nP";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            PAWN_MOVES&=~possibility;
            possibility=PAWN_MOVES&~(PAWN_MOVES-1);
        }
        //y1,y2,"BE"
        //en passant right
        possibility = (BP >> 1)&WP&RANK_4&~FILE_H&EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8+1)+(index%8)+"BE";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            
        }
        //en passant left
        possibility = (BP << 1)&WP&RANK_4&~FILE_A&EP;//shows piece to remove, not the destination
        if (possibility != 0)
        {
            int index=Long.numberOfTrailingZeros(possibility);
            move=""+(index%8-1)+(index%8)+"BE";
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
            { list+=move;   move="";}PAWN_MOVES&=~possibility;
            
        }
       return list;
    }
    
    public static String possibleR(long OCCUPIED,long R,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove)
    {
        String list="",move="";
        long i=R&~(R-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=HAndVMoves(iLocation)&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move+=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                { list+=move; move="";}possibility&=~j;
                j=possibility&~(possibility-1);
            }
            R&=~i;
            i=R&~(R-1);
        }
        return list;
    }
    public static String possibleN(long OCCUPIED,long N,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long i=N&~(N-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH&NOT_MY_PIECES;
            }
            else {
                possibility &=~FILE_AB&NOT_MY_PIECES;
            }
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                { list+=move;
                move="";
                }
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            N&=~i;
            i=N&~(N-1);
        }
        return list;
    }
    public static String possibleB(long OCCUPIED,long B,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long i=B&~(B-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=DAndAntiDMoves(iLocation)&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                { list+=move;
                move="";
                }
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            B&=~i;
            i=B&~(B-1);
        }
        return list;
    }
    
    public static String possibleQ(long OCCUPIED,long Q,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long i=Q&~(Q-1);
        long possibility;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=(HAndVMoves(iLocation)|DAndAntiDMoves(iLocation))&NOT_MY_PIECES;
            long j=possibility&~(possibility-1);
            while (j != 0)
            {
                int index=Long.numberOfTrailingZeros(j);
                move=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
                if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                { list+=move; move="";}
                possibility&=~j;
                j=possibility&~(possibility-1);
            }
            Q&=~i;
            i=Q&~(Q-1);
        }
        return list;
    }
    public static String possibleK(long OCCUPIED,long K,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long possibility;
        int iLocation=Long.numberOfTrailingZeros(K);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH&NOT_MY_PIECES;
        }
        else {
            possibility &=~FILE_AB&NOT_MY_PIECES;
        }
        long j=possibility&~(possibility-1);
        while (j != 0)
        {
            int index=Long.numberOfTrailingZeros(j);
            move=""+(iLocation/8)+(iLocation%8)+(index/8)+(index%8);
            if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                { list+=move;
                move="";
                }
            possibility&=~j;
            j=possibility&~(possibility-1);
        }
        return list;
    }
    
    public static String possibleCW(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long UNSAFE=unsafeForWhite(WP,WR,WN,WB,WQ,WK,BP,BR,BN,BB,BQ,BK);
        if ((UNSAFE&WK)==0) {
            if (CWK&&(((1L<<CASTLE_ROOKS[0])&WR)!=0))
            {
                if (((OCCUPIED|UNSAFE)&((1L<<61)|(1L<<62)))==0) {
                    move="7476";
                    if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                    { list+=move;   move="";}
                }
            }
            if (CWQ&&(((1L<<CASTLE_ROOKS[1])&WR)!=0))
            {
                if (((OCCUPIED|(UNSAFE&~(1L<<57)))&((1L<<57)|(1L<<58)|(1L<<59)))==0) {
                    move="7472";
                    if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                    { list+=move;   move="";}
                }
            }
        }
        return list;
    }
    public static String possibleCB(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove) 
    {
        String list="",move="";
        long UNSAFE=unsafeForBlack(WP,WR,WN,WB,WQ,WK,BP,BN,BB,BR,BQ,BK);
        if ((UNSAFE&BK)==0) {
            if (CBK&&(((1L<<CASTLE_ROOKS[2])&BR)!=0))
            {
                if (((OCCUPIED|UNSAFE)&((1L<<5)|(1L<<6)))==0) {
                    move="0406";
                    if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                    { list+=move;   move="";}
                }
            }
            if (CBQ&&(((1L<<CASTLE_ROOKS[3])&BR)!=0))
            {
                if (((OCCUPIED|(UNSAFE&~(1L<<1)))&((1L<<1)|(1L<<2)|(1L<<3)))==0) {
                    move="0402";
                    if(kingSafe(move,WP, WR, WN, WB, WQ, WK, BP, BR, BN, BB, BQ, BK, EP,CWK,CWQ,CBK,CBQ,WhiteToMove))
                    { list+=move;   move="";}
                }
            }
        }
        return list;
    }
    
    public static long unsafeForBlack(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK) 
    {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((WP>>>7)&~FILE_A);//pawn capture right
        unsafe|=((WP>>>9)&~FILE_H);//pawn capture left
        long possibility;
        //knight
        long i=WN&~(WN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            WN&=~i;
            i=WN&~(WN-1);
        }
        //bishop/queen
        long QB=WQ|WB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=DAndAntiDMoves(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=WQ|WR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=HAndVMoves(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(WK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        return unsafe;
    }
    public static long unsafeForWhite(long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK) 
    {
        long unsafe;
        OCCUPIED=WP|WN|WB|WR|WQ|WK|BP|BN|BB|BR|BQ|BK;
        //pawn
        unsafe=((BP<<7)&~FILE_H);//pawn capture right
        unsafe|=((BP<<9)&~FILE_A);//pawn capture left
        long possibility;
        //knight
        long i=BN&~(BN-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
            {
                possibility=KNIGHT_SPAN<<(iLocation-18);
            }
            else {
                possibility=KNIGHT_SPAN>>(18-iLocation);
            }
            if (iLocation%8<4)
            {
                possibility &=~FILE_GH;
            }
            else {
                possibility &=~FILE_AB;
            }
            unsafe |= possibility;
            BN&=~i;
            i=BN&~(BN-1);
        }
        //bishop/queen
        long QB=BQ|BB;
        i=QB&~(QB-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=DAndAntiDMoves(iLocation);
            unsafe |= possibility;
            QB&=~i;
            i=QB&~(QB-1);
        }
        //rook/queen
        long QR=BQ|BR;
        i=QR&~(QR-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            possibility=HAndVMoves(iLocation);
            unsafe |= possibility;
            QR&=~i;
            i=QR&~(QR-1);
        }
        //king
        int iLocation=Long.numberOfTrailingZeros(BK);
        if (iLocation>9)
        {
            possibility=KING_SPAN<<(iLocation-9);
        }
        else {
            possibility=KING_SPAN>>(9-iLocation);
        }
        if (iLocation%8<4)
        {
            possibility &=~FILE_GH;
        }
        else {
            possibility &=~FILE_AB;
        }
        unsafe |= possibility;
        return unsafe;
    }
    
    public static void drawBitboard(long bitBoard) 
    {
        String chessBoard[][]=new String[8][8];
        for (int i=0;i<64;i++) {
            chessBoard[i/8][i%8]="";
        }
        for (int i=0;i<64;i++) {
            if (((bitBoard>>>i)&1)==1) {chessBoard[i/8][i%8]="P";}
            if ("".equals(chessBoard[i/8][i%8])) {chessBoard[i/8][i%8]=" ";}
        }
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }
    
    public static int whiteAttack(long WP,long WR,long WN,long WB,long WQ,long WK)
    {
        
        return 0;
    }
    
    public static int blackAttack(long BP,long BR,long BN,long BB,long BQ,long BK)
    {
        
        return 0;
    }
    
    
    public static boolean isUnderCheck()
    {
        // if(WK &( OR of attack of all the enemy pieces))
        if((UserInterface.WK & (Moves.attackBP | Moves.attackBR | Moves.attackBN | Moves.attackBB | Moves.attackBQ | Moves.attackBK))== UserInterface.WK)
        {
            return true;
        }
        else 
            return false;
    }
    
    public static boolean kingSafe(String move,long WP,long WR,long WN,long WB,long WQ,long WK,long BP,long BR,long BN,long BB,long BQ,long BK,long EP,boolean CWK,boolean CWQ,boolean CBK,boolean CBQ,boolean WhiteToMove)
    {
        long P,R,N,B,Q,K,EK,enemyAttack=0L,OCCUPIED,NOT_MY_PIECES,EMPTY,MY_PIECES;
        
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
                else if (((1L<<start)&WR&(1L<<Moves.CASTLE_ROOKS[0]))!=0) {CWKt=false;}
                else if (((1L<<start)&WR&(1L<<Moves.CASTLE_ROOKS[1]))!=0) {CWQt=false;}
                else if (((1L<<start)&BR&(1L<<Moves.CASTLE_ROOKS[2]))!=0) {CBKt=false;}
                else if (((1L<<start)&BR&1L)!=0) {CBQt=false;}
            }
        
        OCCUPIED=WPt|WNt|WBt|WRt|WQt|WKt|BPt|BNt|BBt|BRt|BQt|BKt;
        EMPTY=~OCCUPIED;
        
        if(WhiteToMove)
        {
            R=BRt;N=BNt;B=BBt;Q=BQt;EK=BKt;K=WKt;
            NOT_MY_PIECES=~(BPt|BNt|BBt|BRt|BQt|BKt);
            MY_PIECES=BPt|BNt|BBt|BRt|BQt|WKt;
            enemyAttack |=(BPt<<7)&~FILE_H;//capture right
            enemyAttack |=(BPt<<9)&~FILE_A;//capture left
        }
        else
        {
            R=WRt;N=WNt;B=WBt;Q=WQt;EK=WKt;K=BKt;
            NOT_MY_PIECES=~(WPt|WNt|WBt|WRt|WQt|WKt);
            MY_PIECES=WPt|WNt|WBt|WRt|WQt|BKt;
            enemyAttack |= (WPt>>7)&~FILE_A;//capture right
            enemyAttack |=(WPt>>9)&~FILE_H;//capture left
            
        }
        if((K&(enemyAttack))==K)
            return false;

        //queen
        long i=Q&~(Q-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            enemyAttack |=(HAndVMoves1(iLocation,OCCUPIED)|DAndAntiDMoves1(iLocation,OCCUPIED))&NOT_MY_PIECES;
            enemyAttack |=(HAndVMoves1(iLocation,OCCUPIED)|DAndAntiDMoves1(iLocation,OCCUPIED))&MY_PIECES;
            Q&=~i;
            i=Q&~(Q-1);
        }
        
        if((K&(enemyAttack))==K)
            return false;
        //bishop
        i=B&~(B-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            enemyAttack |=DAndAntiDMoves1(iLocation,OCCUPIED)&NOT_MY_PIECES;
            enemyAttack |=DAndAntiDMoves1(iLocation,OCCUPIED)&MY_PIECES;
            B&=~i;
            i=B&~(B-1);
        }
        if((K&(enemyAttack))==K)
            return false;
        //rook
        i=R&~(R-1);
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            enemyAttack |=HAndVMoves1(iLocation,OCCUPIED)&NOT_MY_PIECES;
            enemyAttack |=HAndVMoves1(iLocation,OCCUPIED)&MY_PIECES;
            R&=~i;
            i=R&~(R-1);
        }
        if((K&(enemyAttack))==K)
            return false;
        //knight
        i=N&~(N-1);
        long possibility=0L;
        while(i != 0)
        {
            int iLocation=Long.numberOfTrailingZeros(i);
            if (iLocation>18)
                possibility=KNIGHT_SPAN<<(iLocation-18);
            else
                possibility=KNIGHT_SPAN>>(18-iLocation);
            if (iLocation%8<4)
            {    
                possibility &=~FILE_GH;
            }
            else 
            {
                possibility &=~FILE_AB;
            }
            enemyAttack |= possibility;
            N&=~i;
            i=N&~(N-1);
        }
        if((K&(enemyAttack))==K)
            return false;
        //King
        possibility=0L;
        int iLocation=Long.numberOfTrailingZeros(EK);
        if (iLocation>9)
            possibility=KING_SPAN<<(iLocation-9);
        else 
            possibility=KING_SPAN>>(9-iLocation);
        if (iLocation%8<4)
            {
            possibility &=~FILE_GH;
            }
        else 
            {
            possibility &=~FILE_AB;
            }        
        enemyAttack |= possibility;
        
        if(K==(K&enemyAttack))
            return false;
        else 
            return true;
    }

}