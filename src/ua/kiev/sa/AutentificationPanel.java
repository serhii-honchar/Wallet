package ua.kiev.sa;

import org.jcp.xml.dsig.internal.dom.ApacheNodeSetData;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AutentificationPanel extends JPanel {
    private Statement st = null;
    private Connection conn = null;
    ResultSet rs = null;


    //конструктор в который передается параметры авторизации после заполнения регистрационной формы
    public AutentificationPanel(String login, String pass) {

        this();
        this.loginTextField.setText(login);
        this.passwordField.setText(pass);
        repaint();
        revalidate();
        validate();

    }


    // констуркто по-умолчанию
    public AutentificationPanel() {
        //создаем панель
        autWin = new JPanel();
        //устанавливаем компоновщик панели
        autWin.setLayout(new GridBagLayout());
        //создаем метку ЛОГИН, размещаем ее на панели
        loginLabel = new JLabel("Login");
        autWin.add(loginLabel,
                new GridBagConstraints(0, 1, 2, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 5, 3), 0, 0));

        //создаем текстовое поля ввода логина и размещаем его на панели
        loginTextField = new JTextField(32);
        loginTextField.setToolTipText("Введите логин");
        autWin.add(loginTextField,
                new GridBagConstraints(3, 1, 2, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 5, 0), 0, 0));

        //создаем метку ПАРОЛЬ, размещаем ее на панели
        passwordLabel = new JLabel("Password");
        autWin.add(passwordLabel,
                new GridBagConstraints(0, 3, 2, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 5, 3), 0, 0));

        //создаем поле ввода пароля и размещаем на панели, устанавливая маску-символ
        passwordField = new JPasswordField(32);
        passwordField.setToolTipText("Введите пароль");
        passwordField.setEchoChar('*');
        autWin.add(passwordField,
                new GridBagConstraints(3, 3, 2, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 5, 3), 0, 0));
        // добавляем слушатель клавиши Ентер для входа при активных полях Логин и Пароль
        KeyHandler enter = new KeyHandler();
        autWin.addKeyListener(enter);
        autWin.requestFocusInWindow();
        passwordField.addKeyListener(enter);
        loginTextField.addKeyListener(enter);
        //создаем кнопку ВОЙТИ, размещаем ее на панели и добавляем обработчик
        enterButton = new JButton("Войти");
        autWin.add(enterButton,
                new GridBagConstraints(4, 5, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        //создаем кнопку "Зарегистрироваться", размещаем ее на панели и добавляем обработчик
        registrationButton = new JButton("Зарегистрироваться");
        registrationButton.setFocusPainted(true);
        autWin.add(registrationButton,
                new GridBagConstraints(3, 5, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        add(autWin);

        // добавляем слушатель событий на кнопку ВОЙТИ
        EntrarList entrar = new EntrarList();
        enterButton.addActionListener((ActionListener) entrar);
        // обработка событий при нажатий РЕГИСТРАЦИЯ
        registrationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // удаляем панель ввода
                    MyWallet.mainFrame.getContentPane().removeAll();
                    revalidate();
                    repaint();
                    //добавляем панель регистрации и сжиаем ее
                    MyWallet.mainFrame.setContentPane(new Registration1());
                    MyWallet.mainFrame.setTitle("Введите данные");
                    MyWallet.mainFrame.pack();
                    MyWallet.mainFrame.validate();
                    MyWallet.mainFrame.setVisible(true);
                    MyWallet.mainFrame.setLocationRelativeTo(null);

                } catch (Exception e1) {
                    e1.printStackTrace();

                }

            }
        });
        repaint();

        //добавляем панель на фрейм и сжимаем его
        validate();
        repaint();
        revalidate();

    }

    // класс реализующий проверку данных аутентификации
    private class EntrarList implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            checking();
            if (autorizationChecked) {
                entering();
            }

        }
    }

    private class KeyHandler implements KeyListener {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

                checking();
                if (autorizationChecked) {
                    entering();
                }
            }
        }


        public void keyTyped(KeyEvent e) {

        }


        public void keyReleased(KeyEvent e) {

        }
    }

    public void checking() {
        try {
            conn=MyWallet.cp.ds.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * From Users where " +
                    "login='" + loginTextField.getText() + "' and " +
                    "Password='" + generateHashCode(passwordField.getPassword()) + "';");
            if (rs.next() != false) {
                autorizationChecked = true;
                autWin.removeAll();
                //флаг верности введенных данных
                p = true;
                Statement statement = conn.createStatement();
                ResultSet resultSetCount=statement.executeQuery("SELECT Users.Login,Users.First_name,Users.Surname," +
                        "Users.Organization,Users.Sex," +
                        "Counts.Account, Counts.Balance, Counts.Currency " +
                        "FROM Users, Counts " +
                        "where Counts.id_users=(Select users.id from Users where Users.login='"+loginTextField.getText()+
                        "' and Users.login='"+ loginTextField.getText()+"');");

                ArrayList<Account> a = new ArrayList<Account>();

                while(resultSetCount.next()){
                String acc=resultSetCount.getString(6);
                    float balance = resultSetCount.getFloat(7);
                    String currency=resultSetCount.getString(8);
                    Account account=new Account(acc,balance,currency);
                    a.add(account);
                }


                u = new User(rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), a);
                System.out.println(a.get(0).toStringText());
                System.out.println(""+rs.getString(2)+"|"+ rs.getString(4)+"|"+ rs.getString(5)+"|"+rs.getString(6)+"|"+ rs.getString(7));
                if (!p) {
                    errorDialog("Неправильное имя или пароль", "Ошибка");
                }
                user = u;
            } else  {
                autorizationChecked=false;
                errorDialog("Неправильное имя или пароль", "Ошибка");
            }

        } catch (SQLException ecx) {
            ecx.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }


    void entering() {
        if (autorizationChecked) {
            try {
                MyWallet.mainFrame.getContentPane().removeAll();
                MyWallet.mainFrame.setContentPane(new ManePanel(user));
                MyWallet.mainFrame.setTitle("Кошелек");
                MyWallet.mainFrame.pack();
                MyWallet.mainFrame.validate();
                MyWallet.mainFrame.setVisible(true);
                MyWallet.mainFrame.setLocationRelativeTo(null);

            } catch (Exception f) {
                f.printStackTrace();
            }
        }
//            вывод сообщения об ошибке
        if (!(autorizationChecked)) {
            errorDialog("Неправильное имя или пароль", "Ошибка");
        }
    }


    public static void errorDialog(String str1, String str2) {
        JOptionPane.showMessageDialog(new JPanel(), str1, str2,
                JOptionPane.ERROR_MESSAGE);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setText(String str, String pass) {
        loginTextField.setText(str);
        passwordField.setText(pass);
    }

private JPanel autWin;
private JLabel loginLabel = null;
private JTextField loginTextField = new JTextField();
private JLabel passwordLabel;
private JPasswordField passwordField = new JPasswordField();
private JButton enterButton;
private JButton registrationButton;
private String[] users;
private ArrayList<String[]> us = new ArrayList<String[]>();
private User u;
boolean p = false;
private BufferedReader br1;
private FileInputStream fis1;
private ObjectInputStream ois1;
User user;


private static JMenuBar menuBar = new JMenuBar();
private static JMenu menuFile = new JMenu();
private static JMenuItem menuAbout = new JMenu();
private static JMenuItem menuAboutProgramm = new JMenuItem();
private static JMenuItem menuFileExit = new JMenuItem();
private static JMenuItem menuSettings = new JMenu();
private static JMenuItem menuSetSettings = new JMenuItem();
boolean autorizationChecked = false;

    public JMenuBar addMenu() {

        menuAbout.setText("About");
        menuAboutProgramm.setText("About MyWallet");
        menuSettings.setText("Settings");
        menuSetSettings.setText("Settings");

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
        menuSettings.add(menuSetSettings);

        menuBar.add(menuFile);
        menuBar.add(menuSettings);
        menuBar.add(menuAbout);
        return menuBar;
    }

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void AboutProgramm_ActionPerformed(ActionEvent e) {
        JFrame jf = new AboutWindow();
    }
    public int generateHashCode(char[] c) {
        int result = 17;
        for (int i = 0; i < c.length; i++) {

            result = 37 * result + (c[i]);
        }
        return result;
    }

}
