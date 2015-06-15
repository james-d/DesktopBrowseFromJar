package application;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AppWithHelp extends JFrame {
    
    public AppWithHelp() {
        initUI();
        setSize(250, 120);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void initUI() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Help");
        button.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });
        panel.add(button);
        add(panel);
    }
    
    private void showHelp() {
        Path helpPath = Paths.get(System.getProperty("user.home"), "axiar", "help", "index.html");
        if (! Files.exists(helpPath)) {
            extractHelpFiles();
        }
        try {
            URI helpLocation = helpPath.toUri();
            Desktop.getDesktop().browse(helpLocation);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    private void extractHelpFiles() {
        
        
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/resources/help_file_list.txt")))) {
            
            Files.createDirectories(Paths.get(System.getProperty("user.home"), "axiar", "help"));

            String filename ;
            while ((filename = in.readLine()) != null) {
                extractFile(filename);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    
    private void extractFile(String filename) throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/resources/"+filename);
                OutputStream out = Files.newOutputStream(Paths.get(System.getProperty("user.home"), "axiar", "help", filename))) {
            int data ;
            while ((data = in.read()) != -1) {
                out.write(data);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppWithHelp());
    }

}
