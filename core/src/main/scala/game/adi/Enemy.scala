package game.adi

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{Batch, Sprite}
import com.badlogic.gdx.math.Vector2

class Enemy {
    private var x:Float=_
    private var y:Float=_
    private var speed:Int=200
    var sprite:Sprite=_
    
    def setpos(X:Float, Y:Float):Unit={
        x=X
        y=Y
    }
    def initSprite(texture: Texture, scale: Float): Unit = {
        sprite = new Sprite(texture)
        sprite.setSize(texture.getWidth * scale, texture.getHeight * scale)
        sprite.setOriginCenter()
    }

    def draw(batch: Batch):Unit={
        sprite.setPosition(x,y)
        sprite.draw(batch)
    }

    def update(dt:Float):Unit={
        y-=speed*dt
    }

    def pos(): Vector2 = {
        return new Vector2(x, y)
    }

}
