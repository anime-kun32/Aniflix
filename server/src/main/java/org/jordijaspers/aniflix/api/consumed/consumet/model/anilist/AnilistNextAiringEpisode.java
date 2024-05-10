package org.jordijaspers.aniflix.api.consumed.consumet.model.anilist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The next airing episode of an anime.
 */
@Data
public class AnilistNextAiringEpisode {

    @JsonProperty("airingTime")
    private Long airingTime;
    
    @JsonProperty("timeUntilAiring")
    private Long timeUntilAiring;
    
    @JsonProperty("episode")
    private Long episode;

}
