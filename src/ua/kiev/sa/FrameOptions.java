package ua.kiev.sa;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FrameOptions extends JFrame {
    private JFrame f;
    private Dimension screenSize;
    private Dimension frameSize;
    private Toolkit t;
    private Image image;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuAbout = new JMenu();
    private JMenuItem menuAboutProgramm = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    Toolkit tool;

    public void centeredFrame(JFrame frame, String name) {
        f = frame;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        f.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle(name);
        f.setVisible(true);
        t = Toolkit.getDefaultToolkit();
        image = t.getImage("e://Download//cop.jpg ");
        f.setIconImage(image);

    }


    public FrameOptions() {
        super();
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void AboutProgramm_ActionPerformed(ActionEvent e) {
        JFrame jf = new AboutWindow();
    }
    //Размещение окна по средине фрейма

    private void setPosition() {
        screenSize = tool.getScreenSize();
        frameSize = this.getPreferredSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    public void frameParameters(JFrame frame, int i, boolean b, String string,
                                Image image, Component c,
                                LayoutManager layout) {
        frame.setDefaultCloseOperation(i);
        frame.setVisible(b);
        //выбор названия окна
        frame.setTitle(string);
        //установка иконки
        frame.setIconImage(image);
        //устанавливаем окно по средине экрана
        frame.setLocationRelativeTo(c);
        //устанавливаем компоновщик
        frame.setLayout(layout);
    }
    private void setMenu(){

        this.setJMenuBar(menuBar);

        menuAbout.setText("About");
        menuAboutProgramm.setText("About MyWallet");
        menuFile.setText("File");
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                fileExit_ActionPerformed(ae);
            }
        });
        menuAboutProgramm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutProgramm_ActionPerformed(e);
            }
        });
        menuFile.add(menuFileExit);
        menuAbout.add(menuAboutProgramm);
        menuBar.add(menuFile);
        menuBar.add(menuAbout);


    }

}


