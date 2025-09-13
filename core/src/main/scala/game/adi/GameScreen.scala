package game.adi

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.{Gdx, Input, Screen}
import com.badlogic.gdx.graphics.{Color, Texture}
import com.badlogic.gdx.graphics.g2d.{Batch, BitmapFont}
import com.badlogic.gdx.utils.ScreenUtils

import scala.collection.mutable.ArrayBuffer
import scala.compiletime.uninitialized
import scala.util.Random


class GameScreen(game: Swerve) extends Screen {
    private var player: Player = new Player
    private val batch: Batch = game.batch
    private var playerImg: Texture = uninitialized
    private var smartEnemy: Enemy = uninitialized
    private var background: Background = uninitialized
    private var t1: Float = 0f
    private var tt1 = 2f
    private var model: Model = uninitialized
    private val centre = (Gdx.graphics.getWidth) / 2
    private val scoreLabel: BitmapFont = new BitmapFont()
    private val limit = 460
    private val scalef = 2.3f
    private var score: Int = 0
    private var highScore: Int = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")
    private val pauseScreen: PauseScreen = new PauseScreen(game, this)
    private var paused: Boolean = false
    private var innit: Boolean = false
    private val music: Music = game.music
    private var endTimer: Float = uninitialized
    private var addBG = false


    private val backgrounds = new ArrayBuffer[Background]()
    private val smartEnemies = new ArrayBuffer[Enemy]()

    def newBG(y: Float): Unit = {
        background = new Background
        background.initSprite(Assets.loadBackground(), 4)
        background.setY(y)
        backgrounds += background
    }

    override def show(): Unit = {
        music.setVolume(0.8f)
        if (!innit) {

            highScore = prefs.getInteger("highscore", 0)
            playerImg = Assets.loadPlayerSprite()
            player.initSprite(playerImg, scalef)

            model = Assets.loadModel()

            scoreLabel.getData.setScale(2f)

            newBG(0f)
            newBG(2160)
            innit = true


        }
    }

    override def render(v: Float): Unit = {

        ScreenUtils.clear(Color.valueOf("545454"))

        if (!paused) {
            batch.begin()
            backgrounds.foreach(b =>
                b.update(v)
                b.draw(batch)

                if (b.position().y <= -2160) {
                    addBG = true
                }
            )
            if (addBG) {
                newBG(backgrounds.last.position().y + 2160)
                addBG = false
            }
            backgrounds.filterInPlace(b =>
                b.position().y >= -2160
            )


            t1 += v

            if (t1 >= tt1) {
                spawnSmartEnemy()
                t1 = 0f
                tt1 = Random.between(1.8f, 2.4f)
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


            batch.end()

        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(pauseScreen)
            setPaused(true)
        }

        batch.begin()
        scoreLabel.draw(batch, "Press ESC to pause", 0, Gdx.graphics.getHeight)
        scoreLabel.draw(batch, f"Score : $score", 0, Gdx.graphics.getHeight - 50)
        scoreLabel.draw(batch, f"High Score : $highScore", 0, Gdx.graphics.getHeight - 80)
        player.update(v)
        player.draw(batch)
        batch.end()
    }


    def setPaused(p: Boolean): Unit = paused = p

    private def gameOver(): Unit = {
        setPaused(true)
        game.setScreen(new GameOverScreen(game))
        dispose()

    }

    private def spawnSmartEnemy(): Unit = {
        smartEnemy = new Enemy
        val ranx = Random.between(centre - limit, centre + limit - (30 * scalef))

        smartEnemy.setpos(ranx, Gdx.graphics.getHeight + 400)
        smartEnemy.initSprite(Assets.loadSmartEnemySprite(), scalef)
        smartEnemy.followPlayer()
        smartEnemies += smartEnemy
    }


    def getPlayer: Player = {
        player
    }
    def getBackground: ArrayBuffer[Background]={
        backgrounds
    }

    override def resume(): Unit = {}

    override def resize(i: Int, i1: Int): Unit = {}

    override def pause(): Unit = {}

    override def hide(): Unit = {}


    override def dispose(): Unit = {
        pauseScreen.dispose()
    }

}
