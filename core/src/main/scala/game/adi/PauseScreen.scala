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

class PauseScreen(game:Swerve, gameScreen:GameScreen) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var stage: Stage = new Stage()
    private var table: Table = uninitialized
    private var resumeButton: VisTextButton = uninitialized
    private var label: VisLabel = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")
    private var highScore: Int = uninitialized
    private var hsLabel: VisLabel = uninitialized
    var exit: VisTextButton = uninitialized

    exit = VisTextButton("Exit")
    exit.addListener(new ClickListener {
        override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            Gdx.app.exit()
            dispose()
            Assets.dispose()

        }
    })

    override def show(): Unit = {
        if (!VisUI.isLoaded) VisUI.load()
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        resumeButton = new VisTextButton("Resume")
        resumeButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(gameScreen)
                gameScreen.setPaused(false)
            }
        })


        highScore = prefs.getInteger("highscore", 0)
        hsLabel = new VisLabel(f"Highscore : $highScore")
        hsLabel.setFontScale(3)

        table.add(hsLabel).padBottom(200).row()
        table.add(resumeButton).width(300f).height(150f).pad(100).row()
        table.add(exit).width(150).height(75f)
        table.center()
        table.setFillParent(true)
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
