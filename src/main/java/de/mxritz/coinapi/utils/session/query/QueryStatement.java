package de.mxritz.coinapi.utils.session.query;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JavaDoc this file!
 * Created: 2022
 *
 * @author Moritz Selz (moritzselz@gmail.com)
 */
public interface QueryStatement {
    ResultSet execute(Object... var1) throws SQLException;
}
