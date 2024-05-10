package org.jordijaspers.aniflix.api.anime.controller;

import lombok.RequiredArgsConstructor;
import org.jordijaspers.aniflix.api.anime.model.Anime;
import org.jordijaspers.aniflix.api.anime.model.mapper.AnimeMapper;
import org.jordijaspers.aniflix.api.anime.model.request.AnimeRequest;
import org.jordijaspers.aniflix.api.anime.model.request.GenreRequest;
import org.jordijaspers.aniflix.api.anime.model.request.PageRequest;
import org.jordijaspers.aniflix.api.anime.model.response.AnimeResponse;
import org.jordijaspers.aniflix.api.anime.model.response.DetailedAnimeResponse;
import org.jordijaspers.aniflix.api.anime.model.response.EpisodeResponse;
import org.jordijaspers.aniflix.api.anime.service.AnimeService;
import org.jordijaspers.aniflix.api.consumed.consumet.model.anilist.AnilistRecentEpisode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.jordijaspers.aniflix.api.Paths.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * The controller for the anime endpoints.
 */
@RestController
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    private final AnimeMapper animeMapper;

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = ANIME_SEARCH, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnimeResponse>> searchAnime(@RequestBody final AnimeRequest request) {
        final Map<String, String> filters = animeMapper.toFilters(request);
        final List<Anime> anime = animeService.searchAnime(filters);
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithoutEpisodes(anime));
    }

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(path = ANIME_DETAILS, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DetailedAnimeResponse> getAnimeDetails(@PathVariable("id") final int anilistId) {
        final Anime anime = animeService.findByAnilistId(anilistId);
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithEpisodes(anime));
    }

    // ======================================== ANIME OVERVIEW ========================================

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(path = ANIME_BANNER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AnimeResponse> getAnimeBanner() {
        final Anime anime = animeService.getAnimeBanner();
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithoutEpisodes(anime));
    }

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = ANIME_RECENT, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EpisodeResponse>> getRecentAnime(@RequestBody final PageRequest request) {
        final List<AnilistRecentEpisode> recentEpisodes = animeService.getAnimeOfRecentEpisodes(request.getPerPage(), request.getPage());
        return ResponseEntity.status(OK).body(animeMapper.toRecentEpisodesResponse(recentEpisodes));
    }

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = ANIME_POPULAR, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnimeResponse>> getPopularAnime(@RequestBody final PageRequest request) {
        final List<Anime> popularAnime = animeService.getPopularAnime(request.getPerPage(), request.getPage());
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithoutEpisodes(popularAnime));
    }

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = ANIME_TRENDING, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnimeResponse>> getTrendingAnime(@RequestBody final PageRequest request) {
        final List<Anime> trendingAnime = animeService.getTrendingAnime(request.getPerPage(), request.getPage());
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithoutEpisodes(trendingAnime));
    }

    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(path = ANIME_GENRE, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AnimeResponse>> getAnimeByGenre(@RequestBody final GenreRequest request) {
        final List<Anime> animeByGenre = animeService.getAnimeByGenre(request.getGenre(), request.getPerPage(), request.getPage());
        return ResponseEntity.status(OK).body(animeMapper.toResponseWithoutEpisodes(animeByGenre));
    }
}
