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
 * Main menu screen
 * Main menu for game
 * @property pgame
 * @constructor Create empty Main menu screen
 */
class MainMenuScreen(val pgame: Mazoorek) : Screen {
    private var game: Mazoorek = pgame
    private var batch = pgame.batch
    private var camera = OrthographicCamera()
    private var play = Rectangle()
    private var sett = Rectangle()
    private var exit = Rectangle()

    private var playT = Texture(Gdx.files.internal("BTNS/playReleased.png"))
    private var setT = Texture(Gdx.files.internal("BTNS/optionsReleased.png"))
    private var exitT = Texture(Gdx.files.internal("BTNS/exitReleased.png"))
    private var playTP = Texture(Gdx.files.internal("BTNS/playPressed.png"))
    private var setTP = Texture(Gdx.files.internal("BTNS/optionsPressed.png"))
    private var exitTP = Texture(Gdx.files.internal("BTNS/exitPressed.png"))
    private var font: BitmapFont = BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false)
    private var touch = Vector3()


    init {
        camera.setToOrtho(false, 800f, 480f)
        play.x = 125f
        play.y = 25f
        play.width = 100f
        play.height = 50f

        sett.x = 350f
        sett.y = 25f
        sett.width = 100f
        sett.height = 50f

        exit.x = 575f
        exit.y = 25f
        exit.width = 100f
        exit.height = 50f

    }

    override fun show() {
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.OLIVE)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(playT, play.x, play.y, play.width, play.height)
        batch.draw(setT, sett.x, sett.y, sett.width, sett.height)
        batch.draw(exitT, exit.x, exit.y, exit.width, exit.height)
        font.draw(batch, "Highest score: ${game.manager.getHscore()}", 20f, 480f)



        if (Gdx.input.justTouched()) {
            camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))

            if (game.isClicked(exit, touch)) {
                batch.draw(exitTP, exit.x, exit.y, exit.width, exit.height)
                Gdx.app.exit()
                this.dispose()
            }

            if (game.isClicked(play, touch)) {
                batch.draw(playTP, play.x, play.y, play.width, play.height)
                this.game.screen = DifficultyScreen(game)
                this.dispose()
            }

            if(game.isClicked(sett, touch)){
                batch.draw(setTP, sett.x, sett.y, sett.width, sett.height)
                this.game.screen = OptionsWindow(game)
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
        playT.dispose()
        setT.dispose()
        exitT.dispose()
        playTP.dispose()
        setTP.dispose()
        exitTP.dispose()
    }

}