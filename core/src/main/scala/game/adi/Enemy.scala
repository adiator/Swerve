package game.adi

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.{Polygon, Vector2}

import scala.compiletime.uninitialized

class Enemy(game: GameScreen) {
    private var x: Float = uninitialized
    private var y: Float = uninitialized
    private var speedx: Float = uninitialized
    private var speedy: Float = 450
    var sprite: Sprite = uninitialized
    var velocity: Vector2 = uninitialized
    var follow: Boolean = false
    private val centre = (game.getViewport.getWorldWidth)/2
    var overtaken: Boolean = false
    private var collider: Polygon = uninitialized

    def followPlayer():Unit = {
        follow = true
    }
    def setpos(X: Float, Y: Float): Unit = {
        x = X
        y = Y
    }

    def setCollisionShape(vertices: Array[Float]): Unit = {
        collider = new Polygon(vertices)
        collider.setOrigin(sprite.getOriginX, sprite.getOriginY)
        syncCollider()
    }

    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
        val w = sprite.getWidth
        val h = sprite.getHeight
        val verts = Array[Float](
            0.000f * w, 0.730f * h,
            0.034f * w, 0.952f * h,
            0.138f * w, 1.000f * h,
            0.862f * w, 1.000f * h,
            0.966f * w, 0.952f * h,
            1.000f * w, 0.730f * h,
            1.000f * w, 0.698f * h,
            0.966f * w, 0.032f * h,
            0.897f * w, 0.000f * h,
            0.103f * w, 0.000f * h,
            0.034f * w, 0.032f * h,
            0.000f * w, 0.698f * h
        )
        setCollisionShape(verts)
    }

    private def syncCollider(): Unit = {
        if (collider != null) {
            collider.setPosition(sprite.getX, sprite.getY)
        }
    }


    def draw(batch: Batch): Unit = {
        sprite.setPosition(x, y)
        sprite.draw(batch)
        syncCollider()
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
    def setYSpeed(s:Float):Unit = {
        speedy = s
    }
    private def updatevel(o: Vector2, n: Vector2): Unit = {
        velocity = n sub o

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

    def getCollider():Polygon = {
        collider
    }

}
