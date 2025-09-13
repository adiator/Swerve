package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2

import scala.compiletime.uninitialized

class Background {
    private var bgSprite:Sprite = uninitialized
    private val x:Float = 0
    private var y:Float = 0

    private var scalef = 0

    def setY(y:Float): Unit = {
        this.y = y
    }
    def initSprite(tex:Texture, scale:Float): Unit = {
        bgSprite = new Sprite(tex)
        bgSprite.scale(scale)
        bgSprite.setOrigin(0, 0)
    }

    def draw(batch: Batch): Unit = {
        bgSprite.setPosition(x, y)
        bgSprite.draw(batch)
    }

    def update(dt:Float): Unit = {
        y -= 1100*dt
    }

    def position():Vector2 = {
        return new Vector2(x, y)
    }

}
