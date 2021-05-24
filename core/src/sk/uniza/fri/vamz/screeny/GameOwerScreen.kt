package sk.uniza.fri.vamz.screeny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import sk.uniza.fri.vamz.Mazoorek

/**
 * Game ower screen
 * Screen which is displayed when game is ower
 * @property pgame
 * @constructor Create empty Game ower screen
 */
class GameOwerScreen(val pgame: Mazoorek) : Screen {
    private var game: Mazoorek = pgame
    private var batch = pgame.batch
    private var camera = OrthographicCamera()
    private var restart = Rectangle()
    private var menu = Rectangle()
    private var touch = Vector3()

    private var restarT = Texture(Gdx.files.internal("BTNS/restartReleased.png"))
    private var menuT = Texture(Gdx.files.internal("BTNS/menuReleased.png"))
    private var restartTT = Texture(Gdx.files.internal("BTNS/restartPressed.png"))
    private var menuTT = Texture(Gdx.files.internal("BTNS/menuPressed.png"))
    private var font: BitmapFont = BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false)



    init {
        camera.setToOrtho(false, 800f, 480f)
        restart.x = 205f
        restart.y = 160f
        restart.width = 100f
        restart.height = 50f

        menu.x = 495f
        menu.y = 160f
        menu.width = 100f
        menu.height = 50f

    }

    override fun show() {
    }
    /**
     * Render method for drawing all objects
     */

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.OLIVE)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(restarT, restart.x, restart.y, restart.width, restart.height)
        batch.draw(menuT, menu.x, menu.y, menu.width, menu.height)
        font.draw(batch, "Your score: ${game.manager.getScore()}", 280f, 293f)



        if (Gdx.input.justTouched()) {
            camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))

            if (game.isClicked(restart, touch)) {
                batch.draw(restartTT, restart.x, restart.y, restart.width, restart.height)
                this.game.screen = DifficultyScreen(game)
                this.dispose()
            }

            if(game.isClicked(menu, touch)){
                batch.draw(menuTT, menu.x, menu.y, menu.width, menu.height)
                this.game.screen = MainMenuScreen(game)
                this.dispose()
            }
        }
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
        restarT.dispose()
        restartTT.dispose()
        menuT.dispose()
        menuTT.dispose()
    }

}