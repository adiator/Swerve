package game.adi

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.{Gdx, Input, Screen}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.{VisLabel, VisTextButton}
import com.badlogic.gdx.scenes.scene2d.ui.Table

import scala.collection.mutable.ArrayBuffer
import scala.compiletime.uninitialized

class PauseScreen(game:Swerve, gameScreen:GameScreen) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private val stage: Stage = new Stage()
    private var table: Table = uninitialized
    private var resumeButton: VisTextButton = uninitialized
    private var label: VisLabel = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")
    private var highScore: Int = uninitialized
    private var hsLabel: VisLabel = uninitialized
    private var exitButton: VisTextButton = uninitialized
    private var mainMenuButton: VisTextButton = uninitialized
    private var player: Player = uninitialized
    private var background:ArrayBuffer[Background] = uninitialized

    private val music:Music = game.music



    override def show(): Unit = {
        player = gameScreen.getBackground
        background = gameScreen.getBackground
        if (!VisUI.isLoaded) VisUI.load()
        music.setVolume(0.4f)
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        resumeButton = new VisTextButton("Resume")
        resumeButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                showGame()
            }
        })

        mainMenuButton = VisTextButton("Home")
        mainMenuButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new MainScreen(game))
                dispose()
            }
        })

        exitButton = VisTextButton("Exit")
        exitButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                Gdx.app.exit()
                dispose()
                Assets.dispose()

            }
        })

        highScore = prefs.getInteger("highscore", 0)
        hsLabel = new VisLabel(f"Highscore : $highScore")
        hsLabel.setFontScale(3)

        table.add(hsLabel).padBottom(200).row()
        table.add(resumeButton).width(300f).height(150f).pad(100).row()
        table.add(mainMenuButton).width(150).height(75f).row()
        table.add(exitButton).width(150).height(75f)
        table.center()
        table.setFillParent(true)
        stage.addActor(table)



    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.valueOf("2a2a38"))
        game.batch.begin()
        background.foreach(b=>
            b.draw(game.batch)
        )
        player.draw(game.batch)
        game.batch.end()
        stage.act(v)
        stage.draw()
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            showGame()
        }

    }

    private def showGame():Unit={
        game.setScreen(gameScreen)
        gameScreen.setPaused(false)
    }

    override def resize(width: Int, height: Int): Unit = {}

    override def pause(): Unit = {}

    override def resume(): Unit = {}

    override def hide(): Unit = {}

    override def dispose(): Unit = {
        stage.dispose()
    }
}
