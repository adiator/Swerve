package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.ScreenUtils

import scala.collection.mutable.ArrayBuffer
import scala.compiletime.uninitialized
import scala.util.Random



class GameScreen(game: Swerve) extends Screen {
    private var player: Player = uninitialized
    private val batch: Batch = game.batch
    private var playerImg: Texture = uninitialized
    private var smartEnemy: Enemy = uninitialized
    private var dumbEnemyL: Enemy = uninitialized
    private var dumbEnemyR: Enemy = uninitialized
    private var t1:Float=0
    private var t2:Float=0
    private var model:Model = uninitialized
    private val centre = (Gdx.graphics.getWidth)/2
    private val limit = 200
    private val scalef = 0.6f


    private val smartEnemies = new ArrayBuffer[Enemy]()
    private val dumbEnemiesL = new ArrayBuffer[Enemy]()
    private val dumbEnemiesR = new ArrayBuffer[Enemy]()

    private def spawnSmartEnemy(): Unit = {
        smartEnemy = new Enemy
        val ranx= Random.between(centre-limit, centre+limit)

        smartEnemy.setpos(ranx, Gdx.graphics.getHeight)
        smartEnemy.initSprite(new Texture("Audi.png"), scalef)
        smartEnemy.followPlayer()
        smartEnemies += smartEnemy
    }

    private def spawnDumbEnemy(): Unit = {
        dumbEnemyL = new Enemy
        dumbEnemyR = new Enemy
        val ranxL = Random.between(50, centre - (limit+300))
        val ranxR = Random.between(centre + (limit+300), centre*2-50)

        dumbEnemyL.setpos(ranxL, Gdx.graphics.getHeight)
        dumbEnemyL.initSprite(new Texture("Mini_truck.png"), scalef)
        dumbEnemiesL += dumbEnemyL

        dumbEnemyR.setpos(ranxR, Gdx.graphics.getHeight)
        dumbEnemyR.initSprite(new Texture("Mini_truck.png"), scalef)
        dumbEnemiesR += dumbEnemyR
    }



    override def show(): Unit = {
        player = new Player
        playerImg = new Texture("Police.png")
        player.initSprite(playerImg, scalef)

        model = new Model

    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        player.update(v)
        player.draw(batch)

        t1+=v
        t2+=v
        if(t1>=2){
            spawnSmartEnemy()
            t1=0
        }
        if(t2>=0.6){
            spawnDumbEnemy()
            t2 = 0
        }

        smartEnemies.foreach(e =>
            e.update(v, player, model)
            e.draw(batch)
            if(e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)){
                gameOver()
            }
        )
        smartEnemies.filterInPlace(e=>
            e.pos().y >= -150
        )

        dumbEnemiesL.foreach(e =>
            e.update(v, player, model)
            e.draw(batch)
            if (e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)) {
                gameOver()
            }
        )
        dumbEnemiesL.filterInPlace(e =>
            e.pos().y >= -150
        )

        dumbEnemiesR.foreach(e =>
            e.update(v, player, model)
            e.draw(batch)
            if (e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)) {
                gameOver()
            }
        )
        dumbEnemiesR.filterInPlace(e =>
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
