/*
 * To change this template file, choose Settings | Editor | File and Code Templates
 * and change the template in the editor.
 */

package sample;

/**
 * @author Ahmed Abuwarda, Date: Apr 29,2020 , 1:44 AM.
 */
public class Table {

    private String url;
    private int id;

    public Table() {
        url = "https://";
        id = 0;
    }

    /**
     * @param url url
     * @param id  id
     */
    public Table(int id, String url) {
        this.url = url;
        this.id = id;
    }

    /**
     * @param url set url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param id set id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

}