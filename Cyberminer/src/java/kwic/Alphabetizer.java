/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Parth on 6/21/2017.
 */
public class Alphabetizer {
    private List<String> unsortedList;

    public Alphabetizer()
    {
    }

    public List<String> sortLines()
    {
        //output list
        List<String> output = new ArrayList<>(unsortedList.size());
        Collections.sort(unsortedList);
        return unsortedList;
    }

    public void setUnsortedList(List<String> unsortedList)
    {
        this.unsortedList = unsortedList;
    }
    
    

}