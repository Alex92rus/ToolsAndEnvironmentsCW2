package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by andreas on 02/12/2016.
 */
public class CSVFileWriter
{
    private final static String fileName = "output.csv";


    public static void writeHeader( String header )
    {
        CSVFileWriter.write( header );
    }


    public static void write( String content )
    {
        try ( BufferedWriter bw = new BufferedWriter( new FileWriter( CSVFileWriter.fileName, true ) ) )
        {
            bw.write( content + "\n" );
        }
        catch ( IOException e )
        {
            ConsolePrinter.printError( e );
        }
    }
}
