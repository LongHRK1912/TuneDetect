package com.hrk.apps.hrkdev.core.model.spotify

data class SearchSpotifyResponse(
    val tracks: Tracks? = null
)

data class Tracks(
    val href: String? = null,
    val items: List<TracksItem>? = null,
    val limit: Int? = null,
    val next: String? = null,
    val offset: Int? = null,
    val previous: Any? = null,
    val total: Int? = null
)

data class TracksItem(
    val album: Album? = null,
    val artists: List<ArtistX>? = null,
    val available_markets: List<String>? = null,
    val disc_number: Int? = null,
    val duration_ms: Int? = null,
    val explicit: Boolean? = null,
    val external_ids: ExternalIds? = null,
    val external_urls: ExternalUrlsXXX? = null,
    val href: String? = null,
    val id: String? = null,
    val is_local: Boolean? = null,
    val is_playable: Boolean? = null,
    val name: String? = null,
    val popularity: Int? = null,
    val preview_url: Any? = null,
    val track_number: Int? = null,
    val type: String? = null,
    val uri: String? = null
)

data class Album(
    val album_type: String? = null,
    val artists: List<ArtistX>? = null,
    val available_markets: List<String>? = null,
    val external_urls: ExternalUrlsXXX? = null,
    val href: String? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val is_playable: Boolean? = null,
    val name: String? = null,
    val release_date: String? = null,
    val release_date_precision: String? = null,
    val total_tracks: Int? = null,
    val type: String? = null,
    val uri: String? = null
)

data class ArtistX(
    val external_urls: ExternalUrlsXXX? = null,
    val href: String? = null,
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val uri: String? = null
)

data class ExternalIds(
    val isrc: String? = null
)

data class ExternalUrlsXXX(
    val spotify: String? = null
)

data class Image(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)