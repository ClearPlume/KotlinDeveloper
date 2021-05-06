package seamcarving

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter rectangle width:")
    val width = scanner.nextInt()

    println("Enter rectangle height:")
    val height = scanner.nextInt()

    val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)

    println("Enter output image name:")
    val imgName = scanner.next()

    val graphics = image.createGraphics()
    graphics.color = Color.RED
    graphics.drawLine(0, 0, width - 1, height - 1)
    graphics.drawLine(width - 1, 0, 0, height - 1)

    ImageIO.write(image, "png", File(imgName))
}
