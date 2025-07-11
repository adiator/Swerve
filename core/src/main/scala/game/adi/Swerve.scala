package game.adi

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.{Game, Gdx}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.kotcrab.vis.ui.VisUI

import java.awt.Color
import scala.compiletime.uninitialized

class Swerve extends Game{
    var batch:SpriteBatch = uninitialized
    var music:Music = uninitialized
    override def create(): Unit = {
        batch = new SpriteBatch()
        music = Gdx.audio.newMusic(Gdx.files.internal("Eurobeat.mp3"))
        VisUI.load(Gdx.files.internal("skins/star-soldier-ui.json"))
        setScreen(new LoadingScreen(this))
    }


    override def render(): Unit = super.render()

    override def dispose(): Unit = if(batch!=null) {
        batch.dispose()
        VisUI.dispose()
        music.dispose()
    }
}
