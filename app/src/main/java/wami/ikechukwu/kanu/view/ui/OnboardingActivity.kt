package wami.ikechukwu.kanu.view.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import wami.ikechukwu.kanu.R
import wami.ikechukwu.kanu.databinding.ActivityOnboardingBinding
import wami.ikechukwu.kanu.viewmodel.OnboardingViewModel

class OnboardingActivity : AppCompatActivity() {

    //        FOR THE DATA BINDING
    private lateinit var binding: ActivityOnboardingBinding

    //    FOR THE VIEW MODEL
    private lateinit var onboardViewModel: OnboardingViewModel

    //    CURRENT VIEW PAGER ADAPTER POSITION
    var currentViewPagerPosition = 0

    //    GET STARTED BUTTON ANIMATION
    lateinit var getStartedBtnAnim: Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      HIDDING THE ACTION BAR
        supportActionBar?.hide()

//        BINDING THE LAYOUT TO DATABINDING
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

//        ACCESSING THE VIEW MODEL CLASS
        onboardViewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]

//        VIEWPAGER WIDGET FROM THE LAYOUT
        binding.onboardingViewpager.adapter = onboardViewModel.initTheAdapter()
        binding.onboardingTabLayout.setupWithViewPager(binding.onboardingViewpager)
        binding.onboardingViewpager.setPadding(40, 0, 40, 0)

//        SET THE CURRENT ITEM FROM THE VIEWPAGER
        currentViewPagerPosition = binding.onboardingViewpager.currentItem

//      WHEN THE NEXT BUTTON IS CLICKED DO THE FOLLOWING
        binding.onboardingNextButton.setOnClickListener {

            if (currentViewPagerPosition < onboardViewModel.onArrayListSize()) {
                currentViewPagerPosition++

                setCurrentPosition()
            }

            if (currentViewPagerPosition == onboardViewModel.onArrayListSize()) {
                viewVisibility()
                getStartedButtonAnimation()
            }

        }

    }

    private fun getStartedButtonAnimation() {
        getStartedBtnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.getstarted_btn_anim)
        binding.getStartedButton.animation = getStartedBtnAnim
    }

    private fun setCurrentPosition() {
        binding.onboardingViewpager.currentItem = currentViewPagerPosition
    }

    private fun viewVisibility() {
//            SET THE GET STARTED BUTTON TO BE VISIBLE
        binding.getStartedButton.visibility = View.VISIBLE

//            SET THE NEXT BUTTON INVISIBLE
        binding.onboardingNextButton.visibility = View.INVISIBLE

//            SET THE TAB LAYOUT INVISIBLE
        binding.onboardingTabLayout.visibility = View.INVISIBLE
    }
}


