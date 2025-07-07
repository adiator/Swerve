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

class MainScreen(game:Swerve) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var stage: Stage = uninitialized
    private var table:Table = uninitialized
    private var startButton: VisTextButton = uninitialized
    private var label: VisLabel = uninitialized

    override def show(): Unit = {
        if(!VisUI.isLoaded) VisUI.load()
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        startButton = new VisTextButton("Start")
        startButton.addListener(new ClickListener{
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new GameScreen(game))
                dispose()

            }
        })

        table.add(startButton)
        table.center()
        table.setFillParent(true)
        stage.addActor(table)
    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
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
