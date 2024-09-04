package dev.asisee.donotdo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalImageLoader provides generateImageLoader()) {
                App()
            }
        }
    }

    private fun generateImageLoader() : ImageLoader {
        return ImageLoader {
            options {
                androidContext(applicationContext)
            }
            components {
                setupDefaultComponents()
            }
            interceptor {
                memoryCacheConfig {
                    maxSizePercent(applicationContext, 0.25)
                }
                diskCacheConfig {
                    directory(applicationContext.cacheDir.resolve("image_cache").toOkioPath())
                    maxSizeBytes(512L * 1024 * 1024)
                }
            }
        }
    }
}

