package bean;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class UserBean {
    private int imageview;
    private String name;

    public UserBean(int imageview, String name) {
        this.imageview = imageview;
        this.name = name;
    }

    public UserBean() {
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "imageview=" + imageview +
                ", name='" + name + '\'' +
                '}';
    }
}
