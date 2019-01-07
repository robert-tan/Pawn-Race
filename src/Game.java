public class Game {

  private Board board;
  public Move[] moves;
  private int index;
  private Color currentPlayer;

  public Game(Board board) {
    this.board = board;
    moves = new Move[300];
    index = 0;
    currentPlayer = Color.WHITE;
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  public Move getLastMove() {
    if (index == 0) {
      return null;
    } else {
      return moves[index];
    }
  }

  public void applyMove(Move move) {
    if (currentPlayer == Color.WHITE) {
      currentPlayer = Color.BLACK;
    } else {
      currentPlayer = Color.WHITE;
    }
    index++;
    moves[index] = move;
    board.applyMove(move);
  }

  public void unapplyMove() {
    Move move = getLastMove();
    if (currentPlayer == Color.WHITE) {
      currentPlayer = Color.BLACK;
    } else {
      currentPlayer = Color.WHITE;
    }
    moves[index] = null;
    index--;
    board.unapplyMove(move);
  }

  public boolean isStuck() {
//    for (int i = 0; i < 8; i++) {
//      for (int j = 1; j < 7; j++) {
//        int a = board.getSquare(i, j).occupiedBy() == Color.WHITE ? 1 : -1;
//        Color c = board.getSquare(i, j).occupiedBy() == Color.WHITE ? Color.BLACK : Color.WHITE;
//        if (board.getSquare(i, j).occupiedBy() != Color.NONE) {
//          if (board.getSquare(i, j + a).occupiedBy() == Color.NONE) {
//            return false;
//          }
//          if (i != 0) {
//            if (board.getSquare(i - 1, j + a).occupiedBy() == c) {
//              return false;
//            }
//          }
//          if (i != 7) {
//            if (board.getSquare(i + 1, j + a).occupiedBy() == c) {
//              return false;
//            }
//          }
//        }
//      }
//    }
    return false;
  }

  public boolean isFinished() {
    if (lastLine(Color.WHITE) || lastLine(Color.BLACK) || noColor(Color.BLACK) || noColor(
        Color.WHITE)) {
      return true;
    }
    return false;
  }

  public boolean noColor(Color c) {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        if (board.getSquare(i, j).occupiedBy() == c) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean lastLine(Color c) {
    int a = c == Color.WHITE ? 7 : 0;
    for (int i = 0; i < 8; i++) {
      if (board.getSquare(i, a).occupiedBy() == c) {
        return true;
      }
    }
    return false;
  }

  public Color getGameResult() {
    if (noColor(Color.WHITE) || lastLine(Color.WHITE)) {
      return Color.WHITE;
    }
    else if (noColor(Color.BLACK) || lastLine(Color.BLACK)) {
      return Color.BLACK;
    }
    else {
      return Color.NONE;
    }
  }

  public Move parseMove(String san) {
    Square from = board.getSquare(san.charAt(0) - 'a', san.charAt(1) - '0' - 1);
    Square to = board.getSquare(san.charAt(3) - 'a', san.charAt(4) - '0' - 1);
    boolean isCapture = san.charAt(2) == 'x';
    boolean isEnPassantCapture = (to.occupiedBy() == Color.NONE) && isCapture;
    Move parsedMove = new Move(from, to, isCapture, isEnPassantCapture);
    return parsedMove;
  }
}
