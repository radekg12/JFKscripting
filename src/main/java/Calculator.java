import javax.script.Invocable;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Calculator extends JPanel {
    private Integer oldValue, newValue;
    private JTextArea screen;
    private GridBagConstraints c;
    private String operation = "";
    private HashMap<String, Invocable> options = new HashMap<>();

    public Calculator() {
        super(new GridBagLayout());
        screen = new JTextArea();
        screen.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        screen.setEditable(false);
        oldValue = 0;
        newValue = 0;
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(screen, c);
        c.gridwidth = 1;
        c.weighty = 1;
        createButton1_9();
        crateNumberButton(1, 4, 0);

        JButton button = new JButton("CE");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldValue = 0;
                newValue = 0;
                screen.setText("");
            }
        });
        c.gridx = 3;
        c.gridy = 1;
        add(button, c);

        button = new JButton("+/-");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newValue *= (-1);
                screen.setText("" + newValue);
                operation = "+/-";

            }
        });
        c.gridx = 0;
        c.gridy = 4;
        add(button, c);

        addOperationButton(new JButton(), "-", 3, 2, 1);
        addOperationButton(new JButton(), "+", 3, 3, 1);

        button = new JButton("=");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (options.containsKey(operation)) {
                    executeMyFunction(operation);
                    return;
                } else
                    switch (operation) {
                        case "+":
                            newValue = add(oldValue, newValue);
                            break;
                        case "-":
                            newValue = sub(oldValue, newValue);
                            break;
                        default:
                            break;
                    }
                screen.setText("" + newValue);
            }
        });
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 4;
        add(button, c);
        c.gridwidth = 1;
    }

    private void executeMyFunction(String operation) {
        Invocable invocable = options.get(operation);
        Object result = null;
        try {
            result = invocable.invokeFunction(operation.split("[(]")[0], oldValue, newValue);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (result instanceof Double) newValue = (int) ((double) result);
        if (result instanceof Integer) newValue = (int) result;
        screen.setText("" + newValue);
    }


    public void createButton1_9() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                crateNumberButton(j, 3 - i, i * 3 + j + 1);
    }

    public void crateNumberButton(int x, int y, int i) {
        JButton button = new JButton("" + i);
        button.setBackground(Color.GRAY.darker());
        int finalI = i;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newValue == 0 && oldValue == 0) newValue = finalI;
                else newValue = Integer.parseInt(String.valueOf(newValue) + finalI);
                screen.setText(String.valueOf(newValue));
            }
        });
        c.gridx = x;
        c.gridy = y;
        add(button, c);
    }

    public void addOperationButton(JButton button, String name, int gridx, int gridy, int gridwidth) {
        button.setText(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldValue = newValue;
                newValue = 0;
                screen.setText("");
                operation = name;
            }
        });
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        add(button, c);
    }

    public void addCustomOperationButton(String name, Invocable invocable) {
        if (options.containsKey(name)) {
            int i = 0;
            String tmp;
            do {
                i++;
                tmp = name + "(" + i + ")";

            } while (options.containsKey(tmp));
            name = tmp;
        }
        String finalName = name;
        JButton button = new JButton(name);
        button.setBackground(Color.BLUE.darker());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldValue = newValue;
                newValue = 0;
                screen.setText("");
                operation = finalName;
            }
        });
        c.gridx = options.size() % 4;
        c.gridy = 5 + options.size() / 4;
        c.gridwidth = 1;
        SwingUtilities.invokeLater(() -> add(button, c));
        revalidate();
        options.put(name, invocable);
    }

    public int add(int x, int y) {
        return x + y;
    }

    public int sub(int x, int y) {
        return x - y;
    }

}
