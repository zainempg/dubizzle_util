package dubizzle.com.util

import android.os.SystemClock
import android.view.View

class DebouncedClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

object DebouncedClickListenerObject {
    @JvmStatic
    fun View.setDebounceClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = DebouncedClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
}
