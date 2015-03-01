package forschungen;

import java.sql.*;
import java.text.ParseException;

public class questionsSearch {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://localhost:5432/german.stackexchange";
        String user = "postgres";
        String password = "00fafage";
        
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.prepareStatement("SELECT id, title FROM posts WHERE parent_id = -1 AND body LIKE ?");
            st.setString(1, "%" + "frankfurt" + "%");
            
            System.out.println(st);
            
            rs = st.executeQuery();
            
            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {
            if (ex.getMessage().contains("ERROR")){
                System.out.println(ex.getMessage() + "\n\n\n");              
            }
        } 
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
}
