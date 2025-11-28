package backend;

//File: ChessPiece.java
import javax.swing.ImageIcon;

public class ChessPiece {
 private final PieceType type;
 private final PieceColor color;
 private final ImageIcon icon;

 public ChessPiece(PieceType type, PieceColor color, ImageIcon icon) {
     this.type = type;
     this.color = color;
     this.icon = icon;
 }

 public PieceType getType() { return type; }
 public PieceColor getColor() { return color; }
 public ImageIcon getIcon() { return icon; }
}