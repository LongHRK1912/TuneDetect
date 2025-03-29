package com.hrk.apps.hrkdev.core.model.iacr_cloud

val Empty_Result = listOf(1001,0)

data class ACRCloudResponse(
    val metadata: Metadata? = null,
    val result_type: Int? = null,
    val status: Status? = null
)

data class Metadata(
    val custom_files: List<CustomFile>? = null,
    val music: List<Music>? = null,
    val timestamp_utc: String? = null
)

data class Status(
    val code: Int? = null,
    val msg: String? = null,
    val version: String? = null
)

data class CustomFile(
    val acrid: String? = null,
    val bucket_id: Int? = null,
    val db_begin_time_offset_ms: Int? = null,
    val db_end_time_offset_ms: Int? = null,
    val duration_ms: Int? = null,
    val key1: String? = null,
    val key2: String? = null,
    val sample_begin_time_offset_ms: Int? = null,
    val sample_end_time_offset_ms: Int? = null,
    val title: String? = null
)

data class Music(
    val acrid: String? = null,
    val album: Album? = null,
    val artists: List<Artist>? = null,
    val contributors: Contributors? = null,
    val db_begin_time_offset_ms: Int? = null,
    val db_end_time_offset_ms: Int? = null,
    val duration_ms: Int? = null,
    val external_ids: ExternalIds? = null,
    val external_metadata: ExternalMetadata? = null,
    val genres: List<Genre>? = null,
    val label: String? = null,
    val langs: List<LangXX>? = null,
    val language: String? = null,
    val lyrics: Lyrics? = null,
    val play_offset_ms: Int? = null,
    val release_by_territories: List<ReleaseByTerritory>? = null,
    val release_date: String? = null,
    val result_from: Int? = null,
    val rights_claim: List<RightsClaim>? = null,
    val sample_begin_time_offset_ms: Int? = null,
    val sample_end_time_offset_ms: Int? = null,
    val score: Int? = null,
    val title: String? = null
)

data class Album(
    val langs: List<LangXX>? = null,
    val name: String? = null
)

data class Artist(
    val langs: List<LangXX>? = null,
    val name: String? = null
)

data class Contributors(
    val composers: List<String>? = null,
    val lyricists: List<String>? = null
)

data class ExternalIds(
    val isrc: String? = null,
    val iswc: String? = null,
    val upc: String? = null
)

data class ExternalMetadata(
    val deezer: Deezer? = null,
    val musicbrainz: List<Musicbrainz>? = null,
    val musicstory: Musicstory? = null,
    val spotify: Spotify? = null,
    val youtube: Youtube? = null
)

data class Genre(
    val name: String? = null
)

data class LangXX(
    val code: String? = null,
    val name: String? = null
)

data class Lyrics(
    val copyrights: List<String>? = null
)

data class ReleaseByTerritory(
    val release_date: String? = null,
    val territories: List<String>? = null
)

data class RightsClaim(
    val distributor: Distributor? = null,
    val rights_claim_policy: String? = null,
    val rights_owners: List<RightsOwner>? = null,
    val territories: List<String>? = null
)

data class Deezer(
    val album: AlbumX? = null,
    val artists: List<ArtistX>? = null,
    val track: Track? = null
)

data class Musicbrainz(
    val track: Track? = null
)

data class Musicstory(
    val album: AlbumX? = null,
    val track: Track? = null
)

data class Spotify(
    val album: AlbumX? = null,
    val artists: List<ArtistX>? = null,
    val track: Track? = null
)

data class Youtube(
    val vid: String? = null
)

data class AlbumX(
    val id: String? = null
)

data class ArtistX(
    val id: String? = null
)

data class Track(
    val id: String? = null
)

data class Distributor(
    val id: String? = null,
    val name: String? = null
)

data class RightsOwner(
    val name: String? = null,
    val share_percentage: Double? = null
)