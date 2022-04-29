package cz.mendelu.tododolist.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import cz.mendelu.tododolist.R
import cz.mendelu.tododolist.databinding.ViewInfoBinding
import cz.mendelu.tododolist.databinding.ViewTextInputBinding


class InfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0)
    : FrameLayout(context, attrs, defStyleAttrs)
{
    init {
        init(context, attrs)
    }

    private lateinit var binding: ViewInfoBinding

    var text: String
        get() = binding.value.text.toString()
        set(value){
            binding.value.setText(value)
        }

    private fun init(context: Context, attrs: AttributeSet?){
        binding = ViewInfoBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.InfoView)
        val value = attributes.getString(R.styleable.InfoView_value)
        val header = attributes.getString(R.styleable.InfoView_header)
        val image = attributes.getDrawable(R.styleable.InfoView_image)

        binding.value.text = value
        binding.header.text = header
        binding.infoImage.setImageDrawable(image)

        attributes.recycle()
    }

    fun setError(error: String?){
        binding.value.error = error
    }

    fun addTextChangeListener(watcher: TextWatcher){
        binding.value.addTextChangedListener(watcher)
    }
}