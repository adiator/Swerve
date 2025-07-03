package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.ScreenUtils

import scala.collection.mutable.ArrayBuffer
import scala.compiletime.uninitialized
import scala.util.Random



class GameScreen(game: Swerve) extends Screen {
    var player: Player = uninitialized
    var batch: Batch = game.batch
    var playerImg: Texture = uninitialized
    var enemy: Enemy = uninitialized
    var t:Float=0
    var model:Model = uninitialized

    private val enemies = new ArrayBuffer[Enemy]()

    private def spawnEnemy(): Unit = {
        enemy = new Enemy
        val ranx= Random().nextFloat()*(Gdx.graphics.getWidth-50)

        enemy.setpos(ranx, Gdx.graphics.getHeight)
        enemy.initSprite(new Texture("Audi.png"), 0.4)
        enemies += enemy
    }

    override def show(): Unit = {
        player = new Player
        playerImg = new Texture("Police.png")
        player.initSprite(playerImg, 0.4)

        model = new Model

    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        player.update(v)
        player.draw(batch)

        t+=v
        if(t>=2){
            spawnEnemy()
            t=0
        }
        enemies.foreach(e =>
            e.update(v, player, model)
            e.draw(batch)
            if(e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)){
                gameOver()
            }
        )
        enemies.filterInPlace(e=>
            e.pos().y >= -150
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

    private def gameOver():Unit={
        game.setScreen(new GameOverScreen(game))
        dispose()
    }

}
