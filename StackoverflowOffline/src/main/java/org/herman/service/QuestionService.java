package org.herman.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.herman.model.Comment;
import org.herman.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    
    public List<Question> process_page(int id){
        System.out.println("ENTERING PAGE PROCESSING");
        List<Question> posts = new ArrayList<Question>();
        posts.add(get_question(id));
        posts.addAll(get_answers(id));
        
        return posts;
    }
    
    public Question get_question(int id){
        System.out.println("ENTERING QUESTION PROCESSING");
        Question question = null;
        
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/german");
            con = ds.getConnection();
            st = con.prepareStatement("SELECT title, body FROM posts WHERE id = ?");
            st.setInt(1, id);

            rs = st.executeQuery();
            
            while (rs.next()) {
                
                question = new Question(id, rs.getString(1), rs.getString(2), get_comments(id));
                
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
        
        return question;
    }
    
    public List<Question> get_answers(int id){
        System.out.println("ENTERING ANSWERS PROCESSING");
        List<Question> answers = new ArrayList<Question>();
        
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/german");
            con = ds.getConnection();
            st = con.prepareStatement("SELECT body, id FROM posts WHERE parent_id = ? ORDER BY creation_date DESC");
            st.setInt(1, id);

            rs = st.executeQuery();
            
            while (rs.next()) {
                
                answers.add(new Question(id, rs.getString(1), get_comments(rs.getInt(2))));
                
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
        
        return answers;
    }
    
    public List<Comment> get_comments(int id){
        System.out.println("ENTERING COMMENTS PROCESSING");
        List<Comment> comments = new ArrayList<Comment>();
        
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("jdbc/german");
            con = ds.getConnection();
            st = con.prepareStatement("SELECT text FROM comments WHERE post_id = ? ORDER BY creation_date ASC");
            st.setInt(1, id);
            
            rs = st.executeQuery();
            
            while (rs.next()) {
                
                comments.add(new Comment(rs.getString(1)));
                
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
        
        return comments;
    }
}
