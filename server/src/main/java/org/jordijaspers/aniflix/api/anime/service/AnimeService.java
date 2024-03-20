package org.jordijaspers.aniflix.api.anime.service;

import lombok.RequiredArgsConstructor;
import org.hawaiiframework.repository.DataNotFoundException;
import org.jordijaspers.aniflix.api.anime.model.Anime;
import org.jordijaspers.aniflix.api.anime.model.Episode;
import org.jordijaspers.aniflix.api.anime.model.constant.Genres;
import org.jordijaspers.aniflix.api.anime.repository.AnimeRepository;
import org.jordijaspers.aniflix.api.consumet.model.anilist.AnilistRecentEpisode;
import org.jordijaspers.aniflix.api.consumet.service.ConsumetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimeService.class);

    private final ConsumetService consumetService;

    private final AnimeRepository animeRepository;

    public List<Anime> searchAnime(final Map<String, String> filters) {
        return consumetService.searchAnime(filters);
    }

    public List<AnilistRecentEpisode> getAnimeOfRecentEpisodes(final int perPage, final int page) {
        return consumetService.getRecentEpisodes(perPage, page);
    }

    public Anime getAnimeBanner() {
        final List<Anime> animeTrailers = getPopularAnime(50, 1)
                .stream()
                .filter(anime -> isNotBlank(anime.getTrailerUrl()))
                .toList();

        return animeTrailers.get((int) (Math.random() * animeTrailers.size()));
    }

    public List<Anime> getPopularAnime(final int perPage, final int page) {
        return consumetService.getPopular(perPage, page);
    }

    public List<Anime> getTrendingAnime(final int perPage, final int page) {
        return consumetService.getTrending(perPage, page);
    }

    public List<Anime> getAnimeByGenre(final Genres genre, final int perPage, final int page) {
        return consumetService.getByGenre(genre, perPage, page);
    }

    public Anime findAnimeByTitle(final String title) {
        if (isBlank(title)) {
            LOGGER.warn("Cannot look up a blank anime title.");
            return null;
        }

        LOGGER.info("Attempting to look up anime with title '{}'", title);
        return animeRepository.findByTitle(title)
                .map(this::updateAnimeInfo)
                .orElseGet(() -> {
                    try {
                        return saveAnime(consumetService.getAnimeDetails(title));
                    } catch (final DataNotFoundException exception) {
                        LOGGER.warn("Anime with title '{}' not found.", title);
                        return null;
                    }
                });
    }

    public Anime findByAnilistId(final int anilistId) {
        LOGGER.info("Attempting to look up anime with Anilist ID '{}'", anilistId);
        return animeRepository.findByAnilistId(anilistId)
                .map(this::updateAnimeInfo)
                .orElseGet(() -> saveAnime(consumetService.getAnimeDetails(anilistId)));
    }

    public Anime saveAnime(final Anime anime) {
        LOGGER.info("Anime with title '{}' not yet in the database, attempting to save it.", anime.getTitle());

        // Detach episodes from the anime temporarily
        final Set<Episode> episodes = anime.getEpisodes();
        anime.setEpisodes(new HashSet<>());

        // Save the anime without episodes first
        final Anime preSave = animeRepository.save(anime);

        // Reattach episodes and set their anime reference
        episodes.forEach(episode -> {
            episode.setAnime(preSave);
            preSave.getEpisodes().add(episode);
        });

        // Save the anime with episodes
        return animeRepository.save(preSave);
    }

    public Anime updateAnimeInfo(final Anime anime) {
        if (!anime.isCompleted()) {
            synchronizeData(anime);
        }
        return anime;
    }

    //TODO: fix async
    @Async("anime.synchronize")
    protected void synchronizeData(final Anime anime) {
        LOGGER.info("Synchronizing consumet data with the database for id '{}'", anime.getAnilistId());
        final Anime updatedInfo = consumetService.getAnimeDetails(anime.getAnilistId());
        // Set the Genre from the old anime to the updated anime
        updatedInfo.setGenres(anime.getGenres());

        // Transfer episode IDs from the old anime to the updated one
        final Set<Episode> oldEpisodes = anime.getEpisodes();
        final Set<Episode> updatedEpisodes = updatedInfo.getEpisodes();

        // Assuming that episodes are uniquely identified by some identifier, for example, an episode number
        final Map<String, Episode> episodeMap = new ConcurrentHashMap<>();
        oldEpisodes.forEach(episode -> episodeMap.put(episode.getUrl(), episode));

        // Update the IDs in the updated episodes
        updatedEpisodes.forEach(updatedEpisode -> {
            final Episode oldEpisode = episodeMap.get(updatedEpisode.getUrl());
            updatedEpisode.setAnime(updatedInfo);
            if (nonNull(oldEpisode)) {
                updatedEpisode.setId(oldEpisode.getId());
            }
        });

        // Save the updated anime with episode IDs transferred
        animeRepository.save(updatedInfo);
        LOGGER.info("Synchronization completed for anime with id '{}'", anime.getAnilistId());
    }
}
