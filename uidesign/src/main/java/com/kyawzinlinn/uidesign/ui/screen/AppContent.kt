@file:OptIn(ExperimentalMaterial3Api::class)

package com.kyawzinlinn.uidesign.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.uidesign.R
import com.kyawzinlinn.uidesign.datasource.DataSource
import com.kyawzinlinn.uidesign.model.Category
import com.kyawzinlinn.uidesign.ui.components.CarouselSlider
import kotlinx.coroutines.launch

@Composable
fun AppContent(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }


    Scaffold(modifier = modifier, topBar = {
        TopBarLayout(modifier = Modifier.padding(16.dp))
    }, bottomBar = { BottomNavigation() }) {
        Column(
            modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
            CarouselSlider(
                itemCount = DataSource.images.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                onItemClick = {
                    showBottomSheet = true
                }
            ) {
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(DataSource.images.get(it)),
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(24.dp))
            CategoriesList()
            Spacer(Modifier.height(24.dp))
            EventList()
            Spacer(Modifier.height(24.dp))
            UpcomingShowList()
            if (showBottomSheet) {
                DetailBottomSheet(
                    onDismiss = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
fun UpcomingShowList(
    modifier: Modifier = Modifier.height(200.dp)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Upcoming Shows",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "See all",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
    }
    Spacer(Modifier.height(8.dp))
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(DataSource.upcomingShows) {
            Card(modifier = Modifier
                .height(150.dp)
                .width(200.dp)) {
                Box(modifier = Modifier) {
                    Image(
                        painter = painterResource(it.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = size.height / 3,
                                endY = size.height
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        }
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(
                                    RoundedCornerShape(4.dp)
                                )
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.schedule),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = it.time,
                                color = Color.Black,
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                        Text(
                            text = it.caption,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(DataSource.bottomNavigationItems) {
            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(it.icon),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(it.title)
            }
        }
    }
}

@Composable
fun EventList(modifier: Modifier = Modifier.height(150.dp)) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(DataSource.events) { event ->
            Card(
                modifier = Modifier,
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Icon(
                            painter = painterResource(event.icon),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = event.description
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = event.status,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesList(
    modifier: Modifier = Modifier.height(240.dp)
) {
    val categories = listOf(
        Category("Map", R.drawable.group_342),
        Category("Inhabitants", R.drawable.group_342_2),
        Category("Shows", R.drawable.group_341),
        Category("Shopping", R.drawable.group_275),
        Category("Dine", R.drawable.groupg_310),
        Category("Meet & Grets", R.drawable.layer_1),
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(it.image),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = it.title,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun TopBarLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(painter = painterResource(R.drawable.chevron_left), contentDescription = null)
        }
        Image(
            painter = painterResource(R.drawable.image_3),
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
            modifier = Modifier.height(52.dp)
        )
        Image(
            painter = painterResource(R.drawable.leading_icon),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
            modifier = Modifier.width(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppContentPreview() {
    AppContent()
}