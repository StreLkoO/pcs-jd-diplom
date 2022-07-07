

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> entry = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) {
        for (File pdf : Objects.requireNonNull(pdfsDir.listFiles())) {
            try (var doc = new PdfDocument(new PdfReader(pdf))) {
                for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                    var text = PdfTextExtractor.getTextFromPage(doc.getPage(i));
                    var words = text.split("\\P{IsAlphabetic}+");
                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) {
                        if (word.isEmpty()) {
                            continue;
                        }
                        freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
                    }
                    int finalI = i;
                    freqs.forEach((k, v) -> {
                        List<PageEntry> list = entry.getOrDefault(k, new ArrayList<>());
                        list.add(new PageEntry(pdf.getName(), finalI, v));
                        entry.put(k, list);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        if (entry.containsKey(word)) {
            return entry.get(word).stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }

    }
}
