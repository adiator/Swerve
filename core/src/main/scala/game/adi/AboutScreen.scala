package game.adi

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.{Gdx, Screen}
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.{VisLabel, VisTextButton}
import com.kotcrab.vis.ui.widget.LinkLabel

import scala.compiletime.uninitialized

class AboutScreen(game:Swerve) extends Screen{
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight
    private var stage: Stage = uninitialized
    private var table: Table = uninitialized
    private var aboutLabel: VisLabel = uninitialized
    private var skinCredit: VisLabel = uninitialized
    private var licenseLink: LinkLabel = uninitialized
    private var copyRightLabel: VisLabel = uninitialized
    private var backButton: VisTextButton = uninitialized
    private var githubLink: LinkLabel = uninitialized
    private var songCredits : VisLabel = uninitialized
    private var songCredits2 : VisLabel = uninitialized
    private var songCredits3 : VisLabel = uninitialized
    private val padding:Int = 75
    override def show(): Unit = {
        if (!VisUI.isLoaded) VisUI.load()
        stage = new Stage()
        Gdx.input.setInputProcessor(stage)
        table = new Table()
        backButton = VisTextButton("Back")
        backButton.addListener(new ClickListener {
            override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
                game.setScreen(new MainScreen(game))
                dispose()

            }
        })


        table.center()
        table.setFillParent(true)


        aboutLabel = new VisLabel("About")
        aboutLabel.setFontScale(2)

        copyRightLabel = new VisLabel("Copyright © 2025 Aditya Pant")


        skinCredit = new VisLabel("UI Skin: Star Soldier by Ray3K, licensed under ")
        licenseLink = new LinkLabel("CC BY 4.0", "https://creativecommons.org/licenses/by/4.0/")

        songCredits = new VisLabel("Music by:")
        songCredits2 = new LinkLabel("https://www.steven-obrien.net/", "https://www.steven-obrien.net/")
        songCredits3 = new VisLabel(" 80s Synthpop Experiment - Steven O'Brien")

        githubLink = new LinkLabel("Source Code", "https://github.com/Aidont/Swerve?tab=readme-ov-file")



        table.add(aboutLabel).padLeft(100).padBottom(padding+50).row()
        table.add(copyRightLabel).padLeft(100).padBottom(padding).row()
        table.add(skinCredit).padBottom(padding)
        table.add(licenseLink).padBottom(padding).row()
        table.add(songCredits).padLeft(-350).padBottom(padding)
        table.add(songCredits2).padLeft(-550).padBottom(padding).row()
        table.add(songCredits3).padLeft(120).padBottom(padding).row()
        table.add(githubLink).padLeft(100).padBottom(padding).row()
        table.add(backButton).padLeft(100).width(150).height(75f)
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
