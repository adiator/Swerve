package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
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
    private var t1: Float = 0f
    private var t2: Float = 0f
    private var tt1 = 2f
    private var tt2 = 0.6f
    private var model: Model = uninitialized
    private val centre = (Gdx.graphics.getWidth) / 2
    private val scoreLabel: BitmapFont = new BitmapFont()
    private val limit = 200
    private val scalef = 0.6f
    private var score: Int = 0
    private var highScore: Int = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")


    private val smartEnemies = new ArrayBuffer[Enemy]()
    private val dumbEnemiesL = new ArrayBuffer[Enemy]()
    private val dumbEnemiesR = new ArrayBuffer[Enemy]()


    override def show(): Unit = {
        highScore = prefs.getInteger("highscore", 0)
        player = new Player
        playerImg = Assets.loadPlayerSprite()
        player.initSprite(playerImg, scalef)

        model = Assets.loadModel()

        scoreLabel.getData.setScale(2f)

    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        scoreLabel.draw(batch, f"Score : $score", 0, Gdx.graphics.getHeight - 20)
        scoreLabel.draw(batch, f"High Score : $highScore", 0, Gdx.graphics.getHeight - 50)
        player.update(v)
        player.draw(batch)

        t1 += v
        t2 += v
        if (t1 >= tt1) {
            spawnSmartEnemy()
            t1 = 0f
            tt1 = Random.between(1.8f, 2.4f)
        }
        if (t2 >= tt2) {
            spawnDumbEnemy()
            t2 = 0f
            tt2 = Random.between(0.5f, 0.8f)
        }

        smartEnemies.foreach(e =>
            e.update(v, player, model)
            e.draw(batch)
            if (e.sprite.getBoundingRectangle.overlaps(player.sprite.getBoundingRectangle)) {
                gameOver()
            }
            if (player.pos().y > (e.pos().y + 213 * scalef) && !e.overtaken) {
                e.overtaken = true
                score += 1
                if (score > highScore) {
                    prefs.putInteger("highscore", score)
                    prefs.flush()
                    highScore = score
                }
            }
        )
        smartEnemies.filterInPlace(e =>
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



    private def gameOver(): Unit = {
        game.setScreen(new GameOverScreen(game))
        dispose()
    }

    private def spawnSmartEnemy(): Unit = {
        smartEnemy = new Enemy
        val ranx = Random.between(centre - limit, centre + limit)

        smartEnemy.setpos(ranx, Gdx.graphics.getHeight)
        smartEnemy.initSprite(Assets.loadSmartEnemySprite(), scalef)
        smartEnemy.followPlayer()
        smartEnemies += smartEnemy
    }

    private def spawnDumbEnemy(): Unit = {
        dumbEnemyL = new Enemy
        dumbEnemyR = new Enemy
        val ranxL = Random.between(50, centre - (limit + 300))
        val ranxR = Random.between(centre + (limit + 300), centre * 2 - 50)

        dumbEnemyL.setpos(ranxL, Gdx.graphics.getHeight + 10)
        dumbEnemyL.initSprite(Assets.loadDumbEnemySprite(), scalef)
        dumbEnemiesL += dumbEnemyL

        dumbEnemyR.setpos(ranxR, Gdx.graphics.getHeight + 10)
        dumbEnemyR.initSprite(Assets.loadDumbEnemySprite(), scalef)
        dumbEnemiesR += dumbEnemyR
    }

    override def resume(): Unit = {}

    override def resize(i: Int, i1: Int): Unit = {}

    override def pause(): Unit = {}

    override def hide(): Unit = {}


    override def dispose(): Unit = {
    }

}
