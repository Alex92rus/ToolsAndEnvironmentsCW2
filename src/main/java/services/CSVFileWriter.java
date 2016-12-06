package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by andreas on 02/12/2016.
 */
public class CSVFileWriter
{
    private String fileName;


    public CSVFileWriter( String fileName )
    {
        this.fileName = fileName;
    }


    public void writeHeader( String header )
    {
        this.write( header );
    }


    public void write( String content )
    {
        try ( BufferedWriter bw = new BufferedWriter( new FileWriter( this.fileName, true ) ) )
        {
            bw.write( content + "\n" );
        }
        catch ( IOException e )
        {
            ConsolePrinter.printError( e );
        }
    }
}
