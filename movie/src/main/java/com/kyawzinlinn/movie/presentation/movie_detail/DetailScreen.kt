package com.kyawzinlinn.movie.presentation.movie_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kyawzinlinn.movie.domain.model.MovieDetailResponse
import com.kyawzinlinn.movie.presentation.home_screen.HomeViewModel
import com.kyawzinlinn.movie.presentation.home_screen.Loading
import com.kyawzinlinn.movie.utils.IMG_URL_PREFIX_500
import com.kyawzinlinn.movie.utils.Resource
import okhttp3.internal.immutableListOf

@Composable
fun DetailScreen(
    viewModel: HomeViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val uiState by viewModel.uiState.collectAsState()
    val movieDetailsState = uiState.movieDetails.collectAsState(Resource.Loading).value

    Column(modifier.padding(16.dp)) {
        when (movieDetailsState) {
            is Resource.Loading -> Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Loading()
            }
            is Resource.Error -> Text(text = movieDetailsState.message)
            is Resource.Success -> MovieDetailsContent(movieDetailResponse = movieDetailsState.data, navigateBack = navigateBack)
        }
    }
}

@Composable
fun MovieDetailsContent(
    navigateBack: () -> Unit,
    movieDetailResponse: MovieDetailResponse,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    Column(modifier) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${IMG_URL_PREFIX_500}/${movieDetailResponse.backdrop_path}")
                    .crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            IconButton(onClick = navigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "${movieDetailResponse.title} (${movieDetailResponse.release_date})",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Rating - ${movieDetailResponse.vote_average.toString()}",
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = movieDetailResponse.overview
        )
    }
}