package ua.kiev.sa;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OperationToDB implements Runnable {
    private  static Thread t1;


        private Connection conn=null;
        User user;
        String login;
    ArrayList<Operation> operations=new ArrayList<Operation>();
    public OperationToDB(User c, ArrayList<Operation>al) {
            user=c;
            operations = al;
            login=user.getLogin();
        t1 = new Thread(Thread.currentThread().getThreadGroup(), this,"Thread adding to DB" );
        t1.start();
            try {
                conn=MyWallet.cp.ds.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }

    @Override
    public void run() {
        for (int i = 0; i < operations.size(); i++) {
            System.out.println(i+" --"+System.currentTimeMillis());

            String addingOperationToDB="insert into '"+login+"Operations' VALUES ('"+
                    operations.get(i).getD().toString()+ "','" +operations.get(i).getCat()+"','"+operations.get(i).getSubcat()+
                    "','"+ operations.get(i).getAccount()+"','"+ operations.get(i).getF()+"')";
          try {
                PreparedStatement pst=conn.prepareStatement(addingOperationToDB);
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println(i+" --"+System.currentTimeMillis());

        }

    }


}
