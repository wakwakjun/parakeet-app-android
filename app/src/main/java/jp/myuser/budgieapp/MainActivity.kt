package jp.myuser.budgieapp

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // 呼吸エフェクト用View（インコの画像を表示するView）
    private lateinit var birdView: View
    private var loveCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // XMLなしのViewベース設計：後ほど具体的なレイアウトコードをここに記述
        // 現時点では、猫版から移植したロジックの受け皿を用意します
        
        setupBirdView()
        startBreathingEffect()
    }

    private fun setupBirdView() {
        // ここでViewの生成とクリックリスナー（Loveカウント）を設定
        // アクセシビリティ確保のため、タップ範囲を広げる設定をここに含めます
    }

    /**
     * インコらしさを出すための呼吸エフェクト
     * 猫よりも少し速いテンポ（1.5秒周期）に設定
     */
    private fun startBreathingEffect() {
        val breathe = ScaleAnimation(
            1.0f, 1.05f, 1.0f, 1.05f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1500 // 猫より速め
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        // birdView.startAnimation(breathe)
    }
}
