package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.{Intersector, Polygon, Vector2}
import com.badlogic.gdx.{Gdx, Input}

import scala.compiletime.uninitialized


class Player {
    private val width = Gdx.graphics.getWidth.toFloat
    private val height = Gdx.graphics.getHeight.toFloat
    private var x: Float = width/2
    private var y: Float = height/2
    private val speed = 275
    var sprite: Sprite = uninitialized
    var velocity: Vector2 = uninitialized
    private var scalef:Float = uninitialized
    private var collider:Polygon = uninitialized

    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        scalef = scale
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
        val w = sprite.getWidth
        val h = sprite.getHeight
        val verts = Array[Float](
            0.000f * w, 0.672f * h,
            0.036f * w, 0.922f * h,
            0.179f * w, 0.984f * h,
            0.786f * w, 0.984f * h,
            0.929f * w, 0.922f * h,
            0.964f * w, 0.672f * h,
            0.964f * w, 0.641f * h,
            0.929f * w, 0.047f * h,
            0.821f * w, 0.000f * h,
            0.143f * w, 0.000f * h,
            0.036f * w, 0.047f * h,
            0.000f * w, 0.641f * h
        )
        setCollisionShape(verts)

    }

    private def setCollisionShape(vertices:Array[Float]):Unit = {
        collider = new Polygon(vertices)
        collider.setOrigin(sprite.getOriginX, sprite.getOriginY)
        syncCollider()
    }
    private def updatevel(o: Vector2, n: Vector2): Unit = {
        velocity = n.sub(o)

    }

    private def syncCollider():Unit = {
        if (collider != null) {
            collider.setPosition(sprite.getX, sprite.getY)
        }
    }
    def update(dt: Float): Unit = {
        val oldPos = new Vector2(x, y)
        if (x >= 500) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * dt
        }
        if(x <= width-500-(28*scalef)){
            if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * dt
        }
        if (y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * dt
        }
        if(y <= height-(64*scalef)-100){
            if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * dt
        }
        val newPos = new Vector2(x, y)
        updatevel(oldPos, newPos)
    }

    def draw(batch: Batch): Unit = {
        sprite.setPosition(x, y)
        sprite.draw(batch)
        syncCollider()

    }
    
    def collides(other:Enemy): Boolean = {
        Intersector.overlapConvexPolygons(collider, other.getCollider())
    }
    def pos(): Vector2 = {
        new Vector2(x, y)
    }
}
