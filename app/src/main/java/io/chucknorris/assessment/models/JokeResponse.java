package io.chucknorris.assessment.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Project Name - chucknorris-jokes
 * Created on 2021/05/26 at 4:55 PM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JokeResponse {

    @SerializedName("total")
    private int total;

    @SerializedName("result")
    public List<Joke> jokes;
}
