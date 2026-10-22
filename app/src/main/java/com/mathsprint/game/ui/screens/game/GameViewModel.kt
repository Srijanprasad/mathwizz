package com.mathsprint.game.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathsprint.game.data.repository.GameRepository
import com.mathsprint.game.domain.engine.DailyChallengeGenerator
import com.mathsprint.game.domain.engine.MathEngine
import com.mathsprint.game.domain.engine.ScoringEngine
import com.mathsprint.game.domain.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class GameUiState(
    val session: GameSession? = null,
    val isEvaluatingAnswer: Boolean = false,
    val isPaused: Boolean = false,
    val summary: RoundSummary? = null,
    val remainingSeconds: Int = 60,
    val totalSeconds: Int = 60
)

class GameViewModel(
    private val repository: GameRepository,
    private val mathEngine: MathEngine = MathEngine()
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var questionStartTimeMs: Long = System.currentTimeMillis()
    private var timerJob: Job? = null

    fun startGame(
        mode: GameMode,
        category: QuestionCategory,
        difficulty: DifficultyLevel
    ) {
        val questions = when (mode) {
            GameMode.DAILY_CHALLENGE -> DailyChallengeGenerator.generateDailyQuestions()
            GameMode.TIME_ATTACK -> mathEngine.generateBatch(category, difficulty, 50)
            GameMode.LEVEL_PROGRESSION -> mathEngine.generateBatch(category, difficulty, 10)
            GameMode.QUICK_PLAY, GameMode.PRACTICE -> mathEngine.generateBatch(category, difficulty, 10)
        }

        val totalTime = if (mode == GameMode.TIME_ATTACK) 60 else (questions.size * difficulty.defaultTimeLimitSeconds)

        val session = GameSession(
            sessionId = UUID.randomUUID().toString(),
            mode = mode,
            category = category,
            difficulty = difficulty,
            questions = questions,
            remainingTimeSeconds = totalTime
        )

        _uiState.update {
            it.copy(
                session = session,
                isEvaluatingAnswer = false,
                isPaused = false,
                summary = null,
                remainingSeconds = totalTime,
                totalSeconds = totalTime
            )
        }

        questionStartTimeMs = System.currentTimeMillis()

        if (mode.isTimed) {
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (!_uiState.value.isPaused && _uiState.value.session?.isFinished == false) {
                    val currentRemaining = _uiState.value.remainingSeconds - 1
                    if (currentRemaining <= 0) {
                        _uiState.update { it.copy(remainingSeconds = 0) }
                        finishGame()
                        break
                    } else {
                        _uiState.update { it.copy(remainingSeconds = currentRemaining) }
                    }
                }
            }
        }
    }

    fun selectAnswer(selectedOption: AnswerOption) {
        val currentSession = _uiState.value.session ?: return
        if (_uiState.value.isEvaluatingAnswer || currentSession.isFinished || _uiState.value.isPaused) return

        val question = currentSession.currentQuestion ?: return
        val responseTime = System.currentTimeMillis() - questionStartTimeMs
        val isCorrect = selectedOption.isCorrect

        // Score & combo calculation
        val eval = ScoringEngine.evaluateAnswer(
            isCorrect = isCorrect,
            currentScore = currentSession.score,
            currentStreak = currentSession.currentStreak,
            difficulty = currentSession.difficulty,
            responseTimeMs = responseTime
        )

        val maxStreak = maxOf(currentSession.maxStreak, eval.newStreak)

        // Mark options visually with non-color and color feedback
        val updatedOptions = question.options.map { opt ->
            when {
                opt.id == selectedOption.id && isCorrect -> opt.copy(state = AnswerState.CORRECT)
                opt.id == selectedOption.id && !isCorrect -> opt.copy(state = AnswerState.INCORRECT)
                opt.isCorrect && !isCorrect -> opt.copy(state = AnswerState.CORRECT) // reveal correct
                else -> opt.copy(state = AnswerState.DEFAULT)
            }
        }

        val updatedQuestion = question.copy(options = updatedOptions)
        val updatedQuestions = currentSession.questions.toMutableList().apply {
            set(currentSession.currentIndex, updatedQuestion)
        }

        val record = RecordedAnswer(
            questionId = question.id,
            expression = question.expression,
            selectedAnswer = selectedOption.text,
            correctAnswer = question.correctAnswerText,
            isCorrect = isCorrect,
            responseTimeMs = responseTime
        )

        val updatedAnswers = currentSession.answers + record

        val updatedSession = currentSession.copy(
            questions = updatedQuestions,
            score = eval.newScore,
            currentStreak = eval.newStreak,
            maxStreak = maxStreak,
            answers = updatedAnswers
        )

        _uiState.update {
            it.copy(
                session = updatedSession,
                isEvaluatingAnswer = true
            )
        }

        // Brief delay before advancing to maintain smooth flow state without blocking
        viewModelScope.launch {
            delay(300)
            val nextIndex = currentSession.currentIndex + 1
            if (nextIndex >= currentSession.questions.size && currentSession.mode != GameMode.TIME_ATTACK) {
                finishGame()
            } else if (currentSession.mode == GameMode.TIME_ATTACK && nextIndex >= currentSession.questions.size) {
                // Generate more questions on the fly for time attack if player answers all 50
                val extra = mathEngine.generateBatch(currentSession.category, currentSession.difficulty, 20)
                _uiState.update { state ->
                    state.copy(
                        session = state.session?.copy(
                            questions = state.session.questions + extra,
                            currentIndex = nextIndex
                        ),
                        isEvaluatingAnswer = false
                    )
                }
                questionStartTimeMs = System.currentTimeMillis()
            } else {
                _uiState.update { state ->
                    state.copy(
                        session = state.session?.copy(currentIndex = nextIndex),
                        isEvaluatingAnswer = false
                    )
                }
                questionStartTimeMs = System.currentTimeMillis()
            }
        }
    }

    private fun finishGame() {
        timerJob?.cancel()
        val session = _uiState.value.session ?: return
        val durationSeconds = ((System.currentTimeMillis() - session.startTimeMs) / 1000).coerceAtLeast(1)
        val totalQuestions = session.answers.size
        val correctCount = session.answers.count { it.isCorrect }
        val accuracy = if (totalQuestions == 0) 0 else ((correctCount * 100) / totalQuestions)

        val summary = RoundSummary(
            sessionId = session.sessionId,
            mode = session.mode,
            category = session.category,
            difficulty = session.difficulty,
            totalQuestions = totalQuestions,
            correctCount = correctCount,
            accuracyPercent = accuracy,
            totalScore = session.score,
            maxStreak = session.maxStreak,
            durationSeconds = durationSeconds,
            answers = session.answers
        )

        _uiState.update {
            it.copy(
                session = session.copy(isFinished = true),
                summary = summary,
                isEvaluatingAnswer = false
            )
        }

        // Persist session to local Room database
        viewModelScope.launch {
            repository.saveSession(summary)
            if (session.mode == GameMode.DAILY_CHALLENGE) {
                repository.recordDailyChallengeCompletion(
                    dateKey = DailyChallengeGenerator.getTodayDateKey(),
                    score = summary.totalScore,
                    accuracyPercent = summary.accuracyPercent
                )
            }
        }
    }

    fun pauseGame() {
        _uiState.update { it.copy(isPaused = true) }
    }

    fun resumeGame() {
        questionStartTimeMs = System.currentTimeMillis()
        _uiState.update { it.copy(isPaused = false) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
