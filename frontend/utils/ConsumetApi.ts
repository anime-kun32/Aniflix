const BASE_URL = process.env.NEXT_PUBLIC_CONSUMET_BASE_URL

const ConsumetApi = {
  fetchRecentEpisodes: `${BASE_URL}/anime/gogoanime/recent-episodes?type=1`,
  fetchRecentEpisodesDub: `${BASE_URL}/anime/gogoanime/recent-episodes?type=3`,
  fetchRandomAnime: `${BASE_URL}/meta/anilist/random-anime`,
  fetchPopularAnime: `${BASE_URL}/meta/anilist/popular`,
  fetchTrending: `${BASE_URL}/meta/anilist/trending`,
  fetchGenre: `${BASE_URL}/meta/anilist/advanced-search?sort=["TRENDING", "SCORE_DESC"]&genres=["{genre}"]&type=ANIME&format=TV`,

  searchAnime: `${BASE_URL}/meta/anilist/{query}`,
  fetchAnimeDetails: `${BASE_URL}/meta/anilist/info/{id}?provider=gogoanime`
}

export default ConsumetApi
