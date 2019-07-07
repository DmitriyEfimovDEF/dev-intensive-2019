package ru.aisdev.devint2019

import org.junit.Test

import org.junit.Assert.*
import ru.aisdev.devint2019.entities.*
import ru.aisdev.devint2019.extensions.TimeUnits
import ru.aisdev.devint2019.extensions.add
import ru.aisdev.devint2019.extensions.format
import ru.aisdev.devint2019.extensions.toUserView
import ru.aisdev.devint2019.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user1 = User("1")
        val user2 = User("2", "John", "Cena")
        val user3 = User("3")

//        user1.printMe()
        val user4 = user1.copy("4", "lupa", "pupa")

//        user4.printMe()
//        println("$user1 \n$user2 \n$user3")
    }

    @Test
    fun test_factory() {
//        User.makeUser("Abdul ibn Obstul Zadom-bey")
        User.makeUser("John Wick")

        val user5 = User.makeUser("asdf asdf ")
        println(user5)
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("Someone Special")
        val userSecond = user.copy("5")
        fun getUserInfo() = userSecond
        val (id, firstName, lastName) = getUserInfo()
        println(
            """
            $id,
            $firstName,
            $lastName
            ${if (user == userSecond) "userSecond is complete copy of user, user.hashcode is ${user.hashCode()} and userSecond.hashCode is ${user.hashCode()}" else "userSecond is NOT complete copy of user, user.hashcode is ${user.hashCode()} userSecond.hashCode ${userSecond.hashCode()}"}
            ${if (user === userSecond) "userSecond is the same object to user, user.address is ${System.identityHashCode(
                user
            )} and userSecond.address is ${System.identityHashCode(userSecond)}" else "userSecond is another object of user, user.address is ${System.identityHashCode(
                user
            )} userSecond.address ${System.identityHashCode(userSecond)}"}

            """.trimIndent()
        )
    }

    @Test
    fun test_date_extensions() {
        val user = User.makeUser("asdf qwer")
        val userSecond = user.copy(id = "1234", lastVisit = Date().add(50, TimeUnits.SECONDS))

        println(
            """
            ${user.lastVisit?.format()}
            ${userSecond.lastVisit?.format("mm:ss")}
        """.trimIndent()
        )
    }

    @Test
    fun test_data_mapping() {
        val user = User.makeUser("Ефимов Дмитрий")
        val userView = user.toUserView()
    }

    @Test
    fun test_message_factory() {
        val user = User.makeUser("Ефимов Дмитрий")
        val textMessage = BaseMessage.makeMessage(
            user,
            chat = Chat("1234"),
            payload = "some text message",
            timeStamp = Date(),
            type = MessageType.TEXT
        )
        val imageMessage = BaseMessage.makeMessage(
            user,
            chat = Chat("1234"),
            payload = "some image message",
            timeStamp = Date(),
            type = MessageType.TEXT
        )

        when (imageMessage) {
            is TextMessage -> println("this is the text message")
            is ImageMessage -> println("this is the image message")
        }

        println(textMessage.formatMessage())
        println(imageMessage.formatMessage())

    }
    @Test
    fun test_getInitials() {
        val pair = Utils.parseFullName("some")
        println(Utils.getInitials("${pair.first} ${pair.second}"))
    }
}
