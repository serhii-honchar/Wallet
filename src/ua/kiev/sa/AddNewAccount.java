package ua.kiev.sa;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class AddNewAccount extends JDialog {

    public AddNewAccount(User user) {
        c = user;
        addNewAccountFrame = new JDialog();

        addNewAccountFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addNewAccountFrame.setModal(true);
        addNewAccountFrame.add(addNewAccountPanel());
        addNewAccountFrame.setTitle("Введите новый аккаунт");
        addNewAccountFrame.setLayout(new GridBagLayout());
        Dimension d = new Dimension(200, 300);
        addNewAccountFrame.setMinimumSize(d);
        addNewAccountFrame.pack();
        addNewAccountFrame.validate();
        addNewAccountFrame.setLocationRelativeTo(null);
        addNewAccountFrame.setVisible(true);
        addNewAccountFrame.setLocationRelativeTo(null);
    }

    public JPanel addNewAccountPanel() {
        addNewAccountPanel = new JPanel();
        addNewAccountPanel.setLayout(new GridBagLayout());

        JLabel newAccountNameLabel = new JLabel("Название аккаунта");
        addNewAccountPanel.add(newAccountNameLabel,
                new GridBagConstraints(0, 1, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        JLabel newAccountSumLabel = new JLabel("Начальная сумма");
        addNewAccountPanel.add(newAccountSumLabel,
                new GridBagConstraints(0, 3, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        JLabel newAccountCurrencyLabel = new JLabel("Валюта счета");
        addNewAccountPanel.add(newAccountCurrencyLabel,
                new GridBagConstraints(0, 5, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));

        newAccountNameText = new JTextField(30);
        newAccountNameText.setToolTipText("Введите название нового счета");
        addNewAccountPanel.add(newAccountNameText,
                new GridBagConstraints(0, 2, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        newAccountSumText = new JTextField(30);
        newAccountSumText.setToolTipText("Введите начальную сумму на новом счете");
        addNewAccountPanel.add(newAccountSumText,
                new GridBagConstraints(0, 4, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        newAccountCurrencyBox=new JComboBox(new String[]{"UAH", "RU", "USD", "EUR"});
        addNewAccountPanel.add(newAccountCurrencyBox,
                new GridBagConstraints(0, 6, 6, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        JButton newAccCancel = new JButton("Отмена");
        addNewAccountPanel.add(newAccCancel,
                new GridBagConstraints(3, 7, 1, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        newAccCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewAccountFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                addNewAccountFrame.dispose();
            }

        });
        JButton newAccOk = new JButton("ОК");
        addNewAccountPanel.add(newAccOk,
                new GridBagConstraints(5, 7, 1, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));

        newAccOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.getAcc().add(new Account(newAccountNameText.getText().trim(),
                        Float.parseFloat(newAccountSumText.getText().trim()),
                        newAccountSumText.getText().trim()));
                try {
                    Connection conn=MyWallet.cp.ds.getConnection();
                    String addingNewAccount="Insert Into Counts(Account, Balance, Currency,id_users)" +
                            "select '"+newAccountNameText.getText()+"','"+newAccountSumText.getText()+"', '"+
                            (String)newAccountCurrencyBox.getSelectedItem()+"', Users.id FROM Users where Users.Login='"+c.getLogin()+"' ;";

                    String addingNewAccount1="INSERT INTO Counts('Account','Balance', 'Currency','id_users') " +
                            "SELECT '"+newAccountNameText.getText()+"','"+newAccountSumText.getText()+"','"+
                            (String)newAccountCurrencyBox.getSelectedItem()+"',Users.id from users where Users.Login="+c.getLogin()+";";
//
                    System.out.println(addingNewAccount);
                    System.out.println(addingNewAccount1);
                    PreparedStatement st=conn.prepareStatement(addingNewAccount1);
//                  st.executeUpdate();
                    st.close();
                    conn.close();
                    ManePanel.accountsTableModel.addRow(new Object[]{newAccountNameText.getText(),newAccountSumText.getText(), newAccountCurrencyBox.getSelectedItem() });
//                    ManePanel.setAccountsTable();
                } catch (SQLException e1) {
                    System.out.println("___________________________________++++++++++++++++++++++++++++++++++");
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                addNewAccountFrame.dispose();
            }
        });
        addNewAccountPanel.validate();
        addNewAccountPanel.repaint();
        return addNewAccountPanel;

    }
    private User c;
    private JPanel addNewAccountPanel;
    private JDialog addNewAccountFrame;
    private JComboBox<String> newAccountCurrencyBox;
    private JTextField newAccountNameText;
    private JTextField newAccountSumText;


}
