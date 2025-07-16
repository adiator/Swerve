package game.adi

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

import scala.compiletime.uninitialized

class Divider{
    private var x: Float = uninitialized
    private var y: Float = uninitialized
    private var shapeRenderer: ShapeRenderer = uninitialized

    def innit():Unit ={
        shapeRenderer = new ShapeRenderer()
    }
    def setPos(x:Float, y:Float):Unit = {
        this.x = x
        this.y = y
    }
    def draw():Unit = {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(Color.WHITE)
        shapeRenderer.rect(x, y, 20, 150)
        shapeRenderer.end()
    }

    def update(dt:Float):Unit = {
        y -= 1200*dt
        draw()
    }

    def pos():Vector2={
        new Vector2(x, y)
    }

    def dispose():Unit = {
        shapeRenderer.dispose()
    }
}
