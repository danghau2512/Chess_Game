import javax.swing.*;
import java.awt.*;

public class ChessTable extends JFrame {

    private final BoardPanel boardPanel;
    private GameSetup currentSetup;

    public ChessTable() {
        super("Chess Game");

        this.boardPanel = new BoardPanel();

        setJMenuBar(createMenuBar());
        add(boardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu optionsMenu = new JMenu("Menu");

        final JMenuItem setupItem = new JMenuItem("Tùy chỉnh");
        setupItem.addActionListener(e -> showGameSetupDialog());
        final JMenuItem quitItem = new JMenuItem("Thoát");
        quitItem.addActionListener(e -> System.exit(0));

        optionsMenu.add(setupItem);
        optionsMenu.add(quitItem);
        menuBar.add(optionsMenu);
        return menuBar;
    }

    private void showGameSetupDialog() {
        GameSetupDialog dialog = new GameSetupDialog(this);
        GameSetup setup = dialog.showDialog();  // block cho đến khi bấm OK/Cancel
        if (setup != null) {
            this.currentSetup = setup;
            System.out.println("White: " + setup.getWhiteType()
                    + " | Black: " + setup.getBlackType()
                    + " | Depth: " + setup.getSearchDepth());

        }
    }
}
