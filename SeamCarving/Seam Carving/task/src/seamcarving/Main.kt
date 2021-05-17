package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt

const val IN = "in"
const val OUT = "out"

val validCommands = arrayOf(IN, OUT)

val <T> Array<Array<T>>.row: Int
    get() = size

val <T> Array<Array<T>>.col: Int
    get() = this[0].size

fun main(args: Array<String>) {
    val commands = args.parse()

    val img = ImageIO.read(File(commands[IN]!!))
    // img [After] operation
    val imgA = BufferedImage(img.width, img.height, BufferedImage.TYPE_INT_RGB)

    seam(img, imgA)

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

fun BufferedImage.energies(): Array<Array<Double>> {
    val energies = Array(height) { Array(width) { 0.0 } }

    exec { x, y, _ ->
        val xL: Int
        val xR: Int
        val yU: Int
        val yD: Int

        when (x) {
            width - 1 -> {
                xL = x - 2
                xR = x
            }
            0 -> {
                xL = 0
                xR = 2
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
            height - 1 -> {
                yU = y - 2
                yD = y
            }
            else -> {
                yU = y - 1
                yD = y + 1
            }
        }

        val lColor = this[xL, y]
        val rColor = this[xR, y]
        val uColor = this[x, yU]
        val dColor = this[x, yD]

        val xGradient = lColor..rColor
        val yGradient = uColor..dColor
        val energy = sqrt(xGradient + yGradient)

        energies[y][x] = energy
    }

    return energies
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

/**
 * Functions for previous stages
 */
fun negative(img: BufferedImage, imgA: BufferedImage) {
    img.exec { x, y, color -> imgA[x, y] = color.negative() }
}

fun energies(img: BufferedImage, imgA: BufferedImage) {
    val energies = img.energies()

    val maxEnergy = energies.map { row -> row.maxOf { it } }.maxOf { it }

    img.exec { x, y, _ ->
        val intensity = (energies[y][x] * 255 / maxEnergy).toInt()
        imgA[x, y] = Color(intensity, intensity, intensity)
    }
}

fun seam(img: BufferedImage, imgA: BufferedImage) {
    val energies = img.energies()
    val seamEnergies = Array(energies.row) { Array(energies.col) { SeamEnergyWithBackPointer() } }
    img.exec { x, y, color -> imgA[x, y] = color }

    for (col in energies[0].indices) {
        seamEnergies[0][col].energy = energies[0][col]
        seamEnergies[0][col].x = col
    }

    for (row in 1 until energies.row) {
        for (col in 0 until energies.col) {
            val left = if (col == 0) {
                0
            } else {
                col - 1
            }

            val right: Int = if (col == energies.col - 1) {
                energies.col - 1
            } else {
                col + 1
            }

            val prevRowSeams = mutableMapOf<Int, Double>()

            for (i in left..right) {
                prevRowSeams[i] = seamEnergies[row - 1][i].energy + energies[row][col]
            }

            val backPointer = prevRowSeams.minByOrNull { it.value }!!
            seamEnergies[row][col].energy = backPointer.value
            seamEnergies[row][col].prevX = backPointer.key
            seamEnergies[row][col].x = col
        }
    }

    var seam = seamEnergies.last().minByOrNull { it.energy }!!
    var row = energies.row - 1

    do {
        imgA[seam.x, row--] = Color.RED
        seam = if (row < 0) {
            seamEnergies[0][seam.x]
        } else {
            seamEnergies[row][seam.prevX]
        }
    } while (row >= 0)
}

class SeamEnergyWithBackPointer {
    var energy = 0.0
    var prevX = -1
    var x = -1

    override fun toString(): String {
        return "x: $x, energy:  $energy, prevX: $prevX"
    }
}
