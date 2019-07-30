package cdbol.br.com.clubedabola.screens.home.profile.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.TextView
import cdbol.br.com.clubedabola.R
import cdbol.br.com.clubedabola.manarge.ApiManager
import cdbol.br.com.clubedabola.mvp.BaseMvpActivity
import cdbol.br.com.clubedabola.screens.home.EditProfileContract
import cdbol.br.com.clubedabola.screens.home.EditProfilePresenter
import cdbol.br.com.clubedabola.utils.CircleTransform
import cdbol.br.com.clubedabola.utils.FileUtils
import cdbol.br.com.clubedabola.utils.PreferencesString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class EditProfileActivity : BaseMvpActivity<EditProfileContract.View, EditProfileContract.Presenter>() {

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
        private val READ_EXTERNAL_STORAGE = 10
    }

    private var newFile: File? = null

    override var mPresenter: EditProfileContract.Presenter = EditProfilePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initToolBar()

        container_change_password.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)

        }

        Picasso.get().load(getHirer().avatar).transform(CircleTransform()).placeholder(R.drawable.ic_user).into(iv_photo)

        edt_name.setText(getHirer().name)
        edt_nickname.setText(getHirer().nickname)
        edt_birthday.setText(getHirer().birthday)
        edt_email.setText(getHirer().email)

        iv_photo.setOnClickListener {
            checkPermission()
            selectImageInAlbum()
        }
    }

    private fun initToolBar() {

        val toolBar = findViewById<Toolbar>(R.id.toolbar_edit_profile)
        setSupportActionBar(toolBar!!)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        var title = toolBar.findViewById<TextView>(R.id.toolbar_title)

        title.text = getString(R.string.title_edit_profile)

        var done = toolBar.findViewById<Button>(R.id.toolbar_done)
        var cancel = toolBar.findViewById<Button>(R.id.toolbar_cancelar)

        done?.setOnClickListener {

        }

        cancel?.setOnClickListener { onBackPressed() }


//        val back = toolBar.findViewById<ImageButton>(R.id.toolbar_back)
//
//        back?.setOnClickListener {
//            onBackPressed()
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {

                }
                return
            }

            else -> {

            }
        }
    }

    private fun checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE)

            return
        }

    }

    private fun uploadImage(file: File) {

        Log.d("Image", "Filename " + file.name)
        val mFile = RequestBody.create(MediaType.parse("image/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, mFile)

        ApiManager.uploadImage(fileToUpload, PreferencesString.instance.getString("hirerId")!!)
                .doOnError { Log.d("Image", "error") }
                .subscribe {
                    callGetHirerId()
                    newFile!!.delete()
                }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                onSelectFromGalleryResult(data)
            }
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var uri: Uri = data!!.data
        val filePath = FileUtils.getSmartFilePath(this, uri)
        val file = File(filePath)
        try {
            newFile = file.copyTo(File(Environment.getExternalStorageDirectory(),file.name), true)
            FileUtils.saveBitmapToFile(newFile!!)
            uploadImage(newFile!!)
            Picasso.get().load(newFile!!).transform(CircleTransform()).placeholder(R.drawable.avatar).into(iv_photo)

        }catch (e: FileNotFoundException){
            e.printStackTrace()
        }


    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!.get("data") as Bitmap
        val bytes = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)

        val destination = File(getExternalStorageDirectory(),
                System.currentTimeMillis().toString() + ".jpg")

        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        iv_photo.setImageBitmap(thumbnail)
    }

    private fun selectImageInAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }

    fun takePhoto() {
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent1.resolveActivity(packageManager) != null) {
            startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
        }
    }

}
