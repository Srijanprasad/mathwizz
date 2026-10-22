package com.mathsprint.game.domain.model

enum class GameMode(
    val title: String,
    val subtitle: String,
    val defaultQuestionCount: Int,
    val isTimed: Boolean
) {
    QUICK_PLAY(
        title = "Quick Play",
        subtitle = "10 rapid-fire questions to warm up your brain",
        defaultQuestionCount = 10,
        isTimed = false
    ),
    DAILY_CHALLENGE(
        title = "Daily Challenge",
        subtitle = "A consistent puzzle set refreshed every midnight",
        defaultQuestionCount = 10,
        isTimed = true
    ),
    PRACTICE(
        title = "Topic Practice",
        subtitle = "Target specific topics at your own pace",
        defaultQuestionCount = 10,
        isTimed = false
    ),
    TIME_ATTACK(
        title = "Time Attack",
        subtitle = "Race against a 60-second clock to score high combos",
        defaultQuestionCount = 50,
        isTimed = true
    ),
    LEVEL_PROGRESSION(
        title = "Level Journey",
        subtitle = "Unlock skill bands from Novice to Grandmaster",
        defaultQuestionCount = 10,
        isTimed = false
    );
}
