package com.example.mvvmApp.ui.mainpage

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.databinding.DataBindingUtil
import com.example.mvvmApp.R
import com.example.mvvmApp.databinding.ActivityHomeBinding
import com.example.mvvmApp.ui.obtainViewModel
import com.example.mvvmApp.ui.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnTouchListener {
    private val DEBUG_TAG :String="TEST_DATA"
    private lateinit var viewModel: HomeViewModel
    fun obtainViewModel(): HomeViewModel = obtainViewModel(HomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        binding.viewModel = viewModel
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewFragment()

        rootView.setOnTouchListener(this)
    }

    private fun setupViewFragment() {
        supportFragmentManager.findFragmentById(R.id.contentFrame)
            ?: replaceFragmentInActivity(HomeFragment.newInstance(), R.id.contentFrame)
    }

    private var mActivePointerId: Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {

        // Get the pointer ID
        mActivePointerId = event.getPointerId(0)

        // ... Many touch events later...

        // Use the pointer ID to find the index of the active pointer
        // and fetch its position
        val (x: Float, y: Float) = event.findPointerIndex(mActivePointerId).let { pointerIndex ->
            // Get the pointer's current position
            event.getX(pointerIndex) to event.getY(pointerIndex)
        }
        val (xPos: Int, yPos: Int) = MotionEventCompat.getActionMasked(event).let { action ->
            Log.d(DEBUG_TAG, "The action is ${actionToString(action)}")
            // Get the index of the pointer associated with the action.
            MotionEventCompat.getActionIndex(event).let { index ->
                // The coordinates of the current screen contact, relative to
                // the responding View or Activity.
                MotionEventCompat.getX(event, index).toInt() to MotionEventCompat.getY(event, index).toInt()
            }
        }

        if (event.pointerCount > 1) {
            Log.d(DEBUG_TAG, "Multitouch event")

        } else {
            // Single touch event
            Log.d(DEBUG_TAG, "Single touch event")
        }

        return true
    }

    // Given an action int, returns a string description
    fun actionToString(action: Int): String {
        return when (action) {
            MotionEvent.ACTION_DOWN -> "Down"
            MotionEvent.ACTION_MOVE -> "Move"
            MotionEvent.ACTION_POINTER_DOWN -> "Pointer Down"
            MotionEvent.ACTION_UP -> "Up"
            MotionEvent.ACTION_POINTER_UP -> "Pointer Up"
            MotionEvent.ACTION_OUTSIDE -> "Outside"
            MotionEvent.ACTION_CANCEL -> "Cancel"
            else -> ""
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // Get the pointer ID
        mActivePointerId = event!!.getPointerId(0)

        // ... Many touch events later...

        // Use the pointer ID to find the index of the active pointer
        // and fetch its position
        val (x: Float, y: Float) = event.findPointerIndex(mActivePointerId).let { pointerIndex ->
            // Get the pointer's current position
            event.getX(pointerIndex) to event.getY(pointerIndex)
        }
        val (xPos: Int, yPos: Int) = MotionEventCompat.getActionMasked(event).let { action ->
            Log.d(DEBUG_TAG, "The action is ${actionToString(action)}")
            // Get the index of the pointer associated with the action.
            MotionEventCompat.getActionIndex(event).let { index ->
                // The coordinates of the current screen contact, relative to
                // the responding View or Activity.
                MotionEventCompat.getX(event, index).toInt() to MotionEventCompat.getY(event, index).toInt()
            }
        }

        if (event.pointerCount > 1) {
            Log.d(DEBUG_TAG, "Multitouch event")

        } else {
            // Single touch event
            Log.d(DEBUG_TAG, "Single touch event")
        }

        return true
    }
}