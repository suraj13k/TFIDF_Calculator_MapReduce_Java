import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TFIDFCalculator {
    public static void main(final String[] args) throws IOException {
        final File file1 = new File("output1.txt"); 
        final File file2 = new File("output2.txt"); 
        final File file3 = new File("output3.txt"); 
        final BufferedReader br1 = new BufferedReader(new FileReader(file1)); 
        final BufferedReader br2 = new BufferedReader(new FileReader(file2)); 
        final BufferedReader br3 = new BufferedReader(new FileReader(file3)); 
        HashMap<Output1Key,Integer> output1HM = new HashMap<Output1Key,Integer>();
        HashMap<Output1Key,Double> tfIDFHM = new HashMap<Output1Key,Double>();
        HashMap<String,Integer> docWordCountHM = new HashMap<String,Integer>();
        HashMap<String,Integer> termDocCountHM = new HashMap<String,Integer>();
        ExtractOutput1(br1, output1HM); 
        ExtractOutput(br2, docWordCountHM); 
        ExtractOutput(br3, termDocCountHM); 

        int totalDocs = docWordCountHM.size();

        for (Output1Key output1Key : output1HM.keySet()) {
            int noOfDocsWithTerm = termDocCountHM.get(output1Key.term);
            double idf = java.lang.Math.log(totalDocs/noOfDocsWithTerm);
            int tFrequency = output1HM.get(output1Key);
            int totalTermsInDoc = docWordCountHM.get(output1Key.doc);
            double tf = ((double)tFrequency)/totalTermsInDoc;
            tfIDFHM.put(output1Key, tf*idf);
        }

        ArrayList<TFIDF> list = new ArrayList<>();
        
        for (Output1Key key : tfIDFHM.keySet()) {
            list.add(new TFIDF(key,tfIDFHM.get(key)));
        }

        list.sort(new MyComparator());

        String inidoc = list.get(0).key.doc;
        int count =0;
        for (TFIDF tfidf : list) {
           

            if(inidoc.equals(tfidf.key.doc))
                count++;
            else{
                count = 0;
                inidoc = tfidf.key.doc;
                System.out.println("");
            }
             
            if(count<20) {
                System.out.println("Document - "+tfidf.key.doc +" Term - "+ tfidf.key.term +"\t"+ tfidf.value);
            }
            
        }
        
        br1.close();
    }

    private static void ExtractOutput(BufferedReader br, HashMap<String, Integer> output2hm) throws IOException {
        String st;
        while ((st = br.readLine()) != null) 
        {
            String[] keyValue = st.split("\\t");
            output2hm.put(keyValue[0],Integer.parseInt(keyValue[1]));
        }
    }

    private static void ExtractOutput1(final BufferedReader br, HashMap<Output1Key, Integer> output1HM)
            throws IOException {
        String st;
        while ((st = br.readLine()) != null) 
        {
            String[] keyValue = st.split("\\t");
            String[] termdoc = keyValue[0].split(",");
            Output1Key key = new Output1Key(termdoc[0],termdoc[1]);
            output1HM.put(key,Integer.parseInt(keyValue[1]));
        }
    }    
}

class Output1Key{
    String term;
    String doc;

    public Output1Key(String term, String doc) {
        this.term = term;
        this.doc = doc;
    }
}


class MyComparator implements Comparator<TFIDF> {
    public int compare(TFIDF o1, TFIDF o2) {
        int value1 = o1.key.doc.compareTo(o2.key.doc);
        if (value1 == 0) {
            if(o1.value>o2.value){
                return -1;
            }
            else if(o1.value<o2.value){
                return 1;
            }
            else return 0;
        }
        return value1;
    }
}

class TFIDF{
    Output1Key key;
    double value;

    public TFIDF(Output1Key key, double value) {
        this.key = key;
        this.value = value;
    }
}