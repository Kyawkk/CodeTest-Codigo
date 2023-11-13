@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.kyawzinlinn.uidesign.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.uidesign.R
import com.kyawzinlinn.uidesign.datasource.DataSource
import com.kyawzinlinn.uidesign.ui.components.CarouselSlider

@ExperimentalMaterial3Api
@Composable
fun DetailBottomSheet(
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CarouselSlider(
                itemCount = DataSource.images.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                onItemClick = {}
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
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "ZONE 1",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Alligator Gar",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.walk), contentDescription = null)
                    Text(
                        text = "410m away",
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Map",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Most freshwater fish live less than 10 years, start reproducing at one or two years old, and produce offspring almost every year. Alligator gar are different. Female alligator gar can live more than 50 years, are capable of reproducing at five to 10 years, and reproduce only a few times each decade in most Texas waters. To successfully reproduce, alligator gar require large, overbank floods during the spring and early summer. Near the peak of these floods, congregations of five to 20 adults leave the river and seek shallow, grassy areas on the floodplain to spawn. Eggs are deposited over the vegetation, and after about 48 hours they hatch. The newly hatched young remain attached to flooded vegetation for another two to four days, after which they drift with receding flood waters. If flood waters recede too quickly, the eggs and larvae may die. "
                )
                Spacer(Modifier.height(32.dp))
            }
        }

    }
}