package com.droidproger.byedpilight.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.droidproger.byedpilight.BuildConfig
import com.droidproger.byedpilight.R
import com.droidproger.byedpilight.dataModel
import com.droidproger.byedpilight.ui.theme.ComposeAppTheme

@Composable
fun AboutScreen(){
    Dialog(onDismissRequest = { dataModel.showAbout = false}) {
        val version = BuildConfig.VERSION_NAME
        val build = BuildConfig.VERSION_CODE
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically),//.height(200.dp),
            shape = RoundedCornerShape(16.dp),
        )
        {
            Text(
                text = stringResource(R.string.aboutText),
                Modifier
                    .padding(start = 10.dp ,top = 20.dp, end = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                , textAlign = TextAlign.Center
                )
            HorizontalDivider(
                Modifier
                    .padding(15.dp),
                thickness = 2.dp)
            Text(
                buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            stringResource(R.string.byedpiLink),
                            TextLinkStyles(style = SpanStyle(color = Color.Blue)),)
                        )
                    {
                        append(stringResource(R.string.byedpi))
                    }
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                , textAlign = TextAlign.Center
            )
            Text(
                buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            stringResource(R.string.byedpiAndroidLink),
                            TextLinkStyles(style = SpanStyle(color = Color.Blue)),)
                    )
                    {
                        append(stringResource(R.string.byedpiAndroid))
                    }
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                , textAlign = TextAlign.Center
            )
            Text(
                buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            stringResource(R.string.hevsocks5Link),
                            TextLinkStyles(style = SpanStyle(color = Color.Blue)),)
                    )
                    {
                        append(stringResource(R.string.hevsocks5))
                    }
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                , textAlign = TextAlign.Center
            )
            Text(
                buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            stringResource(R.string.thisAppLink),
                            TextLinkStyles(style = SpanStyle(color = Color.Blue)),)
                    )
                    {
                        append(stringResource(R.string.thisApp))
                    }
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentSize()
                , textAlign = TextAlign.Center
            )
            HorizontalDivider(
                Modifier
                    .padding(15.dp),
                thickness = 2.dp)
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(R.string.version),
                    Modifier
                        .padding(10.dp)
                        .wrapContentSize()
                    , textAlign = TextAlign.Center
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    Modifier
                        .padding(10.dp)
                        .wrapContentSize()
                    , textAlign = TextAlign.Center
                )
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(R.string.build),
                    Modifier
                        .padding(10.dp)
                        .wrapContentSize()
                    , textAlign = TextAlign.Center
                )
                Text(
                    text = BuildConfig.VERSION_CODE.toString(),
                    Modifier
                        .padding(10.dp)
                        .wrapContentSize()
                    , textAlign = TextAlign.Center
                )
            }

            TextButton(
                onClick = {
                    dataModel.showAbout = false
                },
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text("Ok")
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun AboutPreview(){
    ComposeAppTheme {
        AboutScreen()
    }
}