package game.adi

import com.badlogic.gdx.{Gdx, Input, Screen}
import com.badlogic.gdx.graphics.{Color, GL20}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.utils.ScreenUtils

class GameOverScreen(game: Swerve) extends Screen {
    val batch: SpriteBatch = game.batch
    val font = new BitmapFont()
    private val width = Gdx.graphics.getWidth
    private val height = Gdx.graphics.getHeight

    override def show(): Unit = {}

    override def render(delta: Float): Unit = {
        ScreenUtils.clear(Color.BLACK)
        batch.begin()
        font.getData().setScale(1.5)
        font.setColor(Color.WHITE)
        font.draw(batch, "Game Over", width/2, height/2)
        font.draw(batch, "Press SPACE to restart", width/2-40, height/2-100)
        batch.end()

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game))
            dispose()
        }
    }

    override def resize(width: Int, height: Int): Unit = {}
    override def pause(): Unit = {}
    override def resume(): Unit = {}
    override def hide(): Unit = {}
    override def dispose(): Unit = {
        font.dispose()
    }
}
