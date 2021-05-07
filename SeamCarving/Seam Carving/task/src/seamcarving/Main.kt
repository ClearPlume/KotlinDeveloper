package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt

const val IN = "in"
const val OUT = "out"

val validCommands = arrayOf(IN, OUT)

fun main(args: Array<String>) {
    val commands = args.parse()

    val img = ImageIO.read(File(commands[IN]!!))
    val imgA = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_RGB)

    val energies = mutableListOf<Double>()

    img.exec { x, y, _ ->
        val xL: Int
        val xR: Int
        val yU: Int
        val yD: Int

        when (x) {
            0 -> {
                xL = 0
                xR = 2
            }
            img.width - 1 -> {
                xL = x - 2
                xR = x
            }
            else -> {
                xL = x - 1
                xR = x + 1
            }
        }

        when (y) {
            0 -> {
                yU = 0
                yD = 2
            }
            img.height - 1 -> {
                yU = y - 2
                yD = y
            }
            else -> {
                yU = y - 1
                yD = y + 1
            }
        }

        val lColor = img[xL, y]
        val rColor = img[xR, y]
        val uColor = img[x, yU]
        val dColor = img[x, yD]

        val xGradient = lColor..rColor
        val yGradient = uColor..dColor
        val energy = sqrt(xGradient + yGradient)

        energies.add(energy)
    }

    val maxEnergy = energies.maxOf { it }
    var index = 0

    img.exec { x, y, _ ->
        val intensity = (energies[index++] * 255 / maxEnergy).toInt()
        imgA[x, y] = Color(intensity, intensity, intensity)
    }

    ImageIO.write(imgA, "png", File(commands[OUT]!!))
}

fun Array<String>.parse(): Map<String, String> {
    val commands = mutableMapOf<String, String>()

    for (i in indices) {
        if (this[i].startsWith('-')) {
            if (this[i].substring(1) in validCommands) {
                if (i < lastIndex) {
                    if (this[i + 1].matches(Regex("[\\w\\-/]+\\.[a-zA-Z]{1,5}"))) {
                        commands[this[i].substring(1)] = this[i + 1]
                    }
                }
            }
        }
    }

    return commands
}

inline fun BufferedImage.exec(operation: (Int, Int, Color) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            operation(x, y, Color(getRGB(x, y)))
        }
    }
}

operator fun BufferedImage.get(x: Int, y: Int): Color {
    return Color(getRGB(x, y))
}

operator fun BufferedImage.set(x: Int, y: Int, color: Color) {
    return setRGB(x, y, color.rgb)
}

fun Color.negative() = Color(255 - red, 255 - green, 255 - blue)

/**
 * calc gradient of 2 colors
 */
operator fun Color.rangeTo(other: Color): Double {
    val r = red - other.red
    val g = green - other.green
    val b = blue - other.blue

    return (r * r + g * g + b * b).toDouble()
}
