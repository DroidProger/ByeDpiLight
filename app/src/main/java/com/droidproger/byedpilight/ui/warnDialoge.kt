package com.droidproger.byedpilight.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.droidproger.byedpilight.R
import com.droidproger.byedpilight.dataModel
import com.droidproger.byedpilight.ui.theme.ComposeAppTheme

@Composable
fun StopServiceDialog() {

    Dialog(onDismissRequest = { dataModel.showWarn = false}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(Icons.Filled.Warning,stringResource(R.string.warning))
                Text(stringResource(R.string.warning))
            }
            Text(
                text = stringResource(R.string.warnText),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    //.fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ){
                TextButton(
                    onClick = {
                        dataModel.showWarn = false
                    },
                    Modifier
                        .padding(10.dp)

                ) {
                    Text("Ok")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogPreview(){
    ComposeAppTheme {
        StopServiceDialog()
    }
}