import javax.swing.*;
import java.awt.*;

public final class PieceImages {

    private static final int PIECE_SIZE = 64; // kích thước icon

    private PieceImages() {}

    private static ImageIcon load(String name) {
        ImageIcon icon = new ImageIcon("img/" + name);
        Image img = icon.getImage().getScaledInstance(
                PIECE_SIZE, PIECE_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static final ImageIcon WP = load("WP.png");
    public static final ImageIcon WN = load("WN.png");
    public static final ImageIcon WB = load("WB.png");
    public static final ImageIcon WR = load("WR.png");
    public static final ImageIcon WQ = load("WQ.png");
    public static final ImageIcon WK = load("WK.png");

    public static final ImageIcon BP = load("BP.png");
    public static final ImageIcon BN = load("BN.png");
    public static final ImageIcon BB = load("BB.png");
    public static final ImageIcon BR = load("BR.png");
    public static final ImageIcon BQ = load("BQ.png");
    public static final ImageIcon BK = load("BK.png");
}
