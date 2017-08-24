/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Parth
 */

@Named("kwicBean")
@RequestScoped
public class KWICTestBean 
{
    private String url;
    private String descriptor;
    private String lines;
    private List<String> inputList;
    private List<String> circularShifts;
    private List<String> alphaList;
    
    private String shiftOut;
    private String sortOut;
    
    public void setData()
    {
        inputList = new ArrayList<>();
        //Pattern  urlPattern = Pattern.compile(KwicReference.URL_PATTERN);
//        Matcher urlMatcher = urlPattern.matcher(url);
        
        
        Collections.addAll(inputList, lines.split("(\\r\\n|\\r|\\n)"));
        
        setCircular();
        setAplabetic();
    }
    
    // https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)
    
    public void setCircular()
    {
        CircularShift circularShift = new CircularShift();
        this.circularShifts = circularShift.shiftAllLines();
        
        StringBuilder stringBuilder = new StringBuilder();
        for(String line: circularShifts)
        {
            stringBuilder.append(line + "\n");
        }
        //shiftOut  =stringBuilder.toString();
        
        System.out.println(stringBuilder.toString());
        
    }
    
    public void setAplabetic()
    {
        Alphabetizer alphabetizer = new Alphabetizer();
        this.alphaList = alphabetizer.sortLines();
        
        StringBuilder stringBuilder = new StringBuilder();
        for(String line: alphaList)
        {
            stringBuilder.append(line + "\n");
        }
        //sortOut  =stringBuilder.toString();
        System.out.println(stringBuilder.toString());
        
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getShiftOut() {
        return shiftOut;
    }

    public void setShiftOut(String shiftOut) {
        this.shiftOut = shiftOut;
    }

    public String getSortOut() {
        return sortOut;
    }

    public void setSortOut(String sortOut) {
        this.sortOut = sortOut;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
    
}
