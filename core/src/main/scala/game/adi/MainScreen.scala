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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.Actor

import scala.compiletime.uninitialized

class MainScreen(game:Swerve) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var stage: Stage = uninitialized
    private var table:Table = uninitialized
    private var aboutTable: Table = uninitialized
    private var musicTable: Table = uninitialized
    private var startButton: VisTextButton = uninitialized
    private var label: VisLabel = uninitialized
    private val prefs = Gdx.app.getPreferences("profile")
    private var highScore: Int = uninitialized
    private var hsLabel: VisLabel = uninitialized
    private var exitButton: VisTextButton = uninitialized
    private var aboutButton: VisTextButton = uninitialized
    private val music:Music = game.music
    private var musicButton: VisTextButton = uninitialized
    var musicOn = true

    override def show(): Unit = {
        if(!VisUI.isLoaded) VisUI.load()
        music.setVolume(0.4f)
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        aboutTable = new Table()
        musicTable = new Table()
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
        musicButton = new VisTextButton("Music off")
        musicButton.addListener(
            new ChangeListener {
                override def changed(changeEvent: ChangeListener.ChangeEvent, actor: Actor): Unit = {
                    musicOn = !musicOn
                    if (musicOn) {
                        music.play()
                        musicButton.setText("Music off")
                    } else {
                        music.pause()
                        musicButton.setText("Music on")
                    }

                }
            }
        )
        highScore = prefs.getInteger("highscore", 0)
        hsLabel = new VisLabel(f"Highscore : $highScore")
        hsLabel.setFontScale(3)
        aboutTable.left().top()
        aboutTable.setFillParent(true)
        aboutTable.add(aboutButton).width(150f).height(75f)

        musicTable.center().right()
        musicTable.setFillParent(true)
        musicTable.add(musicButton).width(250f).height(125f)

        table.center()
        table.setFillParent(true)

        table.add(hsLabel).padBottom(200).row()
        table.add(startButton).width(300f).height(150f).row()
        table.add(exitButton).width(150).height(75f)


        stage.addActor(aboutTable)
        stage.addActor(musicTable)
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
