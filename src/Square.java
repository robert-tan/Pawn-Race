public class Square {

  private int x;
  private int y;
  private Color c;

  public Square(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Color occupiedBy() {
    return c;
  }

  public void setOcuppier(Color color) {
    c = color;
  }

  public String toString() {
    char a = (char) x;
    a += 'a';
    int b = y + 1;
    return a + "" + b + "";
  }
}