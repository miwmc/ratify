package com.mcm.ratify.ui.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.mcm.ratify.R
import com.mcm.ratify.ui.navigation.TopNavigationBar

@Composable
fun HelpScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopNavigationBar(navController = navController)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.help_label),
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin " +
                            "ornare diam at tincidunt tincidunt. Ut convallis varius justo, ut " +
                            "gravida orci lacinia ut. Nam at justo nibh. Duis tempor augue ut " +
                            "suscipit convallis. Duis commodo lectus sed condimentum dapibus. " +
                            "Morbi venenatis nec nisl vel aliquet. Integer ac mauris efficitur " +
                            "sem consequat condimentum. Suspendisse nec euismod tellus, eu porta " +
                            "dolor.\n" +
                            "\n" +
                            "Ut lacinia, magna sit amet aliquet aliquam, ipsum nisi vehicula " +
                            "urna, quis convallis lectus dolor a nisl. Fusce luctus tellus eros, " +
                            "sed dictum eros tristique eget. Nam quis volutpat dolor. Nam quis " +
                            "nisi non nisl blandit laoreet quis at orci. Donec facilisis nisl " +
                            "lacus. Integer commodo finibus lectus, eu mollis ante fringilla " +
                            "vitae. Nunc sit amet sem elementum, fermentum nisi eu, hendrerit" +
                            " nunc. Sed ac lobortis velit. Mauris placerat venenatis lorem eget " +
                            "aliquam. Fusce pulvinar leo sed faucibus porta. Fusce porta mi ut dui iaculis, ut malesuada purus commodo. Aenean auctor, nisl a porta varius, odio arcu commodo nisl, quis blandit elit tortor vulputate nisl. Integer elementum odio bibendum augue congue auctor. Sed convallis vehicula massa at ornare. Aenean at metus non urna pretium pharetra sed eu eros. Nullam porta, mauris id scelerisque imperdiet, arcu sapien tempor nisi, non hendrerit mi metus eu nunc.",
                textAlign = TextAlign.Justify
                )
            }
        }
    }
}