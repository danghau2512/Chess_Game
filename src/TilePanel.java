import javax.swing.*;
import java.awt.*;

public class TilePanel extends JPanel {

    private final int row;
    private final int col;
    private final JLabel pieceLabel;

    public TilePanel(int row, int col) {
        this.row = row;
        this.col = col;

        setPreferredSize(new Dimension(80, 80));
        setOpaque(true);
        assignTileColor();

        setLayout(new BorderLayout());
        pieceLabel = new JLabel();
        pieceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pieceLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(pieceLabel, BorderLayout.CENTER);
    }

    private void assignTileColor() {
        boolean isLight = (row + col) % 2 == 0;
        Color light = new Color(0xF0, 0xF3, 0xF7);
        Color dark  = new Color(0x45, 0x5A, 0x73);;
        setBackground(isLight ? light : dark);
    }

      public void setPieceIcon(ImageIcon icon) {
        pieceLabel.setIcon(icon);
        repaint();
    }
    public void clearPiece() {
        setPieceIcon(null);
    }
}
