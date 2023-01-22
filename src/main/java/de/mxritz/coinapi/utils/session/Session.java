package de.mxritz.coinapi.utils.session;

import de.mxritz.coinapi.utils.session.query.QueryStatement;
import de.mxritz.coinapi.utils.session.query.UpdateStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JavaDoc this file!
 * Created: 2022
 *
 * @author Moritz Selz (moritzselz@gmail.com)
 */
public class Session {
    private Connection connection;
    private final String host;
    private final Integer port;
    private final String database;
    private final String user;
    private final String password;
    private final Boolean autoReconnet;

    public Session(String host, int port, String database, String user, String password, boolean autoReconnet) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.autoReconnet = autoReconnet;
    }

    public void disable() {
        this.close();
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=" + this.autoReconnet, this.user, this.password);
            System.out.println("[API] The connection to the database was opened.");
        } catch (Exception var2) {
            var2.printStackTrace();
            System.out.println("[API] Error while connecting to the database.");
        }

    }

    public void close() {
        try {
            this.connection.close();
            System.out.println("[API] The connection to the database was closed.");
        } catch (SQLException var2) {
            var2.printStackTrace();
            System.out.println("[API] Error while disconnecting from the database.");
        }

    }

    public QueryStatement prepareQueryStatement(String query) {
        return (args) -> {
            Statement statement = this.connection.createStatement();
            String queryString = query;
            Object[] var5 = args;
            int var6 = args.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Object object = var5[var7];
                queryString = queryString.replaceFirst("\\?", "'" + object.toString() + "'");
            }

            return statement.executeQuery(queryString);
        };
    }

    public UpdateStatement prepareUpdateStatement(String query) {
        return (args) -> {
            Statement statement = this.connection.createStatement();
            String queryString = query;
            Object[] var5 = args;
            int var6 = args.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Object object = var5[var7];
                queryString = queryString.replaceFirst("\\?", "'" + object.toString() + "'");
            }

            return statement.executeUpdate(queryString);
        };
    }

    public Connection getConnection() {
        return this.connection;
    }
}

