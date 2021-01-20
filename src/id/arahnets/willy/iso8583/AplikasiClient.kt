package id.arahnets.willy.iso8583

import java.io.DataOutputStream
import java.io.IOException
import java.lang.StringBuilder
import java.math.BigInteger
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class AplikasiClient {
    fun main() {

        val formatterBit7 = SimpleDateFormat("MMddHHmmss")

        println("========================= REQUEST =========================")

        val logonRequest: MutableMap<Int, String> = LinkedHashMap()
        logonRequest[7] = formatterBit7.format(Date())
        logonRequest[11] = "834624"
        logonRequest[70] = "001"

        //get bitmap request
        val client = AplikasiClient()
        val bitmapRequest = client.countBitmap(logonRequest)
        println("Bitmap : $bitmapRequest")

        //convert bitmap to binary
        val strBitToBinRequest = bitmapRequest.toString(2)
        println("Convert Binary : $strBitToBinRequest")

        //convert bitmap to hexa
        val strBitToHexRequest = bitmapRequest.toString(16)
        println("Convert Hexa : $strBitToHexRequest")

        val strLOgonRequest = client.messageString("0800", logonRequest)
        println("Logon Request : $strLOgonRequest")

        val messageLength = strLOgonRequest.length + 2
        println("Message Length : $messageLength")

        val byteArrayLengt = ByteArray(2)
        byteArrayLengt[0] = (messageLength shr 8 and 0xff).toByte()
        byteArrayLengt[0] = (messageLength and 0xff).toByte()
        println("Message Length Byte Order : ${String(byteArrayLengt)}")

        client.sending(strLOgonRequest, messageLength)


        println("========================= RESPONSE =========================")

        val logonResponse: MutableMap<Int, String> = LinkedHashMap()
        logonResponse[7] = formatterBit7.format(Date())
        logonResponse[11] = "834624"
        logonResponse[39] = "00"
        logonResponse[70] = "001"

        //get bitmap request
        val bitmapResponse = client.countBitmap(logonResponse)
        println("Bitmap : $bitmapRequest")

        //convert bitmap to binary
        val strBitToBinResponse = bitmapResponse.toString(2)
        println("Convert Binary : $strBitToBinResponse")

        //convert bitmap to hexa
        val strBitToHexResponse = bitmapResponse.toString(16)
        println("Convert Hexa : $strBitToHexResponse")
    }

    fun countBitmap(message: MutableMap<Int, String>): BigInteger {

        var bitmap = BigInteger.ZERO

        for (dataElement in message.keys) {
            if (dataElement > 64) {
                bitmap = bitmap.setBit(128 - 1)
            }
            bitmap = bitmap.setBit(128 - dataElement)
        }

        return bitmap

    }

    fun messageString(mti: String, message: MutableMap<Int, String>): String {
        val hasil = StringBuilder()
        hasil.append(mti)
        hasil.append(countBitmap(message).toString(16))
        for (dataElement in message.keys) {
            hasil.append(message.get(dataElement))
        }
        return hasil.toString()
    }


    private fun sending(message: String, messageLength: Int) {

        try {
            val koneksi = Socket("localhost", 12345)
            val out = DataOutputStream(koneksi.getOutputStream())
            out.writeShort(messageLength)
            out.writeBytes(message)
            out.close()

            println("Message sudah terkirim")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}