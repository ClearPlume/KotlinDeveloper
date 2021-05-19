package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt

const val IN = "in"
const val OUT = "out"
const val WIDTH = "width"
const val HEIGHT = "height"

val validCommands = arrayOf(IN, OUT, WIDTH, HEIGHT)

val <T> ArrayList<ArrayList<T>>.row: Int
    get() = size

val <T> ArrayList<ArrayList<T>>.col: Int
    get() = this[0].size

fun main(args: Array<String>) {
    val commands = args.parse()

    var img = ImageIO.read(File(commands[IN]!!))

    repeat(commands[HEIGHT]!!.toInt()) {
        img = seam(img, Direction.HORIZONTAL)
    }

    repeat(commands[WIDTH]!!.toInt()) {
        img = seam(img, Direction.VERTICAL)
    }

    ImageIO.write(img, "png", File(commands[OUT]!!))
}

fun Array<String>.parse(): Map<String, String> {
    val commands = mutableMapOf<String, String>()

    for (i in indices) {
        if (this[i].startsWith('-')) {
            if (this[i].substring(1) in validCommands) {
                if (i < lastIndex) {
                    if (this[i + 1].matches(Regex("([\\w\\-/]+\\.[a-zA-Z]{1,5})|\\d+"))) {
                        commands[this[i].substring(1)] = this[i + 1]
                    }
                }
            }
        }
    }

    return commands
}

inline fun <T> ArrayList<ArrayList<T>>.init(row: Int, col: Int, ele: () -> T): ArrayList<ArrayList<T>> {
    repeat(row) {
        val rowArr = ArrayList<T>()
        repeat(col) {
            rowArr.add(ele())
        }
        add(rowArr)
    }

    return this
}

fun <T> ArrayList<ArrayList<T>>.transposed(): ArrayList<ArrayList<T>> {
    val transposed = ArrayList<ArrayList<T>>(row).init(col, row) { Any() as T }

    for (row in 0 until row) {
        for (col in 0 until col) {
            transposed[col][row] = this[row][col]
        }
    }

    return transposed
}

inline fun BufferedImage.exec(operation: (Int, Int, Color) -> Unit) {
    for (y in 0 until height) {
        for (x in 0 until width) {
            operation(x, y, Color(getRGB(x, y)))
        }
    }
}

fun BufferedImage.energies(horizontal: Boolean = false): ArrayList<ArrayList<Double>> {
    val energies = ArrayList<ArrayList<Double>>().init(height, width) { 0.0 }

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

    return if (horizontal) {
        energies.transposed()
    } else {
        energies
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

fun seam(img: BufferedImage, direction: Direction = Direction.VERTICAL): BufferedImage {
    return seam(img, direction == Direction.HORIZONTAL)
}

fun seam(img: BufferedImage, horizontal: Boolean): BufferedImage {
    val energies = img.energies(horizontal)
    val seamEnergies = ArrayList<ArrayList<SeamEnergyWithBackPointer>>().init(
        energies.row,
        energies.col
    ) { SeamEnergyWithBackPointer() }
    val imgA = if (horizontal) {
        BufferedImage(img.width, img.height - 1, img.type)
    } else {
        BufferedImage(img.width - 1, img.height, img.type)
    }

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

    if (horizontal) {
        var x = 0
        do {
            for (xI in 0 until img.height) {
                if (xI != seam.x) {
                    imgA[row, x++] = img[row, xI]
                }
            }
            x = 0
            row--
            seam = if (row < 0) {
                seamEnergies[0][seam.x]
            } else {
                seamEnergies[row][seam.prevX]
            }
        } while (row >= 0)
    } else {
        var x = 0
        do {
            for (xI in 0 until img.width) {
                if (xI != seam.x) {
                    imgA[x++, row] = img[xI, row]
                }
            }
            x = 0
            row--
            seam = if (row < 0) {
                seamEnergies[0][seam.x]
            } else {
                seamEnergies[row][seam.prevX]
            }
        } while (row >= 0)
    }

    return imgA
}

enum class Direction { HORIZONTAL, VERTICAL }

data class SeamEnergyWithBackPointer(var energy: Double = 0.0, var prevX: Int = -1, var x: Int = -1)
