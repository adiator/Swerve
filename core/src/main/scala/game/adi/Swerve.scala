package game.adi

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class Swerve extends Game{
    var batch:SpriteBatch = _
    override def create(): Unit = {
        batch = new SpriteBatch()
        setScreen(new GameScreen(this))
    }

    override def render(): Unit = super.render()

    override def dispose(): Unit = if(batch!=null) batch.dispose()
}
