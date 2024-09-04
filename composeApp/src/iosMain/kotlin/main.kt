import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.seiko.imageloader.LocalImageLoader
import dev.asisee.donotdo.App
import dev.asisee.donotdo.Res
import dev.asisee.donotdo.generateImageLoader
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        CompositionLocalProvider(LocalImageLoader provides generateImageLoader()) {
            App()
        }
    }
}


