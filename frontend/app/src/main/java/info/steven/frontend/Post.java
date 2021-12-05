package info.steven.frontend;

public class Post {
    private int id;
    private String user_id;
    private int likes;
    private String name;
    private String category;
    private String url;
    private String creator;

    public Post(String user_id, int likes, String name, String category, String url, String creator) {
        this.user_id = user_id;
        this.likes = likes;
        this.name = name;
        this.category = category;
        this.url = url;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", likes=" + likes +
                ", title='" + name + '\'' +
                ", category='" + category + '\'' +
                ", img_url='" + url + '\'' +
                '}';
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
