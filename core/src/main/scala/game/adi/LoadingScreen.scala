package game.adi

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.ScreenUtils

class LoadingScreen(game:Swerve) extends Screen{

    private val batch = game.batch
    private val font = new BitmapFont()
    private var time = 0f
    private var loaded = false
    override def show(): Unit = {
    }

    override def render(v: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        font.draw(batch, "Loading", 950, 500)
        batch.end()

        time += v
        if(time>=2){
            if (!loaded) {
                Assets.load()
                loaded = true
            }
        }
        if(time>=6f){
            game.setScreen(new GameScreen(game))
            dispose()
        }
    }

    override def resize(width: Int, height: Int): Unit = {}

    override def pause(): Unit = {}

    override def resume(): Unit = {}

    override def hide(): Unit = {}

    override def dispose(): Unit = font.dispose()
}
