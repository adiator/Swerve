package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.{VisLabel, VisTextButton}
import com.badlogic.gdx.scenes.scene2d.ui.Table

import scala.compiletime.uninitialized


class GameOverScreen(game: Swerve) extends Screen {
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    var stage: Stage = uninitialized
    var table: Table = uninitialized
    var restart: VisTextButton = uninitialized
    var exit: VisTextButton = uninitialized
    var gameOver: VisLabel = uninitialized

    override def show(): Unit = {
        if (!VisUI.isLoaded) VisUI.load()
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)

        restart = VisTextButton("Restart")
        restart.setFocusBorderEnabled(true)
        restart.setScale(5)
        restart.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new GameScreen(game))
                dispose()
            }
        })
        exit = VisTextButton("Exit")
        exit.addListener(new ClickListener{
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                Gdx.app.exit()
                dispose()
                Assets.dispose()

            }
        })
        gameOver = new VisLabel("You crashed")
        gameOver.setFontScale(2)
        table = new Table()
        table.setFillParent(true)
        table.center()

        table.add(gameOver).padBottom(200).row()
        table.add(restart).width(300f).height(150f).pad(100).row()
        table.add(exit).width(150).height(75f)
        stage.addActor(table)

    }


    override def render(delta: Float): Unit = {
        ScreenUtils.clear(Color.DARK_GRAY)
        stage.act(delta)
        stage.draw()
    }

    override def resize(width: Int, height: Int): Unit = {}

    override def pause(): Unit = {}

    override def resume(): Unit = {}

    override def hide(): Unit = {}

    override def dispose(): Unit = {
        stage.dispose()
    }
}
