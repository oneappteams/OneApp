package mind.com.oneapp.Framework;

/**
 * Created by Kiran on 16-02-2016.
 */

public class RowData {


    private String category;
    private String title;
    private String subTitle;
    private String url;



    public RowData(String category, String title, String subTitle, String url)
    {
        this.setCategory(category);
        this.setTitle(title);
        this.setSubTitle(subTitle);
        this.setUrl(url);


    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
