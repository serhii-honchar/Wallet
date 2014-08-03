package ua.kiev.sa;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Serg
 * Date: 22.06.14
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class ConnPool1 {



        private static final int MAX_ACTIVE = 100;
        private static final int MAX_WAIT = 100;
        private  Connection conn = null;
        public static DataSource ds = null;

    int i;
    int j;
        public ConnPool1() {

            if (ds == null) {
                try {
                    File f= new File("WalletInfo.db");
                    if(!f.exists()){
                        f.createNewFile();
                    }
                    //Адаптер для JDBC-драйвера. Хранит параметры подключения к БД
                    DriverAdapterCPDS pcds = new DriverAdapterCPDS();
                    pcds.setDriver("org.sqlite.JDBC");
                    pcds.setUrl("jdbc:sqlite:WalletInfo.sqlite3");

                    SharedPoolDataSource tds = new SharedPoolDataSource();
                    tds.setConnectionPoolDataSource(pcds);

                    tds.setMaxTotal(MAX_ACTIVE);

                    tds.setMaxConnLifetimeMillis(MAX_WAIT);

                    ds = tds;
                    iniatializeDB(ds);
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                catch (FileNotFoundException exception){
                    exception.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }


        public Connection openConnection() throws SQLException {
            if (conn == null) conn = ds.getConnection();
            return conn;

        }

        public static int getActiveConnection() {
            return ((SharedPoolDataSource)ds).getNumActive();
        }

        /**
         * Если соединение открыто, то возвращает соединение с DB
         public Connection getConnection() throws SQLException {
         */
        public Connection getConnection() throws SQLException {

            return openConnection();
    }

    public void close() {
        try {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }
    catch (SQLException e) {
        e.printStackTrace();
    }

}
    private void iniatializeDB(DataSource ds) {
        try {
            conn = ds.getConnection();
//            st = conn.createStatement();
//            String createTableUsers="CREATE TABLE IF NOT EXISTS 'Users'('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,'Login' TEXT NOT NULL UNIQUE, 'Password' TEXT NOT NULL,'First_name' TEXT,'Surname' TEXT,'Organization' TEXT, 'Sex' TEXT);";

            String createTableUsers="CREATE TABLE IF NOT EXISTS Users\n" +
                    "(\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, \n" +
                    "Login VARCHAR(32) NOT NULL UNIQUE, \n" +
                    "Password VARCHAR(32) NOT NULL,\n" +
                    "First_name VARCHAR(32),\n" +
                    "Surname VARCHAR(32) ,\n" +
                    "Organization VARCHAR(32), \n" +
                    "Sex CHAR);" ;
            PreparedStatement pst=conn.prepareStatement(createTableUsers);
//            st.execute(createTableUsers);
            pst.execute();
            pst.close();
            conn.close();
            System.out.println(conn);
            conn=ds.getConnection();
            Statement st=conn.createStatement();
            String createTableCount="CREATE TABLE IF NOT EXISTS 'Counts'" +
                    "('id_counts' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "'Account' TEXT ," +
                    "'Balance' double, " +
                    "'Currency' TEXT," +
                    "'id_users' integer, " +
                    "foreign key (id_users) references Users(id));";
            st.execute(createTableCount);
            ResultSet rs = st.executeQuery("select * from Users");
            while (rs.next()){
                rs.isFirst();
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(("Ошибка: " +e.getMessage()));
//            System.out.println("111111111111111111");
//            e.getStackTrace();
        } finally {
            try{
                conn.close(); }
            catch(Exception e) {
                System.out.println("++++++++++++++++++++++++++++++++");

                System.out.println(e.getStackTrace());
            }
}

              initCategory();
              initSubCategory();
    }

    private void initCategory() {
        try {

            conn=MyWallet.cp.ds.getConnection();

            String creatingCategoty="CREATE TABLE IF NOT EXISTS 'categories'" +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE ON CONFLICT IGNORE, " +
                    "'category' text UNIQUE ON CONFLICT IGNORE)";
            PreparedStatement pst=conn.prepareStatement(creatingCategoty);
            pst.execute();
            String query="Select count(*) from 'categories'";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                 i=Integer.parseInt((String)rs.getString(1));
            }

            if(i==0){
            Statement sql=conn.createStatement();
                System.out.println("Начало"+System.currentTimeMillis());
            sql.addBatch("INSERT INTO categories VALUES ('1','Категории');");
            sql.addBatch("INSERT INTO categories VALUES ('2','Алкоголь');");
            sql.addBatch("INSERT INTO categories VALUES ('3','Гигиена');");
            sql.addBatch("INSERT INTO categories VALUES ('4','Группа');");
            sql.addBatch("INSERT INTO categories VALUES('5','Доход');");
            sql.addBatch("INSERT INTO categories VALUES('6','Еда');");
            sql.addBatch("INSERT INTO categories VALUES('7','Доходы');");
            sql.addBatch("INSERT INTO categories VALUES('8','Здоровье');");
            sql.addBatch("INSERT INTO categories VALUES('9','Канцтовары');");
            sql.addBatch("INSERT INTO categories VALUES('10','Клубы');");
            sql.addBatch("INSERT INTO categories VALUES('11','Комната');");
            sql.addBatch("INSERT INTO categories VALUES('12','Купоны');");
            sql.addBatch("INSERT INTO categories VALUES('13','Напитки');");
            sql.addBatch("INSERT INTO categories VALUES('14','Одежда');");
            sql.addBatch("INSERT INTO categories VALUES('15','Подарили');");
            sql.addBatch("INSERT INTO categories VALUES('16','Подарок');");
            sql.addBatch("INSERT INTO categories VALUES('17','Посуда');");
            sql.addBatch("INSERT INTO categories VALUES('18','Работа');");
            sql.addBatch("INSERT INTO categories VALUES('19','Разное');");
            sql.addBatch("INSERT INTO categories VALUES('20','Ремонт');");
            sql.addBatch("INSERT INTO categories VALUES('21','Связь');");
            sql.addBatch("INSERT INTO categories VALUES('22','Спорт');");
            sql.addBatch("INSERT INTO categories VALUES('23','Техника');");
            sql.addBatch("INSERT INTO categories VALUES('24','Транспорт');");
            sql.addBatch("INSERT INTO categories VALUES('25','Услуги');");
                System.out.println("Добавляются категории по умолчанию");
            sql.executeBatch();
                System.out.println("Конец"+System.currentTimeMillis());
            }


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private void initSubCategory() {
        try {

            conn=MyWallet.cp.ds.getConnection();

            String creatingCategoty="CREATE TABLE IF NOT EXISTS 'subcategories'" +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "'subcategories' text UNIQUE ON CONFLICT IGNORE," +
                    "'id_category' integer," +
                    "foreign key (id_category) references categories(id));)";
            PreparedStatement pst=conn.prepareStatement(creatingCategoty);
            pst.execute();

            String query="Select count(*) from 'subcategories'";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(query);

            while(rs.next()){
                j=Integer.parseInt((String)rs.getString(1));
            }

            if(j==0){
            Statement sql=conn.createStatement();

                System.out.println("Добавление подкатегорий по-умолчанию");
                System.out.println("Начало"+System.currentTimeMillis());
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Алкоголь',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гигиена',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Группа',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Доход',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Еда',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Доходы',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Здоровье',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Канцтовары',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Клубы',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Комната',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Купоны',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Одежда',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Напитки',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Подарили',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Подарок',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Посуда',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Работа',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Разное',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Ремонт',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Связь',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Спорт',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Техника',1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Транспорт', 1);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Услуги', 1);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Вино',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Водка',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Коктейль',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Коньяк',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пиво',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сидр',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Слабоалкоголка',2);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шампанское',2);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Антиперспирант',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гель для душа',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Зубная паста',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Лезвие',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мочалка',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мыло',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Порошок',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Салфетки',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Туалетная бумага',3);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шампунь',3);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Виньетка',4);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('День рождения',4);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кондиционер',4);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Общак',4);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пьянка',4);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Выиграш',5);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Отдали',5);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Стиралка',5);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Холодильник',5);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Арахис',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бастурма',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Батончик',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Беляши',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бигмак',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бисквит',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бутерброд',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Вафли',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Выпечка',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гамбургер',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Горох',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Горчица',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гренки',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гречка',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Гриль голени',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Грудинка гриль',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Жевачка',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Икра мойвы',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Имбирь',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Картофель',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кекс',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кетчуп',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Колбаса', 6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кофе', 6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Куры гриль',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Лаваш',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Люля кебаб',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Майонез',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Макароны',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Маккофе',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Масло',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Масло подсолнечное',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мед',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мидии',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Морковка по-корейски',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мороженное',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мясная тарелка',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Мясо',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Обед',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Обеды',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Оливки',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Орешки',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Отбивная',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пампушки',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Паштет',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пельмени',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Печенье',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пироженное',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пирожок',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Приправа',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Рис',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Рулет',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Рыба сушенная',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Салат',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сардины',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сахар',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Скумбрия',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сметана',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сникерс',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Соления',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сосиска в тесте',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сосиски',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Спагетти',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Суп',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сухарики',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сухофрукты',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Суши',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сыр',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сыр панне',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сырковая масса',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Терамису',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Тунец',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Хлеб',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Хот-дог',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Хрен',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чебурек',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чипсы',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шаурма',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шашлык',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шинка',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шоколад',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шпроты',6);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Яйца',6);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Зарплата',7);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Компенсация',7);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Нокспрей',8);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Стоматология',8);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Тетради',9);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Открытка',9);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сорри бабушка',10);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бионика',10);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Дакота',10);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Саксон',10);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Общак',11);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Уборка',11);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чай',12);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Вода',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Вода питьевая',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Вода сладкая',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Йогурт',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Квас',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кефир',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кока-кола',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кофе',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Минеральная',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Молоко',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Молоко топленное',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пепси',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Ряжанка',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сок',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Спрайт',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Фанта',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чай',13);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чай жидкий',13);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Запонки и зажим',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Значок',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Кошелек',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Носки',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сумка',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Тапочки',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Туфли',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шапка',14);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шорты',14);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('День рождение',15);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сестра',15);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Сидей',16);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Степанов',16);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Хральцова',16);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Заварник',17);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Благотворительность',18);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Интернет',18);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Общак',18);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Емкости',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Ключи',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Пакет',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Потерял',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Распределить',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Стаканчики',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Чаевые',19);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Шарики',19);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Краска',20);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Часы',20);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('МТС',21);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Жираф',21);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Интертелеком',21);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Лайф',21);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Утел',21);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Билеты футбол',22);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Бильярд',22);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Батарейки',23);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Планшет',23);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Телефон',23);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Такси',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Автобус',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Возврат билета',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Маршрутка',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Метро',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Поезд',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Попутка',24);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Троллейбус',24);");

            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Карточка',25);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Почта',25);");
            sql.addBatch("INSERT INTO subcategories (subcategories, id_category) VALUES ('Стрижка',25);");

            sql.executeBatch();
                System.out.println("Конец"+System.currentTimeMillis());
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


}

