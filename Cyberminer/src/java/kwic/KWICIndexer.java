/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwic;

import ejb.IIndexingService;
import ejb.IndexingService;
import enitity.TokenizedUrl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Parth
 */
@Named("indexer")
@RequestScoped
public class KWICIndexer
{

    @EJB
    private IIndexingService indexingService;
    public static final String URL_PATTERN = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
    public static final String LINES_DELIMITER = "(\\r\\n|\\r|\\n)";
    public static final String URL_DELIMITER = " ";
    
    private String lines;
    private String errorLabel;
    
    private List<String> inputLines;
    private List<TokenizedUrl> urls;
    private int currBuildOption=1; //  1 = Update 2 = Initial Build
    
    public void buildIndices()
    {
        inputLines = new ArrayList<>();
        urls  = new ArrayList<>();
        if(currBuildOption == 2)
        {
            indexingService.emptyIndices();
        }
        
        setData();
    }
    
    public void setData()
    {
        
        List<String> tempLine;
        tempLine=new ArrayList<>();
        Pattern urlPattern = Pattern.compile(URL_PATTERN);
        Matcher urlMatcher;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        Collections.addAll(inputLines, lines.split(LINES_DELIMITER));
        CircularShift circularShift = new CircularShift();
        Alphabetizer alphabetizer = new Alphabetizer();
        
        for(String line : inputLines)
        {
            //Collections.addAll(tempLine, line.split(URL_DELIMITER));
            tempLine.add(line.substring(0, line.indexOf(URL_DELIMITER)).trim());
            tempLine.add(line.substring(line.indexOf(URL_DELIMITER)+1).trim());
            TokenizedUrl tempUrl = new TokenizedUrl();
            
            urlMatcher = urlPattern.matcher(tempLine.get(0));
            {
                if(urlMatcher.find())
                {
                    tempUrl.setUrl(urlMatcher.group(0));
                }
                else
                {
                    errorLabel = "Skipping inserstion: invalid url : '" + tempLine.get(0) + "'";
                    continue;
                }
            }
            tempUrl.setCircularLines(circularShift.shiftLine(tempLine.get(1).trim()));
            alphabetizer.setUnsortedList(tempUrl.getCircularLines());
            tempUrl.setParsedLines(alphabetizer.sortLines());
            tempUrl.setLastModified(new Date(Calendar.getInstance().getTimeInMillis()));
            urls.add(tempUrl);
            tempLine.clear();
        }
        
        for(TokenizedUrl token: urls)
        {
            indexingService.addIndex(token);
        }
    }

    public List<TokenizedUrl> getUrls()
    {
        return urls;
    }

    public void setUrls(List<TokenizedUrl> urls)
    {
        this.urls = urls;
    }    

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getErrorLabel()
    {
        return errorLabel;
    }

    public void setErrorLabel(String errorLabel)
    {
        this.errorLabel = errorLabel;
    }

    public int getCurrBuildOption()
    {
        return currBuildOption;
    }

    public void setCurrBuildOption(int currBuildOption)
    {
        this.currBuildOption = currBuildOption;
    }
    
    private <T> String listToString(List<T> list)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(T item: list)
        {
            stringBuilder.append(item.toString() + "\n");
        }
        
        return stringBuilder.toString();
    }
}
