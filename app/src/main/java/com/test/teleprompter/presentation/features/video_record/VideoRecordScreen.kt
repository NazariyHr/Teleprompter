package com.test.teleprompter.presentation.features.video_record

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.teleprompter.R
import com.test.teleprompter.presentation.common.theme.MainBgColor
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme
import com.test.teleprompter.presentation.features.video_record.components.CameraPreview
import kotlinx.coroutines.delay

@Composable
fun VideoRecordScreenRoot(
    scenarioId: Int,
    navController: NavController,
    viewModel: VideoRecordViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.saveScenario(scenarioId)
    VideoRecordScreen(
        state = state
    )
}

@SuppressLint("MissingPermission")
@Composable
private fun VideoRecordScreen(
    state: VideoRecordScreenState
) {
    val context = LocalContext.current
    var recording by remember { mutableStateOf<Recording?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }

    var isRecording by remember { mutableStateOf(false) }
    var isUserScrolling by remember { mutableStateOf(false) }

    val d = LocalDensity.current
    val lineWidth by remember(d) {
        derivedStateOf {
            with(d) { 2.dp.toPx() }
        }
    }
    val circleRadius by remember(d) {
        derivedStateOf {
            with(d) { 12.dp.toPx() }
        }
    }
    val maxYOffset by remember(d) {
        derivedStateOf {
            with(d) { (200 - 24).dp.toPx() }
        }
    }
    var yOffset by remember {
        mutableStateOf(0f)
    }
    val speed by remember(yOffset) {
        derivedStateOf {
            val minSpeed = 50L
            val maxSpeed = 1L
            val diff = minSpeed - maxSpeed
            val percent = (yOffset * 100.toFloat() / maxYOffset) / 100
            val s = maxSpeed + (diff - diff * percent)
            Log.d("VideoRecordScreen", "percent: $percent")
            Log.d("VideoRecordScreenSpeed", "speed: $s")
            s
        }
    }

    val scroll = rememberScrollState()
    LaunchedEffect(isRecording, isUserScrolling, speed) {
        while (isRecording && !isUserScrolling) {
            delay(speed.toLong())
            if (scroll.canScrollForward) {
                scroll.scrollTo(scroll.value + 1)
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MainBgColor)
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            // preview
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CameraPreview(
                    modifier = Modifier.alpha(0f),
                    onVideoCapture = {
                        videoCapture = it
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //content
                if (state.scenario != null) {
                    Text(
                        text = state.scenario.title,
                        fontWeight = FontWeight.Bold
                    )

                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .pointerInput(true) {
                                    awaitEachGesture {
                                        awaitFirstDown()
                                        isUserScrolling = true

                                        do {
                                            val event: PointerEvent = awaitPointerEvent()
                                            // ACTION_MOVE loop
                                        } while (event.changes.any { it.pressed })
                                        isUserScrolling = false

                                        // ACTION_UP is here
                                    }
                                }
                                .verticalScroll(scroll)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.scenario.text
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (!isRecording) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    recording?.stop()
                                    val contentValues = ContentValues()
                                    contentValues.put(
                                        MediaStore.MediaColumns.DISPLAY_NAME,
                                        "Teleprompter_video_${System.currentTimeMillis()}"
                                    )
                                    contentValues.put(
                                        MediaStore.MediaColumns.MIME_TYPE,
                                        "video/mp4"
                                    )
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        contentValues.put(
                                            MediaStore.Video.Media.RELATIVE_PATH,
                                            "Movies/Teleprompter"
                                        )
                                    }
                                    val options =
                                        MediaStoreOutputOptions
                                            .Builder(
                                                context.contentResolver,
                                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                            )
                                            .setContentValues(contentValues)
                                            .build()

                                    recording = videoCapture
                                        ?.output
                                        ?.prepareRecording(context, options)
                                        ?.withAudioEnabled()
                                        ?.start(ContextCompat.getMainExecutor(context)) { recordEvent ->
                                            when (recordEvent) {
                                                is VideoRecordEvent.Start -> {
                                                    //recording started
                                                    isRecording = true
                                                }

                                                is VideoRecordEvent.Finalize -> {
                                                    isRecording = false
                                                    // recording ended
                                                    if (recordEvent.hasError()) {
                                                        recording?.close()
                                                        recording = null
                                                        Log.e(
                                                            "CameraPreview",
                                                            "Video capture ends with error: ${recordEvent.error}"
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                }
                            ) {
                                Text(text = stringResource(id = R.string.start))
                            }
                        }
                        if (isRecording) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    recording?.stop()
                                }
                            ) {
                                Text(text = stringResource(id = R.string.stop))
                            }
                        }
                    }
                }
            }

            if (isRecording) {
                //speed
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.speed),
                        contentDescription = "text speed"
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .width(24.dp)
                            .height(200.dp)
                            .pointerInput(true) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    val nextYOffset = yOffset + dragAmount.y
                                    yOffset = if (nextYOffset < 0f) {
                                        0f
                                    } else if (nextYOffset > maxYOffset) {
                                        maxYOffset
                                    } else {
                                        nextYOffset
                                    }

                                    Log.d(
                                        "pointerInput",
                                        "dragAmount.y: ${dragAmount.y}; yOffset: $yOffset"
                                    )
                                }
                            }
                            .drawBehind {
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(x = size.width / 2, y = 0f),
                                    end = Offset(x = size.width / 2, y = size.height),
                                    cap = StrokeCap.Round,
                                    strokeWidth = lineWidth
                                )

                                drawCircle(
                                    color = Color.Black,
                                    radius = circleRadius,
                                    center = Offset(x = size.width / 2, y = circleRadius + yOffset)
                                )
                            }
                    )
                }
            }

            if (isRecording) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .background(
                            color = Color.Red,
                            shape = CircleShape
                        )
                        .size(10.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    TeleprompterTheme {
        VideoRecordScreen(
            state = VideoRecordScreenState()
        )
    }
}