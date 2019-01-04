package com.justcode.hxl.StepperView

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.justcode.hxl.sugar.toast
import com.justcode.hxl.viewaction.R
import kotlinx.android.synthetic.main.activity_stepper_view.*
import stepperview.VerticalStepperItemView

class StepperViewActivity : AppCompatActivity() {
    private val mSteppers = arrayOfNulls<VerticalStepperItemView>(3)
    private var mActivatedColorRes = R.color.material_blue_500
    private var mDoneIconRes = R.drawable.ic_done_white_16dp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stepper_view)
        mSteppers[0] = stepper_0
        mSteppers[1] = stepper_1
        mSteppers[2] = stepper_2
        VerticalStepperItemView.bindSteppers(mSteppers[0], mSteppers[1], mSteppers[2])
        button_next_0.setOnClickListener {
            mSteppers[0]?.nextStep()
        }
        button_test_error.setOnClickListener {
            if (mSteppers[0]?.getErrorText() != null) {
                mSteppers[0]?.setErrorText(null)
            } else {
                mSteppers[0]?.setErrorText("Test error!")
            }
        }
        button_prev_1.setOnClickListener {
            mSteppers[1]?.prevStep()
        }
        button_next_1.setOnClickListener {
            mSteppers[1]?.nextStep()
        }
        button_prev_2.setOnClickListener {
            mSteppers[2]?.prevStep()
        }
        button_next_2.setOnClickListener {
            "Finish!".toast()
        }
        btn_change_point_color.setOnClickListener {
            if (mActivatedColorRes == R.color.material_blue_500) {
                mActivatedColorRes = R.color.material_deep_purple_500
            } else {
                mActivatedColorRes = R.color.material_blue_500
            }
            for (stepper in mSteppers) {
                stepper?.setActivatedColorResource(mActivatedColorRes)
            }
        }
        btn_change_done_icon.setOnClickListener {
            if (mDoneIconRes == R.drawable.ic_done_white_16dp) {
                mDoneIconRes = R.drawable.ic_save_white_16dp
            } else {
                mDoneIconRes = R.drawable.ic_done_white_16dp
            }
            for (stepper in mSteppers) {
                stepper?.setDoneIconResource(mDoneIconRes)
            }
        }

    }
}
