package jp.myuser.budgieapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var rootLayout: FrameLayout
    private lateinit var birdView: ImageView
    private lateinit var statusText: TextView
    private lateinit var loveText: TextView

    private var loveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. コードでレイアウトを構築
        setupLayout()

        // 2. 状態（ナイトモード）の初期確認
        updateStatusByTime()

        // 3. 命の演出（呼吸エフェクト）開始
        startBreathingEffect()
    }

    private fun setupLayout() {
        rootLayout = FrameLayout(this).apply {
            backgroundColor = Color.WHITE
        }

        // インコ画像（メインView）
        birdView = ImageView(this).apply {
            // リソース名は drawable/parakeet_idle に差し替えてください
            setImageResource(resources.getIdentifier("parakeet_idle", "drawable", packageName))
            
            // アクセシビリティ：タップ範囲を大幅拡大 (80dp相当)
            val iconSize = (80 * resources.displayMetrics.density).toInt()
            layoutParams = FrameLayout.LayoutParams(iconSize, iconSize).apply {
                gravity = Gravity.CENTER
            }

            setOnClickListener {
                handleBirdTap()
            }
        }

        // ステータス表示（起きてる/寝てる）
        statusText = TextView(this).apply {
            text = "元気なインコ"
            textSize = 18f
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                setMargins(0, 0, 0, 200)
            }
        }

        // 親密度（Love）表示
        loveText = TextView(this).apply {
            text = "Love: 0"
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.TOP or Gravity.END
                setMargins(0, 50, 50, 0)
            }
        }

        rootLayout.addView(birdView)
        rootLayout.addView(statusText)
        rootLayout.addView(loveText)

        setContentView(rootLayout)
    }

    /**
     * 時間帯による見た目とステータスの変化（ナイトモード）
     */
    private fun updateStatusByTime() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        // 22時〜6時は就寝
        if (hour >= 22 || hour < 6) {
            rootLayout.backgroundColor = Color.parseColor("#2C3E50") // 深夜色
            statusText.text = "Zzz... (Sleep)"
            statusText.setTextColor(Color.WHITE)
            birdView.alpha = 0.6f
            // 画像を眠っているものに変更
            birdView.setImageResource(resources.getIdentifier("parakeet_sleep", "drawable", packageName))
        } else {
            rootLayout.backgroundColor = Color.WHITE
            statusText.text = "起きてるよ！"
            statusText.setTextColor(Color.BLACK)
            birdView.alpha = 1.0f
        }
    }

    /**
     * インコ特有の速めの呼吸エフェクト
     */
    private fun startBreathingEffect() {
        val breathe = ScaleAnimation(
            1.0f, 1.08f, 1.0f, 1.08f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1500 // 猫より少しクイックな動き
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        birdView.startAnimation(breathe)
    }

    /**
     * タップ時のリアクション（Love加算）
     */
    private fun handleBirdTap() {
        loveCount++
        loveText.text = "Love: $loveCount"

        // 親密度に応じた簡易アクション（例：少し跳ねる）
        val jump = ScaleAnimation(
            1.0f, 1.2f, 1.0f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 100
            repeatCount = 1
            repeatMode = Animation.REVERSE
        }
        birdView.startAnimation(jump)
        
        // 終了後に呼吸エフェクトを再開
        jump.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                startBreathingEffect()
            }
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}
