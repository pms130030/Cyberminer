/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cyberminer;

import ejb.IIndexingService;
import ejb.IndexingService;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

/**
 *
 * @author Parth
 */
@Named("searchController")
@RequestScoped
public class SearchController
{

    @EJB
    private IIndexingService indexingService;

    private String search;
    private String errorLabel;
    private List<String> urls;
    
    
    
    public void doSearch()
    {
        try
        {
            urls = indexingService.searchIndices(search);
        } catch (SQLException ex)
        {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(urls.isEmpty())
        {
            errorLabel = "No matching results";
        }
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }

    public String getErrorLabel()
    {
        return errorLabel;
    }

    public void setErrorLabel(String errorLabel)
    {
        this.errorLabel = errorLabel;
    }

    public List<String> getUrls()
    {
        return urls;
    }

    public void setUrls(List<String> urls)
    {
        this.urls = urls;
    }
    
    
}
