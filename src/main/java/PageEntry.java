public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public String toString() {
        return "pdf: " + pdfName + " page: " + page + " count: " + count;
    }

    @Override
    public int compareTo(PageEntry o) {
        int res = this.count - o.count;
        if (res == 0) {
            res = this.pdfName.compareTo(o.pdfName);
            if (res == 0) {
                return this.page - o.page;
            } else {
                return res;
            }
        } else {
            return res;
        }
    }

}
