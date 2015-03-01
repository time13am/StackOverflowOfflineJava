package org.herman.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.herman.model.Question;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    public List<Question> Search(String keyword) {
        List<Question> result = new ArrayList<Question>();

        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/german");
            con = ds.getConnection();
            st = con.prepareStatement("SELECT id, title FROM posts WHERE title <> '' AND body LIKE ?");
            st.setString(1, "%" + keyword + "%");

            rs = st.executeQuery();
            
            while (rs.next()) {
                result.add(new Question(rs.getInt(1), rs.getString(2)));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

            //return null;
        } catch (NamingException ex) {
            Logger.getLogger(SearchService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        return result;
    }
}
