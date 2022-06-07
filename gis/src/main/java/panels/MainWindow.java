package panels;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    JPanel mainPanel;
    LeftPanel leftPanel;
    CentralPanel centralPanel;
    RightPanel rightPanel;
    static MainWindow instance = new MainWindow();

    private MainWindow(){
        init();
    }

    public JPanel createLeftPanel(){
        leftPanel = new LeftPanel();
        JPanel container = new JPanel();
        container.add(leftPanel);
        return container;
    }

    public JPanel createCentralPanel(){
        centralPanel = new CentralPanel();
        JPanel container = new JPanel();
        container.add(centralPanel);
        return container;
    }

    public JPanel createRightPanel(){
        rightPanel = new RightPanel();
        JPanel container = new JPanel();
        container.add(rightPanel);
        return container;
    }

    public void init(){
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1000,1000);

        JPanel panel = new JPanel(new GridLayout(1,3));
        panel.add(createLeftPanel());
        panel.add(createCentralPanel());
        panel.add(createRightPanel());

        mainPanel = panel;
        add(mainPanel);
    }

    public static MainWindow getInstance(){
        return instance;
    }

}
