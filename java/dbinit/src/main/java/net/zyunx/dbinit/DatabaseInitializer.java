/*
Copyright (c) 2016 Zhang Yun
All rights reserved.

Redistribution and use in source and binary forms are permitted
provided that the above copyright notice and this paragraph are
duplicated in all such forms and that any documentation,
advertising materials, and other materials related to such
distribution and use acknowledge that the software was developed
by Zhang Yun. The name of the
Zhang Yun may not be used to endorse or promote products derived
from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package net.zyunx.dbinit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 *
 * @author zyun
 */
public class DatabaseInitializer {
    protected Log logger = LogFactory.getLog(DatabaseInitializer.class);
    
    protected DataSource dataSource;
    protected ApplicationContext context;
    
    protected String resourcePrefix = "classpath:database/";
    protected String resourceSuffix = ".sql";
    
    public DatabaseInitializer() {
   
    }
    
    public void populateDatabase(String[] tables) throws SQLException, IOException {
        for (String table : tables) {
            if (!doesTableExist(table)) {
                logger.info("Table " + table + " does not exist");
                populateTable(table);
            }
        }
    }

    
    
    public void populateTable(String tableName) throws IOException, SQLException {
        logger.info("Populate table " + tableName);
        Connection conn = this.dataSource.getConnection();
        Statement stmt = conn.createStatement();
        String sql = resourceContent(context.getResource(this.resourcePrefix+ tableName + this.resourceSuffix));
        stmt.executeUpdate(sql);
        stmt.close();
    }
    
    
    public boolean doesTableExist(String tableName) throws SQLException {
        boolean exist = true;
        
        Connection conn = this.dataSource.getConnection();
        Statement stmt = conn.createStatement();
        
        try {
            stmt.execute("select count(1) from " + tableName);
        } catch (SQLException ex) {
            exist = false;
        } finally {
            stmt.close();
        }
        
        return exist;
    }
    
    
    public String resourceContent(Resource resource) throws IOException {
        StringBuilder sb = new StringBuilder();
        Reader reader = new InputStreamReader(resource.getInputStream());
        CharBuffer cb = CharBuffer.allocate(1024);
        int c;

        do {
            c = reader.read(cb);
            sb.append(cb);
            cb.clear();
        } while (c != -1);

        return sb.toString();
    }
    
    
    
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public String getResourcePrefix() {
        return resourcePrefix;
    }

    public void setResourcePrefix(String resourcePrefix) {
        this.resourcePrefix = resourcePrefix;
    }

    public String getResourceSuffix() {
        return resourceSuffix;
    }

    public void setResourceSuffix(String resourceSuffix) {
        this.resourceSuffix = resourceSuffix;
    }

}
