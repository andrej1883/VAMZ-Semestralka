package sk.uniza.fri.vamz.screeny

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import sk.uniza.fri.vamz.GameDifficulty
import sk.uniza.fri.vamz.Mazoorek

class DifficultyScreen(val pgame: Mazoorek) : Screen {
    private var game: Mazoorek = pgame
    private var batch = pgame.batch
    private var camera = OrthographicCamera()
    private var easy = Rectangle()
    private var medium = Rectangle()
    private var hard = Rectangle()

    private var easyT = Texture(Gdx.files.internal("BTNS/easyReleased.png"))
    private var mediumT = Texture(Gdx.files.internal("BTNS/mediumReleased.png"))
    private var hardT = Texture(Gdx.files.internal("BTNS/hardReleased.png"))
    private var easyTT = Texture(Gdx.files.internal("BTNS/easyPressed.png"))
    private var mediumTT = Texture(Gdx.files.internal("BTNS/mediumPressed.png"))
    private var hardTT = Texture(Gdx.files.internal("BTNS/hardPressed.png"))
    private var touch = Vector3()


    init {
        camera.setToOrtho(false, 800f, 480f)
        easy.x = 350f
        easy.y = 347.5f
        easy.width = 100f
        easy.height = 50f

        medium.x = 350f
        medium.y = 215f
        medium.width = 100f
        medium.height = 50f

        hard.x = 350f
        hard.y = 82.5f
        hard.width = 100f
        hard.height = 50f


    }

    override fun show() {
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.OLIVE)
        camera.update()
        game.batch.projectionMatrix = camera.combined

        batch.begin()
        batch.draw(easyT, easy.x, easy.y, easy.width, easy.height)
        batch.draw(mediumT, medium.x, medium.y, medium.width, medium.height)
        batch.draw(hardT, hard.x, hard.y, hard.width, hard.height)

        if (Gdx.input.justTouched()) {
            camera.unproject(touch.set(Gdx.input.getX().toFloat(), Gdx.input.getY().toFloat(), 0f))

            if (game.isClicked(hard, touch)) {
                game.manager.setDifficulty(GameDifficulty.HARD)
                batch.draw(hardTT, hard.x, hard.y, hard.width, hard.height)
                this.game.screen = GameScreen(game)
                this.dispose()
            }

            if (game.isClicked(easy, touch)) {
                game.manager.setDifficulty(GameDifficulty.EASY)
                batch.draw(easyTT, easy.x, easy.y, easy.width, easy.height)
                this.game.screen = GameScreen(game)
                this.dispose()
            }

            if(game.isClicked(medium, touch)){
                game.manager.setDifficulty(GameDifficulty.MEDIUM)
                batch.draw(mediumTT, medium.x, medium.y, medium.width, medium.height)
                this.game.screen = GameScreen(game)
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
        easyT.dispose()
        mediumT.dispose()
        hardT.dispose()
        easyTT.dispose()
        mediumTT.dispose()
        hardTT.dispose()
    }

}