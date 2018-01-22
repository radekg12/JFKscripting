import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private Calculator calculator;
    private PanelZeSkryptami panel;

    public MyFrame() {
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 400));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        panel = new PanelZeSkryptami(this);
        calculator = new Calculator();
        initGUI();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyFrame());
    }

    private void initGUI() {
        SwingUtilities.invokeLater(()->{
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(15, 15, 15, 15);
            c.fill = GridBagConstraints.BOTH;
            c.gridwidth = 2;
            c.gridheight = 1;
            c.weightx = 1;
            c.weighty = 1;
            c.gridx = 0;
            c.gridy = 0;
            add(calculator, c);
            c.gridwidth = 1;
            c.weightx = 1;
            c.gridx = 2;
            add(panel, c);
        });
    }


    public Calculator getCalculator() {
        return calculator;
    }

    public PanelZeSkryptami getPanel() {
        return panel;
    }
}
