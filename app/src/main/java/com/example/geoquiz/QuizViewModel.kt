package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val cheatedQuestions: MutableMap<Int, Boolean> = mutableMapOf()
    private val answeredQuestions = mutableSetOf<Int>()

    private val questionBank = listOf(
        Question(R.string.question_australia, false),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    var correctCount = 0
        private set
    var questionsAnswered = 0
        private set

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val questionBankSize: Int
        get() = questionBank.size

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
    }

    fun incrementCorrectCount() {
        correctCount++
    }

    fun incrementQuestionsAnswered() {
        questionsAnswered++
    }

    fun isQuizFinished(): Boolean {
        return questionsAnswered == questionBank.size
    }

    fun markCheatedOnCurrentQuestion() {
        cheatedQuestions[currentIndex] = true
    }

    fun hasCheatedOnCurrentQuestion(): Boolean {
        return cheatedQuestions[currentIndex] ?: false
    }

    fun markQuestionAsAnswered() {
        answeredQuestions.add(currentIndex)
    }

    fun isQuestionAnswered(): Boolean {
        return answeredQuestions.contains(currentIndex)
    }


}