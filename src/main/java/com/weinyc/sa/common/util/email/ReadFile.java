package com.weinyc.sa.common.util.email;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadFile
{
    private String fileName = null;
    private int bufferSize = 1000;
    
    public ReadFile () {}
    
    public ReadFile (String fileName, int bufferSize)
    {
        this.setFileName (fileName);
        this.setBufferSize (bufferSize);    
    }
    
    public ReadFile (String fileName)
    {
        this(fileName, 1000);
    }
    
    public void setFileName (String fileName)
    {
        this.fileName = fileName;    
    }
    
    public String getFileName ()
    {
        return this.fileName;    
    }
    
    public void setBufferSize (int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
    
    public int getBufferSize ()
    {
        return this.bufferSize;    
    }
    
    public ArrayList <String>read () throws java.io.FileNotFoundException, java.io.IOException
    {
        FileReader fr = new FileReader (this.getFileName());
        BufferedReader br = new BufferedReader (fr);
        ArrayList<String>aList = new ArrayList<String> (this.getBufferSize());
        
        String line = null;
        while (     (line = br.readLine()) != null)
        {
            aList.add(line);
        }
        
        br.close();
        fr.close();
        
        return aList;
    }
    
    public static void main (String args[])    //include main for testing purposes
    {
        try
        {
            ReadFile rf = new ReadFile("D:\\tomcat5.5\\webapps\\rem\\htdoc\\styles\\planing\\css\\Q_REM_L001.css");
            ArrayList<String> a = rf.read();
            for(String x : a){
            	 System.out.println (x);
            }
        }
        catch (Exception e)
        {
            System.out.println (e.getMessage());    
        }
    }
} 