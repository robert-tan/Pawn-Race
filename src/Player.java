import java.util.Random;

public class Player {

  private Game game;
  private Board board;
  private Color color;
  private boolean isComputerPlayer;
  private Player opponent;
  private int startingRank;
  private int direction;

  public Player(Game game, Board board, Color color, boolean isComputerPlayer) {
    this.game = game;
    this.board = board;
    this.color = color;
    this.isComputerPlayer = isComputerPlayer;
    if (color == Color.WHITE) {
      startingRank = 1;
      direction = 1;
    }
    else {
      startingRank = 6;
      direction = -1;
    }
  }

  public void setOpponent(Player opponent) {
    this.opponent = opponent;
  }

  public Player getOpponent() {
    return opponent;
  }

  public Game getGame() {
    return game;
  }

  public Board getBoard() {
    return board;
  }

  public Color getColor() {
    return color;
  }

  public boolean isComputerPlayer() {
    return isComputerPlayer;
  }

  public boolean isFinished() {
    if (game.isFinished() || isStuck()) {
      return true;
    }
    return false;
  }

  private boolean isStuck() {
    if (getAllValidMoves()[0] == null) {
      return true;
    }
    return false;
  }

  public Square[] getAllPawns() {
    Square[] allPawns = new Square[7];
    int num = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board.getSquare(i, j).occupiedBy() == color) {
          allPawns[num] = board.getSquare(i, j);
          num++;
        }
      }
    }
    return allPawns;
  }

  public int getNumAllPawns() {
    int num = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board.getSquare(i, j).occupiedBy() == color) {
          num++;
        }
      }
    }
    return num;
  }

  public Move[] getAllValidMoves() {
    Move[] moves = new Move[28];
    Square[] allPawns = getAllPawns();
    int index = 0;
    Color c = color == Color.WHITE ? Color.BLACK : Color.WHITE;
    int a = color == Color.WHITE ? 1 : -1;
    int startRow = color == Color.WHITE ? 1 : 6;
    for (int i = 0; i < 7 && allPawns[i] != null; i++) {
      Square pawn = allPawns[i];
      int x = pawn.getX();
      int y = pawn.getY();
      if (y == startRow && board.getSquare(x, y + 2 * a).occupiedBy() == Color.NONE &&
          board.getSquare(x, y + a).occupiedBy() == Color.NONE) {
        moves[index] = new Move(pawn, board.getSquare(x, y + 2 * a), false, false);
        index++;
      }
      if (y != 7 && y != 0 && board.getSquare(x, y + a).occupiedBy() == Color.NONE) {
        moves[index] = new Move(pawn, board.getSquare(x, y + a), false, false);
        index++;
      }
      if (x != 7 && y != 7 && y != 0 && board.getSquare(x + 1, y + a).occupiedBy() == c) {
        moves[index] = new Move(pawn, board.getSquare(x + 1, y + a), true, false);
        index++;
      }
      if (x != 0 && y != 7 && y != 0 && board.getSquare(x - 1, y + a).occupiedBy() == c) {
        moves[index] = new Move(pawn, board.getSquare(x - 1, y + a), true, false);
        index++;
      }
      if (game.getLastMove() != null) {
        Square opFrom = game.getLastMove().getFrom();
        Square opTo = game.getLastMove().getTo();
        int b = color == Color.WHITE ? 6 : 1;
        if (opFrom.getY() == b && y == b - 2 * a && y == opTo.getY()) {
          if (opFrom.getX() == x - 1) {
            moves[index] = new Move(pawn, board.getSquare(x - 1, y + a), true, true);
            index++;
          } else if (opFrom.getX() == x + 1) {
            moves[index] = new Move(pawn, board.getSquare(x + 1, y + a), true, true);
            index++;
          }
        }
      }
    }
//    for (int i = 0; i < index; i++) {
//      System.out.println(moves[i].getSAN());
//    }
    return moves;
  }

  public boolean isPassed(Square square) {
    int x = square.getX();
    int y = square.getY();
    if (square.occupiedBy() == Color.WHITE) {
      for (int j = y; j < 8; j++) {
        if (board.getSquare(x, j).occupiedBy() == Color.BLACK) {
          return false;
        }
        if (x != 0 && board.getSquare(x - 1, j).occupiedBy() == Color.BLACK) {
          return false;
        }
        if (x != 7 && board.getSquare(x + 1, j).occupiedBy() == Color.BLACK) {
          return false;
        }
      }
    } else {
      for (int j = y; j >= 0; j--) {
        if (board.getSquare(x, j).occupiedBy() == Color.WHITE) {
          return false;
        }
      }
      if (x != 0) {
        for (int j = y; j >= 0; j--) {
          if (board.getSquare(x - 1, j).occupiedBy() == Color.WHITE) {
            return false;
          }
        }
      }
      if (x != 7) {
        for (int j = y; j >= 0; j--) {
          if (board.getSquare(x + 1, j).occupiedBy() == Color.WHITE) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public boolean hasOpenFile(Square square) {
    int x = square.getX();
    int y = square.getY();
    int count = 3;
    if (square.occupiedBy() == Color.WHITE) {
      for (int j = y; j < 8; j++) {
        if (board.getSquare(x, j).occupiedBy() == Color.BLACK) {
          count--;
        }
        if (x != 0 && board.getSquare(x - 1, j).occupiedBy() == Color.BLACK) {
          count--;
        }
        if (x != 7 && board.getSquare(x + 1, j).occupiedBy() == Color.BLACK) {
          count--;
        }
      }
    } else {
      for (int j = y; j >= 0; j--) {
        if (board.getSquare(x, j).occupiedBy() == Color.WHITE) {
          count--;
        }
      }
      if (x != 0) {
        for (int j = y; j >= 0; j--) {
          if (board.getSquare(x - 1, j).occupiedBy() == Color.WHITE) {
            count--;
          }
        }
      }
      if (x != 7) {
        for (int j = y; j >= 0; j--) {
          if (board.getSquare(x + 1, j).occupiedBy() == Color.WHITE) {
            count--;
          }
        }
      }
    }
    return !(count == 0);
  }

  public int numProtectedPawns() {
    int count = 0;
    for (Square pawn : getAllPawns()) {
      if (pawn != null && isProtected(pawn)) {
        count++;
      }
    }
    return count;
  }

  private boolean isProtected(Square pawn) {
    int a = (pawn.occupiedBy() == Color.WHITE) ? 1 : -1;
    if (pawn.getX() != 7) {
      if (board.getSquare(pawn.getY() - a, pawn.getX() + 1).occupiedBy() == color) return true;
    }
    if (pawn.getX() != 0) {
      if (board.getSquare(pawn.getY() - a, pawn.getX() - 1).occupiedBy() == color) return true;
    }
    return false;
  }

  public boolean guarding(Square pawn) {
    if (pawn.getX() != 7) {
      if (board.getSquare(pawn.getX() + 1,pawn.getY() + direction).occupiedBy() == opponent.getColor()) {
        return true;
      }
    }
    if (pawn.getX() != 0) {
      if (board.getSquare(pawn.getX() - 1,pawn.getY() + direction).occupiedBy() == opponent.getColor()) {
        return true;
      }
    }
    return false;
  }

  public boolean noAdjacentPawn(Square pawn) {
    int count = 0;
    for (int i = 0; i < 8; i++) {
      if (pawn.getX() != 7) {
        if (board.getSquare(pawn.getX() + 1, i).occupiedBy() == color) count++;
      }
      if (pawn.getX() != 0) {
        if (board.getSquare(pawn.getX() - 1, i).occupiedBy() == color) count++;
      }
    }
    if (count >= 2) return true;
    return false;
  }

  public int forwardness() {
    int forwardness = 0;
    for (Square pawn : getAllPawns()) {
      if (pawn != null) {
        forwardness += forwardnessOfPawn(pawn);
      }
    }
    return forwardness;
  }

  public int forwardnessOfPawn(Square pawn) {
    int forwardness = 0;
    forwardness += 10 * (direction * (pawn.getY() - startingRank));
    if (isPassed(pawn)) {
      forwardness *= 10;
    }
    if (noAdjacentPawn(pawn) && isProtected(pawn)) {
      forwardness *= 5;
    }
    else if (noAdjacentPawn(pawn)) {
      forwardness *= 3;
    }
    if (guarding(pawn)) {
      forwardness *= 3;
    }
//    if (hasOpenFile(pawn)) {
//      forwardness *= 2;
//    }
    if (isProtected(pawn)) {
      forwardness *= 2;
    }
    if (canBeCaptured(pawn) && !isProtected(pawn)) {
      forwardness = 0;
    }
    else if (canBeCaptured(pawn) && isProtected(pawn)) {
      forwardness /= 2;
    }
    return forwardness;
  }

  public int numSemiOpenFiles() {
    int count = 0;
    for (Square pawn : getAllPawns()) {
      if (pawn != null && semiOpenFile(pawn)) count++;
    }
    return count;
  }

  public boolean semiOpenFile(Square pawn) {
    if (color == Color.WHITE) {
      for (int i = pawn.getY(); i < 8; i++) {
        if (board.getSquare(pawn.getX(), i).occupiedBy() != Color.NONE) return false;
      }
      return true;
    }
    else {
      for (int i = pawn.getY(); i >= 0; i--) {
        if (board.getSquare(pawn.getX(), i).occupiedBy() != Color.NONE) return false;
      }
      return true;
    }
  }

  public int numPawnsCanBeCaptured() {
    int count = 0;
    for (Square pawn : getAllPawns()) {
      if (pawn != null && canBeCaptured(pawn)) {
        count++;
      }
    }
    return count;
  }

  public boolean canBeCaptured(Square pawn) {
    int a = (pawn.occupiedBy() == Color.WHITE) ? 1 : -1;
    if (pawn.getX() != 7) {
      if (board.getSquare(pawn.getY() + a, pawn.getX() + 1).occupiedBy() == opponent.getColor()) return true;
    }
    if (pawn.getX() != 0) {
      if (board.getSquare(pawn.getY() + a, pawn.getX() - 1).occupiedBy() == opponent.getColor()) return true;
    }
    return false;
  }

  public boolean hasPassedPawn() {
    Square[] pawns = getAllPawns();
    for (int i = 0; i < getNumAllPawns(); i++) {
      if (isPassed(pawns[i])) {
        return true;
      }
    }
    return false;
  }

  public Square getPassedPawn() {
    Square[] pawns = getAllPawns();
    if (hasPassedPawn()) {
      for (int i = 0; i < getNumAllPawns(); i++) {
        if (isPassed(pawns[i])) {
          return pawns[i];
        }
      }
    }
    return null;
  }


  public int count(Move[] moves) {
    int count = 0;
    for (int i = 0; moves[i] != null; i++) {
      count++;
    }
    return count;
  }

  public void makeMove() {
    int n = new Random().nextInt(count(getAllValidMoves()));
    game.applyMove(getAllValidMoves()[n]);
  }

  public void makeMove(Move move) {
    game.applyMove(move);
  }

  public void makeMove(int i) {
    game.applyMove(getAllValidMoves()[i]);
  }

}

