package seamcarving

import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

const val IN = "in"
const val OUT = "out"

val validCommands = arrayOf(IN, OUT)

fun main(args: Array<String>) {
    val commands = args.parse()

    val img = ImageIO.read(File(commands[IN]!!))

    for (y in 0 until img.height) {
        for (x in 0 until img.width) {
            val color = Color(img.getRGB(x, y))
            img.setRGB(x, y, color.negative().rgb)
        }
    }

    ImageIO.write(img, "png", File(commands[OUT]!!))
}

fun Array<String>.parse(): Map<String, String> {
    val commands = mutableMapOf<String, String>()

    for (i in indices) {
        if (this[i].startsWith('-')) {
            if (this[i].substring(1) in validCommands) {
                if (i < lastIndex) {
                    if (this[i + 1].matches(Regex("[a-zA-Z0-9_\\-/]+\\.[a-zA-Z]{1,5}"))) {
                        commands[this[i].substring(1)] = this[i + 1]
                    }
                }
            }
        }
    }

    return commands
}

fun Color.negative() = Color(255 - red, 255 - green, 255 - blue)
