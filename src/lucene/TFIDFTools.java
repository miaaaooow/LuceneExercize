package lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.BytesRef;

public class TFIDFTools {

	public static int getTinDNumber(String term, String contentField,
			IndexReader indexReader) {
		Term lTerm = new Term(contentField, term);
		try {
			return indexReader.docFreq(lTerm);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static double getTFIDF(String term, int docId, String contentField,
			IndexReader indexReader, IndexSearcher searcher) throws IOException {
		// how many contain the term
		int TD = getTinDNumber(term, contentField, indexReader);
		// total number of docs
		int N = indexReader.numDocs();
		double TFIDF = 0;
		
		Terms terms = indexReader.getTermVector(docId, contentField);
		if (terms != null) {
			TermsEnum termEnum = terms.iterator(null);

			int TF = 0;
			BytesRef text;
			while ((text = termEnum.next()) != null) {
				System.out.println(text.utf8ToString());
				if (term.equals(text.utf8ToString())) {
					TF += 1;
				}
			}
			
			if (TD > 0) {
				TFIDF = TF * Math.log(N / TD);
			} else {
				TFIDF = 0;
			}
		}
		return TFIDF;

	}
}
