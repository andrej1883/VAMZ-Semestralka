package sk.uniza.fri.vamz.screeny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import sk.uniza.fri.vamz.Catcher
import sk.uniza.fri.vamz.GameManager
import sk.uniza.fri.vamz.Mazoorek

/**
 * Game screen
 * Screen where gameplay is displayed
 * @property pgame
 * @constructor Create empty Game screen
 */
class GameScreen(val pgame: Mazoorek) : Screen {
    private val game: Mazoorek = pgame
    private var musica: Music
    private var batch: SpriteBatch = game.batch
    private var camera: OrthographicCamera = OrthographicCamera()
    private var checker = game.manager
    private var ctchr: Catcher = Catcher(batch)
    private var backgr: Texture
    private var font: BitmapFont = BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false)
    private var back = Rectangle()
    private var menu = Rectangle()
    private var pauseG = Rectangle()
    private var touch = Vector3()

    private var backT = Texture(Gdx.files.internal("BTNS/backReleased.png"))
    private var backTP = Texture(Gdx.files.internal("BTNS/backPressed.png"))
    private var menuT = Texture(Gdx.files.internal("BTNS/menuReleased.png"))
    private var menuTT = Texture(Gdx.files.internal("BTNS/menuPressed.png"))
    private var pauseT = Texture(Gdx.files.internal("pause.png"))


    init {
        camera.setToOrtho(false, 800f, 480f)
        backgr = Texture(Gdx.files.internal("bela.png"))
        musica = Gdx.audio.newMusic(Gdx.files.internal("rez.mp3"))
        musica.volume = checker.getMscLvl()
        checker.reset()

        pauseG.width = 40f
        pauseG.height = 40f
        pauseG.y = 430f;
        pauseG.x = 380f

        back.width = 100f
        back.height = 50f
        back.x = 205f
        back.y = 160f

        menu.x = 495f
        menu.y = 160f
        menu.width = 100f
        menu.height = 50f
    }

    override fun show() {
    }

    /**
     * Method for rendering gameplay
     */
    override fun render(delta: Float) {
        ScreenUtils.clear(Color.OLIVE)
        musica.play()
        batch.setProjectionMatrix(camera.combined)

        if (Gdx.input.justTouched()) {

            camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))
            if (game.isClicked(pauseG, touch)) {
                checker.setState(GameManager.GameState.PAUSED)
            }
        }

        if (checker.getState() == GameManager.GameState.PAUSED) {
            batch.begin()
            font.draw(batch, "Ak sa ti podari skore presne 100k mas u mna pivo:D", 20f, 293f)
            batch.draw(menuT, menu.x, menu.y, menu.width, menu.height)
            batch.draw(backT, back.x, back.y, back.width, back.height)
            if (Gdx.input.justTouched()) {
                camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))

                if (game.isClicked(menu, touch)) {
                    batch.draw(menuTT, menu.x, menu.y, menu.width, menu.height)
                    this.game.screen = MainMenuScreen(game)
                    this.dispose()
                }

                if (game.isClicked(back, touch)) {
                    batch.draw(backTP, back.x, back.y, back.width, back.height)
                    checker.setState(GameManager.GameState.PLAYING)
                    camera.update()
                }
            }
            batch.end()
        } else {

            batch.begin()
            camera.update()
            batch.draw(backgr, 0f, 0f, 800f, 480f)
            batch.draw(pauseT, pauseG.x, pauseG.y, pauseG.width, pauseG.height)


            for (i in checker.getFObjs()) {
                i.display(batch)
            }
            ctchr.display()
            font.draw(batch, "Saved kebabs: ${checker.getKebabsCatched()}", 20f, 480f)
            font.draw(batch, "Dropped kebabs: ${checker.getKebabsDropped()}", 500f, 480f)
            batch.end()

            checker.check(ctchr, camera)
            if (checker.getState() == GameManager.GameState.GAME_OVER) {
                musica.stop()
                this.game.screen = GameOwerScreen(game)
                dispose()
            }
        }
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
        musica.dispose()
    }
}