package models;

/**
 * Created by andreas on 03/12/2016.
 */
public class BuildSummary
{
    private String projectName;
    private String language;
    private boolean isFromCore;
    private String status;
    private int counter;


    public BuildSummary(String projectName, String language, boolean isFromCore, String status, int counter )
    {
        this.projectName = projectName;
        this.language = language;
        this.isFromCore = isFromCore;
        this.status = status;
        this.counter = counter;
    }


    public String getProjectName()
    {
        return this.projectName;
    }


    public String getLanguage()
    {
        return this.language;
    }


    public boolean isFromCore()
    {
        return this.isFromCore;
    }


    public String getStatus()
    {
        return this.status;
    }


    public int getCounter()
    {
        return this.counter;
    }
}
