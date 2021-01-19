package id.arahnets.willy.iso8583

import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class AplikasiClient {
    fun main (){

        val formatterBit7  = SimpleDateFormat("MMddHHmmss")

        val   logonRequest :MutableMap<Int, String>  = LinkedHashMap()
        logonRequest[7] = formatterBit7.format(Date())
        logonRequest[11] = "834624"
        logonRequest[70] = "001"

        var bitmap: BigInteger = BigInteger.ZERO.setBit(128-1)
        bitmap = bitmap.setBit(128-7)
        bitmap = bitmap.setBit(128-11)
        bitmap = bitmap.setBit(128-70)

        val strBitToBin = bitmap.toString(2)
        println("Convert Binary : $strBitToBin")

        val strBitToHex = bitmap.toString(16)
        println("Convert Hexa : $strBitToHex")
    }

}