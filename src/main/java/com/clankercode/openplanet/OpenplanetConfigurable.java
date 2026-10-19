package com.clankercode.openplanet;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import javax.swing.*;
import java.awt.*;

public final class OpenplanetConfigurable implements Configurable {
    private JTextField path;
    private JTextArea options;
    public String getDisplayName() { return "Openplanet"; }
    public JComponent createComponent() {
        path = new JTextField(45);
        options = new JTextArea(10, 45);
        options.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JPanel form = new JPanel(new BorderLayout(0, 12));
        JPanel top = new JPanel(new BorderLayout(0, 5));
        JLabel label = new JLabel("Server executable (absolute path; blank uses openplanet-lsp on PATH)");
        label.setLabelFor(path);
        top.add(label, BorderLayout.NORTH); top.add(path, BorderLayout.CENTER);
        form.add(top, BorderLayout.NORTH);
        JPanel middle = new JPanel(new BorderLayout(0, 5));
        JLabel jsonLabel = new JLabel("Explicit initialization options (JSON object, snake_case keys)");
        jsonLabel.setLabelFor(options);
        middle.add(jsonLabel, BorderLayout.NORTH); middle.add(new JScrollPane(options), BorderLayout.CENTER);
        form.add(middle, BorderLayout.CENTER);
        form.add(new JLabel("Defaults inherit server configuration. Restart Openplanet in LSP Consoles after applying."), BorderLayout.SOUTH);
        JPanel outer = new JPanel(new BorderLayout()); outer.add(form, BorderLayout.NORTH);
        reset(); return outer;
    }
    public boolean isModified() {
        var state = OpenplanetSettings.getInstance();
        return path != null && (!path.getText().trim().equals(state.serverPath) || !options.getText().equals(state.initializationOptions));
    }
    public void apply() throws ConfigurationException {
        try { ServerConfiguration.validateExecutable(path.getText()); ServerConfiguration.initializationOptions(options.getText()); }
        catch (IllegalArgumentException e) { throw new ConfigurationException(e.getMessage()); }
        var state = OpenplanetSettings.getInstance(); state.serverPath = path.getText().trim(); state.initializationOptions = options.getText();
    }
    public void reset() { if (path != null) { var state = OpenplanetSettings.getInstance(); path.setText(state.serverPath); options.setText(state.initializationOptions); } }
    public void disposeUIResources() { path = null; options = null; }
}
