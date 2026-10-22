package com.mathsprint.game.domain.model

enum class QuestionCategory(
    val displayName: String,
    val symbol: String,
    val description: String,
    val isImplemented: Boolean = true
) {
    ADDITION("Addition", "+", "Master rapid mental sums", true),
    SUBTRACTION("Subtraction", "−", "Quick difference calculations", true),
    MULTIPLICATION("Multiplication", "×", "Times tables & double-digit products", true),
    DIVISION("Division", "÷", "Clean integer quotients without remainders", true),
    FRACTIONS("Fractions", "½", "Fractions addition, subtraction & simplification", true),
    DECIMALS("Decimals", "0.5", "Decimal operations & precision calculations", true),
    PERCENTAGES("Percentages", "%", "Everyday discounts and mental ratios", true),
    MIXED("Mixed Operations", "±×÷", "Combine arithmetic operators dynamically", true);

    companion object {
        fun coreOperations(): List<QuestionCategory> = listOf(
            ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, MIXED
        )
    }
}
