package io.chucknorris.assessment.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/24 at 8:31 PM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Joke {

    @SerializedName("categories")
    private List<String> categories;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("icon_url")
    private String iconURL;

    @SerializedName("id")
    private String id;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("url")
    private String url;

    @SerializedName("value")
    private String value;

}
