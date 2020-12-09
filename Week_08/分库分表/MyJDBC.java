import java.sql.*;
import java.util.LinkedList;
import java.util.Random;

public class MyFirstJDBC {
    static final String URL = "jdbc:mysql://127.0.0.1:3307/emall";//"jdbc:mysql://localhost:3306/emall";
    static final String USER = "root";
    static final String PASS = "123456";
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
	
    public static Connection getConnection() {
        return conn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        //myQuery();
        insertMultiNoTran(); //用时235ms

    }

    //Query
    private static void myQuery() throws ClassNotFoundException, SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs =stmt.executeQuery("select name,age from users");
        while(rs.next()) {
            System.out.println("Username: " + rs.getString("name") + "\t User age: "+ rs.getInt("age"));
        }
    }

    private static void insertMultiNoTran() {
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            StringBuffer sql = new StringBuffer("INSERT INTO orders(user_id, goodLists, total_price, status, deliver_status) VALUES");
            conn.setAutoCommit(false);
            Random rand = new Random();
            LinkedList<Integer> user_id = new LinkedList<Integer>();
            LinkedList<String> goodLists = new LinkedList<String>();
            LinkedList<Integer> total_price = new LinkedList<Integer>();
            LinkedList<Integer> status = new LinkedList<Integer>();
            LinkedList<String> deliver_status = new LinkedList<String>();

            goodLists.add(getRandomString(25));
            deliver_status.add("On route");
            user_id.add(rand.nextInt(100));
            total_price.add(rand.nextInt(10000));
            status.add(rand.nextInt(2));
            for (int i = 1; i <= 20000;i++) {
                goodLists.add(getRandomString(25));
                deliver_status.add("On route");
                user_id.add(rand.nextInt(100));
                total_price.add(rand.nextInt(10000));
                status.add(rand.nextInt(2));
            }

            long startTime = System.currentTimeMillis();
            int count = 0;
            System.out.println("开始插入>>>>>>");
            for (int i = 0; i < 2000;i++) {
                if(count!=0) {
                    sql.append(",");
                }
                count++;
                sql.append("(" + user_id.get(i) + ",'" + goodLists.get(i) + "'," + total_price.get(i)
                        + "," + status.get(i) +",'" + deliver_status.get(i) + "')");//user_id, goodLists, total_price, status, deliver_status
                if (count == 1000) {
                    pstm = conn.prepareStatement(sql.toString());
                    pstm.execute();
                    conn.commit();
                    count = 0;
                    sql = new StringBuffer("INSERT INTO orders( user_id, goodLists, total_price, status, deliver_status) VALUES");
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
}

