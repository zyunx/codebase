package net.zyunx.dbinit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {

    protected static final Log logger = LogFactory.getLog(App.class);

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        DataSource dataSource = context.getBean("dataSource", DataSource.class);

        // initialize database 
        initializeDatabase(dataSource, context);

        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from auth_user");
        if (result.next()) {
            logger.info("User: " + result.getString("username") + ", " + result.getString("password"));
        }
        logger.info("Do some CRUD");
        conn.close();

    }
    
    
    public static void initializeDatabase(DataSource dataSource, ApplicationContext context) throws SQLException, IOException  {
         String[] tables = new String[] {
            "auth_user",
            "auth_role",
            "auth_permission",
            "auth_user_role",
            "auth_user_permission",
            "auth_role_permission"
        };
         
        DatabaseInitializer dbInit = new DatabaseInitializer();
        dbInit.setContext(context);
        dbInit.setDataSource(dataSource);
        
        try {
            dbInit.populateDatabase(tables);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

}
