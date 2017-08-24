/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enitity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

/**
 *
 * @author Parth
 */
@Entity
public class TokenizedUrl implements Serializable
{
    @Id
    private String url;
    private Date lastModified;
    @ElementCollection
    @CollectionTable(name = "URL_Descriptors", joinColumns=@JoinColumn(name="URL_ID")
    )
    private List<String> parsedLines;
    @Transient
    private List<String> circularLines;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public List<String> getParsedLines()
    {
        return parsedLines;
    }

    public void setParsedLines(List<String> descriptors)
    {
        this.parsedLines = descriptors;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    public List<String> getCircularLines()
    {
        return circularLines;
    }

    public void setCircularLines(List<String> circularLines)
    {
        this.circularLines = circularLines;
    }
    
    

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (url != null ? url.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TokenizedUrl))
        {
            return false;
        }
        TokenizedUrl other = (TokenizedUrl) object;
        if ((this.url == null && other.url != null) || (this.url != null && !this.url.equals(other.url)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "enitity.TokenizedUrl[ URL=" + url + " ]";
    }
    
}
