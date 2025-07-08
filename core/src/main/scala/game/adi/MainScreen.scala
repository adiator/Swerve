package game.adi

import com.badlogic.gdx.{Gdx, Input, Screen}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.{VisLabel, VisTextButton}
import com.badlogic.gdx.scenes.scene2d.ui.Table

import scala.compiletime.uninitialized

class MainScreen(game:Swerve) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var stage: Stage = uninitialized
    private var table:Table = uninitialized
    private var aboutTable: Table = uninitialized
    private var startButton: VisTextButton = uninitialized
    private var label: VisLabel = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")
    private var highScore: Int = uninitialized
    private var hsLabel: VisLabel = uninitialized
    private var exitButton: VisTextButton = uninitialized
    private var aboutButton: VisTextButton = uninitialized

    override def show(): Unit = {
        if(!VisUI.isLoaded) VisUI.load()
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        aboutTable = new Table()
        startButton = new VisTextButton("Start")
        startButton.addListener(new ClickListener{
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new GameScreen(game))
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
        aboutButton = new VisTextButton("About")
        aboutButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new AboutScreen(game))
                dispose()

            }
        })
        highScore = prefs.getInteger("highscore", 0)
        hsLabel = new VisLabel(f"Highscore : $highScore")
        hsLabel.setFontScale(3)
        aboutTable.left().top()
        aboutTable.setFillParent(true)
        aboutTable.add(aboutButton).width(150f).height(75f)

        table.center()
        table.setFillParent(true)

        table.add(hsLabel).padBottom(200).row()
        table.add(startButton).width(300f).height(150f).row()
        table.add(exitButton).width(150).height(75f)
        stage.addActor(aboutTable)
        stage.addActor(table)

    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.DARK_GRAY)
        stage.act(v)
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
