package cdn.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import cdn.mycalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastResult: String = ""
    private var isResultJustCalculated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNumberButtons()
        setupOperatorButtons()
        setupActionButtons()
    }

    private fun setupNumberButtons() {
        binding.num0.setOnClickListener { appendVal("0", false) }
        binding.num1.setOnClickListener { appendVal("1", false) }
        binding.num2.setOnClickListener { appendVal("2", false) }
        binding.num3.setOnClickListener { appendVal("3", false) }
        binding.num4.setOnClickListener { appendVal("4", false) }
        binding.num5.setOnClickListener { appendVal("5", false) }
        binding.num6.setOnClickListener { appendVal("6", false) }
        binding.num7.setOnClickListener { appendVal("7", false) }
        binding.num8.setOnClickListener { appendVal("8", false) }
        binding.num9.setOnClickListener { appendVal("9", false) }
        binding.numDot.setOnClickListener { appendVal(".", false) }
    }

    private fun setupOperatorButtons() {
        binding.actionAdd.setOnClickListener { appendVal("+", false) }
        binding.actionMinus.setOnClickListener { appendVal("-", false) }
        binding.actionMultiply.setOnClickListener { appendVal("×", false) }
        binding.actionDivide.setOnClickListener { appendVal("/", false) }
        binding.startBracket.setOnClickListener { appendVal("(", false) }
        binding.closeBracket.setOnClickListener { appendVal(")", false) }
    }

    private fun setupActionButtons() {
        binding.clear.setOnClickListener { appendVal("", true) }

        binding.actionBack.setOnClickListener {
            val expression = binding.placeholder.text.toString()
            if (expression.isNotEmpty()) {
                binding.placeholder.text = expression.substring(0, expression.length - 1)
            }
        }

        binding.actionEquals.setOnClickListener {
            try {
                val rawInput = binding.placeholder.text.toString()
                val expressionStr = rawInput.replace("×", "*")
                val expression = ExpressionBuilder(expressionStr).build()
                val result = expression.evaluate()

                val longResult = result.toLong()
                val resultStr = if (result == longResult.toDouble()) {
                    longResult.toString()
                } else {

                        val symbols = DecimalFormatSymbols().apply {
                            decimalSeparator = '.'
                        }
                        val formatter = DecimalFormat("#.###", symbols)
                        formatter.format(result)
                }

                binding.answer.text = resultStr
                lastResult = resultStr
                isResultJustCalculated = true

            } catch (e: Exception) {
                Toast.makeText(this, "Invalid expression", Toast.LENGTH_SHORT).show()
                Log.e("EXCEPTION", "Message: ${e.message}")
            }
        }
    }

    private fun appendVal(string: String, isClear: Boolean) {
        if (isClear) {
            binding.placeholder.text = ""
            binding.answer.text = ""
            isResultJustCalculated = false
        } else {
            if (isResultJustCalculated) {
                if (string.trim() in listOf("+", "-", "×", "/")) {
                    binding.placeholder.text = "$lastResult$string"
                } else {
                    binding.placeholder.text = string
                }
                isResultJustCalculated = false
            } else {
                binding.placeholder.append(string)
            }
        }
    }
}
