package cdbol.br.com.clubedabola.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import cdbol.br.com.clubedabola.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class FullProgressDialog {
    companion object {
        fun fullProgressDialog(context: Context): Dialog{
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT))
            return dialog
        }

        fun showAlert(context: Context, message:String){
            context!!.alert(message) {
                yesButton {}
            }.show()
        }


    }

}