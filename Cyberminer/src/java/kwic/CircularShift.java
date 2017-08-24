package kwic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Parth on 6/21/2017.
 */
public class CircularShift {
    public static final String[] NOISE_WORDS = {"a", "and", "the", "of", "an" };
    
    private List<String> input;
    private List<String> output;

    public CircularShift()
    {
        output = new ArrayList<>();
    }

    public List<String> shiftAllLines()
    {
        //shift every line and add add set of lines to output
        for(String line: input)
        {
            output.addAll(shiftLine(line));
        }

        return output;
    }

    //generate all possible shifts from line
    public List<String> shiftLine(String line)
    {
        //split lines by whitespace and prepare output list
        String workingLine = line.trim();
        String lineTokens[] = workingLine.split("\\s");
        List<String> outputLines = new ArrayList<>();

        //start from the second word
        for(int i =0; i<lineTokens.length;i++)
        {
            outputLines.add(shiftLineFromWord(i,lineTokens));
        }

        return outputLines;
    }

    //generate single shift of a line from a word
    private String shiftLineFromWord(int firstWord, String[] tokenizedLine)
    {
        int numOfTokens = tokenizedLine.length;
        StringBuilder stringBuilder = new StringBuilder();

        //while the index doesnt wrap back around
        for(int i=0,  j=firstWord; i<numOfTokens; i++ , j++)
        {
            if(Arrays.asList(NOISE_WORDS).contains(tokenizedLine[firstWord]))
            {
                continue;
            }
            else
            {
                stringBuilder.append(tokenizedLine[j%numOfTokens] + " ");
            }
            
            
        }

        //now shifts will have extra space as the last char
        //stringBuilder.deleteCharAt(stringBuilder.length()-1);
        String output = stringBuilder.toString().trim();
        return output;
    }

    public void setInput(List<String> input)
    {
        this.input = input;
    }
    
    
}
