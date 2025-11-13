import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public static final int BOARD_SIZE = 8;
    private final TilePanel[][] tiles = new TilePanel[BOARD_SIZE][BOARD_SIZE];

    public BoardPanel() {
        super(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                TilePanel tile = new TilePanel(row, col);
                tiles[row][col] = tile;
                add(tile);
            }
        }

        initStartPosition();
    }

    private void initStartPosition() {
                for (int col = 0; col < BOARD_SIZE; col++) {
            tiles[6][col].setPieceIcon(PieceImages.WP); // hàng 7
            tiles[1][col].setPieceIcon(PieceImages.BP); // hàng 2
        }

        // Quân trắng hàng cuối (row 7)
        tiles[7][0].setPieceIcon(PieceImages.WR);
        tiles[7][7].setPieceIcon(PieceImages.WR);
        tiles[7][1].setPieceIcon(PieceImages.WN);
        tiles[7][6].setPieceIcon(PieceImages.WN);
        tiles[7][2].setPieceIcon(PieceImages.WB);
        tiles[7][5].setPieceIcon(PieceImages.WB);
        tiles[7][3].setPieceIcon(PieceImages.WQ);
        tiles[7][4].setPieceIcon(PieceImages.WK);

        // Quân đen hàng đầu (row 0)
        tiles[0][0].setPieceIcon(PieceImages.BR);
        tiles[0][7].setPieceIcon(PieceImages.BR);
        tiles[0][1].setPieceIcon(PieceImages.BN);
        tiles[0][6].setPieceIcon(PieceImages.BN);
        tiles[0][2].setPieceIcon(PieceImages.BB);
        tiles[0][5].setPieceIcon(PieceImages.BB);
        tiles[0][3].setPieceIcon(PieceImages.BQ);
        tiles[0][4].setPieceIcon(PieceImages.BK);
    }
}
