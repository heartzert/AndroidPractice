package heartzert.test.all

import com.nhaarman.mockitokotlin2.*
import org.junit.*
import org.junit.Assert.*
import org.junit.Test
import org.mockito.*
import org.mockito.junit.*

/**
 * Created by heartzert on 2020/3/13.
 * Email: heartzert@163.com
 */
class MockitoTest {

    @Mock
    var person = mock<Person> {
        on { age } doReturn 88
        on { name } doReturn "大飞机"
    }

    @Rule
    @JvmField
    val mRule = MockitoJUnit.rule()

    @Test
    fun test() {
        assertNotNull(person)
    }

    @Test
    fun test2() {
        whenever(person.school).thenReturn("幼儿园")

        println(person.name)
        println(person.age)
        println(person.school)
    }

    @Test
    fun test3() {
        person.walk()
        person.walk()

        /**
         * after(long millis)	在给定的时间后进行验证
         * timeout(long millis)	验证方法执行是否超时
         * atLeast(int minNumberOfInvocations)	至少进行n次验证
         * atMost(int maxNumberOfInvocations)	至多进行n次验证
         * description(String description)	验证失败时输出的内容
         * times(int wantedNumberOfInvocations)	验证调用方法的次数
         * never()	验证交互没有发生,相当于times(0)
         * only()	验证方法只被调用一次，相当于times(1)
         */
        //验证walk方法至少执行了2次
        verify(person, atLeast(2)).walk()
    }

    @Test
    fun test4() {
        /**
         * anyObject()	        匹配任何对象
         * any(Class<T> type)	与anyObject()一样
         * any()	            与anyObject()一样
         * anyBoolean()	        匹配任何boolean和非空Boolean
         * anyByte()	        匹配任何byte和非空Byte
         * anyCollection()	    匹配任何非空Collection
         * anyDouble()	        匹配任何double和非空Double
         * anyFloat()	        匹配任何float和非空Float
         * anyInt()	            匹配任何int和非空Integer
         * anyList()	        匹配任何非空List
         * anyLong()	        匹配任何long和非空Long
         * anyMap()         	匹配任何非空Map
         * anyString()	        匹配任何非空String
         * contains(String substring)	参数包含给定的substring字符串
         * argThat(ArgumentMatcher <T> matcher)	创建自定义的参数匹配模式
         * inOrder(Object… mocks)	验证执行顺序
         *
         * reset(T … mocks)	重置Mock
         * spy(Class<T> classToSpy)	实现调用真实对象的实现
         * @InjectMocks注解    自动将模拟对象注入到被测试对象中
         */

        whenever(person.eat(any<String>())).thenReturn("真好吃")
        println(person.eat("吃饺子"))

        whenever(person.eat2(argThat(ArgumentMatcher {
            it.length == 3
        }))).thenReturn("吃对了")
        println(person.eat2("吃包子"))
    }

    @Test
    fun testOrder() {
        person.eat("")
        person.eat2("")

        //验证先执行eat2,再执行eat，会失败
        val order = inOrder(person)
        order.verify(person).eat2(any())
        order.verify(person).eat(any())
    }

}

interface Person {
    val name: String
    val age: Int
    val school: String

    fun eat(a: String): String
    fun eat2(a: String): String

    fun walk()

    fun drink(int: Int): String
}
