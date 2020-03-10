# TFIDF_Calculator_MapReduce_Java
TFIDF calculator for the words in list of documents provided as input. This program is written in JAVA. 

Q1.	Execute all phases of the TFIDF program, on the small sample data shared by Sahil, and submit the following items:
    a.	TFIDF for top 18 terms in each document, sorted in descending order of their tfidf values, and formatted for easy readability.

Q2.	Modify the programs to remove from consideration all those words that occur only once or twice in each document. Repeat the task of Q1 above. Comment on any changes in the results of part 1(a). Select at least 3 different words for which there is a change in their tfidf values and explain the reason for the change.

Q3.	Now consider a “Term” to mean a 2-gram (two words occurring sequentially) in a document. Modify the programs given to you to compute the TFIDF for each 2-gram. Submit the following items:
    a.	List of top 20 2-grams for each document, having the highest TFIDF values. The task of selecting the top 20 terms does not need to be done by the MapReduce program.
    b.	Which output – obtained in 3(a) or in 2(a) – better characterizes the documents? Give reasons for your answers.

Q4.	Once your program is working for the above two parts, run the programs on a larger collection of documents (to be provided to you by March 2nd) and repeat the above task . Discuss the results for 1(a), 2(a), and 3(a) in the context of the new set of documents. 


# MapReduce 
*****How it works******:

# Phase 1:
  **Mapper:**
    Input : 		(documentName, contents);
    Output:		((word, documentName), 1);

  **Reducer:**
    n = 			Sum count for word in document
    Output:		((word, documentName),n);

# Phase 2:
  **Mapper:**
    Input : 		((word, documentName),n);
    Output:		(documentName, n);

  **Reducer:**
    N = 			Sum count for words in document
    Output:		(documentName, N);
    
# Phase 3:
  **Mapper:**
    Input : 		((word, documentName),n);
    Output:		(word, 1);

  **Reducer:**
    N = 			total count of a word
    Output:		(word, N);
    
# Calculate TF-IDF on Stand-Alone M/C (Java Program TFIDFCalculator.java):

      totalDocs (Total Number of documents) = number of lines in phase2 output file.

      docWithTerm (Number of documents with term t in it) = form pahse3 output file.

      Calculating IDF:
      IDF = log(totalDocs / docWithTerm)


      tFrequency (Number of times term t appears in a document) = form pahse1 output file.

      totalTerms (Total number of terms in the given document) = form pahse2 output file.

      Calculating TF:
      TF = tFrequency /  totalTerms


      Calculating TF-IDF:
      tfidf = TF * IDF



