package com.levrost.sillycat

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.levrost.sillycat.ui.theme.SillycatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SillycatTheme {
                // Вызываем только один корневой Composable
                ImageWithNoseTap()
            }
        }
    }
}

@Composable
fun ImageWithNoseTap() {
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.meow) }

    // Задаём границы прямоугольника вокруг носа кота
    val noseLeft   = 405f
    val noseTop    = 1089f
    val noseRight  = 539f
    val noseBottom = 1349f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset: Offset ->
                    if (offset.x in noseLeft..noseRight &&
                        offset.y in noseTop..noseBottom) {
                        mediaPlayer.start()
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.unnamed),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}  // ← Не забываем закрыть функцию ImageWithNoseTap!

@Preview
@Composable
fun CombinedPreview() {
    SillycatTheme {
        // Для превью выводим оба экрана в колонке

            ImageWithNoseTap()

    }
}
