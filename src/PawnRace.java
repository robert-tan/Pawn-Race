import javafx.util.Pair;

public class PawnRace {

  public static void main(String[] args) {
    String results = "";
    boolean isPlayerOneC = args[0].equals("C");
    boolean isPlayerTwoC = args[1].equals("C");
    char whiteGap = Character.toLowerCase(args[2].charAt(0));
    char blackGap = Character.toLowerCase(args[3].charAt(0));
    int whiteWin = 0;
    int blackWin = 0;
    int draw = 0;
    while (true) {
      Board board = new Board(whiteGap, blackGap);
      Game game = new Game(board);
      Player p1 = new Player(game, board, Color.WHITE, isPlayerOneC);
      Player p2 = new Player(game, board, Color.BLACK, isPlayerTwoC);
      Player currentPlayer = p1;
      p1.setOpponent(p2);
      p2.setOpponent(p1);
      MinimaxAI p1AI = new MinimaxAI(p1);
      MinimaxAI p2AI = new MinimaxAI(p2);
      while (!currentPlayer.isFinished()) {
        board.display();
        if (game.getCurrentPlayer() == Color.WHITE) {
          //Pair<Integer, Move> hi = p1AI.minimax(7, p1.getColor(), Integer.MIN_VALUE, Integer.MAX_VALUE);
          //System.out.println(hi.getKey() + " " + hi.getValue().getSAN());
          if (p1.isComputerPlayer()) {
            p1.makeMove(p1AI.minimaxBestMove(8));
            System.out.println("Mr.White's last move is " + game.getLastMove().getSAN());
          } else {
            System.out.println("Mr.White Give command please :))");
//                    Move move = game.parseMove(IOUtil.readString());
//                    Move [] validMoves = p1.getAllValidMoves();
//                    p1.makeMove(IOUtil.readInt());
            game.applyMove(game.parseMove(IOUtil.readString()));
//                    System.out.println("WHITE" + p1.isPassed(game.getLastMove().getTo()));
          }
        } else {
//        System.out.println(p2AI.minimax(7, p2.getColor(), Integer.MIN_VALUE, Integer.MAX_VALUE).getKey());
          if (p2.isComputerPlayer()) {
            p2.makeMove(p2AI.minimaxBestMove(5));
            System.out.println("Mr.Black's last move is " + game.getLastMove().getSAN());
          } else {
            System.out.println("Mr.Black Give command please :))");
            game.applyMove(game.parseMove(IOUtil.readString()));
//                    Move [] validMoves = p2.getAllValidMoves();
//                    p2.makeMove(IOUtil.readInt());
//                    System.out.println("BLACK" + p2.isPassed(game.getLastMove().getTo()));
          }
        }
        if (game.getCurrentPlayer() == Color.WHITE)
          currentPlayer = p1;
        else
          currentPlayer = p2;
      }
      if (game.getGameResult() == Color.NONE) {
        System.out.println("DRAW");
        draw++;
      } else {
        System.out.println(game.getGameResult() + " WINS!!!");
        if (game.getGameResult() == Color.WHITE) whiteWin++;
        else blackWin++;
      }
      if (whiteGap == 'h' && blackGap == 'h') break;
      if (blackGap == 'h') {
        whiteGap ++;
        blackGap = 'a';
      }
      else {
        blackGap++;
      }
      results += whiteGap + " " + blackGap + " " + game.getGameResult() + "\n";
    }
    System.out.println(results);
    System.out.println("White Wins: " + whiteWin);
    System.out.println("Black Wins: " + blackWin);
    System.out.println("Draws: " + draw);
  }
}