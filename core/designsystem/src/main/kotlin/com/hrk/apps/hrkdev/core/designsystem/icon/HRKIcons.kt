package com.hrk.apps.hrkdev.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ShortText
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Upcoming
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Grid3x3
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material.icons.rounded.ViewDay
import androidx.compose.ui.graphics.vector.ImageVector
import com.hrk.apps.hrkdev.core.designsystem.R

object HRKIcons {
    val AddIcon = Icon.DrawableResourceIcon(R.drawable.ic_plus)
    val SearchIcon = Icon.DrawableResourceIcon(R.drawable.ic_search)
    val InfoIcon = Icon.DrawableResourceIcon(R.drawable.ic_info)
    val CloseIcon = Icon.DrawableResourceIcon(R.drawable.ic_close)
    val BlackIcon = Icon.DrawableResourceIcon(R.drawable.ic_arrow_back)
    val ArrowLeftIcon = Icon.DrawableResourceIcon(R.drawable.ic_arrow_left)
    val SaveIcon = Icon.DrawableResourceIcon(R.drawable.ic_save)
    val BackgroundApp = Icon.DrawableResourceIcon(R.drawable.background_app)
    val EmptyMusic = Icon.DrawableResourceIcon(R.drawable.ic_empty)
    val Logo = Icon.DrawableResourceIcon(R.drawable.logo)

    //default
    val Add = Icons.Rounded.Add
    val ArrowBack = Icons.AutoMirrored.Rounded.ArrowBack
    val Bookmark = Icons.Rounded.Bookmark
    val BookmarkBorder = Icons.Rounded.BookmarkBorder
    val Bookmarks = Icons.Rounded.Bookmarks
    val BookmarksBorder = Icons.Outlined.Bookmarks
    val Check = Icons.Rounded.Check
    val Close = Icons.Rounded.Close
    val Grid3x3 = Icons.Rounded.Grid3x3
    val MoreVert = Icons.Default.MoreVert
    val Person = Icons.Rounded.Person
    val Search = Icons.Rounded.Search
    val Settings = Icons.Rounded.Settings
    val ShortText = Icons.AutoMirrored.Rounded.ShortText
    val Upcoming = Icons.Rounded.Upcoming
    val UpcomingBorder = Icons.Outlined.Upcoming
    val ViewDay = Icons.Rounded.ViewDay
}

sealed interface Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon
    data class DrawableResourceIcon(@DrawableRes val resourceId: Int) : Icon
}
