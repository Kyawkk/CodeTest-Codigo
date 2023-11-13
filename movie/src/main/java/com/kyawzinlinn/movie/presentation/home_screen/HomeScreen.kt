@file:OptIn(ExperimentalMaterial3Api::class)

package com.kyawzinlinn.movie.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kyawzinlinn.movie.R
import com.kyawzinlinn.movie.data.local.entitiy.MovieEntity
import com.kyawzinlinn.movie.domain.toFavoriteMovieEntity
import com.kyawzinlinn.movie.utils.IMG_URL_PREFIX_300
import com.kyawzinlinn.movie.utils.MovieType
import com.kyawzinlinn.movie.utils.Resource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val popularMoviesState =
        uiState.popularMovies.collectAsState(initial = Resource.Loading).value
    val upComingMoviesState =
        uiState.upcomingMovies.collectAsState(initial = Resource.Loading).value

    Column(modifier = modifier) {
        Text(
            text = "Popular",
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        when (popularMoviesState) {
            is Resource.Loading -> Loading()
            is Resource.Success -> MovieListLayout(
                popularMoviesState.data,
                onSaved = { isFavorite, movie ->
                    viewModel.toggleFavorite(
                        isFavorite,
                        movie.toFavoriteMovieEntity(MovieType.Popular),
                        MovieType.Popular
                    )
                },
                onMovieItemClick = onMovieItemClick
            )

            is Resource.Error -> Text(popularMoviesState.message)
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Upcoming",
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        when (upComingMoviesState) {
            is Resource.Loading -> Loading()
            is Resource.Success -> MovieListLayout(
                upComingMoviesState.data,
                onSaved = { isFavorite, movie ->
                    viewModel.toggleFavorite(
                        isFavorite,
                        movie.toFavoriteMovieEntity(MovieType.Upcoming),
                        MovieType.Upcoming
                    )
                },
                onMovieItemClick = onMovieItemClick
            )

            is Resource.Error -> Text(upComingMoviesState.message)
        }
    }
}

@Composable
fun MovieListLayout(
    movies: List<MovieEntity>,
    onMovieItemClick: (String) -> Unit,
    onSaved: (Boolean, MovieEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(12.dp)
    ) {
        items(movies) {
            MovieItem(
                movieEntity = it,
                onSaved = onSaved,
                onItemClick = onMovieItemClick
            )
        }
    }
}

@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = modifier.size(40.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun MovieItem(
    movieEntity: MovieEntity,
    onItemClick: (String) -> Unit,
    onSaved: (Boolean, MovieEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by rememberSaveable { mutableStateOf(movieEntity.favorite) }
    Card(modifier = modifier
        .width(140.dp)
        .height(180.dp),
        onClick = { onItemClick(movieEntity.id.toString()) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("$IMG_URL_PREFIX_300${movieEntity.backdrop_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movieEntity.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            IconButton(onClick = {
                selected = !selected
                onSaved(selected, movieEntity)
            }) {
                Icon(
                    painter = painterResource(if (selected) R.drawable.bookmark_filled else R.drawable.bookmark_24px),
                    contentDescription = null
                )
            }
        }
    }
}