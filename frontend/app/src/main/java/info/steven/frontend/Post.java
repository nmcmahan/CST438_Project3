package info.steven.frontend;

public class Post {
    private int id;
    private int likes;
    private String user_id;
    private String name;
    private String category;
    private String url;

    public Post(int id, int likes, String user_id, String name, String category, String url) {
        this.id = id;
        this.user_id = user_id;
        this.likes = likes;
        this.name = name;
        this.category = category;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", likes=" + likes +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", img_url='" + url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
