package pride

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PrideApplication

fun main(args: Array<String>) {
    runApplication<PrideApplication>(*args)
} 