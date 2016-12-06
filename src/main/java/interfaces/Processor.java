package interfaces;

import services.Database;

import java.sql.SQLException;

/**
 * Created by andreas on 06/12/2016.
 */
public interface Processor
{
    public void run( Database database ) throws SQLException;
}
