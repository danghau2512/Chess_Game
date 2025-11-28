package backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TilePanel extends JPanel {

    private final int row;
    private final int col;
    private final JLabel pieceLabel;
    private ChessPiece piece;
    private boolean isSelected = false;

    // Thêm tham chiếu đến BoardPanel để gọi lại khi click
    public TilePanel(int row, int col, BoardPanel board) {
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

        // THÊM: Xử lý click chuột
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                board.tileClicked(row, col); // Gọi lại BoardPanel
            }
        });
    }

    private void assignTileColor() {
        boolean isLight = (row + col) % 2 == 0;
        setBackground(isLight ? new Color(240, 243, 247) : new Color(69, 90, 115));
    }

    // thay đồi dùng ChessPiece thay vì ImageIcon
    public void setPiece(ChessPiece piece) {
        this.piece = piece;
        pieceLabel.setIcon(piece != null ? piece.getIcon() : null);
        repaint();
    }

    public ChessPiece getPiece() {
        return piece;
    }

    // hiệu ứng chọn ô
    public void setSelected(boolean selected) {
        if (selected != isSelected) {
            isSelected = selected;
            setBorder(selected ? BorderFactory.createLineBorder(Color.yellow, 4) : null);
        }
    }

    // hiệu ứng nước đi hợp lệ
    public void highlightPossibleMove() {
        setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
    }

    public void clearHighlight() {
        setBorder(null);
        setSelected(false);
    }

    // Getter để BoardPanel biết tọa độ
    public int getRow() { return row; }
    public int getCol() { return col; }
}