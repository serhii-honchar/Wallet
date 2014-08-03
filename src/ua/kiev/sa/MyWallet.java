package ua.kiev.sa;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class MyWallet extends JFrame {


    public MyWallet() {
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Авторизируйтесь..");
        cp=new ConnPool1();
        AutentificationPanel autentificationPanel = new AutentificationPanel();
        mainFrame.setContentPane(autentificationPanel);
        mainFrame.pack();
        Dimension d = new Dimension(350, 110);
        mainFrame.setMinimumSize(d);
        mainFrame.validate();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
   }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MyWallet();
    }

    public static JFrame mainFrame;
    public static ConnPool1 cp;

}
