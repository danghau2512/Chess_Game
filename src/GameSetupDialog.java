import javax.swing.*;
import java.awt.*;

public class GameSetupDialog extends JDialog {

    private final JRadioButton whiteHuman;
    private final JRadioButton whiteComputer;
    private final JRadioButton blackHuman;
    private final JRadioButton blackComputer;
    private final JSpinner depthSpinner;

    private GameSetup result = null;

    public GameSetupDialog(Frame owner) {
        super(owner, "Tùy chỉnh", true); // modal
                setLayout(new BorderLayout());

        // ==== khối chọn White Player ====
        JPanel whitePanel = new JPanel(new GridLayout(1, 2));
        whitePanel.setBorder(BorderFactory.createTitledBorder("Quân trắng"));
        whiteHuman = new JRadioButton("Người", true);
        whiteComputer = new JRadioButton("Máy");
        ButtonGroup whiteGroup = new ButtonGroup();
        whiteGroup.add(whiteHuman);
        whiteGroup.add(whiteComputer);
        whitePanel.add(whiteHuman);
        whitePanel.add(whiteComputer);

        // ==== khối chọn Black Player ====
        JPanel blackPanel = new JPanel(new GridLayout(1, 2));
        blackPanel.setBorder(BorderFactory.createTitledBorder("Quân đen"));
        blackHuman = new JRadioButton("Người");
        blackComputer = new JRadioButton("Máy", true);
        ButtonGroup blackGroup = new ButtonGroup();
        blackGroup.add(blackHuman);
        blackGroup.add(blackComputer);
        blackPanel.add(blackHuman);
        blackPanel.add(blackComputer);

        // ==== khối chọn Depth ====
        JPanel depthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        depthPanel.setBorder(BorderFactory.createTitledBorder("Chọn độ sâu"));
        depthSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        Dimension d = depthSpinner.getPreferredSize();
        d.width = 100;
        depthSpinner.setPreferredSize(d);
        depthPanel.add(new JLabel("Độ sâu"));
        depthPanel.add(depthSpinner);


        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            PlayerType white = whiteHuman.isSelected() ? PlayerType.HUMAN : PlayerType.COMPUTER;
            PlayerType black = blackHuman.isSelected() ? PlayerType.HUMAN : PlayerType.COMPUTER;
            int depth = (Integer) depthSpinner.getValue();
            result = new GameSetup(white, black, depth);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            result = null;
            dispose();
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);

        // ==== layout ====
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(whitePanel);
        center.add(blackPanel);
        center.add(depthPanel);

        add(center, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setSize(350, 350);
        setLocationRelativeTo(owner);
    }

    public GameSetup showDialog() {
        setVisible(true);
        return result;
    }
}
