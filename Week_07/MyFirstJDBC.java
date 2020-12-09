import java.sql.*;
import java.util.LinkedList;
import java.util.Random;


public class MyFirstJDBC {
    static final String URL = "jdbc:mysql://127.0.0.1:3306/emall00";//"jdbc:mysql://localhost:3306/emall";
    static final String USER = "root";
    static final String PASS = "root";
    private static Connection conn = null;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        //myQuery();
        //insertOne(); //用时128713ms  //用时46751ms
        //insertMulti(); //用时282ms
        //batchInsert();  //用时125515ms
        insertMultiNoTran(); //用时235ms
        //  batchInsertNoTran(); //用时1625ms

    }

    //Query
    private static void myQuery() throws ClassNotFoundException, SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs =stmt.executeQuery("select name,age from users");
        while(rs.next()) {
            System.out.println("Username: " + rs.getString("name") + "\t User age: "+ rs.getInt("age"));
        }
    }

    //insert delete update
/*
*单Value
*/
    private static void insertOne() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            String sql = "INSERT INTO USERS(name, password, age)" +
                    "values(" + "?,?,?)";
            pstm = conn.prepareStatement(sql);
            Random rand = new Random();
            String name = getRandomString(8);
            String pass = getRandomString(8);
            int age = rand.nextInt(60);

            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 10000;i++) {
                pstm.setString(1, name);
                pstm.setString(2, pass);
                pstm.setInt(3, age);
                pstm.execute();
            }
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入完成，用时" + (endTime-startTime) +"ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(pstm!= null) {
                try {
                    pstm.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private static String getRandomString(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length;i++) {
            int number = rand.nextInt(63);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /*
     *多Value
     */
    private static void insertMulti() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            StringBuffer sql = new StringBuffer("INSERT INTO USERS(name, password, age) VALUES");

            Random rand = new Random();
            String name = getRandomString(8);
            String pass = getRandomString(8);
            int age = rand.nextInt(60);
            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 100000;i++) {
                if(count!=0) {
                    sql.append(",");
                }
                count++;
                sql.append("('" + name + "','" + pass + "'," + age + ")");
                if (count == 25000) {
                    pstm = conn.prepareStatement(sql.toString());
                    pstm.execute();
                    count = 0;
                    sql = new StringBuffer("INSERT INTO USERS(name, password, age) VALUES");
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入完成，用时" + (endTime-startTime) +"ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(pstm!= null) {
                try {
                    pstm.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /*
     *batch
     */
    private static void batchInsert() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            String sql = "INSERT INTO USERS(name, password, age)" +
                    "values(" + "?,?,?)";
            pstm = conn.prepareStatement(sql);
            Random rand = new Random();
            String name = getRandomString(8);
            String pass = getRandomString(8);
            int age = rand.nextInt(60);

            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 10000;i++) {
                pstm.setString(1, name);
                pstm.setString(2, pass);
                pstm.setInt(3, age);
                pstm.addBatch();
                count++;
                if(count>=2500) {
                    int[] ids = pstm.executeBatch();
                    pstm.clearBatch();
                    count = 0;
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入完成，用时" + (endTime-startTime) +"ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(pstm!= null) {
                try {
                    pstm.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private static void insertMultiNoTran() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            StringBuffer sql = new StringBuffer("INSERT INTO orders0(user_id, goodLists, total_price, status, deliver_status) VALUES");
            conn.setAutoCommit(false);
            Random rand = new Random();
           // LinkedList<Integer> id = new LinkedList<Integer>();
            LinkedList<Integer> user_id = new LinkedList<Integer>();
            LinkedList<String> goodLists = new LinkedList<String>();
            LinkedList<Integer> total_price = new LinkedList<Integer>();
            LinkedList<Integer> status = new LinkedList<Integer>();
            LinkedList<String> deliver_status = new LinkedList<String>();
           // id.add(0);
            goodLists.add(getRandomString(25));
            deliver_status.add("On route");
            user_id.add(rand.nextInt(100));
            total_price.add(rand.nextInt(10000));
            status.add(rand.nextInt(2));
            for (int i = 1; i <= 20000;i++) {
               // id.add(id.get(i-1)+rand.nextInt(3)+1);
                goodLists.add(getRandomString(25));
                deliver_status.add("On route");
                user_id.add(rand.nextInt(100));
                total_price.add(rand.nextInt(10000));
                status.add(rand.nextInt(2));
            }

            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 20000;i++) {
                if(count!=0) {
                    sql.append(",");
                }
                count++;
                sql.append("(" + user_id.get(i) + ",'" + goodLists.get(i) + "'," + total_price.get(i)
                        + "," + status.get(i) +",'" + deliver_status.get(i) + "')");//user_id, goodLists, total_price, status, deliver_status//  +id.get(i)+","
                if (count == 10000) {
                    pstm = conn.prepareStatement(sql.toString());
                    pstm.execute();
                    conn.commit();
                    count = 0;
                    sql = new StringBuffer("INSERT INTO orders0( user_id, goodLists, total_price, status, deliver_status) VALUES");
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入完成，用时" + (endTime-startTime) +"ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if(pstm!= null) {
                try {
                    pstm.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private static void batchInsertNoTran() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            String sql = "INSERT INTO USERS(name, password, age)" +
                    "values(" + "?,?,?)";
            conn.setAutoCommit(false);
            pstm = conn.prepareStatement(sql);
            Random rand = new Random();
            String name = getRandomString(8);
            String pass = getRandomString(8);
            int age = rand.nextInt(60);

            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 10000; i++) {
                pstm.setString(1, name);
                pstm.setString(2, pass);
                pstm.setInt(3, age);
                pstm.addBatch();
                count++;
                if (count >= 2500) {
                    int[] ids = pstm.executeBatch();
                    pstm.clearBatch();
                    conn.commit();
                    count = 0;
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入完成，用时" + (endTime - startTime) + "ms");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}

