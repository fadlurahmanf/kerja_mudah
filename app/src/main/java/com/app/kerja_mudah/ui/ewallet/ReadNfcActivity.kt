package com.app.kerja_mudah.ui.ewallet

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityReadNfcBinding

class ReadNfcActivity : BaseActivity<ActivityReadNfcBinding>(ActivityReadNfcBinding::inflate) {

    companion object{
        val TAG = ReadNfcActivity::class.java.simpleName
    }

    private lateinit var nfcAdapter:NfcAdapter
    private lateinit var mPendingIntent:PendingIntent

    override fun initSetup() {
        Log.i(TAG, "initSetup")
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        performTagOperations(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        Log.i(TAG, "connecting to NFC ${intent?.action}")
        super.onNewIntent(intent)
        performTagOperations(intent!!)
    }

    private fun enableForeGroundDispatch(activity:AppCompatActivity, adapter: NfcAdapter){
        Log.i(TAG, "enableForegroundDispatch")
        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        filters[0] = IntentFilter()
        with(filters[0]) {
            this?.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            this?.addCategory(Intent.CATEGORY_DEFAULT)
            try {
                this?.addDataType("plain/text")
            } catch (ex: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Check your MIME type")
            }
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    override fun inject() {

    }

    override fun onResume() {
        super.onResume()
        mPendingIntent = PendingIntent.getActivity(this, 0, Intent(this, ReadNfcActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        nfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null)
//        enableForeGroundDispatch(this, nfcAdapter)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    private fun performTagOperations(intent: Intent) {
        Log.i(TAG, "performTagOperations ${intent.action}")
        val action = intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            with(parcelables) {
                val inNdefMessage = this?.get(0) as NdefMessage
                val inNdefRecords = inNdefMessage.records
                val ndefRecord_0 = inNdefRecords[0]

                val inMessage = String(ndefRecord_0.payload)
                Log.i(TAG, "inMessage: ${inMessage}")
            }
        }
    }
}