package com.example.beep.data.sample

import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.data.dto.mypage.PresetResponse

val receivedMessages = listOf(
    MessageResponse(
        0,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        1,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        2,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        3,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        4,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        5,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        6,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        7,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        8,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        9,
        "audioUri",
        "receivedcont",
        "01000000000",
        "01011111111",
        "01000000000",
        "yyyymmdd",
        "tag",
        1
    ),
)

val sentMessages = listOf(
    MessageResponse(
        0,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        1,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        2,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        3,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        4,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        5,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        6,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        7,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        8,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
    MessageResponse(
        9,
        "audioUri",
        "sentcontenten",
        "01000000000",
        "01011111111",
        "01011111111",
        "yyyymmdd",
        "tag",
        1
    ),
)


// part = Int? 우선 0 = 연락처, 1 = 메시지로 만들어둠
val contactPresetList = mapOf<Long, PresetResponse>(
    0L to PresetResponse(0, 0, 0, 0, "010-0000-0000"),
    1L to PresetResponse(1, 0, 1, 0, "010-0000-0000"),
    2L to PresetResponse(2, 0, 2, 0, "010-0000-0000"),
    3L to PresetResponse(3, 0, 3, 0, "010-0000-0000"),
    4L to PresetResponse(4, 0, 4, 0, "010-0000-0000"),
    5L to PresetResponse(5, 0, 5, 0, "010-0000-0000"),
    // 6번 프리셋 설정 안한 경우
//    6L to PresetResponse(6, 0, 6, 0, "010-0000-0000"),
    7L to PresetResponse(7, 0, 7, 0, "010-0000-0000"),
    8L to PresetResponse(8, 0, 8, 0, "010-0000-0000"),
    9L to PresetResponse(9, 0, 9, 0, "010-0000-0000"),
)

val messagePresetList = mapOf<Long, PresetResponse>(
    0L to PresetResponse(0, 0, 0, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    1L to PresetResponse(1, 0, 1, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    2L to PresetResponse(2, 0, 2, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    3L to PresetResponse(3, 0, 3, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    4L to PresetResponse(4, 0, 4, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    5L to PresetResponse(5, 0, 5, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    6L to PresetResponse(6, 0, 6, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    7L to PresetResponse(7, 0, 7, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    8L to PresetResponse(8, 0, 8, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
    9L to PresetResponse(9, 0, 9, 1, "ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍ"),
)