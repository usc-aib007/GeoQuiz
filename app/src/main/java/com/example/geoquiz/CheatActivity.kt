package com.example.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val KEY_ANSWER_SHOWN = "answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false
    private var answerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Set default result in case the user does not click "Show Answer"
        setAnswerShownResult(false)

        if (savedInstanceState != null) {
            answerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false)
            if (answerShown) {
                showAnswer()
                setAnswerShownResult(true)
            }
        }

        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            showAnswer()
            answerShown = true
            setAnswerShownResult(true)
        }

        if (answerShown) {
            showAnswer()
            setAnswerShownResult(true)
        }
    }

    private fun showAnswer() {
        answerTextView.text = getString(if (answerIsTrue) R.string.true_button else R.string.false_button)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, answerShown)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply { putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown) }
        setResult(RESULT_OK, data)
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
        private const val KEY_ANSWER_SHOWN = "answer_shown"

        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
