package importers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class Import {

    public static void main(String[] args) throws FactoryConfigurationError, FileNotFoundException, XMLStreamException, SQLException, NumberFormatException, ParseException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new FileReader("g:\\Temp\\german\\Comments.xml"));

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        new Comment(reader).store_entry();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    break;
            }
        }
        
        reader = factory.createXMLStreamReader(new FileReader("g:\\Temp\\german\\PostHistory.xml"));

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        new PostHistory(reader).store_entry();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    break;
            }
        }
        
        reader = factory.createXMLStreamReader(new FileReader("g:\\Temp\\german\\Posts.xml"));

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("row".equals(reader.getLocalName())) {
                        new Post(reader).store_entry();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    break;
            }
        }
    }
}

class Post {

    public String Id;
    public String PostTypeId;
    public String AcceptedAnswerId;
    public String ParentID;
    public String CreationDate;
    public String Score;
    public String ViewCount;
    public String Body;
    public String OwnerUserId ;
    public String OwnerDisplayName;
    public String LastEditorUserId;
    public String LastEditorDisplayName;
    public String LastEditDate;
    public String LastActivityDate;
    public String Title;
    public String Tags;
    public String AnswerCount;
    public String CommentCount;
    public String FavoriteCount;
    public String ClosedDate;
    public String CommunityOwnedDate;

    public Post(XMLStreamReader reader) {
        this.Id                     = reader.getAttributeValue("", "Id");
        this.PostTypeId             = reader.getAttributeValue("", "PostTypeId");
        this.AcceptedAnswerId       = reader.getAttributeValue("", "AcceptedAnswerId");
        this.ParentID               = reader.getAttributeValue("", "ParentId");
        this.CreationDate           = reader.getAttributeValue("", "CreationDate");
        this.Score                  = reader.getAttributeValue("", "Score");
        this.ViewCount              = reader.getAttributeValue("", "ViewCount");
        this.Body                   = reader.getAttributeValue("", "Body");
        this.OwnerUserId            = reader.getAttributeValue("", "OwnerUserId");
        this.OwnerDisplayName       = reader.getAttributeValue("", "OwnerDisplayName");
        this.LastEditorUserId       = reader.getAttributeValue("", "LastEditorUserId");
        this.LastEditorDisplayName  = reader.getAttributeValue("", "LastEditorDisplayName");
        this.LastEditDate           = reader.getAttributeValue("", "LastEditDate");
        this.LastActivityDate       = reader.getAttributeValue("", "LastActivityDate");
        this.Title                  = reader.getAttributeValue("", "Title");
        this.Tags                   = reader.getAttributeValue("", "Tags");
        this.AnswerCount            = reader.getAttributeValue("", "AnswerCount");
        this.CommentCount           = reader.getAttributeValue("", "CommentCount");
        this.FavoriteCount          = reader.getAttributeValue("", "FavoriteCount");
        this.ClosedDate             = reader.getAttributeValue("", "ClosedDate");
        this.CommunityOwnedDate     = reader.getAttributeValue("", "CommunityOwnedDate");
    }
    
