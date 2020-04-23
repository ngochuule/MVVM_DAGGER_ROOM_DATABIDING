package com.example.mvvmApp.data.datasource.homepage

import android.content.Intent
import com.example.mvvmApp.MyApplication
import com.example.mvvmApp.ui.detailpage.DetailActivity

class HomeDataSourceImpl private constructor(
    private val app: MyApplication
) : HomeDataSource {

    companion object {

        @Volatile
        private var INSTANCE: HomeDataSourceImpl? = null

        fun getInstance(app: MyApplication) =
            INSTANCE ?: synchronized(HomeDataSourceImpl::class.java) {
                INSTANCE ?: HomeDataSourceImpl(app).also {
                    INSTANCE = it
                }
            }
    }

    override fun onNextPageClick() {
//       val it = Intent(app, DetailActivity::class.java)
//        app.startActivity(it)

        val it2 = Intent(app, DetailActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            it.putExtra("value","111")
        }
        app.startActivity(it2)

       /* val it3 = Intent(app, DetailActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("value","1111")
        }*/
    }
}