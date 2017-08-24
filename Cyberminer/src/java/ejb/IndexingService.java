/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import enitity.TokenizedUrl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Parth
 */
@Stateless
public class IndexingService implements IIndexingService
{
    
    public static final String[] LOGICAL_OP= {"AND", "NOT", "OR", "&&", "!", "||"};
    public static final String DESCRIPTORS = "URL_DESCRIPTORS";
    public static final String PARSED_LINES = "PARSEDLINES";
    public static final String TOKENIZEDURL = "TOKENIZEDURL";
    
    @PersistenceContext(unitName = "CyberminerPU")
    private EntityManager em;

    @Override
    public void persist(Object object)
    {
        em.persist(object);
    }
    
    @Override
    public void addIndex(Object object)
    {
        em.merge(object);
    }
    
    @Override
    public void emptyIndices()
    {
        //empty descriptors then URLs
        Query emptyDescriptors = em.createNativeQuery("DELETE FROM " + TOKENIZEDURL);
        Query emptyURLs = em.createNamedQuery("DELETE FROM " + DESCRIPTORS);
        
        emptyDescriptors.executeUpdate();
        emptyURLs.executeUpdate();
    }
    
    @Override
    public List<String> searchIndices(String search) throws SQLException
    {
        //System.out.println("Searching for : " + search);
        String query = "Select Distinct URL_ID from " + DESCRIPTORS + " where ( ";
        StringBuilder builder = new StringBuilder(query);
        List<String> searchWords = new ArrayList<>();
        Collections.addAll(searchWords, search.split("\\s"));
        
        for(String word: searchWords)
        {
            if(Arrays.asList(LOGICAL_OP).contains(word))
            {
                if(word.equals(LOGICAL_OP[0]) || word.equals(LOGICAL_OP[3]))
                {
                    builder.append(" AND ");
                }
                else if (word.equals(LOGICAL_OP[1]) || word.equals(LOGICAL_OP[4]))
                {
                    builder.append(" NOT ");
                }
                else if(word.equals(LOGICAL_OP[2]) || word.equals(LOGICAL_OP[5]))
                {
                    builder.append(" OR ");
                }
            }
            else
            {
                builder.append(PARSED_LINES).append(" LIKE '%" + word + "%'");
            }
        }
        builder.append(")");
        
        return em.createNativeQuery(builder.toString()).getResultList(); 
    }
    
    
    
}
