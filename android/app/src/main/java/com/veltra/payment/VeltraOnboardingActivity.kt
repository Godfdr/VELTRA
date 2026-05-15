package com.veltra.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.veltra.payment.databinding.ActivityVeltraOnboardingBinding
import com.veltra.payment.databinding.ItemOnboardingPageBinding

class VeltraOnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeltraOnboardingBinding

    private val onboardingItems = listOf(
        OnboardingItem(
            "Tap & Go",
            "NFC payments method with friends and merchants. Pay instantly with a simple tap.",
            R.drawable.ic_onboarding_nfc
        ),
        OnboardingItem(
            "Offline Payment",
            "Payment on the go, without access to internet. Secure and reliable anywhere.",
            R.drawable.ic_onboarding_offline
        ),
        OnboardingItem(
            "Spot a Friend",
            "Fast, easy, and fun way to ask friends for money. Request cash in just one click.",
            R.drawable.ic_onboarding_spot
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeltraOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupIndicators()

        binding.nextBtn.setOnClickListener {
            if (binding.viewPager.currentItem + 1 < onboardingItems.size) {
                binding.viewPager.currentItem += 1
            } else {
                navigateToSignUp()
            }
        }

        binding.skipBtn.setOnClickListener { navigateToSignUp() }
    }

    private fun setupViewPager() {
        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
                if (position == onboardingItems.size - 1) {
                    binding.nextBtn.text = "Get Started"
                } else {
                    binding.nextBtn.text = "Next"
                }
            }
        })
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(onboardingItems.size)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_circle_dot
                )
            )
            indicators[i]?.layoutParams = layoutParams
            binding.indicatorContainer.addView(indicators[i])
        }
        updateIndicators(0)
    }

    private fun updateIndicators(position: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.alpha = 1f
                imageView.scaleX = 1.2f
                imageView.scaleY = 1.2f
                imageView.imageTintList = android.content.res.ColorStateList.valueOf(0xFF00D4FF.toInt())
            } else {
                imageView.alpha = 0.3f
                imageView.scaleX = 1f
                imageView.scaleY = 1f
                imageView.imageTintList = android.content.res.ColorStateList.valueOf(0xFF9CA3AF.toInt())
            }
        }
    }

    private fun navigateToSignUp() {
        startActivity(Intent(this, VeltraSignUpActivity::class.java))
        finish()
    }

    data class OnboardingItem(val title: String, val desc: String, val imageRes: Int)

    class OnboardingAdapter(private val items: List<OnboardingItem>) :
        RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemOnboardingPageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount() = items.size

        class ViewHolder(private val binding: ItemOnboardingPageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(item: OnboardingItem) {
                binding.onboardingTitle.text = item.title
                binding.onboardingDesc.text = item.desc
                // Use imageRes instead of Lottie for now to ensure visuals are seen
                binding.onboardingLottie.setImageResource(item.imageRes)
                
                // Add a simple entrance animation for the image
                binding.onboardingLottie.alpha = 0f
                binding.onboardingLottie.scaleX = 0.8f
                binding.onboardingLottie.scaleY = 0.8f
                binding.onboardingLottie.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(600)
                    .start()
            }
        }
    }
}
