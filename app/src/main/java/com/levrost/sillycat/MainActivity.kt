package com.levrost.sillycat

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.levrost.sillycat.ui.theme.SillycatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SillycatTheme {
                ImageWithNoseTap()
            }
        }
    }
}

@Composable
fun ImageWithNoseTap() {
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(5).build()
    }
    val meowId = remember { soundPool.load(context, R.raw.meow, 1) }
    val noseLeftRatio = 0.375f
    val noseRightRatio = 0.499f
    val noseTopRatio = 0.449f
    val noseBottomRatio = 0.556f
    var imageWidth by remember { mutableFloatStateOf(0f) }
    var imageHeight by remember { mutableFloatStateOf(0f) }
    Log.d("CAT", "imageWidth=$imageWidth imageHeight=$imageHeight")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset: Offset ->
                    val noseLeft = imageWidth * noseLeftRatio
                    val noseRight = imageWidth * noseRightRatio
                    val noseTop = imageHeight * noseTopRatio
                    val noseBottom = imageHeight * noseBottomRatio
                    if (offset.x in noseLeft..noseRight &&
                        offset.y in noseTop..noseBottom) {
                        soundPool.play(meowId, 1f,
                            1f, 1, 0, 1f)
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(R.drawable.unnamed),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
                .onGloballyPositioned { cords ->
                    imageWidth = cords.size.width.toFloat()
                    imageHeight = cords.size.height.toFloat()
                    Log.d("CAT", "width=$imageWidth height=$imageHeight")
                },
            contentScale = ContentScale.FillBounds
        )
    }
}