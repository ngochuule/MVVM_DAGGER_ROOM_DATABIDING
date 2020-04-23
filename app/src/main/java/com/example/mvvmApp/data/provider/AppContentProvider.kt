package com.example.mvvmApp.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.mvvmApp.MyApplication

class AppContentProvider : ContentProvider(){
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // nothing to do
        val app: MyApplication = context as MyApplication
        app.appComponent.inject(this)
        return null
    }

    override fun onCreate(): Boolean {
        // nothing to do
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        // nothing to do
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        // nothing to do
        return 0
    }

    override fun getType(uri: Uri): String? {
        // nothing to do
        return "0"
    }
}