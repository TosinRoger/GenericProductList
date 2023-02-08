package com.tosin.genericproductlist.domain.factory.utils

import java.math.BigDecimal
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    private const val SALT_CHARS = "abcdefghijklmnopqrstuvwxyz1234567890"
    private const val SALT_CHARS_ALPHABET = "abcdefghijklmnopqrstuvwxyz"

    fun randomString(): String = randomUUID()

    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(1, 1000 + 1)

    fun randomDouble(): Double = randomInt().toDouble()

    fun randomLong(): Long = randomInt().toLong()

    fun randomBigDecimal(): BigDecimal = randomInt().toBigDecimal()

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun randomDate(): Date = randomCalendar().time

    fun randomCalendar(): Calendar = Calendar.getInstance()

    fun randomUUID(): String = UUID.randomUUID().toString()

    fun randomEmail(): String {
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 10) { // length of the random string.
            val index = (rnd.nextFloat() * SALT_CHARS.length).toInt()
            salt.append(SALT_CHARS[index])
        }
        return "$salt@churros.com"
    }

    fun randomAlphabet(): String {
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 10) { // length of the random string.
            val index = (rnd.nextFloat() * SALT_CHARS_ALPHABET.length).toInt()
            salt.append(SALT_CHARS_ALPHABET[index])
        }
        return salt.toString()
    }
}
