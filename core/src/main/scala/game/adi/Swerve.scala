package game.adi

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.kotcrab.vis.ui.VisUI

import scala.compiletime.uninitialized

class Swerve extends Game{
    var batch:SpriteBatch = uninitialized
    override def create(): Unit = {
        batch = new SpriteBatch()
        VisUI.load()
        setScreen(new LoadingScreen(this))
    }

    override def render(): Unit = super.render()

    override def dispose(): Unit = if(batch!=null) batch.dispose();VisUI.dispose()
}
