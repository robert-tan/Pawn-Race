public class Move {

  private Square from;
  private Square to;
  private boolean isCapture;
  private boolean isEnPassantCapture;

  public Move(Square from, Square to, boolean isCapture, boolean isEnPassantCapture) {
    this.from = from;
    this.to = to;
    this.isCapture = isCapture;
    this.isEnPassantCapture = isEnPassantCapture;
  }

  public Square getFrom() {
    return from;
  }

  public Square getTo() {
    return to;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public boolean isEnPassantCapture() {
    return isEnPassantCapture;
  }

  public String getSAN() {
    if (isCapture || isEnPassantCapture) {
      return from.toString() + "x" + to.toString();
    } else {
      return to.toString();
    }
  }
}