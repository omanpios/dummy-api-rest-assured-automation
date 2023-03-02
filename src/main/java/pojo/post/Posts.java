package pojo.post;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Posts {
    public String id;
    public String image;
    public int likes;
    public ArrayList<String> tags;
    public String text;
    public Date publishDate;
    public Owner owner;
}
