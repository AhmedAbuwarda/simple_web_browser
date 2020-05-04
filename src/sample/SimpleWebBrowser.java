package sample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.net.URL;


public class SimpleWebBrowser extends JPanel {

    /**
     * The main routine simply opens a window that shows a SimpleWebBrowser panel.
     */
    public static void main(String[] args) {

        JFrame window = new JFrame("Ahmed Web Browser");
        SimpleWebBrowser content = new SimpleWebBrowser();
        window.setContentPane(content);
        window.setSize(600, 500);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screenSize.width - window.getWidth()) / 2,
                (screenSize.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    // The pane in which documents are displayed.
    private final JEditorPane editPane;

    // an input box where the user enters the URL of a document
    private final JTextField locationInput;


    /**
     * when the user clicks on a link in the document.
     */
    private class LinkListener implements HyperlinkListener {
        public void hyperlinkUpdate(HyperlinkEvent evt) {
            if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                loadURL(evt.getURL());
            }
        }
    }


    /**
     * Defines a listener that loads a new page when the user
     * clicks the "Go" button or presses return in the location
     * input box.
     */
    private class GoListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            URL url;
            try {
                String location = locationInput.getText().trim();
                if (location.length() == 0)
                    throw new Exception();
                if (!location.contains("://"))
                    location = "http://" + location;
                url = new URL(location);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(SimpleWebBrowser.this,
                        "The Location input box does not\nccontain a legal URL.");
                return;
            }
            loadURL(url);
            locationInput.selectAll();
            locationInput.requestFocus();
        }
    }


    /**
     * Construct a panel that contains a JEditorPane in a JScrollPane,
     * with a tool bar that has a Location input box and a Go button.
     */
    public SimpleWebBrowser() {

        setBackground(Color.BLACK);
        setLayout(new BorderLayout(1, 1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        editPane = new JEditorPane();
        editPane.setEditable(false);
        editPane.addHyperlinkListener(new LinkListener());
        add(new JScrollPane(editPane), BorderLayout.CENTER);

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);
        ActionListener goListener = new GoListener();
        locationInput = new JTextField("www.google.com", 40);
        locationInput.addActionListener(goListener);
        JButton goButton = new JButton(" Search ");
        goButton.addActionListener(goListener);
        toolbar.add(new JLabel(" Location: "));
        toolbar.add(locationInput);
        toolbar.addSeparator(new Dimension(5, 0));
        toolbar.add(goButton);

    }


    /**
     * Loads the document at the specified URL into the edit pane.
     */
    private void loadURL(URL url) {
        try {
            editPane.setPage(url);
        } catch (Exception e) {
            editPane.setContentType("text/plain");
            editPane.setText("Error, the requested document was not found!\n" + e);
        }
    }

}
