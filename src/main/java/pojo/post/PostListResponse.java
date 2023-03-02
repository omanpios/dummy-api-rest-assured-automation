package pojo.post;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PostListResponse {
    public ArrayList<Data> data;
    public int total;
    public int page;
    public int limit;
}
