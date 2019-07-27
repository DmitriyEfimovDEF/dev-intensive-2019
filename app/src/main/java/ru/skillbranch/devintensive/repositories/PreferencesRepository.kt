package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils


object PreferencesRepository {

    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val ABOUT = "ABOUT"
    private const val REPOSITORY = "REPOSITORY"
    private const val RATING = "RATING"
    private const val RESPECT = "RESPECT"

    private const val APP_THEME = "APP_THEME"


    private val prefs: SharedPreferences by lazy {
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveAppTheme(theme: Int) {
        putValue(APP_THEME to theme)
    }

    fun getAppTheme(): Int = prefs.getInt(APP_THEME, AppCompatDelegate.MODE_NIGHT_NO)


    fun saveProfile(profile: Profile) {
        with(profile) {
            putValue(FIRST_NAME to firstName)
            putValue(LAST_NAME to lastName)
            putValue(ABOUT to about)
            putValue(REPOSITORY to repository)
            putValue(RATING to rating)
            putValue(RESPECT to respect)
        }
    }

    fun getProfile() = Profile(
            prefs.getString(FIRST_NAME, "")!!,
            prefs.getString(LAST_NAME, "")!!,
            prefs.getString(ABOUT, "")!!,
            prefs.getString(REPOSITORY, "")!!,
            prefs.getInt(RATING, 0),
            prefs.getInt(RESPECT, 0)
        )

    private fun putValue(pair: Pair<String, Any>) = with(prefs.edit()) {
        val key = pair.first
        val value = pair.second

        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored")
        }
        apply()
    }

    fun getInitials(): Pair<String?, String?> {
        return prefs.getString(FIRST_NAME, "") to prefs.getString(LAST_NAME, "")
    }

    fun getInitialsDrawable(initials: Pair<String, String>): Drawable {
        val data = Utils.toInitials(initials.first, initials.second)
        return genInitialsDrawable(data!!)
    }


    private fun genInitialsDrawable(initials: String): Drawable {
//        return TextDrawable
//            .builder()
//            .beginConfig()
//            .width(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
//            .height(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
//            .endConfig()
//            .buildRound(initials, R.attr.colorAccent)

        val bitmap = getInitialsBitmap(initials, 48f, Color.WHITE)
        return BitmapDrawable(App.applicationContext().resources, bitmap)
    }

    private fun getInitialsBitmap(text:String, textSize:Float, textColor:Int): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.CENTER

        val image = Bitmap.createBitmap(112, 112, Bitmap.Config.ARGB_8888)

//        val value = TypedValue()
//        App.applicationContext().theme.resolveAttribute(R.attr.colorAccent, value, true)

        image.eraseColor(ContextCompat.getColor(App.applicationContext(), R.color.color_accent))
        val canvas = Canvas(image)
        canvas.drawText(text, 56f, 56f + paint.textSize / 3, paint)
        return image
    }

}
