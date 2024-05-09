package org.jordijaspers.aniflix.api.anime.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.jordijaspers.aniflix.api.consumed.consumet.ConsumetConstants.Constants.SLASH;
import static org.jordijaspers.aniflix.config.GlobalConfiguration.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "episode")
@ToString(exclude = "anime")
@EqualsAndHashCode(exclude = "anime")
public class Episode implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "gogoanime_id")
    private String gogoanimeId;

    @Column(name = "zoro_id")
    private String zoroId;

    @Column(name = "title")
    private String title;

    @Column(name = "number")
    private int number;

    @Column(name = "image")
    private String image;

    @Column(name = "duration")
    private long duration;

    @Column(name = "air_date")
    private LocalDateTime airDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Anime anime;

    @Transient
    private StreamingLinks streamingLinks;

    /**
     * Remove the slash at the start in the gogoanime id, if it exists.
     * @return
     */
    public String getGogoanimeId() {
        return nonNull(gogoanimeId) && gogoanimeId.startsWith(SLASH) ? gogoanimeId.substring(1) : gogoanimeId;
    }

    /**
     * Remove the slash at the start in the zoro id, if it exists.
     * @return
     */
    public String getZoroId() {
        return nonNull(zoroId) && zoroId.startsWith(SLASH) ? zoroId.substring(1) : zoroId;
    }
}
