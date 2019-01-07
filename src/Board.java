public class Board {

  private Square[][] board = new Square[8][8];
  private char whiteGap;
  private char blackGap;

  public Board(char whiteGap, char blackGap) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        board[i][j] = new Square(i, j);
        board[i][j].setOcuppier(Color.NONE);
        if (j == 1 && i != whiteGap - 'a') {
          board[i][j].setOcuppier(Color.WHITE);
        } else if (j == 6 && i != blackGap - 'a') {
          board[i][j].setOcuppier(Color.BLACK);
        }
      }
    }
    this.whiteGap = whiteGap;
    this.blackGap = blackGap;
  }

  public Board getCopy() {
    Board copy = new Board(whiteGap, blackGap);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        copy.setSquare(i, j, board[i][j].occupiedBy());
      }
    }
    return copy;
  }

  public void setSquare(int x, int y, Color c) {
    board[x][y].setOcuppier(c);
  }

  public Square getSquare(int x, int y) {
    return board[x][y];
  }

  public void applyMove(Move move) {
    Color c = move.getFrom().occupiedBy();
    move.getFrom().setOcuppier(Color.NONE);
    move.getTo().setOcuppier(c);
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    if (move.isEnPassantCapture()) {
      if (c == Color.WHITE) {
        board[toX][toY - 1].setOcuppier(Color.NONE);
      } else {
        board[toX][toY + 1].setOcuppier(Color.NONE);
      }
    }
  }

  public void unapplyMove(Move move) {
    Color c = move.getTo().occupiedBy();
    move.getFrom().setOcuppier(c);
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    if (move.isEnPassantCapture()) {
      board[toX][toY].setOcuppier(Color.NONE);
      if (c == Color.WHITE) {
        board[toX][toY - 1].setOcuppier(Color.BLACK);
      } else {
        board[toX][toY + 1].setOcuppier(Color.WHITE);
      }
    } else if (move.isCapture()) {
      if (c == Color.WHITE) {
        move.getTo().setOcuppier(Color.BLACK);
      } else {
        move.getTo().setOcuppier(Color.WHITE);
      }
    } else {
      move.getTo().setOcuppier(Color.NONE);
    }
  }

  public void display() {
    System.out.println("  A B C D E F G H");
    for (int row = 7; row >= 0; row--) {
      int x = row + 1;
      System.out.print(x + " ");
      for (int col = 0; col < 8; col++) {
        if (board[col][row].occupiedBy() == Color.WHITE) {
//          System.out.print((char) 9823 + " ");
          System.out.print("W ");
        } else if (board[col][row].occupiedBy() == Color.BLACK) {
//          System.out.print((char) 9817 + " ");
          System.out.print("B ");
        } else {
          System.out.print(". ");
        }
      }
      System.out.println(x + "");
    }
    System.out.println("  A B C D E F G H");
  }

  public String displayString() {
    String result = "";
    result += "  A B C D E F G H\n";
    for (int row = 7; row >= 0; row--) {
      int x = row + 1;
      result += x + " ";
      for (int col = 0; col < 8; col++) {
        if (board[col][row].occupiedBy() == Color.WHITE) {
//          System.out.print((char) 9823 + " ");
          result += "W ";
        } else if (board[col][row].occupiedBy() == Color.BLACK) {
//          System.out.print((char) 9817 + " ");
          result += "B ";
        } else {
          result += ". ";
        }
      }
      result += x + "";
    }
    return result;
  }

}
