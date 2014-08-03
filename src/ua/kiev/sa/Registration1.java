package ua.kiev.sa;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.sql.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Registration1 extends JPanel implements ActionListener {
    ResultSet rs;
//    ConnPool1 cp = MyWallet.cp;
    private Connection conn1=null;
    private String loginForCheck;
    private Matcher loginNameMatcher;
    PreparedStatement pst;
    private char[] value;

    @SuppressWarnings("compatibility:1191852575668179839")


    public Registration1() {
        //CREATING JPANEL
        MyWallet.mainFrame.setMaximumSize(getPreferredSize());
        MyWallet.mainFrame.setLocationRelativeTo(null);
        regpanel = new JPanel();
        regpanel.setLayout(new GridBagLayout());
        DocLoginList docLoginList = new DocLoginList();
        DocPasswordList docPasswordList = new DocPasswordList();
        DocSumList docSumList = new DocSumList();

        //ELEMENTS OF LOGIN
        loginLabel = new JLabel("Login");
        regpanel.add(loginLabel,
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        login = "Enter your login";
        loginText = new JTextField(32);
        loginText.setToolTipText(login);
        loginText.getDocument().addDocumentListener(docLoginList);
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
        passwordText.getDocument().addDocumentListener(docPasswordList);
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
        sexList.setSelectedIndex(0);
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
        accountLabelError = new JLabel();
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
        sumText.getDocument().addDocumentListener(docSumList);
        regpanel.add(sumText,
                new GridBagConstraints(1, 14, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        sumLabelError = new JLabel();
        regpanel.add(sumLabelError,
                new GridBagConstraints(2, 14, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

        //ELEMENTS OF ORGANIZATION
        currencyLabel = new JLabel("Currency");
        regpanel.add(currencyLabel,
                new GridBagConstraints(0, 16, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        currencyBox = new JComboBox(new String[]{"UAH", "RU", "USD", "EUR"});

        //currencyBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        currency = "Enter your currency";
        //currencyText = new JTextField(32);
        //currencyText.setToolTipText(currency);
        regpanel.add(currencyBox,
                new GridBagConstraints(1, 16, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        currencyLabelError = new JLabel();
        regpanel.add(currencyLabelError,
                new GridBagConstraints(2, 16, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        currencyLabelError.setText("");

        //CREATING REGISTRATION BUTTON
        regButton = new JButton("Создать профиль");
        regpanel.add(regButton,
                new GridBagConstraints(1, 17, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));

        regButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    // проверяем поля а корректность введенных данных

                    if (!(checkField())) {
                        Connection conn1 = MyWallet.cp.ds.getConnection();
                        String addingUsers="INSERT INTO 'Users' " +
                                "('Login', 'Password', 'First_name', 'Surname', 'Organization','Sex')" +
                                " Values " +
                                "('"+ loginText.getText() + "', " +
                                "'"+ generateHashCode(passwordText.getPassword()) +"',"+
                                "'"+ surnameText.getText() +"'," +
                                "'"+nameText.getText()+  "', "+
                                "'"+organizationText.getText()+ "', "+
                                "'"+ (String) sexList.getSelectedValue()+"')";
                        if (conn1 == null) {
                            System.out.println("Нет соединения с БД!");
                            System.exit(0);
                        }
                        PreparedStatement pst=conn1.prepareStatement(addingUsers);
//                        st.executeUpdate(addingUsers);
//                        st.close();

                        pst.executeUpdate();
                        pst.close();
                        Statement st1 = conn1.createStatement();
//                        String createTableAccount=;
                        st1.executeUpdate("INSERT INTO 'Counts' ('Account','Balance', 'Currency','id_users')" +
                                " SELECT '" +
                                accountText.getText() + "', "+
                                "'" +sumText.getText() +"', "     +
                                "'" +(String)currencyBox.getSelectedItem()+"',"+
//                                "'" +organizationText.getText()+"')");
                                "id from Users where login='"+loginText.getText()+"' LIMIT 1;");
                        if(conn1!=null) conn1.close();

                        try {
                            //удаляем панель регистрации и добавляем панель входа
                            MyWallet.mainFrame.setContentPane(new AutentificationPanel());
//                            passwordText.getPassword().toString()));
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
                new GridBagConstraints(0, 17, 1, 1, 0, 0, GridBagConstraints.EAST,
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


    public void actionPerformed(ActionEvent e) {
    }
    public int generateHashCode(char[] c) {
        int result = 17;
        for (int i = 0; i <c.length ; i++) {

            result = 37 * result + (c[i]);
        }
        return result;
    }
    // проверка правильности логина
    private boolean checkLoginField() {
        loginNamePattern = Pattern.compile("[0-9A-Za-z]{1,}");
        loginNameMatcher = loginNamePattern.matcher(loginText.getText());
        String l=loginText.getText();
        try {
            Connection conn1 = MyWallet.cp.ds.getConnection();

            PreparedStatement pst = conn1.prepareStatement("SELECT Login FROM Users WHERE Login = '" + l + "'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                loginForCheck = rs.getString(1);
                System.out.println(loginForCheck);
            }
            if (loginForCheck != null) {
                setLoginLabelError(occupiedLogin);
                loginLog = true;
             }  else if (loginText.getText().equals(login) |
                    loginText.getText().equals("")) {
                loginLabelError.setText(CHANGEFIELD);
                loginLog = true;
            } else if (!loginNameMatcher.matches()) {
                loginLabelError.setText(ERRORINSYMBOLS);
                loginLog = true;
            }
            else {
                loginLabelError.setText("");
                loginLog = false;
            }
        } catch (SQLException exc) {
            if(exc.getMessage().equals("[SQLITE_ERROR] SQL error or missing database (no such table: Users)")){
                loginForCheck="";
            }
            if (exc.getMessage().equals("java.lang.NullPointerException")){
                loginLog=false;

            }

        } catch (NullPointerException npe) {
            npe.printStackTrace();

        } finally {
            try {
                loginForCheck=null;
                if (conn1 != null) {
                    pst.close();
                    conn1.close();

                }
            } catch (SQLException e) {
                System.out.println();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return loginLog;
    }

    // проверка логина
        private boolean checkPasswordField() {
            passwordPattern = Pattern.compile("[A-Za-z.!@#$%^&*+()-_+=*/<>?]{2,}");
        String s = String.valueOf(passwordText.getPassword());
        Matcher passwordMatcher =
                passwordPattern.matcher(s);

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
            passwordLog = false;

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


    private boolean checkField() {
        return checkLoginField() &
                checkPasswordField() &
                checkSumField() &
                checkAccountNameField() &
                checkFirstNameField() &
                checkOrganizationField() &
                checkSurnameField();

    }


    private class DocLoginList implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            checkLoginField();
        }

        public void removeUpdate(DocumentEvent e) {
            checkLoginField();
        }

        public void insertUpdate(DocumentEvent e) {
            checkLoginField();
        }

    }

    private class DocPasswordList implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            checkPasswordField();
        }

        public void removeUpdate(DocumentEvent e) {
            checkPasswordField();
        }

        public void insertUpdate(DocumentEvent e) {
            checkPasswordField();
        }

    }

    private class DocSumList implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            checkSumField();
        }

        public void removeUpdate(DocumentEvent e) {
            checkSumField();
        }

        public void insertUpdate(DocumentEvent e) {
            checkSumField();
        }
        public int hashCode() {
            int h = hash;
            if (h == 0 && value.length > 0) {
                char val[] = value;

                for (int i = 0; i < value.length; i++) {
                    h = 31 * h + val[i];
                }
                hash = h;
            }
            return h;
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

    public void setLoginLabelError(String error) {
        this.loginLabelError.setText(error);
    }

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

    private JLabel currencyLabel;
    private String currency;
    private JTextField currencyText;
    private JLabel currencyLabelError;
    private JList currencyList;
    public static JComboBox<String> currencyBox;

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
    private int hash;
//    private final char value[];

    private final String CHANGEFIELD = "Change this field";
    private final String ERRORINSYMBOLS = "This field might consists of numbers in format 0.00";

    boolean p = false;
    private User c;
    private static final String occupiedLogin ="такой логин уже занят";
}
