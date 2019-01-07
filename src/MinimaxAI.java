import javafx.util.Pair;
import java.util.Random;

//TODO: Improve heuristic formula, add Monte Carlo Tree Sort
//try to store all moves with the same score in an ArrayList & randomize the move returned for effective Monte Carlo-ing
public class MinimaxAI {

  Player player;
  Random random = new Random();

  public MinimaxAI(Player player) {
    this.player = player;
  }

  public Move minimaxBestMove(int depth) {
    return minimax(depth, player.getColor(), Integer.MIN_VALUE, Integer.MAX_VALUE).getValue();
  }

  public Move randomBestOrSecondBest(int depth, double probBest) {
    if (random.nextDouble() > probBest && player.getAllValidMoves()[0] != null) {
      return secondBestMove(depth);
    }
    else {
      return minimaxBestMove(depth);
    }
  }

  public Move secondBestMove(int depth) {
    Move[] validMoves = player.getAllValidMoves();
    Move secondBestMove = validMoves[1];
    int bestScore = Integer.MIN_VALUE;
    int secondBestScore = Integer.MIN_VALUE;
    for(Move nextMove : validMoves) {
      if (nextMove != null) {
        int score;
        player.getGame().applyMove(nextMove);
        score = minimax(depth - 1, player.getColor(), Integer.MIN_VALUE, Integer.MAX_VALUE)
            .getKey();
        if (score > bestScore)
          bestScore = score;
        if (bestScore >= score && score >= secondBestScore) {
          secondBestScore = score;
          secondBestMove = nextMove;
        }
        player.getGame().unapplyMove();
      }
    }
    return secondBestMove;
  }

  public Pair<Integer, Move> minimax(int depth, Color c, int alpha, int beta) {
    int score;
    int bestScore;
    Color opColor = (c == Color.WHITE) ? Color.BLACK : Color.WHITE;
    Player currentPlayer;
    if (player.getColor() == c) {
      currentPlayer = player;
    } else {
      currentPlayer = player.getOpponent();
    }
    Move[] validMoves = currentPlayer.getAllValidMoves();
    Move bestMove = validMoves[0];
    if (depth == 0 || player.getGame().isFinished()) {
      bestScore = evaluateBoard();
    } else {
      for (Move move : validMoves) {
        if (move != null) {
          player.getGame().applyMove(move);
          score = minimax(depth - 1, opColor, alpha, beta).getKey();
          if (player.getColor() == c) {
            if (score > alpha) {
              alpha = score;
              bestMove = move;
            }
          } else {
            if (score < beta) {
              beta = score;
              bestMove = move;
            }
          }
          player.getGame().unapplyMove();
          if (alpha >= beta) {
            break;
          }
        }
      }
      bestScore = (c == player.getColor()) ? alpha : beta;
    }
    return new Pair<>(bestScore, bestMove);
  }

  public Pair<Integer, Move> minimax(int depth, Color c) {
    Move bestMove = null;
    int bestScore = (c == Color.WHITE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    int currentScore;
    Color opColor = (c == Color.WHITE) ? Color.BLACK : Color.WHITE;
    Player currentPlayer;
    if (player.getColor() == c) {
      currentPlayer = player;
    } else {
      currentPlayer = player.getOpponent();
    }
    Move[] validMoves = currentPlayer.getAllValidMoves();
    if (depth == 0 || player.getGame().isFinished() || validMoves[0] == null) {
      bestScore = evaluateBoard();
    } else {
      for (Move move : validMoves) {
        if (move != null) {
          player.getGame().applyMove(move);
          currentScore = minimax(depth - 1, opColor).getKey();
          if (c == Color.WHITE) {
            if (currentScore > bestScore) {
              bestScore = currentScore;
              bestMove = move;
            }
          } else {
            if (currentScore < bestScore) {
              bestScore = currentScore;
              bestMove = move;
            }
          }
          player.getGame().unapplyMove();
        }
      }
    }
    return new Pair<>(bestScore, bestMove);
  }

  public int evaluateBoard() {
    // TODO: Add pawn chain, !!controls opponents spaces!!, zugzwang, blocking move formulas
    int score = 0;
    if (player.isFinished() && player.getGame().getGameResult() == player.getColor()) {
      return Integer.MAX_VALUE;
    }
    if (player.isFinished() && player.getGame().getGameResult() == player.getOpponent()
        .getColor()) {
      return Integer.MIN_VALUE;
    }
    if (player.isFinished() && player.getGame().getGameResult() == Color.NONE) {
      return 0;
    }
    score += 10000 * player.getNumAllPawns() - 10000 * player.getOpponent().getNumAllPawns();
//    score += 10 * player.numProtectedPawns() - 10 * player.getOpponent().getNumAllPawns();
    score += 10 * player.forwardness() - 10 * player.getOpponent().forwardness();
//  score += -10 * player.numPawnsCanBeCaptured() + 10 * player.getOpponent().numPawnsCanBeCaptured();
//    score += 10 * player.numSemiOpenFiles() - 10 * player.getOpponent().numSemiOpenFiles();
    if (player.hasPassedPawn()) {
      score += 70000;
    }
    if (player.getOpponent().hasPassedPawn()) {
      score -= 70000;
    }
    if (player.hasPassedPawn() && player.getOpponent().hasPassedPawn()) {
      Square passedPawn = player.getPassedPawn();
      Square opPassedPawn = player.getOpponent().getPassedPawn();
      if (player.getColor() == Color.BLACK) {
        if (passedPawn.getY() < 7 - opPassedPawn.getY()) {
          score += 70000;
        } else if (passedPawn.getY() > 7 - opPassedPawn.getY()) {
          score -= 70000;
        } else {
          if (player.getGame().getCurrentPlayer() == Color.BLACK) {
            score += 70000;
          } else {
            score -= 70000;
          }
        }
      } else if (player.getColor() == Color.WHITE) {
        if (7 - passedPawn.getY() < opPassedPawn.getY()) {
          score += 70000;
        } else if (7 - passedPawn.getY() > opPassedPawn.getY()) {
          score -= 70000;
        } else {
          if (player.getGame().getCurrentPlayer() == Color.WHITE) {
            score += 70000;
          } else {
            score -= 70000;
          }
        }
      }
    }
    return score;
  }

}
