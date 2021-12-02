package info.steven.frontend;

public class Post {
    private int id;
    private int user_id;
    private int likes;
    private String title;
    private String category;
    private String img_url;

    public Post(int id, int user_id, int likes, String title, String category, String img_url) {
        this.id = id;
        this.user_id = user_id;
        this.likes = likes;
        this.title = title;
        this.category = category;
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", likes=" + likes +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
