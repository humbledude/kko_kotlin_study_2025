package week2

class Person(
    val name: String,
    val age: Int,
) {
    fun introduce() = "안녕하세요, 제 이름은 $name 입니다. 나이는 $age 세입니다."
}

open class Animal(val name: String) {
    open fun sound() = "소리없음"
}

class Dog(name: String) : Animal(name) {
    override fun sound() = "멍멍"
}

interface Drivable {
    fun drive(): String
}

class Car(val model: String) : Drivable {
    override fun drive() = "$model 자동차가 달립니다."
}

fun main() {
    val person = Person("홍길동", 30)
    println(person.introduce())

    // named arguments 테스트 (순서 변경)
    val person2 = Person(age = 25, name = "김철수")
    println(person2.introduce())


    // 상속
    val animal = Animal("동물")
    println(animal.sound())
    val dog = Dog("강아지")
    println(dog.sound())

    val car = Car("테슬라 모델 Y")
    println(car.drive())
}