    public void store_entry() throws NumberFormatException, ParseException
    {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://localhost:5432/german.stackexchange";
        String user = "postgres";
        String password = "00fafage";
 
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.prepareStatement("INSERT INTO posts(id, post_type_id, accepted_answer_id, parent_id, score, view_count, body, owner_user_id, owner_display_name, last_editor_user_id, last_editor_display_name, title, answer_count, comment_count, tags, favourite_count, creation_date, last_edit_date, last_activity_date, closed_date, community_owned_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, Integer.parseInt(this.Id));
            st.setInt(2, Integer.parseInt(this.PostTypeId));
            if (this.AcceptedAnswerId != null) { st.setInt(3, Integer.parseInt(this.AcceptedAnswerId)); } else { st.setInt(3, -1); } 
            if (this.ParentID != null) { st.setInt(4, Integer.parseInt(this.ParentID)); } else { st.setInt(4, -1); }            
            st.setInt(5, Integer.parseInt(this.Score));
            if (this.ViewCount != null) { st.setInt(6, Integer.parseInt(this.ViewCount)); } else { st.setInt(6, -1); } 
            st.setString(7, this.Body);
            if (this.OwnerUserId != null) { st.setInt(8, Integer.parseInt(this.OwnerUserId)); } else { st.setInt(8, -1); }
            st.setString(9, this.OwnerDisplayName);
            if (this.LastEditorUserId != null) { st.setInt(10, Integer.parseInt(this.LastEditorUserId)); } else { st.setInt(10, -1); }
            st.setString(11, this.LastEditorDisplayName);
            st.setString(12, this.Title);
            if (this.AnswerCount != null) { st.setInt(13, Integer.parseInt(this.AnswerCount)); } else { st.setInt(13, -1); }
            st.setInt(14, Integer.parseInt(this.CommentCount));
            st.setString(15, this.Tags);
            if (this.FavoriteCount != null) { st.setInt(16, Integer.parseInt(this.FavoriteCount)); } else { st.setInt(16, -1); }
            st.setTimestamp(17, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.CreationDate).getTime()));
            if (this.LastEditDate != null)
            {
                st.setTimestamp(18, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.LastEditDate).getTime()));
            } 
            else 
            {
                st.setTimestamp(18, null);
            }
            st.setTimestamp(19, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.LastActivityDate).getTime()));
            if (this.ClosedDate != null)
            {
                st.setTimestamp(20, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.ClosedDate).getTime()));
            } 
            else
            {
                st.setTimestamp(20, null);
            }
            if (this.CommunityOwnedDate != null)
            {
                st.setTimestamp(21, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.CommunityOwnedDate).getTime()));
            } 
            else 
            {
                st.setTimestamp(21, null);
            }
            rs = st.executeQuery();
            
            if (rs.next()) {
                System.out.println(rs.getString(1));
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

class PostHistory {

    public String Id;
    public String PostHistoryTypeId;
    public String PostId;
    public String RevisionGUID;
    public String CreationDate;
    public String UserId;
    public String UserDisplayName;
    public String Comment;
    public String Text;

    public PostHistory(XMLStreamReader reader) {
        this.Id                 = reader.getAttributeValue("", "Id");
        this.PostHistoryTypeId  = reader.getAttributeValue("", "PostHistoryTypeId");
        this.PostId             = reader.getAttributeValue("", "PostId");
        this.RevisionGUID       = reader.getAttributeValue("", "RevisionGUID");
        this.CreationDate       = reader.getAttributeValue("", "CreationDate");
        if (this.UserId == null) {
            this.UserId         = "-1";
        } else {
            this.UserId         = reader.getAttributeValue("", "UserId");
        }
        this.UserDisplayName    = reader.getAttributeValue("", "UserDisplayName");
        this.Comment            = reader.getAttributeValue("", "Comment");
        this.Text               = reader.getAttributeValue("", "Text");
    }
    
    public void store_entry() throws NumberFormatException, ParseException
    {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://localhost:5432/german.stackexchange";
        String user = "postgres";
        String password = "00fafage";
 
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.prepareStatement("INSERT INTO posthistory(id, post_history_type_id, post_id, creation_date, user_id, user_display_name, comment, text) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, Integer.parseInt(this.Id));
            st.setInt(2, Integer.parseInt(this.PostHistoryTypeId));
            st.setInt(3, Integer.parseInt(this.PostId));
            st.setTimestamp(4, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.CreationDate).getTime()));
            st.setInt(5, Integer.parseInt(this.UserId));
            st.setString(6, this.UserDisplayName);
            st.setString(7, this.Comment);
            st.setString(8, this.Text);
            rs = st.executeQuery();
            
            if (rs.next()) {
                System.out.println(rs.getString(1));
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


class Comment {

    public String Id;
    public String PostId;
    public String Score;
    public String Text;
    public String CreationDate;
    public String UserDisplayName;
    public String UserId;

    public Comment(XMLStreamReader reader) {
        this.Id                 = reader.getAttributeValue("", "Id");
        this.PostId             = reader.getAttributeValue("", "PostId");
        this.Score              = reader.getAttributeValue("", "Score");
        this.Text               = reader.getAttributeValue("", "Text");
        this.CreationDate       = reader.getAttributeValue("", "CreationDate");
        this.UserDisplayName    = reader.getAttributeValue("", "UserDisplayName");
        this.UserId             = reader.getAttributeValue("", "UserId");
    }

    public void store_entry() throws NumberFormatException, ParseException
    {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        String url = "jdbc:postgresql://localhost:5432/german.stackexchange";
        String user = "postgres";
        String password = "00fafage";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.prepareStatement("INSERT INTO comments(id, post_id, score, text, creation_date, user_display_name, user_id) VALUES(?, ?, ?, ?, ?, ?, ?)");
            st.setInt(1, Integer.parseInt(this.Id));
            st.setInt(2, Integer.parseInt(this.PostId));
            st.setInt(3, Integer.parseInt(this.Score));
            st.setString(4, this.Text);
            System.out.println(this.CreationDate);
            st.setTimestamp(5, new java.sql.Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(this.CreationDate).getTime()));
            st.setString(6, this.UserDisplayName);
            if (this.UserId != null) {
                st.setInt(7, Integer.parseInt(this.UserId));
            } else {
                st.setInt(7, -1);
            }

            rs = st.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            if (ex.getMessage().contains("ERROR")) {
                System.out.println(ex.getMessage() + "\n\n\n");
            }
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
    }
}
