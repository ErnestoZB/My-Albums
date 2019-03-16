package mx.com.ernox.albums.extensions

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Activity.showAlertDialog(message : String)
{
    AlertDialog.Builder(this)
               .setMessage(message)
               .setCancelable(true)
               .setNeutralButton("Ok", null)
               .create()
               .show()
}

fun FragmentActivity.replaceFragment(layoutId: Int, fragment: Fragment)
{
    supportFragmentManager.beginTransaction()
                          .replace(layoutId, fragment)
                          .commit()
}