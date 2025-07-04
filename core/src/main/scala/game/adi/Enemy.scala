package game.adi

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2

class Enemy {
    private var x: Float = _
    private var y: Float = _
    private var speedx: Float = _
    private var speedy: Float = 250
    var sprite: Sprite = _
    var velocity: Vector2 = _
    var follow: Boolean = false
    private val centre = (Gdx.graphics.getWidth)/2


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
        if(follow && x>=centre-480) {
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
