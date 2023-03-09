package pojo.post;

import lombok.Data;

import java.sql.Array;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePost {
    private String text;
    private String image;
    private int likes;
    private Array tags;
    private String owner;
}
