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
        aboutLabel.setFontScale(3)

        copyRightLabel = new VisLabel("Copyright © 2025 Aditya Pant")


        skinCredit = new VisLabel("UI Skin: Star Soldier by Ray3K, licensed under ")
        licenseLink = new LinkLabel("CC BY 4.0", "https://creativecommons.org/licenses/by/4.0/")

        githubLink = new LinkLabel("Source Code", "https://github.com/Aidont/Swerve?tab=readme-ov-file")



        table.add(aboutLabel).padLeft(100).padBottom(200).row()
        table.add(copyRightLabel).padLeft(100).padBottom(200).row()
        table.add(skinCredit).padBottom(150)
        table.add(licenseLink).padBottom(150).row()
        table.add(githubLink).padLeft(100).padBottom(150).row()
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
