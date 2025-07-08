package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Gdx, Input}
import scala.compiletime.uninitialized


class Player {
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var x: Float = width/2
    private var y: Float = height/2
    private val speed = 200
    var sprite: Sprite = uninitialized
    var velocity: Vector2 = uninitialized
    private var scalef:Float = uninitialized

    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        scalef = scale
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
    }

    private def updatevel(o: Vector2, n: Vector2): Unit = {
        velocity = n.sub(o)

    }

    def update(dt: Float): Unit = {
        val oldPos = new Vector2(x, y)
        if (x >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * dt
        }
        if(x <= width-(98*scalef)-400){
            if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * dt
        }
        if (y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * dt
        }
        if(y <= height-(214*scalef)){
            if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * dt
        }
        val newPos = new Vector2(x, y)
        updatevel(oldPos, newPos)
    }

    def draw(batch: Batch): Unit = {
        sprite.setPosition(x, y)
        sprite.draw(batch)

    }

    def pos(): Vector2 = {
        new Vector2(x, y)
    }
}
