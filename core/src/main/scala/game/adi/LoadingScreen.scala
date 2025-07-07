package game.adi

import com.badlogic.gdx.{Gdx, Screen}
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.ScreenUtils
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.{VisLabel, VisProgressBar}

import scala.compiletime.uninitialized

class LoadingScreen(game:Swerve) extends Screen{

    private val batch = game.batch
    private val font = new BitmapFont()
    private var time = 0f
    private var loaded = false
    private var stage: Stage = uninitialized
    private var table: Table = uninitialized
    private var progressBar: VisProgressBar = uninitialized
    private var label: VisLabel = uninitialized

    override def show(): Unit = {
        if (!VisUI.isLoaded) VisUI.load()
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)
        progressBar = new VisProgressBar(0f, 25f, 0.01f, false)
        progressBar.setValue(0)
        progressBar.setAnimateDuration(0.25f)
        label = new VisLabel("Loading")
        table = new Table()
        table.setFillParent(true)
        table.center()

        table.add(label).row()
        table.add(progressBar)

        stage.addActor(table)
    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        stage.act(v)
        stage.draw()

        time += v
        if(time>=5){
            if (!loaded) {
                Assets.load()
                loaded = true
            }
            if(Assets.loaded) time = 18f; Assets.loaded = false
        }
        progressBar.setValue(time)
        if(time>=25f){
            game.setScreen(new MainScreen(game))
            dispose()
        }
    }

    override def resize(width: Int, height: Int): Unit = {}

    override def pause(): Unit = {}

    override def resume(): Unit = {}

    override def hide(): Unit = {}

    override def dispose(): Unit = {
        stage.dispose()
    }
}
