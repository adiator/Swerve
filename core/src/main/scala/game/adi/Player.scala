package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.{Gdx, Input}

import java.awt.Rectangle
import javax.swing.Renderer

class Player {
    private var x :Float= 200
    private var y :Float= 100
    private var speed = 150
    var sprite:Sprite =_

    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
    }
    def update(dt:Float): Unit = {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= speed * dt
        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += speed * dt
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += speed * dt
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= speed * dt
    }

    def draw(batch: Batch):Unit ={
        sprite.setPosition(x, y)
        sprite.draw(batch)
    }

//    def render(renderer: ShapeRenderer): Unit ={
//        renderer.rect(x, y, 25, 50)
//    }

    def pos():Vector2={
        return new Vector2(x, y)
    }
}
