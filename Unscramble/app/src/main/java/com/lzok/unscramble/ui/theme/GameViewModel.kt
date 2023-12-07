package com.lzok.unscramble.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lzok.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    var userGuess by mutableStateOf("")
        private set
    private lateinit var currentWord: String

    //    游戏中使用的一组词语
    private var usedWords: MutableSet<String> = mutableSetOf()

    // 游戏界面状态,用于避免其他类更新状态的后备属性
    private val _uiState = MutableStateFlow(GameUiState())

    //    变为只读流
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    private fun shuffleCurrentWord(word: String): String {
//        打乱这个词
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    //    从列表中随机选择一个单词并打乱单词的字母顺序
    private fun pickRandomWordAndShuffle(): String {
        // 继续随机选择一个新单词，直到选到一个之前没有使用过的为止
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord

    }

}