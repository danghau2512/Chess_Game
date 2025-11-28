package backend;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {

    public static final int BOARD_SIZE = 8;
    private final TilePanel[][] tiles = new TilePanel[BOARD_SIZE][BOARD_SIZE];

    // biến quản lý lượt chơi
    private TilePanel selectedTile = null;
    private List<TilePanel> possibleMoves = new ArrayList<>();
    private PieceColor currentTurn = PieceColor.WHITE; // trắng đi trước

    public BoardPanel() {
        super(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        // tạo các ô và thêm vào panel
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                TilePanel tile = new TilePanel(row, col, this);
                tiles[row][col] = tile;
                add(tile);
            }
        }

        initStartPosition(); // đặt quân cờ ban đầu
    }

    // THÊM hàm xử lý khi người chơi click vào 1 ô
    public void tileClicked(int row, int col) {
        TilePanel clickedTile = tiles[row][col];

        if (selectedTile == null) {
            // chưa chọn quân thì chọn quân cho hợp lệ
            if (clickedTile.getPiece() != null && clickedTile.getPiece().getColor() == currentTurn) {
                selectTile(clickedTile);
            }
        } else {
            // đã chọn quân rồi
            if (clickedTile == selectedTile) {
                deselectAll(); // click lại thì bỏ chọn
            }
            else if (possibleMoves.contains(clickedTile)) {
                movePiece(selectedTile, clickedTile); // move
                deselectAll();
                switchTurn(); // đổi lượt
            }
            else if (clickedTile.getPiece() != null && clickedTile.getPiece().getColor() == currentTurn) {
                deselectAll();
                selectTile(clickedTile); // chọn quân khác cùng phe
            }
            else {
                deselectAll(); // click sai thì bỏ chọn
            }
        }
    }

    private void selectTile(TilePanel tile) {
        selectedTile = tile;
        selectedTile.setSelected(true);
        highlightPossibleMoves(tile);
    }

    private void deselectAll() {
        if (selectedTile != null) {
            selectedTile.setSelected(false);
            selectedTile = null;
        }
        clearPossibleMoves();
    }

    private void clearPossibleMoves() {
        for (TilePanel tile : possibleMoves) {
            tile.clearHighlight();
        }
        possibleMoves.clear();
    }

    //hiển thị các nước đi hơp lệ
    private void highlightPossibleMoves(TilePanel from) {
        clearPossibleMoves();
        ChessPiece p = from.getPiece();
        if (p == null) return;

        int r = from.getRow();
        int c = from.getCol();

        switch (p.getType()) {
            case PAWN -> addPawnMoves(r, c, p.getColor());
            case ROOK -> addRookMoves(r, c);
            case KNIGHT -> addKnightMoves(r, c);
            case BISHOP -> addBishopMoves(r, c);
            case QUEEN -> { addRookMoves(r, c); addBishopMoves(r, c); }
            case KING -> addKingMoves(r, c);
        }
    }

    //tính nước đi hơp lệ
    private void addPawnMoves(int r, int c, PieceColor color) {
        int dir = color == PieceColor.WHITE ? -1 : 1;
        int startRow = color == PieceColor.WHITE ? 6 : 1;

        // Tiến 1 ô
        if (inBounds(r + dir, c) && tiles[r + dir][c].getPiece() == null) {
            possibleMoves.add(tiles[r + dir][c]);
            tiles[r + dir][c].highlightPossibleMove();

            // Tiến 2 ô từ hàng đầu
            if (r == startRow && inBounds(r + 2*dir, c) && tiles[r + 2*dir][c].getPiece() == null) {
                possibleMoves.add(tiles[r + 2*dir][c]);
                tiles[r + 2*dir][c].highlightPossibleMove();
            }
        }

        // Ăn chéo
        for (int dc : new int[]{-1, 1}) {
            int nr = r + dir, nc = c + dc;
            if (inBounds(nr, nc) && tiles[nr][nc].getPiece() != null &&
                tiles[nr][nc].getPiece().getColor() != color) {
                possibleMoves.add(tiles[nr][nc]);
                tiles[nr][nc].highlightPossibleMove();
            }
        }
    }

    private void addRookMoves(int r, int c) {
        addLineMoves(r, c, 1, 0);
        addLineMoves(r, c, -1, 0);
        addLineMoves(r, c, 0, 1);
        addLineMoves(r, c, 0, -1);
    }

    private void addBishopMoves(int r, int c) {
        addLineMoves(r, c, 1, 1);
        addLineMoves(r, c, 1, -1);
        addLineMoves(r, c, -1, 1);
        addLineMoves(r, c, -1, -1);
    }

    private void addLineMoves(int r, int c, int dr, int dc) {
        int nr = r + dr, nc = c + dc;
        while (inBounds(nr, nc)) {
            TilePanel tile = tiles[nr][nc];
            if (tile.getPiece() == null) {
                possibleMoves.add(tile);
                tile.highlightPossibleMove();
            } else {
                if (tile.getPiece().getColor() != tiles[r][c].getPiece().getColor()) {
                    possibleMoves.add(tile);
                    tile.highlightPossibleMove();
                }
                break;
            }
            nr += dr; nc += dc;
        }
    }

    private void addKnightMoves(int r, int c) {
        int[][] deltas = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
        for (int[] d : deltas) {
            int nr = r + d[0], nc = c + d[1];
            if (inBounds(nr, nc)) {
                TilePanel tile = tiles[nr][nc];
                if (tile.getPiece() == null || tile.getPiece().getColor() != tiles[r][c].getPiece().getColor()) {
                    possibleMoves.add(tile);
                    tile.highlightPossibleMove();
                }
            }
        }
    }

    private void addKingMoves(int r, int c) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (inBounds(nr, nc)) {
                    TilePanel tile = tiles[nr][nc];
                    if (tile.getPiece() == null || tile.getPiece().getColor() != tiles[r][c].getPiece().getColor()) {
                        possibleMoves.add(tile);
                        tile.highlightPossibleMove();
                    }
                }
            }
        }
    }

    private void movePiece(TilePanel from, TilePanel to) {
        to.setPiece(from.getPiece());
        from.setPiece(null);
    }

    private void switchTurn() {
        currentTurn = (currentTurn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        System.out.println("Đến lượt: " + (currentTurn == PieceColor.WHITE ? "Trắng" : "Đen"));
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    // Đặt lại vị trí ban đầu với ChessPiece
    private void initStartPosition() {
        for (int c = 0; c < 8; c++) {
            tiles[6][c].setPiece(new ChessPiece(PieceType.PAWN, PieceColor.WHITE, PieceImages.WP));
            tiles[1][c].setPiece(new ChessPiece(PieceType.PAWN, PieceColor.BLACK, PieceImages.BP));
        }

        tiles[7][0].setPiece(new ChessPiece(PieceType.ROOK, PieceColor.WHITE, PieceImages.WR));
        tiles[7][7].setPiece(new ChessPiece(PieceType.ROOK, PieceColor.WHITE, PieceImages.WR));
        tiles[7][1].setPiece(new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE, PieceImages.WN));
        tiles[7][6].setPiece(new ChessPiece(PieceType.KNIGHT, PieceColor.WHITE, PieceImages.WN));
        tiles[7][2].setPiece(new ChessPiece(PieceType.BISHOP, PieceColor.WHITE, PieceImages.WB));
        tiles[7][5].setPiece(new ChessPiece(PieceType.BISHOP, PieceColor.WHITE, PieceImages.WB));
        tiles[7][3].setPiece(new ChessPiece(PieceType.QUEEN, PieceColor.WHITE, PieceImages.WQ));
        tiles[7][4].setPiece(new ChessPiece(PieceType.KING, PieceColor.WHITE, PieceImages.WK));

        tiles[0][0].setPiece(new ChessPiece(PieceType.ROOK, PieceColor.BLACK, PieceImages.BR));
        tiles[0][7].setPiece(new ChessPiece(PieceType.ROOK, PieceColor.BLACK, PieceImages.BR));
        tiles[0][1].setPiece(new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK, PieceImages.BN));
        tiles[0][6].setPiece(new ChessPiece(PieceType.KNIGHT, PieceColor.BLACK, PieceImages.BN));
        tiles[0][2].setPiece(new ChessPiece(PieceType.BISHOP, PieceColor.BLACK, PieceImages.BB));
        tiles[0][5].setPiece(new ChessPiece(PieceType.BISHOP, PieceColor.BLACK, PieceImages.BB));
        tiles[0][3].setPiece(new ChessPiece(PieceType.QUEEN, PieceColor.BLACK, PieceImages.BQ));
        tiles[0][4].setPiece(new ChessPiece(PieceType.KING, PieceColor.BLACK, PieceImages.BK));
    }
}