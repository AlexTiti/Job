package com.alex.kotlin.job

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

const val AUTHORITY = "com.alex.kotlin.job.provider"
const val data = "vnd.android.cursor.dir/test"

class Provider : ContentProvider() {


    var dataBase: DataBase? = null


    private val usrSwitch = UriMatcher(UriMatcher.NO_MATCH)

    init {
        usrSwitch.addURI(AUTHORITY, "path", 1)
        usrSwitch.addURI(AUTHORITY, "job", 2)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val long = dataBase?.writableDatabase?.insert(getTable(uri), null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return ContentUris.withAppendedId(uri, long!!)
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return dataBase?.readableDatabase?.query(getTable(uri), null, selection, selectionArgs, null, null, sortOrder)
    }

    override fun onCreate(): Boolean {
        dataBase = DataBase(context)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        when (usrSwitch.match(uri)) {
            1 -> {
                return data
            }
        }
        return null
    }

    fun getTable(uri: Uri): String {

        when (usrSwitch.match(uri)) {
            2 -> return "job"
        }
        return "job"
    }
}