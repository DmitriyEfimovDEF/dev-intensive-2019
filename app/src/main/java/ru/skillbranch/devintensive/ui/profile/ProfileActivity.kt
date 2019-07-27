package ru.skillbranch.devintensive.ui.profile

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_constraint.et_about
import kotlinx.android.synthetic.main.activity_profile_constraint.et_first_name
import kotlinx.android.synthetic.main.activity_profile_constraint.et_last_name
import kotlinx.android.synthetic.main.activity_profile_constraint.et_repository
import kotlinx.android.synthetic.main.activity_profile_constraint.iv_avatar
import kotlinx.android.synthetic.main.activity_profile_constraint.tv_rank



class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
        const val IS_REPO_VALID = "IS_REPO_VALID"
    }

    private lateinit var viewModel: ProfileViewModel
    private var isEditMode = false
    private lateinit var viewFields : Map<String, TextView>

    private lateinit var repositoryTextWatcher: TextWatcher
    var isRepoValid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)
        initViews(savedInstanceState)
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        et_repository.addTextChangedListener(repositoryTextWatcher)
    }

    override fun onPause() {
        super.onPause()
        et_repository.removeTextChangedListener(repositoryTextWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
        outState?.putBoolean(IS_REPO_VALID, isRepoValid)
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        viewModel.getProfileData().observe(this, Observer {
            updateUI(it)
        })

        viewModel.getTheme().observe(this, Observer {
            updateTheme(it)
        })

        viewModel.getInitialsDrawable().observe(this, Observer {
            iv_avatar.setImageDrawable(it)
        })

    }

    private fun updateTheme(mode: Int) {
        delegate.setLocalNightMode(mode)
        viewModel.updateInitialsDrawable()
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also {
            for ((k,v) in viewFields) {
                v.text = it[k].toString()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?) {
        viewFields = mapOf(
            "nickName" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )

        isRepoValid = savedInstanceState?.getBoolean(IS_REPO_VALID, true) ?: true

        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE,false) ?: false
        showCurrentMode(isEditMode)

        btn_edit.setOnClickListener {
            if (isEditMode) {
                if (!isRepoValid) {
                    et_repository.text?.clear()

                    wr_repository.error = null
                    wr_repository.isErrorEnabled = false
                }
                saveProfileInfo()
            }
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }

        repositoryTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!Utils.isRepoValid(s.toString())) {
                    isRepoValid = false
                    wr_repository.error = "Невалидный адрес репозитория"
                } else {
                    isRepoValid = true

                    wr_repository.error = null
                    wr_repository.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {}
        }

    }

    private fun showCurrentMode(isEdit: Boolean) {
        val data = setOf("firstName", "lastName", "about", "repository")
        val info = viewFields.filter { data.contains(it.key) }

        for ((_,v) in info) {
            v.apply {
                isFocusable = isEdit
                isFocusableInTouchMode = isEdit
                isEnabled = isEdit
                background.alpha = if (isEdit) 255 else 0
            }
        }

        ic_eye.visibility = if (isEdit) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEdit

        with (btn_edit) {
            val filter:  ColorFilter? =  if (isEdit) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon = if (isEdit) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo() {
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply {
            viewModel.saveProfileData(this)
        }
    }

}
