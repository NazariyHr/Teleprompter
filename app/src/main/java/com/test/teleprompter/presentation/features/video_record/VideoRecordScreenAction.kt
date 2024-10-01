package com.test.teleprompter.presentation.features.video_record

sealed class VideoRecordScreenAction {
    data object OnScenariosListClicked : VideoRecordScreenAction()
}