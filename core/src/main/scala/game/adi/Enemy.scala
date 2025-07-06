package game.adi

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2

import scala.compiletime.uninitialized

class Enemy {
    private var x: Float = uninitialized
    private var y: Float = uninitialized
    private var speedx: Float = uninitialized
    private val speedy: Float = 250
    var sprite: Sprite = uninitialized
    var velocity: Vector2 = uninitialized
    var follow: Boolean = false
    private val centre = (Gdx.graphics.getWidth)/2
    var overtaken: Boolean = false


    def followPlayer():Unit = {
        follow = true
    }
    def setpos(X: Float, Y: Float): Unit = {
        x = X
        y = Y
    }

    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
    }

    def draw(batch: Batch): Unit = {
        sprite.setPosition(x, y)
        sprite.draw(batch)
    }

    def x_speed(p: Player, m:Model): Float = {
        val dist = p.pos().sub(pos())
        val mag = dist.len()
        var vel = m.predict(mag)
        if (dist.x < 0) vel = -vel
        if (dist.x<5 && dist.x>(-5)){
            vel = 0
        }

        vel

    }

    private def updatevel(o: Vector2, n: Vector2): Unit = {
        velocity = n.sub(o)

    }

    def update(dt: Float, player: Player, model:Model): Unit = {
        y-=speedy*dt
        val oldPos = new Vector2(x, y)
        if(follow && player.pos().x>=centre-480 && player.pos().x<centre+480) {
            speedx = x_speed(player, model)
            x += speedx
        }
        val newPos = new Vector2(x, y)
        updatevel(oldPos, newPos)
    }

    def pos(): Vector2 = {
        new Vector2(x, y)
    }


}
