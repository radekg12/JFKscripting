import javax.script.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PanelZeSkryptami extends JPanel {
    private JTextField arg1TextField, arg2TextField;
    private JButton addJSFunButton, addGroovyButton;
    private JLabel arg1Label, arg2Label, scriptJSLabel, scriptGroovyLabel;
    private JTextArea scriptJSTextArea, scriptGroovyTextArea;
    private MyFrame frame;
    private GridBagConstraints c;

    public PanelZeSkryptami(MyFrame frame) {
        setLayout(new GridBagLayout());
        this.frame = frame;
        arg1Label = new JLabel("arg 1: ");
        arg2Label = new JLabel("arg 2: ");
        scriptJSLabel = new JLabel("JavaScript script: ");
        scriptGroovyLabel = new JLabel("Groovy script: ");
        arg1TextField = new JTextField();
        arg2TextField = new JTextField();
        addJSFunButton = new JButton("Add JavaScript");
        addGroovyButton = new JButton("Add Groovy");
        scriptJSTextArea = new JTextArea();
        scriptGroovyTextArea = new JTextArea();
        scriptJSTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        scriptGroovyTextArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 11));
        scriptJSTextArea.setLineWrap(true);
        scriptGroovyTextArea.setLineWrap(true);

        addJSFunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scriptJSString = scriptJSTextArea.getText();
                addScript("nashorn", scriptJSString);
            }
        });


        addGroovyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scriptGroovyString = scriptGroovyTextArea.getText();
                addScript("groovy", scriptGroovyString);
            }
        });
        initGUI();
    }

    public void addScript(String engineName, String script) {
        ScriptEngineManager menager = new ScriptEngineManager();
        ScriptEngine engine = menager.getEngineByName(engineName);
        try {
            engine.eval(script);
        } catch (ScriptException e1) {
            e1.printStackTrace();
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
        addToPanel(arg1Label, 0, 0, 1, 1, 1, 1);
        addToPanel(arg2Label, 2, 0, 1, 1, 1, 1);
        addToPanel(arg1TextField, 0, 1, 1, 1, 1, 1);
        addToPanel(arg2TextField, 2, 1, 1, 1, 1, 1);
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
