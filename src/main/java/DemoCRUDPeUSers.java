import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DemoCRUDPeUSers {

    public static void main(String[] args) throws SQLException {

        DemoCRUDPeUSers obiect = new DemoCRUDPeUSers();
        User u = new User();
        Scanner sc = new Scanner(System.in);
        boolean chooseMenu = false;

        while (!chooseMenu) {
            obiect.menu();
            int option = sc.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Username: ");
                    sc.nextLine();
                    String usernamekb = sc.nextLine();
                    System.out.println("Password: ");
                    String passwordkb = sc.nextLine();
                    u.setUsername(usernamekb);
                    u.setPassword(passwordkb);
                    boolean isAdded = obiect.insert(u);
                    if(isAdded)
                        System.out.println("User added successfully.");
                    else
                        System.out.println("Error while adding user.");
                    break;
                }
                case 2: {
                    List<User> result = obiect.readAllUsers();
                    System.out.println(result);
                    break;
                }
                case 3: {
                    System.out.println("Update Username: ");
                    sc.nextLine();
                    String usernamekb = sc.nextLine();
                    System.out.println("Update Password: ");
                    String passwordkb = sc.nextLine();
                    System.out.println("For which id? :");
                    int idkb = sc.nextInt();
                    u.setUsername(usernamekb);
                    u.setPassword(passwordkb);
                    u.setId(idkb);
                    boolean ex = obiect.update(u);
                    if(ex)
                    System.out.println("User updated successfully.");
                    else
                        System.out.println("Error while updating user.");
                    break;
                }
                case 4: {
                    System.out.println("Username to be deleted: ");
                    sc.nextLine();
                    String usernamekb = sc.nextLine();
                    u.setUsername(usernamekb);
                    boolean delete = obiect.delete(u);
                    if(delete)
                    System.out.println("User deleted successfully.");
                    else
                        System.out.println("Error while deleting user.");
                    break;
                }
                case 5: {
                    chooseMenu = true;
                }
                default:
                    break;
            }
        }
    }

    private void menu() {
        
        System.out.print("""
                
                Choose your option:
                    1. Insert users
                    2. Print users
                    3. Update users
                    4. Delete users
                    5. Exit
                Your option is:\040""");
    }

    private boolean insert(User u) throws SQLException {

        //conectare la DB cu incarcare driver

        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB = "postgres";
        final String PWDB = "1234";

        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDB);

        //rulare sql

        PreparedStatement pst = conn.prepareStatement("insert into users(username,password) values(?,?)");
        pst.setString(1, u.getUsername());
        pst.setString(2, u.getPassword());
        int val = pst.executeUpdate(); // 1 pt inserare ok sau 0 pt operatie nereusita
        boolean ok = false;

        if (val != 0)
            ok = true;
        return ok;

    }

    private List<User> readAllUsers() throws SQLException {

        List<User> lu = new ArrayList<>();

        //citeste din DB toti userii si returneaza lista lor
        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB = "postgres";
        final String PWDB = "1234";

        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDB);

        //rulare sql

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from users order by username asc");


        while (rs.next()) {
            String user = rs.getString("username").trim();
            String p = rs.getString("password").trim();
            int iddb = rs.getInt("id");
            User u = new User(user, p, iddb);
            lu.add(u);
        }

        return lu;
    }

    private boolean update(User u) throws SQLException {
        //cod care scrie in DB

        //conectare la DB cu incarcare driver

        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB = "postgres";
        final String PWDB = "1234";

        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDB);

        //rulare sql

        PreparedStatement pst = conn.prepareStatement("update users set password=?,username=? where id=?");
        pst.setString(1, u.getPassword());
        pst.setString(2, u.getUsername());
        pst.setInt(3, u.getId());
        int val = pst.executeUpdate(); // 1 pt inserare ok sau 0 pt operatie nereusita
        boolean ok = false;

        if (val != 0)
            ok = true;
        return ok;

        //daca are rezultate citirea lor
    }

    private boolean delete(User u) throws SQLException {

        //conectare la DB cu incarcare driver

        final String URLDB = "jdbc:postgresql://localhost:5432/grupajava";
        final String USERNAMEDB = "postgres";
        final String PWDB = "1234";

        Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDB);

        //rulare sql

        PreparedStatement pst = conn.prepareStatement("delete from users where username=?");
        pst.setString(1, u.getUsername());
        int val = pst.executeUpdate(); // 1 pt inserare ok sau 0 pt operatie nereusita
        boolean ok = false;

        if (val != 0)
            ok = true;
        return ok;
    }
}
