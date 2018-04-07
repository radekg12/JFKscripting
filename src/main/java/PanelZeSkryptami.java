import javax.script.*;
import javax.swing.*;
import java.awt.*;

public class PanelZeSkryptami extends JPanel {
    private JButton addJSFunButton, addGroovyButton;
    private JLabel scriptJSLabel, scriptGroovyLabel;
    private JTextArea scriptJSTextArea, scriptGroovyTextArea;
    private MyFrame frame;
    private GridBagConstraints c;

    public PanelZeSkryptami(MyFrame frame) {
        setLayout(new GridBagLayout());
        this.frame = frame;
        scriptJSLabel = new JLabel("JavaScript script: ");
        scriptGroovyLabel = new JLabel("Groovy script: ");
        addJSFunButton = new JButton("Add JavaScript");
        addGroovyButton = new JButton("Add Groovy");
        scriptJSTextArea = new JTextArea();
        scriptGroovyTextArea = new JTextArea();
        scriptJSTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        scriptGroovyTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        scriptJSTextArea.setLineWrap(true);
        scriptGroovyTextArea.setLineWrap(true);

        addJSFunButton.addActionListener(e -> {
            String scriptJSString = scriptJSTextArea.getText();
            addScript("nashorn", scriptJSString);
        });


        addGroovyButton.addActionListener(e -> {
            String scriptGroovyString = scriptGroovyTextArea.getText();
            addScript("groovy", scriptGroovyString);
        });
        initGUI();
    }

    public void addScript(String engineName, String script) {
        ScriptEngineManager menager = new ScriptEngineManager();
        ScriptEngine engine = menager.getEngineByName(engineName);
        try {
            engine.eval(script);
        } catch (ScriptException e1) {
            JOptionPane.showMessageDialog(null, "Nieprawidłowa treść skryptu!!!\n Spróbuj ponownie");
        }
        String funName = "";
        if (engineName.equals("nashorn"))
            funName = engine.getBindings(ScriptContext.ENGINE_SCOPE).keySet().iterator().next();
        if (engineName.equals("groovy")) funName = script.replace("def ", "").split("[ ]*[(]")[0];
        Invocable invocable = (Invocable) engine;
        frame.getCalculator().addCustomOperationButton(funName, invocable);

    }

    private void initGUI() {
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;
        addToPanel(scriptJSLabel, 0, 2, 1, 1, 1, 1);
        addToPanel(scriptGroovyLabel, 2, 2, 1, 1, 1, 1);
        addToPanel(scriptJSTextArea, 0, 3, 1, 1, 1, 4);
        addToPanel(scriptGroovyTextArea, 2, 3, 1, 1, 1, 4);
        addToPanel(addJSFunButton, 0, 4, 1, 1, 1, 1);
        addToPanel(addGroovyButton, 2, 4, 1, 1, 1, 1);
    }

    public void addToPanel(Component component, int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.weightx = weightx;
        c.weighty = weighty;
        add(component, c);
    }

}
