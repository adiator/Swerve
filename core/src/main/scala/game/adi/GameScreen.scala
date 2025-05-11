package game.adi

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.ScreenUtils
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class GameScreen(game: Swerve) extends Screen {
    var player: Player = _
    var batch: Batch = game.batch
    var playerImg: Texture = _
    var enemy: Enemy = _
    var t:Float=0

    val enemies = new ArrayBuffer[Enemy]()

    def spawnEnemy(): Unit = {
        enemy = new Enemy
        var ranx= Random().nextFloat()*(590-50)

        enemy.setpos(ranx, 460)
        enemy.initSprite(new Texture("Audi.png"), 0.4)
        enemies += enemy
    }

    override def show(): Unit = {
        player = new Player
        playerImg = new Texture("Police.png")
        player.initSprite(playerImg, 0.4)

    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        player.update(v)
        player.draw(batch)

        t+=v
        if(t>=1){
            spawnEnemy()
            t=0
        }
        enemies.foreach(e =>
            e.update(v)
            e.draw(batch)
            if(e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)){
                gameOver()
            }
        )
        enemies.filterInPlace(e=>
            e.pos().y >= 50
        )

        batch.end()
    }

    override def resume(): Unit = {}

    override def resize(i: Int, i1: Int): Unit = {}

    override def pause(): Unit = {}

    override def hide(): Unit = {}


    override def dispose(): Unit = {
        if (playerImg != null) playerImg.dispose()
    }

    def gameOver():Unit={
        game.setScreen(new GameOverScreen(game))
        dispose()
    }

}
