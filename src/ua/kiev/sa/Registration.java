package ua.kiev.sa;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.SQLException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Registration extends JPanel implements ActionListener {
    private ConnPool1 cp = null;
    private Statement st = null;
    private Connection conn = null;
    ResultSet rs = null;

    @SuppressWarnings("compatibility:1191852575668179839")


    public Registration(ConnPool1 connPool) {
        this();
        cp = connPool;
        try{
            conn = cp.getConnection();
            st = conn.createStatement();
        }   catch (SQLException exc){
            exc.printStackTrace();
        }

    }

    public Registration() {
        //CREATING JPANEL
//        Dimension d = new Dimension(435, 370);
        MyWallet.mainFrame.setMaximumSize(getPreferredSize());
        MyWallet.mainFrame.setLocationRelativeTo(null);
        regpanel = new JPanel();
        regpanel.setLayout(new GridBagLayout());
        DocList docList = new DocList();
        //ELEMENTS OF LOGIN
        loginLabel = new JLabel("Login");
        regpanel.add(loginLabel,
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        login = "Enter your login";
        loginText = new JTextField(32);
        loginText.setToolTipText(login);
        loginText.getDocument().addDocumentListener(docList);
        regpanel.add(loginText,
                new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        loginLabelError = new JLabel();
        regpanel.add(loginLabelError,
                new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));


        //ELEMENTS OF PASSWORD
        passwordLabel = new JLabel("Password");
        regpanel.add(passwordLabel,
                new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        passwordText = new JPasswordField(32);
        passwordText.setEchoChar('*');
        passwordText.setToolTipText("Enter your password");
        passwordText.getDocument().addDocumentListener(docList);
        regpanel.add(passwordText,
                new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        passwordLabelError = new JLabel();
        regpanel.add(passwordLabelError,
                new GridBagConstraints(2, 2, 1, 1, 0, 0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));


        //ELEMENTS OF NAME
        nameLabel = new JLabel("Firstname");
        regpanel.add(nameLabel,
                new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(10, 10, 10, 10), 0, 0));
        name = "Enter your Firstname";
        nameText = new JTextField(32);
        nameText.setToolTipText(name);
        regpanel.add(nameText,
                new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        nameLabelError = new JLabel();
        regpanel.add(nameLabelError,
                new GridBagConstraints(2, 4, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));


        //ELEMENTS OF SURNAME
        surnameLabel = new JLabel("Surname");
        regpanel.add(surnameLabel,
                new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        surname = "Enter your Surname";
        surnameText = new JTextField(32);
        surnameText.setToolTipText(surname);
        regpanel.add(surnameText,
                new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        surnameLabelError = new JLabel();
        regpanel.add(surnameLabelError,
                new GridBagConstraints(2, 6, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));


        //ELEMENTS OF ORGANIZATION
        organizationLabel = new JLabel("Organization");
        regpanel.add(organizationLabel,
                new GridBagConstraints(0, 8, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        organization = "Enter your organization name";
        organizationText = new JTextField(32);
        organizationText.setToolTipText(organization);
        regpanel.add(organizationText,
                new GridBagConstraints(1, 8, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        organizationLabelError = new JLabel();
        regpanel.add(organizationLabelError,
                new GridBagConstraints(2, 8, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        organizationLabelError.setText("");

        //ELEMENTS OF SEX
        sexLabel = new JLabel("Sex");
        sexLabel.setToolTipText("Enter your sex");
        regpanel.add(sexLabel,
                new GridBagConstraints(0, 10, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        sexList = new JList(new String[]{"male", "female"});
        sexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        regpanel.add(sexList,
                new GridBagConstraints(1, 10, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        sexLabelError = new JLabel();
        regpanel.add(sexLabelError,
                new GridBagConstraints(2, 10, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        //First account name
        accountLabel = new JLabel("Name of main account");
        regpanel.add(accountLabel,
                new GridBagConstraints(0, 12, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));

        accountName = "Enter your accounts name";
        accountText = new JTextField(32);
        accountText.setToolTipText(accountName);
        regpanel.add(accountText,
                new GridBagConstraints(1, 12, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        accountLabelError=new JLabel();
        regpanel.add(accountLabelError,
                new GridBagConstraints(2, 12, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));



        //Starting balance of account
        sumLabel = new JLabel("Count");
        regpanel.add(sumLabel,
                new GridBagConstraints(0, 14, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        sum = "Enter amount of money";
        sumText = new JTextField(32);
        sumText.setToolTipText(sum);
        sumText.getDocument().addDocumentListener(docList);
        regpanel.add(sumText,
                new GridBagConstraints(1, 14, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        sumLabelError = new JLabel();
        regpanel.add(sumLabelError,
                new GridBagConstraints(2, 14, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));


        //CREATING REGISTRATION BUTTON
        regButton = new JButton("Создать профиль");
        regpanel.add(regButton,
                new GridBagConstraints(1, 15, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));

        regButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // проверяем поля а корректность введенных данных
                    checkField();
                    if (!(loginLog & passwordLog & nameLog & surnameLog &
                            organizationLog)) {
                        Account a=new Account(accountText.getText(),Float.valueOf(sumText.getText()), "");

                        ArrayList<Account> accs=new ArrayList<Account>();
                        accs.add(a);

                        c =
                                new User(loginText.getText(), "",
                                        surnameText.getText(), nameText.getText(),
                                        organizationText.getText(),accs);
                        try {
                            File f = new File("Accounts.file");

                            //проверяем наличие файла для записи
                            if (!f.exists()) {
                                f.createNewFile();
                            }
                            fileWrite(f, c, true,
                                    new File("Accounts.ser"));

                            //удаляем панель регистрации и добавляем панель входа

                            MyWallet.mainFrame.setContentPane(new AutentificationPanel(loginText.getText(),
                                    passwordText.getPassword().toString()));
                            MyWallet.mainFrame.validate();
                            MyWallet.mainFrame.repaint();
                            MyWallet.mainFrame.pack();
                            MyWallet.mainFrame.setLocationRelativeTo(null);

                            MyWallet.mainFrame.setTitle("Войдите");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }


        });

        //CREATING RETURN BUTTON
        returnButton = new JButton("Назад");
        regpanel.add(returnButton,
                new GridBagConstraints(0, 15, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(10, 10, 10, 10), 0, 0));
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    //удаляем панель регистрации и добавляем панель входа
                    MyWallet.mainFrame.setContentPane(new AutentificationPanel());
                    Dimension d = new Dimension(350, 110);
                    MyWallet.mainFrame.setMinimumSize(d);
                    MyWallet.mainFrame.validate();
                    MyWallet.mainFrame.repaint();
                    MyWallet.mainFrame.pack();
                    MyWallet.mainFrame.setLocationRelativeTo(null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        MyWallet.mainFrame.pack();
        this.add(regpanel);
    }

    public void fileWrite(File f, User s, boolean b, File fser) {
        //                                создаем файлрайтер для дозаписи
        try {
            FileWriter fw;
            fw = new FileWriter(f, b);
            BufferedWriter bw = new BufferedWriter(fw);
            //создаем объект для его записи в файл
            //записываем данные в файл
            //переносим данные из буфера
            bw.write(s.writingToFile() + "\n");
            // сериализируем файл
            bw.flush();
            FileOutputStream fos = new FileOutputStream(fser);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // закрываем файловые потоки и потоки сериализации
            oos.writeObject(f);
            bw.close();
            oos.flush();
            oos.close();
        } catch (IOException e) {
        }
    }

    public void actionPerformed(ActionEvent e) {
    }

    // проверка правильности логина
    private boolean checkLoginField() {
        try {
            conn = cp.getConnection();
            st = conn.createStatement();
            loginNamePattern = Pattern.compile("[0-9A-Za-z]{1,}");
            Matcher loginNameMatcher = loginNamePattern.matcher(loginText.getText());
            if (st.execute("SELECT * FROM Users WHERE Login=" + loginText.getText())) {
                loginLabelError.setText("такой логин уже занят");
                loginLog = true;
            } else if (loginText.getText().equals(login) |
                    loginText.getText().equals("")) {
                loginLabelError.setText(CHANGEFIELD);
                loginLog = true;
            } else if (!loginNameMatcher.matches()) {
                loginLabelError.setText(ERRORINSYMBOLS);
                loginLog = true;
            } else {
                loginLabelError.setText("");
                loginLog = false;
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            cp.close();
        }
        return loginLog;
    }

    // проверка логина
    private boolean checkPasswordField() {
        passwordPattern = Pattern.compile("[.!@#$%^&*+()-_+=*/<>?]{2,}");
        String s = String.valueOf(passwordText.getPassword());
        Matcher passwordMatcher =
                passwordPattern.matcher(s);
        try {
            if (passwordText.getPassword().equals("")) {
                passwordLabelError.setText(CHANGEFIELD);
                passwordLog = true;
            } else if (!passwordMatcher.matches() | passwordText.getPassword().length < 2 |
                    passwordText.getPassword().equals("")) {
                passwordLabelError.setText("Enter correct password");
                passwordLog = true;
            } else {
                passwordLabelError.setText("");
                passwordLog = false;
            }
        }

        finally {
            cp.close();
            passwordLog= false;
        }
        return passwordLog;
    }

    //     проверка имени
    private boolean checkFirstNameField() {
        if (nameText.getText().equals(name)) {
            nameLabelError.setText(CHANGEFIELD);
            nameLog = true;
        } else {
            nameLabelError.setText("");
            nameLog = false;
        }
        return nameLog;
    }
    //     проверка фамилии
    private boolean checkSurnameField() {
        if (surnameText.getText().equals(surname)) {
            surnameLabelError.setText(CHANGEFIELD);
            surnameLog = true;
        } else {
            surnameLabelError.setText("");
            surnameLog = false;
        }
    return surnameLog;
    }


    //     проверка названия организации
    private boolean checkOrganizationField() {
        if (organizationText.getText().equals(organization)) {
            organizationLabelError.setText(CHANGEFIELD);
            organizationLog = true;
        } else {
            organizationLabelError.setText("");
            organizationLog = false;
        }
        return organizationLog;
    }

    //     проверка имени счета
    private boolean checkAccountNameField() {
        if (accountText.getText().equals(accountName)) {
           accountLabelError.setText(CHANGEFIELD);
            accountLog = true;
        } else {
            accountLabelError.setText("");
            accountLog = false;
        }
        return accountLog;
    }

    //     проверка суммы счета
    private boolean checkSumField() {
        sumPattern = Pattern.compile("\\-?\\d+(\\.\\d{0,2})?");
        Matcher sumMatcher =
                sumPattern.matcher(sumText.getText());
        if (sumText.getText().equals("")) {
            sumLabelError.setText(CHANGEFIELD);
            sumLog = true;
        } else if (!sumMatcher.matches()) {
            sumLabelError.setText(ERRORINSYMBOLS);
            sumLog = true;
        } else {
            sumLabelError.setText("");
            sumLog = false;
        }
        return sumLog;
    }


    private boolean checkField(){
      return checkLoginField()&
        checkPasswordField()&
        checkSumField()&
        checkAccountNameField()&
        checkFirstNameField()&
        checkOrganizationField()&
        checkSurnameField();

    }
//        MyWallet.mainFrame.pack();
//        regpanel.revalidate();
//        regpanel.repaint();
//        MyWallet.mainFrame.setLocationRelativeTo(null);
//        Dimension d =MyWallet.mainFrame.getSize();
//
//        return (loginLog & passwordLog & surnameLog & nameLog &
//                organizationLog);
//
//    }


    private class DocList implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            checkField();
        }

        public void removeUpdate(DocumentEvent e) {
            checkField();
        }

        public void insertUpdate(DocumentEvent e) {
            checkField();
        }

    }

    Pattern accountNamePattern;
    Matcher accountNameMatcher;
    Pattern passwordPattern;
    Pattern passwordMatcher;
    private static final long serialVersionUID = 1L;
    private JPanel regpanel;
    private JButton regButton;
    private JButton returnButton;

    private String login;
    private JLabel loginLabel;
    private JTextField loginText;
    private JLabel loginLabelError;
    private Pattern loginNamePattern;
    boolean b;


    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JLabel passwordLabelError;

    private JLabel nameLabel;
    private String name;
    private JTextField nameText;
    private JLabel nameLabelError;

    private JLabel surnameLabel;
    private String surname;
    private JTextField surnameText;
    private JLabel surnameLabelError;

    private JLabel organizationLabel;
    private JTextField organizationText;
    private JLabel organizationLabelError;
    private String organization;

    private JLabel sexLabel;
    private JList sexList;
    private JLabel sexLabelError;

    private JLabel accountLabel;
    private String accountName;
    private JTextField accountText;
    private JLabel accountLabelError;

    private JLabel sumLabel;
    private String sum;
    private JTextField sumText;
    private JLabel sumLabelError;
    private Pattern sumPattern;

    private boolean loginLog;
    private boolean passwordLog;
    private boolean nameLog;
    private boolean surnameLog;
    private boolean organizationLog;
    private boolean sumLog;
    private boolean accountLog;


    private final String CHANGEFIELD = "Change this field";
    private final String ERRORINSYMBOLS = "This field can consists of letters a-z and numbers";

    boolean p = false;
    private User c;

}
