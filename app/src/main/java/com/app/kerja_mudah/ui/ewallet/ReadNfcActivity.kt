package com.app.kerja_mudah.ui.ewallet

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentFilter.MalformedMimeTypeException
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.util.Log
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.databinding.ActivityReadNfcBinding
import com.google.android.gms.common.util.Hex
import java.nio.ByteBuffer
import java.util.*
import kotlin.experimental.and


class ReadNfcActivity : BaseActivity<ActivityReadNfcBinding>(ActivityReadNfcBinding::inflate) {

    companion object{
        val TAG = ReadNfcActivity::class.java.simpleName
    }

    private var nfcAdapter:NfcAdapter? = null
    private lateinit var mPendingIntent:PendingIntent

    override fun initSetup() {
        Log.i(TAG, "initSetup")
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null){
            showOkDialog(
                title = "Oops..",
                content = "This device didn't support nfc",
                cancelable = false,
                listener = {
                    dismissOkDialog()
                    finish()
                }
            )
            return
        }
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent!!)
        super.onNewIntent(intent)
        performTagOperations()
    }

    private fun enableForeGroundDispatch(){
        val intent = Intent(this.applicationContext, this::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent =
            PendingIntent.getActivity(this.applicationContext, 0, intent, PendingIntent.FLAG_MUTABLE)

        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        // Notice that this is the same filter as in our manifest.
        filters[0] = IntentFilter()
        filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        try {
            filters[0]!!.addDataType("text/plain")
        } catch (e: MalformedMimeTypeException) {
            throw RuntimeException("Check your mime type.")
        }

        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun inject() {

    }

    override fun onResume() {
        super.onResume()
        enableForeGroundDispatch()
    }
    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun performTagOperations() {
        Log.i(TAG, "ACTION ${intent.action}")
        val action = intent.action
        if (action == NfcAdapter.ACTION_TAG_DISCOVERED){
            val tagFromIntent:Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            Log.i(TAG, "TAG FROM INTENT $tagFromIntent")
            val miffareClassic = MifareClassic.get(tagFromIntent)
            Log.i(TAG, "MIFARE CLASSIC SIZE ${miffareClassic.size}")
            var data: ByteArray? = null
            val dfn_srv = byteArrayOf(0xA0.toByte(), 0x00,
                0x00, 0x00, 0x03, 0x86.toByte(), 0x98.toByte(),
                0x07, 0x01)

            val isoDep = IsoDep.get(tagFromIntent)
            try {
                isoDep.connect()
                if (isoDep.isConnected){
                    val sys = "1PAY.SYS.DDF01".toByteArray()
                    var back = isoDep.transceive(getSelectCommand(sys))
                    back = isoDep.transceive(getSelectCommand(dfn_srv))
                    val readMoney = byteArrayOf(0x80.toByte(), // CLA Class
                        0x5C.toByte(), // INS Instruction
                        0x00.toByte(), // P1 Parameter 1
                        0x02.toByte(), // P2 Parameter 2
                        0x04.toByte())// Le
                    val money = isoDep.transceive(readMoney)
                    if (money != null) {
                        val cash = byteToInt(money, money.size)
                        val ba = cash / 100.0f
                        Log.e(TAG, "cash: ${cash.toString()}")
                    }else{
                        Log.e(TAG, "Else ${money?.size}")
                    }
                }else{
                    Log.e(TAG, "Not connected")
                }
            }catch (e:Exception){
                Log.e(TAG, "${e.message}")
            }

            // todo donot delete
//            try {
//                miffareClassic.connect()
//                var auth = false
//                val cardData: String? = null
//                val secCount: Int = miffareClassic.sectorCount
//                var bCount:Int = 0
//                var bIndex:Int = 0
//                if (miffareClassic.isConnected){
//                    showSnackBar("Connected")
//                    for (j in 0 until secCount) {
//                        auth = miffareClassic.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT)
//                        if (auth) {
//                            bCount = miffareClassic.getBlockCountInSector(j)
//                            bIndex = 0
//                            for (i in 0 until bCount) {
//                                bIndex = miffareClassic.sectorToBlock(j)
//                                data = miffareClassic.readBlock(bIndex)
//                                Log.i(TAG, "tes ${Hex.bytesToStringLowercase(data)}")
//                                bIndex++
//                            }
//                        } else { // Authentication failed - Handle it
//                        }
//                    }
//                }else{
//                    showSnackBar("Not connected")
//                }
//
//            }catch (e:Exception){
//
//            }
        }

        // todo do not delete until nfc is solved
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
//            val parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
//            with(parcelables) {
//                val inNdefMessage = this?.get(0) as NdefMessage
//                val inNdefRecords = inNdefMessage.records
//                val ndefRecord_0 = inNdefRecords[0]
//
//                val inMessage = String(ndefRecord_0.payload)
//                Log.i(TAG, "inMessage: $inMessage")
//            }
//        }
    }

    private fun byteToInt(money: ByteArray?, i: Int): Int {
        return money?.let {
            var num = 0
            for (i in it.indices) {
                num = num.shl(8)
                num = num.or(it[i].and((0x00ff).toByte()).toInt())
            }
            num
        } ?: 0
    }

    private fun getSelectCommand(aid: ByteArray): ByteArray {
        val cmdOse = ByteBuffer.allocate(aid.size + 6)
        cmdOse.put(0x00.toByte()) // CLA Class
            .put(0xA4.toByte()) // INS Instruction
            .put(0x04.toByte()) // P1 Parameter 1
            .put(0x00.toByte()) // P2 Parameter 2
            .put(aid.size.toByte()) // Lc
            .put(aid).put(0x00.toByte()) // Le
        return cmdOse.array()
    }
}