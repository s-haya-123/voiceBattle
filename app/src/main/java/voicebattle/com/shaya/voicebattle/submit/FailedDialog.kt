package voicebattle.com.shaya.voicebattle.submit

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class FailedDialog: DialogFragment() {
    var menulist = arrayOf("OK")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).apply {
            setTitle("バトルに失敗しました…")
            setMessage("通信状況が悪いとランキングに反映できない可能性があります")
        }.create()
    }
}